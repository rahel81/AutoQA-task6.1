package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    private static SelenideElement fieldSumma = $("[data-test-id=amount] input");
    private static SelenideElement fieldCardFrom = $("[data-test-id='from'] .input__control");
    private static SelenideElement buttonReplenish = $("[data-test-id='action-transfer'] .button__text");
    private SelenideElement buttonCancel = $("[data-test-id='action-cancel'] .button__text");
    private SelenideElement messageError = $("[data-test-id= 'error-notification']");

    public static DashboardPage transferMoney(String transferSumma, String transferFrom) {
        fieldSumma.setValue(transferSumma);
        fieldCardFrom.setValue(transferFrom);
        buttonReplenish.click();
        return new DashboardPage();
    }

    public void transferMoneyError() {
        messageError.shouldBe(Condition.visible);
    }
}
