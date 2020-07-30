package comp3350.pbbs.tests.persistence;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertNotEquals;

import comp3350.pbbs.objects.*;
import comp3350.pbbs.application.*;
import comp3350.pbbs.objects.Card;
import comp3350.pbbs.persistence.DataAccess;

public class TestDataAccess extends TestCase
{
    private DataAccess dataAccess;

    public TestDataAccess(String arg0) {
        super(arg0);
    }

    public void setUp() {
        // initially testing will be done on stub database
        dataAccess = new StubDatabase("test");
        DataAccess.populateData(dataAccess);
        // switching to HSQL database can also be done by following these 2 lines:
        // dataAccess = new DataAccessObject(Main.dbName);
        // dataAccess.open(Main.getDBPathName());
        // If you're testing the data access object, the testValidValues will fail, but If you run
        // It individually itll be fine (darn persistent storage)
    }

    public void tearDown() {
    }

    public void testBudgetCategories() {
        ArrayList<BudgetCategory> budgets;
        boolean result;
        boolean returnedBudget;

        BudgetCategory b1 = new BudgetCategory("Groceries", 100);
        assertNotNull(b1);//BudgetCategories object created

        //budgets ArrayList created with zero objects
        budgets = new ArrayList<BudgetCategory>();
        assertEquals(0, budgets.size());

        //testing the size of the ArrayList
        assertEquals(4, dataAccess.getBudgetsSize());

        //testing findBudgetCategory
        returnedBudget = dataAccess.findBudgetCategory(b1);
        assertTrue(returnedBudget);
        BudgetCategory b2 = new BudgetCategory("Car", 50); //not in the list
        returnedBudget = dataAccess.findBudgetCategory(b2);
        assertFalse(returnedBudget);

        //insertBudgetCategory test
        result = dataAccess.insertBudgetCategory(b2);
        assertTrue(result);
        returnedBudget = dataAccess.findBudgetCategory(b2);
        assertTrue(returnedBudget);
        //duplicate budget can be added
        result = dataAccess.insertBudgetCategory(b1);
        assertTrue(result);
        assertEquals(6, dataAccess.getBudgetsSize());

        //testing the getBudgets
        assertNotEquals(budgets, dataAccess.getBudgets());
        budgets.add(b2);
        budgets.add(b1);
        assertTrue(dataAccess.getBudgets().containsAll(budgets));

        //testing updateBudgetCategory
        BudgetCategory newBudget = new BudgetCategory("Car Bill", 300);
        returnedBudget = dataAccess.updateBudgetCategory(b2, newBudget);
        assertTrue(returnedBudget);
        assertFalse(dataAccess.getBudgets().containsAll(budgets));// dataAccess is updated
    }

    public void testCards() {
        ArrayList<Card> cards;
        boolean result;

        //cards ArrayList created with zero objects
        cards = new ArrayList<Card>();
        assertEquals(0, cards.size());

        //testing the size of the ArrayList
        //System.out.println(dataAccess.getCards());
        //assertEquals(2, dataAccess.getCardsSize());

        //testing findCard
        Card card1 = new Card("Card1", "2334222333", "Aziz", 12, 2023, 14);
        result = dataAccess.findCard(card1);
        assertFalse(result);

        //insertCard test
        dataAccess.insertCard(card1);
        result = dataAccess.findCard(card1);
        assertTrue(result); //card added successfully
        //assertEquals(3, dataAccess.getCardsSize());
        //duplicate card can't be added
        dataAccess.insertCard(card1);
        //assertNotEquals(4, dataAccess.getCardsSize());

        //testing the getCards
        assertNotEquals(cards, dataAccess.getCards());
        cards.add(card1);
        assertTrue(dataAccess.getCards().containsAll(cards));

        //testing updateCard
        Card newCard = new Card("newCard", "11112222", "Aziz", 12, 2022, 18);
        result = dataAccess.updateCard(card1, newCard);
        assertTrue(result);
        assertNotEquals(cards, dataAccess.getCards());// dataAccess is updated
        assertTrue(dataAccess.findCard(newCard));//newCard has been updated successfully
    }

    public void testTransaction() {
        ArrayList<Transaction> transactions;
        boolean result;
        boolean returnedTransaction;
        Card card1 = dataAccess.getCards().get(0);
        BudgetCategory b1 = dataAccess.getBudgets().get(0);

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        date = calendar.getTime();
        Transaction t1 = new Transaction(Services.calcDate(date, -5), 5, "Bought Chips", card1, b1);
        assertNotNull(t1);//Transaction object created

        //transactions ArrayList created with zero objects
        transactions = new ArrayList<Transaction>();
        assertEquals(0, transactions.size());

        //testing the size of the ArrayList
        assertEquals(4, dataAccess.getTransactionsSize());

        //testing findTransaction
        returnedTransaction = dataAccess.findTransaction(t1);
        assertFalse(returnedTransaction);

        //insertTransaction test
        result = dataAccess.insertTransaction(t1);
        assertTrue(result);//inserted successfully
        returnedTransaction = dataAccess.findTransaction(t1);
        assertTrue(returnedTransaction);
        //duplicate transaction can be added
        result = dataAccess.insertTransaction(t1);
        assertTrue(result);
        assertEquals(6, dataAccess.getTransactionsSize());

        //testing the getTransactions
        assertNotEquals(transactions, dataAccess.getTransactions());
        transactions.add(t1);
        transactions.add(t1);
        assertTrue(dataAccess.getTransactions().containsAll(transactions));

        //testing updateTransaction
        Transaction newTransaction = new Transaction(Services.calcDate(date, -6), 50, "bill Paid", card1, b1);
        result = dataAccess.updateTransaction(t1, newTransaction);
        assertTrue(result);
        assertNotEquals(transactions, dataAccess.getTransactions());// dataAccess is updated
    }

    public void testUser() {
        //No user name is set
        try {
            dataAccess.getUsername();
            fail("Expected NullPointerException!");
        } catch (NullPointerException ignored) {
        }
        dataAccess.setUsername("Aziz");
        assertNotNull(dataAccess.getUsername());
    }

    public void testValidValues() {
        List<BudgetCategory> budgets;
        List<Card> cards;
        List<Transaction> transactions;
        BudgetCategory budgetCategory;
        Card Card;
        Transaction transaction;

        budgets = dataAccess.getBudgets();
        budgetCategory = budgets.get(0);
        assertEquals("Rent/Mortgage", budgetCategory.getBudgetName());
        assertEquals(500.0, budgetCategory.getBudgetLimit());
        assertEquals(4, budgets.size());

        cards = dataAccess.getCards();
        Card = cards.get(0);
        assertEquals("Visa", Card.getCardName());
        assertEquals("1000100010001000", Card.getCardNum());
        assertEquals("Jimmy", Card.getHolderName());
        assertEquals(12, Card.getExpireMonth());
        assertEquals(2021, Card.getExpireYear());
        assertEquals(18, Card.getPayDate());
        assertEquals(5, cards.size());

        transactions = dataAccess.getTransactions();
        transaction = transactions.get(0);
        Date date = new Date();

        assertEquals(50.0f, transaction.getAmount());
        assertEquals("Bought Chickens", transaction.getDescription());
        assertEquals(cards.get(0), transaction.getCard());
        assertEquals(budgets.get(1), transaction.getBudgetCategory());

        //No user name is set
        try {
            dataAccess.getUsername();
            fail("Expected NullPointerException!");
        } catch (NullPointerException ignored) {
        }
        //user name can be any String
        dataAccess.setUsername(" ");
        assertEquals(" ", dataAccess.getUsername());

    }

    public void testNullValues() {
        try {
            dataAccess.setUsername(null);
            fail("Expected NullPointerException!");
        } catch (NullPointerException ignored) {
        }
    }
}
