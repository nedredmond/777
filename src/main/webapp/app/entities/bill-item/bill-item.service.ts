import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBillItem } from 'app/shared/model/bill-item.model';

type EntityResponseType = HttpResponse<IBillItem>;
type EntityArrayResponseType = HttpResponse<IBillItem[]>;

@Injectable({ providedIn: 'root' })
export class BillItemService {
    public resourceUrl = SERVER_API_URL + 'api/bill-items';

    constructor(protected http: HttpClient) {}

    create(billItem: IBillItem): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(billItem);
        return this.http
            .post<IBillItem>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(billItem: IBillItem): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(billItem);
        return this.http
            .put<IBillItem>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IBillItem>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IBillItem[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(billItem: IBillItem): IBillItem {
        const copy: IBillItem = Object.assign({}, billItem, {
            dueDate: billItem.dueDate != null && billItem.dueDate.isValid() ? billItem.dueDate.format(DATE_FORMAT) : null,
            paymentDate: billItem.paymentDate != null && billItem.paymentDate.isValid() ? billItem.paymentDate.format(DATE_FORMAT) : null
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
            res.body.forEach((billItem: IBillItem) => {
                billItem.dueDate = billItem.dueDate != null ? moment(billItem.dueDate) : null;
                billItem.paymentDate = billItem.paymentDate != null ? moment(billItem.paymentDate) : null;
            });
        }
        return res;
    }
}
