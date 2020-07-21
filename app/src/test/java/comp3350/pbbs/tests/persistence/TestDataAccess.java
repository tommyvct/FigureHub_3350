package comp3350.pbbs.tests.persistence;

import android.widget.ArrayAdapter;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertNotEquals;

import comp3350.pbbs.objects.*;
import comp3350.pbbs.application.*;
import comp3350.pbbs.persistence.DataAccess;
import comp3350.pbbs.persistence.DataAccessObject;
import comp3350.pbbs.tests.persistence.StubDatabase;

public class TestDataAccess extends TestCase {
    private DataAccess dataAccess;

    public TestDataAccess(String arg0) {
        super(arg0);
    }

    public void setUp() {
        //initially testing testing will be done on stub database
        dataAccess = new DataAccessObject(Main.dbName);
        dataAccess.open(Main.getDBPathName());
        //switching to HSQL database can also be done by following these 2 lines:
        //dataAccess = new DataAccessObject(Main.dbName);
        //dataAccess.populateData();
    }

    public void tearDown() {
    }

    public void testBudgetCategories() {
        ArrayList<BudgetCategory> budgets;
        boolean result;
        BudgetCategory returnedBudget;

        BudgetCategory b1 = new BudgetCategory("Groceries", 100);
        assertNotNull(b1);//BudgetCategories object created

        //budgets ArrayList created with zero objects
        budgets = new ArrayList<BudgetCategory>();
        assertEquals(0, budgets.size());

        //addBudgetCategories test
        result = dataAccess.addBudgetCategories(budgets);
        assertTrue(result); //all the other budgets added successfully

        //testing the size of the ArrayList
        assertEquals(4, dataAccess.getBudgetsSize());

        //testing findBudgetCategory
        returnedBudget = dataAccess.findBudgetCategory(b1);
        assertEquals(b1, returnedBudget);
        BudgetCategory b2 = new BudgetCategory("Car", 50);
        returnedBudget = dataAccess.findBudgetCategory(b2);
        assertNotEquals(b2, returnedBudget);

        //insertBudgetCategory test
        result = dataAccess.insertBudgetCategory(b2);
        assertTrue(result);
        returnedBudget = dataAccess.findBudgetCategory(b2);
        assertEquals(b2, returnedBudget);
        //duplicate budget can be added
        result = dataAccess.insertBudgetCategory(b1);
        assertTrue(result);
        assertEquals(6, dataAccess.getBudgetsSize());

        //testing the getBudgets
        assertNotEquals(budgets, dataAccess.getBudgets());
        budgets.add(b2);
        budgets.add(b1);
        assertEquals(budgets, dataAccess.getBudgets());

        //testing updateBudgetCategory
        BudgetCategory newBudget = new BudgetCategory("Car Bill", 300);
        returnedBudget = dataAccess.updateBudgetCategory(b2, newBudget);
        assertNotNull(returnedBudget);
        assertNotEquals(budgets, dataAccess.getBudgets());// dataAccess is updated
        assertEquals(newBudget, returnedBudget);//returns the updated BudgetCategory

        //testing deleteBudgetCategory
        returnedBudget = dataAccess.deleteBudgetCategory(newBudget);
        assertNotNull(returnedBudget);
        assertEquals(newBudget, returnedBudget);
        assertNull(dataAccess.findBudgetCategory(newBudget));
        assertEquals(5, dataAccess.getBudgetsSize());
    }

    public void testCreditCards() {
        ArrayList<CreditCard> cards;
        boolean result;

        //cards ArrayList created with zero objects
        cards = new ArrayList<CreditCard>();
        assertEquals(0, cards.size());

        //addAllCreditCards test
        result = dataAccess.addAllCreditCards(cards);
        assertTrue(result); //all the other cards added successfully

        //testing the size of the ArrayList
        assertEquals(2, dataAccess.getCardsSize());

        //testing findCreditCard
        CreditCard card1 = new CreditCard("Card1", "2334222333", "Aziz", 12, 2023, 14);
        result = dataAccess.findCreditCard(card1);
        assertFalse(result);

        //insertCreditCard test
        dataAccess.insertCreditCard(card1);
        result = dataAccess.findCreditCard(card1);
        assertTrue(result); //card added successfully
        assertEquals(3, dataAccess.getCardsSize());
        //duplicate card can't be added
        dataAccess.insertCreditCard(card1);
        assertNotEquals(4, dataAccess.getCardsSize());

        //testing the getCreditCards
        assertNotEquals(cards, dataAccess.getCreditCards());
        cards.add(card1);
        assertEquals(cards, dataAccess.getCreditCards());

        //testing updateCreditCard
        CreditCard newCard = new CreditCard("newCard", "11112222", "Aziz", 12, 2022, 18);
        result = dataAccess.updateCreditCard(card1, newCard);
        assertTrue(result);
        assertNotEquals(cards, dataAccess.getCreditCards());// dataAccess is updated
        assertTrue(dataAccess.findCreditCard(newCard));//newCard has been updated successfully

        //testing deleteCreditCard
        dataAccess.deleteCreditCard(newCard);
        assertFalse(dataAccess.findCreditCard(newCard));//newCard has been deleted
        assertEquals(2, dataAccess.getCardsSize());
    }

    public void testTransaction() {
        ArrayList<Transaction> transactions;
        boolean result;
        Transaction returnedTransaction;
        CreditCard card1 = dataAccess.getCreditCards().get(0);
        BudgetCategory b1 = dataAccess.getBudgets().get(0);

        Date date = new Date();
        Transaction t1 = new Transaction(Services.calcDate(date, -5), 5, "Bought Chips", card1, b1);
        assertNotNull(t1);//Transaction object created

        //transactions ArrayList created with zero objects
        transactions = new ArrayList<Transaction>();
        assertEquals(0, transactions.size());

        //addTransaction test
        result = dataAccess.addTransactions(transactions);
        assertTrue(result); //all the other transactions added successfully

        //testing the size of the ArrayList
        assertEquals(4, dataAccess.getTransactionsSize());

        //testing findTransaction
        returnedTransaction = dataAccess.findTransaction(t1);
        assertNull(returnedTransaction);

        //insertTransaction test
        result = dataAccess.insertTransaction(t1);
        assertTrue(result);//inserted successfully
        returnedTransaction = dataAccess.findTransaction(t1);
        assertEquals(t1, returnedTransaction);
        //duplicate transaction can be added
        result = dataAccess.insertTransaction(t1);
        assertTrue(result);
        assertEquals(6, dataAccess.getTransactionsSize());

        //testing the getTransactions
        assertNotEquals(transactions, dataAccess.getTransactions());
        transactions.add(t1);
        transactions.add(t1);
        assertEquals(transactions, dataAccess.getTransactions());

        //testing updateTransaction
        Transaction newTransaction = new Transaction(Services.calcDate(date, -6), 50, "bill Paid", card1, b1);
        result = dataAccess.updateTransaction(t1, newTransaction);
        assertTrue(result);
        assertNotEquals(transactions, dataAccess.getTransactions());// dataAccess is updated

        //testing deleteTransaction
        result = dataAccess.deleteTransaction(newTransaction);
        assertTrue(result);//deleted successfully
        assertEquals(5, dataAccess.getTransactionsSize());
    }

    public void testUser() {
        assertNull(dataAccess.getUsername());
        dataAccess.setUsername("Aziz");
        assertNotNull(dataAccess.getUsername());
    }

    public void testValidValues() {
        ArrayList<BudgetCategory> budgets;
        ArrayList<CreditCard> cards;
        ArrayList<Transaction> transactions;
        BudgetCategory budgetCategory;
        CreditCard creditCard;
        Transaction transaction;

        budgets = new ArrayList<BudgetCategory>();
        dataAccess.addBudgetCategories(budgets);
        budgetCategory = budgets.get(0);
        assertEquals("Rent/Mortgage", budgetCategory.getBudgetName());
        assertEquals(500.0, budgetCategory.getBudgetLimit());
        assertEquals(4, budgets.size());

        cards = new ArrayList<CreditCard>();
        dataAccess.addAllCreditCards(cards);
        creditCard = cards.get(0);
        assertEquals("Visa", creditCard.getCardName());
        assertEquals("1000100010001000", creditCard.getCardNum());
        assertEquals("Jimmy", creditCard.getHolderName());
        assertEquals(12, creditCard.getExpireMonth());
        assertEquals(2021, creditCard.getExpireYear());
        assertEquals(18, creditCard.getPayDate());
        assertEquals(2, cards.size());

        transactions = new ArrayList<Transaction>();
        dataAccess.addTransactions(transactions);
        transaction = transactions.get(0);
        Date date = new Date();
        //TODO: this test fails sometimes depending on the time change during the test
        assertEquals(Services.calcDate(date, -5), transaction.getTime());

        assertEquals(50.0f, transaction.getAmount());
        assertEquals("Bought Chickens", transaction.getDescription());
        assertEquals(cards.get(0), transaction.getCard());
        assertEquals(budgets.get(1), transaction.getBudgetCategory());

        //user name can be any String
        dataAccess.setUsername(" ");
        assertEquals(" ", dataAccess.getUsername());

    }

    public void testNullValues() {


        try {
            dataAccess.setUsername(null);
            fail("Expected NullPointerException!");
        } catch (NullPointerException e) {
        }

    }
}
