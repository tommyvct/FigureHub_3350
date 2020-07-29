package comp3350.pbbs.persistence;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import comp3350.pbbs.application.Services;
import comp3350.pbbs.objects.*;

public interface DataAccess {

    void open(String dbPath);

    void close();

    String getDBName();

    boolean addBudgetCategories(List<BudgetCategory> budgetList);

    boolean findBudgetCategory(BudgetCategory currentBudget);

    boolean insertBudgetCategory(BudgetCategory newBudget);

    List<BudgetCategory> getBudgets();

    boolean updateBudgetCategory(BudgetCategory currentBudget, BudgetCategory newBudget);

    boolean deleteBudgetCategory(BudgetCategory currentBudget);

    int getBudgetsSize();

    boolean addAllCreditCards(List<CreditCard> cardList);

    boolean findCreditCard(CreditCard currCard);

    boolean insertCreditCard(CreditCard newCard);

    List<CreditCard> getCreditCards();

    boolean updateCreditCard(CreditCard currCard, CreditCard newCard);

    boolean deleteCreditCard(CreditCard currCard);

    int getCardsSize();

    boolean addTransactions(List<Transaction> transactionsList);

    boolean findTransaction(Transaction currentTransaction);

    boolean insertTransaction(Transaction newTransaction);

    List<Transaction> getTransactions();

    boolean updateTransaction(Transaction currentTransaction, Transaction newTransaction);

    boolean deleteTransaction(Transaction currentTransaction);

    int getTransactionsSize();

    String getUsername();

    boolean setUsername(String newUsername);

    /**
     * This method is used for populating fake data into the stub database
     */
    static void populateData(DataAccess dataAccess) {
        BudgetCategory rent, groceries, utilities, phoneBill;   //various types of BudgetCategories
        CreditCard card1, card2;                                //variables for multiple cards
        Transaction t1, t2, t3, t4;                             //variables for multiple transactions
        List<BudgetCategory> budgets = new ArrayList<BudgetCategory>();
        List<CreditCard> creditCards = new ArrayList<CreditCard>();
        List<Transaction> transactions = new ArrayList<Transaction>();

        rent = new BudgetCategory("Rent/Mortgage", 500);
        budgets.add(rent);
        groceries = new BudgetCategory("Groceries", 100);
        budgets.add(groceries);
        utilities = new BudgetCategory("Utilities", 80);
        budgets.add(utilities);
        phoneBill = new BudgetCategory("Phone Bill", 75);
        budgets.add(phoneBill);

        card1 = new CreditCard("Visa", "1000100010001000", "Jimmy", 12, 2021, 18);
        creditCards.add(card1);
        card2 = new CreditCard("Mastercard", "1002100310041005", "Jimmy", 11, 2021, 15);
        creditCards.add(card2);

        //local date variable
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        date = calendar.getTime();
        t1 = new Transaction(Services.calcDate(date, -5), 50, "Bought Chickens", card1, groceries);
        transactions.add(t1);
        t2 = new Transaction(Services.calcDate(date, -8), 450, "Rent Paid", card2, rent);
        transactions.add(t2);
        t3 = new Transaction(Services.calcDate(date, 2), 40, "Hydro bill paid", card2, utilities);
        transactions.add(t3);
        t4 = new Transaction(Services.calcDate(date, 3), 75, "Phone Bill paid", card2, phoneBill);
        transactions.add(t4);

        cards.add(new Card("CIBC Advantage Debit Card", "4506445712345678", "Jimmy", 12, 2021));
        cards.add(new Card("TD Access Card", "4724090212345678", "Jimmy", 11, 2021));
        cards.add(new Card("RBC Client Card", "4519011234567890", "Jimmy", 0, 0));


        dataAccess.addBudgetCategories(budgets);
        dataAccess.addAllCreditCards(creditCards);
        dataAccess.addTransactions(transactions);
    }
}

