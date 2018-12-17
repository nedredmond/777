/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AppTestModule } from '../../../test.module';
import { MoneyAccountDeleteDialogComponent } from 'app/entities/money-account/money-account-delete-dialog.component';
import { MoneyAccountService } from 'app/entities/money-account/money-account.service';

describe('Component Tests', () => {
    describe('MoneyAccount Management Delete Component', () => {
        let comp: MoneyAccountDeleteDialogComponent;
        let fixture: ComponentFixture<MoneyAccountDeleteDialogComponent>;
        let service: MoneyAccountService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AppTestModule],
                declarations: [MoneyAccountDeleteDialogComponent]
            })
                .overrideTemplate(MoneyAccountDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MoneyAccountDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MoneyAccountService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
