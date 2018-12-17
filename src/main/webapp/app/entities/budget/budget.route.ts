import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Budget } from 'app/shared/model/budget.model';
import { BudgetService } from './budget.service';
import { BudgetComponent } from './budget.component';
import { BudgetDetailComponent } from './budget-detail.component';
import { BudgetUpdateComponent } from './budget-update.component';
import { BudgetDeletePopupComponent } from './budget-delete-dialog.component';
import { IBudget } from 'app/shared/model/budget.model';

@Injectable({ providedIn: 'root' })
export class BudgetResolve implements Resolve<IBudget> {
    constructor(private service: BudgetService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Budget> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Budget>) => response.ok),
                map((budget: HttpResponse<Budget>) => budget.body)
            );
        }
        return of(new Budget());
    }
}

export const budgetRoute: Routes = [
    {
        path: 'budget',
        component: BudgetComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'App.budget.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'budget/:id/view',
        component: BudgetDetailComponent,
        resolve: {
            budget: BudgetResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'App.budget.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'budget/new',
        component: BudgetUpdateComponent,
        resolve: {
            budget: BudgetResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'App.budget.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'budget/:id/edit',
        component: BudgetUpdateComponent,
        resolve: {
            budget: BudgetResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'App.budget.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const budgetPopupRoute: Routes = [
    {
        path: 'budget/:id/delete',
        component: BudgetDeletePopupComponent,
        resolve: {
            budget: BudgetResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'App.budget.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
