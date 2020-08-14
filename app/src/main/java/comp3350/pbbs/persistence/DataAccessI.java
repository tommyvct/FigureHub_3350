package comp3350.pbbs.persistence;

import java.util.List;

import comp3350.pbbs.objects.*;
import comp3350.pbbs.objects.Card;

/**
 * DataAccess
 * Group4
 * PBBS
 * <p>
 * This class defines the interface for the persistence layer.
 */
public interface DataAccessI {

	void open(String dbPath);

	void close();

	String getDBName();

	boolean findBudgetCategory(BudgetCategory currentBudget);

	boolean insertBudgetCategory(BudgetCategory newBudget);

	List<BudgetCategory> getBudgets();

	boolean updateBudgetCategory(BudgetCategory currentBudget, BudgetCategory newBudget);

	int getBudgetsSize();

	boolean findBankAccount(BankAccount toFind);

	boolean insertBankAccount(BankAccount newAccount);

	boolean updateBankAccount(BankAccount toUpdate, BankAccount newAccount);

	List<BankAccount> getAllBankAccounts();

	List<BankAccount> getAccountsFromDebitCard(Card from);

	boolean findCard(Card currCard);

	boolean insertCard(Card newCard);

	List<Card> getCreditCards();

	List<Card> getDebitCards();

	List<Card> getCards();

	List<Card> getActiveCards();

	boolean updateCard(Card currCard, Card newCard);

	boolean markInactive(Card toMark);

	boolean markActive(Card toMark);

	int getCreditCardsSize();

	int getDebitCardsSize();

	boolean findTransaction(Transaction currentTransaction);

	boolean insertTransaction(Transaction newTransaction);

	List<Transaction> getTransactions();

	boolean updateTransaction(Transaction currentTransaction, Transaction newTransaction);

	boolean deleteTransaction(Transaction currentTransaction);

	int getTransactionsSize();

	String getUsername();

	boolean setUsername(String newUsername);
}

