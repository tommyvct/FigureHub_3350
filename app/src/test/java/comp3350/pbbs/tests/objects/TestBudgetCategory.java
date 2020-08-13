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
    private String budgetName;
    private double budgetLimit;
    private BudgetCategory newBudget;

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
        assertEquals(newBudget, Budget1);

        BudgetCategory Budget2 = new BudgetCategory("Rent", 200.00);
        assertNotNull(Budget2);
        assertNotEquals(newBudget, Budget2);

        BudgetCategory Budget3 = new BudgetCategory("Utilities", 0);
        assertNotNull(Budget3);
        assertNotEquals(newBudget, Budget3);
    }

    public void testInvalidBudgetCategories() {
        //won't create an object if passed negative value in budgetLimit
        try {
            BudgetCategory Budget4 = new BudgetCategory("Phone", -20.00);
            fail("Expected limit should be positive");
        } catch (IllegalArgumentException ignored) {
        }
        try {
            BudgetCategory Budget4 = new BudgetCategory("", 20);
            fail("Expected category should have a name");
        } catch (IllegalArgumentException ignored) {
        }
        try {
            BudgetCategory Budget4 = new BudgetCategory(null, 20);
            fail("Expected category should have a name");
        } catch (IllegalArgumentException ignored) {
        }
    }

}
