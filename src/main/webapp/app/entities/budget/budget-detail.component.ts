import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBudget } from 'app/shared/model/budget.model';

@Component({
    selector: 'jhi-budget-detail',
    templateUrl: './budget-detail.component.html'
})
export class BudgetDetailComponent implements OnInit {
    budget: IBudget;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ budget }) => {
            this.budget = budget;
        });
    }

    previousState() {
        window.history.back();
    }
}
