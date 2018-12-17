import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBudget } from 'app/shared/model/budget.model';

type EntityResponseType = HttpResponse<IBudget>;
type EntityArrayResponseType = HttpResponse<IBudget[]>;

@Injectable({ providedIn: 'root' })
export class BudgetService {
    public resourceUrl = SERVER_API_URL + 'api/budgets';

    constructor(protected http: HttpClient) {}

    create(budget: IBudget): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(budget);
        return this.http
            .post<IBudget>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(budget: IBudget): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(budget);
        return this.http
            .put<IBudget>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IBudget>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IBudget[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(budget: IBudget): IBudget {
        const copy: IBudget = Object.assign({}, budget, {
            startDate: budget.startDate != null && budget.startDate.isValid() ? budget.startDate.format(DATE_FORMAT) : null,
            endDate: budget.endDate != null && budget.endDate.isValid() ? budget.endDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.startDate = res.body.startDate != null ? moment(res.body.startDate) : null;
            res.body.endDate = res.body.endDate != null ? moment(res.body.endDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((budget: IBudget) => {
                budget.startDate = budget.startDate != null ? moment(budget.startDate) : null;
                budget.endDate = budget.endDate != null ? moment(budget.endDate) : null;
            });
        }
        return res;
    }
}
