package comp3350.pbbs.business;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import comp3350.pbbs.objects.BudgetCategory;
import comp3350.pbbs.objects.CreditCard;
import comp3350.pbbs.application.Main;
import comp3350.pbbs.application.Services;
import comp3350.pbbs.objects.Transaction;
import comp3350.pbbs.persistence.StubDatabase;

/**
 * AccessCreditCard
 * Group4
 * PBBS
 *
 * This class defines the access layer where deliver credit card info to the database
 */
public class AccessCreditCard {
    private StubDatabase db;    // create an object of the database

    /**
     * constructor: enabling access to the database
     */
    public AccessCreditCard() {
        db = Services.getDataAccess(Main.dbName);
    }

    /**
     * method: find a credit card exist or not in the database
     *
     * @param currCard a credit card needs to be found from the database
     * @return true if this credit card has been added into the database
     */
    public boolean findCreditCard(CreditCard currCard) {
        return db.findCreditCard(currCard);
    }

    /**
     * method: add a credit card into the database
     *
     * @param newCard a credit card needs to be added into the database
     * @return true if this credit card does not exist in the database
     */
    public boolean insertCreditCard(CreditCard newCard) {
        if (!findCreditCard(newCard)) {
            db.insertCreditCard(newCard);
            return true;
        }
        return false;
    }

    /**
     * method: update a credit card existed in the database
     *
     * NOT IMPLEMENTED in presentation for iteration1.
     *
     * @param currCard an old credit card needs to be replaced
     * @param newCard  a new credit card will replace the other one
     * @return true if the old credit card does exist in the database
     */
    public boolean updateCreditCard(CreditCard currCard, CreditCard newCard) {
        return db.updateCreditCard(currCard, newCard);
    }

    /**
     * Getter method to get the credit cards.
     *
     * @return creditCards ArrayList.
     */
    public ArrayList<CreditCard> getCreditCards() {
        return db.getCreditCards();
    }

    /**
     * method: check if the a credit card holder's full name is valid
     *
     * @param str the credit card holder name
     * @return true if the holder name meet the requirement of the format
     */
    public boolean isValidName(String str) {
        return CreditCard.isValidName(str);
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
    public int isValidExpirationDate(String month, String year) {
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

    /**
     * method: check if the input pay date is valid
     *
     * @param n the day
     * @return true if the day is real-world existed
     */
    public boolean isValidPayDate(int n) {
        return CreditCard.isValidPayDate(n);
    }

    /**
     * Calculates the total amount spent for a given CreditCard from the transactions in that category
     * based on the given month
     *
     * @param currentCard is the specified BudgetCategory
     * @param monthAndYear is the month and year to query
     * @return the total amount from transactions in that budget category
     */
    public float calculateCreditCardTotal(CreditCard currentCard, Calendar monthAndYear){
        float sum = 0;
        List<Transaction> transactions = db.getTransactions();

        for(int i = 0; i < transactions.size() && monthAndYear != null; i++){
            Transaction currentTransaction = transactions.get(i);
            CreditCard transactionCard = currentTransaction.getCard();
            Calendar currTime = Calendar.getInstance();
            currTime.setTime(currentTransaction.getTime());
            // Check if the budget categories are the same and if the year and month are the same
            if(transactionCard.equals(currentCard) &&
                    currTime.get(Calendar.MONTH) == monthAndYear.get(Calendar.MONTH) &&
                    currTime.get(Calendar.YEAR) == monthAndYear.get(Calendar.YEAR)){
                sum += currentTransaction.getAmount();
            }
        }

        return sum;
    }

    /**
     * Retrieves a list of months that have transactions for a certain card.
     *
     * @param card      The credit card to query.
     * @return              A list of Calendar instances with the year and month specified.
     */
    public List<Calendar> getActiveMonths(CreditCard card) {
        List<Calendar> activeMonths = new ArrayList<Calendar>();

        // Loop through all transactions
        for(Transaction transaction : db.getTransactions()) {
            if(card.equals(transaction.getCard())) {
                // Construct the calendar object
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(transaction.getTime());
                // Remove time after month
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.HOUR, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                // Add to set if not appeared
                if(!activeMonths.contains(calendar)) {
                    activeMonths.add(calendar);
                }
            }
        }
        return activeMonths;
    }
}