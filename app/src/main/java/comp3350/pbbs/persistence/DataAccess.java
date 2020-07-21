package comp3350.pbbs.persistence;

import java.util.ArrayList;
import java.util.List;

import comp3350.pbbs.objects.*;

public interface DataAccess {

    void open(String dbName);

    void close();

    boolean addBudgetCategories(List<BudgetCategory> budgetList);

    BudgetCategory findBudgetCategory(BudgetCategory currentBudget);

    boolean insertBudgetCategory(BudgetCategory newBudget);

    ArrayList<BudgetCategory> getBudgets();

    BudgetCategory updateBudgetCategory(BudgetCategory currentBudget, BudgetCategory newBudget);

    BudgetCategory deleteBudgetCategory(BudgetCategory currentBudget);

    int getBudgetsSize();

    boolean addAllCreditCards(List<CreditCard> cardList);

    boolean findCreditCard(CreditCard currCard);

    void insertCreditCard(CreditCard newCard);

    ArrayList<CreditCard> getCreditCards();

    boolean updateCreditCard(CreditCard currCard, CreditCard newCard);

    void deleteCreditCard(CreditCard currCard);

    int getCardsSize();

    boolean addTransactions(List<Transaction> transactionsList);

    Transaction findTransaction(Transaction currentTransaction);

    boolean insertTransaction(Transaction newTransaction);

    ArrayList<Transaction> getTransactions();

    boolean updateTransaction(Transaction currentTransaction, Transaction newTransaction);

    boolean deleteTransaction(Transaction currentTransaction);

    int getTransactionsSize();

    String getUsername();

    void setUsername(String newUsername);
}

