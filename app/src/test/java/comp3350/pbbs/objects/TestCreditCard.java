package comp3350.pbbs.objects;

import junit.framework.TestCase;
import static org.junit.Assert.assertNotEquals;

public class TestCreditCard extends TestCase
{
	private String num;
	private String name;
	private int expMon;
	private int expYear;
	private int payDay;

	public void setUp() {
		num = "1000100010001000";
		name = "Alan Alfred";
		expMon = 6;
		expYear = 22;
		payDay = 27;
	}

	public void testGetCardNum() {
		CreditCard card1 = new CreditCard(num, name, expMon, expYear, payDay);
		CreditCard card2 = new CreditCard(num, name, expMon, expYear, payDay);
		assertEquals(card1.getCardNum(), card2.getCardNum());
		card2 = new CreditCard(num + 11, name, expMon, expYear, payDay);
		assertNotEquals(card1.getCardNum(), card2.getCardNum());
		try {
			new CreditCard(null, name, expMon, expYear, payDay);
		} catch (IllegalArgumentException ignored) { }
	}

	public void testGetHolderName() {
		CreditCard card1 = new CreditCard(num, name, expMon, expYear, payDay);
		CreditCard card2 = new CreditCard(num, name, expMon, expYear, payDay);
		assertEquals(card1.getHolderName(), card2.getHolderName());
		card2 = new CreditCard(num, "Bob Bushman", expMon, expYear, payDay);
		assertNotEquals(card1.getHolderName(), card2.getHolderName());
		try {
			new CreditCard(num, null, expMon, expYear, payDay);
		} catch (IllegalArgumentException ignored) { }
	}

	public void testGetExpireMonth() {
		CreditCard card1 = new CreditCard(num, name, expMon, expYear, payDay);
		CreditCard card2 = new CreditCard(num, name, expMon, expYear, payDay);
		assertEquals(card1.getExpireMonth(), card2.getExpireMonth());
		card2 = new CreditCard(num, name, 9, expYear, payDay);
		assertNotEquals(card1.getExpireMonth(), card2.getExpireMonth());
		try {
			new CreditCard(num, name, -1, expYear, payDay);
		} catch (IllegalArgumentException ignored) { }
	}

	public void testGetExpireYear() {
		CreditCard card1 = new CreditCard(num, name, expMon, expYear, payDay);
		CreditCard card2 = new CreditCard(num, name, expMon, expYear, payDay);
		assertEquals(card1.getExpireYear(), card2.getExpireYear());
		card2 = new CreditCard(num, name, expMon, 2000, payDay);
		assertNotEquals(card1.getExpireYear(), card2.getExpireYear());
		try {
			new CreditCard(num, name, expMon, -1, payDay);
		} catch (IllegalArgumentException ignored) { }
	}

	public void testGetPayDate() {
		CreditCard card1 = new CreditCard(num, name, expMon, expYear, payDay);
		CreditCard card2 = new CreditCard(num, name, expMon, expYear, payDay);
		assertEquals(card1.getPayDate(), card2.getPayDate());
		card2 = new CreditCard(num, name, expMon, expYear, 12);
		assertNotEquals(card1.getPayDate(), card2.getPayDate());
		try {
			new CreditCard(num, name, expMon, expYear, -1);
		} catch (IllegalArgumentException ignored) { }
	}
}
