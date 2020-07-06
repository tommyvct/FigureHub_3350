package comp3350.pbbs.tests.objects;

import junit.framework.TestCase;
import java.util.Calendar;
import java.util.Date;
import comp3350.pbbs.objects.BudgetCategory;
import comp3350.pbbs.objects.CreditCard;
import comp3350.pbbs.objects.Transaction;
import static org.junit.Assert.assertNotEquals;

/**
 * TestTransaction
 * Joshua Smallwood
 * PBBS
 *
 * This class defines a test suite for the Transaction class.
 */
public class TestTransaction extends TestCase {
    private Date now;  // Reference to the current time
    private CreditCard card;           // Test credit card
    private BudgetCategory budgetCategory; // Test budget category
    private float amount;       // Test amount
    private String description; // Test description

    /**
     * Method that runs before each test, sets the test values.
     */
    public void setUp() {
        now = new Date();
        card = new CreditCard("1111111111111111", "Jane Doe", 1, 2021, 15);
        budgetCategory = new BudgetCategory("Groceries", 200);
        amount = 5.57f;
        description = "Bought groceries.";
    }

    /**
     * Test equality on dates and invalid dates.
     */
    public void testTransactionDates() {
        Transaction transaction = new Transaction(now, amount, description, card, budgetCategory);
        Transaction otherTransaction = new Transaction(now, amount, description, card, budgetCategory);
        assertEquals(transaction, otherTransaction);

        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.DATE, 21);
        otherTransaction = new Transaction(cal.getTime(), amount, description, card, budgetCategory);
        assertNotEquals(transaction, otherTransaction);

        try {
            new Transaction(null, amount, description, card, budgetCategory);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException iae) { }
    }

    /**
     * Test equality on amounts and invalid amounts.
     */
    public void testTransactionAmounts() {
        Transaction transaction = new Transaction(now, amount, description, card, budgetCategory);
        Transaction otherTransaction = new Transaction(now, amount, description, card, budgetCategory);
        assertEquals(transaction, otherTransaction);

        otherTransaction = new Transaction(now, amount + 1, description, card, budgetCategory);
        assertNotEquals(transaction, otherTransaction);

        try {
            new Transaction(now, -0.24f, description, card, budgetCategory);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException iae) { }
    }

    /**
     * Test equality on descriptions and invalid descriptions
     */
    public void testDescriptions() {
        Transaction transaction = new Transaction(now, amount, description, card, budgetCategory);
        Transaction otherTransaction = new Transaction(now, amount, description, card, budgetCategory);
        assertEquals(transaction, otherTransaction);

        otherTransaction = new Transaction(now, amount, "Paid for rent.", card, budgetCategory);
        assertNotEquals(transaction, otherTransaction);

        try {
            new Transaction(now, amount, null, card, budgetCategory);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException iae) { }

        try {
            new Transaction(now, amount, "", card, budgetCategory);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException iae) { }
    }

    /**
     * Test equality on cards and invalid cards.
     */
    public void testCards() {
        Transaction transaction = new Transaction(now, amount, description, card, budgetCategory);
        Transaction otherTransaction = new Transaction(now, amount, description, card, budgetCategory);
        assertEquals(transaction, otherTransaction);

        CreditCard otherCard = new CreditCard("2222222222222222", "Jane Doe", 1, 2023, 15);
        otherTransaction = new Transaction(now, amount, description, otherCard, budgetCategory);
        assertNotEquals(transaction, otherTransaction);

        try {
            new Transaction(now, amount, description, null, budgetCategory);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException iae) { }
    }

    /**
     * Test equality on budget categories and invalid budget categories
     */
    public void testBudgetCategories() {
        Transaction transaction = new Transaction(now, amount, description, card, budgetCategory);
        Transaction otherTransaction = new Transaction(now, amount, description, card, budgetCategory);
        assertEquals(transaction, otherTransaction);

        BudgetCategory otherBudgetCategory = new BudgetCategory("Entertainment", 50);
        otherTransaction = new Transaction(now, amount, description, card, otherBudgetCategory);
        assertNotEquals(transaction, otherTransaction);

        try {
            new Transaction(now, amount, description, card, null);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException iae) { }
    }
}
