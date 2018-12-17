import { element, by, ElementFinder } from 'protractor';

export class BillsComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-bills div table .btn-danger'));
    title = element.all(by.css('jhi-bills div h2#page-heading span')).first();

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

export class BillsUpdatePage {
    pageTitle = element(by.id('jhi-bills-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    paymentTotalInput = element(by.id('field_paymentTotal'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setPaymentTotalInput(paymentTotal) {
        await this.paymentTotalInput.sendKeys(paymentTotal);
    }

    async getPaymentTotalInput() {
        return this.paymentTotalInput.getAttribute('value');
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

export class BillsDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-bills-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-bills'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
