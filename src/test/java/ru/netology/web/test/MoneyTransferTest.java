package ru.netology.web.test;

import com.codeborne.selenide.Selenide;
import lombok.val;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.TransferPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    String transferValid = "1000";
    String transferInvalid = "100000";

    private DashboardPage shouldOpenPage() {
        open("http://localhost:9999");
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        return verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldTransferMoneyCard1toCard2() {
        DashboardPage dashboardPage = shouldOpenPage();
        val balanceCard1BeforeTransfer = dashboardPage.getCardBalance(DataHelper.getNumberCard1().getNumber());
        val balanceCard2BeforeTransfer = dashboardPage.getCardBalance(DataHelper.getNumberCard2().getNumber());
        val transferPage = dashboardPage.transferMoney(DataHelper.getNumberCard2().getNumber());
        transferPage.transferMoney(transferValid, DataHelper.getNumberCard1().getNumber());
        val balanceCard1AfterTransfer = dashboardPage.getCardBalance(DataHelper.getNumberCard1().getNumber());
        val balanceCard2AfterTransfer = dashboardPage.getCardBalance(DataHelper.getNumberCard2().getNumber());
        int transferSum = Integer.parseInt(transferValid);
        assertEquals(balanceCard1BeforeTransfer - transferSum, balanceCard1AfterTransfer);
        assertEquals(balanceCard2BeforeTransfer + transferSum, balanceCard2AfterTransfer);
    }

    @Test
    void shouldTransferMoneyCard2toCard1() {
        DashboardPage dashboardPage = shouldOpenPage();
        val balanceCard1BeforeTransfer = dashboardPage.getCardBalance(DataHelper.getNumberCard1().getNumber());
        val balanceCard2BeforeTransfer = dashboardPage.getCardBalance(DataHelper.getNumberCard2().getNumber());
        val transferPage = dashboardPage.transferMoney(DataHelper.getNumberCard1().getNumber());
        transferPage.transferMoney(transferValid, DataHelper.getNumberCard2().getNumber());
        val balanceCard1AfterTransfer = dashboardPage.getCardBalance(DataHelper.getNumberCard1().getNumber());
        val balanceCard2AfterTransfer = dashboardPage.getCardBalance(DataHelper.getNumberCard2().getNumber());
        int transferSum = Integer.parseInt(transferValid);
        assertEquals(balanceCard1BeforeTransfer + transferSum, balanceCard1AfterTransfer);
        assertEquals(balanceCard2BeforeTransfer - transferSum, balanceCard2AfterTransfer);
    }

    @Test
    void shouldTransferMoneyCard1toCard2Invalid() {
        DashboardPage dashboardPage = shouldOpenPage();
        val transferPage = dashboardPage.transferMoney(DataHelper.getNumberCard2().getNumber());
        transferPage.transferMoney(transferInvalid, DataHelper.getNumberCard1().getNumber());
        transferPage.transferMoneyError();
    }
}