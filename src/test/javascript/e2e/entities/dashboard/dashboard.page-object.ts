import { element, by, ElementFinder } from 'protractor';

export class DashboardComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-dashboard div table .btn-danger'));
    title = element.all(by.css('jhi-dashboard div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class DashboardUpdatePage {
    pageTitle = element(by.id('jhi-dashboard-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    transactionInput = element(by.id('field_transaction'));
    billsInput = element(by.id('field_bills'));
    accountsInput = element(by.id('field_accounts'));
    budgetInput = element(by.id('field_budget'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setTransactionInput(transaction) {
        await this.transactionInput.sendKeys(transaction);
    }

    async getTransactionInput() {
        return this.transactionInput.getAttribute('value');
    }

    async setBillsInput(bills) {
        await this.billsInput.sendKeys(bills);
    }

    async getBillsInput() {
        return this.billsInput.getAttribute('value');
    }

    async setAccountsInput(accounts) {
        await this.accountsInput.sendKeys(accounts);
    }

    async getAccountsInput() {
        return this.accountsInput.getAttribute('value');
    }

    async setBudgetInput(budget) {
        await this.budgetInput.sendKeys(budget);
    }

    async getBudgetInput() {
        return this.budgetInput.getAttribute('value');
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class DashboardDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-dashboard-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-dashboard'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
