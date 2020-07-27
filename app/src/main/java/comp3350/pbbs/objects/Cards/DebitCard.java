package comp3350.pbbs.objects.Cards;

import org.jetbrains.annotations.NotNull;

/**
 * DebitCard
 * Group4
 * PBBS
 *
 * This class defines a debit card with information it includes
 */
public class DebitCard implements ICard {
    private String cardName;    // name of a debit card
    private String cardNum;     // number of a debit card
    private String holderName;  // user full name of a debit card
    private int expireMonth;    // the month a debit card is expired, 2-digits (MM)
    private int expireYear;     // the year a debit card is expired, 4-digits (YYYY)

    /**
     * constructor: includes full info of a debit card
     *
     * @param num  number of a debit card
     * @param usr  user full name of a debit card
     * @param expM the month a debit card is expired, 2-digits (MM)
     * @param expY the year a debit card is expired, 4-digits (YYYY)
     */
    public DebitCard(String cardName, String num, String usr, int expM, int expY) {
        errorMsg(num, usr, expM, expY);
        this.cardName = cardName.isEmpty() ? "No Name" : cardName;
        cardNum = num;
        holderName = usr;
        expireMonth = expM;
        expireYear = expY;
    }

    /**
     * method: show error message when the debit card info is invalid
     *
     * @param num  number of a debit card
     * @param usr  user full name of a debit card
     * @param expM the month a debit card is expired, 2-digits (MM)
     * @param expY the year a debit card is expired, 4-digits (YYYY)
     */
    public void errorMsg(String num, String usr, int expM, int expY) {
        if (num == null || num.isEmpty())
            throw new IllegalArgumentException("A debit Card requires a valid number.");
        if (!isValidName(usr))
            throw new IllegalArgumentException("A debit Card requires a valid holder name.");
        if (!isValidExpiration(expM, expY))
            throw new IllegalArgumentException("A debit Card requires a valid expire date.");
    }

    /**
     * method: check if the a debit card holder's full name is valid
     *
     * @param str the debit card holder name
     * @return true if the holder name meet the requirement of the format
     */
    public static boolean isValidName(String str) {
        return CreditCard.isValidName(str);
    }

    /**
     * method: check if the input expire date is valid
     *
     * @param m the month
     * @param y the year
     * @return true if the expire month & year are
     * 1) month and year are real-world existed, and
     * 2) after the current month of current year
     */
    public static boolean isValidExpiration(int m, int y) {
        return CreditCard.isValidExpiration(m, y);
    }

    /**
     * method: compare if two debit cards are same
     *
     * @param other another debit card
     * @return true if both debit cards have the same card number
     */
    public boolean equals(DebitCard other) {
        return getCardNum().equals(other.getCardNum());
    }

    /**
     * method: display the debit card info when it is requested
     *
     * @return a string represents this object and its fields
     */
    @NotNull
    public String toString() {
        String[] month = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        //the string "next month" needs to be replaced to real month later
        return  getCardName() + (getCardNum().length() > 4 ?
                (" •••• " + getCardNum().substring(getCardNum().length() - 4)) : " " + getCardNum())
                + "\n" +
                "Valid until " + month[getExpireMonth() - 1] + " " + getExpireYear() + "\n" +
                getHolderName() + "\n" ;
    }

    public String toStringShort()
    {
        return getCardName() + " •••• " + getCardNum().substring(getCardNum().length() - 4);
    }

    /**
     * methods: getters for instance fields
     *
     * @return values of fields
     */
    public String getCardNum() {
        return cardNum;
    }

    public String getCardName() {
        return cardName;
    }

    public int getExpireMonth() {
        return expireMonth;
    }

    public int getExpireYear() {
        return expireYear;
    }

    public String getHolderName() {
        return holderName;
    }
}
