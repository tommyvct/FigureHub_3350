package comp3350.pbbs.tests.business;

import junit.framework.TestCase;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import comp3350.pbbs.application.Main;
import comp3350.pbbs.application.Services;
import comp3350.pbbs.objects.CreditCard;
import comp3350.pbbs.business.AccessCreditCard;
import comp3350.pbbs.tests.persistence.StubDatabase;

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
        Services.createDataAccess(new StubDatabase("test"));
        card = new CreditCard("mastercard", "1001200230034004", "Si-Chuan Hotpot", 12, 2024, 12);
        acc = new AccessCreditCard();
        acc.insertCreditCard(card);
    }

    /**
     * This teardown method disconnects from the database
     */
    public void tearDown() {
        Services.closeDataAccess();
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
        assertTrue(acc.isValidPayDate(1));
        assertTrue(acc.isValidPayDate(15));
        assertTrue(acc.isValidPayDate(31));
        assertFalse(acc.isValidPayDate(-1));
        assertFalse(acc.isValidPayDate(0));
        assertFalse(acc.isValidPayDate(-15));
        assertFalse(acc.isValidPayDate(32));
        assertFalse(acc.isValidPayDate(64));
    }

    /**
     * Test validating expiration dates
     */
    public void testExpirationDate() {
        Calendar calender = Calendar.getInstance();
        int currMonth = calender.get(Calendar.MONTH) + 1;
        int currYear = calender.get(Calendar.YEAR);
        assertEquals(0, acc.isValidExpirationDate("1", "2068"));
        assertEquals(0, acc.isValidExpirationDate("12", "2068"));
        assertEquals(1, acc.isValidExpirationDate("-1", "2068"));
        assertEquals(1, acc.isValidExpirationDate("-20", "2068"));
        assertEquals(1, acc.isValidExpirationDate("13", "2068"));
        assertEquals(1, acc.isValidExpirationDate("24", "2068"));
        assertEquals(2, acc.isValidExpirationDate("1", "2100"));
        assertEquals(2, acc.isValidExpirationDate("1", "3000"));
        assertEquals(3, acc.isValidExpirationDate("13", "2100"));
        assertEquals(3, acc.isValidExpirationDate("24", "3000"));
        assertEquals(4, acc.isValidExpirationDate("1", "-20"));
        assertEquals(4, acc.isValidExpirationDate("-1", "-20"));
        assertEquals(4, acc.isValidExpirationDate("1", "900"));
        assertEquals(4, acc.isValidExpirationDate("1", "90"));
        assertEquals(4, acc.isValidExpirationDate("1", "9"));
        assertEquals(5, acc.isValidExpirationDate("1", Integer.toString(currYear)));
        assertEquals(6, acc.isValidExpirationDate("1", Integer.toString(currYear-1)));
        assertEquals(7, acc.isValidExpirationDate("string", "2068"));
        assertEquals(7, acc.isValidExpirationDate("1", "string"));
        assertEquals(7, acc.isValidExpirationDate("string", "string 2"));
        assertEquals(7, acc.isValidExpirationDate("", "2068"));
        assertEquals(7, acc.isValidExpirationDate(null, "2068"));
        assertEquals(7, acc.isValidExpirationDate("1", ""));
        assertEquals(7, acc.isValidExpirationDate("1", null));
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
        assertTrue(acc.isValidName("cool name"));
        assertFalse(acc.isValidName(""));
        assertFalse(acc.isValidName(null));
        assertFalse(acc.isValidName("X AE A-12"));
    }
}