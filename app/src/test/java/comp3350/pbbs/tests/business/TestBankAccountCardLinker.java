package comp3350.pbbs.tests.business;

import junit.framework.TestCase;

import java.util.List;

import comp3350.pbbs.business.BankAccountCardLinker;
import comp3350.pbbs.objects.BankAccount;
import comp3350.pbbs.objects.Card;
import comp3350.pbbs.persistence.DataAccessController;
import comp3350.pbbs.persistence.DataAccessI;
import comp3350.pbbs.tests.persistence.StubDatabase;

public class TestBankAccountCardLinker extends TestCase {
	private DataAccessI testDB;
	List<BankAccount> bankAccountList;
	List<Card> debitCardList;
	private BankAccountCardLinker bankAccountCardLinker;

	private String accountName = "MyBMO";
	private String accountNumber = "33336666";
	private Card debitCard = new Card("BMO debit", "1001300350057007", "Henry Hotdog", 12, 2024);
	private BankAccount bankAccount = new BankAccount(accountName, accountNumber, debitCard);

	/**
	 * This method connects to the database, create and initiate instance variables
	 */
	public void setUp() {
		testDB = DataAccessController.createDataAccess(new StubDatabase("populateTest"));
		bankAccountCardLinker = new BankAccountCardLinker();
		testDB.insertCard(debitCard);
		testDB.insertBankAccount(bankAccount);
		debitCardList = testDB.getDebitCards();
		bankAccountList = testDB.getAllBankAccounts();
	}

	/**
	 * test getting the accounts from the current card in the db
	 * test getting new bank account after directly inserting it into db
	 * test new card getting bank accounts after directly inserting it into db
	 */
	public void testGetAccountsFromDebitCard() {
		int numOfAccounts = testDB.getAllBankAccounts().size();
		int numOfDebitCards = testDB.getDebitCards().size();

		// test getting all bank accounts and cards from the db
		assertEquals(testDB.getAllBankAccounts(), bankAccountList);
		assertEquals(testDB.getDebitCards(), debitCardList);

		// test bank account from the debit card inserted into db
		List<BankAccount> list1 = bankAccountCardLinker.getAccountsFromDebitCard(debitCard);
		assertEquals(list1.get(0), bankAccount);

		// test bank account that is directly inserted into db
		BankAccount my2ndBMO = new BankAccount("AnotherBMO", "555000", debitCard);
		testDB.insertBankAccount(my2ndBMO);
		numOfAccounts++;
		assertEquals(bankAccountList.get(numOfAccounts - 1), my2ndBMO); // test from the ArrayList
		List<BankAccount> list2 = bankAccountCardLinker.getAccountsFromDebitCard(debitCard); // test from the Linker
		assertEquals(list2.get(0), bankAccount);

		// test bank account and card newly inserted into db
		Card myICBC = new Card("ICBC debit", "9009800870076006", "Hao Zheng", 6, 2024);
		BankAccount my1stICBC = new BankAccount("1stICBC", "888888", myICBC);
		testDB.insertCard(myICBC);
		testDB.insertBankAccount(my1stICBC);
		numOfDebitCards++;
		numOfAccounts++;
		debitCardList = testDB.getDebitCards();

		assertEquals(debitCardList.get(numOfDebitCards - 1), myICBC);
		assertEquals(bankAccountList.get(numOfAccounts - 1), my1stICBC); // test from the ArrayList
		List<BankAccount> list3 = bankAccountCardLinker.getAccountsFromDebitCard(myICBC); // test from the Linker
		assertEquals(list3.get(0), my1stICBC);
	}
}
