/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { BudgetItemUpdateComponent } from 'app/entities/budget-item/budget-item-update.component';
import { BudgetItemService } from 'app/entities/budget-item/budget-item.service';
import { BudgetItem } from 'app/shared/model/budget-item.model';

describe('Component Tests', () => {
    describe('BudgetItem Management Update Component', () => {
        let comp: BudgetItemUpdateComponent;
        let fixture: ComponentFixture<BudgetItemUpdateComponent>;
        let service: BudgetItemService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AppTestModule],
                declarations: [BudgetItemUpdateComponent]
            })
                .overrideTemplate(BudgetItemUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BudgetItemUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BudgetItemService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new BudgetItem(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.budgetItem = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new BudgetItem();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.budgetItem = entity;
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
