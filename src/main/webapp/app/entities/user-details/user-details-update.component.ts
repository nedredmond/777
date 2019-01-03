import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IUserDetails } from 'app/shared/model/user-details.model';
import { UserDetailsService } from './user-details.service';

@Component({
    selector: 'jhi-user-details-update',
    templateUrl: './user-details-update.component.html'
})
export class UserDetailsUpdateComponent implements OnInit {
    userDetails: IUserDetails;
    isSaving: boolean;

    constructor(protected userDetailsService: UserDetailsService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ userDetails }) => {
            this.userDetails = userDetails;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.userDetails.id !== undefined) {
            this.subscribeToSaveResponse(this.userDetailsService.update(this.userDetails));
        } else {
            this.subscribeToSaveResponse(this.userDetailsService.create(this.userDetails));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserDetails>>) {
        result.subscribe((res: HttpResponse<IUserDetails>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
