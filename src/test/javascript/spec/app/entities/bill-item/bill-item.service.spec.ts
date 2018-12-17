/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { BillItemService } from 'app/entities/bill-item/bill-item.service';
import { IBillItem, BillItem } from 'app/shared/model/bill-item.model';

describe('Service Tests', () => {
    describe('BillItem Service', () => {
        let injector: TestBed;
        let service: BillItemService;
        let httpMock: HttpTestingController;
        let elemDefault: IBillItem;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(BillItemService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new BillItem(0, 'AAAAAAA', currentDate, currentDate, 0, false);
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

            it('should create a BillItem', async () => {
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
                    .create(new BillItem(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a BillItem', async () => {
                const returnedFromService = Object.assign(
                    {
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

            it('should return a list of BillItem', async () => {
                const returnedFromService = Object.assign(
                    {
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

            it('should delete a BillItem', async () => {
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
