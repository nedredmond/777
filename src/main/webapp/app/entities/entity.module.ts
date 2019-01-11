import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AppUserDetailsModule } from './user-details/user-details.module';
import { AppMoneyAccountModule } from './money-account/money-account.module';
import { AppTransactionModule } from './transaction/transaction.module';
import { AppBudgetModule } from './budget/budget.module';
import { AppBudgetItemModule } from './budget-item/budget-item.module';
import { AppBillsModule } from './bills/bills.module';
import { AppBillItemModule } from './bill-item/bill-item.module';
import { AppDashboardModule } from 'app/entities/dashboard/dashboard.module';

/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        AppUserDetailsModule,
        AppMoneyAccountModule,
        AppTransactionModule,
        AppBudgetModule,
        AppBudgetItemModule,
        AppBillsModule,
        AppBillItemModule,
        AppDashboardModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppEntityModule {}
