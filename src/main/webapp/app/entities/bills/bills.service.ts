import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

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
        return this.http.post<IBills>(this.resourceUrl, bills, { observe: 'response' });
    }

    update(bills: IBills): Observable<EntityResponseType> {
        return this.http.put<IBills>(this.resourceUrl, bills, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IBills>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBills[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
