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
    private AccessBudgetCategory accessBudgetCategory = null;
    private DataAccess testDB;
    List<BudgetCategory> categories;

    //Testing Data
    private Date testDate = new Date(2020-07-15);
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
        accessBudgetCategory = new AccessBudgetCategory();
        assertTrue(accessBudgetCategory.insertBudgetCategory("entertainment", "50"));
        assertTrue(accessBudgetCategory.insertBudgetCategory("restaurants", "50"));

        categories = accessBudgetCategory.getAllBudgetCategories();
    }

    /**
     * test that the AccessBudgetCategory worked an contains stub data.
     */
    public void testNewAccess() {
        assertNotNull(accessBudgetCategory);
        //test that there are 4 budget categories in the stub DB

        assertEquals(6, categories.size());

        //These are the expected contents of the stub DB
        BudgetCategory rent, groceries, utilities, phoneBill;
        rent = new BudgetCategory("Rent/Mortgage", 500);
        groceries = new BudgetCategory("Groceries", 100);
        utilities = new BudgetCategory("Utilities", 80);
        phoneBill = new BudgetCategory("Phone Bill", 75);

        assertTrue(accessBudgetCategory.findBudgetCategory(rent));
        assertTrue(accessBudgetCategory.findBudgetCategory(groceries));
        assertTrue(accessBudgetCategory.findBudgetCategory(utilities));
        assertTrue(accessBudgetCategory.findBudgetCategory(phoneBill));
    }

    /**
     * Testing that all methods work using valid input
     */
    public void testValidInput(){
        //Add one category by itself
        assertTrue(accessBudgetCategory.insertBudgetCategory("Houseware", "15"));
        assertTrue(accessBudgetCategory.findBudgetCategory(bc3));

        //Other valid inputs
        assertTrue(accessBudgetCategory.insertBudgetCategory("aaaaa", "0000000000123456789"));
        assertTrue(accessBudgetCategory.insertBudgetCategory("bbbbb", "00000000001234567.89"));
        assertTrue(accessBudgetCategory.insertBudgetCategory("ccccc", "123456789"));

        //test that there are now 7 budget categories in DB
        assertEquals(10, categories.size());

        //Update an existing category
        BudgetCategory newBC3 = new BudgetCategory("Furniture", 100);
        assertTrue(accessBudgetCategory.updateBudgetCategory(bc3, "Furniture", "100"));  //returns old BudgetCategory
        assertTrue(accessBudgetCategory.findBudgetCategory(newBC3));    // New BudgetCategory can be found
        assertFalse(accessBudgetCategory.findBudgetCategory(bc3)); // Old BudgetCategory cannot be found

        assertTrue(accessBudgetCategory.updateBudgetCategory(newBC3, "Furniture", "0000000000123456789"));  //returns old BudgetCategory
        assertTrue(accessBudgetCategory.updateBudgetCategory(new BudgetCategory("Furniture", 123456789f), "Furniture", "1234567.89"));  //returns old BudgetCategory
        assertTrue(accessBudgetCategory.updateBudgetCategory(new BudgetCategory("Furniture", 1234567.89f), "Furniture", "00000000001234567.89"));  //returns old BudgetCategory

        //test that there are still 7 budget categories in DB
        assertEquals(10, categories.size());
    }

    /**
     * Testing that all methods work using invalid integer input
     */
    public void testInvalidLimitIntegerInput() {
        assertFalse(accessBudgetCategory.insertBudgetCategory("Houseware", "fifty"));
        assertFalse(accessBudgetCategory.insertBudgetCategory("Houseware", "-50"));
        assertFalse(accessBudgetCategory.insertBudgetCategory("Houseware", "1.234"));
        assertFalse(accessBudgetCategory.insertBudgetCategory("Houseware", "1,23"));
        assertFalse(accessBudgetCategory.insertBudgetCategory("Houseware", "1 23456"));

        //test that there are still only 6 budget categories in DB
        assertEquals(6, categories.size());

        //invalid input for limit (must be integer) while updating
        BudgetCategory newBC2 = new BudgetCategory("Food places", 100);
        assertFalse(accessBudgetCategory.updateBudgetCategory(bc2, "Food places", "hundred"));
        assertFalse(accessBudgetCategory.findBudgetCategory(newBC2));    // New BudgetCategory cannot be found
        assertFalse(accessBudgetCategory.updateBudgetCategory(bc2, "Food places", "-50"));
        assertFalse(accessBudgetCategory.updateBudgetCategory(bc2, "Food places", "1.234"));
        assertFalse(accessBudgetCategory.updateBudgetCategory(bc2, "Food places", "1,23"));
        assertFalse(accessBudgetCategory.updateBudgetCategory(bc2, "Food places", "1 23456"));
        assertTrue(accessBudgetCategory.findBudgetCategory(bc2)); // Old BudgetCategory can still be found

        //test that there are still 6 budget categories in DB
        assertEquals(6, categories.size());
    }

    /**
     * Testing that all methods work using invalid zero input
     */
    public void testInvalidLimitZeroInput(){
        //invalid input for limit (must be integer) while inserting
        assertFalse(accessBudgetCategory.insertBudgetCategory("Houseware", "0"));
        assertFalse(accessBudgetCategory.insertBudgetCategory("Houseware", "00"));
        assertFalse(accessBudgetCategory.insertBudgetCategory("Houseware", "0.0"));
        assertFalse(accessBudgetCategory.insertBudgetCategory("Houseware", "0.00"));

        //test that there are still only 6 budget categories in DB
        assertEquals(6, categories.size());

        //invalid input for limit (must be integer) while updating
        BudgetCategory newBC2 = new BudgetCategory("Food places", 0.0f);
        assertFalse(accessBudgetCategory.updateBudgetCategory(bc2, "Food places", "0"));
        assertFalse(accessBudgetCategory.findBudgetCategory(newBC2));    // New BudgetCategory cannot be found
        assertFalse(accessBudgetCategory.updateBudgetCategory(bc2, "Food places", "00"));
        assertFalse(accessBudgetCategory.updateBudgetCategory(bc2, "Food places", "0.0"));
        assertFalse(accessBudgetCategory.updateBudgetCategory(bc2, "Food places", "0.00"));
        assertTrue(accessBudgetCategory.findBudgetCategory(bc2)); // Old BudgetCategory can still be found

        //test that there are still 6 budget categories in DB
        assertEquals(6, categories.size());
    }

    /**
     * Testing using null input
     */
    public void testInvalidNullInput(){
        //invalid input for limit (must be integer) while updating
        BudgetCategory newBC2 = new BudgetCategory("Food places", 100.0f);
        assertFalse(accessBudgetCategory.updateBudgetCategory(null, "Food places", "100"));
        assertFalse(accessBudgetCategory.findBudgetCategory(newBC2));    // New BudgetCategory cannot be found
        assertTrue(accessBudgetCategory.findBudgetCategory(bc2)); // Old BudgetCategory can still be found

        //test that there are still 6 budget categories in DB
        assertEquals(6, categories.size());
    }

    /**
     * Testing that all methods work using invalid empty input
     */
    public void testInvalidEmptyInputBudgetLimit(){
        //invalid input for limit (must be integer) while inserting
        assertFalse(accessBudgetCategory.insertBudgetCategory("Houseware", ""));
        assertFalse(accessBudgetCategory.insertBudgetCategory("Houseware", " "));
        assertFalse(accessBudgetCategory.insertBudgetCategory("Houseware", "."));
        assertFalse(accessBudgetCategory.insertBudgetCategory("Houseware", null));

        //test that there are still only 6 budget categories in DB
        assertEquals(6, categories.size());

        //invalid input for limit (must be integer) while updating
        assertFalse(accessBudgetCategory.updateBudgetCategory(bc2, "Food places", ""));
        assertFalse(accessBudgetCategory.updateBudgetCategory(bc2, "Food places", " "));
        assertFalse(accessBudgetCategory.updateBudgetCategory(bc2, "Food places", "."));
        assertFalse(accessBudgetCategory.updateBudgetCategory(bc2, "Food places", null));
        assertTrue(accessBudgetCategory.findBudgetCategory(bc2)); // Old BudgetCategory can still be found

        //test that there are still 6 budget categories in DB
        assertEquals(6, categories.size());
    }

    /**
     * Testing that all methods work using invalid empty input
     */
    public void testInvalidEmptyInputBudgetName(){
        //invalid input for name while inserting
        assertFalse(accessBudgetCategory.insertBudgetCategory("", "50"));
        assertFalse(accessBudgetCategory.insertBudgetCategory(" ", "50"));
        assertFalse(accessBudgetCategory.insertBudgetCategory("\n", "50"));
        assertFalse(accessBudgetCategory.insertBudgetCategory(null, "50"));

        //test that there are still only 6 budget categories in DB
        assertEquals(6, categories.size());

        //invalid input for name while updating
        assertFalse(accessBudgetCategory.updateBudgetCategory(bc2, "", "100"));
        assertFalse(accessBudgetCategory.updateBudgetCategory(bc2, " ", "100"));
        assertFalse(accessBudgetCategory.updateBudgetCategory(bc2, "\n", "100"));
        assertFalse(accessBudgetCategory.updateBudgetCategory(bc2, null, "100"));
        assertTrue(accessBudgetCategory.findBudgetCategory(bc2)); // Old BudgetCategory can still be found

        //test that there are still 6 budget categories in DB
        assertEquals(6, categories.size());
    }

    /**
     * testing the findBudgetCategory method in AccessBudgetCategory
     */
    public void testFinding() {
        BudgetCategory failSearch = new BudgetCategory("Balloons", 1000);
        BudgetCategory successSearch = new BudgetCategory("Groceries", 100);
        assertFalse(accessBudgetCategory.findBudgetCategory(failSearch));
        assertTrue(accessBudgetCategory.findBudgetCategory(successSearch));
    }

    /**
     * testing adding a list of BudgetCategory to the stub, as well as adding individually
     */
    public void testAdding(){
        assertTrue(accessBudgetCategory.insertBudgetCategory("Houseware", "15"));
        assertFalse(accessBudgetCategory.insertBudgetCategory("Houseware", "15"));    //No duplicates
        assertTrue(accessBudgetCategory.findBudgetCategory(bc3));
    }

    /**
     * testing switching two versions of BudgetCategory
     */
    public void testUpdate() {
        BudgetCategory oldBC = new BudgetCategory("Electronics", 50);
        accessBudgetCategory.insertBudgetCategory("Electronics", "50");
        assertTrue(accessBudgetCategory.findBudgetCategory(oldBC));

        BudgetCategory newBC = new BudgetCategory("Computer", 500);
        assertTrue(accessBudgetCategory.updateBudgetCategory(oldBC, "Computer", "500"));
        assertTrue(accessBudgetCategory.findBudgetCategory(newBC));
    }

    /**
     * Test calculating budget total for invalid inputs
     */
    public void testCalculateInvalidBudgetCategory() {
        assertEquals(0f, accessBudgetCategory.calculateBudgetCategoryTotal(null, null));
        assertEquals(0f, accessBudgetCategory.calculateBudgetCategoryTotal(new BudgetCategory("test", 20), null));
        assertEquals(0f, accessBudgetCategory.calculateBudgetCategoryTotal(null, Calendar.getInstance()));
    }

    /**
     * Test calculating budget total for no transactions
     */
    public void testCalculateNoTransactionsTotal() {
        Calendar currMonth = Calendar.getInstance();
        currMonth.setTime(testDate);
        //The two budget categories should not have any associated transactions
        assertEquals(0.0f, accessBudgetCategory.calculateBudgetCategoryTotal(bc1, currMonth));
        assertEquals(0.0f, accessBudgetCategory.calculateBudgetCategoryTotal(bc2, currMonth));
    }

    /**
     * Test calculating budget total for a single transaction
     */
    public void testCalculateOneTransactionTotal() {
        Calendar currMonth = Calendar.getInstance();
        currMonth.setTime(testDate);
        testDB.insertTransaction(t1);
        assertEquals(20.45f, accessBudgetCategory.calculateBudgetCategoryTotal(bc1, currMonth));
        assertEquals(0.0f, accessBudgetCategory.calculateBudgetCategoryTotal(bc2, currMonth));
        currMonth.add(Calendar.MONTH, 1);
        assertEquals(0.0f, accessBudgetCategory.calculateBudgetCategoryTotal(bc1, currMonth));

        //From Stub Database
        currMonth.set(2020, 0, 1);
        assertEquals(450.0f, accessBudgetCategory.calculateBudgetCategoryTotal(categories.get(0), currMonth));    // Stub rent budget Category
        assertEquals(50.0f, accessBudgetCategory.calculateBudgetCategoryTotal(categories.get(1), currMonth));     //groceries budget category
        assertEquals(40.0f, accessBudgetCategory.calculateBudgetCategoryTotal(categories.get(2), currMonth));     //utilities budget category
        assertEquals(75.0f, accessBudgetCategory.calculateBudgetCategoryTotal(categories.get(3), currMonth));     //phone bill budget category
    }

    /**
     * Test calculating budget total for multiple transactions
     */
    public void testCalculateMultipleTransactionsTotal() {
        Calendar currMonth = Calendar.getInstance();
        currMonth.setTime(testDate);
        testDB.insertTransaction(t1);
        testDB.insertTransaction(t2);
        assertEquals(60.45f, accessBudgetCategory.calculateBudgetCategoryTotal(bc1, currMonth));
        assertEquals(0f, accessBudgetCategory.calculateBudgetCategoryTotal(bc2, currMonth));
        Transaction t3 = new Transaction(testDate, 50.53f, "Ate burger", testCard, bc2);
        testDB.insertTransaction(t3);
        assertEquals(60.45f, accessBudgetCategory.calculateBudgetCategoryTotal(bc1, currMonth));
        assertEquals(50.53f, accessBudgetCategory.calculateBudgetCategoryTotal(bc2, currMonth));

        //From Stub Database
        currMonth.setTime(testDate);
        StubBudgetCalcHelper(categories, testDate);

        currMonth.set(2020, 0, 1);
        assertEquals(450.0f, accessBudgetCategory.calculateBudgetCategoryTotal(categories.get(0), currMonth));    // Stub rent budget Category
        assertEquals(50.0f, accessBudgetCategory.calculateBudgetCategoryTotal(categories.get(1), currMonth));     //groceries budget category
        assertEquals(40.0f, accessBudgetCategory.calculateBudgetCategoryTotal(categories.get(2), currMonth));     //utilities budget category
        assertEquals(75.0f, accessBudgetCategory.calculateBudgetCategoryTotal(categories.get(3), currMonth));     //phone bill budget category
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
        assertEquals(20.45f, accessBudgetCategory.calculateBudgetCategoryTotal(bc1, currMonth));
        currMonth.add(Calendar.MONTH, 1);
        assertEquals(40f, accessBudgetCategory.calculateBudgetCategoryTotal(bc1, currMonth));

        //From Stub Database
        assertEquals(123.45f, accessBudgetCategory.calculateBudgetCategoryTotal(categories.get(0), currMonth));    // Stub rent budget Category
        assertEquals(123.45f, accessBudgetCategory.calculateBudgetCategoryTotal(categories.get(1), currMonth));     //groceries budget category
        assertEquals(123.45f, accessBudgetCategory.calculateBudgetCategoryTotal(categories.get(2), currMonth));     //utilities budget category
        assertEquals(123.45f, accessBudgetCategory.calculateBudgetCategoryTotal(categories.get(3), currMonth));     //phone bill budget category

    }

    public void StubBudgetCalcHelper(List<BudgetCategory> categories, Date date){
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
        List<Calendar> result = accessBudgetCategory.getActiveMonths(bc1);
        assertTrue(result.isEmpty());
    }

    /**
     * Test getting active months with a single transaction
     */
    public void testSingleActiveMonth() {
        testDB.insertTransaction(t1);
        List<Calendar> result = accessBudgetCategory.getActiveMonths(bc1);
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
        List<Calendar> result = accessBudgetCategory.getActiveMonths(bc1);
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
        result = accessBudgetCategory.getActiveMonths(bc1);
        assertEquals(2, result.size());
        assertTrue(result.contains(calendar));
    }

    /**
     * Test getting active months with an invalid input
     */
    public void testInvalidActiveMonths() {
        try {
            accessBudgetCategory.getActiveMonths(null);
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