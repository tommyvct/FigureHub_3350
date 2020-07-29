package comp3350.pbbs.persistence;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import comp3350.pbbs.application.Services;
import comp3350.pbbs.objects.BankAccount;
import comp3350.pbbs.objects.BudgetCategory;
import comp3350.pbbs.objects.Cards.Card;
import comp3350.pbbs.objects.Transaction;

/**
 * StubDatabase
 * Group4
 * PBBS
 *
 * This class defines the persistence layer (stub database).
 */
public class StubDatabase {
    @SuppressWarnings({"FieldCanBeLocal", "unused"})
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
     * This method is used for populating fake data into the stub database
     */
    public void populateData() {
        BudgetCategory rent, groceries, utilities, phoneBill;   //various types of BudgetCategories
        Card card1, card2, card3;                               //variables for multiple credit cards
        Transaction t1, t2, t3, t4;                             //variables for multiple transactions

        budgets = new ArrayList<>();
        rent = new BudgetCategory("Rent/Mortgage", 500);
        budgets.add(rent);
        groceries = new BudgetCategory("Groceries", 100);
        budgets.add(groceries);
        utilities = new BudgetCategory("Utilities", 80);
        budgets.add(utilities);
        phoneBill = new BudgetCategory("Phone Bill", 75);
        budgets.add(phoneBill);

        card1 = new Card("Visa", "1000100010001000", "Jimmy", 12, 2021, 18);
        cards.add(card1);
        card2 = new Card("Mastercard", "1002100310041005", "Jimmy", 11, 2021, 15);
        cards.add(card2);

//        debitCards = new ArrayList<>();
        cards.add(new Card("CIBC Advantage Debit Card", "4506445712345678", "Jimmy", 12, 2021));
        cards.add(new Card("TD Access Card", "4724090212345678", "Jimmy", 11, 2021));
        cards.add(new Card("RBC Client Card", "4519011234567890", "Jimmy", 0, 0));

        //local date variable
        Date date = null;
        try {
            date = new SimpleDateFormat("YYYY-mm-dd").parse("2020-07-25");
        } catch (ParseException e) {
            System.out.println("serglhikuj");
            date = new Date();
        }
        transactions = new ArrayList<>();
        t1 = new Transaction(Services.calcDate(date, -5), 50, "Bought Chickens", card1, groceries);
        transactions.add(t1);
        t2 = new Transaction(Services.calcDate(date, -8), 450, "Rent Paid", card2, rent);
        transactions.add(t2);

        t3 = new Transaction(Services.calcDate(date, 0), 40, "Hydro bill paid", card2, utilities);
        transactions.add(t3);
        t4 = new Transaction(Services.calcDate(date, -10), 75, "Phone Bill paid", card2, phoneBill);
        transactions.add(t4);

        username = null;    //initializing the username with Null, it is going to call the mane from user input
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
     * @return the BudgetCategory object
     */
    public BudgetCategory findBudgetCategory(BudgetCategory currentBudget) {
        BudgetCategory budgetCategory = null;
        int index = budgets.indexOf(currentBudget);
        if (index >= 0) {
            budgetCategory = budgets.get(index);
        }
        return budgetCategory;
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
     * @return updated budgetCategory
     */
    public BudgetCategory updateBudgetCategory(BudgetCategory currentBudget, BudgetCategory newBudget) {
        int index = budgets.indexOf(currentBudget);
        BudgetCategory result = null;
        if (index >= 0) {
            result = budgets.set(index, newBudget);
        }
        return result;
    }

    /**
     * This method will remove a budget category.
     *
     * @return newly deleted budgetCategory
     */
    public BudgetCategory deleteBudgetCategory(BudgetCategory currentBudget) {
        int index = budgets.indexOf(currentBudget);
        BudgetCategory result = null;
        if (index >= 0) {
            result = budgets.remove(index);
        }
        return result;
    }

    /**
     * This method will add all the bank accounts to the given card list.
     *
     * @param accountList all bank accounts in the stub DB will be added to here.
     * @return true if added successfully.
     */
    public boolean addAllBankAccounts(List<BankAccount> accountList) {
        return accountList.addAll(accounts);
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
     * method: delete a bank account from the database
     *
     * @param toDelete a bank account needs to be deleted from the database
     * @return true if this bank account card does exist in the database
     */
    public boolean deleteBankAccount(BankAccount toDelete) {
        if (findBankAccount(toDelete)) {
            accounts.remove(toDelete);
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
        if (index >= 0) {
            accounts.set(index, newAccount);
            return true;
        }
        return false;
    }

    public ArrayList<BankAccount> getAllBankAccounts() {
        return accounts;
    }

    /**
     * method: get the bank account from a debit card
     *
     * @param from the debit card
     * @return BankAccount ArrayList links this debit card
     */
    public ArrayList<BankAccount> getAccountsFromDebitCard(Card from)
    {
        ArrayList<BankAccount> ret = new ArrayList<>();
        for (BankAccount account : accounts) {
            if (account.getLinkedCard().equals(from)) {
                ret.add(account);
            }
        }
        return ret;
    }

//    /**
//     * This method will add all the credit cards to the given card list.
//     *
//     * @param cardList all credit cards in the stub SB will be added to here.
//     * @return true if added successfully.
//     */
//    @SuppressWarnings("unused")  // will be used at some point in the future
//    public boolean addAllCreditCards(List<Card> cardList) {
//        ArrayList<Card> toAdd = new ArrayList<>();
//
//        for (Card c : cards)
//        {
//            if (!c.isDebit())
//            {
//                toAdd.add((Card) c);
//            }
//        }
//        return cardList.addAll(toAdd);
//    }
//
//    /**
//     * This method will add all the debit cards to the given card list.
//     *
//     * @param cardList all debit cards in the stub SB will be added to here.
//     * @return true if added successfully.
//     */
//    @SuppressWarnings("unused")  // will be used at some point in the future
//    public boolean addAllDebitCards(List<Card> cardList) {
//        ArrayList<Card> toAdd = new ArrayList<>();
//
//        for (Card c : cards)
//        {
//            if (c.isDebit())
//            {
//                toAdd.add((Card) c);
//            }
//        }
//        return cardList.addAll(toAdd);
//    }

    /**
     * This method will add all the cards to the given card list.
     *
     * @param cardList all cards in the stub DB will be added to here.
     * @return true if added successfully.
     */
    @SuppressWarnings("unused")
    public boolean addAllCards(List<Card> cardList)
    {
        return cardList.addAll(cards);
    }

    /**
     * This method will find if a card exist or not.
     *
     * @param toFind card to find
     * @return the card object.
     */
    public boolean findCard(Card toFind)
    {
        return cards.indexOf(toFind) >= 0;
    }

    /**
     * This method inserts a new card with the ArrayList.
     */
    public boolean insertCard(Card newCard) {
        if (!findCard(newCard)) {
            cards.add(newCard);
            return true;
        }
        return false;
    }

    /**
     * This method removes a given card
     */
    public boolean deleteCard(Card toDelete) {
        if (findCard(toDelete)) {
            cards.remove(toDelete);
            return true;
        }
        return false;
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
        if (index >= 0) {
            cards.set(index, newCard);
            return true;
        }
        return false;
    }

    /**
     * Mark given card as inactive.
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
    public ArrayList<Card> getCards()
    {
        return cards;
    }

    /**
     * Getter method to get all the credit cards.
     *
     * @return creditCards ArrayList.
     */
    public ArrayList<Card> getCreditCards() {
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
    public ArrayList<Card> getDebitCards() {
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
     * @return the transaction object.
     */
    @SuppressWarnings("unused")  // will be used at some point in the future
    public Transaction findTransaction(Transaction currentTransaction) {
        Transaction transaction = null;
        int index = transactions.indexOf(currentTransaction);
        if (index >= 0) {
            transaction = transactions.get(index);
        }
        return transaction;
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

    /**
     * Getter for username
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * setter for username, used when renaming
     * the username could be anything single line.
     * this is ensured on presentation side
     *

     * @param newUsername String representation of the user's name

     */
    public void setUsername(String newUsername) {
        this.username = newUsername;
    }


}