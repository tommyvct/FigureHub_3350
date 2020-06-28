package comp3350.pbbs.objects;

public class budgetCategories {
    private String budgetID;
    private String budgetName;
    private double budgetLimit;

    public budgetCategories(String newID, String newBudgetName, double newLimit){
        if(newLimit <0){
            throw new IllegalArgumentException("Expected a budget limit should be positive");
        }

        this.budgetID = newID;
        this.budgetName = newBudgetName;
        this.budgetLimit = newLimit;
    }
    public String getBudgetID(){
        return budgetID;
    }
    public String getBudgetName(){
        return budgetName;
    }
    public double getBudgetLimit(){
        return budgetLimit;
    }
    public String toString(){
        return "Budget: "+budgetID+" "+budgetName+" "+budgetLimit;
    }
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

