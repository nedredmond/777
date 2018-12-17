import { IMoneyAccount } from 'app/shared/model//money-account.model';

export interface IUserDetails {
    id?: number;
    netWorth?: number;
    totalCash?: number;
    accounts?: IMoneyAccount[];
}

export class UserDetails implements IUserDetails {
    constructor(public id?: number, public netWorth?: number, public totalCash?: number, public accounts?: IMoneyAccount[]) {}
}
