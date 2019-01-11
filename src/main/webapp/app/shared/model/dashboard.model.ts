import { Moment } from 'moment';

export interface IDashboard {
    id?: number;
    transaction?: Moment;
    bills?: Moment;
    accounts?: Moment;
    budget?: Moment;
}

export class Dashboard implements IDashboard {
    constructor(public id?: number, public transaction?: Moment, public bills?: Moment, public accounts?: Moment, public budget?: Moment) {}
}
