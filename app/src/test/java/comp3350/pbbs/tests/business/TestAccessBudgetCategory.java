package comp3350.pbbs.tests.business;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import comp3350.pbbs.application.Services;
import comp3350.pbbs.business.AccessBudgetCategory;
import comp3350.pbbs.objects.BudgetCategory;
import comp3350.pbbs.objects.Cards.CreditCard;
import comp3350.pbbs.objects.Transaction;
import comp3350.pbbs.persistence.StubDatabase;

/**
 * TestAccessBudgetCategory
 * Group4
 * PBBS
 *
 * This class tests AccessBudgetCategory class
 */
public class TestAccessBudgetCategory extends TestCase {
    private AccessBudgetCategory testAccess = null;
    private ArrayList<BudgetCategory> newBudgetCategories = new ArrayList<BudgetCategory>();
    private BudgetCategory bc1 = new BudgetCategory("entertainment", 50);
    private BudgetCategory bc2 = new BudgetCategory("restaurants", 50);
    private BudgetCategory bc3 = new BudgetCategory("Houseware", 15);

    /**
     * creating StubDatabase and AccessBudgetCategory
     */
    public void setUp() {
        Services.createDataAccess("TBCU");
        testAccess = new AccessBudgetCategory();

        newBudgetCategories.add(bc1);
        newBudgetCategories.add(bc2);
        testAccess.addBudgetCategories(newBudgetCategories);
    }

    /**
     * test that the AccessBudgetCategory worked an contains stub data.
     */
    public void testNewAccess() {
        assertNotNull(testAccess);
        //test that there are 4 budget categories in the stub DB
        assertEquals(6, testAccess.getAllBudgetCategories().size());

        //These are the expected contents of the stub DB
        BudgetCategory rent, groceries, utilities, phoneBill;
        rent = new BudgetCategory("Rent/Mortgage", 500);
        groceries = new BudgetCategory("Groceries", 100);
        utilities = new BudgetCategory("Utilities", 80);
        phoneBill = new BudgetCategory("Phone Bill", 75);

        assertNotNull(testAccess.findBudgetCategory(rent));
        assertNotNull(testAccess.findBudgetCategory(groceries));
        assertNotNull(testAccess.findBudgetCategory(utilities));
        assertNotNull(testAccess.findBudgetCategory(phoneBill));
    }

    /**
     * Testing that all methods work using valid input
     */
    public void testValidInput(){
        //Add one category by itself
        assertTrue(testAccess.insertBudgetCategory("Houseware", "15"));
        assertEquals(bc3, testAccess.findBudgetCategory(bc3));

        //test that there are now 7 budget categories in DB
        assertEquals(7, testAccess.getAllBudgetCategories().size());

        //Update an existing category
        BudgetCategory newBC3 = new BudgetCategory("Furniture", 100);
        assertEquals(bc3, testAccess.updateBudgetCategory(bc3, "Furniture", "100"));  //returns old BudgetCategory
        assertEquals(newBC3, testAccess.findBudgetCategory(newBC3));    // New BudgetCategory can be found
        assertNull(testAccess.findBudgetCategory(bc3)); // Old BudgetCategory cannot be found

        //test that there are still 7 budget categories in DB
        assertEquals(7, testAccess.getAllBudgetCategories().size());

        //Delete a category
        assertEquals(newBC3, testAccess.deleteBudgetCategory(newBC3));
        assertNull(testAccess.findBudgetCategory(newBC3));

        //test that there are now 6 budget categories in DB
        assertEquals(6, testAccess.getAllBudgetCategories().size());
    }

    /**
     * Testing that all methods work using invalid integer input
     */
    public void testInvalidLimitIntegerInput() {
        assertFalse(testAccess.insertBudgetCategory("Houseware", "fifty"));

        //test that there are still only 6 budget categories in DB
        assertEquals(6, testAccess.getAllBudgetCategories().size());

        //invalid input for limit (must be integer) while updating
        BudgetCategory newBC2 = new BudgetCategory("Food places", 100);
        assertNull(testAccess.updateBudgetCategory(bc2, "Food places", "hundred"));
        assertNull(testAccess.findBudgetCategory(newBC2));    // New BudgetCategory cannot be found
        assertEquals(bc2, testAccess.findBudgetCategory(bc2)); // Old BudgetCategory can still be found

        //test that there are still 6 budget categories in DB
        assertEquals(6, testAccess.getAllBudgetCategories().size());
    }

    /**
     * Testing that all methods work using invalid zero input
     */
    public void testInvalidLimitZeroInput(){
        //invalid input for limit (must be integer) while inserting
        assertFalse(testAccess.insertBudgetCategory("Houseware", "0"));

        //test that there are still only 6 budget categories in DB
        assertEquals(6, testAccess.getAllBudgetCategories().size());

        //invalid input for limit (must be integer) while updating
        BudgetCategory newBC2 = new BudgetCategory("Food places", 0);
        assertNull(testAccess.updateBudgetCategory(bc2, "Food places", "0"));
        assertNull(testAccess.findBudgetCategory(newBC2));    // New BudgetCategory cannot be found
        assertEquals(bc2, testAccess.findBudgetCategory(bc2)); // Old BudgetCategory can still be found

        //test that there are still 6 budget categories in DB
        assertEquals(6, testAccess.getAllBudgetCategories().size());
    }

    /**
     * Testing that all methods work using invalid empty input
     */
    public void testInvalidEmptyInput1(){
        //invalid input for limit (must be integer) while inserting
        assertFalse(testAccess.insertBudgetCategory("Houseware", ""));

        //test that there are still only 6 budget categories in DB
        assertEquals(6, testAccess.getAllBudgetCategories().size());

        //invalid input for limit (must be integer) while updating
        BudgetCategory newBC2 = new BudgetCategory("Food places", 100);
        assertNull(testAccess.updateBudgetCategory(bc2, "Food places", ""));
        assertNull(testAccess.findBudgetCategory(newBC2));    // New BudgetCategory cannot be found
        assertEquals(bc2, testAccess.findBudgetCategory(bc2)); // Old BudgetCategory can still be found

        //test that there are still 6 budget categories in DB
        assertEquals(6, testAccess.getAllBudgetCategories().size());
    }

    /**
     * Testing that all methods work using invalid empty input
     */
    public void testInvalidEmptyInput2(){
        //invalid input for limit (must be integer) while inserting
        assertFalse(testAccess.insertBudgetCategory("", "50"));

        //test that there are still only 6 budget categories in DB
        assertEquals(6, testAccess.getAllBudgetCategories().size());

        //invalid input for limit (must be integer) while updating
        BudgetCategory newBC2 = new BudgetCategory("Food places", 100);
        assertNull(testAccess.updateBudgetCategory(bc2, "", "100"));
        assertNull(testAccess.findBudgetCategory(newBC2));    // New BudgetCategory cannot be found
        assertEquals(bc2, testAccess.findBudgetCategory(bc2)); // Old BudgetCategory can still be found

        //test that there are still 6 budget categories in DB
        assertEquals(6, testAccess.getAllBudgetCategories().size());
    }

    public boolean updateHelper(String newName, int newLimit, boolean pass){
        boolean success = false;
        BudgetCategory newBC2 = new BudgetCategory(newName, newLimit);
        success = testAccess.updateBudgetCategory(bc2, newName, ""+newLimit) != null;
        return success;
    }

    /**
     * testing the findBudgetCategory method in AccessBudgetCategory
     */
    public void testFinding() {
        BudgetCategory failSearch = new BudgetCategory("Balloons", 1000);
        BudgetCategory successSearch = new BudgetCategory("Groceries", 100);
        assertNull(testAccess.findBudgetCategory(failSearch));
        assertEquals(successSearch, testAccess.findBudgetCategory(successSearch));
    }

    /**
     * testing adding a list of BudgetCategory to the stub, as well as adding individually
     */
    public void testAdding(){
        assertTrue(testAccess.addBudgetCategories(newBudgetCategories));
        //Test that addition was success and that the findBudgetCategory method works
        assertEquals(bc1, testAccess.findBudgetCategory(bc1));
        assertEquals(bc2, testAccess.findBudgetCategory(bc2));

        assertTrue(testAccess.insertBudgetCategory("Houseware", "15"));
        assertNotNull(testAccess.findBudgetCategory(bc3));
    }

    /**
     * testing switching two versions of BudgetCategory
     */
    public void testUpdate() {
        BudgetCategory oldBC = new BudgetCategory("Electronics", 50);
        testAccess.insertBudgetCategory("Electronics", "50");
        assertNotNull(testAccess.findBudgetCategory(oldBC));

        BudgetCategory newBC = new BudgetCategory("Computer", 500);
        assertNotNull(testAccess.updateBudgetCategory(oldBC, "Computer", "500"));
        assertNotNull(testAccess.findBudgetCategory(newBC));
    }

    /**
     * test removing a BudgetCategory from the stub.
     */
    public void testDelete() {
        BudgetCategory testBC = new BudgetCategory("Furniture", 100);
        testAccess.insertBudgetCategory("Furniture", "100");
        assertNotNull(testAccess.findBudgetCategory(testBC));
        assertNotNull(testAccess.deleteBudgetCategory(testBC));
        assertNull(testAccess.findBudgetCategory(testBC));
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
        currMonth.setTime(new Date());
        //The two budget categories should not have any associated transactions
        assertEquals(0.0f, testAccess.calculateBudgetCategoryTotal(bc1, currMonth));
        assertEquals(0.0f, testAccess.calculateBudgetCategoryTotal(bc2, currMonth));
//        CreditCard testCard = new CreditCard("Amex", "1000100010001000", "Alan Alfred", 6, 2022, 27);   // never used
    }

    /**
     * Test calculating budget total for a single transaction
     */
    public void testCalculateOneTransactionTotal() {
        CreditCard testCard = new CreditCard("Amex", "1000100010001000", "Alan Alfred", 6, 2022, 27);
        Transaction t1 = new Transaction(new Date(), 20, "Played at the arcade", testCard, bc1);
        StubDatabase db = Services.getDataAccess("TBCU");
        Calendar currMonth = Calendar.getInstance();
        currMonth.setTime(new Date());
        db.insertTransaction(t1);
        assertEquals(20.0f, testAccess.calculateBudgetCategoryTotal(bc1, currMonth));
        assertEquals(0.0f, testAccess.calculateBudgetCategoryTotal(bc2, currMonth));
        currMonth.add(Calendar.MONTH, 1);
        assertEquals(0.0f, testAccess.calculateBudgetCategoryTotal(bc1, currMonth));
    }

    /**
     * Test calculating budget total for multiple transactions
     */
    public void testCalculateMultipleTransactionsTotal() {
        CreditCard testCard = new CreditCard("Amex", "1000100010001000", "Alan Alfred", 6, 2022, 27);
        Transaction t1 = new Transaction(new Date(), 20, "Watched a movie", testCard, bc1);
        Transaction t2 = new Transaction(new Date(), 40, "Bought a video game", testCard, bc1);
        StubDatabase db = Services.getDataAccess("TBCU");
        Calendar currMonth = Calendar.getInstance();
        currMonth.setTime(new Date());
        db.insertTransaction(t1);
        db.insertTransaction(t2);
        assertEquals(60f, testAccess.calculateBudgetCategoryTotal(bc1, currMonth));
        assertEquals(0f, testAccess.calculateBudgetCategoryTotal(bc2, currMonth));
        Transaction t3 = new Transaction(new Date(), 50, "Ate burger", testCard, bc2);
        db.insertTransaction(t3);
        assertEquals(60f, testAccess.calculateBudgetCategoryTotal(bc1, currMonth));
        assertEquals(50f, testAccess.calculateBudgetCategoryTotal(bc2, currMonth));
    }

    public void testCalculateTransactionDifferentMonths() {
        CreditCard testCard = new CreditCard("Amex", "1000100010001000", "Alan Alfred", 6, 2022, 27);
        Transaction t1 = new Transaction(new Date(), 20, "Watched a movie", testCard, bc1);
        Calendar currMonth = Calendar.getInstance();
        currMonth.setTime(new Date());
        currMonth.add(Calendar.MONTH, 1);
        Transaction t2 = new Transaction(currMonth.getTime(), 40, "Bought a video game", testCard, bc1);
        StubDatabase db = Services.getDataAccess("TBCU");
        currMonth.setTime(new Date());
        db.insertTransaction(t1);
        db.insertTransaction(t2);
        assertEquals(20f, testAccess.calculateBudgetCategoryTotal(bc1, currMonth));
        currMonth.add(Calendar.MONTH, 1);
        assertEquals(40f, testAccess.calculateBudgetCategoryTotal(bc1, currMonth));
    }

    /**
     * This method closes StubDatabase
     */
    public void tearDown() {
        Services.closeDataAccess();
    }
}