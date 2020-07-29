package comp3350.pbbs.tests.objects;

import junit.framework.TestCase;
import comp3350.pbbs.objects.BankAccount;
import comp3350.pbbs.objects.Cards.Card;
import static org.junit.Assert.assertNotEquals;

public class TestBankAccount extends TestCase
{
    private String accountName;
    private String accountNumber;
    private Card linkedCard;

    public void setUp() {
        accountName = "My CIBC";
        accountNumber = "7139820";
        linkedCard = new Card("CIBC Advantage Debit", "4506445712345678", "Tommy", 3, 2024);
    }

    /**
     * method: test the field accountName
     */
    public void testAccountName() {
        BankAccount acc1, acc2;

        // case 1: with regular account name
        acc1 = new BankAccount(accountName, accountNumber, linkedCard);
        acc2 = new BankAccount(accountName, accountNumber, linkedCard);
        assertEquals(acc1.getAccountName(), acc2.getAccountName());
        acc2 = new BankAccount("My TD", accountNumber, linkedCard);
        assertNotEquals(acc1.getAccountName(), acc2.getAccountName());

        // case 2: with null account name
        acc1 = new BankAccount(null, accountNumber, linkedCard);
        acc2 = new BankAccount(null, accountNumber, linkedCard);
        assertEquals(acc1.getAccountName(), acc2.getAccountName());
        acc2 = new BankAccount("My TD", accountNumber, linkedCard);
        assertNotEquals(acc1.getAccountName(), acc2.getAccountName());
    }

    /**
     * method: test the field accountNumber
     */
    public void testAccountNumber() {
        BankAccount acc1, acc2;
        acc1 = new BankAccount(accountName, accountNumber, linkedCard);
        acc2 = new BankAccount(accountName, accountNumber, linkedCard);
        assertEquals(acc1.getAccountNumber(), acc2.getAccountNumber());
        acc2 = new BankAccount(accountName, "1357964", linkedCard);
        assertNotEquals(acc1.getAccountNumber(), acc2.getAccountNumber());

        try {
            new BankAccount(accountName, null, linkedCard);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ignored) {}
    }

    /**
     * method: test the field linkedCard
     */
    public void testLinkedCard() {
        Card newCard;
        BankAccount acc1, acc2;
        acc1 = new BankAccount(accountName, accountNumber, linkedCard);
        acc2 = new BankAccount(accountName, accountNumber, linkedCard);
        assertEquals(acc1.getLinkedCard(), acc2.getLinkedCard());
        newCard = new Card("TD Access Debit", "5135794680086666", "John", 5, 2022);
        acc2 = new BankAccount(accountName, accountNumber, newCard);
        assertNotEquals(acc1.getLinkedCard(), acc2.getLinkedCard());

        try {
            new BankAccount(accountName, accountNumber, null);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ignored) {}
    }
}
