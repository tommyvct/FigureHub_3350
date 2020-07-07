package comp3350.pbbs.business;

import java.util.ArrayList;

import comp3350.pbbs.objects.CreditCard;
import comp3350.pbbs.application.Main;
import comp3350.pbbs.application.Services;
import comp3350.pbbs.persistence.StubDatabase;

/**
 * AccessCreditCard
 * Hao Zheng
 * PBBS
 *
 * This class defines the access layer where deliver credit card info to the database
 */
public class AccessCreditCard
{
	private StubDatabase db;	// create an object of the database

	/**
	 * constructor: enabling access to the database
	 */
	public AccessCreditCard() {
		db = Services.getDataAccess(Main.dbName);
	}

	/**
	 * method: find a credit card exist or not in the database
	 * @param currCard a credit card needs to be found from the database
	 * @return true if this credit card has been added into the database
	 */
	public boolean findCreditCard(CreditCard currCard) {
		return db.findCreditCard(currCard);
	}

	/**
	 * method: add a credit card into the database
	 * @param newCard a credit card needs to be added into the database
	 * @return true if this credit card does not exist in the database
	 */
	public boolean insertCreditCard(CreditCard newCard) {
		if (!findCreditCard(newCard)) {
			db.insertCreditCard(newCard);
			return true;
		}
		return false;
	}

	/**
	 * method: delete a credit card from the database
	 * @param currCard a credit card needs to be deleted from the database
	 * @return true if this credit card does exist in the database
	 */
	public boolean deleteCreditCard(CreditCard currCard) {
		if (findCreditCard(currCard)) {
			db.deleteCreditCard(currCard);
			return true;
		}
		return false;
	}

	/**
	 * method: update a credit card existed in the database
	 * @param currCard an old credit card needs to be replaced
	 * @param newCard a new credit card will replace the other one
	 * @return true if the old credit card does exist in the database
	 */
	public boolean updateCreditCard(CreditCard currCard, CreditCard newCard) {
		return db.updateCreditCard(currCard, newCard);
	}

	/**
	 * Getter method to get the credit cards.
	 * @return creditCards ArrayList.
	 */
	public ArrayList<CreditCard> getCreditCards()
	{
		return db.getCreditCards();
	}
}