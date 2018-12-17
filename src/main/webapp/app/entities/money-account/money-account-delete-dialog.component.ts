import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMoneyAccount } from 'app/shared/model/money-account.model';
import { MoneyAccountService } from './money-account.service';

@Component({
    selector: 'jhi-money-account-delete-dialog',
    templateUrl: './money-account-delete-dialog.component.html'
})
export class MoneyAccountDeleteDialogComponent {
    moneyAccount: IMoneyAccount;

    constructor(
        protected moneyAccountService: MoneyAccountService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.moneyAccountService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'moneyAccountListModification',
                content: 'Deleted an moneyAccount'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-money-account-delete-popup',
    template: ''
})
export class MoneyAccountDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ moneyAccount }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MoneyAccountDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.moneyAccount = moneyAccount;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
