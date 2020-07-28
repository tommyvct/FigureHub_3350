package comp3350.pbbs.business;

import java.util.ArrayList;

import comp3350.pbbs.objects.Cards.*;
import comp3350.pbbs.application.Main;
import comp3350.pbbs.application.Services;
import comp3350.pbbs.persistence.StubDatabase;

/**
 * AccessDebitCard
 * Group4
 * PBBS
 *
 * This class defines the access layer where deliver cards info to the database
 */
public class AccessICard
{
    private StubDatabase db;    // create an object of the database

    /**
     * constructor: enabling access to the database
     */
    public AccessICard()
    {
        db = Services.getDataAccess(Main.dbName);
    }

    /**
     * method: find a card exist or not in the database
     *
     * @param toFind a card needs to be found from the database
     * @return true if this card has been added into the database
     */
    public boolean findCard(ICard toFind)
    {
        return db.findCard(toFind);
    }

    /**
     * method: add a card into the database
     *
     * @param newCard a card needs to be added into the database
     * @return true if this card does not exist in the database
     */
    public boolean insertCard(ICard newCard)
    {
        if (!findCard(newCard))
        {
            db.insertCard(newCard);
            return true;
        }

        return false;
    }

    /**
     * method: delete a debit card from the database
     *
     * NOT IMPLEMENTED in presentation for iteration1.
     *
     * @param toDelete a debit card needs to be deleted from the database
     * @return true if this debit card does exist in the database
     */
    public boolean deleteCard(ICard toDelete) {
        if (findCard(toDelete)) {
            db.deleteCard(toDelete);
            return true;
        }

        return false;
    }

    /**
     * method: update a card existed in the database
     *
     * NOT IMPLEMENTED in presentation for iteration1.
     *
     * @param toUpdate an old card needs to be replaced
     * @param newCard  a new card will replace the other one
     * @return true if the old card does exist in the database
     */
    public boolean updateCard(ICard toUpdate, ICard newCard) {
        if (toUpdate.getClass().equals(newCard.getClass()))
        {
            return db.updateCard(toUpdate, newCard);
        }
        else
        {
            return false;
        }
    }

    /**
     * Getter method to get the debit cards.
     *
     * @return debitCards ArrayList.
     */
    public ArrayList<DebitCard> getDebitCards() {
        return db.getDebitCards();
    }

    /**
     * Getter method to get the credit cards.
     *
     * @return creditCards ArrayList.
     */
    public ArrayList<ICard> getCreditCards() {
        return db.getCreditCards();
    }

    public ArrayList<ICard> getCards()
    {
        return db.getCards();
    }
}
