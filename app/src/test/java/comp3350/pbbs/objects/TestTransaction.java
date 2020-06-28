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

    /**
     * Method that runs before each test, sets the test values.
     */
    public void setUp() {
        now = LocalDateTime.now();
        card = 1;
        budgetCategory = 1;
        amount = 5.57f;
    }

    /**
     * Test equality on dates and invalid dates.
     */
    public void testTransactionDates() {
        Transaction transaction = new Transaction(now, amount, card, budgetCategory);
        Transaction otherTransaction = new Transaction(now, amount, card, budgetCategory);
        assertEquals(transaction, otherTransaction);

        otherTransaction = new Transaction(now.plusDays(21), amount, card, budgetCategory);
        assertNotEquals(transaction, otherTransaction);

        try {
            new Transaction(null, amount, card, budgetCategory);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException iae) { }
    }

    /**
     * Test equality on amounts and invalid amounts.
     */
    public void testTransactionAmounts() {
        Transaction transaction = new Transaction(now, amount, card, budgetCategory);
        Transaction otherTransaction = new Transaction(now, amount, card, budgetCategory);
        assertEquals(transaction, otherTransaction);

        otherTransaction = new Transaction(now, amount + 1, card, budgetCategory);
        assertNotEquals(transaction, otherTransaction);

        try {
            new Transaction(now, -0.24f, card, budgetCategory);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException iae) { }
    }

    /**
     * Test equality on cards and invalid cards.
     */
    public void testInvalidCards() {
        // TODO: Change this to work with the CreditCard object
        Transaction transaction = new Transaction(now, amount, card, budgetCategory);
        Transaction otherTransaction = new Transaction(now, amount, card, budgetCategory);
        assertEquals(transaction, otherTransaction);

        int otherCard = card + 1;
        otherTransaction = new Transaction(now, amount, otherCard, budgetCategory);
        assertNotEquals(transaction, otherTransaction);

        try {
            new Transaction(now, amount, -1, budgetCategory);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException iae) { }
    }

    /**
     * Test equality on budget categories and invalid budget categories
     */
    public void testInvalidBudgetCategories() {
        // TODO: Change this to work with the BudgetCategory object
        Transaction transaction = new Transaction(now, amount, card, budgetCategory);
        Transaction otherTransaction = new Transaction(now, amount, card, budgetCategory);
        assertEquals(transaction, otherTransaction);

        int otherBudgetCategory = budgetCategory + 1;
        otherTransaction = new Transaction(now, amount, card, otherBudgetCategory);
        assertNotEquals(transaction, otherTransaction);

        try {
            new Transaction(now, amount, card, -1);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException iae) { }
    }
}
