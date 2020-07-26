package comp3350.pbbs.tests.objects;

import java.util.Calendar;

import junit.framework.TestCase;

import comp3350.pbbs.objects.Cards.DebitCard;

import static org.junit.Assert.assertNotEquals;

/**
 * TestDebitCard
 * Group4
 * PBBS
 *
 * This class tests the methods in the DebitCard class
 */
public class TestDebitCard extends TestCase {
    private String num;    // 16-digits number of a debit card
    private String name;    // user full name of a debit card
    private int expMon;        // the month a debit card is expired, 2-digits (MM)
    private int expYear;    // the year a debit card is expired, 4-digits (YYYY)

    /**
     * setup: instantiate a default debit card
     */
    public void setUp() {
        num = "1000100010001000";
        name = "Alan Alfred";
        expMon = 12;
        expYear = 2021;
    }

    /**
     * method: test isValidLength(str)
     * case: a debit card number cannot be null or non-16-digits long
     */
    public void testCardNum() {
        DebitCard card1 = new DebitCard("mastercard", num, name, expMon, expYear);
        DebitCard card2 = new DebitCard("mastercard", num, name, expMon, expYear);
        assertEquals(card1.getCardNum(), card2.getCardNum());
        card2 = new DebitCard("mastercard", "1000200030004000", name, expMon, expYear);
        assertNotEquals(card1.getCardNum(), card2.getCardNum());
        try {
            new DebitCard("mastercard", null, name, expMon, expYear);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ignored) {
        }
    }

    /**
     * method: test isValidName(str)
     * case: a debit card holder name cannot be null or real-world non-existed
     */
    public void testHolderName() {
        DebitCard card1 = new DebitCard("mastercard", num, name, expMon, expYear);
        DebitCard card2 = new DebitCard("mastercard", num, name, expMon, expYear);
        assertEquals(card1.getHolderName(), card2.getHolderName());
        card2 = new DebitCard("mastercard", num, "Bob Bushman", expMon, expYear);
        assertNotEquals(card1.getHolderName(), card2.getHolderName());
        try {
            new DebitCard("mastercard", num, null, expMon, expYear);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ignored) {
        }
        try {
            new DebitCard("mastercard", num, "1@3?", expMon, expYear);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ignored) {
        }
    }

    /**
     * method: test the month of isValidExpiration(int, int)
     * case: a month cannot be negative, and must fall into range 1 to 12
     */
    public void testExpireMonth() {
        DebitCard card1 = new DebitCard("mastercard", num, name, expMon, expYear);
        DebitCard card2 = new DebitCard("mastercard", num, name, expMon, expYear);
        assertEquals(card1.getExpireMonth(), card2.getExpireMonth());
        card2 = new DebitCard("mastercard", num, name, 9, expYear);
        assertNotEquals(card1.getExpireMonth(), card2.getExpireMonth());
        try {
            new DebitCard("mastercard", num, name, -1, expYear);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ignored) {
        }
        try {
            new DebitCard("mastercard", num, name, 15, expYear);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ignored) {
        }
    }

    /**
     * method: test the year of isValidExpiration(int, int)
     * case: a year cannot be negative, and must be less than A.D.3000
     */
    public void testExpireYear() {
        DebitCard card1 = new DebitCard("mastercard", num, name, expMon, expYear);
        DebitCard card2 = new DebitCard("mastercard", num, name, expMon, expYear);
        assertEquals(card1.getExpireYear(), card2.getExpireYear());
        card2 = new DebitCard("mastercard", num, name, expMon, expYear + 1);
        assertNotEquals(card1.getExpireYear(), card2.getExpireYear());
        try {
            new DebitCard("mastercard", num, name, expMon, -1);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ignored) {
        }
        try {
            new DebitCard("mastercard", num, name, expMon, 3000);
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
            new DebitCard("mastercard", num, name, currMonth - 3, currYear);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ignored) {
        }
    }
}
