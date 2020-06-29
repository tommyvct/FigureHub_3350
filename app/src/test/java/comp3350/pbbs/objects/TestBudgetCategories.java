package comp3350.pbbs.objects;

import junit.framework.TestCase;

import comp3350.pbbs.objects.budgetCategories;

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
    private String budgetID;                //Test budget id
    private String budgetName;              //Test budget name
    private Double budgetLimit;             //Test budget limit
    private budgetCategories newBudget;     //Test budgetCategories object

    /**
     * Method to set the test values.
     */
    public void setUp(){
      budgetID = "1";
      budgetName = "Groceries";
      budgetLimit = 100.00;
      newBudget = new budgetCategories(budgetID, budgetName, budgetLimit);
    }

    /**
     * Method to test the getBudgetID
     */
    public void testGetBudgetID(){
        assertEquals("1", newBudget.getBudgetID());
        assertNotEquals("100",newBudget.getBudgetID());
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
        assertEquals("Budget: 1 Groceries "+budgetLimit, newBudget.toString());
        assertNotEquals("Budget: Groceries 1"+budgetLimit, newBudget.toString());
    }

    /**
     * Method to test the equals
     */
    public void testEquals(){
        budgetCategories Budget1 = new budgetCategories("1","Groceries", 100.00);
        assertNotNull(Budget1);
        assertTrue(newBudget.equals(Budget1));

        budgetCategories Budget2 = new budgetCategories("2", "Rent", 200.00);
        assertNotNull(Budget2);
        assertFalse(newBudget.equals(Budget2));

        budgetCategories Budget3 = new budgetCategories("3","Groceries", 100.00);
        assertNotNull(Budget3);
        assertFalse(newBudget.equals(Budget3));//only ID is unique, not the budget name or limit

        //won't create an object if passed negative value in budgetLimit
        try {
            budgetCategories Budget4 = new budgetCategories("4", "Phone", -20.00);
            fail("Expected limit will be positive");
        }catch (IllegalArgumentException e){}

    }

}
