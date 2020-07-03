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
     * Inserts a single new budget category to database
     * @param currentBudgetCat the new category to be added
     */
    public boolean insertBudgetCategory(BudgetCategory currentBudgetCat)
    {
        return dataAccess.insertBudgetCategory(currentBudgetCat);
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
     * Removes a BudgetCategory from the DB
     * @param currentBudgetCat the category to be removed
     */
    public BudgetCategory deleteBudgetCategory(BudgetCategory currentBudgetCat)
    {
        return dataAccess.deleteBudgetCategory(currentBudgetCat);
    }

}
