/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TransactionComponentsPage, TransactionDeleteDialog, TransactionUpdatePage } from './transaction.page-object';

const expect = chai.expect;

describe('Transaction e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let transactionUpdatePage: TransactionUpdatePage;
    let transactionComponentsPage: TransactionComponentsPage;
    let transactionDeleteDialog: TransactionDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Transactions', async () => {
        await navBarPage.goToEntity('transaction');
        transactionComponentsPage = new TransactionComponentsPage();
        expect(await transactionComponentsPage.getTitle()).to.eq('App.transaction.home.title');
    });

    it('should load create Transaction page', async () => {
        await transactionComponentsPage.clickOnCreateButton();
        transactionUpdatePage = new TransactionUpdatePage();
        expect(await transactionUpdatePage.getPageTitle()).to.eq('App.transaction.home.createOrEditLabel');
        await transactionUpdatePage.cancel();
    });

    it('should create and save Transactions', async () => {
        const nbButtonsBeforeCreate = await transactionComponentsPage.countDeleteButtons();

        await transactionComponentsPage.clickOnCreateButton();
        await promise.all([
            transactionUpdatePage.setAmountInput('5'),
            transactionUpdatePage.transactionTypeSelectLastOption(),
            transactionUpdatePage.setDateTimeInput('2000-12-31'),
            transactionUpdatePage.setDescriptionInput('description'),
            transactionUpdatePage.setMemoInput('memo'),
            transactionUpdatePage.categorySelectLastOption(),
            transactionUpdatePage.moneyAccountSelectLastOption()
        ]);
        expect(await transactionUpdatePage.getAmountInput()).to.eq('5');
        expect(await transactionUpdatePage.getDateTimeInput()).to.eq('2000-12-31');
        expect(await transactionUpdatePage.getDescriptionInput()).to.eq('description');
        expect(await transactionUpdatePage.getMemoInput()).to.eq('memo');
        await transactionUpdatePage.save();
        expect(await transactionUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await transactionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Transaction', async () => {
        const nbButtonsBeforeDelete = await transactionComponentsPage.countDeleteButtons();
        await transactionComponentsPage.clickOnLastDeleteButton();

        transactionDeleteDialog = new TransactionDeleteDialog();
        expect(await transactionDeleteDialog.getDialogTitle()).to.eq('App.transaction.delete.question');
        await transactionDeleteDialog.clickOnConfirmButton();

        expect(await transactionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
