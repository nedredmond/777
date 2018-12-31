/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { BillItemComponentsPage, BillItemDeleteDialog, BillItemUpdatePage } from './bill-item.page-object';

const expect = chai.expect;

describe('BillItem e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let billItemUpdatePage: BillItemUpdatePage;
    let billItemComponentsPage: BillItemComponentsPage;
    let billItemDeleteDialog: BillItemDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load BillItems', async () => {
        await navBarPage.goToEntity('bill-item');
        billItemComponentsPage = new BillItemComponentsPage();
        expect(await billItemComponentsPage.getTitle()).to.eq('App.billItem.home.title');
    });

    it('should load create BillItem page', async () => {
        await billItemComponentsPage.clickOnCreateButton();
        billItemUpdatePage = new BillItemUpdatePage();
        expect(await billItemUpdatePage.getPageTitle()).to.eq('App.billItem.home.createOrEditLabel');
        await billItemUpdatePage.cancel();
    });

    it('should create and save BillItems', async () => {
        const nbButtonsBeforeCreate = await billItemComponentsPage.countDeleteButtons();

        await billItemComponentsPage.clickOnCreateButton();
        await promise.all([
            billItemUpdatePage.setCompanyNameInput('companyName'),
            billItemUpdatePage.setDueDateInput('2000-12-31'),
            billItemUpdatePage.setPaidDateInput('2000-12-31'),
            billItemUpdatePage.setPaymentAmountInput('5'),
            billItemUpdatePage.moneyAccountSelectLastOption(),
            billItemUpdatePage.billsSelectLastOption()
        ]);
        expect(await billItemUpdatePage.getCompanyNameInput()).to.eq('companyName');
        expect(await billItemUpdatePage.getDueDateInput()).to.eq('2000-12-31');
        expect(await billItemUpdatePage.getPaidDateInput()).to.eq('2000-12-31');
        expect(await billItemUpdatePage.getPaymentAmountInput()).to.eq('5');
        await billItemUpdatePage.save();
        expect(await billItemUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await billItemComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last BillItem', async () => {
        const nbButtonsBeforeDelete = await billItemComponentsPage.countDeleteButtons();
        await billItemComponentsPage.clickOnLastDeleteButton();

        billItemDeleteDialog = new BillItemDeleteDialog();
        expect(await billItemDeleteDialog.getDialogTitle()).to.eq('App.billItem.delete.question');
        await billItemDeleteDialog.clickOnConfirmButton();

        expect(await billItemComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
