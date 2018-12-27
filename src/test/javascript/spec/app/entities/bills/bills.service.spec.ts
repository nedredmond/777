/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { BillsService } from 'app/entities/bills/bills.service';
import { IBills, Bills } from 'app/shared/model/bills.model';

describe('Service Tests', () => {
    describe('Bills Service', () => {
        let injector: TestBed;
        let service: BillsService;
        let httpMock: HttpTestingController;
        let elemDefault: IBills;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(BillsService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Bills(0, 0, 'AAAAAAA', currentDate, currentDate, 0, false);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        dueDate: currentDate.format(DATE_FORMAT),
                        paymentDate: currentDate.format(DATE_FORMAT)
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

            it('should create a Bills', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        dueDate: currentDate.format(DATE_FORMAT),
                        paymentDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dueDate: currentDate,
                        paymentDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Bills(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Bills', async () => {
                const returnedFromService = Object.assign(
                    {
                        paymentTotal: 1,
                        companyName: 'BBBBBB',
                        dueDate: currentDate.format(DATE_FORMAT),
                        paymentDate: currentDate.format(DATE_FORMAT),
                        paymentAmount: 1,
                        autoPay: true
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        dueDate: currentDate,
                        paymentDate: currentDate
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

            it('should return a list of Bills', async () => {
                const returnedFromService = Object.assign(
                    {
                        paymentTotal: 1,
                        companyName: 'BBBBBB',
                        dueDate: currentDate.format(DATE_FORMAT),
                        paymentDate: currentDate.format(DATE_FORMAT),
                        paymentAmount: 1,
                        autoPay: true
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dueDate: currentDate,
                        paymentDate: currentDate
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

            it('should delete a Bills', async () => {
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
