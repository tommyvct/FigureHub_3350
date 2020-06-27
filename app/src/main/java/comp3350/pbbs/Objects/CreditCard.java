package comp3350.pbbs.Objects;
import java.util.*;

public class CreditCard
{
	private int cardID;			//the unique id for each credit card
	private String cardNum;		//16-digits number of a credit card
	private String payDate;		//the date user needs to ready for payment
	private String expireDate;	//the date a credit card is expired
	private String holderName;	//user full name of a credit card
	private int numOfCards; 	//total amount of credit cards

	/* constraints to a credit card */
	private static final int CARD_NUM_LENGTH = 16;
	private static final String REGEX = "^[a-zA-Z \\-.']*$"; 	//^[a-zA-Z \-\.\']*$

	/* constructor: as a primary key of a credit card */
	public CreditCard(int id) {
		cardID = id;
	}

	/* constructor: includes full info of a credit card */
	public CreditCard(int id, String num, String pay, String exp, String usr) {
		cardID = id;
		cardNum = num;
		payDate = pay;
		expireDate = exp;
		holderName = usr;
	}

	/* method: check if the input card number is 16-digits */
	public boolean isValidLength(String str) {
		boolean result;
		if (str.length() != CARD_NUM_LENGTH) {
			System.out.println("Invalid credit card number, please enter again.");
			result = false;
		} else {
			result = true;
		}
		return result;
	}

	/* method: check if the input card holder full name is valid */
	public boolean isFullName(String str) {
		boolean result;
		if (!str.matches(REGEX)) {
			System.out.println("Invalid user name, please enter again.");
			result = false;
		} else {
			result = true;
		}
		return result;
	}

	/* method: check if the input expire date is valid */
	public boolean isValidExpDate(String str) {
		boolean result;
		String s1 = str.substring(0, 1);
		String s2 = str.substring(2, str.length()-1);
		int month = Integer.parseInt(s1);
		int year = Integer.parseInt(s2);

		Calendar calender = Calendar.getInstance();
		int currMonth = calender.get(Calendar.MONTH) + 1;
		int currYear = calender.get(Calendar.YEAR);
		if (month < 1 || month > 12 || year < currYear || year > 6666
				|| (year == currYear && month < currMonth)) {
			System.out.println("Invalid expire date, please enter again.");
			result = false;
		} else {
			result = true;
		}
		return result;
	}

	/* method: check if the input pay date is valid */
	public boolean isValidPayDate(String str) {
		//ask user to input the next due date for payment
		//then use Calender to tell the current month to calculate the cycle
		boolean result;
		int day = Integer.parseInt(str);
		if (day < 1 || day > 31) {
			System.out.println("Invalid payment date, please enter again.");
			result = false;
		} else {
			result = true;
		}
		return result;
	}

	/* method: compare if two credit cards are same */
	public boolean equals(Object other) {
		boolean result = false;
		CreditCard card;
		if (other instanceof CreditCard) {
			card = (CreditCard) other;
			if (card.cardNum.equals(((CreditCard) other).cardNum)) {
				System.out.println("This credit card has already existed.");
				result = true;
			}
		}
		return result;
	}

	/* method: add a new credit card */
	public void addCard() {
		System.out.println("A credit card has been added!");
		numOfCards++;
	}

	/* method: remove an existing credit card */
	public void removeCard() {
		System.out.println("A credit card has been removed!");
		numOfCards--;
	}

	/* display the credit card info when it is requested */
	public String toString() {
		String msg = "Number of credit card exists: " + numOfCards;
		String info = "";
		for (int i = 0; i < numOfCards; i++) {
			info += "CARD#" + getCardID() + " [" + getCardNum() + "] Holder: " + getHolderName() +
					" Expire until: " + getExpireDate() + " Expected payment due: " + getPayDate();
		}
		return msg + "\n" + info;
	}

	/* getters */
	public int getCardID() { return cardID; }

	public String getCardNum() { return cardNum; }

	public String getPayDate() { return payDate; }

	public String getExpireDate() { return expireDate; }

	public String getHolderName() { return holderName; }
}

