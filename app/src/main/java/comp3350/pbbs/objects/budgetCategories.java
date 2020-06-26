package comp3350.pbbs.objects;

public class budgetCategories {
    private String budgetID;
    private double budgetLimit;
    private String budgetName;

    public budgetCategories(String newID, double newLimit, String newBudgetName){
        this.budgetID = newID;
        this.budgetLimit = newLimit;
        this.budgetName = newBudgetName;
    }
    public String getBudgetID(){
        return budgetID;
    }
    public double getBudgetLimit(){
        return budgetLimit;
    }
    public String getBudgetName(){
        return budgetName;
    }
    public String toString(){
        return "Budget ID: "+budgetID+" Budget Name: "+budgetName+" Budget Limit: "+budgetLimit;
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

