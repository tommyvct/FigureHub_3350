package comp3350.pbbs.business;

import java.util.List;

import comp3350.pbbs.application.Main;
import comp3350.pbbs.objects.BankAccount;
import comp3350.pbbs.objects.Card;
import comp3350.pbbs.persistence.DataAccessController;
import comp3350.pbbs.persistence.DataAccessI;

public class BankAccountCardLinker {
    private DataAccessI db;    // create an object of the database

    /**
     * constructor: enabling access to the database
     */
    public BankAccountCardLinker() {
        db = DataAccessController.getDataAccess(Main.dbName);
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
}
