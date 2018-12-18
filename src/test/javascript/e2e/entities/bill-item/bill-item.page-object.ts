import { element, by, ElementFinder } from 'protractor';

export class BillItemComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-bill-item div table .btn-danger'));
    title = element.all(by.css('jhi-bill-item div h2#page-heading span')).first();

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

export class BillItemUpdatePage {
    pageTitle = element(by.id('jhi-bill-item-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    companyNameInput = element(by.id('field_companyName'));
    dueDateInput = element(by.id('field_dueDate'));
    paymentDateInput = element(by.id('field_paymentDate'));
    paymentAmountInput = element(by.id('field_paymentAmount'));
    autoPayInput = element(by.id('field_autoPay'));
    moneyAccountSelect = element(by.id('field_moneyAccount'));
    billsSelect = element(by.id('field_bills'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setCompanyNameInput(companyName) {
        await this.companyNameInput.sendKeys(companyName);
    }

    async getCompanyNameInput() {
        return this.companyNameInput.getAttribute('value');
    }

    async setDueDateInput(dueDate) {
        await this.dueDateInput.sendKeys(dueDate);
    }

    async getDueDateInput() {
        return this.dueDateInput.getAttribute('value');
    }

    async setPaymentDateInput(paymentDate) {
        await this.paymentDateInput.sendKeys(paymentDate);
    }

    async getPaymentDateInput() {
        return this.paymentDateInput.getAttribute('value');
    }

    async setPaymentAmountInput(paymentAmount) {
        await this.paymentAmountInput.sendKeys(paymentAmount);
    }

    async getPaymentAmountInput() {
        return this.paymentAmountInput.getAttribute('value');
    }

    getAutoPayInput() {
        return this.autoPayInput;
    }

    async moneyAccountSelectLastOption() {
        await this.moneyAccountSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async moneyAccountSelectOption(option) {
        await this.moneyAccountSelect.sendKeys(option);
    }

    getMoneyAccountSelect(): ElementFinder {
        return this.moneyAccountSelect;
    }

    async getMoneyAccountSelectedOption() {
        return this.moneyAccountSelect.element(by.css('option:checked')).getText();
    }

    async billsSelectLastOption() {
        await this.billsSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async billsSelectOption(option) {
        await this.billsSelect.sendKeys(option);
    }

    getBillsSelect(): ElementFinder {
        return this.billsSelect;
    }

    async getBillsSelectedOption() {
        return this.billsSelect.element(by.css('option:checked')).getText();
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

export class BillItemDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-billItem-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-billItem'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
