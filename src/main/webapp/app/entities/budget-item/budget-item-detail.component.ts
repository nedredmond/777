import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBudgetItem } from 'app/shared/model/budget-item.model';

@Component({
    selector: 'jhi-budget-item-detail',
    templateUrl: './budget-item-detail.component.html'
})
export class BudgetItemDetailComponent implements OnInit {
    budgetItem: IBudgetItem;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ budgetItem }) => {
            this.budgetItem = budgetItem;
        });
    }

    previousState() {
        window.history.back();
    }
}
