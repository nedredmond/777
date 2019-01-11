/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { DashboardUpdateComponent } from 'app/entities/dashboard/dashboard-update.component';
import { DashboardService } from 'app/entities/dashboard/dashboard.service';
import { Dashboard } from 'app/shared/model/dashboard.model';

describe('Component Tests', () => {
    describe('Dashboard Management Update Component', () => {
        let comp: DashboardUpdateComponent;
        let fixture: ComponentFixture<DashboardUpdateComponent>;
        let service: DashboardService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AppTestModule],
                declarations: [DashboardUpdateComponent]
            })
                .overrideTemplate(DashboardUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DashboardUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DashboardService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Dashboard(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.dashboard = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Dashboard();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.dashboard = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
