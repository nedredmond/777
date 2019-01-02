/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { BillsComponentsPage, BillsDeleteDialog, BillsUpdatePage } from './bills.page-object';

const expect = chai.expect;

describe('Bills e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let billsUpdatePage: BillsUpdatePage;
    let billsComponentsPage: BillsComponentsPage;
    let billsDeleteDialog: BillsDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Bills', async () => {
        await navBarPage.goToEntity('bills');
        billsComponentsPage = new BillsComponentsPage();
        expect(await billsComponentsPage.getTitle()).to.eq('App.bills.home.title');
    });

    it('should load create Bills page', async () => {
        await billsComponentsPage.clickOnCreateButton();
        billsUpdatePage = new BillsUpdatePage();
        expect(await billsUpdatePage.getPageTitle()).to.eq('App.bills.home.createOrEditLabel');
        await billsUpdatePage.cancel();
    });

    it('should create and save Bills', async () => {
        const nbButtonsBeforeCreate = await billsComponentsPage.countDeleteButtons();

        await billsComponentsPage.clickOnCreateButton();
        await promise.all([
            billsUpdatePage.setPaymentTotalInput('5'),
            billsUpdatePage.setCompanyNameInput('companyName'),
            billsUpdatePage.setDueDateInput('2000-12-31'),
            billsUpdatePage.setPaymentDateInput('2000-12-31'),
            billsUpdatePage.setPaymentAmountInput('5'),
            billsUpdatePage.moneyAccountSelectLastOption()
        ]);
        expect(await billsUpdatePage.getPaymentTotalInput()).to.eq('5');
        expect(await billsUpdatePage.getCompanyNameInput()).to.eq('companyName');
        expect(await billsUpdatePage.getDueDateInput()).to.eq('2000-12-31');
        expect(await billsUpdatePage.getPaymentDateInput()).to.eq('2000-12-31');
        expect(await billsUpdatePage.getPaymentAmountInput()).to.eq('5');
        const selectedAutoPay = billsUpdatePage.getAutoPayInput();
        if (await selectedAutoPay.isSelected()) {
            await billsUpdatePage.getAutoPayInput().click();
            expect(await billsUpdatePage.getAutoPayInput().isSelected()).to.be.false;
        } else {
            await billsUpdatePage.getAutoPayInput().click();
            expect(await billsUpdatePage.getAutoPayInput().isSelected()).to.be.true;
        }
        await billsUpdatePage.save();
        expect(await billsUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await billsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Bills', async () => {
        const nbButtonsBeforeDelete = await billsComponentsPage.countDeleteButtons();
        await billsComponentsPage.clickOnLastDeleteButton();

        billsDeleteDialog = new BillsDeleteDialog();
        expect(await billsDeleteDialog.getDialogTitle()).to.eq('App.bills.delete.question');
        await billsDeleteDialog.clickOnConfirmButton();

        expect(await billsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
