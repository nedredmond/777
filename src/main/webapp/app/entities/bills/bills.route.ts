import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Bills } from 'app/shared/model/bills.model';
import { BillsService } from './bills.service';
import { BillsComponent } from './bills.component';
import { BillsDetailComponent } from './bills-detail.component';
import { BillsUpdateComponent } from './bills-update.component';
import { BillsDeletePopupComponent } from './bills-delete-dialog.component';
import { IBills } from 'app/shared/model/bills.model';

@Injectable({ providedIn: 'root' })
export class BillsResolve implements Resolve<IBills> {
    constructor(private service: BillsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Bills> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Bills>) => response.ok),
                map((bills: HttpResponse<Bills>) => bills.body)
            );
        }
        return of(new Bills());
    }
}

export const billsRoute: Routes = [
    {
        path: 'bills',
        component: BillsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'App.bills.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bills/:id/view',
        component: BillsDetailComponent,
        resolve: {
            bills: BillsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'App.bills.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bills/new',
        component: BillsUpdateComponent,
        resolve: {
            bills: BillsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'App.bills.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bills/:id/edit',
        component: BillsUpdateComponent,
        resolve: {
            bills: BillsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'App.bills.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const billsPopupRoute: Routes = [
    {
        path: 'bills/:id/delete',
        component: BillsDeletePopupComponent,
        resolve: {
            bills: BillsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'App.bills.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
