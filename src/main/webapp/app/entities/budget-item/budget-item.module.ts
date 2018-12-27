import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ProgressBarModule } from 'angular-progress-bar';

import { AppSharedModule } from 'app/shared';
import {
    BudgetItemComponent,
    BudgetItemDetailComponent,
    BudgetItemUpdateComponent,
    BudgetItemDeletePopupComponent,
    BudgetItemDeleteDialogComponent,
    budgetItemRoute,
    budgetItemPopupRoute
} from './';

const ENTITY_STATES = [...budgetItemRoute, ...budgetItemPopupRoute];

@NgModule({
    imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BudgetItemComponent,
        BudgetItemDetailComponent,
        BudgetItemUpdateComponent,
        BudgetItemDeleteDialogComponent,
        BudgetItemDeletePopupComponent,
        ProgressBarModule
    ],
    entryComponents: [BudgetItemComponent, BudgetItemUpdateComponent, BudgetItemDeleteDialogComponent, BudgetItemDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppBudgetItemModule {}
