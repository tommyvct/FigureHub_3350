package comp3350.pbbs.tests.business;

import junit.framework.TestCase;

import comp3350.pbbs.application.Main;
import comp3350.pbbs.objects.CreditCard;
import comp3350.pbbs.business.AccessCreditCard;

/**
 * TestAccessCreditCard
 * Group4
 * PBBS
 *
 * This class tests the methods in the AccessCreditCard class
 */
public class TestAccessCreditCard extends TestCase {
    private CreditCard card;        // a CreditCard object
    private AccessCreditCard acc;    // a AccessCreditCard object

    /**
     * This method connects to the database, create and initiate instance variables
     */
    public void setUp() {
        Main.startup();
        card = new CreditCard("mastercard", "1001200230034004", "Si-Chuan Hotpot", 12, 2024, 12);
        acc = new AccessCreditCard();
        acc.insertCreditCard(card);
    }

    /**
     * This teardown method disconnects from the database
     */
    public void tearDown() {
        Main.shutDown();
    }

    /**
     * This method tests findCreditCard(CreditCard)
     */
    public void testFindCreditCard() {
        CreditCard card1 = new CreditCard("mastercard", "5005600670078008", "Cheese Burger", 3, 2021, 18);
        assertTrue(acc.findCreditCard(card));
        assertFalse(acc.findCreditCard(card1));
    }

    /**
     * This method tests insertCreditCard(CreditCard)
     */
    public void testInsertCreditCard() {
        CreditCard card1 = new CreditCard("mastercard", "5005600670078008", "Cheese Burger", 3, 2021, 18);
        assertTrue(acc.insertCreditCard(card1));
        assertFalse(acc.insertCreditCard(card1));
    }

    /**
     * This method tests deleteCreditCard(CreditCard)
     */
    public void testDeleteCreditCard() {
        assertTrue(acc.deleteCreditCard(card));
        assertFalse(acc.deleteCreditCard(card));
    }

    /**
     * This method tests updateCreditCard(CreditCard)
     */
    public void testUpdateCreditCard() {
        CreditCard card1 = new CreditCard("mastercard", "5005600670078008", "Cheese Burger", 3, 2021, 18);
        assertTrue(acc.updateCreditCard(card, card1));
        assertFalse(acc.updateCreditCard(card, card1));
    }

}