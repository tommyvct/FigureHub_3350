package comp3350.pbbs.business;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import comp3350.pbbs.application.Main;
import comp3350.pbbs.objects.Card;
import comp3350.pbbs.objects.Transaction;
import comp3350.pbbs.persistence.DataAccessController;
import comp3350.pbbs.persistence.DataAccessI;

public class CardTransactionLinker {
    private DataAccessI db;    // create an object of the database

    /**
     * constructor: enabling access to the database
     */
    public CardTransactionLinker() {
        db = DataAccessController.getDataAccess(Main.dbName);
    }

    /**
     * Calculates the total amount spent for a given Card from the transactions in that category
     * based on the given month
     *
     * @param currentCard  is the specified BudgetCategory
     * @param monthAndYear is the month and year to query
     * @return the total amount from transactions in that budget category
     */
    public float calculateCardTotal(Card currentCard, Calendar monthAndYear) {
        float sum = 0;
        List<Transaction> transactions = db.getTransactions();

        for (int i = 0; i < transactions.size() && monthAndYear != null; i++) {
            Transaction currentTransaction = transactions.get(i);
            Card transactionCard = currentTransaction.getCard();
            Calendar currTime = Calendar.getInstance();
            currTime.setTime(currentTransaction.getTime());
            // Check if the budget categories are the same and if the year and month are the same
            if (transactionCard.equals(currentCard) &&
                    currTime.get(Calendar.MONTH) == monthAndYear.get(Calendar.MONTH) &&
                    currTime.get(Calendar.YEAR) == monthAndYear.get(Calendar.YEAR)) {
                sum += currentTransaction.getAmount();
            }
        }

        return sum;
    }

    /**
     * Retrieves a list of months that have transactions for a certain card.
     *
     * @param card The credit card to query.
     * @return A list of Calendar instances with the year and month specified.
     */
    public List<Calendar> getActiveMonths(Card card) {
        if (card == null) {
            throw new NullPointerException("Expected a non null card");
        }
        List<Calendar> activeMonths = new ArrayList<Calendar>();

        // Loop through all transactions
        for (Transaction transaction : db.getTransactions()) {
            if (card.equals(transaction.getCard())) {
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
                if (!activeMonths.contains(calendar)) {
                    activeMonths.add(calendar);
                }
            }
        }
        return activeMonths;
    }
}
