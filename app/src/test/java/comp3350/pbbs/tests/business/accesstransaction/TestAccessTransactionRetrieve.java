package comp3350.pbbs.tests.business.accesstransaction;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import comp3350.pbbs.application.Services;
import comp3350.pbbs.business.AccessTransaction;
import comp3350.pbbs.objects.BudgetCategory;
import comp3350.pbbs.objects.Cards.Card;
import comp3350.pbbs.objects.Transaction;
import comp3350.pbbs.persistence.DataAccess;
import comp3350.pbbs.tests.persistence.StubDatabase;

/**
 * TestAccessTransactionRetrieve
 * Group4
 * PBBS
 *
 * This class tests part of the AccessTransaction class
 */
public class TestAccessTransactionRetrieve extends TestCase {
    private AccessTransaction accessTransaction;
    private Transaction testTransaction1;
    private Transaction testTransaction2;
    private Transaction testTransaction3;
    private Date testDate;
    private String testDesc;
    private float testAmount;
    private Card testCard;
    private BudgetCategory testBudgetCategory;
    private DataAccess db;

    /**
     * Sets the test values and pre-populates database
     */
    public void setUp() {
        db = Services.createDataAccess(new StubDatabase("test"));

        testDate = new Date();
        testAmount = 20;
        testDesc = "Bought groceries.";
        accessTransaction = new AccessTransaction(true);
        testCard = new Card("mastercard", "1000100010001000", "Alan Alfred", 6, 2022, 27);
        testBudgetCategory = new BudgetCategory("Groceries", 100);
        testTransaction1 = new Transaction(testDate, testAmount, testDesc, testCard, testBudgetCategory);
        testTransaction2 = new Transaction(Services.calcDate(testDate, -1), testAmount, testDesc, testCard, testBudgetCategory);
        testTransaction3 = new Transaction(Services.calcDate(testDate, -2), testAmount, testDesc, testCard, testBudgetCategory);

        assertTrue(db.insertTransaction(testTransaction1));
        assertTrue(db.insertTransaction(testTransaction2));
        assertTrue(db.insertTransaction(testTransaction3));
     }

    /**
     * Closes the server connection
     */
    public void tearDown() {
        Services.closeDataAccess();
    }

    /**
     * Tests retrieving transactions on a range containing a single date
     */
    public void testRangeOneDay() {
        List<Transaction> results = accessTransaction.retrieveTransactions(Services.calcDate(testDate, -1), Services.calcDate(testDate, 1));
        assertTrue(results.contains(testTransaction1));
        assertFalse(results.contains(testTransaction2));
        assertFalse(results.contains(testTransaction3));
    }

    /**
     * Tests retrieving transactions on a range containing two dates
     */
    public void testRangeTwoDays() {
        List<Transaction> results = accessTransaction.retrieveTransactions(Services.calcDate(testDate, -2), Services.calcDate(testDate, 1));
        assertTrue(results.contains(testTransaction1));
        assertTrue(results.contains(testTransaction2));
        assertFalse(results.contains(testTransaction3));
    }

    /**
     * Test invalid inputs for ranges, as well as a range outside the domain of dates
     */
    public void testInvalidRanges() {
        assertNull(accessTransaction.retrieveTransactions(null, null));
        assertNull(accessTransaction.retrieveTransactions(new Date(), null));
        assertNull(accessTransaction.retrieveTransactions(null, new Date()));

        Calendar c = Calendar.getInstance();
        c.set(1999, 1, 11);
        assertTrue(accessTransaction.retrieveTransactions(c.getTime(), c.getTime()).isEmpty());
    }

    /**
     * Tests retrieving all transactions through having no arguments or setting a wide date range
     */
    public void testRetrieveAllTransactions() {
        List<Transaction> results = accessTransaction.retrieveTransactions();
        assertTrue(results.contains(testTransaction1));
        assertTrue(results.contains(testTransaction2));
        assertTrue(results.contains(testTransaction3));

        Transaction testTransaction4 = new Transaction(Services.calcDate(testDate, -5), testAmount, testDesc, testCard, testBudgetCategory);
        assertFalse(results.contains(testTransaction4));

        results = accessTransaction.retrieveTransactions(Services.calcDate(testDate, -500), Services.calcDate(testDate, 500));
        assertTrue(results.contains(testTransaction1));
        assertTrue(results.contains(testTransaction2));
        assertTrue(results.contains(testTransaction3));
        assertFalse(results.contains(testTransaction4));
    }

    /**
     * Test getting active months with no transactions
     */
    public void testEmptyActiveMonths() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(testDate);
        BudgetCategory bc1 = new BudgetCategory("test", 20);
        List<Calendar> result = accessTransaction.getActiveMonths(bc1);
        assertTrue(result.isEmpty());
    }

    /**
     * Test getting active months with a single transaction
     */
    public void testSingleActiveMonth() {
        BudgetCategory bc1 = new BudgetCategory("test", 20);
        Transaction t1 = new Transaction(testDate, testAmount, testDesc, testCard, bc1);
        db.insertTransaction(t1);
        List<Calendar> result = accessTransaction.getActiveMonths(bc1);
        assertEquals(1, result.size());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(testDate);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        assertTrue(result.contains(calendar));
    }

    /**
     * Test getting active months with multiple transactions
     */
    public void testMultipleActiveMonths() {
        BudgetCategory bc1 = new BudgetCategory("test", 20);
        Transaction t1 = new Transaction(testDate, testAmount, testDesc, testCard, bc1);
        Transaction t2 = new Transaction(testDate, testAmount+1, testDesc, testCard, bc1);
        db.insertTransaction(t1);
        db.insertTransaction(t2);
        List<Calendar> result = accessTransaction.getActiveMonths(bc1);
        assertEquals(1, result.size());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(testDate);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        assertTrue(result.contains(calendar));
        calendar.set(1000, 1, 1);
        Transaction t3 = new Transaction(calendar.getTime(), testAmount, testDesc, testCard, bc1);
        db.insertTransaction(t3);
        result = accessTransaction.getActiveMonths(bc1);
        assertEquals(2, result.size());
        assertTrue(result.contains(calendar));
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        assertTrue(result.contains(calendar));
    }

    /**
     * Test getting active months with an invalid input
     */
    public void testInvalidActiveMonths() {
        try {
            accessTransaction.getActiveMonths(null);
            fail("Expected NullPointerException.");
        } catch (NullPointerException ignored) {

        }
    }
}
