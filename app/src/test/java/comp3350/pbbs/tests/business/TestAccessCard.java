package comp3350.pbbs.tests.business;

import junit.framework.TestCase;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import comp3350.pbbs.application.Services;
import comp3350.pbbs.business.AccessCard;
import comp3350.pbbs.objects.BudgetCategory;
import comp3350.pbbs.objects.Card;
import comp3350.pbbs.objects.Transaction;
import comp3350.pbbs.persistence.DataAccess;
import comp3350.pbbs.tests.persistence.StubDatabase;

/**
 * TestAccessCreditCard
 * Group4
 * PBBS
 *
 * This class tests the methods in the AccessCreditCard class
 * NOTE: All of the validation testing for bad input is done in TestAccessValidation.
 */
public class TestAccessCard extends TestCase {
    private DataAccess testDB;
    List<Card> stubCards;
    private AccessCard testAccess;    // a AccessCreditCard object

    //Testing Data
    private Date testDate = new Date(2020 - 07 - 15);
    private Card card = new Card("mastercard", "1001200230034004", "Si-Chuan Hotpot", 12, 2024, 12);
    // a Card object
    private Card card2 = new Card("visa", "1111222233334444", "Si-Chuan Hotpot", 11, 2022, 04);        // a Card object
    private BudgetCategory testBudgetCategory = new BudgetCategory("Houseware", 20);
    private Transaction t1 = new Transaction(testDate, 20.45f, "Watched a movie", card, testBudgetCategory);
    private Transaction t2 = new Transaction(testDate, 40f, "Bought a video game", card, testBudgetCategory);
    private Calendar currMonth;

    /**
     * This method connects to the database, create and initiate instance variables
     */
    public void setUp() {
        testDB = Services.createDataAccess(new StubDatabase("populateTest"));
        stubCards = testDB.getCards();
        testAccess = new AccessCard();
        testAccess.insertCard(card);
        currMonth = Calendar.getInstance();
        currMonth.setTime(testDate);
    }

    /**
     * Testing that all methods work using valid input
     */
    public void testValidInput() {
        //Test Finding input:
        assertTrue(testAccess.findCard(card));

        //tests finding cards already in stubDB
        for (int i = 0; i < stubCards.size(); i++) {
            assertTrue(testAccess.findCard(stubCards.get(i)));
        }

        Card card1 = new Card("mastercard", "5005600670078008", "Cheese Burger", 3, 2021, 18);

        //Test Inserting Card:
        assertTrue(testAccess.insertCard(card1));
        assertFalse(testAccess.insertCard(card1));  //no duplicates

        assertTrue(testAccess.insertCard(new Card("(abcdefg)", "5005600670078888", "abcdefg", 1, 2021, 31)));
        assertTrue(testAccess.insertCard(new Card("(abcdefg)", "5005600677778008", "abcdefg", 12, 2021, 31)));
        assertTrue(testAccess.insertCard(new Card("(abcdefg)", "5005666670078008", "abcdefg", 1, 2021, 1)));
        assertTrue(testAccess.insertCard(new Card("(abcdefg)", "5555600670078008", "abcdefg", 1, 2022, 31)));

        //Testing updating Card
        Card card2 = new Card("mastercard2", "1234123412341234", "Ham Burgler", 3, 2021, 18);
        Card debitCard = new Card("mastercard debit", "5615215412345678", "Tommy", 3, 2026);
        assertFalse(testAccess.updateCard(card2, debitCard));
        assertTrue(testAccess.updateCard(card, card2));
        assertFalse(testAccess.updateCard(card, card2));
        assertTrue(testAccess.findCard(card2));
        assertFalse(testAccess.findCard(card));
    }

    /**
     * Testing that all methods using null input
     */
    public void testNullInput() {
        //Test failure to find card not in DB
        Card card1 = new Card("mastercard", "5005600670078008", "Cheese Burger", 3, 2021, 18);
        assertFalse(testAccess.findCard(card1));

        //Test Inserting Card:
        assertFalse(testAccess.insertCard(null));

        //Testing updating Card
        Card debitCard = new Card("mastercard debit", "5615215412345678", "Tommy", 3, 2026);
        assertFalse(testAccess.updateCard(null, debitCard));
        assertFalse(testAccess.updateCard(card, null));
    }

    /**
     * This method tests deleting credit cards
     */
    // public void testDeleteCreditCard() {
    //     Card card1 = new Card("mastercard", "5005600670078008", "Cheese Burger", 3, 2021, 18);
    //     assertTrue(testAccess.deleteCard(card));
    //     assertFalse(testAccess.deleteCard(card));
    //     assertFalse(testAccess.deleteCard(card1));
    // }

    /**
     * Test retrieving a list of credit cards
     */
    public void testCardList() {
        Card card1 = new Card("mastercard", "5005600670078008", "Cheese Burger", 3, 2021, 18);
        List<Card> list = testAccess.getCreditCards();
        assertTrue(list.contains(card));
        assertFalse(list.contains(card1));
        testAccess.insertCard(card1);
        list = testAccess.getCreditCards();
        assertTrue(list.contains(card));
        assertTrue(list.contains(card1));

        Card debit1 = new Card("Debit 1", "165158684", "Tommy Vercetti", 6, 2077);
        list = testAccess.getDebitCards();
        assertFalse(list.contains(card1));
        assertFalse(list.contains(card));
        assertFalse(list.contains(debit1));
        testAccess.insertCard(debit1);
        list = testAccess.getDebitCards();
        assertFalse(list.contains(card1));
        assertFalse(list.contains(card));
        assertTrue(list.contains(debit1));
    }

    /**
     * Test calculating budget total for invalid inputs
     */
    public void testCalculateInvalidCard() {
        assertEquals(0f, testAccess.calculateCardTotal(null, null));
        assertEquals(0f, testAccess.calculateCardTotal(new Card("Amex", "1000100010001000", "Alan Alfred", 6, 2022, 27), null));
        assertEquals(0f, testAccess.calculateCardTotal(null, Calendar.getInstance()));
    }

    /**
     * Test calculating card total for no transactions
     */
    public void testCalculateNoTransactionsTotal() {
        //The two cards should not have any associated transactions
        assertEquals(0.0f, testAccess.calculateCardTotal(card, currMonth));
        assertEquals(0.0f, testAccess.calculateCardTotal(card, currMonth));
    }

    /**
     * Test calculating card total for a single transaction
     */
    public void testCalculateOneTransactionTotal() {
        testDB.insertTransaction(t1);
        assertEquals(20.45f, testAccess.calculateCardTotal(card, currMonth));
        assertEquals(0.0f, testAccess.calculateCardTotal(card2, currMonth));
        currMonth.add(Calendar.MONTH, 1);
        assertEquals(0.0f, testAccess.calculateCardTotal(card, currMonth));

        //From Stub Database
        currMonth.set(2020, 0, 1);
        assertEquals(50f, testAccess.calculateCardTotal(stubCards.get(0), currMonth));    // Stub rent budget Category
    }

    /**
     * Test calculating card total for multiple transactions
     */
    public void testCalculateMultipleTransactionsTotal() {
        testDB.insertTransaction(t1);
        testDB.insertTransaction(t2);
        assertEquals(60.45f, testAccess.calculateCardTotal(card, currMonth));
        assertEquals(0f, testAccess.calculateCardTotal(card2, currMonth));
        Transaction t3 = new Transaction(testDate, 50.53f, "Ate burger", card2, testBudgetCategory);
        testDB.insertTransaction(t3);
        assertEquals(60.45f, testAccess.calculateCardTotal(card, currMonth));
        assertEquals(50.53f, testAccess.calculateCardTotal(card2, currMonth));

        //From Stub Database
        currMonth.set(2020, 0, 1);
        assertEquals(565f, testAccess.calculateCardTotal(stubCards.get(1), currMonth));    // Stub rent budget Category
    }

    /**
     * Test calculating card total for different transaction months
     */
    public void testCalculateTransactionDifferentMonths() {
        currMonth.add(Calendar.MONTH, 1);
        Transaction t4 = new Transaction(currMonth.getTime(), 40, "Bought a video game", card, testBudgetCategory);
        currMonth.setTime(testDate);
        testDB.insertTransaction(t1);
        testDB.insertTransaction(t4);
        assertEquals(20.45f, testAccess.calculateCardTotal(card, currMonth));
        currMonth.add(Calendar.MONTH, 1);
        assertEquals(40f, testAccess.calculateCardTotal(card, currMonth));
    }

    /**
     * Test getting active months with no transactions
     */
    public void testEmptyActiveMonths() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(testDate);
        List<Calendar> result = testAccess.getActiveMonths(card);
        assertTrue(result.isEmpty());
    }

    /**
     * Test getting active months with a single transaction
     */
    public void testSingleActiveMonth() {
        testDB.insertTransaction(t1);
        List<Calendar> result = testAccess.getActiveMonths(card);
        assertEquals(1, result.size());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(testDate);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        assertTrue(result.contains(calendar));
    }

    /**
     * Test getting active months with multiple transactions
     */
    public void testMultipleActiveMonths() {
        testDB.insertTransaction(t1);
        testDB.insertTransaction(t2);
        List<Calendar> result = testAccess.getActiveMonths(card);
        assertEquals(1, result.size());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(testDate);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        assertTrue(result.contains(calendar));
        calendar.set(1000, 1, 1);
        Transaction t3 = new Transaction(calendar.getTime(), 20, "Bought groceries.", card, testBudgetCategory);
        testDB.insertTransaction(t3);
        result = testAccess.getActiveMonths(card);
        assertEquals(2, result.size());
        assertTrue(result.contains(calendar));
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        assertTrue(result.contains(calendar));
    }

    /**
     * Test getting active months with an invalid input
     */
    public void testInvalidActiveMonths() {
        try {
            testAccess.getActiveMonths(null);
            fail("Expected NullPointerException.");
        } catch (NullPointerException ignored) {

        }
    }

    /**
     * This teardown method disconnects from the database
     */
    public void tearDown() {
        Services.closeDataAccess();
    }

}