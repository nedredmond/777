import { IBudget } from 'app/shared/model//budget.model';

export const enum Category {
    RENT = 'RENT',
    FOOD = 'FOOD',
    INCOME = 'INCOME',
    UTILITIES = 'UTILITIES',
    SHOPPING = 'SHOPPING',
    TRANSFER = 'TRANSFER',
    AUTOMOTIVE = 'AUTOMOTIVE',
    MISCELLANEOUS = 'MISCELLANEOUS'
}

export interface IBudgetItem {
    id?: number;
    expectedSpending?: number;
    actualSpending?: number;
    category?: Category;
    budget?: IBudget;
}

export class BudgetItem implements IBudgetItem {
    constructor(
        public id?: number,
        public expectedSpending?: number,
        public actualSpending?: number,
        public category?: Category,
        public budget?: IBudget
    ) {}
}
