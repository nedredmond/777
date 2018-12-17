/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AppTestModule } from '../../../test.module';
import { BillItemComponent } from 'app/entities/bill-item/bill-item.component';
import { BillItemService } from 'app/entities/bill-item/bill-item.service';
import { BillItem } from 'app/shared/model/bill-item.model';

describe('Component Tests', () => {
    describe('BillItem Management Component', () => {
        let comp: BillItemComponent;
        let fixture: ComponentFixture<BillItemComponent>;
        let service: BillItemService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AppTestModule],
                declarations: [BillItemComponent],
                providers: []
            })
                .overrideTemplate(BillItemComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BillItemComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BillItemService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new BillItem(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.billItems[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
