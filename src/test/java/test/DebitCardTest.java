package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import data.SQLHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.MainPage;
import page.PaymentFormBuyPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DebitCardTest {

    private MainPage mainPage;
    private PaymentFormBuyPage paymentFormBuyPage;

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }


    @BeforeEach
    public void setup() {
        mainPage = open("http://localhost:8080/", MainPage.class);
    }

    @AfterEach
    void clean() {
        SQLHelper.clear();
    }

    @Test
    public void shouldPurchaseWithApprovedCard() {
        paymentFormBuyPage = mainPage.payWithDebitCard();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.generateYear(1);
        var owner = DataHelper.generateOwner("EN");
        var cvc = DataHelper.generateCVCCode(3);
        paymentFormBuyPage.filledForm(cardNumber, month, year, owner, cvc);
        paymentFormBuyPage.waitSuccessfulNotification();
        var expected = DataHelper.getFirstCardStatus();
        var actual = SQLHelper.getDebitPaymentStatus();
        assertEquals(expected, actual);
    }
    @Test
    public void shouldDenyWithDeclinedCard() {
        paymentFormBuyPage = mainPage.payWithDebitCard();
        var cardNumber = DataHelper.getSecondCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.generateYear(1);
        var owner = DataHelper.generateOwner("EN");
        var cvc = DataHelper.generateCVCCode(3);
        paymentFormBuyPage.filledForm(cardNumber, month, year, owner, cvc);
        paymentFormBuyPage.waitErrorNotification();
        var expected = DataHelper.getSecondCardStatus();
        var actual = SQLHelper.getDebitPaymentStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void shouldDenyWithEmptyCardField() {
        paymentFormBuyPage = mainPage.payWithDebitCard();
        var cardNumber = DataHelper.getEmptyCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.generateYear(1);
        var owner = DataHelper.generateOwner("EN");
        var cvc = DataHelper.generateCVCCode(3);
        paymentFormBuyPage.filledForm(cardNumber, month, year, owner, cvc);
        paymentFormBuyPage.waitEmptyField();
    }

    @Test
    public void shouldDenyWithEmptyMonthField() {
        paymentFormBuyPage = mainPage.payWithDebitCard();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getEmptyMonth();
        var year = DataHelper.generateYear(1);
        var owner = DataHelper.generateOwner("EN");
        var cvc = DataHelper.generateCVCCode(3);
        paymentFormBuyPage.filledForm(cardNumber, month, year, owner, cvc);
        paymentFormBuyPage.waitEmptyField();
    }

    @Test
    public void shouldDenyWithEmptyYearField() {
        paymentFormBuyPage = mainPage.payWithDebitCard();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getEmptyYear();
        var owner = DataHelper.generateOwner("EN");
        var cvc = DataHelper.generateCVCCode(3);
        paymentFormBuyPage.filledForm(cardNumber, month, year, owner, cvc);
        paymentFormBuyPage.waitEmptyField();
    }

    @Test
    public void shouldDenyWithEmptyOwnerField() {
        paymentFormBuyPage = mainPage.payWithDebitCard();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.generateYear(1);
        var owner = DataHelper.getEmptyOwner();
        var cvc = DataHelper.generateCVCCode(3);
        paymentFormBuyPage.filledForm(cardNumber, month, year, owner, cvc);
        paymentFormBuyPage.waitEmptyField();
    }

    @Test
    public void shouldDenyEmptyCVCField() {
        paymentFormBuyPage = mainPage.payWithDebitCard();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.generateYear(1);
        var owner = DataHelper.generateOwner("EN");
        var cvc = DataHelper.getEmptyCVC();
        paymentFormBuyPage.filledForm(cardNumber, month, year, owner, cvc);
        paymentFormBuyPage.waitEmptyField();
    }

    @Test
    public void shouldDenyWithInvalidDateMonth() {
        paymentFormBuyPage = mainPage.payWithDebitCard();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateInvalidMonthDate(2);
        var year = DataHelper.generateYear(0);
        var owner = DataHelper.generateOwner("EN");
        var cvc = DataHelper.generateCVCCode(3);
        paymentFormBuyPage.filledForm(cardNumber, month, year, owner, cvc);
        paymentFormBuyPage.waitWrongCardDate();
    }

    @Test
    public void shouldBeEmptyCardFieldWithCyrillicChars() {
        paymentFormBuyPage = mainPage.payWithDebitCard();
        var cardNumber = DataHelper.getGenerateInvalidCardInfo("RU");
        paymentFormBuyPage.onlyCardField(cardNumber);
        paymentFormBuyPage.emptyCardField();
    }

    @Test
    public void shouldBeEmptyCardFieldWithLatinChars() {
        paymentFormBuyPage = mainPage.payWithDebitCard();
        var cardNumber = DataHelper.getGenerateInvalidCardInfo("EN");
        paymentFormBuyPage.onlyCardField(cardNumber);
        paymentFormBuyPage.emptyCardField();
    }

    @Test
    public void shouldBeEmptyCardFieldWithSpecialsChars() {
        paymentFormBuyPage = mainPage.payWithDebitCard();
        var cardNumber = DataHelper.getSpecialSimbolsCardInfo();
        paymentFormBuyPage.onlyCardField(cardNumber);
        paymentFormBuyPage.emptyCardField();
    }

    @Test
    public void shouldBeEmptyMonthFieldWithSpecialsChars() {
        paymentFormBuyPage = mainPage.payWithDebitCard();
        var month = DataHelper.getSpecialsSymbolMonth();
        paymentFormBuyPage.onlyMonthField(month);
        paymentFormBuyPage.emptyMonthField();
    }

    @Test
    public void shouldDineWithSingleNumberInMonthField() {
        paymentFormBuyPage = mainPage.payWithDebitCard();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getInvalidAmountNumbersMonths(1);
        var year = DataHelper.generateYear(1);
        var owner = DataHelper.generateOwner("EN");
        var cvc = DataHelper.generateCVCCode(3);
        paymentFormBuyPage.filledForm(cardNumber, month, year, owner, cvc);
        paymentFormBuyPage.waitWrongFormat();
    }

    @Test
    public void shouldEmptyYearFieldWithCyrillicChars() {
        paymentFormBuyPage = mainPage.payWithDebitCard();
        var year = DataHelper.getGenerateInvalidYearInfo("RU");
        paymentFormBuyPage.onlyYearField(year);
        paymentFormBuyPage.emptyYearField();
    }

    @Test
    public void shouldEmptyYearFieldWithLatinChars() {
        paymentFormBuyPage = mainPage.payWithDebitCard();
        var year = DataHelper.getGenerateInvalidYearInfo("EN");
        paymentFormBuyPage.onlyYearField(year);
        paymentFormBuyPage.emptyYearField();
    }

    @Test
    public void shouldEmptyYearFieldWithSpecialsChars() {
        paymentFormBuyPage = mainPage.payWithDebitCard();
        var year = DataHelper.getSpecialsSymbolsYearInfo();
        paymentFormBuyPage.onlyYearField(year);
        paymentFormBuyPage.emptyYearField();
    }

    @Test
    public void shouldDenyWithSingleNumberInYearField() {
        paymentFormBuyPage = mainPage.payWithDebitCard();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getInvalidAmountNumbersYear(1);
        var owner = DataHelper.generateOwner("EN");
        var cvc = DataHelper.generateCVCCode(3);
        paymentFormBuyPage.filledForm(cardNumber, month, year, owner, cvc);
        paymentFormBuyPage.waitWrongFormat();
    }

    @Test
    public void shouldDenyWithSuchNumberInYearField() {
        paymentFormBuyPage = mainPage.payWithDebitCard();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.generateYear(40);
        var owner = DataHelper.generateOwner("EN");
        var cvc = DataHelper.generateCVCCode(3);
        paymentFormBuyPage.filledForm(cardNumber, month, year, owner, cvc);
        paymentFormBuyPage.waitWrongCardDate();
    }

    @Test
    public void shouldDenyWithInPreviousYearField() {
        paymentFormBuyPage = mainPage.payWithDebitCard();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateInvalidYearDate(2);
        var owner = DataHelper.generateOwner("EN");
        var cvc = DataHelper.generateCVCCode(3);
        paymentFormBuyPage.filledForm(cardNumber, month, year, owner, cvc);
        paymentFormBuyPage.waitCardExpired();
    }

    @Test
    public void shouldDenyOwnerFieldWithCyrillicChars() {
        paymentFormBuyPage = mainPage.payWithDebitCard();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.generateYear(1);
        var owner = DataHelper.generateOwner("RU");
        var cvc = DataHelper.generateCVCCode(3);
        paymentFormBuyPage.filledForm(cardNumber, month, year, owner, cvc);
        paymentFormBuyPage.waitWrongFormat();
    }

    @Test
    public void shouldDenyOwnerFieldWithSpecialsChars() {
        paymentFormBuyPage = mainPage.payWithDebitCard();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.generateYear(1);
        var owner = DataHelper.getSpecialsSymbolsOwner();
        var cvc = DataHelper.generateCVCCode(3);
        paymentFormBuyPage.filledForm(cardNumber, month, year, owner, cvc);
        paymentFormBuyPage.waitWrongFormat();
    }

    @Test
    public void shouldDenyOwnerFieldWithNumbersChars() {
        paymentFormBuyPage = mainPage.payWithDebitCard();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.generateYear(1);
        var owner = DataHelper.getGenerateNumberOwner(8);
        var cvc = DataHelper.generateCVCCode(3);
        paymentFormBuyPage.filledForm(cardNumber, month, year, owner, cvc);
        paymentFormBuyPage.waitWrongFormat();
    }

    @Test
    public void shouldEmptyCVCFieldWithCyrillicChars() {
        paymentFormBuyPage = mainPage.payWithDebitCard();
        var cvc = DataHelper.getGenerateInvalidCvcCode("RU");
        paymentFormBuyPage.onlyCVCField(cvc);
        paymentFormBuyPage.emptyCVCField();
    }

    @Test
    public void shouldEmptyCVCFieldWithLatinChars() {
        paymentFormBuyPage = mainPage.payWithDebitCard();
        var cvc = DataHelper.getGenerateInvalidCvcCode("EN");
        paymentFormBuyPage.onlyCVCField(cvc);
        paymentFormBuyPage.emptyCVCField();
    }

    @Test
    public void shouldEmptyCVCFieldWithSpecialsChars() {
        paymentFormBuyPage = mainPage.payWithDebitCard();
        var cvc = DataHelper.getSpecialSymbolsCvcCode();
        paymentFormBuyPage.onlyCVCField(cvc);
        paymentFormBuyPage.emptyCVCField();
    }

    @Test
    public void shouldRemovedMinusInMonthField() {
        paymentFormBuyPage = mainPage.payWithDebitCard();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getEnterMonth("-2");
        var year = DataHelper.generateYear(1);
        var owner = DataHelper.generateOwner("EN");
        var cvc = DataHelper.generateCVCCode(3);
        paymentFormBuyPage.filledForm(cardNumber, month, year, owner, cvc);
        paymentFormBuyPage.waitWrongFormat();
    }

    @Test
    public void shouldRemovedZeroInMonthField() {
        paymentFormBuyPage = mainPage.payWithDebitCard();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getEnterMonth("00");
        var year = DataHelper.generateYear(1);
        var owner = DataHelper.generateOwner("EN");
        var cvc = DataHelper.generateCVCCode(3);
        paymentFormBuyPage.filledForm(cardNumber, month, year, owner, cvc);
        paymentFormBuyPage.waitWrongCardDate();
    }

    @Test
    public void shouldRemovedUpInMonthField() {
        paymentFormBuyPage = mainPage.payWithDebitCard();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getEnterMonth("13");
        var year = DataHelper.generateYear(1);
        var owner = DataHelper.generateOwner("EN");
        var cvc = DataHelper.generateCVCCode(3);
        paymentFormBuyPage.filledForm(cardNumber, month, year, owner, cvc);
        paymentFormBuyPage.waitWrongCardDate();
    }

    @Test
    public void shouldRemovedMinusInCvcField() {
        paymentFormBuyPage = mainPage.payWithDebitCard();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.generateYear(1);
        var owner = DataHelper.generateOwner("EN");
        var cvc = DataHelper.getEnterCVC("-1");
        paymentFormBuyPage.filledForm(cardNumber, month, year, owner, cvc);
        paymentFormBuyPage.waitWrongFormat();
    }

    @Test
    public void shouldRemovedZeroInCvcField() {
        paymentFormBuyPage = mainPage.payWithDebitCard();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.generateYear(0);
        var owner = DataHelper.generateOwner("EN");
        var cvc = DataHelper.getEnterCVC("000");
        paymentFormBuyPage.filledForm(cardNumber, month, year, owner, cvc);
        paymentFormBuyPage.waitWrongFormat();
    }

    @Test
    public void shouldRemovedUpInCVCField() {
        paymentFormBuyPage = mainPage.payWithDebitCard();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.generateYear(1);
        var owner = DataHelper.generateOwner("EN");
        var cvc = DataHelper.getEnterCVC("999");
        paymentFormBuyPage.filledForm(cardNumber, month, year, owner, cvc);
        paymentFormBuyPage.waitSuccessfulNotification();
        var expected = DataHelper.getFirstCardStatus();
        var actual = SQLHelper.getDebitPaymentStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void shouldAddPaymentIDInOrderEntry() {
        paymentFormBuyPage = mainPage.payWithDebitCard();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.generateYear(1);
        var owner = DataHelper.generateOwner("EN");
        var cvc = DataHelper.generateCVCCode(3);
        paymentFormBuyPage.filledForm(cardNumber, month, year, owner, cvc);
        var expected = SQLHelper.getDebitPaymentID();
        var actual = SQLHelper.getDebitOrderEntryId();
        assertEquals(expected, actual);
    }

}

