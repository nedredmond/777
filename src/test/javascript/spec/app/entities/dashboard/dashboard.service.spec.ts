/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { DashboardService } from 'app/entities/dashboard/dashboard.service';
import { IDashboard, Dashboard } from 'app/shared/model/dashboard.model';

describe('Service Tests', () => {
    describe('Dashboard Service', () => {
        let injector: TestBed;
        let service: DashboardService;
        let httpMock: HttpTestingController;
        let elemDefault: IDashboard;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(DashboardService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Dashboard(0, currentDate, currentDate, currentDate, currentDate);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        transaction: currentDate.format(DATE_TIME_FORMAT),
                        bills: currentDate.format(DATE_TIME_FORMAT),
                        accounts: currentDate.format(DATE_TIME_FORMAT),
                        budget: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Dashboard', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        transaction: currentDate.format(DATE_TIME_FORMAT),
                        bills: currentDate.format(DATE_TIME_FORMAT),
                        accounts: currentDate.format(DATE_TIME_FORMAT),
                        budget: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        transaction: currentDate,
                        bills: currentDate,
                        accounts: currentDate,
                        budget: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Dashboard(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Dashboard', async () => {
                const returnedFromService = Object.assign(
                    {
                        transaction: currentDate.format(DATE_TIME_FORMAT),
                        bills: currentDate.format(DATE_TIME_FORMAT),
                        accounts: currentDate.format(DATE_TIME_FORMAT),
                        budget: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        transaction: currentDate,
                        bills: currentDate,
                        accounts: currentDate,
                        budget: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Dashboard', async () => {
                const returnedFromService = Object.assign(
                    {
                        transaction: currentDate.format(DATE_TIME_FORMAT),
                        bills: currentDate.format(DATE_TIME_FORMAT),
                        accounts: currentDate.format(DATE_TIME_FORMAT),
                        budget: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        transaction: currentDate,
                        bills: currentDate,
                        accounts: currentDate,
                        budget: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a Dashboard', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
