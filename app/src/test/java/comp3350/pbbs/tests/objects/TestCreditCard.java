package comp3350.pbbs.tests.objects;

import java.util.Calendar;
import junit.framework.TestCase;
import comp3350.pbbs.objects.CreditCard;
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

	/* method: test isValidLength(str)  */
	public void testCardNum() {
		CreditCard card1 = new CreditCard(num, name, expMon, expYear, payDay);
		CreditCard card2 = new CreditCard(num, name, expMon, expYear, payDay);
		assertEquals(card1.getCardNum(), card2.getCardNum());
		card2 = new CreditCard("1000200030004000", name, expMon, expYear, payDay);
		assertNotEquals(card1.getCardNum(), card2.getCardNum());
		try {
			new CreditCard(null, name, expMon, expYear, payDay);
			fail("Expected IllegalArgumentException");
		} catch (IllegalArgumentException ignored) { }
		try {
			new CreditCard("900080007000", name, expMon, expYear, payDay);
			fail("Expected IllegalArgumentException");
		} catch (IllegalArgumentException ignored) { }
	}

	/* method: test isValidName(str) */
	public void testHolderName() {
		CreditCard card1 = new CreditCard(num, name, expMon, expYear, payDay);
		CreditCard card2 = new CreditCard(num, name, expMon, expYear, payDay);
		assertEquals(card1.getHolderName(), card2.getHolderName());
		card2 = new CreditCard(num, "Bob Bushman", expMon, expYear, payDay);
		assertNotEquals(card1.getHolderName(), card2.getHolderName());
		try {
			new CreditCard(num, null, expMon, expYear, payDay);
			fail("Expected IllegalArgumentException");
		} catch (IllegalArgumentException ignored) { }
		try {
			new CreditCard(num, "1@3?", expMon, expYear, payDay);
			fail("Expected IllegalArgumentException");
		} catch (IllegalArgumentException ignored) { }
	}

	/* method: test 1st arg of isValidExpiration(int, int) */
	public void testExpireMonth() {
		CreditCard card1 = new CreditCard(num, name, expMon, expYear, payDay);
		CreditCard card2 = new CreditCard(num, name, expMon, expYear, payDay);
		assertEquals(card1.getExpireMonth(), card2.getExpireMonth());
		card2 = new CreditCard(num, name, 9, expYear, payDay);
		assertNotEquals(card1.getExpireMonth(), card2.getExpireMonth());
		try {
			new CreditCard(num, name, -1, expYear, payDay);
			fail("Expected IllegalArgumentException");
		} catch (IllegalArgumentException ignored) { }
		try {
			new CreditCard(num, name, 15, expYear, payDay);
			fail("Expected IllegalArgumentException");
		} catch (IllegalArgumentException ignored) { }
	}

	/* method: test 2nd arg of isValidExpiration(int, int) */
	public void testExpireYear() {
		CreditCard card1 = new CreditCard(num, name, expMon, expYear, payDay);
		CreditCard card2 = new CreditCard(num, name, expMon, expYear, payDay);
		assertEquals(card1.getExpireYear(), card2.getExpireYear());
		card2 = new CreditCard(num, name, expMon, expYear+1, payDay);
		assertNotEquals(card1.getExpireYear(), card2.getExpireYear());
		try {
			new CreditCard(num, name, expMon, -1, payDay);
			fail("Expected IllegalArgumentException");
		} catch (IllegalArgumentException ignored) { }
		try {
			new CreditCard(num, name, expMon, 100, payDay);
			fail("Expected IllegalArgumentException");
		} catch (IllegalArgumentException ignored) { }
	}

	/* method: test both args of isValidExpiration(int, int) */
	public void testSpecialExpiration() {
		Calendar calender = Calendar.getInstance();
		int currMonth = calender.get(Calendar.MONTH) + 1;
		int currYear = calender.get(Calendar.YEAR) - 2000;
		try {
			new CreditCard(num, name, currMonth - 3, currYear, payDay);
			fail("Expected IllegalArgumentException");
		} catch (IllegalArgumentException ignored) { }
	}

	/* method: test isValidPayDate(int) */
	public void testPayDate() {
		CreditCard card1 = new CreditCard(num, name, expMon, expYear, payDay);
		CreditCard card2 = new CreditCard(num, name, expMon, expYear, payDay);
		assertEquals(card1.getPayDate(), card2.getPayDate());
		card2 = new CreditCard(num, name, expMon, expYear, 12);
		assertNotEquals(card1.getPayDate(), card2.getPayDate());
		try {
			new CreditCard(num, name, expMon, expYear, -1);
			fail("Expected IllegalArgumentException");
		} catch (IllegalArgumentException ignored) { }
		try {
			new CreditCard(num, name, expMon, expYear, 36);
			fail("Expected IllegalArgumentException");
		} catch (IllegalArgumentException ignored) { }
	}
}
