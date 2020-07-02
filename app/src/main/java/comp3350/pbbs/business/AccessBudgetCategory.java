package comp3350.pbbs.business;

import java.util.List;

import comp3350.pbbs.application.Main;
import comp3350.pbbs.application.Services;
import comp3350.pbbs.objects.BudgetCategory;
import comp3350.pbbs.persistence.StubDatabase;

public class AccessBudgetCategory {
    private StubDatabase dataAccess;
    private List<BudgetCategory> budgetCategories;
    private BudgetCategory budgetCat;
    private int currentBudgetCat;

    public AccessBudgetCategory ()
    {
        dataAccess = (StubDatabase) Services.getDataAccess(Main.dbName);
        budgetCategories = null;
        budgetCat = null;
        currentBudgetCat = 0;
    }

}
