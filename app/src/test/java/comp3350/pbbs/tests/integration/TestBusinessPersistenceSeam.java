package comp3350.pbbs.tests.integration;

import junit.framework.TestCase;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import comp3350.pbbs.application.Main;
import comp3350.pbbs.business.*;
import comp3350.pbbs.objects.*;
import comp3350.pbbs.persistence.*;
import comp3350.pbbs.tests.persistence.NuclearDataAccessObject;
import comp3350.pbbs.tests.persistence.StubDatabase;

import static org.junit.Assert.assertNotEquals;


public class TestBusinessPersistenceSeam extends TestCase {

	private DataAccessI dataAccess;

	public TestBusinessPersistenceSeam(String arg0) {
		super(arg0);
	}

	@Override
	public void setUp() {
		dataAccess = DataAccessController.createDataAccess(new NuclearDataAccessObject(Main.getDBPathName()));
		if (dataAccess instanceof NuclearDataAccessObject) {
			((NuclearDataAccessObject) dataAccess).nuke();
		}
		StubDatabase.populateData(dataAccess);
	}

	@Override
	public void tearDown() {
		if(dataAccess instanceof NuclearDataAccessObject) {
			((NuclearDataAccessObject) dataAccess).nuke();
		}
		DataAccessController.closeDataAccess();
	}

	public void testAccessBankAccount() {
		DataAccessController.closeDataAccess();
		dataAccess = DataAccessController.createDataAccess(new NuclearDataAccessObject(Main.getDBPathName()));

		AccessBankAccount aba = new AccessBankAccount();
		BankAccount acc1 = new BankAccount("test1", "1357924680", dataAccess.getCards().get(0));
		BankAccount acc2 = new BankAccount("test2", "2468013579", dataAccess.getCards().get(0));
		BankAccount acc3 = new BankAccount("test3", "1234500000", dataAccess.getCards().get(1));
		BankAccount acc4 = new BankAccount("test4", "6789099999", dataAccess.getCards().get(0));

		assertTrue(aba.findBankAccount(dataAccess.getAllBankAccounts().get(0)));
		int numOfAcct = dataAccess.getAllBankAccounts().size();

		// will not find a budget category that wasn't inserted
		assertFalse(dataAccess.findBankAccount(acc2));

		// insert a new bank account into db
		// new added bank account will be at the end of the bank account list
		aba.insertBankAccount(acc1);
		assertTrue(dataAccess.findBankAccount(acc1));
		numOfAcct++;
		assertEquals(dataAccess.getAllBankAccounts().get(numOfAcct - 1), acc1);

		// a debit card can have more than 1 bank accounts
		aba.insertBankAccount(acc4);
		assertTrue(dataAccess.findBankAccount(acc4));
		numOfAcct++;

		// cannot have duplicated bank accounts
		assertFalse(aba.insertBankAccount(acc1));
		assertFalse(aba.updateBankAccount(dataAccess.getAllBankAccounts().get(0), acc1));

		// cannot update a bank account that is pre-stored
		aba.updateBankAccount(dataAccess.getAllBankAccounts().get(0), acc2);
		assertNotEquals(dataAccess.getAllBankAccounts().get(0), acc2);

		// replace the 1st pre-stored bank account by a different one
		// the updated bank account will be at the top of the budget category list
		aba.updateBankAccount(dataAccess.getAllBankAccounts().get(numOfAcct - 1), acc2);
		assertEquals(dataAccess.getAllBankAccounts().get(numOfAcct - 1), acc2);

		// cannot update a bank account from a different debit card
		aba.updateBankAccount(dataAccess.getAllBankAccounts().get(numOfAcct - 1), acc3);
		assertNotEquals(dataAccess.getAllBankAccounts().get(numOfAcct - 1), acc3);

		// test the number of accounts of a debit card
		int count = dataAccess.getAccountsFromDebitCard(dataAccess.getCards().get(0)).size();
		assertEquals(count, 2);

		DataAccessController.closeDataAccess();
	}

	public void testAccessBudgetCategory() {
		DataAccessController.closeDataAccess();
		dataAccess = DataAccessController.createDataAccess(new NuclearDataAccessObject(Main.getDBPathName()));

		AccessBudgetCategory abc = new AccessBudgetCategory();
		BudgetCategory bc1 = new BudgetCategory("Beer", 30.00);
		BudgetCategory bc2 = new BudgetCategory("Snack", 15.00);

		assertTrue(abc.findBudgetCategory(dataAccess.getBudgets().get(0)));
		int numOfBudget = dataAccess.getBudgetsSize();

		// will not find a budget category that wasn't inserted
		assertFalse(dataAccess.findBudgetCategory(bc2));

		// insert a new budget category into db
		// new added budget category will be at the end of the budget category list
		abc.insertBudgetCategory("Beer", "30.00");
		assertTrue(dataAccess.findBudgetCategory(bc1));
		numOfBudget++;
		assertEquals(dataAccess.getBudgets().get(numOfBudget - 1), bc1);

		// replace the 1st pre-stored budget category by a different one
		// the updated budget category will be at the top of the budget category list
		assertTrue(abc.updateBudgetCategory(dataAccess.getBudgets().get(0), "Snack", "15.00"));
		assertEquals(dataAccess.getBudgets().get(0), bc2);

		DataAccessController.closeDataAccess();
	}

	public void testAccessCard() {
		DataAccessController.closeDataAccess();
		dataAccess = DataAccessController.createDataAccess(new NuclearDataAccessObject(Main.getDBPathName()));

		AccessCard ac = new AccessCard();
		Card c1 = new Card("test1", "1000200030004000", "Hao", 12, 2022, 6);
		Card c2 = new Card("test2", "5000600070008000", "Hao", 6, 2024);

		assertTrue(ac.findCard(dataAccess.getCards().get(0)));
		int numOfCredit = dataAccess.getCreditCardsSize();
		int numOfDebit = dataAccess.getDebitCardsSize();

		// will not find a card that wasn't inserted
		assertFalse(dataAccess.findCard(c2));

		// add a credit card into db
		// new added credit card will be at the end of the credit card list
		ac.insertCard(c1);
		assertTrue(dataAccess.findCard(c1));
		numOfCredit++;
		assertEquals(dataAccess.getCreditCards().get(numOfCredit - 1), c1);

		// replace the added credit card by a debit card
		// the updated debit card will be at the top of the debit card list
		// number of debit cards increased by 1
		assertTrue(ac.updateCard(dataAccess.getCards().get(0), c2));
		assertEquals(dataAccess.getDebitCards().get(0), c2);
		numOfDebit++;
		numOfCredit--;

		// test numbers of credit cards and debit cards
		assertEquals(dataAccess.getCreditCardsSize(), numOfCredit);
		assertEquals(dataAccess.getDebitCardsSize(), numOfDebit);

		// mark a card inactive, number of active cards = number of total cards - 1
		ac.markInactive(dataAccess.getDebitCards().get(numOfDebit - 1));
		assertEquals(dataAccess.getActiveCards().size(), dataAccess.getCards().size() - 1);
		ac.markActive(dataAccess.getDebitCards().get(numOfDebit - 1));
		assertEquals(dataAccess.getActiveCards().size(), dataAccess.getCards().size());

		DataAccessController.closeDataAccess();
	}

	public void testBankAccountCardLinker() {
		DataAccessController.closeDataAccess();
		dataAccess = DataAccessController.createDataAccess(new NuclearDataAccessObject(Main.getDBPathName()));

		// create objects for testing
		BankAccountCardLinker bcLinker = new BankAccountCardLinker();
		Card myICBC = new Card("ICBC debit", "9009800870076006", "Hao Zheng", 6, 2024);
		BankAccount my1stICBC = new BankAccount("1stICBC", "888888", myICBC);
		BankAccount my2ndICBC = new BankAccount("2ndICBC", "666666", myICBC);
		int numOfNewAccounts = 0;

		// test bank account from a new card inserted into db
		dataAccess.insertCard(myICBC);
		dataAccess.insertBankAccount(my1stICBC);
		assertEquals(bcLinker.getAccountsFromDebitCard(myICBC).get(numOfNewAccounts), my1stICBC);

		// test bank account from an existed card inserted into db
		dataAccess.insertBankAccount(my2ndICBC);
		numOfNewAccounts++;
		assertEquals(bcLinker.getAccountsFromDebitCard(myICBC).get(numOfNewAccounts), my2ndICBC);

		DataAccessController.closeDataAccess();
	}

	public void testCardTransactionLinker() {
		DataAccessController.closeDataAccess();
		dataAccess = DataAccessController.createDataAccess(new NuclearDataAccessObject(Main.getDBPathName()));

		// create objects for testing
		CardTransactionLinker ctLinker = new CardTransactionLinker();
		BudgetCategory bc = new BudgetCategory("Entertainment", 100);
		Card card = new Card("mastercard", "1001200230034004", "Si-Chuan Hotpot", 12, 2024, 12);
		Date date = new Date(2020 - 7 - 15);
		Calendar transMonth = Calendar.getInstance();
		transMonth.setTime(date);

		// insert budget category and card for transactions
		dataAccess.insertBudgetCategory(bc);
		dataAccess.insertCard(card);

		// test calculating card total for single transaction
		Transaction t1 = new Transaction(date, 25.50f, "Watched a movie", card, bc);
		dataAccess.insertTransaction(t1);
		assertEquals(ctLinker.calculateCardTotal(card, transMonth), t1.getAmount());

		// test calculating card total for multiple transactions
		Transaction t2 = new Transaction(date, 29.99f, "Bought a discounted game", card, bc);
		dataAccess.insertTransaction(t2);
		assertEquals(ctLinker.calculateCardTotal(card, transMonth), t1.getAmount() + t2.getAmount());

		// test getting active months for single transaction
		List<Calendar> listOfMonths = ctLinker.getActiveMonths(card);
		assertEquals(listOfMonths.size(), 1);
		transMonth.set(Calendar.DAY_OF_MONTH, 1);
		transMonth.set(Calendar.HOUR_OF_DAY, 0);
		transMonth.set(Calendar.HOUR, 0);
		transMonth.set(Calendar.MINUTE, 0);
		transMonth.set(Calendar.SECOND, 0);
		transMonth.set(Calendar.MILLISECOND, 0);
		assertTrue(listOfMonths.contains(transMonth));

		// test getting active months for multiple transactions
		transMonth.set(2020, 6, 6);
		Transaction t3 = new Transaction(transMonth.getTime(), 10.00f, "Bought a lottery", card, bc);
		dataAccess.insertTransaction(t3);
		listOfMonths = ctLinker.getActiveMonths(card);
		assertEquals(listOfMonths.size(), 2);
		transMonth.set(Calendar.DAY_OF_MONTH, 1);
		transMonth.set(Calendar.HOUR_OF_DAY, 0);
		transMonth.set(Calendar.HOUR, 0);
		transMonth.set(Calendar.MINUTE, 0);
		transMonth.set(Calendar.SECOND, 0);
		transMonth.set(Calendar.MILLISECOND, 0);
		assertTrue(listOfMonths.contains(transMonth));

		DataAccessController.closeDataAccess();
	}

	public void testBudgetCategoryTransactionLinker() {
		DataAccessController.closeDataAccess();
		dataAccess = DataAccessController.createDataAccess(new NuclearDataAccessObject(Main.getDBPathName()));

		// create objects for testing
		BudgetCategoryTransactionLinker bctLinker = new BudgetCategoryTransactionLinker();
		BudgetCategory bc = new BudgetCategory("Entertainment", 100);
		Card card = new Card("mastercard", "1001200230034004", "Si-Chuan Hotpot", 12, 2024, 12);
		Date date = new Date(2020 - 7 - 15);
		Calendar transMonth = Calendar.getInstance();
		transMonth.setTime(date);

		// insert budget category and card for transactions
		dataAccess.insertBudgetCategory(bc);
		dataAccess.insertCard(card);

		// test calculating budget total for single transaction
		Transaction t1 = new Transaction(date, 25.50f, "Watched a movie", card, bc);
		dataAccess.insertTransaction(t1);
		assertEquals(bctLinker.calculateBudgetCategoryTotal(bc, transMonth), t1.getAmount());

		// test calculating budget total for multiple transactions
		Transaction t2 = new Transaction(date, 29.99f, "Bought a discounted game", card, bc);
		dataAccess.insertTransaction(t2);
		assertEquals(bctLinker.calculateBudgetCategoryTotal(bc, transMonth), t1.getAmount() + t2.getAmount());

		// test getting active months for single transaction
		List<Calendar> listOfMonths = bctLinker.getActiveMonths(bc);
		assertEquals(listOfMonths.size(), 1);
		transMonth.set(Calendar.DAY_OF_MONTH, 1);
		transMonth.set(Calendar.HOUR_OF_DAY, 0);
		transMonth.set(Calendar.HOUR, 0);
		transMonth.set(Calendar.MINUTE, 0);
		transMonth.set(Calendar.SECOND, 0);
		transMonth.set(Calendar.MILLISECOND, 0);
		assertTrue(listOfMonths.contains(transMonth));

		// test getting active months for multiple transactions
		transMonth.set(2020, 6, 6);
		Transaction t3 = new Transaction(transMonth.getTime(), 10.00f, "Bought a lottery", card, bc);
		dataAccess.insertTransaction(t3);
		listOfMonths = bctLinker.getActiveMonths(bc);
		assertEquals(listOfMonths.size(), 2);
		transMonth.set(Calendar.DAY_OF_MONTH, 1);
		transMonth.set(Calendar.HOUR_OF_DAY, 0);
		transMonth.set(Calendar.HOUR, 0);
		transMonth.set(Calendar.MINUTE, 0);
		transMonth.set(Calendar.SECOND, 0);
		transMonth.set(Calendar.MILLISECOND, 0);
		assertTrue(listOfMonths.contains(transMonth));

		DataAccessController.closeDataAccess();
	}
}
