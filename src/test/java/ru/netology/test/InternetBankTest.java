package ru.netology.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.TransferPage;
import static com.codeborne.selenide.Selenide.open;


public class InternetBankTest {

    @BeforeEach
    public void shouldLogin() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }

    @Test
    public void shouldTransferFromFirstCardToSecond() {
        var dashboardPage = new DashboardPage();
        var initialBalanceFirst = dashboardPage.getFirstCardBalance();
        var initialBalanceSecond = dashboardPage.getSecondCardBalance();
        int destinationCardIndex = 1;
        int amount = 42;
        dashboardPage.transferTo(destinationCardIndex)
                .Transfer(amount, DataHelper.getFirstCardNumber().getNumber());
        var currentBalanceFirst = dashboardPage.getFirstCardBalance();
        var currentBalanceSecond = dashboardPage.getSecondCardBalance();
        Assertions.assertEquals(initialBalanceFirst - amount, currentBalanceFirst);
        Assertions.assertEquals(initialBalanceSecond + amount, currentBalanceSecond);
    }

    @Test
    public void shouldTransferFromSecondCardToFirst() {
        var dashboardPage = new DashboardPage();
        var initialBalanceFirst = dashboardPage.getFirstCardBalance();
        var initialBalanceSecond = dashboardPage.getSecondCardBalance();
        int destinationCardIndex = 0;
        int amount = 42;
        dashboardPage.transferTo(destinationCardIndex)
                .Transfer(amount, DataHelper.getSecondCardNumber().getNumber());
        var currentBalanceFirst = dashboardPage.getFirstCardBalance();
        var currentBalanceSecond = dashboardPage.getSecondCardBalance();
        Assertions.assertEquals(initialBalanceFirst + amount, currentBalanceFirst);
        Assertions.assertEquals(initialBalanceSecond - amount, currentBalanceSecond);
    }

    @Test
    public void shouldReturnErrorWhenTransferFromNonexistentCard() {
        var dashboardPage = new DashboardPage();
        var initialBalanceFirst = dashboardPage.getFirstCardBalance();
        var initialBalanceSecond = dashboardPage.getSecondCardBalance();
        int destinationCardIndex = 0;
        int amount = 42;
        var transferPage = new TransferPage();

        dashboardPage.transferTo(destinationCardIndex)
                .Transfer(amount, "5589000000000003");
        transferPage.getNotification().shouldBe(Condition.visible);
        transferPage.getNotification().shouldHave(Condition.exactText("????????????\n" +
                "????????????! ?????????????????? ????????????"));
    }

    @Test
    public void shouldReturnErrorWhenTransferAboveLimit() {
        var dashboardPage = new DashboardPage();
        var initialBalanceFirst = dashboardPage.getFirstCardBalance();
        var initialBalanceSecond = dashboardPage.getSecondCardBalance();
        int destinationCardIndex = 0;
        int amount = 100000;
        var transferPage = new TransferPage();
        dashboardPage.transferTo(destinationCardIndex)
                .Transfer(amount, DataHelper.getSecondCardNumber().getNumber());
        transferPage.getNotification().shouldBe(Condition.visible);
        transferPage.getNotification().shouldHave(Condition.exactText("????????????\n" +
                "????????????! ???? ?????????? ?????????????????????? ???????????????????????? ??????????????"));
    }
}
