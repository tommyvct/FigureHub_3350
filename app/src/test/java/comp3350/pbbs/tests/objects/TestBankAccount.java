package comp3350.pbbs.tests.objects;

import android.renderscript.Int2;

import comp3350.pbbs.application.Main;
import comp3350.pbbs.objects.BankAccount;
import comp3350.pbbs.objects.Cards.DebitCard;

import junit.framework.TestCase;

public class TestBankAccount extends TestCase
{
    DebitCard debitCard1;
    DebitCard debitCard2;

    public void setUp()
    {
        debitCard1 = new DebitCard("CIBC Advantage Debit", "4506445712345678", "Tommy", 3, 2024);
        debitCard2 = new DebitCard("TD Access Debit", "4724090212345678", "Tommy", 3, 2024);
    }

    /**
     * test constructor method
     * case: null params on constructor
     * account name if null or empty, should have a default name of "No Name"
     * account number could not be null
     * An account must be linked to a non null debit card
     */
    public void testBankAccount()
    {
        BankAccount nametest1 = new BankAccount(null, "777", debitCard1);
        BankAccount nametest2 = new BankAccount("", "777", debitCard1);
        BankAccount nametest3 = new BankAccount("fjksdhf", "56131", debitCard2);
        assertEquals("No Name", nametest1.getAccountName());
        assertEquals("No Name", nametest2.getAccountName());
        assertEquals(nametest1.getAccountName(), nametest2.getAccountName());

        assertEquals("777", nametest1.getAccountNumber());
        assertEquals("777", nametest2.getAccountNumber());
        assertEquals(nametest1.getAccountNumber(), nametest2.getAccountNumber());

        assertTrue(nametest1.equals(nametest1));
        assertTrue(nametest2.equals(nametest2));
        assertTrue(nametest1.equals(nametest2));

        assertEquals(nametest1.getLinkedCard(), debitCard1);
        assertEquals(nametest2.getLinkedCard(), debitCard1);
        assertSame(nametest1.getLinkedCard(), debitCard1);
        assertSame(nametest2.getLinkedCard(), debitCard1);
        assertSame(nametest1.getLinkedCard(), nametest2.getLinkedCard());
        assertEquals(nametest1.getLinkedCard(), nametest2.getLinkedCard());

        try
        {
            new BankAccount(null, null, debitCard2);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException ignored) {}

        try
        {
            new BankAccount(null, "894", null);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException ignored) {}

        try
        {
            new BankAccount(null, null, null);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException ignored) {}

    }
}
