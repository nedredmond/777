import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppSharedModule } from 'app/shared';
import {
    DashboardComponent,
    DashboardDetailComponent,
    DashboardUpdateComponent,
    DashboardDeletePopupComponent,
    DashboardDeleteDialogComponent,
    dashboardRoute,
    dashboardPopupRoute
} from './';

import { ProgressBarModule } from 'angular-progress-bar';

const ENTITY_STATES = [...dashboardRoute, ...dashboardPopupRoute];

@NgModule({
    imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES), ProgressBarModule],
    declarations: [
        DashboardComponent,
        DashboardDetailComponent,
        DashboardUpdateComponent,
        DashboardDeleteDialogComponent,
        DashboardDeletePopupComponent
    ],
    entryComponents: [DashboardComponent, DashboardUpdateComponent, DashboardDeleteDialogComponent, DashboardDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppDashboardModule {}
