package comp3350.pbbs.tests.business;

import junit.framework.TestCase;
import comp3350.pbbs.application.Main;
import comp3350.pbbs.objects.CreditCard;
import comp3350.pbbs.business.AccessCreditCard;

/**
 * TestAccessCreditCard
 * Hao Zheng
 * PBBS
 *
 * This class tests the methods in the AccessCreditCard class
 */
public class TestAccessCreditCard extends TestCase
{
	private CreditCard card;		// a CreditCard object
	private AccessCreditCard acc;	// a AccessCreditCard object

	/**
	 * setup: connect to the database, create and initiate instance variables
	 */
	public void setUp() {
		Main.startup();
		card = new CreditCard("1001200230034004", "Si-Chuan Hotpot", 12, 2024, 12);
		acc = new AccessCreditCard();
		acc.insertCreditCard(card);
	}

	/**
	 * teardown: disconnect from the database
	 */
	public void tearDown() {
		Main.shutDown();
	}

	/**
	 * method: test findCreditCard(CreditCard)
	 */
	public void testFindCreditCard() {
		CreditCard card1 = new CreditCard("5005600670078008", "Cheese Burger", 3, 2021, 18);
		assertTrue(acc.findCreditCard(card));
		assertFalse(acc.findCreditCard(card1));
	}

	/**
	 * method: test insertCreditCard(CreditCard)
	 */
	public void testInsertCreditCard() {
		CreditCard card1 = new CreditCard("5005600670078008", "Cheese Burger", 3, 2021, 18);
		assertTrue(acc.insertCreditCard(card1));
		assertFalse(acc.insertCreditCard(card1));
	}

	/**
	 * method: test deleteCreditCard(CreditCard)
	 */
	public void testDeleteCreditCard() {
		assertTrue(acc.deleteCreditCard(card));
		assertFalse(acc.deleteCreditCard(card));
	}

	/**
	 * method: test updateCreditCard(CreditCard)
	 */
	public void testUpdateCreditCard() {
		CreditCard card1 = new CreditCard("5005600670078008", "Cheese Burger", 3, 2021, 18);
		assertTrue(acc.updateCreditCard(card, card1));
		assertFalse(acc.updateCreditCard(card, card1));
	}

	/**
	 * method: test getAllCreditCards()
	 */
	public void testGetAllCreditCards() {
		acc.insertCreditCard(card);
		assertNotNull(acc.getAllCreditCards());
	}
}