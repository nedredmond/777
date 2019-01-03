import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IMoneyAccount } from 'app/shared/model/money-account.model';
import { MoneyAccountService } from './money-account.service';
import { IUserDetails } from 'app/shared/model/user-details.model';
import { UserDetailsService } from 'app/entities/user-details';

@Component({
    selector: 'jhi-money-account-update',
    templateUrl: './money-account-update.component.html'
})
export class MoneyAccountUpdateComponent implements OnInit {
    moneyAccount: IMoneyAccount;
    isSaving: boolean;

    userdetails: IUserDetails[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected moneyAccountService: MoneyAccountService,
        protected userDetailsService: UserDetailsService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ moneyAccount }) => {
            this.moneyAccount = moneyAccount;
        });
        this.userDetailsService.query().subscribe(
            (res: HttpResponse<IUserDetails[]>) => {
                this.userdetails = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.moneyAccount.id !== undefined) {
            this.subscribeToSaveResponse(this.moneyAccountService.update(this.moneyAccount));
        } else {
            this.subscribeToSaveResponse(this.moneyAccountService.create(this.moneyAccount));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMoneyAccount>>) {
        result.subscribe((res: HttpResponse<IMoneyAccount>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUserDetailsById(index: number, item: IUserDetails) {
        return item.id;
    }
}
