/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AppTestModule } from '../../../test.module';
import { BudgetItemDeleteDialogComponent } from 'app/entities/budget-item/budget-item-delete-dialog.component';
import { BudgetItemService } from 'app/entities/budget-item/budget-item.service';

describe('Component Tests', () => {
    describe('BudgetItem Management Delete Component', () => {
        let comp: BudgetItemDeleteDialogComponent;
        let fixture: ComponentFixture<BudgetItemDeleteDialogComponent>;
        let service: BudgetItemService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AppTestModule],
                declarations: [BudgetItemDeleteDialogComponent]
            })
                .overrideTemplate(BudgetItemDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BudgetItemDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BudgetItemService);
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
