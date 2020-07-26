package comp3350.pbbs.tests.business.accesstransaction;

import junit.framework.TestCase;

import comp3350.pbbs.application.Services;
import comp3350.pbbs.business.AccessTransaction;
import comp3350.pbbs.objects.BudgetCategory;
import comp3350.pbbs.objects.CreditCard;
import comp3350.pbbs.tests.persistence.StubDatabase;

/**
 * TestAccessTransactionUpload
 * Group4
 * PBBS
 *
 * This class tests part of the AccessTransaction class
 */
public class TestAccessTransactionUpload extends TestCase {
    private AccessTransaction accessTransaction;
    private String testDate;
    private String testDesc;
    private String testTime;
    private String testAmount;
    private CreditCard testCard;
    private BudgetCategory testBudgetCategory;

    /**
     * Sets the test variables before each test
     */
    public void setUp() {
        Services.createDataAccess(new StubDatabase("test"));
        accessTransaction = new AccessTransaction(true);
        testDesc = "Bought groceries.";
        testDate = "30/3/2020";
        testTime = "2:30";
        testAmount = "12.07";
        testCard = new CreditCard("mastercard", "1000100010001000", "Alan Alfred", 6, 2022, 27);
        testBudgetCategory = new BudgetCategory("Groceries", 100);
    }

    /**
     * Closes the data access connection at the end of each test
     */
    public void tearDown() {
        Services.closeDataAccess();
    }

    /**
     * Test adding valid transactions to the database
     */
    public void testValidTransaction() {
        assertTrue(accessTransaction.addTransaction(testDesc, testDate, testTime, testAmount, testCard, testBudgetCategory));
        assertTrue(accessTransaction.addTransaction(testDesc, "30/3/2020", testTime, testAmount, testCard, testBudgetCategory));
        assertTrue(accessTransaction.addTransaction(testDesc, "30/1/2020", testTime, testAmount, testCard, testBudgetCategory));
    }

    /**
     * Test checking valid dates and adding transactions using the hyphen format rather than slashes
     */
    public void testValidDates() {
        assertTrue(accessTransaction.isValidDateTime(testDate, testTime));
        assertTrue(accessTransaction.isValidDateTime("30-3-2020", "23:59"));
        assertTrue(accessTransaction.addTransaction(testDesc, "30-3-2020", "23:59", testAmount, testCard, testBudgetCategory));
    }

    /**
     * Test checking invalid time strings
     */
    public void testInvalidTimes() {
        assertFalse(accessTransaction.isValidDateTime(testDate, "24:00"));
        assertFalse(accessTransaction.isValidDateTime(testDate, null));
        assertFalse(accessTransaction.isValidDateTime(testDate, ""));
        assertFalse(accessTransaction.isValidDateTime(testDate, "time"));
    }

    /**
     * Test checking invalid date strings
     */
    public void testInvalidDates() {
        assertFalse(accessTransaction.isValidDateTime("30/22/2020", "0:00"));
        assertFalse(accessTransaction.isValidDateTime(null, testTime));
        assertFalse(accessTransaction.isValidDateTime("", testTime));
        assertFalse(accessTransaction.isValidDateTime("date", testTime));
    }

    /**
     * Test inserting invalid date strings
     */
    public void testInvalidTransactionDates() {
        assertFalse(accessTransaction.addTransaction(testDesc, "32/2/2020", testTime, testAmount, testCard, testBudgetCategory));
        assertFalse(accessTransaction.addTransaction(testDesc, "date", testTime, testAmount, testCard, testBudgetCategory));
        assertFalse(accessTransaction.addTransaction(testDesc, "", testTime, testAmount, testCard, testBudgetCategory));
        assertFalse(accessTransaction.addTransaction(testDesc, null, testTime, testAmount, testCard, testBudgetCategory));
    }

    /**
     * Test inserting invalid time strings
     */
    public void testInvalidTransactionTimes() {
        assertFalse(accessTransaction.addTransaction(testDesc, testDate, "25:00", testAmount, testCard, testBudgetCategory));
        assertFalse(accessTransaction.addTransaction(testDesc, testDate, "time", testAmount, testCard, testBudgetCategory));
        assertFalse(accessTransaction.addTransaction(testDesc, testDate, "", testAmount, testCard, testBudgetCategory));
        assertFalse(accessTransaction.addTransaction(testDesc, testDate, null, testAmount, testCard, testBudgetCategory));
    }

    /**
     * Test checking valid amount strings and inserting the non tested amount string format (no decimals)
     */
    public void testValidAmounts() {
        assertTrue(accessTransaction.isValidAmount(testAmount));
        assertTrue(accessTransaction.isValidAmount("20"));
        assertTrue(accessTransaction.addTransaction(testDesc, testDate, testTime, "20", testCard, testBudgetCategory));
    }

    /**
     * Test checking invalid amount strings
     */
    public void testInvalidAmounts() {
        assertFalse(accessTransaction.isValidAmount("20.205"));
        assertFalse(accessTransaction.isValidAmount("-20"));
        assertFalse(accessTransaction.isValidAmount("number"));
        assertFalse(accessTransaction.isValidAmount(""));
        assertFalse(accessTransaction.isValidAmount(null));
    }

    /**
     * Test inserting invalid amount strings
     */
    public void testInvalidTransactionAmounts() {
        assertFalse(accessTransaction.addTransaction(testDesc, testDate, testTime, "20.205", testCard, testBudgetCategory));
        assertFalse(accessTransaction.addTransaction(testDesc, testDate, testTime, "-20", testCard, testBudgetCategory));
        assertFalse(accessTransaction.addTransaction(testDesc, testDate, testTime, "amount", testCard, testBudgetCategory));
        assertFalse(accessTransaction.addTransaction(testDesc, testDate, testTime, "", testCard, testBudgetCategory));
        assertFalse(accessTransaction.addTransaction(testDesc, testDate, testTime, null, testCard, testBudgetCategory));
    }

    /**
     * Test checking and inserting a valid description
     */
    public void testValidDescription() {
        assertTrue(accessTransaction.isValidDescription(testDesc));
    }

    /**
     * Test checking and inserting invalid descriptions
     */
    public void testInvalidDescriptions() {
        assertFalse(accessTransaction.isValidDescription(null));
        assertFalse(accessTransaction.isValidDescription(""));

        assertFalse(accessTransaction.addTransaction(null, testDate, testTime, testAmount, testCard, testBudgetCategory));
        assertFalse(accessTransaction.addTransaction("", testDate, testTime, testAmount, testCard, testBudgetCategory));
    }

    /**
     * Test inserting a transaction with an invalid card
     */
    public void testInvalidCard() {
        assertFalse(accessTransaction.addTransaction(testDesc, testDate, testTime, testAmount, null, testBudgetCategory));
    }

    /**
     * Test inserting a transaction with an invalid budget category
     */
    public void testInvalidBudgetCategory() {
        assertFalse(accessTransaction.addTransaction(testDesc, testDate, testTime, testAmount, testCard, null));
    }
}
