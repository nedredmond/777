/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { BillsDetailComponent } from 'app/entities/bills/bills-detail.component';
import { Bills } from 'app/shared/model/bills.model';

describe('Component Tests', () => {
    describe('Bills Management Detail Component', () => {
        let comp: BillsDetailComponent;
        let fixture: ComponentFixture<BillsDetailComponent>;
        const route = ({ data: of({ bills: new Bills(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AppTestModule],
                declarations: [BillsDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BillsDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BillsDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.bills).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
