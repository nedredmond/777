import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDashboard } from 'app/shared/model/dashboard.model';
import { IMoneyAccount } from 'app/shared/model/money-account.model';
import { IBillItem } from 'app/shared/model/bill-item.model';
import { ITransaction } from 'app/shared/model/transaction.model';
import { IBudget } from 'app/shared/model/budget.model';

type EntityResponseType = HttpResponse<any>;
type EntityArrayResponseType = HttpResponse<any>;

@Injectable({ providedIn: 'root' })
export class DashboardService {
    public resourceUrl = SERVER_API_URL + 'api';

    constructor(protected http: HttpClient) {}

    create(dashboard: IDashboard): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(dashboard);
        return this.http
            .post<IDashboard>(this.resourceUrl + '/dashboard', copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(dashboard: IDashboard): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(dashboard);
        return this.http
            .put<IDashboard>(this.resourceUrl + '/dashboard', copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IDashboard>(`${this.resourceUrl + '/dashboard'}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IDashboard[]>(this.resourceUrl + '/dashboard', { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl + '/dashboard'}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(dashboard: IDashboard): IDashboard {
        const copy: IDashboard = Object.assign({}, dashboard, {
            transaction: dashboard.transaction != null && dashboard.transaction.isValid() ? dashboard.transaction.toJSON() : null,
            bills: dashboard.bills != null && dashboard.bills.isValid() ? dashboard.bills.toJSON() : null,
            accounts: dashboard.accounts != null && dashboard.accounts.isValid() ? dashboard.accounts.toJSON() : null,
            budget: dashboard.budget != null && dashboard.budget.isValid() ? dashboard.budget.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.transaction = res.body.transaction != null ? moment(res.body.transaction) : null;
            res.body.bills = res.body.bills != null ? moment(res.body.bills) : null;
            res.body.accounts = res.body.accounts != null ? moment(res.body.accounts) : null;
            res.body.budget = res.body.budget != null ? moment(res.body.budget) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((dashboard: IDashboard) => {
                dashboard.transaction = dashboard.transaction != null ? moment(dashboard.transaction) : null;
                dashboard.bills = dashboard.bills != null ? moment(dashboard.bills) : null;
                dashboard.accounts = dashboard.accounts != null ? moment(dashboard.accounts) : null;
                dashboard.budget = dashboard.budget != null ? moment(dashboard.budget) : null;
            });
        }
        return res;
    }

    getBudgetForCurrentMonth(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IBudget[]>(this.resourceUrl + '/budgets/currentmonth', { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    getAllMoneyAccount(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IMoneyAccount[]>(this.resourceUrl + '/money-accounts', { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    getUnpaidBills(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IBillItem[]>(this.resourceUrl + '/bill-items/unpaid', { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    getLateBills(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IBillItem[]>(this.resourceUrl + '/bill-items/late', { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    getRecentTransaction(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ITransaction[]>(this.resourceUrl + '/transactions/recent', { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }
}
