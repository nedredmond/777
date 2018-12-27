import { Moment } from 'moment';
import { IBillItem } from 'app/shared/model//bill-item.model';
import { IMoneyAccount } from 'app/shared/model//money-account.model';

export interface IBills {
    id?: number;
    paymentTotal?: number;
    companyName?: string;
    dueDate?: Moment;
    paymentDate?: Moment;
    paymentAmount?: number;
    autoPay?: boolean;
    billItems?: IBillItem[];
    moneyAccount?: IMoneyAccount;
}

export class Bills implements IBills {
    constructor(
        public id?: number,
        public paymentTotal?: number,
        public companyName?: string,
        public dueDate?: Moment,
        public paymentDate?: Moment,
        public paymentAmount?: number,
        public autoPay?: boolean,
        public billItems?: IBillItem[],
        public moneyAccount?: IMoneyAccount
    ) {
        this.autoPay = this.autoPay || false;
    }
}
