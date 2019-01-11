import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IDashboard } from 'app/shared/model/dashboard.model';
import { DashboardService } from './dashboard.service';

@Component({
    selector: 'jhi-dashboard-update',
    templateUrl: './dashboard-update.component.html'
})
export class DashboardUpdateComponent implements OnInit {
    dashboard: IDashboard;
    isSaving: boolean;
    transaction: string;
    bills: string;
    accounts: string;
    budget: string;

    constructor(protected dashboardService: DashboardService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ dashboard }) => {
            this.dashboard = dashboard;
            this.transaction = this.dashboard.transaction != null ? this.dashboard.transaction.format(DATE_TIME_FORMAT) : null;
            this.bills = this.dashboard.bills != null ? this.dashboard.bills.format(DATE_TIME_FORMAT) : null;
            this.accounts = this.dashboard.accounts != null ? this.dashboard.accounts.format(DATE_TIME_FORMAT) : null;
            this.budget = this.dashboard.budget != null ? this.dashboard.budget.format(DATE_TIME_FORMAT) : null;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.dashboard.transaction = this.transaction != null ? moment(this.transaction, DATE_TIME_FORMAT) : null;
        this.dashboard.bills = this.bills != null ? moment(this.bills, DATE_TIME_FORMAT) : null;
        this.dashboard.accounts = this.accounts != null ? moment(this.accounts, DATE_TIME_FORMAT) : null;
        this.dashboard.budget = this.budget != null ? moment(this.budget, DATE_TIME_FORMAT) : null;
        if (this.dashboard.id !== undefined) {
            this.subscribeToSaveResponse(this.dashboardService.update(this.dashboard));
        } else {
            this.subscribeToSaveResponse(this.dashboardService.create(this.dashboard));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDashboard>>) {
        result.subscribe((res: HttpResponse<IDashboard>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
