package comp3350.pbbs.tests.business;

import junit.framework.TestCase;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import comp3350.pbbs.application.Main;
import comp3350.pbbs.application.Services;
import comp3350.pbbs.objects.BudgetCategory;
import comp3350.pbbs.objects.CreditCard;
import comp3350.pbbs.business.AccessCreditCard;
import comp3350.pbbs.objects.Transaction;
import comp3350.pbbs.persistence.StubDatabase;

/**
 * TestAccessCreditCard
 * Group4
 * PBBS
 *
 * This class tests the methods in the AccessCreditCard class
 */
public class TestAccessCreditCard extends TestCase {
    private CreditCard card;        // a CreditCard object
    private CreditCard card2;        // a CreditCard object
    private AccessCreditCard testAccess;    // a AccessCreditCard object

    /**
     * This method connects to the database, create and initiate instance variables
     */
    public void setUp() {
        Main.startup();
        card = new CreditCard("mastercard", "1001200230034004", "Si-Chuan Hotpot", 12, 2024, 12);
        card2 = new CreditCard("visa", "1111222233334444", "Si-Chuan Hotpot", 11, 2022, 04);
        testAccess = new AccessCreditCard();
        testAccess.insertCreditCard(card);
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
        assertTrue(testAccess.findCreditCard(card));
        assertFalse(testAccess.findCreditCard(card1));
    }

    /**
     * This method tests inserting credit cards
     */
    public void testInsertCreditCard() {
        CreditCard card1 = new CreditCard("mastercard", "5005600670078008", "Cheese Burger", 3, 2021, 18);
        assertTrue(testAccess.insertCreditCard(card1));
        assertFalse(testAccess.insertCreditCard(card1));
    }

    /**
     * This method tests deleting credit cards
     */
    public void testDeleteCreditCard() {
        CreditCard card1 = new CreditCard("mastercard", "5005600670078008", "Cheese Burger", 3, 2021, 18);
        assertTrue(testAccess.deleteCreditCard(card));
        assertFalse(testAccess.deleteCreditCard(card));
        assertFalse(testAccess.deleteCreditCard(card1));
    }

    /**
     * This method tests updating credit cards
     */
    public void testUpdateCreditCard() {
        CreditCard card1 = new CreditCard("mastercard", "5005600670078008", "Cheese Burger", 3, 2021, 18);
        assertTrue(testAccess.updateCreditCard(card, card1));
        assertFalse(testAccess.updateCreditCard(card, card1));
        assertTrue(testAccess.findCreditCard(card1));
        assertFalse(testAccess.findCreditCard(card));
    }

    /**
     * Test validating pay dates
     */
    public void testPayDay() {
        assertTrue(testAccess.isValidPayDate(1));
        assertTrue(testAccess.isValidPayDate(15));
        assertTrue(testAccess.isValidPayDate(31));
        assertFalse(testAccess.isValidPayDate(-1));
        assertFalse(testAccess.isValidPayDate(0));
        assertFalse(testAccess.isValidPayDate(-15));
        assertFalse(testAccess.isValidPayDate(32));
        assertFalse(testAccess.isValidPayDate(64));
    }

    /**
     * Test validating expiration dates
     */
    public void testExpirationDate() {
        Calendar calender = Calendar.getInstance();
        int currMonth = calender.get(Calendar.MONTH) + 1;
        int currYear = calender.get(Calendar.YEAR);
        assertEquals(0, testAccess.isValidExpirationDate("1", "2068"));
        assertEquals(0, testAccess.isValidExpirationDate("12", "2068"));
        assertEquals(1, testAccess.isValidExpirationDate("-1", "2068"));
        assertEquals(1, testAccess.isValidExpirationDate("-20", "2068"));
        assertEquals(1, testAccess.isValidExpirationDate("13", "2068"));
        assertEquals(1, testAccess.isValidExpirationDate("24", "2068"));
        assertEquals(2, testAccess.isValidExpirationDate("1", "2100"));
        assertEquals(2, testAccess.isValidExpirationDate("1", "3000"));
        assertEquals(3, testAccess.isValidExpirationDate("13", "2100"));
        assertEquals(3, testAccess.isValidExpirationDate("24", "3000"));
        assertEquals(4, testAccess.isValidExpirationDate("1", "-20"));
        assertEquals(4, testAccess.isValidExpirationDate("-1", "-20"));
        assertEquals(4, testAccess.isValidExpirationDate("1", "900"));
        assertEquals(4, testAccess.isValidExpirationDate("1", "90"));
        assertEquals(4, testAccess.isValidExpirationDate("1", "9"));
        assertEquals(5, testAccess.isValidExpirationDate("1", Integer.toString(currYear)));
        assertEquals(6, testAccess.isValidExpirationDate("1", Integer.toString(currYear-1)));
        assertEquals(7, testAccess.isValidExpirationDate("string", "2068"));
        assertEquals(7, testAccess.isValidExpirationDate("1", "string"));
        assertEquals(7, testAccess.isValidExpirationDate("string", "string 2"));
        assertEquals(7, testAccess.isValidExpirationDate("", "2068"));
        assertEquals(7, testAccess.isValidExpirationDate(null, "2068"));
        assertEquals(7, testAccess.isValidExpirationDate("1", ""));
        assertEquals(7, testAccess.isValidExpirationDate("1", null));
    }

    /**
     * Test retrieving a list of credit cards
     */
    public void testCardList() {
        CreditCard card1 = new CreditCard("mastercard", "5005600670078008", "Cheese Burger", 3, 2021, 18);
        List<CreditCard> list = testAccess.getCreditCards();
        assertTrue(list.contains(card));
        assertFalse(list.contains(card1));
        testAccess.insertCreditCard(card1);
        list = testAccess.getCreditCards();
        assertTrue(list.contains(card));
        assertTrue(list.contains(card1));
    }

    /**
     * Test validating cardholder names
     */
    public void testName() {
        assertTrue(testAccess.isValidName("cool name"));
        assertFalse(testAccess.isValidName(""));
        assertFalse(testAccess.isValidName(null));
        assertFalse(testAccess.isValidName("X AE A-12"));
    }

    /**
     * Test calculating budget total for invalid inputs
     */
    public void testCalculateInvalidCreditCard() {
        assertEquals(0f, testAccess.calculateCreditCardTotal(null, null));
        assertEquals(0f, testAccess.calculateCreditCardTotal(new CreditCard("Amex", "1000100010001000", "Alan Alfred", 6, 2022, 27), null));
        assertEquals(0f, testAccess.calculateCreditCardTotal(null, Calendar.getInstance()));
    }

    /**
     * Test calculating budget total for no transactions
     */
    public void testCalculateNoTransactionsTotal() {
        Calendar currMonth = Calendar.getInstance();
        currMonth.setTime(new Date());
        //The two budget categories should not have any associated transactions
        assertEquals(0.0f, testAccess.calculateCreditCardTotal(card, currMonth));
        assertEquals(0.0f, testAccess.calculateCreditCardTotal(card, currMonth));
        CreditCard testCard = new CreditCard("Amex", "1000100010001000", "Alan Alfred", 6, 2022, 27);
    }

    /**
     * Test calculating budget total for a single transaction
     */
    public void testCalculateOneTransactionTotal() {
        BudgetCategory testBudgetCategory = new BudgetCategory("Houseware", 20);
        Transaction t1 = new Transaction(new Date(), 20, "Played at the arcade", card, testBudgetCategory);
        StubDatabase db = Services.getDataAccess("TBCU");
        Calendar currMonth = Calendar.getInstance();
        currMonth.setTime(new Date());
        db.insertTransaction(t1);
        assertEquals(20.0f, testAccess.calculateCreditCardTotal(card, currMonth));
        assertEquals(0.0f, testAccess.calculateCreditCardTotal(card2, currMonth));
        currMonth.add(Calendar.MONTH, 1);
        assertEquals(0.0f, testAccess.calculateCreditCardTotal(card, currMonth));
    }

    /**
     * Test calculating budget total for multiple transactions
     */
    public void testCalculateMultipleTransactionsTotal() {
        BudgetCategory testBudgetCategory = new BudgetCategory("Houseware", 20);
        Transaction t1 = new Transaction(new Date(), 20.50f, "Watched a movie", card, testBudgetCategory);
        Transaction t2 = new Transaction(new Date(), 40.45f, "Bought a video game", card, testBudgetCategory);
        StubDatabase db = Services.getDataAccess("TBCU");
        Calendar currMonth = Calendar.getInstance();
        currMonth.setTime(new Date());
        db.insertTransaction(t1);
        db.insertTransaction(t2);
        assertEquals(60.95f, testAccess.calculateCreditCardTotal(card, currMonth));
        assertEquals(0f, testAccess.calculateCreditCardTotal(card2, currMonth));
        Transaction t3 = new Transaction(new Date(), 50, "Ate burger", card2, testBudgetCategory);
        db.insertTransaction(t3);
        assertEquals(60.95f, testAccess.calculateCreditCardTotal(card, currMonth));
        assertEquals(50f, testAccess.calculateCreditCardTotal(card2, currMonth));
    }

    public void testCalculateTransactionDifferentMonths() {
        BudgetCategory testBudgetCategory = new BudgetCategory("Houseware", 20);
        Transaction t1 = new Transaction(new Date(), 20, "Watched a movie", card, testBudgetCategory);
        Calendar currMonth = Calendar.getInstance();
        currMonth.setTime(new Date());
        currMonth.add(Calendar.MONTH, 1);
        Transaction t2 = new Transaction(currMonth.getTime(), 40, "Bought a video game", card, testBudgetCategory);
        StubDatabase db = Services.getDataAccess("TBCU");
        currMonth.setTime(new Date());
        db.insertTransaction(t1);
        db.insertTransaction(t2);
        assertEquals(20f, testAccess.calculateCreditCardTotal(card, currMonth));
        currMonth.add(Calendar.MONTH, 1);
        assertEquals(40f, testAccess.calculateCreditCardTotal(card, currMonth));
    }
}