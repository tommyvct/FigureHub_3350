package comp3350.pbbs.tests.business;

import junit.framework.TestCase;
import comp3350.pbbs.application.Services;
import comp3350.pbbs.business.AccessBankAccount;
import comp3350.pbbs.objects.BankAccount;
import comp3350.pbbs.objects.Cards.DebitCard;

public class TestAccessBankAccount extends TestCase
{
	private DebitCard debitCard;
	private BankAccount bankAccount;
	private AccessBankAccount accessBankAccount;

	public void setUp() {
		Services.createDataAccess("TBCU");
		debitCard = new DebitCard("TD Access Debit", "5135794680086666", "John", 5, 2022);
		bankAccount = new BankAccount("My TD", "1357964", debitCard);
		accessBankAccount = new AccessBankAccount();
		accessBankAccount.insertBankAccount(bankAccount);	// add "bankAccount" into DB
	}

	public void testFindBankAccount() {
		DebitCard dC = new DebitCard("CIBC Advantage Debit", "4506445712345678", "Tommy", 3, 2024);
		BankAccount bA = new BankAccount("My RBC", "4682579", dC);
		assertTrue(accessBankAccount.findBankAccount(bankAccount));
		assertFalse(accessBankAccount.findBankAccount(bA));
	}

	public void testInsertBankAccount() {
		DebitCard dC = new DebitCard("CIBC Advantage Debit", "4506445712345678", "Tommy", 3, 2024);
		BankAccount bA = new BankAccount("My RBC", "4682579", dC);
		assertTrue(accessBankAccount.insertBankAccount(bA));
		assertFalse(accessBankAccount.insertBankAccount(bA));
	}

	public void testDeleteBankAccount() {
		DebitCard dC = new DebitCard("CIBC Advantage Debit", "4506445712345678", "Tommy", 3, 2024);
		BankAccount bA = new BankAccount("My RBC", "4682579", dC);
		assertTrue(accessBankAccount.deleteBankAccount(bankAccount));
		assertFalse(accessBankAccount.deleteBankAccount(bankAccount));
		assertFalse(accessBankAccount.deleteBankAccount(bA));
	}

	public void testUpdateBankAccount() {
		DebitCard dC = new DebitCard("CIBC Advantage Debit", "4506445712345678", "Tommy", 3, 2024);
		BankAccount bA = new BankAccount("My RBC", "4682579", dC);
		assertFalse(accessBankAccount.updateBankAccount(bA, bankAccount));
		assertTrue(accessBankAccount.updateBankAccount(bankAccount, bA));
		assertFalse(accessBankAccount.updateBankAccount(bankAccount, bA));
	}
}
