import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { BillItem } from 'app/shared/model/bill-item.model';
import { BillItemService } from './bill-item.service';
import { BillItemComponent } from './bill-item.component';
import { BillItemDetailComponent } from './bill-item-detail.component';
import { BillItemUpdateComponent } from './bill-item-update.component';
import { BillItemDeletePopupComponent } from './bill-item-delete-dialog.component';
import { IBillItem } from 'app/shared/model/bill-item.model';

@Injectable({ providedIn: 'root' })
export class BillItemResolve implements Resolve<IBillItem> {
    constructor(private service: BillItemService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<BillItem> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<BillItem>) => response.ok),
                map((billItem: HttpResponse<BillItem>) => billItem.body)
            );
        }
        return of(new BillItem());
    }
}

export const billItemRoute: Routes = [
    {
        path: 'bill-item',
        component: BillItemComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'App.billItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bill-item/:id/view',
        component: BillItemDetailComponent,
        resolve: {
            billItem: BillItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'App.billItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bill-item/new',
        component: BillItemUpdateComponent,
        resolve: {
            billItem: BillItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'App.billItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bill-item/:id/edit',
        component: BillItemUpdateComponent,
        resolve: {
            billItem: BillItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'App.billItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const billItemPopupRoute: Routes = [
    {
        path: 'bill-item/:id/delete',
        component: BillItemDeletePopupComponent,
        resolve: {
            billItem: BillItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'App.billItem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
