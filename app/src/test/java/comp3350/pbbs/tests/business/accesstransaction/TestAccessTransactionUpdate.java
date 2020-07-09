package comp3350.pbbs.tests.business.accesstransaction;

import junit.framework.TestCase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import comp3350.pbbs.application.Services;
import comp3350.pbbs.business.AccessTransaction;
import comp3350.pbbs.objects.BudgetCategory;
import comp3350.pbbs.objects.CreditCard;
import comp3350.pbbs.objects.Transaction;
import comp3350.pbbs.persistence.StubDatabase;

/**
 * TestAccessTransactionUpdate
 * Group4
 * PBBS
 *
 * This class tests part of the AccessTransaction class
 */
public class TestAccessTransactionUpdate extends TestCase {
    private AccessTransaction accessTransaction;
    private Transaction testTransaction1;
    private Transaction testTransaction2;
    private Transaction testTransaction3;
    private Date testDate;
    private String testTimeStr;
    private String testDateStr;
    private String testAmountStr;
    private String testDesc;
    private float testAmount;
    private CreditCard testCard;
    private BudgetCategory testBudgetCategory;
    private StubDatabase db;

    /**
     * Sets the test variables before each test
     */
    public void setUp() throws ParseException {
        db = Services.createDataAccess("test");

        DateFormat df = new SimpleDateFormat("d/M/yyyy k:m");
        testDate = df.parse(df.format(new Date()));
        testAmount = 19.99f;
        testDesc = "Bought groceries.";
        accessTransaction = new AccessTransaction(true);
        testCard = new CreditCard("fasd f", "1000100010001000", "Alan Alfred", 6, 2022, 27);
        testBudgetCategory = new BudgetCategory("Groceries", 100);
        testTransaction1 = new Transaction(testDate, testAmount, testDesc, testCard, testBudgetCategory);
        testTransaction2 = new Transaction(StubDatabase.calcDate(testDate, -1), testAmount, testDesc, testCard, testBudgetCategory);
        // Won't be added to the database
        testTransaction3 = new Transaction(StubDatabase.calcDate(testDate, -2), 12.07f, testDesc, testCard, testBudgetCategory);
        df = new SimpleDateFormat("d/M/yyyy");
        testDateStr = df.format(testTransaction3.getTime());
        df = new SimpleDateFormat("k:m");
        testTimeStr = df.format(testTransaction3.getTime());
        testAmountStr = "" + testTransaction3.getAmount();

        assertTrue(db.insertTransaction(testTransaction1));
        assertTrue(db.insertTransaction(testTransaction2));
    }

    /**
     * Closes the data access connection at the end of each test
     */
    public void tearDown() {
        Services.closeDataAccess();
    }

    /**
     * Asserts that the transaction table contains testTransaction3 and testTransaction2, but not
     * testTransaction1. Used in multiple test methods.
     */
    private void assertUpdated() {
        List<Transaction> results = accessTransaction.retrieveTransactions();
        assertTrue(results.contains(testTransaction3));
        assertFalse(results.contains(testTransaction1));
        assertTrue(results.contains(testTransaction2));
    }

    /**
     * Test updating valid transactions to the database
     */
    public void testValidTransaction() {
        List<Transaction> results = accessTransaction.retrieveTransactions();
        assertTrue(accessTransaction.updateTransaction(testTransaction1, testDesc, testDateStr, testTimeStr, testAmountStr, testCard, testBudgetCategory));
        assertUpdated();
    }

    /**
     * Test updating with invalid date strings
     */
    public void testInvalidDates() {
        assertFalse(accessTransaction.updateTransaction(testTransaction1, testDesc, "32/2/2020", testTimeStr, testAmountStr, testCard, testBudgetCategory));
        assertFalse(accessTransaction.updateTransaction(testTransaction1, testDesc, "date", testTimeStr, testAmountStr, testCard, testBudgetCategory));
        assertFalse(accessTransaction.updateTransaction(testTransaction1, testDesc, "", testTimeStr, testAmountStr, testCard, testBudgetCategory));
        assertFalse(accessTransaction.updateTransaction(testTransaction1, testDesc, null, testTimeStr, testAmountStr, testCard, testBudgetCategory));
    }

    /**
     * Test updating with invalid time strings
     */
    public void testInvalidTimes() {
        assertFalse(accessTransaction.updateTransaction(testTransaction1, testDesc, testDateStr, "25:00", testAmountStr, testCard, testBudgetCategory));
        assertFalse(accessTransaction.updateTransaction(testTransaction1, testDesc, testDateStr, "time", testAmountStr, testCard, testBudgetCategory));
        assertFalse(accessTransaction.updateTransaction(testTransaction1, testDesc, testDateStr, "", testAmountStr, testCard, testBudgetCategory));
        assertFalse(accessTransaction.updateTransaction(testTransaction1, testDesc, testDateStr, null, testAmountStr, testCard, testBudgetCategory));
    }

    /**
     * Test updating with the non tested amount string format (no decimals)
     */
    public void testValidAmounts() {
        assertTrue(accessTransaction.updateTransaction(testTransaction1, testDesc, testDateStr, testTimeStr, "20", testCard, testBudgetCategory));
        testTransaction3 = new Transaction(StubDatabase.calcDate(testDate, -2), 20, testDesc, testCard, testBudgetCategory);
        assertUpdated();
    }

    /**
     * Test updating with invalid amount strings
     */
    public void testInvalidAmounts() {
        assertFalse(accessTransaction.updateTransaction(testTransaction1, testDesc, testDateStr, testTimeStr, "20.205", testCard, testBudgetCategory));
        assertFalse(accessTransaction.updateTransaction(testTransaction1, testDesc, testDateStr, testTimeStr, "-20", testCard, testBudgetCategory));
        assertFalse(accessTransaction.updateTransaction(testTransaction1, testDesc, testDateStr, testTimeStr, "amount", testCard, testBudgetCategory));
        assertFalse(accessTransaction.updateTransaction(testTransaction1, testDesc, testDateStr, testTimeStr, "", testCard, testBudgetCategory));
        assertFalse(accessTransaction.updateTransaction(testTransaction1, testDesc, testDateStr, testTimeStr, null, testCard, testBudgetCategory));
    }

    /**
     * Test updating with invalid descriptions
     */
    public void testInvalidDescriptions() {
        assertFalse(accessTransaction.isValidDescription(null));
        assertFalse(accessTransaction.updateTransaction(testTransaction1, "", testDateStr, testTimeStr, testAmountStr, testCard, testBudgetCategory));
        assertFalse(accessTransaction.updateTransaction(testTransaction1, null, testDateStr, testTimeStr, testAmountStr, testCard, testBudgetCategory));
    }

    /**
     * Test updating a transaction with an invalid card
     */
    public void testInvalidCard() {
        assertFalse(accessTransaction.updateTransaction(testTransaction1, testDesc, testDateStr, testTimeStr, testAmountStr, null, testBudgetCategory));
    }

    /**
     * Test updating a transaction with an invalid budget category
     */
    public void testInvalidBudgetCategory() {
        assertFalse(accessTransaction.updateTransaction(testTransaction1, testDesc, testDateStr, testTimeStr, testAmountStr, testCard, null));
    }
}
