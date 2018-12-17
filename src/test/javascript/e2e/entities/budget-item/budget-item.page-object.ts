import { element, by, ElementFinder } from 'protractor';

export class BudgetItemComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-budget-item div table .btn-danger'));
    title = element.all(by.css('jhi-budget-item div h2#page-heading span')).first();

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

export class BudgetItemUpdatePage {
    pageTitle = element(by.id('jhi-budget-item-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    expectedSpendingInput = element(by.id('field_expectedSpending'));
    actualSpendingInput = element(by.id('field_actualSpending'));
    categorySelect = element(by.id('field_category'));
    budgetSelect = element(by.id('field_budget'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setExpectedSpendingInput(expectedSpending) {
        await this.expectedSpendingInput.sendKeys(expectedSpending);
    }

    async getExpectedSpendingInput() {
        return this.expectedSpendingInput.getAttribute('value');
    }

    async setActualSpendingInput(actualSpending) {
        await this.actualSpendingInput.sendKeys(actualSpending);
    }

    async getActualSpendingInput() {
        return this.actualSpendingInput.getAttribute('value');
    }

    async setCategorySelect(category) {
        await this.categorySelect.sendKeys(category);
    }

    async getCategorySelect() {
        return this.categorySelect.element(by.css('option:checked')).getText();
    }

    async categorySelectLastOption() {
        await this.categorySelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async budgetSelectLastOption() {
        await this.budgetSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async budgetSelectOption(option) {
        await this.budgetSelect.sendKeys(option);
    }

    getBudgetSelect(): ElementFinder {
        return this.budgetSelect;
    }

    async getBudgetSelectedOption() {
        return this.budgetSelect.element(by.css('option:checked')).getText();
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

export class BudgetItemDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-budgetItem-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-budgetItem'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
