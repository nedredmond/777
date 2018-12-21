/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { BudgetDetailComponent } from 'app/entities/budget/budget-detail.component';
import { Budget } from 'app/shared/model/budget.model';

describe('Component Tests', () => {
    describe('Budget Management Detail Component', () => {
        let comp: BudgetDetailComponent;
        let fixture: ComponentFixture<BudgetDetailComponent>;
        const route = ({ data: of({ budget: new Budget(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AppTestModule],
                declarations: [BudgetDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BudgetDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BudgetDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.budget).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
