/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { TransactionService } from 'app/entities/transaction/transaction.service';
import { ITransaction, Transaction, TransactionType, Category } from 'app/shared/model/transaction.model';

describe('Service Tests', () => {
    describe('Transaction Service', () => {
        let injector: TestBed;
        let service: TransactionService;
        let httpMock: HttpTestingController;
        let elemDefault: ITransaction;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(TransactionService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Transaction(0, 0, TransactionType.CREDIT, currentDate, 'AAAAAAA', 'AAAAAAA', Category.RENT);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        dateTime: currentDate.format(DATE_FORMAT)
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

            it('should create a Transaction', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        dateTime: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dateTime: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Transaction(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Transaction', async () => {
                const returnedFromService = Object.assign(
                    {
                        amount: 1,
                        transactionType: 'BBBBBB',
                        dateTime: currentDate.format(DATE_FORMAT),
                        description: 'BBBBBB',
                        memo: 'BBBBBB',
                        category: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        dateTime: currentDate
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

            it('should return a list of Transaction', async () => {
                const returnedFromService = Object.assign(
                    {
                        amount: 1,
                        transactionType: 'BBBBBB',
                        dateTime: currentDate.format(DATE_FORMAT),
                        description: 'BBBBBB',
                        memo: 'BBBBBB',
                        category: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dateTime: currentDate
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

            it('should delete a Transaction', async () => {
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
