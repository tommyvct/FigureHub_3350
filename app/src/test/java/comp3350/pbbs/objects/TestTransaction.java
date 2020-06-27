package comp3350.pbbs.objects;

import junit.framework.TestCase;

import java.time.LocalDateTime;

import comp3350.pbbs.objects.Transaction;
import java.time.LocalDateTime;


public class TestTransaction extends TestCase
{
    public void testInvalidTransactions() {
        try {
            Transaction transaction = new Transaction(null, 1, 1);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException iae) { }

        try {
            Transaction transaction = new Transaction(LocalDateTime.now(), -2, 1);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException iae) { }

        try {
            Transaction transaction = new Transaction(LocalDateTime.now(), 1, -2);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException iae) { }
    }

    public void testGetters() {
        LocalDateTime now = LocalDateTime.now();
        int card = 1;
        int budgetCategory = 1;
        Transaction transaction = new Transaction(now, card, budgetCategory);
        assertEquals(transaction.getTime(), now);
        assertEquals(transaction.getCard(), card);
        assertEquals(transaction.getBudgetCategory(), budgetCategory);
    }
}
