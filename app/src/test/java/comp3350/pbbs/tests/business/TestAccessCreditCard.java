package comp3350.pbbs.tests.business;

import junit.framework.TestCase;

import java.util.Calendar;
import java.util.List;

import comp3350.pbbs.application.Main;
import comp3350.pbbs.application.Services;
import comp3350.pbbs.business.AccessICard;
import comp3350.pbbs.business.AccessValidation;
import comp3350.pbbs.objects.Cards.CreditCard;
import comp3350.pbbs.objects.Cards.DebitCard;

/**
 * TestAccessCreditCard
 * Group4
 * PBBS
 *
 * This class tests the methods in the AccessCreditCard class
 */
public class TestAccessCreditCard extends TestCase {
    private CreditCard creditCard;      // a CreditCard object
    private AccessICard accessICard;    // a AccessCreditCard object

    /**
     * This method connects to the database, create and initiate instance variables
     */
    public void setUp() {
        Services.createDataAccess("TBCU");
        creditCard = new CreditCard("mastercard", "1001200230034004", "Si-Chuan Hotpot", 12, 2024, 12);
        accessICard = new AccessICard();
        accessICard.insertCard(creditCard);
    }

    /**
     * This method tests finding credit cards in the database
     */
    public void testFindCreditCard() {
        CreditCard creditCard1 = new CreditCard("mastercard", "5005600670078008", "Cheese Burger", 3, 2021, 18);
        assertTrue(accessICard.findCard(creditCard));
        assertFalse(accessICard.findCard(creditCard1));
    }


    /**
     * This method tests deleting credit cards
     */
    public void testDeleteCreditCard() {
        CreditCard creditCard1 = new CreditCard("mastercard", "5005600670078008", "Cheese Burger", 3, 2021, 18);
        assertTrue(accessICard.deleteCard(creditCard));
        assertFalse(accessICard.deleteCard(creditCard));
        assertFalse(accessICard.deleteCard(creditCard1));
    }

    /**
     * This method tests updating credit cards
     */
    public void testUpdateCreditCard() {
        CreditCard creditCard1 = new CreditCard("mastercard", "5005600670078008", "Cheese Burger", 3, 2021, 18);
        DebitCard debitCard = new DebitCard("mastercard debit", "5615215412345678", "Tommy", 3, 2026);
        assertFalse(accessICard.updateCard(creditCard1, debitCard));
        assertTrue(accessICard.updateCard(creditCard, creditCard1));
        assertFalse(accessICard.updateCard(creditCard, creditCard1));
        assertTrue(accessICard.findCard(creditCard1));
        assertFalse(accessICard.findCard(creditCard));
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
        CreditCard creditCard1 = new CreditCard("mastercard", "5005600670078008", "Cheese Burger", 3, 2021, 18);
        List<CreditCard> list = accessICard.getCreditCards();
        assertTrue(list.contains(creditCard));
        assertFalse(list.contains(creditCard1));
        accessICard.insertCard(creditCard1);
        list = accessICard.getCreditCards();
        assertTrue(list.contains(creditCard));
        assertTrue(list.contains(creditCard1));
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