import { Moment } from 'moment';
import { IBudgetItem } from 'app/shared/model//budget-item.model';

export interface IBudget {
    id?: number;
    expectedTotal?: number;
    actualTotal?: number;
    startDate?: Moment;
    endDate?: Moment;
    budgetItems?: IBudgetItem[];
}

export class Budget implements IBudget {
    constructor(
        public id?: number,
        public expectedTotal?: number,
        public actualTotal?: number,
        public startDate?: Moment,
        public endDate?: Moment,
        public budgetItems?: IBudgetItem[]
    ) {}
}
