import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBills } from 'app/shared/model/bills.model';

type EntityResponseType = HttpResponse<IBills>;
type EntityArrayResponseType = HttpResponse<IBills[]>;

@Injectable({ providedIn: 'root' })
export class BillsService {
    public resourceUrl = SERVER_API_URL + 'api/bills';

    constructor(protected http: HttpClient) {}

    create(bills: IBills): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(bills);
        return this.http
            .post<IBills>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(bills: IBills): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(bills);
        return this.http
            .put<IBills>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IBills>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IBills[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(bills: IBills): IBills {
        const copy: IBills = Object.assign({}, bills, {
            dueDate: bills.dueDate != null && bills.dueDate.isValid() ? bills.dueDate.format(DATE_FORMAT) : null,
            paymentDate: bills.paymentDate != null && bills.paymentDate.isValid() ? bills.paymentDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dueDate = res.body.dueDate != null ? moment(res.body.dueDate) : null;
            res.body.paymentDate = res.body.paymentDate != null ? moment(res.body.paymentDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((bills: IBills) => {
                bills.dueDate = bills.dueDate != null ? moment(bills.dueDate) : null;
                bills.paymentDate = bills.paymentDate != null ? moment(bills.paymentDate) : null;
            });
        }
        return res;
    }
}
