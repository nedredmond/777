import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Dashboard } from 'app/shared/model/dashboard.model';
import { DashboardService } from './dashboard.service';
import { DashboardComponent } from './dashboard.component';
import { DashboardDetailComponent } from './dashboard-detail.component';
import { DashboardUpdateComponent } from './dashboard-update.component';
import { DashboardDeletePopupComponent } from './dashboard-delete-dialog.component';
import { IDashboard } from 'app/shared/model/dashboard.model';

@Injectable({ providedIn: 'root' })
export class DashboardResolve implements Resolve<IDashboard> {
    constructor(private service: DashboardService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Dashboard> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Dashboard>) => response.ok),
                map((dashboard: HttpResponse<Dashboard>) => dashboard.body)
            );
        }
        return of(new Dashboard());
    }
}

export const dashboardRoute: Routes = [
    {
        path: 'dashboard',
        component: DashboardComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'App.dashboard.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dashboard/:id/view',
        component: DashboardDetailComponent,
        resolve: {
            dashboard: DashboardResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'App.dashboard.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dashboard/new',
        component: DashboardUpdateComponent,
        resolve: {
            dashboard: DashboardResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'App.dashboard.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dashboard/:id/edit',
        component: DashboardUpdateComponent,
        resolve: {
            dashboard: DashboardResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'App.dashboard.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dashboardPopupRoute: Routes = [
    {
        path: 'dashboard/:id/delete',
        component: DashboardDeletePopupComponent,
        resolve: {
            dashboard: DashboardResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'App.dashboard.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
