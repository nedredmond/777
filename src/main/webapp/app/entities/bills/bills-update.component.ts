import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IBills } from 'app/shared/model/bills.model';
import { BillsService } from './bills.service';

@Component({
    selector: 'jhi-bills-update',
    templateUrl: './bills-update.component.html'
})
export class BillsUpdateComponent implements OnInit {
    bills: IBills;
    isSaving: boolean;

    constructor(protected billsService: BillsService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ bills }) => {
            this.bills = bills;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.bills.id !== undefined) {
            this.subscribeToSaveResponse(this.billsService.update(this.bills));
        } else {
            this.subscribeToSaveResponse(this.billsService.create(this.bills));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IBills>>) {
        result.subscribe((res: HttpResponse<IBills>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
