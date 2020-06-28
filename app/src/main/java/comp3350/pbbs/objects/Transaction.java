package comp3350.pbbs.objects;


import java.text.DecimalFormat;
import java.time.LocalDateTime;

/**
 * Transaction
 * Joshua Smallwood
 * PBBS
 *
 * This class defines a transaction for some amount paid for using a card under some budget
 * category.
 */
public class Transaction {
    private int transactionID;      // ID of this transaction
    private static int nextID = 1;  // The next transaction ID to assign
    private LocalDateTime time;     // The time that the transaction took place
    private float amount;           // The amount that this transaction totalled
    // TODO: Make these fields correspond to the objects rather than id.
    private int cardID;             // The card that this transaction was paid on
    private int budgetCategoryID;   // The budget category this transaction fell under

    /**
     * This method validates the parameters and assigns them.
     *
     * @param transactionTime The time this transaction happened.
     * @param amount The amount this transaction went for.
     * @param card The card this transaction used
     * @param budgetCategory The category this transaction fell under.
     */
    public Transaction(LocalDateTime transactionTime, float amount, int card, int budgetCategory) {
        // Validate input
        if(transactionTime == null)
            throw new IllegalArgumentException("A Transaction needs a time.");
        if(amount < 0)
            throw new IllegalArgumentException("Expected a positive transaction amount.");
        if(card <= 0)
            throw new IllegalArgumentException("Expected a positive card id.");
        if(budgetCategory <= 0)
            throw new IllegalArgumentException("Expected a positive budget category id.");
        // Set object fields
        time = transactionTime;
        this.amount = amount;
        cardID = card;
        budgetCategoryID = budgetCategory;
        transactionID = nextID;
        nextID++;
    }

    // Getters for object fields
    public LocalDateTime getTime() { return time; }
    public float getAmount() { return amount; }
    public int getCard() { return cardID; }
    public int getBudgetCategory() { return budgetCategoryID; }

    /**
     * Tells if this Transaction is equal to the other object.
     *
     * @param other The other object to compare.
     * @return True if this transaction has the same time, amount, card, and budget category,
     *         or false otherwise.
     */
    public boolean equals(Object other) {
        boolean toReturn = false;
        if(other instanceof Transaction) {
            Transaction otherTransaction = (Transaction) other;
            // TODO: Change these to .equals
            // Compare budget categories
            boolean budgetsEqual = this.getBudgetCategory() == (otherTransaction.getBudgetCategory());
            // Compare cards
            boolean cardEqual = this.getCard() == (otherTransaction.getCard());
            // Compare amounts by rounding to two decimal points.
            DecimalFormat rounding = new DecimalFormat("0.00");
            boolean amountEqual = rounding.format(this.getAmount()).equals(rounding.format(otherTransaction.getAmount()));
            // Compare the transaction time
            boolean timeEqual = this.getTime().equals(otherTransaction.getTime());
            // Check if all are equal
            toReturn = budgetsEqual && amountEqual && cardEqual && timeEqual;
        }
        return toReturn;
    }

    /**
     * Gets a string representation of this object.
     * @return A string representing this object and its fields.
     */
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
