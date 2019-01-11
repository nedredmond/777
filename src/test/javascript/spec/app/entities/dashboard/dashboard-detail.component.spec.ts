/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { DashboardDetailComponent } from 'app/entities/dashboard/dashboard-detail.component';
import { Dashboard } from 'app/shared/model/dashboard.model';

describe('Component Tests', () => {
    describe('Dashboard Management Detail Component', () => {
        let comp: DashboardDetailComponent;
        let fixture: ComponentFixture<DashboardDetailComponent>;
        const route = ({ data: of({ dashboard: new Dashboard(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AppTestModule],
                declarations: [DashboardDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DashboardDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DashboardDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.dashboard).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
