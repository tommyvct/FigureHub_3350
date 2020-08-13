package comp3350.pbbs.business;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import comp3350.pbbs.application.Main;
import comp3350.pbbs.objects.BudgetCategory;
import comp3350.pbbs.objects.Transaction;
import comp3350.pbbs.persistence.DataAccessController;
import comp3350.pbbs.persistence.DataAccessI;

public class BudgetCategoryTransactionLinker {
    private DataAccessI dataAccess;                    // variable for the database

    /**
     * This method creates the link to the DB.
     * Also initializes all class variables.
     */
    public BudgetCategoryTransactionLinker() {
        dataAccess = DataAccessController.getDataAccess(Main.dbName);
    }

    /**
     * Calculates the total amount spent for a given BudgetCategory from the transactions in that category
     * based on the given month
     *
     * @param currentBudgetCat is the specified BudgetCategory
     * @param monthAndYear     is the month and year to query
     * @return the total amount from transactions in that budget category
     */
    public float calculateBudgetCategoryTotal(BudgetCategory currentBudgetCat, Calendar monthAndYear) {
        float sum = 0;
        List<Transaction> transactions = dataAccess.getTransactions();

        for (int i = 0; i < transactions.size() && monthAndYear != null; i++) {
            Transaction currentTransaction = transactions.get(i);
            BudgetCategory transactionBudget = currentTransaction.getBudgetCategory();
            Calendar currTime = Calendar.getInstance();
            currTime.setTime(currentTransaction.getTime());
            // Check if the budget categories are the same and if the year and month are the same
            if (transactionBudget.equals(currentBudgetCat) &&
                    currTime.get(Calendar.MONTH) == monthAndYear.get(Calendar.MONTH) &&
                    currTime.get(Calendar.YEAR) == monthAndYear.get(Calendar.YEAR)) {
                sum += currentTransaction.getAmount();
            }
        }
        return sum;
    }

    /**
     * Retrieves a list of months that have transactions for a certain budget category.
     *
     * @param category The budget Category to query.
     * @return A list of Calendar instances with the year and month specified.
     */
    public List<Calendar> getActiveMonths(BudgetCategory category) {
        List<Calendar> activeMonths = new ArrayList<Calendar>();

        // Loop through all transactions
        for (Transaction transaction : dataAccess.getTransactions()) {
            if (category.equals(transaction.getBudgetCategory())) {
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
