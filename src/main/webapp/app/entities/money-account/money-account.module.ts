import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppSharedModule } from 'app/shared';
import { AppAdminModule } from 'app/admin/admin.module';
import {
    MoneyAccountComponent,
    MoneyAccountDetailComponent,
    MoneyAccountUpdateComponent,
    MoneyAccountDeletePopupComponent,
    MoneyAccountDeleteDialogComponent,
    moneyAccountRoute,
    moneyAccountPopupRoute
} from './';

const ENTITY_STATES = [...moneyAccountRoute, ...moneyAccountPopupRoute];

@NgModule({
    imports: [AppSharedModule, AppAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MoneyAccountComponent,
        MoneyAccountDetailComponent,
        MoneyAccountUpdateComponent,
        MoneyAccountDeleteDialogComponent,
        MoneyAccountDeletePopupComponent
    ],
    entryComponents: [
        MoneyAccountComponent,
        MoneyAccountUpdateComponent,
        MoneyAccountDeleteDialogComponent,
        MoneyAccountDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppMoneyAccountModule {}
