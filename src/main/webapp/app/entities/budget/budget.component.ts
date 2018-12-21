import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBudget } from 'app/shared/model/budget.model';
import { AccountService } from 'app/core';
import { BudgetService } from './budget.service';

@Component({
    selector: 'jhi-budget',
    templateUrl: './budget.component.html'
})
export class BudgetComponent implements OnInit, OnDestroy {
    budgets: IBudget[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected budgetService: BudgetService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.budgetService.query().subscribe(
            (res: HttpResponse<IBudget[]>) => {
                this.budgets = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInBudgets();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IBudget) {
        return item.id;
    }

    registerChangeInBudgets() {
        this.eventSubscriber = this.eventManager.subscribe('budgetListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
