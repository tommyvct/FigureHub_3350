package comp3350.pbbs.tests.business;

import junit.framework.TestCase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import comp3350.pbbs.application.Main;
import comp3350.pbbs.business.TransactionAccess;
import comp3350.pbbs.objects.BudgetCategory;
import comp3350.pbbs.objects.CreditCard;


public class TestTransactionAccess extends TestCase {
    private TransactionAccess transactionAccess;
    private String testDate;
    private String testDesc;
    private String testTime;
    private String testAmount;
    private CreditCard testCard;
    private BudgetCategory testBudgetCategory;
    public void setUp() {
        Main.startup();
        transactionAccess = new TransactionAccess();
        testDesc = "Bought groceries.";
        testDate = "30/3/2020";
        testTime = "2:30";
        testAmount = "12.07";
        testCard = new CreditCard("1000100010001000", "Alan Alfred", 6, 22, 27);
        testBudgetCategory = new BudgetCategory("Groceries", 100);
    }

    public void tearDown() {
        Main.shutDown();
    }

    public void testValidTransaction() {
        assertTrue(transactionAccess.addTransaction(testDesc, testDate, testTime, testAmount, testCard, testBudgetCategory));
    }

    public void testValidDates() {
        assertTrue(transactionAccess.isValidDateTime(testDate, testTime));
        assertTrue(transactionAccess.isValidDateTime("30-3-2020", "23:59"));
        assertTrue(transactionAccess.addTransaction(testDesc, "30-3-2020", "23:59", testAmount, testCard, testBudgetCategory));
    }

    public void testInvalidTimes() {
        assertFalse(transactionAccess.isValidDateTime("30/3/2020", "24:00"));
        assertFalse(transactionAccess.isValidDateTime(testDate, null));
        assertFalse(transactionAccess.isValidDateTime(testDate, ""));
        assertFalse(transactionAccess.isValidDateTime(testDate, "time"));
    }

    public void testInvalidDates() {
        assertFalse(transactionAccess.isValidDateTime("30/22/2020", "0:00"));
        assertFalse(transactionAccess.isValidDateTime(null, testTime));
        assertFalse(transactionAccess.isValidDateTime("", testTime));
        assertFalse(transactionAccess.isValidDateTime("date", testTime));
    }

    public void testInvalidTransactionDates() {
        assertFalse(transactionAccess.addTransaction(testDesc, "32/2/2020", testTime, testAmount, testCard, testBudgetCategory));
        assertFalse(transactionAccess.addTransaction(testDesc, "date", testTime, testAmount, testCard, testBudgetCategory));
        assertFalse(transactionAccess.addTransaction(testDesc, "", testTime, testAmount, testCard, testBudgetCategory));
        assertFalse(transactionAccess.addTransaction(testDesc, null, testTime, testAmount, testCard, testBudgetCategory));
    }

    public void testInvalidTransactionTimes() {
        assertFalse(transactionAccess.addTransaction(testDesc, testDate, "25:00", testAmount, testCard, testBudgetCategory));
        assertFalse(transactionAccess.addTransaction(testDesc, testDate, "time", testAmount, testCard, testBudgetCategory));
        assertFalse(transactionAccess.addTransaction(testDesc, testDate, "", testAmount, testCard, testBudgetCategory));
        assertFalse(transactionAccess.addTransaction(testDesc, testDate, null, testAmount, testCard, testBudgetCategory));
    }

    public void testValidAmounts() {
        assertTrue(transactionAccess.isValidAmount(testAmount));
        assertTrue(transactionAccess.isValidAmount("20"));
        assertTrue(transactionAccess.addTransaction(testDesc, testDate, testTime, "20", testCard, testBudgetCategory));
    }

    public void testInvalidAmounts() {
        assertFalse(transactionAccess.isValidAmount("20.205"));
        assertFalse(transactionAccess.isValidAmount("-20"));
        assertFalse(transactionAccess.isValidAmount("number"));
        assertFalse(transactionAccess.isValidAmount(""));
        assertFalse(transactionAccess.isValidAmount(null));
    }

    public void testInvalidTransactionAmounts() {
        assertFalse(transactionAccess.addTransaction(testDesc, testDate, testTime, "20.205",  testCard, testBudgetCategory));
        assertFalse(transactionAccess.addTransaction(testDesc, testDate, testTime, "-20",  testCard, testBudgetCategory));
        assertFalse(transactionAccess.addTransaction(testDesc, testDate, testTime, "amount",  testCard, testBudgetCategory));
        assertFalse(transactionAccess.addTransaction(testDesc, testDate, testTime, "",  testCard, testBudgetCategory));
        assertFalse(transactionAccess.addTransaction(testDesc, testDate, testTime, null,  testCard, testBudgetCategory));
    }

    public void testValidDescriptions() {
        assertTrue(transactionAccess.isValidDescription(testDesc));
        assertTrue(transactionAccess.isValidDescription(""));
        assertTrue(transactionAccess.addTransaction("", testDate, testTime, testAmount, testCard, testBudgetCategory));
    }

    public void testInvalidDescriptions() {
        assertFalse(transactionAccess.isValidDescription(null));
    }

    public void testInvalidCard() {
        assertFalse(transactionAccess.addTransaction(testDesc, testDate, testTime, testAmount, null, testBudgetCategory));
    }

    public void testInvalidBudgetCategory() {
        assertFalse(transactionAccess.addTransaction(testDesc, testDate, testTime, testAmount, testCard, null));
    }
}
