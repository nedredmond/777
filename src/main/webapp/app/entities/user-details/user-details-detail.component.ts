import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserDetails } from 'app/shared/model/user-details.model';

@Component({
    selector: 'jhi-user-details-detail',
    templateUrl: './user-details-detail.component.html'
})
export class UserDetailsDetailComponent implements OnInit {
    userDetails: IUserDetails;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ userDetails }) => {
            this.userDetails = userDetails;
        });
    }

    previousState() {
        window.history.back();
    }
}
