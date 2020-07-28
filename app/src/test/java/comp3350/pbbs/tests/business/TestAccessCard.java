package comp3350.pbbs.tests.business;

import junit.framework.TestCase;

import java.util.Calendar;
import java.util.List;

import comp3350.pbbs.application.Main;
import comp3350.pbbs.application.Services;
import comp3350.pbbs.business.AccessICard;
import comp3350.pbbs.business.AccessValidation;
import comp3350.pbbs.objects.Cards.Card;

/**
 * TestAccessCreditCard
 * Group4
 * PBBS
 *
 * This class tests the methods in the AccessCreditCard class
 */
public class TestAccessCard extends TestCase {
    private Card card;        // a CreditCard object
    private AccessICard acc;    // a AccessCreditCard object

    /**
     * This method connects to the database, create and initiate instance variables
     */
    public void setUp() {
        Main.startup();
        card = new Card("mastercard", "1001200230034004", "Si-Chuan Hotpot", 12, 2024, 12);
        acc = new AccessICard();
        acc.insertCard(card);
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
        Card card1 = new Card("mastercard", "5005600670078008", "Cheese Burger", 3, 2021, 18);
        assertTrue(acc.findCard(card));
        assertFalse(acc.findCard(card1));
    }


    /**
     * This method tests deleting credit cards
     */
    public void testDeleteCreditCard() {
        Card card1 = new Card("mastercard", "5005600670078008", "Cheese Burger", 3, 2021, 18);
        assertTrue(acc.deleteCard(card));
        assertFalse(acc.deleteCard(card));
        assertFalse(acc.deleteCard(card1));
    }

    /**
     * This method tests updating credit cards
     */
    public void testUpdateCreditCard() {
        Card card1 = new Card("mastercard", "5005600670078008", "Cheese Burger", 3, 2021, 18);
        Card debitCard = new Card("mastercard debit", "5615215412345678", "Tommy", 3, 2026);
        assertFalse(acc.updateCard(card1, debitCard));
        assertTrue(acc.updateCard(card, card1));
        assertFalse(acc.updateCard(card, card1));
        assertTrue(acc.findCard(card1));
        assertFalse(acc.findCard(card));
    }

    /**
     * Test validating pay dates
     */
    public void testPayDay() {
        assertTrue(AccessValidation.isValidPayDate(1));
        assertTrue(AccessValidation.isValidPayDate(15));
        assertTrue(AccessValidation.isValidPayDate(31));
        assertFalse(AccessValidation.isValidPayDate(-1));
        assertFalse(AccessValidation.isValidPayDate(0));
        assertFalse(AccessValidation.isValidPayDate(-15));
        assertFalse(AccessValidation.isValidPayDate(32));
        assertFalse(AccessValidation.isValidPayDate(64));
    }

    /**
     * Test validating expiration dates
     */
    public void testExpirationDate() {
        Calendar calender = Calendar.getInstance();
        // int currMonth = calender.get(Calendar.MONTH) + 1;  // never used
        int currYear = calender.get(Calendar.YEAR);
        assertEquals(0, AccessValidation.isValidExpirationDate("1", "2068"));
        assertEquals(0, AccessValidation.isValidExpirationDate("12", "2068"));
        assertEquals(1, AccessValidation.isValidExpirationDate("-1", "2068"));
        assertEquals(1, AccessValidation.isValidExpirationDate("-20", "2068"));
        assertEquals(1, AccessValidation.isValidExpirationDate("13", "2068"));
        assertEquals(1, AccessValidation.isValidExpirationDate("24", "2068"));
        assertEquals(2, AccessValidation.isValidExpirationDate("1", "2100"));
        assertEquals(2, AccessValidation.isValidExpirationDate("1", "3000"));
        assertEquals(3, AccessValidation.isValidExpirationDate("13", "2100"));
        assertEquals(3, AccessValidation.isValidExpirationDate("24", "3000"));
        assertEquals(4, AccessValidation.isValidExpirationDate("1", "-20"));
        assertEquals(4, AccessValidation.isValidExpirationDate("-1", "-20"));
        assertEquals(4, AccessValidation.isValidExpirationDate("1", "900"));
        assertEquals(4, AccessValidation.isValidExpirationDate("1", "90"));
        assertEquals(4, AccessValidation.isValidExpirationDate("1", "9"));
        assertEquals(5, AccessValidation.isValidExpirationDate("1", Integer.toString(currYear)));
        assertEquals(6, AccessValidation.isValidExpirationDate("1", Integer.toString(currYear-1)));
        assertEquals(7, AccessValidation.isValidExpirationDate("string", "2068"));
        assertEquals(7, AccessValidation.isValidExpirationDate("1", "string"));
        assertEquals(7, AccessValidation.isValidExpirationDate("string", "string 2"));
        assertEquals(7, AccessValidation.isValidExpirationDate("", "2068"));
        assertEquals(7, AccessValidation.isValidExpirationDate(null, "2068"));
        assertEquals(7, AccessValidation.isValidExpirationDate("1", ""));
        assertEquals(7, AccessValidation.isValidExpirationDate("1", null));
    }

    /**
     * Test retrieving a list of credit cards
     */
    public void testCardList() {
        Card card1 = new Card("mastercard", "5005600670078008", "Cheese Burger", 3, 2021, 18);
        List<Card> list = acc.getCreditCards();
        assertTrue(list.contains(card));
        assertFalse(list.contains(card1));
        acc.insertCard(card1);
        list = acc.getCreditCards();
        assertTrue(list.contains(card));
        assertTrue(list.contains(card1));
    }

    /**
     * Test validating cardholder names
     */
    public void testName() {
        assertTrue( AccessValidation.isValidName("cool name"));
        assertFalse(AccessValidation.isValidName(""));
        assertFalse(AccessValidation.isValidName(null));
        assertFalse(AccessValidation.isValidName("X AE A-12"));
    }
}