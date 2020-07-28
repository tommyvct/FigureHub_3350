package comp3350.pbbs.tests.objects;

import java.util.Calendar;
import junit.framework.TestCase;
import comp3350.pbbs.objects.Cards.CreditCard;
import static org.junit.Assert.assertNotEquals;

/**
 * TestCreditCard
 * Group4
 * PBBS
 *
 * This class tests the methods in the CreditCard class
 */
public class TestCreditCard extends TestCase {
    private String card;    // name of a card
    private String num;     // serial number of a card
    private String name;    // user full name of a card
    private int expMon;     // the month a card is expired, 2-digits (MM)
    private int expYear;    // the year a card is expired, 4-digits (YYYY)
    private int payDay;     // the payment day of a credit card, 2-digits (DD)

    /**
     * setup: instantiate a default credit card
     */
    public void setUp() {
        card = "Mastercard";
        num = "1000100010001000";
        name = "Alan Alfred";
        expMon = 12;
        expYear = 2021;
        payDay = 24;
    }

    /**
     * method: test instance field cardName
     * case: a credit card name is "No Name" when it is null/empty
     */
    public void testCardName() {
        CreditCard card1, card2;

        // case 1: with regular card name
        card1 = new CreditCard(card, num, name, expMon, expYear, payDay);
        card2 = new CreditCard(card, num, name, expMon, expYear, payDay);
        assertEquals(card1.getCardName(), card2.getCardName());
        card2 = new CreditCard("Visa", num, name, expMon, expYear, payDay);
        assertNotEquals(card1.getCardName(), card2.getCardName());

        // case: with null card name
        card1 = new CreditCard(null, num, name, expMon, expYear, payDay);
        card2 = new CreditCard(null, num, name, expMon, expYear, payDay);
        assertEquals(card1.getCardName(), card2.getCardName());
        card1 = new CreditCard("No Name", num, name, expMon, expYear, payDay);
        assertEquals(card1.getCardName(), card2.getCardName());
        card2 = new CreditCard("Visa", num, name, expMon, expYear, payDay);
        assertNotEquals(card1.getCardName(), card2.getCardName());
    }

    /**
     * method: test isValidLength(str)
     * case: a credit card number cannot be null
     */
    public void testCardNum() {
        CreditCard card1 = new CreditCard(card, num, name, expMon, expYear, payDay);
        CreditCard card2 = new CreditCard(card, num, name, expMon, expYear, payDay);
        assertEquals(card1.getCardNum(), card2.getCardNum());
        card2 = new CreditCard(card, "1000200030004000", name, expMon, expYear, payDay);
        assertNotEquals(card1.getCardNum(), card2.getCardNum());
        try {
            new CreditCard(card, null, name, expMon, expYear, payDay);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ignored) {
        }
    }

    /**
     * method: test isValidName(str)
     * case: a credit card holder name cannot be null or real-world non-existed
     */
    public void testHolderName() {
        CreditCard card1 = new CreditCard(card, num, name, expMon, expYear, payDay);
        CreditCard card2 = new CreditCard(card, num, name, expMon, expYear, payDay);
        assertEquals(card1.getHolderName(), card2.getHolderName());
        card2 = new CreditCard(card, num, "Bob Bushman", expMon, expYear, payDay);
        assertNotEquals(card1.getHolderName(), card2.getHolderName());
        try {
            new CreditCard(card, num, null, expMon, expYear, payDay);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ignored) {
        }
        try {
            new CreditCard(card, num, "1@3?", expMon, expYear, payDay);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ignored) {
        }
    }

    /**
     * method: test the month of isValidExpiration(int, int)
     * case: a month cannot be negative, and must fall into range 1 to 12
     */
    public void testExpireMonth() {
        CreditCard card1 = new CreditCard(card, num, name, expMon, expYear, payDay);
        CreditCard card2 = new CreditCard(card, num, name, expMon, expYear, payDay);
        assertEquals(card1.getExpireMonth(), card2.getExpireMonth());
        card2 = new CreditCard(card, num, name, 9, expYear, payDay);
        assertNotEquals(card1.getExpireMonth(), card2.getExpireMonth());
        try {
            new CreditCard(card, num, name, -1, expYear, payDay);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ignored) {
        }
        try {
            new CreditCard(card, num, name, 15, expYear, payDay);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ignored) {
        }
    }

    /**
     * method: test the year of isValidExpiration(int, int)
     * case: a year cannot be negative, and must be less than A.D.3000
     */
    public void testExpireYear() {
        CreditCard card1 = new CreditCard(card, num, name, expMon, expYear, payDay);
        CreditCard card2 = new CreditCard(card, num, name, expMon, expYear, payDay);
        assertEquals(card1.getExpireYear(), card2.getExpireYear());
        card2 = new CreditCard(card, num, name, expMon, expYear + 1, payDay);
        assertNotEquals(card1.getExpireYear(), card2.getExpireYear());
        try {
            new CreditCard(card, num, name, expMon, -1, payDay);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ignored) {
        }
        try {
            new CreditCard(card, num, name, expMon, 3000, payDay);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ignored) {
        }
    }

    /**
     * method: test both month and year of isValidExpiration(int, int)
     * case: when the expiration of a card is within the current year
     */
    public void testSpecialExpiration() {
        Calendar calender = Calendar.getInstance();
        int currMonth = calender.get(Calendar.MONTH) + 1;
        int currYear = calender.get(Calendar.YEAR);
        try {
            new CreditCard(card, num, name, currMonth - 3, currYear, payDay);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ignored) {
        }
    }

    /**
     * method: test isValidPayDate(int)
     * case: a day cannot be negative or larger than 31
     */
    public void testPayDate() {
        CreditCard card1 = new CreditCard(card, num, name, expMon, expYear, payDay);
        CreditCard card2 = new CreditCard(card, num, name, expMon, expYear, payDay);
        assertEquals(card1.getPayDate(), card2.getPayDate());
        card2 = new CreditCard(card, num, name, expMon, expYear, 12);
        assertNotEquals(card1.getPayDate(), card2.getPayDate());
        try {
            new CreditCard(card, num, name, expMon, expYear, -1);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ignored) {
        }
        try {
            new CreditCard(card, num, name, expMon, expYear, 36);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ignored) {
        }
    }
}
