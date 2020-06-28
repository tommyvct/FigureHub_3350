package comp3350.pbbs.objects;

import junit.framework.TestCase;

import comp3350.pbbs.objects.budgetCategories;

import static org.junit.Assert.assertNotEquals;

public class TestBudgetCategories extends TestCase
{
    private String budgetID;
    private String budgetName;
    private Double budgetLimit;
    private budgetCategories newBudget;

    public void setUp(){
      budgetID = "1";
      budgetName = "Groceries";
      budgetLimit = 100.00;
      newBudget = new budgetCategories(budgetID, budgetName, budgetLimit);
    }

    public void testGetBudgetID(){
        System.out.println("Starting TestBudgetCategories");
        assertEquals("1", newBudget.getBudgetID());
        assertNotEquals("100",newBudget.getBudgetID());
    }
    public void testGetBudgetName(){
        assertEquals("Groceries", newBudget.getBudgetName());
        assertNotEquals("Rent", newBudget.getBudgetName());
    }
    public void testGetBudgetLimit(){
        assertEquals(100.00,newBudget.getBudgetLimit());
        assertNotEquals(200.00, newBudget.getBudgetLimit());
    }
    public void testToString(){
        assertEquals("Budget: 1 Groceries "+budgetLimit, newBudget.toString());
        assertNotEquals("Budget: Groceries 1"+budgetLimit, newBudget.toString());
    }
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

        try {
            budgetCategories Budget4 = new budgetCategories("4", "Phone", -20.00);
            fail("Expected limit will be positive");
        }catch (IllegalArgumentException e){}

        System.out.println("Finished TestBudgetCategories");
    }

}
