package comp3350.pbbs.business;

import java.util.ArrayList;
import java.util.Calendar;

import comp3350.pbbs.objects.Cards.DebitCard;
import comp3350.pbbs.application.Main;
import comp3350.pbbs.application.Services;
import comp3350.pbbs.persistence.StubDatabase;

/**
 * AccessDebitCard
 * Group4
 * PBBS
 *
 * This class defines the access layer where deliver debit card info to the database
 */
public class AccessDebitCard {
    private StubDatabase db;    // create an object of the database

    /**
     * constructor: enabling access to the database
     */
    public AccessDebitCard() {
        db = Services.getDataAccess(Main.dbName);
    }

    /**
     * method: find a debit card exist or not in the database
     *
     * @param currCard a debit card needs to be found from the database
     * @return true if this debit card has been added into the database
     */
    public boolean findDebitCard(DebitCard currCard) {
        return db.findDebitCard(currCard);
    }

    /**
     * method: add a debit card into the database
     *
     * @param newCard a debit card needs to be added into the database
     * @return true if this debit card does not exist in the database
     */
    public boolean insertDebitCard(DebitCard newCard) {
        if (!findDebitCard(newCard)) {
            db.insertDebitCard(newCard);
            return true;
        }
        return false;
    }

    /**
     * method: delete a debit card from the database
     *
     * NOT IMPLEMENTED in presentation for iteration1.
     *
     * @param currCard a debit card needs to be deleted from the database
     * @return true if this debit card does exist in the database
     */
    public boolean deleteDebitCard(DebitCard currCard) {
        if (findDebitCard(currCard)) {
            db.deleteDebitCard(currCard);
            return true;
        }
        return false;
    }

    /**
     * method: update a debit card existed in the database
     *
     * NOT IMPLEMENTED in presentation for iteration1.
     *
     * @param currCard an old debit card needs to be replaced
     * @param newCard  a new debit card will replace the other one
     * @return true if the old debit card does exist in the database
     */
    public boolean updateDebitCard(DebitCard currCard, DebitCard newCard) {
        return db.updateDebitCard(currCard, newCard);
    }

    /**
     * Getter method to get the debit cards.
     *
     * @return debitCards ArrayList.
     */
    public ArrayList<DebitCard> getDebitCards() {
        return db.getDebitCards();
    }

    /**
     * method: check if the a debit card holder's full name is valid
     *
     * @param str the debit card holder name
     * @return true if the holder name meet the requirement of the format
     */
    public static boolean isValidName(String str) {
        return DebitCard.isValidName(str);
    }

    /**
     * method: check if the input expire date is valid
     *
     * @param month the month
     * @param year  the year
     * @return 0 if everything's alright <br>
     * 1 if invalid month <br>
     * 2 if invalid year <br>
     * 3 if both month and year is invalid <br>
     * 4 if year is less than 4 digit <br>
     * 5 if month expired <br>
     * 6 if year expired <br>
     * 7 if null or empty string provided
     */
    public static int isValidExpirationDate(String month, String year) {
        int m, y;
        int result = 0;
        Calendar calender = Calendar.getInstance();
        int currMonth = calender.get(Calendar.MONTH) + 1;
        int currYear = calender.get(Calendar.YEAR);

        try {
            m = Integer.parseInt(month);
            y = Integer.parseInt(year);
        } catch (NumberFormatException e) {
            return 7;
        }

        if (y < 1000)
            return 4;
        if (m < 1 || m > 12)
            result += 1;
        if (y > 2099)
            result += 2;
        if (currYear == y && currMonth > m)
            return 5;
        if (y < currYear)
            return 6;

        return result;
    }
}