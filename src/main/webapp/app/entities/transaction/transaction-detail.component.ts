import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITransaction } from 'app/shared/model/transaction.model';

@Component({
    selector: 'jhi-transaction-detail',
    templateUrl: './transaction-detail.component.html'
})
export class TransactionDetailComponent implements OnInit {
    transaction: ITransaction;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ transaction }) => {
            this.transaction = transaction;
        });
    }

    previousState() {
        window.history.back();
    }
}
