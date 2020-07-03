package comp3350.pbbs.business;

import java.util.ArrayList;
import java.util.List;

import comp3350.pbbs.application.Main;
import comp3350.pbbs.application.Services;
import comp3350.pbbs.objects.BudgetCategory;
import comp3350.pbbs.persistence.StubDatabase;

/**
 * Class AccessBudgetCategory
 * Purpose: to provide safe access to the stub DB to access and modify the DB
 */
public class AccessBudgetCategory {
    private StubDatabase dataAccess;
    private List<BudgetCategory> budgetCategories;
    private BudgetCategory budgetCat;
    private int currentBudgetCat;

    /**
     * This method creates the link to the DB.
     * Also initializes all class variables.
     */
    public AccessBudgetCategory ()
    {
        dataAccess = (StubDatabase) Services.getDataAccess(Main.dbName);
        budgetCategories = null;
        budgetCat = null;
        currentBudgetCat = 0;
    }

    /**
     * This method adds budget categories if they have not been added, or gets
     * the next budgetCat at position currentBudgetCat, or restarts at
     * currentBudgetCat = 0.
     * @return the budgetCategory at position currentBudgetCat in the arraylist
     */
    public BudgetCategory getBudgetCategory()
    {
        boolean result = false;
        if (budgetCategories == null)
        {
            budgetCategories = new ArrayList<BudgetCategory>();
            result = dataAccess.addBudgetCategories(budgetCategories);
            currentBudgetCat = 0;
        }
        if (currentBudgetCat < budgetCategories.size())
        {
            budgetCat = (BudgetCategory) budgetCategories.get(currentBudgetCat);
            currentBudgetCat++;
        }
        else
        {
            budgetCategories = null;
            budgetCat = null;
            currentBudgetCat = 0;
        }
        return budgetCat;
    }

    /**
     * To get all the current budget categories in the DB
     * @return the list of categories
     */
    public ArrayList<BudgetCategory> getAllBudgetCategories(){
        return dataAccess.getBudgets();
    }

    /**
     * For finding if a budget category is in the DB
     * @param currentBudgetCategory the category we are looking for
     * @return null if it can't be found, or the category found.
     */
    public BudgetCategory findBudgetCategory(BudgetCategory currentBudgetCategory){
        return dataAccess.findBudgetCategory(currentBudgetCategory);
    }

    /**
     * Adds a list of budget categories to the DB
     * @param currentBudgetCategories a list of new categories to add to the DB
     * @return success boolean
     */
    public boolean addBudgetCategories(List<BudgetCategory> currentBudgetCategories)
    {
        return dataAccess.addBudgetCategories(currentBudgetCategories);
    }

    /**
     * parse the input from a String passed from Presentation layer, to a Float
     * @param limitStr the string to be parsed into a float
     * @return return the parsed string.
     */
    private Float parseLimit(String limitStr) {
        Float result = null;
        if(limitStr != null) {
            if(limitStr.contains(".")) {
                if(limitStr.matches("\\d*\\.\\d\\d$")) {
                    result = Float.parseFloat(limitStr);
                    if(result < 0)
                        result = null;
                }
            }
            else if(limitStr.matches("[0-9]+")){
                result = (float)Integer.parseInt(limitStr);
            }
        }
        return result;
    }

    /**
     * Takes in params directly from Presentation layer, and converts them to proper format for
     * BudgetCategory
     * @param label name of the BudgetCategory
     * @param limit limit of the BudgetCategory in string form - to be parsed to Float
     * @return success
     */
    public boolean addBudgetCategory(String label, String limit){
        Float limitFlt = parseLimit(limit);
        return insertBudgetCategory(new BudgetCategory(label, limitFlt));
    }

    /**
     * Inserts a single new budget category to database
     * @param currentBudgetCat the new category to be added
     */
    public boolean insertBudgetCategory(BudgetCategory currentBudgetCat)
    {
        return dataAccess.insertBudgetCategory(currentBudgetCat);
    }

    /**
     * Takes in params directly from Presentation layer, and converts them to proper format for
     * deleting a BudgetCategory
     * @param oldLabel, newLabel name of the BudgetCategory
     * @param oldLimit, newLimit limit of the BudgetCategory in string form - to be parsed to Float
     * @return success
     */
    public BudgetCategory updateBudgetCategoryParse(String oldLabel, String oldLimit, String newLabel, String newLimit){
        Float limit1Flt = parseLimit(oldLimit);
        Float limit2Flt = parseLimit(newLimit);
        return updateBudgetCategory(new BudgetCategory(oldLabel, limit1Flt), new BudgetCategory(newLabel, limit2Flt));
    }

    /**
     * Replaces one budget category with another in the DB
     * @param currentBudget the budget category currently in the DB
     * @param newBudget the budget category to replace currentBudget
     */
    public BudgetCategory updateBudgetCategory(BudgetCategory currentBudget, BudgetCategory newBudget)
    {
        return dataAccess.updateBudgetCategory(currentBudget, newBudget);
    }

    /**
     * Takes in params directly from Presentation layer, and converts them to proper format for
     * deleting a BudgetCategory
     * @param label name of the BudgetCategory
     * @param limit limit of the BudgetCategory in string form - to be parsed to Float
     * @return success
     */
    public BudgetCategory deleteBudgetCategoryParse(String label, String limit){
        Float limitFlt = parseLimit(limit);
        return deleteBudgetCategory(new BudgetCategory(label, limitFlt));
    }

    /**
     * Removes a BudgetCategory from the DB
     * @param currentBudgetCat the category to be removed
     */
    public BudgetCategory deleteBudgetCategory(BudgetCategory currentBudgetCat)
    {
        return dataAccess.deleteBudgetCategory(currentBudgetCat);
    }

}
