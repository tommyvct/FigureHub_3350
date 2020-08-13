package comp3350.pbbs.business;

import java.util.List;

import comp3350.pbbs.application.Main;
import comp3350.pbbs.persistence.DataAccessController;
import comp3350.pbbs.objects.BudgetCategory;
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
        dataAccess = DataAccessController.getDataAccess(Main.dbName);
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
        if ((limitFlt = Parser.parseAmount(limit)) != null && limitFlt > 0 && Validation.isValidName(label) && label.length() > 0) {
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
        if (oldBudgetCategory != null && (newLimitFlt = Parser.parseAmount(newLimit)) != null && newLimitFlt > 0 && Validation.isValidName(newLabel))
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
    private boolean updateBudgetCategoryParsed(BudgetCategory currentBudget, BudgetCategory newBudget) {
        return dataAccess.updateBudgetCategory(currentBudget, newBudget);
    }
}
