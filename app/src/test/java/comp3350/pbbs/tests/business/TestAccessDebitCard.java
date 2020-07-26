package comp3350.pbbs.tests.business;

import junit.framework.TestCase;

import java.util.Calendar;
import java.util.List;

import comp3350.pbbs.application.Main;
import comp3350.pbbs.objects.Cards.DebitCard;
import comp3350.pbbs.business.AccessDebitCard;

/**
 * TestAccessDebitCard
 * Group4
 * PBBS
 *
 * This class tests the methods in the AccessDebitCard class
 */
public class TestAccessDebitCard extends TestCase {
    private DebitCard card;        // a DebitCard object
    private AccessDebitCard acc;    // a AccessDebitCard object

    /**
     * This method connects to the database, create and initiate instance variables
     */
    public void setUp() {
        Main.startup();
        card = new DebitCard("CIBC Advantage Debit Card", "4506445712345678", "Jimmy", 12, 2021);
        acc = new AccessDebitCard();
        acc.insertDebitCard(card);
    }

    /**
     * This teardown method disconnects from the database
     */
    public void tearDown() {
        Main.shutDown();
    }

    /**
     * This method tests finding debit cards in the database
     */
    public void testFindDebitCard() {
        DebitCard card1 = new DebitCard("CIBC Advantage Debit Card", "4506445712345678", "Jimmy", 12, 2021);
        assertTrue(acc.findDebitCard(card));
        assertFalse(acc.findDebitCard(card1));
    }

    /**
     * This method tests inserting debit cards
     */
    public void testInsertDebitCard() {
        DebitCard card1 = new DebitCard("CIBC Advantage Debit Card", "4506445712345678", "Jimmy", 12, 2021);
        assertTrue(acc.insertDebitCard(card1));
        assertFalse(acc.insertDebitCard(card1));
    }

    /**
     * This method tests deleting debit cards
     */
    public void testDeleteDebitCard() {
        DebitCard card1 = new DebitCard("CIBC Advantage Debit Card", "4506445712345678", "Jimmy", 12, 2021);
        assertTrue(acc.deleteDebitCard(card));
        assertFalse(acc.deleteDebitCard(card));
        assertFalse(acc.deleteDebitCard(card1));
    }

    /**
     * This method tests updating debit cards
     */
    public void testUpdateDebitCard() {
        DebitCard card1 = new DebitCard("CIBC Advantage Debit Card", "4506445712345678", "Jimmy", 12, 2021);
        assertTrue(acc.updateDebitCard(card, card1));
        assertFalse(acc.updateDebitCard(card, card1));
        assertTrue(acc.findDebitCard(card1));
        assertFalse(acc.findDebitCard(card));
    }

    /**
     * Test validating expiration dates
     */
    public void testExpirationDate() {
        Calendar calender = Calendar.getInstance();
        // int currMonth = calender.get(Calendar.MONTH) + 1; // never used
        int currYear = calender.get(Calendar.YEAR);
        assertEquals(0, AccessDebitCard.isValidExpirationDate("1", "2068"));
        assertEquals(0, AccessDebitCard.isValidExpirationDate("12", "2068"));
        assertEquals(1, AccessDebitCard.isValidExpirationDate("-1", "2068"));
        assertEquals(1, AccessDebitCard.isValidExpirationDate("-20", "2068"));
        assertEquals(1, AccessDebitCard.isValidExpirationDate("13", "2068"));
        assertEquals(1, AccessDebitCard.isValidExpirationDate("24", "2068"));
        assertEquals(2, AccessDebitCard.isValidExpirationDate("1", "2100"));
        assertEquals(2, AccessDebitCard.isValidExpirationDate("1", "3000"));
        assertEquals(3, AccessDebitCard.isValidExpirationDate("13", "2100"));
        assertEquals(3, AccessDebitCard.isValidExpirationDate("24", "3000"));
        assertEquals(4, AccessDebitCard.isValidExpirationDate("1", "-20"));
        assertEquals(4, AccessDebitCard.isValidExpirationDate("-1", "-20"));
        assertEquals(4, AccessDebitCard.isValidExpirationDate("1", "900"));
        assertEquals(4, AccessDebitCard.isValidExpirationDate("1", "90"));
        assertEquals(4, AccessDebitCard.isValidExpirationDate("1", "9"));
        assertEquals(5, AccessDebitCard.isValidExpirationDate("1", Integer.toString(currYear)));
        assertEquals(6, AccessDebitCard.isValidExpirationDate("1", Integer.toString(currYear-1)));
        assertEquals(7, AccessDebitCard.isValidExpirationDate("string", "2068"));
        assertEquals(7, AccessDebitCard.isValidExpirationDate("1", "string"));
        assertEquals(7, AccessDebitCard.isValidExpirationDate("string", "string 2"));
        assertEquals(7, AccessDebitCard.isValidExpirationDate("", "2068"));
        assertEquals(7, AccessDebitCard.isValidExpirationDate(null, "2068"));
        assertEquals(7, AccessDebitCard.isValidExpirationDate("1", ""));
        assertEquals(7, AccessDebitCard.isValidExpirationDate("1", null));
    }

    /**
     * Test retrieving a list of debit cards
     */
    public void testCardList() {
        DebitCard card1 = new DebitCard("CIBC Advantage Debit Card", "4506445712345678", "Jimmy", 12, 2021);
        List<DebitCard> list = acc.getDebitCards();
        assertTrue(list.contains(card));
        assertFalse(list.contains(card1));
        acc.insertDebitCard(card1);
        list = acc.getDebitCards();
        assertTrue(list.contains(card));
        assertTrue(list.contains(card1));
    }

    /**
     * Test validating cardholder names
     */
    public void testName() {
        assertTrue(AccessDebitCard.isValidName("cool name"));
        assertFalse(AccessDebitCard.isValidName(""));
        assertFalse(AccessDebitCard.isValidName(null));
        assertFalse(AccessDebitCard.isValidName("X AE A-12"));
    }
}