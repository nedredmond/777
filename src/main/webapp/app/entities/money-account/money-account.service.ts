import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMoneyAccount } from 'app/shared/model/money-account.model';

type EntityResponseType = HttpResponse<IMoneyAccount>;
type EntityArrayResponseType = HttpResponse<IMoneyAccount[]>;

@Injectable({ providedIn: 'root' })
export class MoneyAccountService {
    public resourceUrl = SERVER_API_URL + 'api/money-accounts';

    constructor(protected http: HttpClient) {}

    create(moneyAccount: IMoneyAccount): Observable<EntityResponseType> {
        return this.http.post<IMoneyAccount>(this.resourceUrl, moneyAccount, { observe: 'response' });
    }

    update(moneyAccount: IMoneyAccount): Observable<EntityResponseType> {
        return this.http.put<IMoneyAccount>(this.resourceUrl, moneyAccount, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IMoneyAccount>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMoneyAccount[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
