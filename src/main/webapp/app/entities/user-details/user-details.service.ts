import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IUserDetails } from 'app/shared/model/user-details.model';

type EntityResponseType = HttpResponse<IUserDetails>;
type EntityArrayResponseType = HttpResponse<IUserDetails[]>;

@Injectable({ providedIn: 'root' })
export class UserDetailsService {
    public resourceUrl = SERVER_API_URL + 'api/user-details';

    constructor(protected http: HttpClient) {}

    create(userDetails: IUserDetails): Observable<EntityResponseType> {
        return this.http.post<IUserDetails>(this.resourceUrl, userDetails, { observe: 'response' });
    }

    update(userDetails: IUserDetails): Observable<EntityResponseType> {
        return this.http.put<IUserDetails>(this.resourceUrl, userDetails, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IUserDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IUserDetails[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
