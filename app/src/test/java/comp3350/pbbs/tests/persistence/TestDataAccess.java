package comp3350.pbbs.tests.persistence;

import android.widget.ArrayAdapter;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertNotEquals;

import comp3350.pbbs.objects.*;
import comp3350.pbbs.application.*;
import comp3350.pbbs.objects.Cards.Card;
import comp3350.pbbs.persistence.DataAccess;
import comp3350.pbbs.persistence.DataAccessObject;
import comp3350.pbbs.tests.persistence.StubDatabase;

public class TestDataAccess extends TestCase {
    private DataAccess dataAccess;

    public TestDataAccess(String arg0) {
        super(arg0);
    }

    public void setUp() {
        //initially testing will be done on stub database
//        dataAccess = new StubDatabase("test");
//        DataAccess.populateData(dataAccess);
       // switching to HSQL database can also be done by following these 2 lines:
        dataAccess = new DataAccessObject(Main.dbName);
        dataAccess.open(Main.getDBPathName());
        // If you're testing the data access object, the testValidValues will fail, but If you run
        // It individually itll be fine (darn persistent storage)
    }

    public void tearDown() {
        Services.closeDataAccess();
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

    public void testBankAccount(){
        ArrayList<BankAccount> bankAccounts;
        boolean result;

        //bankAccounts arrayList is created with zero objects
        bankAccounts = new ArrayList<BankAccount>();
        assertNotNull(bankAccounts);
        assertEquals(0, bankAccounts.size());

        //testing findBankAccount
        BankAccount newAccount1;
        newAccount1 = dataAccess.getAllBankAccounts().get(1);

        result = dataAccess.findBankAccount(newAccount1);
        assertTrue(result);
        BankAccount newAccount2 = new BankAccount("Scotia", "12345678", dataAccess.getCards().get(0));
        result = dataAccess.findBankAccount(newAccount2);
        assertFalse(result);

        //testing insertBankAccount
        result = dataAccess.insertBankAccount(newAccount2);
        assertTrue(result);
        //duplicate can't be added
        result = dataAccess.insertBankAccount(newAccount1);
        assertFalse(result);
        assertEquals(3, dataAccess.getAllBankAccounts().size());

        //testing updateBankAccount
        result = dataAccess.updateBankAccount(newAccount1,newAccount2);
        assertFalse(result);//can't update an account with an existing account
        BankAccount updateAccount = new BankAccount("TD","23456",dataAccess.getCards().get(2));
        result = dataAccess.updateBankAccount(newAccount1, updateAccount);
        assertTrue(result);
        assertFalse(dataAccess.findBankAccount(newAccount1));//false, because it has been updated

        //testing getAllBankAccounts
        assertNotEquals(bankAccounts, dataAccess.getAllBankAccounts());
        BankAccount originalAcc = dataAccess.getAllBankAccounts().get(0);
        bankAccounts.add(originalAcc);
        bankAccounts.add(updateAccount);
        bankAccounts.add(newAccount2);
        assertEquals(bankAccounts, dataAccess.getAllBankAccounts());

        //testing getAccountsFromDebitCard
        Card debitCard = dataAccess.getDebitCards().get(0);
        List<BankAccount> returnedAccounts = new ArrayList<>();
        returnedAccounts = dataAccess.getAccountsFromDebitCard(debitCard);
        assertNotNull(returnedAccounts);
        assertNotEquals(bankAccounts, returnedAccounts);//not all the bankAccounts are from same card
        assertEquals(1, returnedAccounts.size());//total bank accounts associated with the card is one
   }

    public void testCards() {
        ArrayList<Card> cards;
        boolean result;

        //cards ArrayList created with zero objects
        cards = new ArrayList<Card>();
        assertNotNull(cards);
        assertEquals(0, cards.size());

        //testing the size of the ArrayList
        assertEquals(5, dataAccess.getCards().size());

        //testing findCard
        Card card1 = new Card("Card1", "2334222333", "Aziz", 12, 2023, 14);
        result = dataAccess.findCard(card1);
        assertFalse(result);
        Card newCard = dataAccess.getCards().get(0);
        result = dataAccess.findCard(newCard);
        assertTrue(result);

        //insertCard test
        dataAccess.insertCard(card1);
        result = dataAccess.findCard(card1);
        assertTrue(result); //card added successfully
        assertEquals(6, dataAccess.getCards().size());
        //duplicate card can't be added
        dataAccess.insertCard(card1);
        assertNotEquals(7, dataAccess.getCards().size());

        //testing the getCards
        assertNotEquals(cards, dataAccess.getCards());
        cards.add(card1);
        assertTrue(dataAccess.getCards().containsAll(cards));

        //testing updateCard
        Card newCard2 = new Card("newCard2", "11112222", "Aziz", 12, 2022, 18);
        result = dataAccess.updateCard(card1, newCard2);
        assertTrue(result);
        assertNotEquals(cards, dataAccess.getCards());// dataAccess is updated
        assertTrue(dataAccess.findCard(newCard2));//newCard2 has been updated successfully

        //testing the size of different cards
        assertEquals(3, dataAccess.getCreditCardsSize());
        assertEquals(3, dataAccess.getDebitCardsSize());

        //testing markInactive
        assertTrue(dataAccess.markInactive(newCard));
        Card newCard3 = new Card("card3","2324","Aziz",12,2023,12);
        assertFalse(dataAccess.markInactive(newCard3));//doesn't exist in the card list

        //testing getActiveCards
        assertNotNull(dataAccess.getActiveCards());
        assertEquals(5, dataAccess.getActiveCards().size()); //previously inactivated one card

        //testing getCreditCards & getDebitCards
        assertNotEquals(dataAccess.getCreditCards(), dataAccess.getDebitCards());
        assertEquals(3,dataAccess.getCreditCards().size());
        assertEquals(3,dataAccess.getDebitCards().size());

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
        List<BankAccount> bankAccounts;
        BudgetCategory budgetCategory;
        Card Card;
        Transaction transaction;
        BankAccount bankAccount;

        //testing budgetCategory with valid input
        budgets = dataAccess.getBudgets();
        budgetCategory = budgets.get(0);
        assertEquals("Rent/Mortgage", budgetCategory.getBudgetName());
        assertEquals(500.0, budgetCategory.getBudgetLimit());
        assertEquals(4, budgets.size());

        //testing cards with valid input
        cards = dataAccess.getCards();
        Card = cards.get(0);
        assertEquals("Visa", Card.getCardName());
        assertEquals("1000100010001000", Card.getCardNum());
        assertEquals("Jimmy", Card.getHolderName());
        assertEquals(12, Card.getExpireMonth());
        assertEquals(2021, Card.getExpireYear());
        assertEquals(18, Card.getPayDate());
        assertEquals(5, cards.size());


        //testing transaction with valid inputs
        transactions = dataAccess.getTransactions();
        transaction = transactions.get(0);
        Date date = new Date();
        assertEquals(50.0f, transaction.getAmount());
        assertEquals("Bought Chickens", transaction.getDescription());
        assertEquals(cards.get(0), transaction.getCard());
        assertEquals(budgets.get(1), transaction.getBudgetCategory());

        //testing bankAccount with valid inputs
        bankAccounts = dataAccess.getAllBankAccounts();
        bankAccount = bankAccounts.get(0);
        assertEquals("TD student banking", bankAccount.getAccountName());
        assertEquals("50998924", bankAccount.getAccountNumber());
        assertEquals(dataAccess.getCards().get(3), bankAccount.getLinkedCard());

        //userName testing with valid input
        dataAccess.setUsername(" ");
        assertEquals(" ", dataAccess.getUsername());

    }

    public void testEdgeCases() {
        try {
            dataAccess.setUsername(null);
            fail("Expected NullPointerException!");
        } catch (NullPointerException ignored) {
        }

        //BankAccount with null name is valid
        BankAccount nullNameAccount = new BankAccount(null,"123456", dataAccess.getCards().get(3));
        assertNotNull(nullNameAccount);
        assertTrue(dataAccess.insertBankAccount(nullNameAccount));

        //BankAccount with space name is valid
        BankAccount spaceNameAccount = new BankAccount(" ", "123232", dataAccess.getCards().get(1));
        assertNotNull(spaceNameAccount);
        assertTrue(dataAccess.insertBankAccount(spaceNameAccount));

    }
}
