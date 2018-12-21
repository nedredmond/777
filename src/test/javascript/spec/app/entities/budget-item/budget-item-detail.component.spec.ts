/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { BudgetItemDetailComponent } from 'app/entities/budget-item/budget-item-detail.component';
import { BudgetItem } from 'app/shared/model/budget-item.model';

describe('Component Tests', () => {
    describe('BudgetItem Management Detail Component', () => {
        let comp: BudgetItemDetailComponent;
        let fixture: ComponentFixture<BudgetItemDetailComponent>;
        const route = ({ data: of({ budgetItem: new BudgetItem(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AppTestModule],
                declarations: [BudgetItemDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BudgetItemDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BudgetItemDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.budgetItem).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
