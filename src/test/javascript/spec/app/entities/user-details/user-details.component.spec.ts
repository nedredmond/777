/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AppTestModule } from '../../../test.module';
import { UserDetailsComponent } from 'app/entities/user-details/user-details.component';
import { UserDetailsService } from 'app/entities/user-details/user-details.service';
import { UserDetails } from 'app/shared/model/user-details.model';

describe('Component Tests', () => {
    describe('UserDetails Management Component', () => {
        let comp: UserDetailsComponent;
        let fixture: ComponentFixture<UserDetailsComponent>;
        let service: UserDetailsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AppTestModule],
                declarations: [UserDetailsComponent],
                providers: []
            })
                .overrideTemplate(UserDetailsComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(UserDetailsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserDetailsService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new UserDetails(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.userDetails[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
