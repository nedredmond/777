import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IBudgetItem } from 'app/shared/model/budget-item.model';
import { BudgetItemService } from './budget-item.service';
import { IBudget } from 'app/shared/model/budget.model';
import { BudgetService } from 'app/entities/budget';

@Component({
    selector: 'jhi-budget-item-update',
    templateUrl: './budget-item-update.component.html'
})
export class BudgetItemUpdateComponent implements OnInit {
    budgetItem: IBudgetItem;
    isSaving: boolean;

    budgets: IBudget[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected budgetItemService: BudgetItemService,
        protected budgetService: BudgetService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ budgetItem }) => {
            this.budgetItem = budgetItem;
        });
        this.budgetService.query().subscribe(
            (res: HttpResponse<IBudget[]>) => {
                this.budgets = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.budgetItem.id !== undefined) {
            this.subscribeToSaveResponse(this.budgetItemService.update(this.budgetItem));
        } else {
            this.subscribeToSaveResponse(this.budgetItemService.create(this.budgetItem));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IBudgetItem>>) {
        result.subscribe((res: HttpResponse<IBudgetItem>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackBudgetById(index: number, item: IBudget) {
        return item.id;
    }
}
