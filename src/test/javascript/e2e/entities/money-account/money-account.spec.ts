/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { MoneyAccountComponentsPage, MoneyAccountDeleteDialog, MoneyAccountUpdatePage } from './money-account.page-object';

const expect = chai.expect;

describe('MoneyAccount e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let moneyAccountUpdatePage: MoneyAccountUpdatePage;
    let moneyAccountComponentsPage: MoneyAccountComponentsPage;
    let moneyAccountDeleteDialog: MoneyAccountDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load MoneyAccounts', async () => {
        await navBarPage.goToEntity('money-account');
        moneyAccountComponentsPage = new MoneyAccountComponentsPage();
        expect(await moneyAccountComponentsPage.getTitle()).to.eq('App.moneyAccount.home.title');
    });

    it('should load create MoneyAccount page', async () => {
        await moneyAccountComponentsPage.clickOnCreateButton();
        moneyAccountUpdatePage = new MoneyAccountUpdatePage();
        expect(await moneyAccountUpdatePage.getPageTitle()).to.eq('App.moneyAccount.home.createOrEditLabel');
        await moneyAccountUpdatePage.cancel();
    });

    it('should create and save MoneyAccounts', async () => {
        const nbButtonsBeforeCreate = await moneyAccountComponentsPage.countDeleteButtons();

        await moneyAccountComponentsPage.clickOnCreateButton();
        await promise.all([
            moneyAccountUpdatePage.typeSelectLastOption(),
            moneyAccountUpdatePage.setAccountTotalInput('5'),
            moneyAccountUpdatePage.setSignInInput('signIn'),
            moneyAccountUpdatePage.setPwInput('pw'),
            moneyAccountUpdatePage.userSelectLastOption()
        ]);
        expect(await moneyAccountUpdatePage.getAccountTotalInput()).to.eq('5');
        expect(await moneyAccountUpdatePage.getSignInInput()).to.eq('signIn');
        expect(await moneyAccountUpdatePage.getPwInput()).to.eq('pw');
        await moneyAccountUpdatePage.save();
        expect(await moneyAccountUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await moneyAccountComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last MoneyAccount', async () => {
        const nbButtonsBeforeDelete = await moneyAccountComponentsPage.countDeleteButtons();
        await moneyAccountComponentsPage.clickOnLastDeleteButton();

        moneyAccountDeleteDialog = new MoneyAccountDeleteDialog();
        expect(await moneyAccountDeleteDialog.getDialogTitle()).to.eq('App.moneyAccount.delete.question');
        await moneyAccountDeleteDialog.clickOnConfirmButton();

        expect(await moneyAccountComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
