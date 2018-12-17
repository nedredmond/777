/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AppTestModule } from '../../../test.module';
import { MoneyAccountComponent } from 'app/entities/money-account/money-account.component';
import { MoneyAccountService } from 'app/entities/money-account/money-account.service';
import { MoneyAccount } from 'app/shared/model/money-account.model';

describe('Component Tests', () => {
    describe('MoneyAccount Management Component', () => {
        let comp: MoneyAccountComponent;
        let fixture: ComponentFixture<MoneyAccountComponent>;
        let service: MoneyAccountService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AppTestModule],
                declarations: [MoneyAccountComponent],
                providers: []
            })
                .overrideTemplate(MoneyAccountComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MoneyAccountComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MoneyAccountService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new MoneyAccount(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.moneyAccounts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
