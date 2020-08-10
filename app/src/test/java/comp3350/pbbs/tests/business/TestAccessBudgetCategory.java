package comp3350.pbbs.tests.business;

import junit.framework.TestCase;

import java.util.Date;
import java.util.List;

import comp3350.pbbs.persistence.DataAccessController;
import comp3350.pbbs.business.AccessBudgetCategory;
import comp3350.pbbs.objects.BudgetCategory;
import comp3350.pbbs.objects.Card;
import comp3350.pbbs.persistence.DataAccessI;
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
    private DataAccessI testDB;
    List<BudgetCategory> categories;

    //Testing Data
    private Date testDate = new Date(2020 - 07 - 15);
    private BudgetCategory bc1 = new BudgetCategory("entertainment", 50);
    private BudgetCategory bc2 = new BudgetCategory("restaurants", 50);
    private BudgetCategory bc3 = new BudgetCategory("Houseware", 15);
    private Card testCard = new Card("Amex", "1000100010001000", "Alan Alfred", 6, 2022, 27);

    /**
     * creating StubDatabase and AccessBudgetCategory
     */
    public void setUp() {
        testDB = DataAccessController.createDataAccess(new StubDatabase("populateTest"));
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
        rent = new BudgetCategory("Mortgage", 500);
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
    public void testValidInput() {
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
    public void testInvalidLimitZeroInput() {
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
    public void testInvalidNullInput() {
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
    public void testInvalidEmptyInputBudgetLimit() {
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
    public void testInvalidEmptyInputBudgetName() {
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
     * This method closes StubDatabase
     */
    public void tearDown() {
        DataAccessController.closeDataAccess();
    }
}