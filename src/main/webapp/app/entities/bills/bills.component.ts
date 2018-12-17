import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBills } from 'app/shared/model/bills.model';
import { AccountService } from 'app/core';
import { BillsService } from './bills.service';

@Component({
    selector: 'jhi-bills',
    templateUrl: './bills.component.html'
})
export class BillsComponent implements OnInit, OnDestroy {
    bills: IBills[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected billsService: BillsService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.billsService.query().subscribe(
            (res: HttpResponse<IBills[]>) => {
                this.bills = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInBills();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IBills) {
        return item.id;
    }

    registerChangeInBills() {
        this.eventSubscriber = this.eventManager.subscribe('billsListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
