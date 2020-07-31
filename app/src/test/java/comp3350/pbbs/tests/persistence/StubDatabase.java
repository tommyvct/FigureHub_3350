package comp3350.pbbs.tests.persistence;

import java.util.ArrayList;
import java.util.List;

import comp3350.pbbs.objects.BankAccount;
import comp3350.pbbs.objects.BudgetCategory;
import comp3350.pbbs.objects.Card;
import comp3350.pbbs.objects.Transaction;
import comp3350.pbbs.persistence.DataAccess;

/**
 * StubDatabase
 * Group4
 * PBBS
 *
 * This class defines the persistence layer (stub database).
 */
public class StubDatabase implements DataAccess {
    private String databaseName;                    // name of the database, not used in iteration 1
    private ArrayList<Transaction> transactions;    // ArrayList for transactions
    private ArrayList<BudgetCategory> budgets;      // ArrayList for budgets
    private ArrayList<BankAccount> accounts;        // ArrayList for bank accounts
    private ArrayList<Card> cards;                  // ArrayList for cards
    private String username;                        // "Hi, {username}!"

    /**
     * This method is the constructor of the database stub
     *
     * @param name name of the database
     */
    public StubDatabase(String name) {
        this.databaseName = name;
        transactions = new ArrayList<>();
        budgets = new ArrayList<>();
        accounts = new ArrayList<>();
        cards = new ArrayList<>();
    }

    /**
     * Opens the database by populating with fake data.
     *
     * @param dbPath    Database path
     */
    public void open(String dbPath) {
        if(dbPath.contains("populate"))
            DataAccess.populateData(this);
        System.out.println("Opened stub database");
    }

    public String getDBName() {
        return databaseName;
    }

    public void close() {
        System.out.println("Closed stub database");
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
     *
     * @return true, if inserted successfully
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
     * This method will return the size of the budget.
     *
     * @return size of the budget list
     */
    @Override
    public int getBudgetsSize() {
        return budgets.size();
    }


    /**
     * method: find a bank account exist or not in the database
     *
     * @param toFind a bank account needs to be found from the database
     * @return true if this bank account has been added into the database
     */
    public boolean findBankAccount(BankAccount toFind) {
        return accounts.indexOf(toFind) >= 0;
    }

    /**
     * method: add a bank account into the database
     *
     * @param newAccount a bank account needs to be added into the database
     * @return true if this bank account does not exist in the database
     */
    public boolean insertBankAccount(BankAccount newAccount) {
        if (!findBankAccount(newAccount)) {
            accounts.add(newAccount);
            return true;
        }
        return false;
    }

    /**
     * method: update a bank account existed in the database
     *
     * @param toUpdate an old bank account needs to be replaced
     * @param newAccount  a new bank account will replace the other one
     * @return true if the old bank account does exist in the database
     */
    public boolean updateBankAccount(BankAccount toUpdate, BankAccount newAccount) {
        int index = accounts.indexOf(toUpdate);
        int updateValid = accounts.indexOf(newAccount); //update will be valid if the newAccount doesn't cause any duplication
        if (index >= 0 && updateValid <= 0) {
            accounts.set(index, newAccount);
            return true;
        }
        return false;
    }

    /**
     * method: to get all the bankAccounts from the database
     *
     * @return an arrayList of bankAccounts
     */
    public ArrayList<BankAccount> getAllBankAccounts() {
        return accounts;
    }

    /**
     * method: get the bank account from a debit card
     *
     * @param from the debit card
     * @return BankAccount ArrayList links this debit card
     */
    public List<BankAccount> getAccountsFromDebitCard(Card from)
    {
        List<BankAccount> ret = new ArrayList<>();
        for (BankAccount account : accounts) {
            if (account.getLinkedCard().equals(from)) {
                ret.add(account);
            }
        }
        return ret;
    }

    /**
     * This method will find if a card exist or not.
     *
     * @param toFind card to find
     * @return the card object.
     */
    public boolean findCard(Card toFind)
    {
        return cards.contains(toFind);
    }

    /**
     * This method inserts a new card with the ArrayList.
     *
     * @return true, if it inserted properly
     */
    public boolean insertCard(Card newCard) {
        boolean result = false;
        if (!findCard(newCard)) {
            cards.add(newCard);
            result = true;
        }
        return result;
    }

    /**
     * This method updates a card existed in the database
     *
     * @param toUpdate an old card needs to be replaced
     * @param newCard  a new card will replace the other one
     * @return true if the old card does exist in the database
     */
    public boolean updateCard(Card toUpdate, Card newCard) {
        int index = cards.indexOf(toUpdate);
        int updateValid = cards.indexOf(newCard);
        boolean result = false;
        if (index >= 0 && updateValid <= 0) {
            cards.set(index, newCard);
            result = true;
        }
        return result;
    }

    /**
     * This method returns the credit card size
     *
     * @return size of the credit card.
     */
    @Override
    public int getCreditCardsSize() {
        return getCreditCards().size();
    }

    /**
     * This method returns the debit card size
     *
     * @return size of the debit card.
     */
    @Override
    public int getDebitCardsSize() {
        return getDebitCards().size();
    }

    /**
     * Mark given card as inactive.
     *
     * @param toMark card to mark as inactive
     */
    public boolean markInactive(Card toMark)
    {
        int index = cards.indexOf(toMark);
        if (index >= 0)
        {
            cards.get(index).setActive(false);
            return true;
        }
        return false;
    }


    /**
     * Getter method to get all the cards.
     *
     * @return all the cards.
     */
    public List<Card> getCards()
    {
        return cards;
    }

    /**
     * Getter method to get all the credit cards.
     *
     * @return creditCards ArrayList.
     */
    public List<Card> getCreditCards() {
        ArrayList<Card> ret = new ArrayList<>();
        for (Card c : cards) {
            if (c.getPayDate() != 0) {
                ret.add(c);
            }
        }
        return ret;
    }

    /**
     * Getter method to get all the debit cards.
     *
     * @return debitCards ArrayList.
     */
    public List<Card> getDebitCards() {
        ArrayList<Card> ret = new ArrayList<>();
        for (Card c : cards) {
            if (c.getPayDate() == 0) {
                ret.add(c);
            }
        }
        return ret;
    }

    /**
     * Getter method for only active cards
     *
     * @return active cards
     */
    public ArrayList<Card> getActiveCards()
    {
        ArrayList<Card> ret = new ArrayList<>();

        for (Card card : cards)
        {
            if (card.isActive())
            {
                ret.add(card);
            }
        }

        return ret;
    }

    /**
     * This method will find if a transaction exist or not.
     *
     * @param currentTransaction transaction that will be searched
     * @return True if found, or false if not found
     */
    public boolean findTransaction(Transaction currentTransaction) {
        return transactions.contains(currentTransaction);
    }

    /**
     * This method will insert a new transaction with the ArrayList.
     *
     * @param newTransaction transaction that will be inserted
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
     * @param currentTransaction transaction that need to be updated
     * @param newTransaction transaction which will be set
     * @return true if updated successfully.
     */
    public boolean updateTransaction(Transaction currentTransaction, Transaction newTransaction) {
        boolean toReturn = false;
        while(transactions.contains(currentTransaction)) {
            int index = transactions.indexOf(currentTransaction);
            if (index >= 0) {
                toReturn = transactions.set(index, newTransaction) != null;
            }
        }
        return toReturn;
    }

    /**
     * This method will be used to remove a transaction.
     *
     * @param currentTransaction transaction that needs to be deleted
     * @return true if deleted successfully
     */
    public boolean deleteTransaction(Transaction currentTransaction) {
        boolean toReturn = false;
        while (transactions.contains(currentTransaction)) {
            toReturn = transactions.remove(currentTransaction);
        }
        return toReturn;
    }

    /**
     * This method will return the transaction size.
     *
     * @return size of transaction ArrayList
     */
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