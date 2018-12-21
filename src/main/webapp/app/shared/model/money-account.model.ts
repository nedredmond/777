import { ITransaction } from 'app/shared/model//transaction.model';
import { IBillItem } from 'app/shared/model//bill-item.model';
import { IUser } from 'app/core/user/user.model';

export const enum AccountType {
    CREDIT = 'CREDIT',
    CHECKING = 'CHECKING',
    SAVINGS = 'SAVINGS',
    LOAN = 'LOAN'
}

export interface IMoneyAccount {
    id?: number;
    type?: AccountType;
    accountTotal?: number;
    signIn?: string;
    pw?: string;
    transactions?: ITransaction[];
    billItems?: IBillItem[];
    user?: IUser;
}

export class MoneyAccount implements IMoneyAccount {
    constructor(
        public id?: number,
        public type?: AccountType,
        public accountTotal?: number,
        public signIn?: string,
        public pw?: string,
        public transactions?: ITransaction[],
        public billItems?: IBillItem[],
        public user?: IUser
    ) {}
}
