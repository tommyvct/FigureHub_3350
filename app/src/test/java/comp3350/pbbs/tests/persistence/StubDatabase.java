package comp3350.pbbs.tests.persistence;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import comp3350.pbbs.application.Main;
import comp3350.pbbs.application.Services;
import comp3350.pbbs.objects.BudgetCategory;
import comp3350.pbbs.objects.CreditCard;
import comp3350.pbbs.objects.Transaction;
import comp3350.pbbs.persistence.DataAccess;

/**
 * StubDatabase
 * Group4
 * PBBS
 * <p>
 * This class defines the persistence layer (stub database).
 */
public class StubDatabase implements DataAccess {
    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private String dbName;                          //name of the database, not used in iteration 1
    private String dbType = "stub";
    private ArrayList<BudgetCategory> budgets;      //ArrayList for budgets
    private ArrayList<CreditCard> creditCards;      //ArrayList for credit cards
    private ArrayList<Transaction> transactions;    //ArrayList for transactions
    private String username;                        //"Hi, {username}!"

    /**
     * This method is the constructor of the database stub
     *
     * @param name name of the database
     */
    public StubDatabase(String name) {
        this.dbName = name;
        budgets = new ArrayList<>();
        creditCards = new ArrayList<>();
        transactions = new ArrayList<>();
    }

    /**
     * Opens the database by populating with fake data.
     *
     * @param dbPath    Database path
     */
    public void open(String dbPath) {
        if(dbPath.contains("populate"))
            populateData();
    }

    /**
     * This method is used for populating fake data into the stub database
     */
    public void populateData() {
        BudgetCategory rent, groceries, utilities, phoneBill;   //various types of BudgetCategories
        CreditCard card1, card2;                                //variables for multiple cards
        Transaction t1, t2, t3, t4;                             //variables for multiple transactions

        rent = new BudgetCategory("Rent/Mortgage", 500);
        budgets.add(rent);
        groceries = new BudgetCategory("Groceries", 100);
        budgets.add(groceries);
        utilities = new BudgetCategory("Utilities", 80);
        budgets.add(utilities);
        phoneBill = new BudgetCategory("Phone Bill", 75);
        budgets.add(phoneBill);

        card1 = new CreditCard("Visa", "1000100010001000", "Jimmy", 12, 2021, 18);
        creditCards.add(card1);
        card2 = new CreditCard("Mastercard", "1002100310041005", "Jimmy", 11, 2021, 15);
        creditCards.add(card2);

        //local date variable
        Date date = new Date();
        t1 = new Transaction(Services.calcDate(date, -5), 50, "Bought Chickens", card1, groceries);
        transactions.add(t1);
        t2 = new Transaction(Services.calcDate(date, -8), 450, "Rent Paid", card2, rent);
        transactions.add(t2);
        t3 = new Transaction(Services.calcDate(date, 2), 40, "Hydro bill paid", card2, utilities);
        transactions.add(t3);
        t4 = new Transaction(Services.calcDate(date, 3), 75, "Phone Bill paid", card2, phoneBill);
        transactions.add(t4);

        username = null;
        System.out.println("Opened " + dbType + " database " + dbName);
    }

    public void close() {
        System.out.println("Closed " + dbType + " database " + dbName);
    }

    /**
     * This method will add all the budgets to a budget list
     *
     * @return true if added successfully.
     */
    public boolean addBudgetCategories(List<BudgetCategory> budgetList) {
        return budgets.addAll(budgetList);
    }

    /**
     * This method will find if a budget exist or not
     *
     * @return True if found, false if not found
     */
    public boolean findBudgetCategory(BudgetCategory currentBudget) {
        return budgets.contains(currentBudget);
    }

    /**
     * This method will insert a new budget category with the budgets ArrayList.
     */
    public boolean insertBudgetCategory(BudgetCategory newBudget) {
        return budgets.add(newBudget);
    }

    /**
     * Getter method to get the budgets.
     *
     * @return budgets ArrayList.
     */
    public ArrayList<BudgetCategory> getBudgets() {
        return budgets;
    }

    /**
     * This method will be used to update a Budget.
     *
     * @return True if the budget category was updated successfully, or false if not
     */
    public boolean updateBudgetCategory(BudgetCategory currentBudget, BudgetCategory newBudget) {
        int index = budgets.indexOf(currentBudget);
        boolean changed = false;
        if (index >= 0) {
            BudgetCategory result = budgets.set(index, newBudget);
            changed = result != null;
        }
        return changed;
    }

    /**
     * This method will remove a budget category.
     *
     * @return True if the budget category was deleted, or false if not
     */
    public boolean deleteBudgetCategory(BudgetCategory currentBudget) {
        return budgets.remove(currentBudget);
    }

    @Override
    public int getBudgetsSize() {
        return budgets.size();
    }

    /**
     * This method will add all the cards to a card list.
     *
     * @return true if added successfully.
     */
    public boolean addAllCreditCards(List<CreditCard> cardList) {
        return creditCards.addAll(cardList);
    }

    /**
     * This method will find if a card exist or not.
     *
     * @return the card object.
     */
    public boolean findCreditCard(CreditCard currCard) {
        return creditCards.contains(currCard);
    }

    /**
     * This method will insert a new card with the ArrayList.
     */
    public boolean insertCreditCard(CreditCard newCard) {
        boolean result = false;
        if (!findCreditCard(newCard)) {
            result = creditCards.add(newCard);
        }
        return result;
    }

    /**
     * Getter method to get the credit cards.
     *
     * @return creditCards ArrayList.
     */
    public ArrayList<CreditCard> getCreditCards() {
        return creditCards;
    }

    /**
     * This method will be used to update a credit card.
     *
     * @return true if updated correctly.
     */
    public boolean updateCreditCard(CreditCard currCard, CreditCard newCard) {
        int index = creditCards.indexOf(currCard);
        boolean result = false;
        if (index >= 0) {
            creditCards.set(index, newCard);
            result = true;
        }
        return result;
    }

    /**
     * This method will remove a credit card.
     *
     * @return True if removed successfully
     */
    public boolean deleteCreditCard(CreditCard currCard) {
        boolean result = false;
        if (creditCards.contains(currCard)) {
            result = creditCards.remove(currCard);
        }
        return result;
    }

    @Override
    public int getCardsSize() {
        return creditCards.size();
    }

    /**
     * This method will add all the transactions to a transaction list.
     *
     * @return true if added successfully.
     */
    public boolean addTransactions(List<Transaction> transactionsList) {
        return transactions.addAll(transactionsList);
    }

    /**
     * This method will find if a transaction exist or not.
     *
     * @return True if found, or false if not found
     */
    @SuppressWarnings("unused")  // will be used at some point in the future
    public boolean findTransaction(Transaction currentTransaction) {
        return transactions.contains(currentTransaction);
    }

    /**
     * This method will insert a new transaction with the ArrayList.
     *
     * @return true if inserted the transaction properly.
     */
    public boolean insertTransaction(Transaction newTransaction) {
        return transactions.add(newTransaction);
    }

    /**
     * Getter method to get the transactions.
     *
     * @return transactions ArrayList.
     */
    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * This method will be used to update a transaction.
     *
     * @return true if updated successfully.
     */
    public boolean updateTransaction(Transaction currentTransaction, Transaction newTransaction) {
        boolean toReturn = false;
        int index = transactions.indexOf(currentTransaction);
        if (index >= 0) {
            toReturn = transactions.set(index, newTransaction) != null;
        }
        return toReturn;
    }

    /**
     * This method will be used to remove a transaction.
     *
     * @return true if deleted successfully
     */
    public boolean deleteTransaction(Transaction currentTransaction) {
        boolean toReturn = false;
        int index = transactions.indexOf(currentTransaction);
        if (index >= 0) {
            toReturn = transactions.remove(index) != null;
        }
        return toReturn;
    }

    @Override
    public int getTransactionsSize() {
        return transactions.size();
    }

    /**
     * Getter for username
     *
     * @return username
     */
    public String getUsername() {
        if(username == null)
            throw new NullPointerException("No username is set");
        return username;
    }

    /**
     * setter for username, used when renaming
     * the username could be anything single line.
     * this is ensured on presentation side
     *
     * @param newUsername String representation of the user's name
     * @return True if the username was set successfully
     */
    public boolean setUsername(String newUsername) {
        if (newUsername == null) {
            throw new NullPointerException("Expecting a String!");
        }
        this.username = newUsername;
        return true;
    }
}