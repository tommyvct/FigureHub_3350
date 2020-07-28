package comp3350.pbbs.business;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import comp3350.pbbs.application.Main;
import comp3350.pbbs.application.Services;
import comp3350.pbbs.objects.BudgetCategory;
import comp3350.pbbs.objects.Transaction;
import comp3350.pbbs.persistence.StubDatabase;

/**
 * AccessBudgetCategory
 * Group4
 * PBBS
 *
 * This class provides safe access to the stub DB to access and modify the DB
 */
public class AccessBudgetCategory {
    private StubDatabase dataAccess;                    //variable for the database
    private List<BudgetCategory> budgetCategories;      //budgetCategories list
    private BudgetCategory budgetCat;                   //a BudgetCategory object
    private int currentBudgetCat;                       //number of budgetCategories

    /**
     * This method creates the link to the DB.
     * Also initializes all class variables.
     */
    public AccessBudgetCategory() {
        dataAccess = Services.getDataAccess(Main.dbName);
        budgetCategories = null;
        budgetCat = null;
        currentBudgetCat = 0;
    }

    /**
     * This method adds budget categories if they have not been added, or gets
     * the next budgetCat at position currentBudgetCat, or restarts at
     * currentBudgetCat = 0.
     *
     * @return the budgetCategory at position currentBudgetCat in the ArrayList
     */
    @SuppressWarnings("unused")
    public BudgetCategory getBudgetCategory() {
        if (budgetCategories == null) {
            budgetCategories = new ArrayList<>();
            dataAccess.addBudgetCategories(budgetCategories);
            currentBudgetCat = 0;
        }
        if (currentBudgetCat < budgetCategories.size())
        {
            budgetCat = budgetCategories.get(currentBudgetCat);
            currentBudgetCat++;
        } else {
            budgetCategories = null;
            budgetCat = null;
            currentBudgetCat = 0;
        }
        return budgetCat;
    }

    /**
     * To get all the current budget categories in the DB
     *
     * @return the list of categories
     */
    public ArrayList<BudgetCategory> getAllBudgetCategories() {
        return dataAccess.getBudgets();
    }

    /**
     * For finding if a budget category is in the DB
     *
     * @param currentBudgetCategory the category we are looking for
     * @return null if it can't be found, or the category found.
     */
    public BudgetCategory findBudgetCategory(BudgetCategory currentBudgetCategory) {
        return dataAccess.findBudgetCategory(currentBudgetCategory);
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
        if ((limitFlt = AccessValidation.parseAmount(limit)) != null && limitFlt > 0 && AccessValidation.isValidName(label) && label.length() > 0){
            BudgetCategory newBC = new BudgetCategory(label, limitFlt);
            if(findBudgetCategory(newBC) == null)
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
     *
     * NOT IMPLEMENTED in presentation for iteration1.
     *
     * @param  oldBudgetCategory the category to be replaced
     * @param newLabel name of the BudgetCategory
     * @param newLimit limit of the BudgetCategory in string form - to be parsed to Float
     * @return success
     * deleting a BudgetCategory.@return update budgetCategory
     */
    public BudgetCategory updateBudgetCategory(BudgetCategory oldBudgetCategory, String newLabel, String newLimit){
        Float newLimitFlt;
        BudgetCategory result = null;
        if((newLimitFlt = AccessValidation.parseAmount(newLimit)) != null && newLimitFlt > 0 && newLabel.length() > 0)
            result = updateBudgetCategoryParsed(oldBudgetCategory, new BudgetCategory(newLabel, newLimitFlt));
        return result;
    }

    /**
     * Replaces one budget category with another in the DB
     *
     * NOT IMPLEMENTED in presentation for iteration1.
     *
     * @param currentBudget the budget category currently in the DB
     * @param newBudget     the budget category to replace currentBudget
     * @return updated budgetCategory
     */
    public BudgetCategory updateBudgetCategoryParsed(BudgetCategory currentBudget, BudgetCategory newBudget) {
        return dataAccess.updateBudgetCategory(currentBudget, newBudget);
    }

    public BudgetCategory deleteBudgetCategory(BudgetCategory budgetCat){
        return dataAccess.deleteBudgetCategory(budgetCat);
    }


    /**
     * Calculates the total amount spent for a given BudgetCategory from the transactions in that category
     * based on the given month
     *
     * @param currentBudgetCat is the specified BudgetCategory
     * @param monthAndYear is the month and year to query
     * @return the total amount from transactions in that budget category
     */
    public float calculateBudgetCategoryTotal(BudgetCategory currentBudgetCat, Calendar monthAndYear){
        float sum = 0;
        List<Transaction> transactions = dataAccess.getTransactions();

        for(int i = 0; i < transactions.size() && monthAndYear != null; i++){
            Transaction currentTransaction = transactions.get(i);
            BudgetCategory transactionBudget = currentTransaction.getBudgetCategory();
            Calendar currTime = Calendar.getInstance();
            currTime.setTime(currentTransaction.getTime());
            // Check if the budget categories are the same and if the year and month are the same
            if(transactionBudget.equals(currentBudgetCat) &&
               currTime.get(Calendar.MONTH) == monthAndYear.get(Calendar.MONTH) &&
               currTime.get(Calendar.YEAR) == monthAndYear.get(Calendar.YEAR)){
                sum += currentTransaction.getAmount();
            }
        }

        return sum;
    }


    /**
     * Retrieves a list of months that have transactions for a certain budget category.
     *
     * @param category      The budget Category to query.
     * @return              A list of Calendar instances with the year and month specified.
     */
    public List<Calendar> getActiveMonths(BudgetCategory category) {
        List<Calendar> activeMonths = new ArrayList<Calendar>();

        // Loop through all transactions
        for(Transaction transaction : dataAccess.getTransactions()) {
            if(category.equals(transaction.getBudgetCategory())) {
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
