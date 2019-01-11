import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDashboard } from 'app/shared/model/dashboard.model';
import { DashboardService } from './dashboard.service';

@Component({
    selector: 'jhi-dashboard-delete-dialog',
    templateUrl: './dashboard-delete-dialog.component.html'
})
export class DashboardDeleteDialogComponent {
    dashboard: IDashboard;

    constructor(
        protected dashboardService: DashboardService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.dashboardService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'dashboardListModification',
                content: 'Deleted an dashboard'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-dashboard-delete-popup',
    template: ''
})
export class DashboardDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ dashboard }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DashboardDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.dashboard = dashboard;
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
