package comp3350.pbbs.objects;

/**
 * budgetCategories
 * Azizul Hakim
 * PBBS
 *
 * This class defines the object budget category which can be used to create many types of budget.
 */
public class budgetCategories {
    private String budgetID;        //ID of the budget
    private String budgetName;      //Name of the budget
    private double budgetLimit;     //Limit of the category

    /**
     * This is the constructor method which assigns the fields to a Budget Category
     *
     * @param newID The ID of this budget
     * @param newBudgetName The name of this budget
     * @param newLimit The limit of this budget
     */
    public budgetCategories(String newID, String newBudgetName, double newLimit){
        //Limit can't be negative
        if(newLimit <0){
            throw new IllegalArgumentException("Expected a budget limit should be positive");
        }
        this.budgetID = newID;
        this.budgetName = newBudgetName;
        this.budgetLimit = newLimit;
    }

    //Getters for the object fields
    public String getBudgetID(){ return budgetID; }
    public String getBudgetName(){ return budgetName; }
    public double getBudgetLimit(){ return budgetLimit; }

    /**
     * This method returns a String representation of this object
     * @return A String representation of this object
     */
    public String toString(){
        return "Budget: "+budgetID+" "+budgetName+" "+budgetLimit;
    }

    /**
     * This method tells if the budget category is equal to the budgetObj or not
     *
     * @param budgetObj The object to compare
     * @return True if this budget has the same ID as the object
     */
    public boolean equals(Object budgetObj){
        boolean equal = false;
        budgetCategories b;

        if(budgetObj instanceof budgetCategories){
            b = (budgetCategories) budgetObj;
            if(((b.budgetID == null) && (budgetID == null))||(b.budgetID.equals((budgetID)))){
                equal = true;
            }
        }
        return equal;
        }
    }

