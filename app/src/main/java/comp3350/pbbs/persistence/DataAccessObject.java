package comp3350.pbbs.persistence;

import java.util.ArrayList;
import java.util.List;

import comp3350.pbbs.objects.BudgetCategory;
import comp3350.pbbs.objects.CreditCard;
import comp3350.pbbs.objects.Transaction;

public class DataAccessObject implements DataAccess {
    public DataAccessObject(String dbName) {

    }

    @Override
    public void populateData() {

    }

    @Override
    public boolean addBudgetCategories(List<BudgetCategory> budgetList) {
        return false;
    }

    @Override
    public BudgetCategory findBudgetCategory(BudgetCategory currentBudget) {
        return null;
    }

    @Override
    public boolean insertBudgetCategory(BudgetCategory newBudget) {
        return false;
    }

    @Override
    public ArrayList<BudgetCategory> getBudgets() {
        return null;
    }

    @Override
    public BudgetCategory updateBudgetCategory(BudgetCategory currentBudget, BudgetCategory newBudget) {
        return null;
    }

    @Override
    public BudgetCategory deleteBudgetCategory(BudgetCategory currentBudget) {
        return null;
    }

    @Override
    public int getBudgetsSize() {
        return 0;
    }

    @Override
    public boolean addAllCreditCards(List<CreditCard> cardList) {
        return false;
    }

    @Override
    public boolean findCreditCard(CreditCard currCard) {
        return false;
    }

    @Override
    public void insertCreditCard(CreditCard newCard) {

    }

    @Override
    public ArrayList<CreditCard> getCreditCards() {
        return null;
    }

    @Override
    public boolean updateCreditCard(CreditCard currCard, CreditCard newCard) {
        return false;
    }

    @Override
    public void deleteCreditCard(CreditCard currCard) {

    }

    @Override
    public int getCardsSize() {
        return 0;
    }

    @Override
    public boolean addTransactions(List<Transaction> transactionsList) {
        return false;
    }

    @Override
    public Transaction findTransaction(Transaction currentTransaction) {
        return null;
    }

    @Override
    public boolean insertTransaction(Transaction newTransaction) {
        return false;
    }

    @Override
    public ArrayList<Transaction> getTransactions() {
        return null;
    }

    @Override
    public boolean updateTransaction(Transaction currentTransaction, Transaction newTransaction) {
        return false;
    }

    @Override
    public boolean deleteTransaction(Transaction currentTransaction) {
        return false;
    }

    @Override
    public int getTransactionsSize() {
        return 0;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public void setUsername(String newUsername) {

    }
}
