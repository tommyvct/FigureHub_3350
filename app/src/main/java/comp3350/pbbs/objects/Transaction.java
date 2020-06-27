package comp3350.pbbs.objects;


import java.time.LocalDateTime;

public class Transaction {
    private int transactionID;
    private static int nextID = 1;
    private LocalDateTime time;
    // TODO: Make these fields correspond to the objects rather than id.
    private int cardID;
    private int budgetCategoryID;
    public Transaction(LocalDateTime transactionTime, int card, int budgetCategory) {
        if(transactionTime == null)
            throw new IllegalArgumentException("A Transaction needs a time.");
        if(card <= 0)
            throw new IllegalArgumentException("Expected a positive card id.");
        if(budgetCategory <= 0)
            throw new IllegalArgumentException("Expected a positive budget category id.");
        time = transactionTime;
        cardID = card;
        transactionID = nextID;
        nextID++;
    }

    public LocalDateTime getTime() { return time; }
    public int getCard() { return cardID; }
    public int getBudgetCategory() { return budgetCategoryID; }

    public boolean equals(Transaction other) {
        boolean toReturn;
        if(other == null) {
            toReturn = false;
        }
        else {
            // TODO: Change these to .equals
            boolean budgetsEqual = this.getBudgetCategory() == (other.getBudgetCategory());
            boolean cardEqual = this.getCard() == (other.getCard());
            boolean timeEqual = this.getTime().equals(other.getTime());
            toReturn = budgetsEqual && cardEqual && timeEqual;
        }
        return toReturn;
    }
}
