package comp3350.pbbs.tests.objects;

import junit.framework.TestCase;

import comp3350.pbbs.objects.BudgetCategory;

import static org.junit.Assert.assertNotEquals;

/**
 * TestBudgetCategories
 * Group4
 * PBBS
 *
 * This class defines a test suite for the budgetCategories class.
 */
public class TestBudgetCategory extends TestCase {
    private String budgetName;              //Test budget name
    private Double budgetLimit;             //Test budget limit
    private BudgetCategory newBudget;     //Test budgetCategories object

    /**
     * Method to set the test values.
     */
    public void setUp() {
        budgetName = "Groceries";
        budgetLimit = 100.00;
        newBudget = new BudgetCategory(budgetName, budgetLimit);
    }

    /**
     * Method to test the getBudgetName
     */
    public void testGetBudgetName() {
        assertEquals("Groceries", newBudget.getBudgetName());
        assertNotEquals("Rent", newBudget.getBudgetName());
    }

    /**
     * Method to test the getBudgetLimit
     */
    public void testGetBudgetLimit() {
        assertEquals(100.00, newBudget.getBudgetLimit());
        assertNotEquals(200.00, newBudget.getBudgetLimit());
    }

    /**
     * Method to test the equals
     */
    public void testEquals() {
        BudgetCategory Budget1 = new BudgetCategory("Groceries", 100.00);
        assertNotNull(Budget1);
        assertTrue(newBudget.equals(Budget1));

        BudgetCategory Budget2 = new BudgetCategory("Rent", 200.00);
        assertNotNull(Budget2);
        assertFalse(newBudget.equals(Budget2));

        BudgetCategory Budget3 = new BudgetCategory("Utilities", 0);
        assertNotNull(Budget3);
        assertFalse(newBudget.equals(Budget3));

        //won't create an object if passed negative value in budgetLimit
        try {
            BudgetCategory Budget4 = new BudgetCategory("Phone", -20.00);
            fail("Expected limit will be positive");
        } catch (IllegalArgumentException e) {
        }

    }

}
