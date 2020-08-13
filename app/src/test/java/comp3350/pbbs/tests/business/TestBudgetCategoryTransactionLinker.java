package comp3350.pbbs.tests.business;

import junit.framework.TestCase;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import comp3350.pbbs.business.AccessBudgetCategory;
import comp3350.pbbs.business.BudgetCategoryTransactionLinker;
import comp3350.pbbs.objects.BudgetCategory;
import comp3350.pbbs.objects.Card;
import comp3350.pbbs.objects.Transaction;
import comp3350.pbbs.persistence.DataAccessController;
import comp3350.pbbs.persistence.DataAccessI;
import comp3350.pbbs.tests.persistence.StubDatabase;

public class TestBudgetCategoryTransactionLinker extends TestCase {
    private DataAccessI testDB;
    List<BudgetCategory> categories;
    private BudgetCategoryTransactionLinker linker = null;
    private Date testDate = new Date(2020 - 07 - 15);
    private BudgetCategory bc1 = new BudgetCategory("entertainment", 50);
    private BudgetCategory bc2 = new BudgetCategory("restaurants", 50);
    private Card testCard = new Card("Amex", "1000100010001000", "Alan Alfred", 6, 2022, 27);
    private Transaction t1 = new Transaction(testDate, 20.45f, "Watched a movie", testCard, bc1);
    private Transaction t2 = new Transaction(testDate, 40, "Bought a video game", testCard, bc1);

    public void setUp() {
        testDB = DataAccessController.createDataAccess(new StubDatabase("populateTest"));
        linker = new BudgetCategoryTransactionLinker();
        AccessBudgetCategory accessBudgetCategory = new AccessBudgetCategory();
        assertTrue(accessBudgetCategory.insertBudgetCategory("entertainment", "50"));
        assertTrue(accessBudgetCategory.insertBudgetCategory("restaurants", "50"));

        categories = testDB.getBudgets();
    }

    /**
     * Test calculating budget total for invalid inputs
     */
    public void testCalculateInvalidBudgetCategory() {
        assertEquals(0f, linker.calculateBudgetCategoryTotal(null, null));
        assertEquals(0f, linker.calculateBudgetCategoryTotal(new BudgetCategory("test", 20), null));
        assertEquals(0f, linker.calculateBudgetCategoryTotal(null, Calendar.getInstance()));
    }

    /**
     * Test calculating budget total for no transactions
     */
    public void testCalculateNoTransactionsTotal() {
        Calendar currMonth = Calendar.getInstance();
        currMonth.setTime(testDate);
        //The two budget categories should not have any associated transactions
        assertEquals(0.0f, linker.calculateBudgetCategoryTotal(bc1, currMonth));
        assertEquals(0.0f, linker.calculateBudgetCategoryTotal(bc2, currMonth));
    }

    /**
     * Test calculating budget total for a single transaction
     */
    public void testCalculateOneTransactionTotal() {
        Calendar currMonth = Calendar.getInstance();
        currMonth.setTime(testDate);
        testDB.insertTransaction(t1);
        assertEquals(20.45f, linker.calculateBudgetCategoryTotal(bc1, currMonth));
        assertEquals(0.0f, linker.calculateBudgetCategoryTotal(bc2, currMonth));
        currMonth.add(Calendar.MONTH, 1);
        assertEquals(0.0f, linker.calculateBudgetCategoryTotal(bc1, currMonth));

        //From Stub Database
        currMonth.set(2020, 0, 1);
        assertEquals(450.0f, linker.calculateBudgetCategoryTotal(categories.get(0), currMonth));    // Stub rent budget Category
        assertEquals(50.0f, linker.calculateBudgetCategoryTotal(categories.get(1), currMonth));     //groceries budget category
        assertEquals(40.0f, linker.calculateBudgetCategoryTotal(categories.get(2), currMonth));     //utilities budget category
        assertEquals(75.0f, linker.calculateBudgetCategoryTotal(categories.get(3), currMonth));     //phone bill budget category
    }

    /**
     * Test calculating budget total for multiple transactions
     */
    public void testCalculateMultipleTransactionsTotal() {
        Calendar currMonth = Calendar.getInstance();
        currMonth.setTime(testDate);
        testDB.insertTransaction(t1);
        testDB.insertTransaction(t2);
        assertEquals(60.45f, linker.calculateBudgetCategoryTotal(bc1, currMonth));
        assertEquals(0f, linker.calculateBudgetCategoryTotal(bc2, currMonth));
        Transaction t3 = new Transaction(testDate, 50.53f, "Ate burger", testCard, bc2);
        testDB.insertTransaction(t3);
        assertEquals(60.45f, linker.calculateBudgetCategoryTotal(bc1, currMonth));
        assertEquals(50.53f, linker.calculateBudgetCategoryTotal(bc2, currMonth));

        //From Stub Database
        currMonth.setTime(testDate);
        StubBudgetCalcHelper(categories, testDate);

        currMonth.set(2020, 0, 1);
        assertEquals(450.0f, linker.calculateBudgetCategoryTotal(categories.get(0), currMonth));    // Stub rent budget Category
        assertEquals(50.0f, linker.calculateBudgetCategoryTotal(categories.get(1), currMonth));     //groceries budget category
        assertEquals(40.0f, linker.calculateBudgetCategoryTotal(categories.get(2), currMonth));     //utilities budget category
        assertEquals(75.0f, linker.calculateBudgetCategoryTotal(categories.get(3), currMonth));     //phone bill budget category
    }

    /**
     * Test calculating budget category totals for different transaction months
     */
    public void testCalculateTransactionDifferentMonths() {
        Calendar currMonth = Calendar.getInstance();
        currMonth.setTime(testDate);
        currMonth.add(Calendar.MONTH, 1);
        Transaction t3 = new Transaction(currMonth.getTime(), 40, "Bought a video game", testCard, bc1);
        testDB.insertTransaction(t1);
        testDB.insertTransaction(t3);
        StubBudgetCalcHelper(categories, currMonth.getTime());
        currMonth.setTime(testDate);
        assertEquals(20.45f, linker.calculateBudgetCategoryTotal(bc1, currMonth));
        currMonth.add(Calendar.MONTH, 1);
        assertEquals(40f, linker.calculateBudgetCategoryTotal(bc1, currMonth));

        //From Stub Database
        assertEquals(123.45f, linker.calculateBudgetCategoryTotal(categories.get(0), currMonth));    // Stub rent budget Category
        assertEquals(123.45f, linker.calculateBudgetCategoryTotal(categories.get(1), currMonth));     //groceries budget category
        assertEquals(123.45f, linker.calculateBudgetCategoryTotal(categories.get(2), currMonth));     //utilities budget category
        assertEquals(123.45f, linker.calculateBudgetCategoryTotal(categories.get(3), currMonth));     //phone bill budget category

    }

    public void StubBudgetCalcHelper(List<BudgetCategory> categories, Date date) {
        Transaction t4 = new Transaction(date, 123.45f, "Extra Rent", testCard, categories.get(0));
        testDB.insertTransaction(t4);
        Transaction t5 = new Transaction(date, 123.45f, "Extra Groceries", testCard, categories.get(1));
        testDB.insertTransaction(t5);
        Transaction t6 = new Transaction(date, 123.45f, "Extra utilities", testCard, categories.get(2));
        testDB.insertTransaction(t6);
        Transaction t7 = new Transaction(date, 123.45f, "Extra phone bill", testCard, categories.get(3));
        testDB.insertTransaction(t7);
    }

    /**
     * Test getting active months with no transactions
     */
    public void testEmptyActiveMonths() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(testDate);
        BudgetCategory bc1 = new BudgetCategory("test", 20);
        List<Calendar> result = linker.getActiveMonths(bc1);
        assertTrue(result.isEmpty());
    }

    /**
     * Test getting active months with a single transaction
     */
    public void testSingleActiveMonth() {
        testDB.insertTransaction(t1);
        List<Calendar> result = linker.getActiveMonths(bc1);
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
        testDB.insertTransaction(t1);
        testDB.insertTransaction(t2);
        List<Calendar> result = linker.getActiveMonths(bc1);
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
        Transaction t3 = new Transaction(calendar.getTime(), 20, "Bought groceries.", testCard, bc1);
        testDB.insertTransaction(t3);
        result = linker.getActiveMonths(bc1);
        assertEquals(2, result.size());
        assertTrue(result.contains(calendar));
    }

    /**
     * Test getting active months with an invalid input
     */
    public void testInvalidActiveMonths() {
        try {
            linker.getActiveMonths(null);
            fail("Expected NullPointerException.");
        } catch (NullPointerException ignored) {

        }
    }

    /**
     * This method closes StubDatabase
     */
    public void tearDown() {
        DataAccessController.closeDataAccess();
    }
}
