/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AppTestModule } from '../../../test.module';
import { BudgetItemComponent } from 'app/entities/budget-item/budget-item.component';
import { BudgetItemService } from 'app/entities/budget-item/budget-item.service';
import { BudgetItem } from 'app/shared/model/budget-item.model';

describe('Component Tests', () => {
    describe('BudgetItem Management Component', () => {
        let comp: BudgetItemComponent;
        let fixture: ComponentFixture<BudgetItemComponent>;
        let service: BudgetItemService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AppTestModule],
                declarations: [BudgetItemComponent],
                providers: []
            })
                .overrideTemplate(BudgetItemComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BudgetItemComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BudgetItemService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new BudgetItem(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.budgetItems[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
