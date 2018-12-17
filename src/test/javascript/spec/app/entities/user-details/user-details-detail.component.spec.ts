/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { UserDetailsDetailComponent } from 'app/entities/user-details/user-details-detail.component';
import { UserDetails } from 'app/shared/model/user-details.model';

describe('Component Tests', () => {
    describe('UserDetails Management Detail Component', () => {
        let comp: UserDetailsDetailComponent;
        let fixture: ComponentFixture<UserDetailsDetailComponent>;
        const route = ({ data: of({ userDetails: new UserDetails(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AppTestModule],
                declarations: [UserDetailsDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(UserDetailsDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(UserDetailsDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.userDetails).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
