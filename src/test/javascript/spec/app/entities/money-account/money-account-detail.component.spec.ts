/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { MoneyAccountDetailComponent } from 'app/entities/money-account/money-account-detail.component';
import { MoneyAccount } from 'app/shared/model/money-account.model';

describe('Component Tests', () => {
    describe('MoneyAccount Management Detail Component', () => {
        let comp: MoneyAccountDetailComponent;
        let fixture: ComponentFixture<MoneyAccountDetailComponent>;
        const route = ({ data: of({ moneyAccount: new MoneyAccount(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AppTestModule],
                declarations: [MoneyAccountDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MoneyAccountDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MoneyAccountDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.moneyAccount).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
