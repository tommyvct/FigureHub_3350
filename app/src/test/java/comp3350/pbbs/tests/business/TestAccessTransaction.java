package comp3350.pbbs.tests.business;

import junit.framework.TestCase;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import comp3350.pbbs.persistence.DataAccessController;
import comp3350.pbbs.business.AccessTransaction;
import comp3350.pbbs.objects.BankAccount;
import comp3350.pbbs.objects.BudgetCategory;
import comp3350.pbbs.objects.Card;
import comp3350.pbbs.objects.Transaction;
import comp3350.pbbs.persistence.DataAccessI;
import comp3350.pbbs.tests.persistence.StubDatabase;

/**
 * TestAccessTransaction
 * Group4
 * PBBS
 * <p>
 * This class tests AccessTransaction class
 */
public class TestAccessTransaction extends TestCase {
    private AccessTransaction accessTransaction;
    private DataAccessI testDB;
    List<Transaction> transactions;

    //Testing Data
    private Date date = new Date(2020 - 07 - 15);
    private String testDate = date.toString();
    private String testAmount = "12.07";
    private String testDesc = "Bought groceries.";
    private String testTime = "2:30";
    private Card testCard = new Card("mastercard", "1000100010001000", "Alan Alfred", 6, 2022, 27);
    private Card testDebitCard = new Card("Mastercard debit", "94564654684", "Tommy", 03, 2024);
    private BankAccount testBankAccount = new BankAccount("cheque", "965214", testDebitCard);
    private BudgetCategory testBudgetCategory = new BudgetCategory("Groceries", 100);
    private Transaction testTransaction1 = new Transaction(date, 19.99f, testDesc, testCard, testBudgetCategory);
    private Transaction testTransaction2 = new Transaction(StubDatabase.calcDate(date, -1), 19.99f, testDesc, testCard, testBudgetCategory);
    private Transaction testTransaction3 = new Transaction(StubDatabase.calcDate(date, -2), 19.99f, testDesc, testCard, testBudgetCategory);

    /**
     * Before each test, set the test values
     */
    public void setUp() {
        testDB = DataAccessController.createDataAccess(new StubDatabase("test"));
        accessTransaction = new AccessTransaction(true);
        assertTrue(testDB.insertTransaction(testTransaction1));
        assertTrue(testDB.insertTransaction(testTransaction2));
        assertTrue(testDB.insertTransaction(testTransaction3));

        transactions = accessTransaction.retrieveTransactions();
    }

    /**
     * Testing that all methods work using valid input
     */
    public void testValidInput() {
        //Testing adding and updating valid transactions:
        HelperBothCardTypesTogether(true, "groceries", "31/12/2020", "00:00", "1.23", testBudgetCategory);
        HelperBothCardTypesTogether(true, "GROCERIES", "30/1/2020", "1:15", "12.34", testBudgetCategory);
        HelperBothCardTypesTogether(true, "GrOcErIeS!!!!", "30-3-2020", "23:59", "000000.12", testBudgetCategory);
        HelperBothCardTypesTogether(true, "???}}}|||", "30/3/2022", "19:45", "20", testBudgetCategory);
        HelperBothCardTypesTogether(true, "How about now?", "1/8/2020", "12:34", "123456789.00", testBudgetCategory);
        HelperBothCardTypesTogether(true, " Spaces Spaces ", "30/1/2020", "5:16", "123456789.89", testBudgetCategory);
        HelperBothCardTypesTogether(true, "! testDesc !", "30-3-2020", "23:59", "0.15", testBudgetCategory);

        assertUpdated();
    }

    /**
     * Testing that all methods work using invalid integer input (for $ amount only)
     */
    public void testInvalidLimitIntegerInput() {
        HelperBothCardTypesTogether(false, testDesc, testDate, testTime, "20.205", testBudgetCategory);
        HelperBothCardTypesTogether(false, testDesc, testDate, testTime, "-20", testBudgetCategory);
        HelperBothCardTypesTogether(false, testDesc, testDate, testTime, "amount", testBudgetCategory);
        HelperBothCardTypesTogether(false, testDesc, testDate, testTime, "-20.005", testBudgetCategory);
        HelperBothCardTypesTogether(false, testDesc, testDate, testTime, " $0.25 ", testBudgetCategory);
        HelperBothCardTypesTogether(false, testDesc, testDate, testTime, "-0.25", testBudgetCategory);
        HelperBothCardTypesTogether(false, testDesc, testDate, testTime, "Twenty", testBudgetCategory);
        HelperBothCardTypesTogether(false, testDesc, testDate, testTime, "(20)", testBudgetCategory);
    }

    /**
     * Testing that all methods work using invalid zero input
     */
    public void testInvalidZeroInput() {
        HelperBothCardTypesTogether(false, testDesc, testDate, testTime, "0", testBudgetCategory);
        HelperBothCardTypesTogether(false, testDesc, testDate, testTime, "-0", testBudgetCategory);
        HelperBothCardTypesTogether(false, testDesc, testDate, testTime, "0.00", testBudgetCategory);
        HelperBothCardTypesTogether(false, testDesc, testDate, testTime, "0,0", testBudgetCategory);
        HelperBothCardTypesTogether(false, testDesc, testDate, testTime, "0 0", testBudgetCategory);
    }

    /**
     * Testing using null and empty input
     */
    public void testInvalidNullAndEmptyInput() {
        //Testing adding and updating transaction with null values:
        //description
        HelperBothCardTypesTogether(false, null, testDate, testTime, testAmount, testBudgetCategory);
        HelperBothCardTypesTogether(false, "", testDate, testTime, testAmount, testBudgetCategory);
        HelperBothCardTypesTogether(false, " ", testDate, testTime, testAmount, testBudgetCategory);
        //time
        HelperBothCardTypesTogether(false, testDesc, testDate, "", testAmount, testBudgetCategory);
        HelperBothCardTypesTogether(false, testDesc, testDate, null, testAmount, testBudgetCategory);
        HelperBothCardTypesTogether(false, testDesc, testDate, " ", testAmount, testBudgetCategory);
        HelperBothCardTypesTogether(false, testDesc, testDate, ":", testAmount, testBudgetCategory);
        HelperBothCardTypesTogether(false, testDesc, testDate, " : ", testAmount, testBudgetCategory);
        //Date
        HelperBothCardTypesTogether(false, testDesc, "", testTime, testAmount, testBudgetCategory);
        HelperBothCardTypesTogether(false, testDesc, null, testTime, testAmount, testBudgetCategory);
        HelperBothCardTypesTogether(false, testDesc, "//", testTime, testAmount, testBudgetCategory);
        HelperBothCardTypesTogether(false, testDesc, " / / ", testTime, testAmount, testBudgetCategory);
        //amount
        HelperBothCardTypesTogether(false, testDesc, testDate, testTime, "", testBudgetCategory);
        HelperBothCardTypesTogether(false, testDesc, testDate, testTime, null, testBudgetCategory);
        HelperBothCardTypesTogether(false, testDesc, testDate, testTime, " ", testBudgetCategory);
        HelperBothCardTypesTogether(false, testDesc, testDate, testTime, ".", testBudgetCategory);
        //cards
        assertFalse(accessTransaction.addTransaction(testDesc, testDate, testTime, testAmount, null, testBudgetCategory));
        assertFalse(accessTransaction.addDebitTransaction(testDesc, testDate, testTime, testAmount, null, null, testBudgetCategory));
        assertFalse(accessTransaction.addDebitTransaction(testDesc, testDate, testTime, testAmount, testDebitCard, null, testBudgetCategory));
        assertFalse(accessTransaction.addDebitTransaction(testDesc, testDate, testTime, testAmount, null, testBankAccount, testBudgetCategory));
        assertFalse(accessTransaction.updateTransaction(transactions.get(0), testDesc, testDate, testTime, testAmount, null, testBudgetCategory));
        assertFalse(accessTransaction.updateDebitTransaction(transactions.get(0), testDesc, testDate, testTime, testAmount, null, null, testBudgetCategory));
        assertFalse(accessTransaction.updateDebitTransaction(transactions.get(0), testDesc, testDate, testTime, testAmount, testDebitCard, null, testBudgetCategory));
        assertFalse(accessTransaction.updateDebitTransaction(transactions.get(0), testDesc, testDate, testTime, testAmount, null, testBankAccount, testBudgetCategory));
        //budget Category
        HelperBothCardTypesTogether(false, testDesc, testDate, testTime, testAmount, null);

        //Test deleting invalid input
        assertFalse(accessTransaction.deleteTransaction(new Transaction(new Date(), 20, "Bought groceries", testCard, testBudgetCategory)));
        assertFalse(accessTransaction.deleteTransaction(null));

    }

    /**
     * Testing using bad date input (only for date params)
     */
    public void testInvalidDateInput() {
        //Testing adding and updating transaction with bad date values:
        HelperBothCardTypesTogether(false, testDesc, "Date", testTime, testAmount, testBudgetCategory);
        HelperBothCardTypesTogether(false, testDesc, "32/2/2020", testTime, testAmount, testBudgetCategory);
        HelperBothCardTypesTogether(false, testDesc, "0/0/0000", testTime, testAmount, testBudgetCategory);
        HelperBothCardTypesTogether(false, testDesc, "-02/01/2022", testTime, testAmount, testBudgetCategory);
        HelperBothCardTypesTogether(false, testDesc, "02/-01/2022", testTime, testAmount, testBudgetCategory);
        HelperBothCardTypesTogether(false, testDesc, "02/01/-2022", testTime, testAmount, testBudgetCategory);
        HelperBothCardTypesTogether(false, testDesc, "15/13/2022", testTime, testAmount, testBudgetCategory);
        HelperBothCardTypesTogether(false, testDesc, "30/02/2022", testTime, testAmount, testBudgetCategory);
    }

    /**
     * Testing using bad time input (only for time params)
     */
    public void testInvalidTimeInput() {
        //Testing adding and updating transaction with bad time values:
        HelperBothCardTypesTogether(false, testDesc, testDate, "24:00", testAmount, testBudgetCategory);
        HelperBothCardTypesTogether(false, testDesc, testDate, "-0:01", testAmount, testBudgetCategory);
        HelperBothCardTypesTogether(false, testDesc, testDate, "48:00", testAmount, testBudgetCategory);
        HelperBothCardTypesTogether(false, testDesc, testDate, "Time", testAmount, testBudgetCategory);
        HelperBothCardTypesTogether(false, testDesc, testDate, "2:60", testAmount, testBudgetCategory);
        HelperBothCardTypesTogether(false, testDesc, testDate, "023:15", testAmount, testBudgetCategory);
        HelperBothCardTypesTogether(false, testDesc, testDate, "23:150", testAmount, testBudgetCategory);
    }

    /**
     * Helper method that enables testing addTransaction, addDebitTransaction, updateTransaction,
     * and updateDebitTransaction at once.
     *
     * @param succeed  param that tells whether the tests should pass or fail
     * @param desc     description to be tested
     * @param date     date to be tested
     * @param time     time to be tested
     * @param amount   amount to be tested
     * @param category category to be tested
     */
    public void HelperBothCardTypesTogether(Boolean succeed, String desc, String date, String time, String amount, BudgetCategory category) {
        if (succeed) {
            assertTrue(accessTransaction.addTransaction(desc, date, time, amount, testCard, category));
            assertTrue(accessTransaction.addDebitTransaction(desc, date, time, amount, testDebitCard, testBankAccount, category));
            assertTrue(accessTransaction.updateTransaction(transactions.get(0), desc, date, time, amount, testCard, category));
            assertTrue(accessTransaction.updateDebitTransaction(transactions.get(0), desc, date, time, amount, testDebitCard, testBankAccount, category));
        } else {
            assertFalse(accessTransaction.addDebitTransaction(desc, date, time, amount, testDebitCard, testBankAccount, category));
            assertFalse(accessTransaction.addTransaction(desc, date, time, amount, testCard, category));
            assertFalse(accessTransaction.updateTransaction(transactions.get(0), desc, date, time, amount, testCard, category));
            assertFalse(accessTransaction.updateDebitTransaction(transactions.get(0), desc, date, time, amount, testDebitCard, testBankAccount, category));
        }
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
     * Test deleting a single transaction, as well as deleting the same transaction
     */
    public void testSingleTransaction() {
        assertTrue(accessTransaction.deleteTransaction(testTransaction1));

        List<Transaction> results = accessTransaction.retrieveTransactions();
        assertFalse(results.contains(testTransaction1));
        assertTrue(results.contains(testTransaction2));

        assertFalse(accessTransaction.deleteTransaction(testTransaction1));
    }

    /**
     * Test deleting multiple transactions
     */
    public void testMultipleTransactions() {
        List<Transaction> results = accessTransaction.retrieveTransactions();
        assertTrue(accessTransaction.deleteTransaction(testTransaction1));
        assertTrue(accessTransaction.deleteTransaction(testTransaction2));
        assertTrue(accessTransaction.deleteTransaction(testTransaction3));

        assertTrue(results.isEmpty());
    }

    /**
     * Tests retrieving transactions on a range containing a single date
     */
    public void testRangeOneDay() {
        List<Transaction> results = accessTransaction.retrieveTransactions(StubDatabase.calcDate(date, -1), StubDatabase.calcDate(date, 1));
        assertTrue(results.contains(testTransaction1));
        assertFalse(results.contains(testTransaction2));
        assertFalse(results.contains(testTransaction3));
    }

    /**
     * Tests retrieving transactions on a range containing two dates
     */
    public void testRangeTwoDays() {
        List<Transaction> results = accessTransaction.retrieveTransactions(StubDatabase.calcDate(date, -2), StubDatabase.calcDate(date, 1));
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

        float testAmountFloat = 19.99f;
        Transaction testTransaction4 = new Transaction(StubDatabase.calcDate(date, -5), testAmountFloat, testDesc, testCard, testBudgetCategory);
        assertFalse(results.contains(testTransaction4));

        results = accessTransaction.retrieveTransactions(StubDatabase.calcDate(date, -500), StubDatabase.calcDate(date, 500));
        assertTrue(results.contains(testTransaction1));
        assertTrue(results.contains(testTransaction2));
        assertTrue(results.contains(testTransaction3));
        assertFalse(results.contains(testTransaction4));
    }

    /**
     * Closes the server connection
     */
    public void tearDown() {
        DataAccessController.closeDataAccess();
    }

}
