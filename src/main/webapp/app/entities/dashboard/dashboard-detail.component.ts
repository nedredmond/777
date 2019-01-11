import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDashboard } from 'app/shared/model/dashboard.model';

@Component({
    selector: 'jhi-dashboard-detail',
    templateUrl: './dashboard-detail.component.html'
})
export class DashboardDetailComponent implements OnInit {
    dashboard: IDashboard;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ dashboard }) => {
            this.dashboard = dashboard;
        });
    }

    previousState() {
        window.history.back();
    }
}
