package comp3350.pbbs;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import comp3350.pbbs.objects.CreditCard;

public class TestCreditCard extends TestCase
{
	CreditCard card1 = new CreditCard(1, "1000100010001000", "Alan Alfred", 6, 22, 27);
	CreditCard card2 = new CreditCard(2, "1000200020002000", "Bob Bushman", 9, 21, 24);
	CreditCard card3 = new CreditCard(3, "1000200020002000", "Cake Cheese", 3, 20, 18);
	CreditCard card4 = new CreditCard(4, "1000200030004000", "Denise Dat", 16, 19, 12);
	CreditCard card5 = new CreditCard(4, "100120023003400", "12345 67890", 9, 19, 123);

	public TestCreditCard(String name) {
		super(name);
	}

	public void setUp() throws Exception {
		super.setUp();
	}

	public void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetCardID() {
		assertEquals(1, card1.getCardID());
		assertNotSame(1, card2.getCardID());
	}

	public void testGetCardNum() {
		assertEquals("1000100010001000", card1.getCardNum());
		assertNotSame("1000100010001000", card2.getCardNum());
	}

	public void testGetHolderName() {
		assertEquals("Alan Alfred", card1.getHolderName());
		assertNotSame("Alan Alfred", card2.getHolderName());
	}

	public void testGetExpireMonth() {
		assertEquals(6, card1.getExpireMonth());
		assertNotSame(6, card2.getExpireMonth());
	}

	public void testGetExpireYear() {
		assertEquals(22, card1.getExpireYear());
		assertNotSame(22, card2.getExpireYear());
	}

	public void testGetPayDate() {
		assertEquals(27, card1.getPayDate());
		assertNotSame(27, card2.getPayDate());
	}

	public void testIsValidLength() {
		assertTrue(card1.isValidLength(card1.getCardNum()));
		assertFalse(card5.isValidLength(card5.getCardNum()));
	}

	public void testIsValidName() {
		assertTrue(card1.isValidName(card1.getHolderName()));
		assertFalse(card5.isValidName(card5.getHolderName()));
	}

	public void testIsValidExpDate() {
		assertTrue(card1.isValidExpDate(card1.getExpireMonth(),card1.getExpireYear()));
		assertTrue(card2.isValidExpDate(card2.getExpireMonth(),card2.getExpireYear()));
		assertFalse(card3.isValidExpDate(card3.getExpireMonth(),card3.getExpireYear()));
		assertFalse(card4.isValidExpDate(card4.getExpireMonth(),card4.getExpireYear()));
	}

	public void testIsValidPayDate() {
		assertTrue(card1.isValidPayDate(card1.getPayDate()));
		assertFalse(card5.isValidPayDate(card5.getPayDate()));
	}

	public void testEquals() {
		assertTrue(card2.equals(card3));
		assertFalse(card2.equals(card1));
	}

	public static Test suite() {
		return new TestSuite(TestCreditCard.class);
	}
}
