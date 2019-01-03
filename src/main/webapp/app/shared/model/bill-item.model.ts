import { Moment } from 'moment';
import { IBills } from 'app/shared/model//bills.model';
import { IMoneyAccount } from 'app/shared/model//money-account.model';

export interface IBillItem {
    id?: number;
    companyName?: string;
    dueDate?: Moment;
    paymentDate?: Moment;
    paymentAmount?: number;
    autoPay?: boolean;
    bills?: IBills;
    account?: IMoneyAccount;
}

export class BillItem implements IBillItem {
    constructor(
        public id?: number,
        public companyName?: string,
        public dueDate?: Moment,
        public paymentDate?: Moment,
        public paymentAmount?: number,
        public autoPay?: boolean,
        public bills?: IBills,
        public account?: IMoneyAccount
    ) {
        this.autoPay = this.autoPay || false;
    }
}
