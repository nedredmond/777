/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { UserDetailsUpdateComponent } from 'app/entities/user-details/user-details-update.component';
import { UserDetailsService } from 'app/entities/user-details/user-details.service';
import { UserDetails } from 'app/shared/model/user-details.model';

describe('Component Tests', () => {
    describe('UserDetails Management Update Component', () => {
        let comp: UserDetailsUpdateComponent;
        let fixture: ComponentFixture<UserDetailsUpdateComponent>;
        let service: UserDetailsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AppTestModule],
                declarations: [UserDetailsUpdateComponent]
            })
                .overrideTemplate(UserDetailsUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(UserDetailsUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserDetailsService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new UserDetails(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.userDetails = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new UserDetails();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.userDetails = entity;
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
