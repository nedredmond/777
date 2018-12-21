import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IMoneyAccount } from 'app/shared/model/money-account.model';
import { AccountService } from 'app/core';
import { MoneyAccountService } from './money-account.service';

@Component({
    selector: 'jhi-money-account',
    templateUrl: './money-account.component.html'
})
export class MoneyAccountComponent implements OnInit, OnDestroy {
    moneyAccounts: IMoneyAccount[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected moneyAccountService: MoneyAccountService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.moneyAccountService.query().subscribe(
            (res: HttpResponse<IMoneyAccount[]>) => {
                this.moneyAccounts = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInMoneyAccounts();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IMoneyAccount) {
        return item.id;
    }

    registerChangeInMoneyAccounts() {
        this.eventSubscriber = this.eventManager.subscribe('moneyAccountListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
