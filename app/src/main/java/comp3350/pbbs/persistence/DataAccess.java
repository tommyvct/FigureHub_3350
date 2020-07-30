package comp3350.pbbs.persistence;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import comp3350.pbbs.application.Services;
import comp3350.pbbs.objects.*;
import comp3350.pbbs.objects.Card;

public interface DataAccess {

    void open(String dbPath);

    void close();

    String getDBName();

    boolean findBudgetCategory(BudgetCategory currentBudget);

    boolean insertBudgetCategory(BudgetCategory newBudget);

    List<BudgetCategory> getBudgets();

    boolean updateBudgetCategory(BudgetCategory currentBudget, BudgetCategory newBudget);

    int getBudgetsSize();

    boolean findBankAccount(BankAccount toFind);

    boolean insertBankAccount(BankAccount newAccount);

    boolean updateBankAccount(BankAccount toUpdate, BankAccount newAccount);

    List<BankAccount> getAllBankAccounts();

    List<BankAccount> getAccountsFromDebitCard(Card from);

    boolean findCard(Card currCard);

    boolean insertCard(Card newCard);

    List<Card> getCreditCards();

    List<Card> getDebitCards();

    List<Card> getCards();

    List<Card> getActiveCards();

    boolean updateCard(Card currCard, Card newCard);

    boolean markInactive(Card toMark);

    int getCreditCardsSize();

    int getDebitCardsSize();

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
        Card card1, card2, card3, card4, card5;                                      //variables for multiple cards
        Transaction t1, t2, t3, t4;                             //variables for multiple transactions
        BankAccount b1,b2;
        List<BudgetCategory> budgets = new ArrayList<BudgetCategory>();
        List<Card> cards = new ArrayList<Card>();
        List<Transaction> transactions = new ArrayList<Transaction>();
        List<BankAccount> bankAccounts = new ArrayList<BankAccount>();

        rent = new BudgetCategory("Rent/Mortgage", 500);
        budgets.add(rent);
        groceries = new BudgetCategory("Groceries", 100);
        budgets.add(groceries);
        utilities = new BudgetCategory("Utilities", 80);
        budgets.add(utilities);
        phoneBill = new BudgetCategory("Phone Bill", 75);
        budgets.add(phoneBill);

        card1 = new Card("Visa", "1000100010001000", "Jimmy", 12, 2021, 18);
        cards.add(card1);
        card2 = new Card("Mastercard", "1002100310041005", "Jimmy", 11, 2021, 15);
        cards.add(card2);

        //local date variable
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-mm-dd").parse("2020-07-25");
        } catch (ParseException e) {
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        date = calendar.getTime();
        t1 = new Transaction(Services.calcDate(date, -5), 50, "Bought Chickens", card1, groceries);
        transactions.add(t1);
        t2 = new Transaction(Services.calcDate(date, -8), 450, "Rent Paid", card2, rent);
        transactions.add(t2);
        t3 = new Transaction(Services.calcDate(date, 0), 40, "Hydro bill paid", card2, utilities);
        transactions.add(t3);
        t4 = new Transaction(Services.calcDate(date, -10), 75, "Phone Bill paid", card2, phoneBill);
        transactions.add(t4);

        card3 = new Card("CIBC Advantage Debit Card", "4506445712345678", "Jimmy", 12, 2021);
        cards.add(card3);
        card4 = new Card("TD Access Card", "4724090212345678", "Jimmy", 11, 2021);
        cards.add(card4);
        card5 = new Card("RBC Client Card", "4519011234567890", "Jimmy", 0, 0);
        cards.add(card5);

        b1 = new BankAccount("TD student banking", "50998924", card4);
        bankAccounts.add(b1);
        b2 = new BankAccount("CIBC banking", "290948376", card3);
        bankAccounts.add(b2);

        for(BudgetCategory b : budgets) {
            dataAccess.insertBudgetCategory(b);
        }
        for(Card c : cards) {
            dataAccess.insertCard(c);
        }
        for(Transaction t : transactions) {
            dataAccess.insertTransaction(t);
        }
        for (BankAccount b : bankAccounts) {
            dataAccess.insertBankAccount(b);
        }
    }
}

