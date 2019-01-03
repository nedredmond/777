import { IUserDetails } from 'app/shared/model//user-details.model';
import { ITransaction } from 'app/shared/model//transaction.model';

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
    userDetails?: IUserDetails;
    transactions?: ITransaction[];
}

export class MoneyAccount implements IMoneyAccount {
    constructor(
        public id?: number,
        public type?: AccountType,
        public accountTotal?: number,
        public signIn?: string,
        public pw?: string,
        public userDetails?: IUserDetails,
        public transactions?: ITransaction[]
    ) {}
}
