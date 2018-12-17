import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { MoneyAccount } from 'app/shared/model/money-account.model';
import { MoneyAccountService } from './money-account.service';
import { MoneyAccountComponent } from './money-account.component';
import { MoneyAccountDetailComponent } from './money-account-detail.component';
import { MoneyAccountUpdateComponent } from './money-account-update.component';
import { MoneyAccountDeletePopupComponent } from './money-account-delete-dialog.component';
import { IMoneyAccount } from 'app/shared/model/money-account.model';

@Injectable({ providedIn: 'root' })
export class MoneyAccountResolve implements Resolve<IMoneyAccount> {
    constructor(private service: MoneyAccountService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<MoneyAccount> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<MoneyAccount>) => response.ok),
                map((moneyAccount: HttpResponse<MoneyAccount>) => moneyAccount.body)
            );
        }
        return of(new MoneyAccount());
    }
}

export const moneyAccountRoute: Routes = [
    {
        path: 'money-account',
        component: MoneyAccountComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'App.moneyAccount.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'money-account/:id/view',
        component: MoneyAccountDetailComponent,
        resolve: {
            moneyAccount: MoneyAccountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'App.moneyAccount.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'money-account/new',
        component: MoneyAccountUpdateComponent,
        resolve: {
            moneyAccount: MoneyAccountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'App.moneyAccount.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'money-account/:id/edit',
        component: MoneyAccountUpdateComponent,
        resolve: {
            moneyAccount: MoneyAccountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'App.moneyAccount.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const moneyAccountPopupRoute: Routes = [
    {
        path: 'money-account/:id/delete',
        component: MoneyAccountDeletePopupComponent,
        resolve: {
            moneyAccount: MoneyAccountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'App.moneyAccount.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
