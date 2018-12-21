/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { MoneyAccountUpdateComponent } from 'app/entities/money-account/money-account-update.component';
import { MoneyAccountService } from 'app/entities/money-account/money-account.service';
import { MoneyAccount } from 'app/shared/model/money-account.model';

describe('Component Tests', () => {
    describe('MoneyAccount Management Update Component', () => {
        let comp: MoneyAccountUpdateComponent;
        let fixture: ComponentFixture<MoneyAccountUpdateComponent>;
        let service: MoneyAccountService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AppTestModule],
                declarations: [MoneyAccountUpdateComponent]
            })
                .overrideTemplate(MoneyAccountUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MoneyAccountUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MoneyAccountService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new MoneyAccount(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.moneyAccount = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new MoneyAccount();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.moneyAccount = entity;
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
