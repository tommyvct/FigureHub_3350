package comp3350.pbbs.tests.objects;

import java.text.DecimalFormat;

/**
 * BudgetCategories
 * Azizul Hakim
 * PBBS
 *
 * This class defines the object budget category which can be used to create many types of budget.
 */
public class BudgetCategories {
    private String budgetName;      //Name of the budget
    private double budgetLimit;     //Limit of the category

    /**
     * This is the constructor method which assigns the fields to a Budget Category
     *
     * @param newBudgetName The name of this budget
     * @param newLimit The limit of this budget
     */
    public BudgetCategories(String newBudgetName, double newLimit){
        //Limit can't be negative
        if(newLimit <0){
            throw new IllegalArgumentException("Expected a budget limit should be positive");
        }
        this.budgetName = newBudgetName;
        this.budgetLimit = newLimit;
    }

    //Getters for the object fields
    public String getBudgetName(){ return budgetName; }
    public double getBudgetLimit(){ return budgetLimit; }

    /**
     * This method returns a String representation of this object
     * @return A String representation of this object
     */
    public String toString(){
        return "Budget: "+budgetName+" "+budgetLimit;
    }

    /**
     * This method tells if the budget category is equal to the budgetObj or not
     *
     * @param budgetObj The object to compare
     * @return True if this budget is the same as the other budget
     */
    public boolean equals(Object budgetObj){
        boolean equal = false;
        BudgetCategories b;

        DecimalFormat rounding = new DecimalFormat("0.00");
        if(budgetObj instanceof BudgetCategories){
            b = (BudgetCategories) budgetObj;
            if((rounding.format(b.getBudgetLimit()).equals(rounding.format(this.getBudgetLimit()))) && b.getBudgetName().equals(this.getBudgetName())){
                equal = true;
            }
        }
        return equal;
        }
    }

