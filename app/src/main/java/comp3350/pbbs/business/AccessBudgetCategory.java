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
     * Adds a list of budget categories to the DB
     *
     * @param currentBudgetCategories a list of new categories to add to the DB
     * @return success boolean
     */
    public boolean addBudgetCategories(List<BudgetCategory> currentBudgetCategories) {
        return dataAccess.addBudgetCategories(currentBudgetCategories);
    }

    /**
     * parse the input from a String passed from Presentation layer, to a Float
     *
     * @param limitStr the string to be parsed into a float
     * @return return the parsed string.
     */
    private Float parseLimit(String limitStr) {
        Float result = null;
        if (limitStr != null) {
            if (limitStr.contains(".")) {
                if (limitStr.matches("\\d*\\.\\d\\d$")) {
                    result = Float.parseFloat(limitStr);
                    if (result < 0)
                        result = null;
                }
            } else if (limitStr.matches("[0-9]+")) {
                result = (float) Integer.parseInt(limitStr);
            }
        }
        return result;
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
        if ((limitFlt = parseLimit(limit)) != null && limitFlt > 0 && label.length() > 0)
            result = insertBudgetCategoryParsed(new BudgetCategory(label, limitFlt));

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
        if((newLimitFlt = parseLimit(newLimit)) != null && newLimitFlt > 0 && newLabel.length() > 0)
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

    /**
     * Takes in params directly from Presentation layer, and converts them to proper format for
     * deleting a BudgetCategory
     *
     * NOT IMPLEMENTED in presentation for iteration1.
     *
     * @param currentBudgetCat the category to be removed
     * @return deleted budgetCategory
     */
    public BudgetCategory deleteBudgetCategory(BudgetCategory currentBudgetCat)
    {
        return dataAccess.deleteBudgetCategory(currentBudgetCat);
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
}
