package comp3350.pbbs.tests.business;

import junit.framework.TestCase;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import comp3350.pbbs.business.AccessCard;
import comp3350.pbbs.business.CardTransactionLinker;
import comp3350.pbbs.objects.BudgetCategory;
import comp3350.pbbs.objects.Card;
import comp3350.pbbs.objects.Transaction;
import comp3350.pbbs.persistence.DataAccessController;
import comp3350.pbbs.persistence.DataAccessI;
import comp3350.pbbs.tests.persistence.StubDatabase;

public class TestCardTransactionLinker extends TestCase {
    private DataAccessI testDB;
    List<Card> stubCards;
    private CardTransactionLinker cardTransactionLinker;    // a AccessCreditCard object

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
        testDB = DataAccessController.createDataAccess(new StubDatabase("populateTest"));
        stubCards = testDB.getCards();
        cardTransactionLinker = new CardTransactionLinker();
        testDB.insertCard(card);
        currMonth = Calendar.getInstance();
        currMonth.setTime(testDate);
    }

    /**
     * Test calculating budget total for invalid inputs
     */
    public void testCalculateInvalidCard() {
        assertEquals(0f, cardTransactionLinker.calculateCardTotal(null, null));
        assertEquals(0f, cardTransactionLinker.calculateCardTotal(new Card("Amex", "1000100010001000", "Alan Alfred", 6, 2022, 27), null));
        assertEquals(0f, cardTransactionLinker.calculateCardTotal(null, Calendar.getInstance()));
    }

    /**
     * Test calculating card total for no transactions
     */
    public void testCalculateNoTransactionsTotal() {
        //The two cards should not have any associated transactions
        assertEquals(0.0f, cardTransactionLinker.calculateCardTotal(card, currMonth));
        assertEquals(0.0f, cardTransactionLinker.calculateCardTotal(card, currMonth));
    }

    /**
     * Test calculating card total for a single transaction
     */
    public void testCalculateOneTransactionTotal() {
        testDB.insertTransaction(t1);
        assertEquals(20.45f, cardTransactionLinker.calculateCardTotal(card, currMonth));
        assertEquals(0.0f, cardTransactionLinker.calculateCardTotal(card2, currMonth));
        currMonth.add(Calendar.MONTH, 1);
        assertEquals(0.0f, cardTransactionLinker.calculateCardTotal(card, currMonth));

        //From Stub Database
        currMonth.set(2020, 0, 1);
        assertEquals(50f, cardTransactionLinker.calculateCardTotal(stubCards.get(0), currMonth));    // Stub rent budget Category
    }

    /**
     * Test calculating card total for multiple transactions
     */
    public void testCalculateMultipleTransactionsTotal() {
        testDB.insertTransaction(t1);
        testDB.insertTransaction(t2);
        assertEquals(60.45f, cardTransactionLinker.calculateCardTotal(card, currMonth));
        assertEquals(0f, cardTransactionLinker.calculateCardTotal(card2, currMonth));
        Transaction t3 = new Transaction(testDate, 50.53f, "Ate burger", card2, testBudgetCategory);
        testDB.insertTransaction(t3);
        assertEquals(60.45f, cardTransactionLinker.calculateCardTotal(card, currMonth));
        assertEquals(50.53f, cardTransactionLinker.calculateCardTotal(card2, currMonth));

        //From Stub Database
        currMonth.set(2020, 0, 1);
        assertEquals(565f, cardTransactionLinker.calculateCardTotal(stubCards.get(1), currMonth));    // Stub rent budget Category
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
        assertEquals(20.45f, cardTransactionLinker.calculateCardTotal(card, currMonth));
        currMonth.add(Calendar.MONTH, 1);
        assertEquals(40f, cardTransactionLinker.calculateCardTotal(card, currMonth));
    }

    /**
     * Test getting active months with no transactions
     */
    public void testEmptyActiveMonths() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(testDate);
        List<Calendar> result = cardTransactionLinker.getActiveMonths(card);
        assertTrue(result.isEmpty());
    }

    /**
     * Test getting active months with a single transaction
     */
    public void testSingleActiveMonth() {
        testDB.insertTransaction(t1);
        List<Calendar> result = cardTransactionLinker.getActiveMonths(card);
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
        List<Calendar> result = cardTransactionLinker.getActiveMonths(card);
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
        result = cardTransactionLinker.getActiveMonths(card);
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
            cardTransactionLinker.getActiveMonths(null);
            fail("Expected NullPointerException.");
        } catch (NullPointerException ignored) {

        }
    }
    public void tearDown() {
        DataAccessController.closeDataAccess();
    }
}
