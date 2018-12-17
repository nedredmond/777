import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBillItem } from 'app/shared/model/bill-item.model';
import { AccountService } from 'app/core';
import { BillItemService } from './bill-item.service';

@Component({
    selector: 'jhi-bill-item',
    templateUrl: './bill-item.component.html'
})
export class BillItemComponent implements OnInit, OnDestroy {
    billItems: IBillItem[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected billItemService: BillItemService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.billItemService.query().subscribe(
            (res: HttpResponse<IBillItem[]>) => {
                this.billItems = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInBillItems();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IBillItem) {
        return item.id;
    }

    registerChangeInBillItems() {
        this.eventSubscriber = this.eventManager.subscribe('billItemListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
