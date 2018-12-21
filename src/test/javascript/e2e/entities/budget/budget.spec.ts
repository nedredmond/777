/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { BudgetComponentsPage, BudgetDeleteDialog, BudgetUpdatePage } from './budget.page-object';

const expect = chai.expect;

describe('Budget e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let budgetUpdatePage: BudgetUpdatePage;
    let budgetComponentsPage: BudgetComponentsPage;
    let budgetDeleteDialog: BudgetDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Budgets', async () => {
        await navBarPage.goToEntity('budget');
        budgetComponentsPage = new BudgetComponentsPage();
        expect(await budgetComponentsPage.getTitle()).to.eq('App.budget.home.title');
    });

    it('should load create Budget page', async () => {
        await budgetComponentsPage.clickOnCreateButton();
        budgetUpdatePage = new BudgetUpdatePage();
        expect(await budgetUpdatePage.getPageTitle()).to.eq('App.budget.home.createOrEditLabel');
        await budgetUpdatePage.cancel();
    });

    it('should create and save Budgets', async () => {
        const nbButtonsBeforeCreate = await budgetComponentsPage.countDeleteButtons();

        await budgetComponentsPage.clickOnCreateButton();
        await promise.all([
            budgetUpdatePage.setExpectedTotalInput('5'),
            budgetUpdatePage.setActualTotalInput('5'),
            budgetUpdatePage.setStartDateInput('2000-12-31'),
            budgetUpdatePage.setEndDateInput('2000-12-31')
        ]);
        expect(await budgetUpdatePage.getExpectedTotalInput()).to.eq('5');
        expect(await budgetUpdatePage.getActualTotalInput()).to.eq('5');
        expect(await budgetUpdatePage.getStartDateInput()).to.eq('2000-12-31');
        expect(await budgetUpdatePage.getEndDateInput()).to.eq('2000-12-31');
        await budgetUpdatePage.save();
        expect(await budgetUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await budgetComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Budget', async () => {
        const nbButtonsBeforeDelete = await budgetComponentsPage.countDeleteButtons();
        await budgetComponentsPage.clickOnLastDeleteButton();

        budgetDeleteDialog = new BudgetDeleteDialog();
        expect(await budgetDeleteDialog.getDialogTitle()).to.eq('App.budget.delete.question');
        await budgetDeleteDialog.clickOnConfirmButton();

        expect(await budgetComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
