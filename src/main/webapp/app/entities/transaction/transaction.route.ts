import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Transaction } from 'app/shared/model/transaction.model';
import { TransactionService } from './transaction.service';
import { TransactionComponent } from './transaction.component';
import { TransactionDetailComponent } from './transaction-detail.component';
import { TransactionUpdateComponent } from './transaction-update.component';
import { TransactionDeletePopupComponent } from './transaction-delete-dialog.component';
import { ITransaction } from 'app/shared/model/transaction.model';

@Injectable({ providedIn: 'root' })
export class TransactionResolve implements Resolve<ITransaction> {
    constructor(private service: TransactionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Transaction> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Transaction>) => response.ok),
                map((transaction: HttpResponse<Transaction>) => transaction.body)
            );
        }
        return of(new Transaction());
    }
}

export const transactionRoute: Routes = [
    {
        path: 'transaction',
        component: TransactionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'App.transaction.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'transaction/:id/view',
        component: TransactionDetailComponent,
        resolve: {
            transaction: TransactionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'App.transaction.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'transaction/new',
        component: TransactionUpdateComponent,
        resolve: {
            transaction: TransactionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'App.transaction.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'transaction/:id/edit',
        component: TransactionUpdateComponent,
        resolve: {
            transaction: TransactionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'App.transaction.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const transactionPopupRoute: Routes = [
    {
        path: 'transaction/:id/delete',
        component: TransactionDeletePopupComponent,
        resolve: {
            transaction: TransactionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'App.transaction.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
