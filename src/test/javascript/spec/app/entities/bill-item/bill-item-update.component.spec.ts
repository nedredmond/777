/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { BillItemUpdateComponent } from 'app/entities/bill-item/bill-item-update.component';
import { BillItemService } from 'app/entities/bill-item/bill-item.service';
import { BillItem } from 'app/shared/model/bill-item.model';

describe('Component Tests', () => {
    describe('BillItem Management Update Component', () => {
        let comp: BillItemUpdateComponent;
        let fixture: ComponentFixture<BillItemUpdateComponent>;
        let service: BillItemService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AppTestModule],
                declarations: [BillItemUpdateComponent]
            })
                .overrideTemplate(BillItemUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BillItemUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BillItemService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new BillItem(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.billItem = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new BillItem();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.billItem = entity;
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
