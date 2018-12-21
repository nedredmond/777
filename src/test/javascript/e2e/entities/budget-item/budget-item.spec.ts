/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { BudgetItemComponentsPage, BudgetItemDeleteDialog, BudgetItemUpdatePage } from './budget-item.page-object';

const expect = chai.expect;

describe('BudgetItem e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let budgetItemUpdatePage: BudgetItemUpdatePage;
    let budgetItemComponentsPage: BudgetItemComponentsPage;
    let budgetItemDeleteDialog: BudgetItemDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load BudgetItems', async () => {
        await navBarPage.goToEntity('budget-item');
        budgetItemComponentsPage = new BudgetItemComponentsPage();
        expect(await budgetItemComponentsPage.getTitle()).to.eq('App.budgetItem.home.title');
    });

    it('should load create BudgetItem page', async () => {
        await budgetItemComponentsPage.clickOnCreateButton();
        budgetItemUpdatePage = new BudgetItemUpdatePage();
        expect(await budgetItemUpdatePage.getPageTitle()).to.eq('App.budgetItem.home.createOrEditLabel');
        await budgetItemUpdatePage.cancel();
    });

    it('should create and save BudgetItems', async () => {
        const nbButtonsBeforeCreate = await budgetItemComponentsPage.countDeleteButtons();

        await budgetItemComponentsPage.clickOnCreateButton();
        await promise.all([
            budgetItemUpdatePage.setExpectedSpendingInput('5'),
            budgetItemUpdatePage.setActualSpendingInput('5'),
            budgetItemUpdatePage.categorySelectLastOption(),
            budgetItemUpdatePage.budgetSelectLastOption()
        ]);
        expect(await budgetItemUpdatePage.getExpectedSpendingInput()).to.eq('5');
        expect(await budgetItemUpdatePage.getActualSpendingInput()).to.eq('5');
        await budgetItemUpdatePage.save();
        expect(await budgetItemUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await budgetItemComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last BudgetItem', async () => {
        const nbButtonsBeforeDelete = await budgetItemComponentsPage.countDeleteButtons();
        await budgetItemComponentsPage.clickOnLastDeleteButton();

        budgetItemDeleteDialog = new BudgetItemDeleteDialog();
        expect(await budgetItemDeleteDialog.getDialogTitle()).to.eq('App.budgetItem.delete.question');
        await budgetItemDeleteDialog.clickOnConfirmButton();

        expect(await budgetItemComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
