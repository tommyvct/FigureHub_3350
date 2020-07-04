package comp3350.pbbs.tests.business;

import junit.framework.TestCase;

import java.util.ArrayList;

import comp3350.pbbs.application.Services;
import comp3350.pbbs.business.AccessBudgetCategory;
import comp3350.pbbs.objects.BudgetCategory;

public class TestAccessBudgetCategory extends TestCase{
    private AccessBudgetCategory testAccess = null;

    /**
     * creating StubDatabase and AccessBudgetCategory
     */
    public void setUp(){
        Services.createDataAccess("TBCU");
        testAccess = new AccessBudgetCategory();
    }

    /**
     * test that the AccessBudgetCategory worked.
     */
    public void testCreation(){
        assertNotNull(testAccess);
    }

    /**
     * test getting all budget categories from stub database
     */
    public void testGetting(){
        assertNotNull(testAccess.getAllBudgetCategories());
    }

    /**
     * testing the findBudgetCategory method in AccessBudgetCategory
     */
    public void testFinding(){
        BudgetCategory failSearch = new BudgetCategory("balloons", 1000);
        BudgetCategory successSearch = new BudgetCategory("Groceries", 100);
        assertNull(testAccess.findBudgetCategory(failSearch));
        assertNotNull(testAccess.findBudgetCategory(successSearch));
    }

    /**
     * testing adding a list of BudgetCategory to the stub, as well as adding individually
     */
    public void testAdding(){
        ArrayList<BudgetCategory> newBudgetCategories = new ArrayList<BudgetCategory>();
        BudgetCategory bc1 = new BudgetCategory("entertainment", 50);
        BudgetCategory bc2 = new BudgetCategory("restaurants", 50);
        newBudgetCategories.add(bc1);
        newBudgetCategories.add(bc2);

        assertTrue(testAccess.addBudgetCategories(newBudgetCategories));
        assertNotNull(testAccess.findBudgetCategory(bc2));

        BudgetCategory bc3 = new BudgetCategory("Houseware", 15);
        assertTrue(testAccess.insertBudgetCategory(bc3));
        assertNotNull(testAccess.findBudgetCategory(bc3));
    }

    /**
     * testing switching two versions of BudgetCategory
     */
    public void testUpdate(){
        BudgetCategory oldBC = new BudgetCategory("Electronics", 50);
        testAccess.insertBudgetCategory(oldBC);
        assertNotNull(testAccess.findBudgetCategory(oldBC));

        BudgetCategory newBC = new BudgetCategory("Computer", 500);
        assertNotNull(testAccess.updateBudgetCategory(oldBC, newBC));
        assertNotNull(testAccess.findBudgetCategory(newBC));
    }

    /**
     * test removing a BudgetCategory from the stub.
     */
    public void testDelete(){
        BudgetCategory testBC = new BudgetCategory("Furniture", 100);
        testAccess.insertBudgetCategory(testBC);
        assertNotNull(testAccess.findBudgetCategory(testBC));
        assertNotNull(testAccess.deleteBudgetCategory(testBC));
        assertNull(testAccess.findBudgetCategory(testBC));
    }

    public void tearDown(){ Services.closeDataAccess();}
}
