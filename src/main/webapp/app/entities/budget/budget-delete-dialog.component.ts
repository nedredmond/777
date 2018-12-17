import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBudget } from 'app/shared/model/budget.model';
import { BudgetService } from './budget.service';

@Component({
    selector: 'jhi-budget-delete-dialog',
    templateUrl: './budget-delete-dialog.component.html'
})
export class BudgetDeleteDialogComponent {
    budget: IBudget;

    constructor(protected budgetService: BudgetService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.budgetService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'budgetListModification',
                content: 'Deleted an budget'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-budget-delete-popup',
    template: ''
})
export class BudgetDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ budget }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(BudgetDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.budget = budget;
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
