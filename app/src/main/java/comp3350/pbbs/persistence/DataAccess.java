package comp3350.pbbs.persistence;

import java.util.ArrayList;
import java.util.List;

import comp3350.pbbs.objects.*;

public interface DataAccess {

    void open(String dbPath);

    void close();

    boolean addBudgetCategories(List<BudgetCategory> budgetList);

    boolean findBudgetCategory(BudgetCategory currentBudget);

    boolean insertBudgetCategory(BudgetCategory newBudget);

    List<BudgetCategory> getBudgets();

    boolean updateBudgetCategory(BudgetCategory currentBudget, BudgetCategory newBudget);

    boolean deleteBudgetCategory(BudgetCategory currentBudget);

    int getBudgetsSize();

    boolean addAllCreditCards(List<CreditCard> cardList);

    boolean findCreditCard(CreditCard currCard);

    boolean insertCreditCard(CreditCard newCard);

    List<CreditCard> getCreditCards();

    boolean updateCreditCard(CreditCard currCard, CreditCard newCard);

    boolean deleteCreditCard(CreditCard currCard);

    int getCardsSize();

    boolean addTransactions(List<Transaction> transactionsList);

    boolean findTransaction(Transaction currentTransaction);

    boolean insertTransaction(Transaction newTransaction);

    List<Transaction> getTransactions();

    boolean updateTransaction(Transaction currentTransaction, Transaction newTransaction);

    boolean deleteTransaction(Transaction currentTransaction);

    int getTransactionsSize();

    String getUsername();

    boolean setUsername(String newUsername);
}

