import { IBillItem } from 'app/shared/model//bill-item.model';

export interface IBills {
    id?: number;
    paymentTotal?: number;
    billItems?: IBillItem[];
}

export class Bills implements IBills {
    constructor(public id?: number, public paymentTotal?: number, public billItems?: IBillItem[]) {}
}
