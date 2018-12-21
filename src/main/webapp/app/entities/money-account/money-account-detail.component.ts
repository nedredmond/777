import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMoneyAccount } from 'app/shared/model/money-account.model';

@Component({
    selector: 'jhi-money-account-detail',
    templateUrl: './money-account-detail.component.html'
})
export class MoneyAccountDetailComponent implements OnInit {
    moneyAccount: IMoneyAccount;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ moneyAccount }) => {
            this.moneyAccount = moneyAccount;
        });
    }

    previousState() {
        window.history.back();
    }
}
