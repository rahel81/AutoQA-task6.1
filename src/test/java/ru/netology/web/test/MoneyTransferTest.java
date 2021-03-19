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
        val balanceCard1BeforeTransfer = DashboardPage.getCardBalance(DataHelper.getNumberCard1().getNumber());  //получение баланса 1 карты до перевода
        val balanceCard2BeforeTransfer = DashboardPage.getCardBalance(DataHelper.getNumberCard2().getNumber());  //получение баланса 2 карты до перевода
        val transferPage = DashboardPage.transferMoney(DataHelper.getNumberCard2().getNumber()); //выбор карты для пополнения
        TransferPage.transferMoney(transferValid, DataHelper.getNumberCard1().getNumber());  //ввод данных
        val balanceCard1AfterTransfer = DashboardPage.getCardBalance(DataHelper.getNumberCard1().getNumber());  //получение баланса
        val balanceCard2AfterTransfer = DashboardPage.getCardBalance(DataHelper.getNumberCard2().getNumber());  //получение баланса
        int transferSum = Integer.parseInt(transferValid);
        assertEquals(balanceCard1BeforeTransfer - transferSum, balanceCard1AfterTransfer);
        assertEquals(balanceCard2BeforeTransfer + transferSum, balanceCard2AfterTransfer);
    }

    @Test
    void shouldTransferMoneyCard2toCard1() {
        DashboardPage dashboardPage = shouldOpenPage();
        val balanceCard1BeforeTransfer = DashboardPage.getCardBalance(DataHelper.getNumberCard1().getNumber());  //получение баланса 1 карты до перевода
        val balanceCard2BeforeTransfer = DashboardPage.getCardBalance(DataHelper.getNumberCard2().getNumber());  //получение баланса 2 карты до перевода
        val transferPage = DashboardPage.transferMoney(DataHelper.getNumberCard1().getNumber()); //выбор карты для пополнения
        TransferPage.transferMoney(transferValid, DataHelper.getNumberCard2().getNumber());  //ввод данных
        val balanceCard1AfterTransfer = DashboardPage.getCardBalance(DataHelper.getNumberCard1().getNumber());  //получение баланса
        val balanceCard2AfterTransfer = DashboardPage.getCardBalance(DataHelper.getNumberCard2().getNumber());  //получение баланса
        int transferSum = Integer.parseInt(transferValid);
        assertEquals(balanceCard1BeforeTransfer + transferSum, balanceCard1AfterTransfer);
        assertEquals(balanceCard2BeforeTransfer - transferSum, balanceCard2AfterTransfer);
    }

    @Test
    void shouldTransferMoneyCard1toCard2Invalid() {
        DashboardPage dashboardPage = shouldOpenPage();
        val balanceCard1BeforeTransfer = DashboardPage.getCardBalance(DataHelper.getNumberCard1().getNumber());  //получение баланса 1 карты до перевода
        val balanceCard2BeforeTransfer = DashboardPage.getCardBalance(DataHelper.getNumberCard2().getNumber());  //получение баланса 2 карты до
        val transferPage = DashboardPage.transferMoney(DataHelper.getNumberCard2().getNumber()); //выбор карты для пополнения
        TransferPage.transferMoney(transferInvalid, DataHelper.getNumberCard1().getNumber());
        val balanceCard1AfterTransfer = DashboardPage.getCardBalance(DataHelper.getNumberCard1().getNumber());  //получение баланса
        val balanceCard2AfterTransfer = DashboardPage.getCardBalance(DataHelper.getNumberCard2().getNumber()); //получение баланса
        transferPage.transferMoneyError();
    }
}