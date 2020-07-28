package comp3350.pbbs.objects;


import android.annotation.SuppressLint;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import comp3350.pbbs.objects.Cards.Card;

/**
 * Transaction
 * Group4
 * PBBS
 *
 * This class defines a transaction for some amount paid for using a card under some budget
 * category.
 */
public class Transaction implements Serializable
{
    private Date time;                      // The time that the transaction took place
    private float amount;                   // The amount that this transaction totalled
    private String description;             // Description of this transaction
    private Card card;                // The card that this transaction was paid on
    private BudgetCategory budgetCategory;  // The budget category this transaction fell under
    private BankAccount bankAccount;

    /**
     * This method validates the parameters and assigns them.
     *
     * @param transactionTime The time this transaction happened.
     * @param amount          The amount this transaction went for.
     * @param description     The description of this transaction.
     * @param card            The card this transaction used
     * @param budgetCategory  The category this transaction fell under.
     */
    public Transaction(Date transactionTime, float amount, String description, Card card, BudgetCategory budgetCategory)
    {
        initTransaction(transactionTime, amount, description, budgetCategory);

        if (card == null)
            throw new IllegalArgumentException("A Transaction needs a card.");

        this.card = card;
        this.bankAccount = null;
    }
    /**
     * This method validates the parameters and assigns them.
     *
     * @param transactionTime The time this transaction happened.
     * @param amount          The amount this transaction went for.
     * @param description     The description of this transaction.
     * @param card            The card this transaction used
     * @param bankAccount     The bank account used
     * @param budgetCategory  The category this transaction fell under.
     */
    public Transaction(Date transactionTime, float amount, String description, Card card, BankAccount bankAccount, BudgetCategory budgetCategory)
    {
        initTransaction(transactionTime, amount, description, budgetCategory);

        if (card == null)
            throw new IllegalArgumentException("A Transaction needs a card.");

        if (bankAccount == null)
            throw new IllegalArgumentException("A Transaction needs a bank account.");

        if (!bankAccount.getLinkedCard().equals(card))
        {
            throw new IllegalArgumentException("This card does not have given account.");
        }
        this.card = card;
        this.bankAccount = bankAccount;
    }

    /**
     * This method validates the parameters and assigns them.
     *
     * @param transactionTime The time this transaction happened.
     * @param amount          The amount this transaction went for.
     * @param description     The description of this transaction.
     * @param budgetCategory  The category this transaction fell under.
     */
    private void initTransaction(Date transactionTime, float amount, String description, BudgetCategory budgetCategory)
    {
        // Validate input
        if (transactionTime == null)
            throw new IllegalArgumentException("A Transaction needs a time.");
        if (amount < 0)
            throw new IllegalArgumentException("Expected a positive transaction amount.");
        if (description == null || description.isEmpty())
            throw new IllegalArgumentException("A Transaction needs a description.");
        if (budgetCategory == null)
            throw new IllegalArgumentException("A Transaction needs a budget category.");
        // Set object fields
        time = transactionTime;
        this.amount = amount;
        this.description = description;
        this.budgetCategory = budgetCategory;
    }

    // Getters for object fields
    public Date getTime() {
        return time;
    }

    public float getAmount() {
        return amount;
    }

    public Card getCard() {
        return card;
    }

    public BudgetCategory getBudgetCategory() {
        return budgetCategory;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Tells if this Transaction is equal to the other object.
     *
     * @param other The other object to compare.
     * @return True if this transaction has the same time, amount, card, and budget category,
     * or false otherwise.
     */
    public boolean equals(Object other) {
        boolean toReturn = false;
        if (other instanceof Transaction) {
            Transaction otherTransaction = (Transaction) other;
            // Compare budget categories
            boolean budgetsEqual = this.getBudgetCategory().equals(otherTransaction.getBudgetCategory());
            // Compare cards
            boolean cardEqual = this.getCard().equals(otherTransaction.getCard());
            // Compare amounts by rounding to two decimal points.
            DecimalFormat rounding = new DecimalFormat("0.00");
            boolean amountEqual = rounding.format(this.getAmount()).equals(rounding.format(otherTransaction.getAmount()));
            // Compare the transaction time
            boolean timeEqual = this.getTime().equals(otherTransaction.getTime());
            // Compare the description
            boolean descEqual = this.getDescription().equals(otherTransaction.getDescription());
            // Check if all are equal
            toReturn = budgetsEqual && amountEqual && cardEqual && timeEqual && descEqual;
        }
        return toReturn;
    }

    /**
     * Gets a string representation of this object.
     *a
     * @return A string representing this object and its fields.
     */
    @NotNull
    public String toString() {
        DecimalFormat rounding = new DecimalFormat("0.00");
        @SuppressLint("SimpleDateFormat")
        DateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy 'at' H:m");

        return  "" + description + "\n" +
                "$" + rounding.format(amount) + "\n" +
                dateFormat.format(time) + "\n" +
                card.toStringShort() + "\n" +
                "Belongs to " + budgetCategory.getBudgetName() + " budget";

    }
}
