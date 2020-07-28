package comp3350.pbbs.tests.business.accesstransaction;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import comp3350.pbbs.application.Services;
import comp3350.pbbs.business.AccessTransaction;
import comp3350.pbbs.objects.BudgetCategory;
import comp3350.pbbs.objects.CreditCard;
import comp3350.pbbs.objects.Transaction;
import comp3350.pbbs.persistence.StubDatabase;

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
    private CreditCard testCard;
    private BudgetCategory testBudgetCategory;
    private StubDatabase db;

    /**
     * Sets the test values and pre-populates database
     */
    public void setUp() {
        db = Services.createDataAccess("test");

        testDate = new Date();
        testAmount = 20;
        testDesc = "Bought groceries.";
        accessTransaction = new AccessTransaction(true);
        testCard = new CreditCard("mastercard", "1000100010001000", "Alan Alfred", 6, 2022, 27);
        testBudgetCategory = new BudgetCategory("Groceries", 100);
        testTransaction1 = new Transaction(testDate, testAmount, testDesc, testCard, testBudgetCategory);
        testTransaction2 = new Transaction(Services.calcDate(testDate, -1), testAmount, testDesc, testCard, testBudgetCategory);
        testTransaction3 = new Transaction(Services.calcDate(testDate, -2), testAmount, testDesc, testCard, testBudgetCategory);

        assertTrue(db.addTransactions(Arrays.asList(testTransaction1, testTransaction2, testTransaction3)));
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


}
