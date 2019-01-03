import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IUserDetails } from 'app/shared/model/user-details.model';
import { AccountService } from 'app/core';
import { UserDetailsService } from './user-details.service';

@Component({
    selector: 'jhi-user-details',
    templateUrl: './user-details.component.html'
})
export class UserDetailsComponent implements OnInit, OnDestroy {
    userDetails: IUserDetails[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected userDetailsService: UserDetailsService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.userDetailsService.query().subscribe(
            (res: HttpResponse<IUserDetails[]>) => {
                this.userDetails = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInUserDetails();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IUserDetails) {
        return item.id;
    }

    registerChangeInUserDetails() {
        this.eventSubscriber = this.eventManager.subscribe('userDetailsListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
