package comp3350.pbbs.objects;

import java.util.Calendar;

/**
 * CreditCard
 * Hao Zheng
 * PBBS
 *
 * This class defines a credit card with information it includes
 */
public class CreditCard
{
	private String cardName;    // name of a credit card
	private String cardNum;		// number of a credit card
	private String holderName;	// user full name of a credit card
	private int expireMonth;	// the month a credit card is expired, 2-digits (MM)
	private int expireYear;		// the year a credit card is expired, 4-digits (YYYY)
	private int payDate;		// the day user needs to ready for payment, 2-digits (DD)

	/**
	 * constants: constraints to a credit card
	 */
	private static final String REGEX = "^[a-zA-Z \\-.']*$"; 	// the format of a name

	/**
	 * constructor: includes full info of a credit card
	 * @param num number of a credit card
	 * @param usr user full name of a credit card
	 * @param expM the month a credit card is expired, 2-digits (MM)
	 * @param expY the year a credit card is expired, 4-digits (YYYY)
	 * @param pay the day user needs to ready for payment, 2-digits (DD)
	 */
	public CreditCard(String cardName, String num, String usr, int expM, int expY, int pay) {
		errorMsg(num, usr, expM, expY, pay);
		this.cardName = cardName.isEmpty() ? "No Name" : cardName;
		cardNum = num;
		holderName = usr;
		expireMonth = expM;
		expireYear = expY;
		payDate = pay;
	}

	/**
	 * method: show error message when the credit card info is invalid
	 * @param num number of a credit card
	 * @param usr user full name of a credit card
	 * @param expM the month a credit card is expired, 2-digits (MM)
	 * @param expY the year a credit card is expired, 4-digits (YYYY)
	 * @param pay the day user needs to ready for payment, 2-digits (DD)
	 */
	public void errorMsg(String num, String usr, int expM, int expY, int pay) {
		if (num == null || num.isEmpty())
			throw new IllegalArgumentException("A Credit Card requires a valid number.");
		if (!isValidName(usr))
			throw new IllegalArgumentException("A Credit Card requires a valid holder name.");
		if (!isValidExpiration(expM, expY))
			throw new IllegalArgumentException("A Credit Card requires a valid expire date.");
		if (!isValidPayDate(pay))
			throw new IllegalArgumentException("A Credit Card requires a valid payment date.");
	}

	/**
	 * method: check if the a credit card holder's full name is valid
	 * @param str the credit card holder name
	 * @return true if the holder name meet the requirement of the format
	 */
	public static boolean isValidName(String str) {
		if (str == null) {
			return false;
		} else {
			return str.matches(REGEX);
		}
	}

	/**
	 * method: check if the input expire date is valid
	 * @param m the month
	 * @param y the year
	 * @return true if the expire month & year are
	 * 		   1) month and year are real-world existed, and
	 * 		   2) after the current month of current year
	 */
	public static boolean isValidExpiration(int m, int y) {
		boolean result;
		Calendar calender = Calendar.getInstance();
		int currMonth = calender.get(Calendar.MONTH) + 1;
		int currYear = calender.get(Calendar.YEAR);
		if (m < 1 || m > 12 || y < currYear || y > 2999) {
			result = false;
		} else {
			result = y != currYear || m >= currMonth;
		}
		return result;
	}

	/**
	 * method: check if the input pay date is valid
	 * @param n the day
	 * @return true if the day is real-world existed
	 */
	public static boolean isValidPayDate(int n) {
		return n >= 1 && n <= 31;
	}

	/**
	 * method: compare if two credit cards are same
	 * @param other another credit card
	 * @return true if both credit cards have the same card number
	 */
	public boolean equals(CreditCard other) {
		return getCardNum().equals(other.getCardNum());
	}

	/**
	 * method: display the credit card info when it is requested
	 * @return a string represents this object and its fields
	 */
	public String toString() {
		String[] month = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
						  "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
		String info = getCardName() + "\n" + getCardNum() + "\nCard Holder: " + getHolderName() +
					  "\nValid Until: " + month[getExpireMonth() - 1] + " " +
					  getExpireYear() + "\nExpected payment due on " + getPayDate();
		return info;
	}

	/**
	 * methods: getters for instance fields
	 * @return values of fields
	 */
	public String getCardNum() { return cardNum; }

	public String getCardName() { return cardName; }

	public int getPayDate() { return payDate; }

	public int getExpireMonth() { return expireMonth; }

	public int getExpireYear() { return expireYear; }

	public String getHolderName() { return holderName; }
}
