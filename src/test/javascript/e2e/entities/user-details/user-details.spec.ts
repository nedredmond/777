/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { UserDetailsComponentsPage, UserDetailsDeleteDialog, UserDetailsUpdatePage } from './user-details.page-object';

const expect = chai.expect;

describe('UserDetails e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let userDetailsUpdatePage: UserDetailsUpdatePage;
    let userDetailsComponentsPage: UserDetailsComponentsPage;
    let userDetailsDeleteDialog: UserDetailsDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load UserDetails', async () => {
        await navBarPage.goToEntity('user-details');
        userDetailsComponentsPage = new UserDetailsComponentsPage();
        expect(await userDetailsComponentsPage.getTitle()).to.eq('App.userDetails.home.title');
    });

    it('should load create UserDetails page', async () => {
        await userDetailsComponentsPage.clickOnCreateButton();
        userDetailsUpdatePage = new UserDetailsUpdatePage();
        expect(await userDetailsUpdatePage.getPageTitle()).to.eq('App.userDetails.home.createOrEditLabel');
        await userDetailsUpdatePage.cancel();
    });

    it('should create and save UserDetails', async () => {
        const nbButtonsBeforeCreate = await userDetailsComponentsPage.countDeleteButtons();

        await userDetailsComponentsPage.clickOnCreateButton();
        await promise.all([userDetailsUpdatePage.setNetWorthInput('5'), userDetailsUpdatePage.setTotalCashInput('5')]);
        expect(await userDetailsUpdatePage.getNetWorthInput()).to.eq('5');
        expect(await userDetailsUpdatePage.getTotalCashInput()).to.eq('5');
        await userDetailsUpdatePage.save();
        expect(await userDetailsUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await userDetailsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last UserDetails', async () => {
        const nbButtonsBeforeDelete = await userDetailsComponentsPage.countDeleteButtons();
        await userDetailsComponentsPage.clickOnLastDeleteButton();

        userDetailsDeleteDialog = new UserDetailsDeleteDialog();
        expect(await userDetailsDeleteDialog.getDialogTitle()).to.eq('App.userDetails.delete.question');
        await userDetailsDeleteDialog.clickOnConfirmButton();

        expect(await userDetailsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
