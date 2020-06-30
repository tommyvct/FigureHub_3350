package comp3350.pbbs.objects;

import junit.framework.TestCase;

import java.time.LocalDateTime;

import comp3350.pbbs.objects.Transaction;
import java.time.LocalDateTime;

import static org.junit.Assert.assertNotEquals;

/**
 * TestTransaction
 * Joshua Smallwood
 * PBBS
 *
 * This class defines a test suite for the Transaction class.
 */
public class TestTransaction extends TestCase {
    private LocalDateTime now;  // Reference to the current time
    private int card;           // Test credit card
    private int budgetCategory; // Test budget category
    private float amount;       // Test amount
    private String description; // Test description

    /**
     * Method that runs before each test, sets the test values.
     */
    public void setUp() {
        now = LocalDateTime.now();
        card = 1;
        budgetCategory = 1;
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

        otherTransaction = new Transaction(now.plusDays(21), amount, description, card, budgetCategory);
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
    }

    /**
     * Test equality on cards and invalid cards.
     */
    public void testCards() {
        // TODO: Change this to work with the CreditCard object
        Transaction transaction = new Transaction(now, amount, description, card, budgetCategory);
        Transaction otherTransaction = new Transaction(now, amount, description, card, budgetCategory);
        assertEquals(transaction, otherTransaction);

        int otherCard = card + 1;
        otherTransaction = new Transaction(now, amount, description, otherCard, budgetCategory);
        assertNotEquals(transaction, otherTransaction);

        try {
            new Transaction(now, amount, description, -1, budgetCategory);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException iae) { }
    }

    /**
     * Test equality on budget categories and invalid budget categories
     */
    public void testBudgetCategories() {
        // TODO: Change this to work with the BudgetCategory object
        Transaction transaction = new Transaction(now, amount, description, card, budgetCategory);
        Transaction otherTransaction = new Transaction(now, amount, description, card, budgetCategory);
        assertEquals(transaction, otherTransaction);

        int otherBudgetCategory = budgetCategory + 1;
        otherTransaction = new Transaction(now, amount, description, card, otherBudgetCategory);
        assertNotEquals(transaction, otherTransaction);

        try {
            new Transaction(now, amount, description, card, -1);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException iae) { }
    }
}
