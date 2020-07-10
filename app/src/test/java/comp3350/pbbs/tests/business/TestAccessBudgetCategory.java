package comp3350.pbbs.tests.business;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Date;

import comp3350.pbbs.application.Services;
import comp3350.pbbs.business.AccessBudgetCategory;
import comp3350.pbbs.objects.BudgetCategory;
import comp3350.pbbs.objects.CreditCard;
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

    /**
     * creating StubDatabase and AccessBudgetCategory
     */
    public void setUp() {
        Services.createDataAccess("TBCU");
        testAccess = new AccessBudgetCategory();
    }

    /**
     * test that the AccessBudgetCategory worked an contains stub data.
     */
    public void testNewAccess() {
        assertNotNull(testAccess);
        //test that there are 4 budget categories in the stub DB
        assertEquals(4, testAccess.getAllBudgetCategories().size());

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
    public void testValidInput() {
        //Adding multiple categories at once
        ArrayList<BudgetCategory> newBudgetCategories = new ArrayList<>();
        BudgetCategory bc1 = new BudgetCategory("entertainment", 50);
        BudgetCategory bc2 = new BudgetCategory("restaurants", 50);
        newBudgetCategories.add(bc1);
        newBudgetCategories.add(bc2);

        assertTrue(testAccess.addBudgetCategories(newBudgetCategories));
        //Test that addition was success and that the findBudgetCategory method works
        assertEquals(bc1, testAccess.findBudgetCategory(bc1));
        assertEquals(bc2, testAccess.findBudgetCategory(bc2));

        //Add one category by itself
        BudgetCategory bc3 = new BudgetCategory("Houseware", 15);
        assertTrue(testAccess.insertBudgetCategory("Houseware", "15"));
        assertEquals(bc3, testAccess.findBudgetCategory(bc3));

        //test that there are now 7 budget categories in DB
        assertEquals(7, testAccess.getAllBudgetCategories().size());

        //Update an existing category
        BudgetCategory newBC3 = new BudgetCategory("Furniture", 100);
        assertEquals(bc3, testAccess.updateBudgetCategory("Houseware", "15", "Furniture", "100"));  //returns old BudgetCategory
        assertEquals(newBC3, testAccess.findBudgetCategory(newBC3));    // New BudgetCategory can be found
        assertNull(testAccess.findBudgetCategory(bc3)); // Old BudgetCategory cannot be found

        //test that there are still 7 budget categories in DB
        assertEquals(7, testAccess.getAllBudgetCategories().size());

        //Delete a category
        assertEquals(newBC3, testAccess.deleteBudgetCategory("Furniture", "100"));
        assertNull(testAccess.findBudgetCategory(newBC3));

        //test that there are now 6 budget categories in DB
        assertEquals(6, testAccess.getAllBudgetCategories().size());
    }

    /**
     * Testing that all methods work using invalid integer input
     */
    public void testInvalidLimitIntegerInput() {
        //setup
        ArrayList<BudgetCategory> newBudgetCategories = new ArrayList<>();
        BudgetCategory bc1 = new BudgetCategory("entertainment", 50);
        BudgetCategory bc2 = new BudgetCategory("restaurants", 50);
        newBudgetCategories.add(bc1);
        newBudgetCategories.add(bc2);

        assertTrue(testAccess.addBudgetCategories(newBudgetCategories));
        //Test that addition was success and that the findBudgetCategory method works
        assertEquals(bc1, testAccess.findBudgetCategory(bc1));
        assertEquals(bc2, testAccess.findBudgetCategory(bc2));

        //invalid input for limit (must be integer) while inserting
        assertFalse(testAccess.insertBudgetCategory("Houseware", "fifty"));

        //test that there are still only 6 budget categories in DB
        assertEquals(6, testAccess.getAllBudgetCategories().size());

        //invalid input for limit (must be integer) while updating
        BudgetCategory newBC2 = new BudgetCategory("Food places", 100);
        assertNull(testAccess.updateBudgetCategory("restaurants", "50", "Food places", "hundred"));
        assertNull(testAccess.findBudgetCategory(newBC2));    // New BudgetCategory cannot be found
        assertEquals(bc2, testAccess.findBudgetCategory(bc2)); // Old BudgetCategory can still be found

        //test that there are still 6 budget categories in DB
        assertEquals(6, testAccess.getAllBudgetCategories().size());

        //invalid input for limit (must be integer) while deleting
        assertNull(testAccess.deleteBudgetCategory("restaurants", "fifty"));
        assertEquals(bc2, testAccess.findBudgetCategory(bc2));

        //test that there are still 6 budget categories in DB
        assertEquals(6, testAccess.getAllBudgetCategories().size());
    }

    /**
     * Testing that all methods work using invalid zero input
     */
    public void testInvalidLimitZeroInput() {
        //setup
        ArrayList<BudgetCategory> newBudgetCategories = new ArrayList<>();
        BudgetCategory bc1 = new BudgetCategory("entertainment", 50);
        BudgetCategory bc2 = new BudgetCategory("restaurants", 50);
        newBudgetCategories.add(bc1);
        newBudgetCategories.add(bc2);

        assertTrue(testAccess.addBudgetCategories(newBudgetCategories));
        //Test that addition was success and that the findBudgetCategory method works
        assertEquals(bc1, testAccess.findBudgetCategory(bc1));
        assertEquals(bc2, testAccess.findBudgetCategory(bc2));

        //invalid input for limit (must be integer) while inserting
        assertFalse(testAccess.insertBudgetCategory("Houseware", "0"));

        //test that there are still only 6 budget categories in DB
        assertEquals(6, testAccess.getAllBudgetCategories().size());

        //invalid input for limit (must be integer) while updating
        BudgetCategory newBC2 = new BudgetCategory("Food places", 100);
        assertNull(testAccess.updateBudgetCategory("restaurants", "50", "Food places", "0"));
        assertNull(testAccess.findBudgetCategory(newBC2));    // New BudgetCategory cannot be found
        assertEquals(bc2, testAccess.findBudgetCategory(bc2)); // Old BudgetCategory can still be found

        //test that there are still 6 budget categories in DB
        assertEquals(6, testAccess.getAllBudgetCategories().size());

        //invalid input for limit (must be integer) while deleting
        assertNull(testAccess.deleteBudgetCategory("restaurants", "0"));
        assertEquals(bc2, testAccess.findBudgetCategory(bc2));

        //test that there are still 6 budget categories in DB
        assertEquals(6, testAccess.getAllBudgetCategories().size());
    }

    /**
     * Testing that all methods work using invalid empty input
     */
    public void testInvalidEmptyInput1() {
        //setup
        ArrayList<BudgetCategory> newBudgetCategories = new ArrayList<>();
        BudgetCategory bc1 = new BudgetCategory("entertainment", 50);
        BudgetCategory bc2 = new BudgetCategory("restaurants", 50);
        newBudgetCategories.add(bc1);
        newBudgetCategories.add(bc2);

        assertTrue(testAccess.addBudgetCategories(newBudgetCategories));
        //Test that addition was success and that the findBudgetCategory method works
        assertEquals(bc1, testAccess.findBudgetCategory(bc1));
        assertEquals(bc2, testAccess.findBudgetCategory(bc2));

        //invalid input for limit (must be integer) while inserting
        assertFalse(testAccess.insertBudgetCategory("Houseware", ""));

        //test that there are still only 6 budget categories in DB
        assertEquals(6, testAccess.getAllBudgetCategories().size());

        //invalid input for limit (must be integer) while updating
        BudgetCategory newBC2 = new BudgetCategory("Food places", 100);
        assertNull(testAccess.updateBudgetCategory("restaurants", "50", "Food places", ""));
        assertNull(testAccess.findBudgetCategory(newBC2));    // New BudgetCategory cannot be found
        assertEquals(bc2, testAccess.findBudgetCategory(bc2)); // Old BudgetCategory can still be found

        //test that there are still 6 budget categories in DB
        assertEquals(6, testAccess.getAllBudgetCategories().size());

        //invalid input for limit (must be integer) while deleting
        assertNull(testAccess.deleteBudgetCategory("restaurants", ""));
        assertEquals(bc2, testAccess.findBudgetCategory(bc2));

        //test that there are still 6 budget categories in DB
        assertEquals(6, testAccess.getAllBudgetCategories().size());
    }

    /**
     * Testing that all methods work using invalid empty input
     */
    public void testInvalidEmptyInput2() {
        //setup
        ArrayList<BudgetCategory> newBudgetCategories = new ArrayList<>();
        BudgetCategory bc1 = new BudgetCategory("entertainment", 50);
        BudgetCategory bc2 = new BudgetCategory("restaurants", 50);
        newBudgetCategories.add(bc1);
        newBudgetCategories.add(bc2);

        assertTrue(testAccess.addBudgetCategories(newBudgetCategories));
        //Test that addition was success and that the findBudgetCategory method works
        assertEquals(bc1, testAccess.findBudgetCategory(bc1));
        assertEquals(bc2, testAccess.findBudgetCategory(bc2));

        //invalid input for limit (must be integer) while inserting
        assertFalse(testAccess.insertBudgetCategory("", "50"));

        //test that there are still only 6 budget categories in DB
        assertEquals(6, testAccess.getAllBudgetCategories().size());

        //invalid input for limit (must be integer) while updating
        BudgetCategory newBC2 = new BudgetCategory("Food places", 100);
        assertNull(testAccess.updateBudgetCategory("restaurants", "50", "", "100"));
        assertNull(testAccess.findBudgetCategory(newBC2));    // New BudgetCategory cannot be found
        assertEquals(bc2, testAccess.findBudgetCategory(bc2)); // Old BudgetCategory can still be found

        //test that there are still 6 budget categories in DB
        assertEquals(6, testAccess.getAllBudgetCategories().size());

        //invalid input for limit (must be integer) while deleting
        assertNull(testAccess.deleteBudgetCategory("", "50"));
        assertEquals(bc2, testAccess.findBudgetCategory(bc2));

        //test that there are still 6 budget categories in DB
        assertEquals(6, testAccess.getAllBudgetCategories().size());
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
    public void testAdding() {
        ArrayList<BudgetCategory> newBudgetCategories = new ArrayList<>();
        BudgetCategory bc1 = new BudgetCategory("entertainment", 50);
        BudgetCategory bc2 = new BudgetCategory("restaurants", 50);
        newBudgetCategories.add(bc1);
        newBudgetCategories.add(bc2);

        assertTrue(testAccess.addBudgetCategories(newBudgetCategories));
        assertNotNull(testAccess.findBudgetCategory(bc2));

        BudgetCategory bc3 = new BudgetCategory("Houseware", 15);
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
        assertNotNull(testAccess.updateBudgetCategory("Electronics", "50", "Computer", "500"));
        assertNotNull(testAccess.findBudgetCategory(newBC));
    }

    /**
     * test removing a BudgetCategory from the stub.
     */
    public void testDelete() {
        BudgetCategory testBC = new BudgetCategory("Furniture", 100);
        testAccess.insertBudgetCategory("Furniture", "100");
        assertNotNull(testAccess.findBudgetCategory(testBC));
        assertNotNull(testAccess.deleteBudgetCategory("Furniture", "100"));
        assertNull(testAccess.findBudgetCategory(testBC));
    }

    /**
     * Test calculating budget total for invalid inputs
     */
    public void testCalculateInvalidBudgetCategory() {
        assertEquals(0, testAccess.calculateBudgetCategoryTotal(null));
    }

    /**
     * Test calculating budget total for no transactions
     */
    public void testCalculateNoTransactionsTotal() {
        BudgetCategory bc1 = new BudgetCategory("entertainment", 50);
        BudgetCategory bc2 = new BudgetCategory("restaurants", 50);
        //The two budget categories should not have any associated transactions
        assertEquals(0, testAccess.calculateBudgetCategoryTotal(bc1));
        assertEquals(0, testAccess.calculateBudgetCategoryTotal(bc2));
    }

    /**
     * Test calculating budget total for a single transaction
     */
    public void testCalculateOneTransactionTotal() {
        BudgetCategory bc1 = new BudgetCategory("entertainment", 50);
        BudgetCategory bc2 = new BudgetCategory("restaurants", 50);
        CreditCard testCard = new CreditCard("Amex", "1000100010001000", "Alan Alfred", 6, 2022, 27);
        Transaction t1 = new Transaction(new Date(), 20, "Played at the arcade", testCard, bc1);
        StubDatabase db = Services.getDataAccess("TBCU");
        db.insertTransaction(t1);
        assertEquals(20, testAccess.calculateBudgetCategoryTotal(bc1));
        assertEquals(0, testAccess.calculateBudgetCategoryTotal(bc2));
    }

    /**
     * Test calculating budget total for multiple transactions
     */
    public void testCalculateMultipleTransactionsTotal() {
        BudgetCategory bc1 = new BudgetCategory("entertainment", 50);
        BudgetCategory bc2 = new BudgetCategory("restaurants", 50);
        CreditCard testCard = new CreditCard("Amex", "1000100010001000", "Alan Alfred", 6, 2022, 27);
        Transaction t1 = new Transaction(new Date(), 20, "Watched a movie", testCard, bc1);
        Transaction t2 = new Transaction(new Date(), 40, "Bought a video game", testCard, bc1);
        StubDatabase db = Services.getDataAccess("TBCU");
        db.insertTransaction(t1);
        db.insertTransaction(t2);
        assertEquals(60, testAccess.calculateBudgetCategoryTotal(bc1));
        assertEquals(0, testAccess.calculateBudgetCategoryTotal(bc2));
        Transaction t3 = new Transaction(new Date(), 50, "Ate burger", testCard, bc2);
        db.insertTransaction(t3);
        assertEquals(60, testAccess.calculateBudgetCategoryTotal(bc1));
        assertEquals(50, testAccess.calculateBudgetCategoryTotal(bc2));
    }

    /**
     * This method closes StubDatabase
     */
    public void tearDown() {
        Services.closeDataAccess();
    }
}