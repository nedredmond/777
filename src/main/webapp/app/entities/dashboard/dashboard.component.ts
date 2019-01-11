import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDashboard } from 'app/shared/model/dashboard.model';
import { AccountService } from 'app/core';
import { DashboardService } from './dashboard.service';
import { IMoneyAccount } from 'app/shared/model/money-account.model';
import { IBillItem } from 'app/shared/model/bill-item.model';
import { IBudget } from 'app/shared/model/budget.model';
import { ITransaction } from 'app/shared/model/transaction.model';

@Component({
    selector: 'jhi-dashboard',
    templateUrl: './dashboard.component.html'
})
export class DashboardComponent implements OnInit, OnDestroy {
    dashboards: IDashboard[];
    currentAccount: any;
    eventSubscriber: Subscription;
    budgets: IBudget[];
    moneyAccounts: IMoneyAccount[];
    lateBills: IBillItem[];
    unpaidBills: IBillItem[];
    transactions: ITransaction[];

    constructor(
        protected dashboardService: DashboardService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.dashboardService.query().subscribe(
            (res: HttpResponse<IDashboard[]>) => {
                this.dashboards = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.getRecentTransaction();
        this.getLateBills();
        this.getUnpaidBills();
        this.getAllMoneyAccount();
        this.getBudgetForCurrentMonth();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInDashboards();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IDashboard) {
        return item.id;
    }

    registerChangeInDashboards() {
        this.eventSubscriber = this.eventManager.subscribe('dashboardListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    getBudgetForCurrentMonth() {
        this.dashboardService.getBudgetForCurrentMonth().subscribe(
            (res: HttpResponse<IBudget[]>) => {
                this.budgets = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    getAllMoneyAccount() {
        this.dashboardService.getAllMoneyAccount().subscribe(
            (res: HttpResponse<IMoneyAccount[]>) => {
                this.moneyAccounts = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    getUnpaidBills() {
        this.dashboardService.getUnpaidBills().subscribe(
            (res: HttpResponse<IBillItem[]>) => {
                this.unpaidBills = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    getLateBills() {
        this.dashboardService.getLateBills().subscribe(
            (res: HttpResponse<IBillItem[]>) => {
                this.lateBills = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    getRecentTransaction() {
        this.dashboardService.getRecentTransaction().subscribe(
            (res: HttpResponse<ITransaction[]>) => {
                this.transactions = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
}
