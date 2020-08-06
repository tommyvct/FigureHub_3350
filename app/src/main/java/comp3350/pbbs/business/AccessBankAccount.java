package comp3350.pbbs.business;

import java.util.List;

import comp3350.pbbs.application.Main;
import comp3350.pbbs.application.Services;
import comp3350.pbbs.objects.BankAccount;
import comp3350.pbbs.objects.Card;
import comp3350.pbbs.persistence.DataAccessI;

/**
 * AccessBankAccount
 * Group4
 * PBBS
 *
 * This class defines the access layer where deliver bank accounts info to the database
 */
public class AccessBankAccount {
    private DataAccessI db;    // create an object of the database

    /**
     * constructor: enabling access to the database
     */
    public AccessBankAccount() {
        db = Services.getDataAccess(Main.dbName);
    }

    /**
     * method: find a bank account exist or not in the database
     *
     * @param toFind a bank account needs to be found from the database
     * @return true if this bank account has been added into the database
     */
    public boolean findBankAccount(BankAccount toFind) {
        return db.findBankAccount(toFind);
    }

    /**
     * method: add a bank account into the database
     *
     * @param newAccount a bank account needs to be added into the database
     * @return true if this bank account does not exist in the database
     */
    public boolean insertBankAccount(BankAccount newAccount) {
        return db.insertBankAccount(newAccount);
    }

    /**
     * method: update a bank account existed in the database
     *
     * @param toUpdate   an old bank account needs to be replaced
     * @param newAccount a new bank account will replace the other one
     * @return true if the old bank account does exist in the database
     */
    public boolean updateBankAccount(BankAccount toUpdate, BankAccount newAccount) {
        if (toUpdate.getClass().equals(newAccount.getClass())) {
            return db.updateBankAccount(toUpdate, newAccount);
        } else {
            return false;
        }
    }

    /**
     * method: get all bank accounts from a debit card
     *
     * @param from the debit card
     * @return BankAccount ArrayList links this debit card
     */
    public List<BankAccount> getAccountsFromDebitCard(Card from) {
        return db.getAccountsFromDebitCard(from);
    }

    /**
     * Getter method to get the bank accounts
     *
     * @return BankAccount ArrayList
     */
    public List<BankAccount> getAllBankAccounts() {
        return db.getAllBankAccounts();
    }
}
