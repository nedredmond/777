/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { BillsUpdateComponent } from 'app/entities/bills/bills-update.component';
import { BillsService } from 'app/entities/bills/bills.service';
import { Bills } from 'app/shared/model/bills.model';

describe('Component Tests', () => {
    describe('Bills Management Update Component', () => {
        let comp: BillsUpdateComponent;
        let fixture: ComponentFixture<BillsUpdateComponent>;
        let service: BillsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AppTestModule],
                declarations: [BillsUpdateComponent]
            })
                .overrideTemplate(BillsUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BillsUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BillsService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Bills(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.bills = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Bills();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.bills = entity;
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
