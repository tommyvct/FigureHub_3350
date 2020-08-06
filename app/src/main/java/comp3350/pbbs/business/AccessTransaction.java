package comp3350.pbbs.business;


import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import comp3350.pbbs.application.Main;
import comp3350.pbbs.application.Services;
import comp3350.pbbs.objects.BankAccount;
import comp3350.pbbs.objects.BudgetCategory;
import comp3350.pbbs.objects.Card;
import comp3350.pbbs.objects.Transaction;
import comp3350.pbbs.persistence.DataAccessI;

/**
 * TransactionAccess
 * Group4
 * PBBS
 *
 * This class defines the access layer between the presentation layer and the persistence layer,
 * focusing on sending new transactions to the database
 */
public class AccessTransaction {
    private DataAccessI db;    // Access to the database

    // Formats for the dates
    public static final String[] DATE_FORMATS = new String[]{
            "dd/MM/yyyy HH:mm",
            "dd-MM-yyyy HH:mm",
    };

    /**
     * This constructor gets access to the database.
     */
    public AccessTransaction() {
        db = Services.getDataAccess(Main.dbName);
    }

    public AccessTransaction(@SuppressWarnings("unused") boolean test) {
        db = Services.getDataAccess("test");
    }


    @SuppressLint("SimpleDateFormat")
    public static String[] reverseParseDateTime(Date toReverse) {
        String[] ret = new String[2];

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");
        ret[0] = dateFormatter.format(toReverse);
        ret[1] = timeFormatter.format(toReverse);

        return ret;
    }

    /**
     * This method parses the given variables into a transaction object
     *
     * @param desc           The description of the transaction
     * @param dateStr        The date of the transaction
     * @param timeStr        The time of the transaction
     * @param amountStr      The amount of the transaction
     * @param card           The card the transaction was paid with
     * @param budgetCategory The category of the transaction
     * @return The parsed transaction, or null if the transaction could not be
     * parsed correctly.
     */
    private Transaction parseTransaction(String desc, String dateStr, String timeStr, String amountStr, Card card, BudgetCategory budgetCategory) {
        Transaction transaction = null;
        // Parse the date
        Date transactionTime = AccessValidation.parseDatetime(dateStr, timeStr);
        // Parse the amount
        float amount = AccessValidation.parseAmount(amountStr);
        // Create the transaction
        try {
            transaction = new Transaction(transactionTime, amount, desc, card, budgetCategory);
        } catch (IllegalArgumentException ignored) {
        }

        return transaction;
    }

    /**
     * This method parses the given variables into a transaction object (paid with debit)
     *
     * @param desc           The description of the transaction
     * @param dateStr        The date of the transaction
     * @param timeStr        The time of the transaction
     * @param amountStr      The amount of the transaction
     * @param debitCard      The card linked with the bank account
     * @param bankAccount    The bank account used
     * @param budgetCategory The category of the transaction
     * @return The parsed transaction, or null if the transaction could not be
     * parsed correctly.
     */
    private Transaction parseDebitTransaction(String desc, String dateStr, String timeStr, String amountStr, Card debitCard, BankAccount bankAccount, BudgetCategory budgetCategory) {
        Transaction transaction = null;
        // Parse the date
        Date transactionTime = AccessValidation.parseDatetime(dateStr, timeStr);
        // Parse the amount
        float amount = AccessValidation.parseAmount(amountStr);
        // Create the transaction
        try {
            transaction = new Transaction(transactionTime, amount, desc, debitCard, bankAccount, budgetCategory);
        } catch (IllegalArgumentException ignored) {
        }

        return transaction;
    }


    /**
     * This method parses the transaction and adds it to the database
     *
     * @param desc           The description of the transaction
     * @param dateStr        The date of the transaction
     * @param timeStr        The time of the transaction
     * @param amountStr      The amount of the transaction
     * @param card           The card the transaction was paid with
     * @param budgetCategory The category of the transaction
     * @return True if the transaction was added successfully, or false if it was
     * not added successfully
     */
    public boolean addTransaction(String desc, String dateStr, String timeStr, String amountStr, Card card, BudgetCategory budgetCategory) {
        boolean toReturn = false;
        // Ensure the parameters are valid
        if (AccessValidation.isValidAmount(amountStr) && AccessValidation.isValidDateTime(dateStr, timeStr) && AccessValidation.isValidDescription(desc)) {
            Transaction transaction = parseTransaction(desc, dateStr, timeStr, amountStr, card, budgetCategory);
            if (transaction != null) {
                toReturn = db.insertTransaction(transaction);
            }
        }

        return toReturn;
    }

    /**
     * This method parses the transaction and adds it to the database (paid with debit)
     *
     * @param desc           The description of the transaction
     * @param dateStr        The date of the transaction
     * @param timeStr        The time of the transaction
     * @param amountStr      The amount of the transaction
     * @param debitCard      The card linked with the bank account
     * @param bankAccount    The bank account used
     * @param budgetCategory The category of the transaction
     * @return True if the transaction was added successfully, or false if it was
     * not added successfully
     */
    public boolean addDebitTransaction(String desc, String dateStr, String timeStr, String amountStr, Card debitCard, BankAccount bankAccount, BudgetCategory budgetCategory) {
        boolean toReturn = false;
        // Ensure the parameters are valid
        if (AccessValidation.isValidAmount(amountStr) && AccessValidation.isValidDateTime(dateStr, timeStr) && AccessValidation.isValidDescription(desc)) {
            Transaction transaction = parseDebitTransaction(desc, dateStr, timeStr, amountStr, debitCard, bankAccount, budgetCategory);
            if (transaction != null) {
                toReturn = db.insertTransaction(transaction);
            }
        }

        return toReturn;
    }


    /**
     * This method takes the old transaction and updates it to the current given values
     * <p>
     * NOT IMPLEMENTED in presentation for iteration1.
     *
     * @param oldTransaction The transaction to replace
     * @param desc           The description of the new transaction
     * @param dateStr        The date of the new transaction
     * @param timeStr        The time of the new transaction
     * @param amountStr      The amount of the new transaction
     * @param card           The card the new transaction was paid with
     * @param budgetCategory The category of the new transaction
     * @return True if the transaction was replaced successfully, or false if it
     * was not replaced successfully
     */
    public boolean updateTransaction(Transaction oldTransaction, String desc, String dateStr, String timeStr, String amountStr, Card card, BudgetCategory budgetCategory) {
        boolean toReturn = false;
        // Ensure the parameters are valid
        if (AccessValidation.isValidAmount(amountStr) && AccessValidation.isValidDateTime(dateStr, timeStr) && AccessValidation.isValidDescription(desc)) {
            Transaction transaction = parseTransaction(desc, dateStr, timeStr, amountStr, card, budgetCategory);
            if (transaction != null) {
                toReturn = db.updateTransaction(oldTransaction, transaction);
            }
        }

        return toReturn;
    }

    /**
     * This method takes the old transaction and updates it to the current given values
     * <p>
     * NOT IMPLEMENTED in presentation for iteration1.
     *
     * @param oldTransaction The transaction to replace
     * @param desc           The description of the new transaction
     * @param dateStr        The date of the new transaction
     * @param timeStr        The time of the new transaction
     * @param amountStr      The amount of the new transaction
     * @param debitCard      The card linked with the bank account
     * @param bankAccount    The bank account used
     * @param budgetCategory The category of the new transaction
     * @return True if the transaction was replaced successfully, or false if it
     * was not replaced successfully
     */
    public boolean updateDebitTransaction(Transaction oldTransaction, String desc, String dateStr, String timeStr, String amountStr, Card debitCard, BankAccount bankAccount, BudgetCategory budgetCategory) {
        boolean toReturn = false;
        // Ensure the parameters are valid
        if (AccessValidation.isValidAmount(amountStr) && AccessValidation.isValidDateTime(dateStr, timeStr) && AccessValidation.isValidDescription(desc)) {
            Transaction transaction = parseDebitTransaction(desc, dateStr, timeStr, amountStr, debitCard, bankAccount, budgetCategory);
            if (transaction != null) {
                toReturn = db.updateTransaction(oldTransaction, transaction);
            }
        }

        return toReturn;
    }

    /**
     * Retrieves a list of all the transactions in the database.
     *
     * @return List of all transactions in the database
     */
    public List<Transaction> retrieveTransactions() {
        return db.getTransactions();
    }

    /**
     * Retrieves a list of transactions that fall between the 2 given dates
     *
     * @param to   Starting date range
     * @param from Ending date range
     * @return List of transactions that come after @param to and come before @param from.
     */
    public List<Transaction> retrieveTransactions(Date to, Date from) {
        List<Transaction> toReturn = null;

        // If the parameters are valid
        if (to != null && from != null) {
            toReturn = new ArrayList<>();
            List<Transaction> allTransactions = retrieveTransactions();

            // Loop through all transactions
            for (Transaction transaction : allTransactions) {
                // If the time of the transaction is between the range [to, from], add to the list
                if (transaction.getTime().after(to) && transaction.getTime().before(from)) {
                    toReturn.add(transaction);
                }
            }
        }

        return toReturn;
    }

    /**
     * Deletes the given transaction in the database
     * <p>
     * NOT IMPLEMENTED in presentation for iteration1.
     *
     * @param toDelete The transaction to delete
     * @return True if the transaction was deleted successfully, false if not
     */
    public boolean deleteTransaction(Transaction toDelete) {
        boolean toReturn = false;

        if (toDelete != null) {
            toReturn = db.deleteTransaction(toDelete);
        }
        return toReturn;
    }
}
