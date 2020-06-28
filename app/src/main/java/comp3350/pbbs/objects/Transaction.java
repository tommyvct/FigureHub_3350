package comp3350.pbbs.objects;


import java.text.DecimalFormat;
import java.time.LocalDateTime;

public class Transaction {
    private int transactionID;
    private static int nextID = 1;
    private LocalDateTime time;
    private float amount;
    // TODO: Make these fields correspond to the objects rather than id.
    private int cardID;
    private int budgetCategoryID;
    public Transaction(LocalDateTime transactionTime, float amount, int card, int budgetCategory) {
        if(transactionTime == null)
            throw new IllegalArgumentException("A Transaction needs a time.");
        if(amount < 0)
            throw new IllegalArgumentException("Expected a positive transaction amount.");
        if(card <= 0)
            throw new IllegalArgumentException("Expected a positive card id.");
        if(budgetCategory <= 0)
            throw new IllegalArgumentException("Expected a positive budget category id.");
        time = transactionTime;
        this.amount = amount;
        cardID = card;
        budgetCategoryID = budgetCategory;
        transactionID = nextID;
        nextID++;
    }

    public LocalDateTime getTime() { return time; }
    public float getAmount() { return amount; }
    public int getCard() { return cardID; }
    public int getBudgetCategory() { return budgetCategoryID; }

    public boolean equals(Object other) {
        boolean toReturn = false;
        if(other != null && other instanceof Transaction) {
            Transaction otherTransaction = (Transaction) other;
            // TODO: Change these to .equals
            boolean budgetsEqual = this.getBudgetCategory() == (otherTransaction.getBudgetCategory());
            DecimalFormat rounding = new DecimalFormat("0.00");
            boolean amountEqual = rounding.format(this.getAmount()).equals(rounding.format(otherTransaction.getAmount()));
            boolean cardEqual = this.getCard() == (otherTransaction.getCard());
            boolean timeEqual = this.getTime().equals(otherTransaction.getTime());
            toReturn = budgetsEqual && amountEqual && cardEqual && timeEqual;
        }
        return toReturn;
    }

    public String toString()
    {
        return "Transaction: " +
                "ID: " + transactionID +
                " Amount: " + amount +
                " Time: " + time +
                " Card: " + cardID +
                " Budget Category: " + budgetCategoryID;
    }
}
