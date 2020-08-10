package comp3350.pbbs.objects;


import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * BudgetCategory
 * Group4
 * PBBS
 *
 * This class defines the object budget category which can be used to create many types of budget.
 */
public class BudgetCategory implements Serializable {
    private String budgetName;      //Name of the budget
    private double budgetLimit;     //Limit of the category

    /**
     * This is the constructor method which assigns the fields to a Budget Category
     *
     * @param newBudgetName The name of this budget
     * @param newLimit      The limit of this budget
     */
    public BudgetCategory(String newBudgetName, double newLimit) {
        //Limit can't be negative
        if (newLimit < 0) {
            throw new IllegalArgumentException("Expected a budget limit should be positive");
        }
        if (newBudgetName == null || newBudgetName.isEmpty()) {
            throw new IllegalArgumentException("Expected a budget limit should have a name");
        }
        this.budgetName = newBudgetName;
        this.budgetLimit = newLimit;
    }

    //Getters for the object fields
    public String getBudgetName() {
        return budgetName;
    }

    public double getBudgetLimit() {
        return budgetLimit;
    }

    /**
     * This method returns a String representation of this object
     *
     * @return A String representation of this object
     */
    @NotNull
    public String toString() {
        return "" + budgetName + "\nLimit: $" + new DecimalFormat("0.00").format(budgetLimit);
    }

    /**
     * This method tells if the budget category is equal to the budgetObj or not
     *
     * @param budgetObj The object to compare
     * @return True if this budget is the same as the other budget
     */
    public boolean equals(Object budgetObj) {
        boolean equal = false;
        BudgetCategory b;

//        DecimalFormat rounding = new DecimalFormat("0.00");
        if (budgetObj instanceof BudgetCategory) {
            b = (BudgetCategory) budgetObj;
            if (b.getBudgetName().equals(this.getBudgetName())) {
                equal = true;
            }
        }
        return equal;
    }
}

