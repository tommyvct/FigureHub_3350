package comp3350.pbbs.objects;

import java.util.*;

public class CreditCard
{
	private String cardNum;		//16-digits number of a credit card
	private int payDate;		//the day user needs to ready for payment, 2-digits (DD)
	private int expireMonth;	//the month a credit card is expired, 2-digits (MM)
	private int expireYear;		//the year a credit card is expired, 2-digits (YY)
	private String holderName;	//user full name of a credit card

	/* constraints to a credit card */
	private static final int CARD_NUM_LENGTH = 16;
	private static final String REGEX = "^[a-zA-Z \\-.']*$";

	/* constructor: includes full info of a credit card */
	public CreditCard(String num, String usr, int expM, int expY, int pay) {
		cardNum = num;
		holderName = usr;
		expireMonth = expM;
		expireYear = expY;
		payDate = pay;
	}

	/* method: check if the input card number is 16-digits */
	public boolean isValidLength(String str) {
		return str.length() == CARD_NUM_LENGTH;
	}

	/* method: check if the input card holder full name is valid */
	public boolean isValidName(String str) {
		return str.matches(REGEX);
	}

	/* method: check if the input pay date is valid */
	public boolean isValidPayDate(int n) {
		return n >= 1 && n <= 31;
	}

	/* method: check if the input expire date is valid */
	public boolean isValidExpDate(int m, int y) {
		boolean result;
		Calendar calender = Calendar.getInstance();
		int currMonth = calender.get(Calendar.MONTH) + 1;
		int currYear = calender.get(Calendar.YEAR);
		if (m < 1 || m > 12 || y + 2000 < currYear || y + 2000 > 2099) {
			result = false;
		} else {
			result = (y + 2000) != currYear || m >= currMonth;
		}
		return result;
	}

	/* method: compare if two credit cards are same */
	public boolean equals(CreditCard other) {
		return getCardNum().equals(other.getCardNum());
	}

	/* method: display the credit card info when it is requested */
	public String toString() {
		String[] month = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
		String info = "CARD: [" + getCardNum() + "] Holder: " + getHolderName() +
				". Expire until: " + month[getExpireMonth() - 1] + " 20" + getExpireYear() +
				". Expected payment due: " + getPayDate();
		return info;
	}

	/* getters */
	public String getCardNum() { return cardNum; }

	public int getPayDate() { return payDate; }

	public int getExpireMonth() { return expireMonth; }

	public int getExpireYear() { return expireYear; }

	public String getHolderName() { return holderName; }
}