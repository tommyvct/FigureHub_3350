package comp3350.pbbs.tests.business;

import junit.framework.TestCase;

import java.util.Calendar;
import java.util.List;

import comp3350.pbbs.application.Main;
import comp3350.pbbs.objects.Cards.CreditCard;
import comp3350.pbbs.business.AccessCreditCard;

/**
 * TestAccessCreditCard
 * Group4
 * PBBS
 *
 * This class tests the methods in the AccessCreditCard class
 */
public class TestAccessCreditCard extends TestCase {
    private CreditCard card;        // a CreditCard object
    private AccessCreditCard acc;    // a AccessCreditCard object

    /**
     * This method connects to the database, create and initiate instance variables
     */
    public void setUp() {
        Main.startup();
        card = new CreditCard("mastercard", "1001200230034004", "Si-Chuan Hotpot", 12, 2024, 12);
        acc = new AccessCreditCard();
        acc.insertCreditCard(card);
    }

    /**
     * This teardown method disconnects from the database
     */
    public void tearDown() {
        Main.shutDown();
    }

    /**
     * This method tests finding credit cards in the database
     */
    public void testFindCreditCard() {
        CreditCard card1 = new CreditCard("mastercard", "5005600670078008", "Cheese Burger", 3, 2021, 18);
        assertTrue(acc.findCreditCard(card));
        assertFalse(acc.findCreditCard(card1));
    }

    /**
     * This method tests inserting credit cards
     */
    public void testInsertCreditCard() {
        CreditCard card1 = new CreditCard("mastercard", "5005600670078008", "Cheese Burger", 3, 2021, 18);
        assertTrue(acc.insertCreditCard(card1));
        assertFalse(acc.insertCreditCard(card1));
    }

    /**
     * This method tests deleting credit cards
     */
    public void testDeleteCreditCard() {
        CreditCard card1 = new CreditCard("mastercard", "5005600670078008", "Cheese Burger", 3, 2021, 18);
        assertTrue(acc.deleteCreditCard(card));
        assertFalse(acc.deleteCreditCard(card));
        assertFalse(acc.deleteCreditCard(card1));
    }

    /**
     * This method tests updating credit cards
     */
    public void testUpdateCreditCard() {
        CreditCard card1 = new CreditCard("mastercard", "5005600670078008", "Cheese Burger", 3, 2021, 18);
        assertTrue(acc.updateCreditCard(card, card1));
        assertFalse(acc.updateCreditCard(card, card1));
        assertTrue(acc.findCreditCard(card1));
        assertFalse(acc.findCreditCard(card));
    }

    /**
     * Test validating pay dates
     */
    public void testPayDay() {
        assertTrue(AccessCreditCard.isValidPayDate(1));
        assertTrue(AccessCreditCard.isValidPayDate(15));
        assertTrue(AccessCreditCard.isValidPayDate(31));
        assertFalse(AccessCreditCard.isValidPayDate(-1));
        assertFalse(AccessCreditCard.isValidPayDate(0));
        assertFalse(AccessCreditCard.isValidPayDate(-15));
        assertFalse(AccessCreditCard.isValidPayDate(32));
        assertFalse(AccessCreditCard.isValidPayDate(64));
    }

    /**
     * Test validating expiration dates
     */
    public void testExpirationDate() {
        Calendar calender = Calendar.getInstance();
        // int currMonth = calender.get(Calendar.MONTH) + 1;  // never used
        int currYear = calender.get(Calendar.YEAR);
        assertEquals(0, AccessCreditCard.isValidExpirationDate("1", "2068"));
        assertEquals(0, AccessCreditCard.isValidExpirationDate("12", "2068"));
        assertEquals(1, AccessCreditCard.isValidExpirationDate("-1", "2068"));
        assertEquals(1, AccessCreditCard.isValidExpirationDate("-20", "2068"));
        assertEquals(1, AccessCreditCard.isValidExpirationDate("13", "2068"));
        assertEquals(1, AccessCreditCard.isValidExpirationDate("24", "2068"));
        assertEquals(2, AccessCreditCard.isValidExpirationDate("1", "2100"));
        assertEquals(2, AccessCreditCard.isValidExpirationDate("1", "3000"));
        assertEquals(3, AccessCreditCard.isValidExpirationDate("13", "2100"));
        assertEquals(3, AccessCreditCard.isValidExpirationDate("24", "3000"));
        assertEquals(4, AccessCreditCard.isValidExpirationDate("1", "-20"));
        assertEquals(4, AccessCreditCard.isValidExpirationDate("-1", "-20"));
        assertEquals(4, AccessCreditCard.isValidExpirationDate("1", "900"));
        assertEquals(4, AccessCreditCard.isValidExpirationDate("1", "90"));
        assertEquals(4, AccessCreditCard.isValidExpirationDate("1", "9"));
        assertEquals(5, AccessCreditCard.isValidExpirationDate("1", Integer.toString(currYear)));
        assertEquals(6, AccessCreditCard.isValidExpirationDate("1", Integer.toString(currYear-1)));
        assertEquals(7, AccessCreditCard.isValidExpirationDate("string", "2068"));
        assertEquals(7, AccessCreditCard.isValidExpirationDate("1", "string"));
        assertEquals(7, AccessCreditCard.isValidExpirationDate("string", "string 2"));
        assertEquals(7, AccessCreditCard.isValidExpirationDate("", "2068"));
        assertEquals(7, AccessCreditCard.isValidExpirationDate(null, "2068"));
        assertEquals(7, AccessCreditCard.isValidExpirationDate("1", ""));
        assertEquals(7, AccessCreditCard.isValidExpirationDate("1", null));
    }

    /**
     * Test retrieving a list of credit cards
     */
    public void testCardList() {
        CreditCard card1 = new CreditCard("mastercard", "5005600670078008", "Cheese Burger", 3, 2021, 18);
        List<CreditCard> list = acc.getCreditCards();
        assertTrue(list.contains(card));
        assertFalse(list.contains(card1));
        acc.insertCreditCard(card1);
        list = acc.getCreditCards();
        assertTrue(list.contains(card));
        assertTrue(list.contains(card1));
    }

    /**
     * Test validating cardholder names
     */
    public void testName() {
        assertTrue(AccessCreditCard.isValidName("cool name"));
        assertFalse(AccessCreditCard.isValidName(""));
        assertFalse(AccessCreditCard.isValidName(null));
        assertFalse(AccessCreditCard.isValidName("X AE A-12"));
    }
}