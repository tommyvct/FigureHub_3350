package comp3350.pbbs.tests.business;

import junit.framework.TestCase;

import comp3350.pbbs.application.Main;
import comp3350.pbbs.business.AccessTransaction;
import comp3350.pbbs.objects.BudgetCategory;
import comp3350.pbbs.objects.CreditCard;


public class TestAccessTransactionUpload extends TestCase {
    private AccessTransaction accessTransaction;
    private String testDate;
    private String testDesc;
    private String testTime;
    private String testAmount;
    private CreditCard testCard;
    private BudgetCategory testBudgetCategory;
    public void setUp() {
        Main.startup();
        accessTransaction = new AccessTransaction();
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
        assertTrue(accessTransaction.addTransaction(testDesc, testDate, testTime, testAmount, testCard, testBudgetCategory));
    }

    public void testValidDates() {
        assertTrue(accessTransaction.isValidDateTime(testDate, testTime));
        assertTrue(accessTransaction.isValidDateTime("30-3-2020", "23:59"));
        assertTrue(accessTransaction.addTransaction(testDesc, "30-3-2020", "23:59", testAmount, testCard, testBudgetCategory));
    }

    public void testInvalidTimes() {
        assertFalse(accessTransaction.isValidDateTime("30/3/2020", "24:00"));
        assertFalse(accessTransaction.isValidDateTime(testDate, null));
        assertFalse(accessTransaction.isValidDateTime(testDate, ""));
        assertFalse(accessTransaction.isValidDateTime(testDate, "time"));
    }

    public void testInvalidDates() {
        assertFalse(accessTransaction.isValidDateTime("30/22/2020", "0:00"));
        assertFalse(accessTransaction.isValidDateTime(null, testTime));
        assertFalse(accessTransaction.isValidDateTime("", testTime));
        assertFalse(accessTransaction.isValidDateTime("date", testTime));
    }

    public void testInvalidTransactionDates() {
        assertFalse(accessTransaction.addTransaction(testDesc, "32/2/2020", testTime, testAmount, testCard, testBudgetCategory));
        assertFalse(accessTransaction.addTransaction(testDesc, "date", testTime, testAmount, testCard, testBudgetCategory));
        assertFalse(accessTransaction.addTransaction(testDesc, "", testTime, testAmount, testCard, testBudgetCategory));
        assertFalse(accessTransaction.addTransaction(testDesc, null, testTime, testAmount, testCard, testBudgetCategory));
    }

    public void testInvalidTransactionTimes() {
        assertFalse(accessTransaction.addTransaction(testDesc, testDate, "25:00", testAmount, testCard, testBudgetCategory));
        assertFalse(accessTransaction.addTransaction(testDesc, testDate, "time", testAmount, testCard, testBudgetCategory));
        assertFalse(accessTransaction.addTransaction(testDesc, testDate, "", testAmount, testCard, testBudgetCategory));
        assertFalse(accessTransaction.addTransaction(testDesc, testDate, null, testAmount, testCard, testBudgetCategory));
    }

    public void testValidAmounts() {
        assertTrue(accessTransaction.isValidAmount(testAmount));
        assertTrue(accessTransaction.isValidAmount("20"));
        assertTrue(accessTransaction.addTransaction(testDesc, testDate, testTime, "20", testCard, testBudgetCategory));
    }

    public void testInvalidAmounts() {
        assertFalse(accessTransaction.isValidAmount("20.205"));
        assertFalse(accessTransaction.isValidAmount("-20"));
        assertFalse(accessTransaction.isValidAmount("number"));
        assertFalse(accessTransaction.isValidAmount(""));
        assertFalse(accessTransaction.isValidAmount(null));
    }

    public void testInvalidTransactionAmounts() {
        assertFalse(accessTransaction.addTransaction(testDesc, testDate, testTime, "20.205",  testCard, testBudgetCategory));
        assertFalse(accessTransaction.addTransaction(testDesc, testDate, testTime, "-20",  testCard, testBudgetCategory));
        assertFalse(accessTransaction.addTransaction(testDesc, testDate, testTime, "amount",  testCard, testBudgetCategory));
        assertFalse(accessTransaction.addTransaction(testDesc, testDate, testTime, "",  testCard, testBudgetCategory));
        assertFalse(accessTransaction.addTransaction(testDesc, testDate, testTime, null,  testCard, testBudgetCategory));
    }

    public void testValidDescriptions() {
        assertTrue(accessTransaction.isValidDescription(testDesc));
        assertTrue(accessTransaction.isValidDescription(""));
        assertTrue(accessTransaction.addTransaction("", testDate, testTime, testAmount, testCard, testBudgetCategory));
    }

    public void testInvalidDescriptions() {
        assertFalse(accessTransaction.isValidDescription(null));
    }

    public void testInvalidCard() {
        assertFalse(accessTransaction.addTransaction(testDesc, testDate, testTime, testAmount, null, testBudgetCategory));
    }

    public void testInvalidBudgetCategory() {
        assertFalse(accessTransaction.addTransaction(testDesc, testDate, testTime, testAmount, testCard, null));
    }
}
