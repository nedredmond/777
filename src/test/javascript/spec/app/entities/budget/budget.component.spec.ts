/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AppTestModule } from '../../../test.module';
import { BudgetComponent } from 'app/entities/budget/budget.component';
import { BudgetService } from 'app/entities/budget/budget.service';
import { Budget } from 'app/shared/model/budget.model';

describe('Component Tests', () => {
    describe('Budget Management Component', () => {
        let comp: BudgetComponent;
        let fixture: ComponentFixture<BudgetComponent>;
        let service: BudgetService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AppTestModule],
                declarations: [BudgetComponent],
                providers: []
            })
                .overrideTemplate(BudgetComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BudgetComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BudgetService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Budget(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.budgets[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
