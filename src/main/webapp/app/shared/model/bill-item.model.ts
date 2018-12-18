import { Moment } from 'moment';
import { IMoneyAccount } from 'app/shared/model//money-account.model';
import { IBills } from 'app/shared/model//bills.model';

export interface IBillItem {
    id?: number;
    companyName?: string;
    dueDate?: Moment;
    paymentDate?: Moment;
    paymentAmount?: number;
    autoPay?: boolean;
    moneyAccount?: IMoneyAccount;
    bills?: IBills;
}

export class BillItem implements IBillItem {
    constructor(
        public id?: number,
        public companyName?: string,
        public dueDate?: Moment,
        public paymentDate?: Moment,
        public paymentAmount?: number,
        public autoPay?: boolean,
        public moneyAccount?: IMoneyAccount,
        public bills?: IBills
    ) {
        this.autoPay = this.autoPay || false;
    }
}
