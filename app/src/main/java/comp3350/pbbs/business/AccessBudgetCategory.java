package comp3350.pbbs.business;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import comp3350.pbbs.application.Main;
import comp3350.pbbs.application.Services;
import comp3350.pbbs.objects.BudgetCategory;
import comp3350.pbbs.objects.Transaction;
import comp3350.pbbs.persistence.DataAccessI;

/**
 * AccessBudgetCategory
 * Group4
 * PBBS
 *
 * This class provides safe access to the stub DB to access and modify the DB
 */
public class AccessBudgetCategory {
    private DataAccessI dataAccess;                    // variable for the database

    /**
     * This method creates the link to the DB.
     * Also initializes all class variables.
     */
    public AccessBudgetCategory() {
        dataAccess = Services.getDataAccess(Main.dbName);
    }

    /**
     * To get all the current budget categories in the DB
     *
     * @return the list of categories
     */
    public List<BudgetCategory> getAllBudgetCategories() {
        return dataAccess.getBudgets();
    }

    /**
     * For finding if a budget category is in the DB
     *
     * @param currentBudgetCategory the category we are looking for
     * @return False if it can't be found, or true if the category found.
     */
    public boolean findBudgetCategory(BudgetCategory currentBudgetCategory) {
        if (currentBudgetCategory != null)
            return dataAccess.findBudgetCategory(currentBudgetCategory);
        else return false;
    }

    /**
     * Takes in params directly from Presentation layer, and converts them to proper format for
     * BudgetCategory
     *
     * @param label name of the BudgetCategory
     * @param limit limit of the BudgetCategory in string form - to be parsed to Float
     * @return true if the budget inserted successfully
     */
    public boolean insertBudgetCategory(String label, String limit) {
        Float limitFlt;
        boolean result = false;
        if ((limitFlt = Parser.parseAmount(limit)) != null && limitFlt > 0 && AccessValidation.isValidName(label) && label.length() > 0) {
            BudgetCategory newBC = new BudgetCategory(label, limitFlt);
            if (!findBudgetCategory(newBC))
                result = insertBudgetCategoryParsed(newBC);
        }
        return result;
    }

    /**
     * Inserts a single new budget category to database
     *
     * @param currentBudgetCat the new category to be added
     * @return true if the budgetCategory inserted successfully.
     */
    public boolean insertBudgetCategoryParsed(BudgetCategory currentBudgetCat) {
        return dataAccess.insertBudgetCategory(currentBudgetCat);
    }

    /**
     * Takes in params directly from Presentation layer, and converts them to proper format for
     * deleting a BudgetCategory
     * <p>
     * NOT IMPLEMENTED in presentation for iteration1.
     *
     * @param oldBudgetCategory the category to be replaced
     * @param newLabel          name of the BudgetCategory
     * @param newLimit          limit of the BudgetCategory in string form - to be parsed to Float
     * @return success
     * deleting a BudgetCategory.@return update budgetCategory
     */
    public boolean updateBudgetCategory(BudgetCategory oldBudgetCategory, String newLabel, String newLimit) {
        Float newLimitFlt;
        boolean result = false;
        if (oldBudgetCategory != null && (newLimitFlt = Parser.parseAmount(newLimit)) != null && newLimitFlt > 0 && AccessValidation.isValidName(newLabel))
            result = updateBudgetCategoryParsed(oldBudgetCategory, new BudgetCategory(newLabel, newLimitFlt));
        return result;
    }

    /**
     * Replaces one budget category with another in the DB
     * <p>
     * NOT IMPLEMENTED in presentation for iteration1.
     *
     * @param currentBudget the budget category currently in the DB
     * @param newBudget     the budget category to replace currentBudget
     * @return True if updated, of false if not updated
     */
    public boolean updateBudgetCategoryParsed(BudgetCategory currentBudget, BudgetCategory newBudget) {
        return dataAccess.updateBudgetCategory(currentBudget, newBudget);
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
