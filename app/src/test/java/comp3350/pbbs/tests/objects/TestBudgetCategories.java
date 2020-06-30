package comp3350.pbbs.tests.objects;

import junit.framework.TestCase;

import static org.junit.Assert.assertNotEquals;

/**
 * TestBudgetCategories
 * Azizul Hakim
 * PBBS
 *
 * This class defines a test suite for the budgetCategories class.
 */
public class TestBudgetCategories extends TestCase
{
    private String budgetName;              //Test budget name
    private Double budgetLimit;             //Test budget limit
    private BudgetCategories newBudget;     //Test budgetCategories object

    /**
     * Method to set the test values.
     */
    public void setUp(){
      budgetName = "Groceries";
      budgetLimit = 100.00;
      newBudget = new BudgetCategories(budgetName, budgetLimit);
    }

    /**
     * Method to test the getBudgetName
     */
    public void testGetBudgetName(){
        assertEquals("Groceries", newBudget.getBudgetName());
        assertNotEquals("Rent", newBudget.getBudgetName());
    }

    /**
     * Method to test the getBudgetLimit
     */
    public void testGetBudgetLimit(){
        assertEquals(100.00,newBudget.getBudgetLimit());
        assertNotEquals(200.00, newBudget.getBudgetLimit());
    }

    /**
     * Method to test the toString
     */
    public void testToString(){
        assertEquals("Budget: Groceries "+budgetLimit, newBudget.toString());
        assertNotEquals("Budget: Groceries 1"+budgetLimit, newBudget.toString());
    }

    /**
     * Method to test the equals
     */
    public void testEquals(){
        BudgetCategories Budget1 = new BudgetCategories("Groceries", 100.00);
        assertNotNull(Budget1);
        assertTrue(newBudget.equals(Budget1));

        BudgetCategories Budget2 = new BudgetCategories("Rent", 200.00);
        assertNotNull(Budget2);
        assertFalse(newBudget.equals(Budget2));

        //won't create an object if passed negative value in budgetLimit
        try {
            BudgetCategories Budget4 = new BudgetCategories("Phone", -20.00);
            fail("Expected limit will be positive");
        }catch (IllegalArgumentException e){}

    }

}
