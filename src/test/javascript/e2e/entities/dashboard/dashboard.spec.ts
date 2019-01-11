/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DashboardComponentsPage, DashboardDeleteDialog, DashboardUpdatePage } from './dashboard.page-object';

const expect = chai.expect;

describe('Dashboard e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let dashboardUpdatePage: DashboardUpdatePage;
    let dashboardComponentsPage: DashboardComponentsPage;
    let dashboardDeleteDialog: DashboardDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Dashboards', async () => {
        await navBarPage.goToEntity('dashboard');
        dashboardComponentsPage = new DashboardComponentsPage();
        expect(await dashboardComponentsPage.getTitle()).to.eq('App.dashboard.home.title');
    });

    it('should load create Dashboard page', async () => {
        await dashboardComponentsPage.clickOnCreateButton();
        dashboardUpdatePage = new DashboardUpdatePage();
        expect(await dashboardUpdatePage.getPageTitle()).to.eq('App.dashboard.home.createOrEditLabel');
        await dashboardUpdatePage.cancel();
    });

    it('should create and save Dashboards', async () => {
        const nbButtonsBeforeCreate = await dashboardComponentsPage.countDeleteButtons();

        await dashboardComponentsPage.clickOnCreateButton();
        await promise.all([
            dashboardUpdatePage.setTransactionInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            dashboardUpdatePage.setBillsInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            dashboardUpdatePage.setAccountsInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            dashboardUpdatePage.setBudgetInput('01/01/2001' + protractor.Key.TAB + '02:30AM')
        ]);
        expect(await dashboardUpdatePage.getTransactionInput()).to.contain('2001-01-01T02:30');
        expect(await dashboardUpdatePage.getBillsInput()).to.contain('2001-01-01T02:30');
        expect(await dashboardUpdatePage.getAccountsInput()).to.contain('2001-01-01T02:30');
        expect(await dashboardUpdatePage.getBudgetInput()).to.contain('2001-01-01T02:30');
        await dashboardUpdatePage.save();
        expect(await dashboardUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await dashboardComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Dashboard', async () => {
        const nbButtonsBeforeDelete = await dashboardComponentsPage.countDeleteButtons();
        await dashboardComponentsPage.clickOnLastDeleteButton();

        dashboardDeleteDialog = new DashboardDeleteDialog();
        expect(await dashboardDeleteDialog.getDialogTitle()).to.eq('App.dashboard.delete.question');
        await dashboardDeleteDialog.clickOnConfirmButton();

        expect(await dashboardComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
