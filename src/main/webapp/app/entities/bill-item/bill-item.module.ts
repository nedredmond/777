import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppSharedModule } from 'app/shared';
import {
    BillItemComponent,
    BillItemDetailComponent,
    BillItemUpdateComponent,
    BillItemDeletePopupComponent,
    BillItemDeleteDialogComponent,
    billItemRoute,
    billItemPopupRoute
} from './';

const ENTITY_STATES = [...billItemRoute, ...billItemPopupRoute];

@NgModule({
    imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BillItemComponent,
        BillItemDetailComponent,
        BillItemUpdateComponent,
        BillItemDeleteDialogComponent,
        BillItemDeletePopupComponent
    ],
    entryComponents: [BillItemComponent, BillItemUpdateComponent, BillItemDeleteDialogComponent, BillItemDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppBillItemModule {}
