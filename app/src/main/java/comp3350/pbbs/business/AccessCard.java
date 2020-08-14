package comp3350.pbbs.business;

import java.util.List;

import comp3350.pbbs.application.Main;
import comp3350.pbbs.persistence.DataAccessController;
import comp3350.pbbs.objects.Card;
import comp3350.pbbs.persistence.DataAccessI;

/**
 * AccessCard
 * Group4
 * PBBS
 * <p>
 * This class defines the access layer where deliver cards info to the database
 */
public class AccessCard {
	private DataAccessI db;    // create an object of the database

	/**
	 * constructor: enabling access to the database
	 */
	public AccessCard() {
		db = DataAccessController.getDataAccess(Main.dbName);
	}

	/**
	 * method: find a card exist or not in the database
	 *
	 * @param toFind a card needs to be found from the database
	 * @return true if this card has been added into the database
	 */
	public boolean findCard(Card toFind) {
		return db.findCard(toFind);
	}

	/**
	 * method: add a card into the database
	 *
	 * @param newCard a card needs to be added into the database
	 * @return true if this card does not exist in the database
	 */
	public boolean insertCard(Card newCard) {
		if (newCard != null) {
			return db.insertCard(newCard);
		} else {
			return false;
		}
	}

	/**
	 * method: update a card existed in the database
	 * <p>
	 * NOT IMPLEMENTED in presentation for iteration1.
	 *
	 * @param toUpdate an old card needs to be replaced
	 * @param newCard  a new card will replace the other one
	 * @return true if the old card does exist in the database
	 */
	public boolean updateCard(Card toUpdate, Card newCard) {
		if (newCard != null && toUpdate != null) {
			return db.updateCard(toUpdate, newCard);
		} else return false;
	}

	/**
	 * Mark given card as inactive.
	 *
	 * @param toMark card to mark as inactive
	 */
	public boolean markInactive(Card toMark) {
		return db.markInactive(toMark);
	}

	/**
	 * Mark given card as active.
	 *
	 * @param toMark card to mark as inactive
	 */
	public boolean markActive(Card toMark) {
		return db.markActive(toMark);
	}


	/**
	 * Getter method to get the debit cards.
	 *
	 * @return debitCards ArrayList.
	 */
	public List<Card> getDebitCards() {
		return db.getDebitCards();
	}

	/**
	 * Getter method to get the credit cards.
	 *
	 * @return creditCards ArrayList.
	 */
	public List<Card> getCreditCards() {
		return db.getCreditCards();
	}

	/**
	 * Getter method for all Cards
	 *
	 * @return all cards
	 */
	public List<Card> getCards() {
		return db.getCards();
	}

	/**
	 * Getter method for only active cards
	 *
	 * @return active cards
	 */
	public List<Card> getActiveCards() {
		return db.getActiveCards();
	}
}