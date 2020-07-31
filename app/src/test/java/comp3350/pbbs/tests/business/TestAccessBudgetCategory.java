package comp3350.pbbs.tests.business;

import junit.framework.TestCase;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import comp3350.pbbs.application.Services;
import comp3350.pbbs.business.AccessBudgetCategory;
import comp3350.pbbs.objects.BudgetCategory;
import comp3350.pbbs.objects.Card;
import comp3350.pbbs.objects.Transaction;
import comp3350.pbbs.persistence.DataAccess;
import comp3350.pbbs.tests.persistence.StubDatabase;

/**
 * TestAccessBudgetCategory
 * Group4
 * PBBS
 *
 * This class tests AccessBudgetCategory class
 */
public class TestAccessBudgetCategory extends TestCase {
    private AccessBudgetCategory testAccess = null;
    private DataAccess testDB;
    List<BudgetCategory> categories;

    //Testing Data
    private Date testDate = new Date(2020 - 07 - 15);
    private BudgetCategory bc1 = new BudgetCategory("entertainment", 50);
    private BudgetCategory bc2 = new BudgetCategory("restaurants", 50);
    private BudgetCategory bc3 = new BudgetCategory("Houseware", 15);
    private Card testCard = new Card("Amex", "1000100010001000", "Alan Alfred", 6, 2022, 27);
    private Transaction t1 = new Transaction(testDate, 20.45f, "Watched a movie", testCard, bc1);
    private Transaction t2 = new Transaction(testDate, 40, "Bought a video game", testCard, bc1);

    /**
     * creating StubDatabase and AccessBudgetCategory
     */
    public void setUp() {
        testDB = Services.createDataAccess(new StubDatabase("populateTest"));
        testAccess = new AccessBudgetCategory();
        assertTrue(testAccess.insertBudgetCategory("entertainment", "50"));
        assertTrue(testAccess.insertBudgetCategory("restaurants", "50"));

        categories = testAccess.getAllBudgetCategories();
    }

    /**
     * test that the AccessBudgetCategory worked an contains stub data.
     */
    public void testNewAccess() {
        assertNotNull(testAccess);
        //test that there are 4 budget categories in the stub DB

        assertEquals(6, categories.size());

        //These are the expected contents of the stub DB
        BudgetCategory rent, groceries, utilities, phoneBill;
        rent = new BudgetCategory("Rent/Mortgage", 500);
        groceries = new BudgetCategory("Groceries", 100);
        utilities = new BudgetCategory("Utilities", 80);
        phoneBill = new BudgetCategory("Phone Bill", 75);

        assertTrue(testAccess.findBudgetCategory(rent));
        assertTrue(testAccess.findBudgetCategory(groceries));
        assertTrue(testAccess.findBudgetCategory(utilities));
        assertTrue(testAccess.findBudgetCategory(phoneBill));
    }

    /**
     * Testing that all methods work using valid input
     */
    public void testValidInput() {
        //Add one category by itself
        assertTrue(testAccess.insertBudgetCategory("Houseware", "15"));
        assertTrue(testAccess.findBudgetCategory(bc3));

        //Other valid inputs
        assertTrue(testAccess.insertBudgetCategory("aaaaa", "0000000000123456789"));
        assertTrue(testAccess.insertBudgetCategory("bbbbb", "00000000001234567.89"));
        assertTrue(testAccess.insertBudgetCategory("ccccc", "123456789"));

        //test that there are now 7 budget categories in DB
        assertEquals(10, categories.size());

        //Update an existing category
        BudgetCategory newBC3 = new BudgetCategory("Furniture", 100);
        assertTrue(testAccess.updateBudgetCategory(bc3, "Furniture", "100"));  //returns old BudgetCategory
        assertTrue(testAccess.findBudgetCategory(newBC3));    // New BudgetCategory can be found
        assertFalse(testAccess.findBudgetCategory(bc3)); // Old BudgetCategory cannot be found

        assertTrue(testAccess.updateBudgetCategory(newBC3, "Furniture", "0000000000123456789"));  //returns old BudgetCategory
        assertTrue(testAccess.updateBudgetCategory(new BudgetCategory("Furniture", 123456789f), "Furniture", "1234567.89"));  //returns old BudgetCategory
        assertTrue(testAccess.updateBudgetCategory(new BudgetCategory("Furniture", 1234567.89f), "Furniture", "00000000001234567.89"));  //returns old BudgetCategory

        //test that there are still 7 budget categories in DB
        assertEquals(10, categories.size());
    }

    /**
     * Testing that all methods work using invalid integer input
     */
    public void testInvalidLimitIntegerInput() {
        assertFalse(testAccess.insertBudgetCategory("Houseware", "fifty"));
        assertFalse(testAccess.insertBudgetCategory("Houseware", "-50"));
        assertFalse(testAccess.insertBudgetCategory("Houseware", "1.234"));
        assertFalse(testAccess.insertBudgetCategory("Houseware", "1,23"));
        assertFalse(testAccess.insertBudgetCategory("Houseware", "1 23456"));

        //test that there are still only 6 budget categories in DB
        assertEquals(6, categories.size());

        //invalid input for limit (must be integer) while updating
        BudgetCategory newBC2 = new BudgetCategory("Food places", 100);
        assertFalse(testAccess.updateBudgetCategory(bc2, "Food places", "hundred"));
        assertFalse(testAccess.findBudgetCategory(newBC2));    // New BudgetCategory cannot be found
        assertFalse(testAccess.updateBudgetCategory(bc2, "Food places", "-50"));
        assertFalse(testAccess.updateBudgetCategory(bc2, "Food places", "1.234"));
        assertFalse(testAccess.updateBudgetCategory(bc2, "Food places", "1,23"));
        assertFalse(testAccess.updateBudgetCategory(bc2, "Food places", "1 23456"));
        assertTrue(testAccess.findBudgetCategory(bc2)); // Old BudgetCategory can still be found

        //test that there are still 6 budget categories in DB
        assertEquals(6, categories.size());
    }

    /**
     * Testing that all methods work using invalid zero input
     */
    public void testInvalidLimitZeroInput() {
        //invalid input for limit (must be integer) while inserting
        assertFalse(testAccess.insertBudgetCategory("Houseware", "0"));
        assertFalse(testAccess.insertBudgetCategory("Houseware", "00"));
        assertFalse(testAccess.insertBudgetCategory("Houseware", "0.0"));
        assertFalse(testAccess.insertBudgetCategory("Houseware", "0.00"));

        //test that there are still only 6 budget categories in DB
        assertEquals(6, categories.size());

        //invalid input for limit (must be integer) while updating
        BudgetCategory newBC2 = new BudgetCategory("Food places", 0.0f);
        assertFalse(testAccess.updateBudgetCategory(bc2, "Food places", "0"));
        assertFalse(testAccess.findBudgetCategory(newBC2));    // New BudgetCategory cannot be found
        assertFalse(testAccess.updateBudgetCategory(bc2, "Food places", "00"));
        assertFalse(testAccess.updateBudgetCategory(bc2, "Food places", "0.0"));
        assertFalse(testAccess.updateBudgetCategory(bc2, "Food places", "0.00"));
        assertTrue(testAccess.findBudgetCategory(bc2)); // Old BudgetCategory can still be found

        //test that there are still 6 budget categories in DB
        assertEquals(6, categories.size());
    }

    /**
     * Testing using null input
     */
    public void testInvalidNullInput() {
        //invalid input for limit (must be integer) while updating
        BudgetCategory newBC2 = new BudgetCategory("Food places", 100.0f);
        assertFalse(testAccess.updateBudgetCategory(null, "Food places", "100"));
        assertFalse(testAccess.findBudgetCategory(newBC2));    // New BudgetCategory cannot be found
        assertTrue(testAccess.findBudgetCategory(bc2)); // Old BudgetCategory can still be found

        //test that there are still 6 budget categories in DB
        assertEquals(6, categories.size());
    }

    /**
     * Testing that all methods work using invalid empty input
     */
    public void testInvalidEmptyInputBudgetLimit() {
        //invalid input for limit (must be integer) while inserting
        assertFalse(testAccess.insertBudgetCategory("Houseware", ""));
        assertFalse(testAccess.insertBudgetCategory("Houseware", " "));
        assertFalse(testAccess.insertBudgetCategory("Houseware", "."));
        assertFalse(testAccess.insertBudgetCategory("Houseware", null));

        //test that there are still only 6 budget categories in DB
        assertEquals(6, categories.size());

        //invalid input for limit (must be integer) while updating
        assertFalse(testAccess.updateBudgetCategory(bc2, "Food places", ""));
        assertFalse(testAccess.updateBudgetCategory(bc2, "Food places", " "));
        assertFalse(testAccess.updateBudgetCategory(bc2, "Food places", "."));
        assertFalse(testAccess.updateBudgetCategory(bc2, "Food places", null));
        assertTrue(testAccess.findBudgetCategory(bc2)); // Old BudgetCategory can still be found

        //test that there are still 6 budget categories in DB
        assertEquals(6, categories.size());
    }

    /**
     * Testing that all methods work using invalid empty input
     */
    public void testInvalidEmptyInputBudgetName() {
        //invalid input for name while inserting
        assertFalse(testAccess.insertBudgetCategory("", "50"));
        assertFalse(testAccess.insertBudgetCategory(" ", "50"));
        assertFalse(testAccess.insertBudgetCategory("\n", "50"));
        assertFalse(testAccess.insertBudgetCategory(null, "50"));

        //test that there are still only 6 budget categories in DB
        assertEquals(6, categories.size());

        //invalid input for name while updating
        assertFalse(testAccess.updateBudgetCategory(bc2, "", "100"));
        assertFalse(testAccess.updateBudgetCategory(bc2, " ", "100"));
        assertFalse(testAccess.updateBudgetCategory(bc2, "\n", "100"));
        assertFalse(testAccess.updateBudgetCategory(bc2, null, "100"));
        assertTrue(testAccess.findBudgetCategory(bc2)); // Old BudgetCategory can still be found

        //test that there are still 6 budget categories in DB
        assertEquals(6, categories.size());
    }

    /**
     * testing the findBudgetCategory method in AccessBudgetCategory
     */
    public void testFinding() {
        BudgetCategory failSearch = new BudgetCategory("Balloons", 1000);
        BudgetCategory successSearch = new BudgetCategory("Groceries", 100);
        assertFalse(testAccess.findBudgetCategory(failSearch));
        assertTrue(testAccess.findBudgetCategory(successSearch));
    }

    /**
     * testing adding a list of BudgetCategory to the stub, as well as adding individually
     */
    public void testAdding() {
        assertTrue(testAccess.insertBudgetCategory("Houseware", "15"));
        assertFalse(testAccess.insertBudgetCategory("Houseware", "15"));    //No duplicates
        assertTrue(testAccess.findBudgetCategory(bc3));
    }

    /**
     * testing switching two versions of BudgetCategory
     */
    public void testUpdate() {
        BudgetCategory oldBC = new BudgetCategory("Electronics", 50);
        testAccess.insertBudgetCategory("Electronics", "50");
        assertTrue(testAccess.findBudgetCategory(oldBC));

        BudgetCategory newBC = new BudgetCategory("Computer", 500);
        assertTrue(testAccess.updateBudgetCategory(oldBC, "Computer", "500"));
        assertTrue(testAccess.findBudgetCategory(newBC));
    }

    /**
     * Test calculating budget total for invalid inputs
     */
    public void testCalculateInvalidBudgetCategory() {
        assertEquals(0f, testAccess.calculateBudgetCategoryTotal(null, null));
        assertEquals(0f, testAccess.calculateBudgetCategoryTotal(new BudgetCategory("test", 20), null));
        assertEquals(0f, testAccess.calculateBudgetCategoryTotal(null, Calendar.getInstance()));
    }

    /**
     * Test calculating budget total for no transactions
     */
    public void testCalculateNoTransactionsTotal() {
        Calendar currMonth = Calendar.getInstance();
        currMonth.setTime(testDate);
        //The two budget categories should not have any associated transactions
        assertEquals(0.0f, testAccess.calculateBudgetCategoryTotal(bc1, currMonth));
        assertEquals(0.0f, testAccess.calculateBudgetCategoryTotal(bc2, currMonth));
    }

    /**
     * Test calculating budget total for a single transaction
     */
    public void testCalculateOneTransactionTotal() {
        Calendar currMonth = Calendar.getInstance();
        currMonth.setTime(testDate);
        testDB.insertTransaction(t1);
        assertEquals(20.45f, testAccess.calculateBudgetCategoryTotal(bc1, currMonth));
        assertEquals(0.0f, testAccess.calculateBudgetCategoryTotal(bc2, currMonth));
        currMonth.add(Calendar.MONTH, 1);
        assertEquals(0.0f, testAccess.calculateBudgetCategoryTotal(bc1, currMonth));

        //From Stub Database
        currMonth.set(2020, 0, 1);
        assertEquals(450.0f, testAccess.calculateBudgetCategoryTotal(categories.get(0), currMonth));    // Stub rent budget Category
        assertEquals(50.0f, testAccess.calculateBudgetCategoryTotal(categories.get(1), currMonth));     //groceries budget category
        assertEquals(40.0f, testAccess.calculateBudgetCategoryTotal(categories.get(2), currMonth));     //utilities budget category
        assertEquals(75.0f, testAccess.calculateBudgetCategoryTotal(categories.get(3), currMonth));     //phone bill budget category
    }

    /**
     * Test calculating budget total for multiple transactions
     */
    public void testCalculateMultipleTransactionsTotal() {
        Calendar currMonth = Calendar.getInstance();
        currMonth.setTime(testDate);
        testDB.insertTransaction(t1);
        testDB.insertTransaction(t2);
        assertEquals(60.45f, testAccess.calculateBudgetCategoryTotal(bc1, currMonth));
        assertEquals(0f, testAccess.calculateBudgetCategoryTotal(bc2, currMonth));
        Transaction t3 = new Transaction(testDate, 50.53f, "Ate burger", testCard, bc2);
        testDB.insertTransaction(t3);
        assertEquals(60.45f, testAccess.calculateBudgetCategoryTotal(bc1, currMonth));
        assertEquals(50.53f, testAccess.calculateBudgetCategoryTotal(bc2, currMonth));

        //From Stub Database
        currMonth.setTime(testDate);
        StubBudgetCalcHelper(categories, testDate);

        currMonth.set(2020, 0, 1);
        assertEquals(450.0f, testAccess.calculateBudgetCategoryTotal(categories.get(0), currMonth));    // Stub rent budget Category
        assertEquals(50.0f, testAccess.calculateBudgetCategoryTotal(categories.get(1), currMonth));     //groceries budget category
        assertEquals(40.0f, testAccess.calculateBudgetCategoryTotal(categories.get(2), currMonth));     //utilities budget category
        assertEquals(75.0f, testAccess.calculateBudgetCategoryTotal(categories.get(3), currMonth));     //phone bill budget category
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
        assertEquals(20.45f, testAccess.calculateBudgetCategoryTotal(bc1, currMonth));
        currMonth.add(Calendar.MONTH, 1);
        assertEquals(40f, testAccess.calculateBudgetCategoryTotal(bc1, currMonth));

        //From Stub Database
        assertEquals(123.45f, testAccess.calculateBudgetCategoryTotal(categories.get(0), currMonth));    // Stub rent budget Category
        assertEquals(123.45f, testAccess.calculateBudgetCategoryTotal(categories.get(1), currMonth));     //groceries budget category
        assertEquals(123.45f, testAccess.calculateBudgetCategoryTotal(categories.get(2), currMonth));     //utilities budget category
        assertEquals(123.45f, testAccess.calculateBudgetCategoryTotal(categories.get(3), currMonth));     //phone bill budget category

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
        List<Calendar> result = testAccess.getActiveMonths(bc1);
        assertTrue(result.isEmpty());
    }

    /**
     * Test getting active months with a single transaction
     */
    public void testSingleActiveMonth() {
        testDB.insertTransaction(t1);
        List<Calendar> result = testAccess.getActiveMonths(bc1);
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
        List<Calendar> result = testAccess.getActiveMonths(bc1);
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
        result = testAccess.getActiveMonths(bc1);
        assertEquals(2, result.size());
        assertTrue(result.contains(calendar));
    }

    /**
     * Test getting active months with an invalid input
     */
    public void testInvalidActiveMonths() {
        try {
            testAccess.getActiveMonths(null);
            fail("Expected NullPointerException.");
        } catch (NullPointerException ignored) {

        }
    }

    /**
     * This method closes StubDatabase
     */
    public void tearDown() {
        Services.closeDataAccess();
    }
}