package comp3350.pbbs.presentation.updateObject;

import android.content.Context;

import comp3350.pbbs.business.AccessBankAccount;
import comp3350.pbbs.objects.BankAccount;
import comp3350.pbbs.presentation.addObject.AddBankAccountDialogue;

/**
 * UpdateBankAccountDialogue
 * Group4
 * PBBS
 *
 * This class allows user to update a bank account
 */
public class UpdateBankAccountDialogue {
	
	/**
	 * Creates the bank account dialogue specifically for updating.
	 *
	 * @param context			Current application context
	 * @param oldBankAccount	Bank account we're updating
	 * @param accessBankAccount	Access object for bank accounts
	 * @param callback			Call back method
	 */
	public static void updateBankAccountDialogue(Context context, BankAccount oldBankAccount, AccessBankAccount accessBankAccount, AddBankAccountDialogue.IDialogueCallback callback) {
		AddBankAccountDialogue.dialogue(context, callback, (bankAccountName, bankAccountNumber) -> accessBankAccount.updateBankAccount(oldBankAccount, new BankAccount(bankAccountName, bankAccountNumber, oldBankAccount.getLinkedCard())), oldBankAccount);
	}

}
