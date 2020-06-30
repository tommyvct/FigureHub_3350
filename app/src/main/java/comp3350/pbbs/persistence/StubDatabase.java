package comp3350.pbbs.persistence;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import comp3350.pbbs.application.*;
import comp3350.pbbs.objects.*;

public class StubDatabase {
    private String databaseName;

    private ArrayList<BudgetCategory> budgets;
    private ArrayList<CreditCard> creditCards;
    private ArrayList<Transaction> transactions;
    private ArrayList<User> user;
    private LocalDateTime time;

    public StubDatabase(String name){
        this.databaseName = name;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void populateData(){
        BudgetCategory rent, groceries, utilities, phoneBill;
        CreditCard card1, card2;
        Transaction t1, t2, t3, t4;
        User user1;

        time = LocalDateTime.now();

        budgets = new ArrayList<BudgetCategory>();
        rent = new BudgetCategory("Rent/Mortgage", 500);
        budgets.add(rent);
        groceries = new BudgetCategory("Groceries", 100);
        budgets.add(groceries);
        utilities = new BudgetCategory("Utilities", 80);
        budgets.add(utilities);
        phoneBill = new BudgetCategory("Phone Bill", 75);
        budgets.add(phoneBill);

        creditCards = new ArrayList<CreditCard>();
        card1 = new CreditCard("1000100010001000", "Jimmy", 12, 2021, 18);
        creditCards.add(card1);
        card2 = new CreditCard("1002100310041005", "Jimmy", 11, 2021, 15);
        creditCards.add(card2);

        transactions = new ArrayList<Transaction>();
        t1 = new Transaction(time.minusDays(5), 50, "Bought Chickens", card1, groceries);
        transactions.add(t1);
        t2 = new Transaction(time.minusDays(8), 450, "Rent Paid", card2, rent);
        transactions.add(t2);
        t3 = new Transaction(time.minusDays(2), 40, "Hydro bill paid", card2, utilities);
        transactions.add(t3);
        t4 = new Transaction(time.minusDays(3), 75, "Phone Bill paid", card2, phoneBill);
        transactions.add(t4);

        user = new ArrayList<User>();
        user1 = new User("Jimmy","Kimel");
        user.add(user1);
    }

    //this method will add all the budgets to a budget list
    //returns true if added successfully.
    public boolean getBudgets(List<BudgetCategory> budgetList){
        return budgetList.addAll(budgets);
    }

    //this method will find if a budget exist or not
    //returns the BudgetCategory object
    public BudgetCategory findBudget(BudgetCategory currentBudget){
        BudgetCategory budgetCategory = null;
        int index = budgets.indexOf(currentBudget);
        if(index >= 0){
            budgetCategory = budgets.get(index);
        }
        return budgetCategory;
    }

    public void insertBudgetCategories(BudgetCategory newBudget){
        budgets.add(newBudget);
    }

    //This method will be used to update a Budget
    public void setBudgets(BudgetCategory currentBudget){
        int index = budgets.indexOf(currentBudget);
        if (index >= 0){
            budgets.set(index,currentBudget);
        }
    }

    //This method will remove a budget category
    public void deleteBudget(BudgetCategory currentBudget){
        int index = budgets.indexOf(currentBudget);
        if (index >= 0){
            budgets.remove(index);
        }
    }

    //this method will add all the cards to a card list
    //returns true if added successfully.
    public boolean getCards(List<CreditCard> cardstList){
        return cardstList.addAll(creditCards);
    }

    //this method will find if a card exist or not
    //returns the card object
    public CreditCard findCard(CreditCard currentCard){
        CreditCard card = null;
        int index = creditCards.indexOf(currentCard);
        if(index >= 0){
            card = creditCards.get(index);
        }
        return card;
    }

    public void insertCreditCard(CreditCard newCard){
        creditCards.add(newCard);
    }

    //This method will be used to update a credit card
    public void setCreditCards(CreditCard currentCard){
        int index = creditCards.indexOf(currentCard);
        if (index >= 0){
            creditCards.set(index,currentCard);
        }
    }

    //This method will remove a credit card
    public void deleteCard(CreditCard currentCard){
        int index = creditCards.indexOf(currentCard);
        if (index >= 0){
            creditCards.remove(index);
        }
    }

    //this method will add all the transactions to a transaction list
    //returns true if added successfully.
    public boolean getTransactions(List<Transaction> transactionsList){
        return transactionsList.addAll(transactions);
    }

    //this method will find if a transaction exist or not
    //returns the transaction object
    public Transaction findTransaction(Transaction currentTransaction){
        Transaction transaction = null;
        int index = transactions.indexOf(currentTransaction);
        if(index >= 0){
            transaction = transactions.get(index);
        }
        return transaction;
    }

    public void insertTransactions(Transaction newTransaction){
        transactions.add(newTransaction);
    }

    //This method will be used to update a transaction
    public void setTransactions(Transaction currentTransaction){
        int index = transactions.indexOf(currentTransaction);
        if (index >= 0){
            transactions.set(index,currentTransaction);
        }
    }

    //This method will remove a budget category
    public void deleteTransaction(Transaction currentTransaction){
        int index = transactions.indexOf(currentTransaction);
        if (index >= 0){
            transactions.remove(index);
        }
    }





}
