package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {

    private static String text;
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private static SelenideElement firstCard = $("[data-test-id=92df3f1c-a033-48e6-8390-206f6b1f56c0]");
    private static SelenideElement secondCard = $("[data-test-id=0f3f5c2a-249e-4c3d-8287-09f7a039391d]");
    private SelenideElement buttonReload = $("[data-test-id=action-reload]");

    private static ElementsCollection cards = $$(".list__item");
    private static final String balanceStart = "баланс: ";
    private static final String balanceFinish = " р.";

    public DashboardPage() {
        heading.shouldBe(Condition.visible);
    }

    public TransferPage transferMoney(String number) {
        $$(".list__item").find(text(number.substring(15, 19))).$("button").click();
        return new TransferPage();
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public int getCardBalance(String number) {
        val text = cards.find(text(number.substring(15, 19))).getText();
        return extractBalance(text);
    }
}
