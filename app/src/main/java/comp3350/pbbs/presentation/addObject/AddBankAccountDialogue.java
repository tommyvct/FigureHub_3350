package comp3350.pbbs.presentation.addObject;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.Objects;

import comp3350.pbbs.R;
import comp3350.pbbs.business.AccessBankAccount;
import comp3350.pbbs.objects.BankAccount;
import comp3350.pbbs.objects.Card;

/**
 * AddBankAccountDialogue
 * Group4
 * PBBS
 *
 * This class allows user to add bank accounts to a debit card
 */
public class AddBankAccountDialogue {
	public interface IDialogueCallback {
		void onDismiss();
	}

	public interface IOperationOutcome {
		boolean success(String bankAccountName, String bankAccountNumber);
	}

	/**
	 * Creates a dialog box to update or add a bank account
	 *
	 * @param context			Application context
	 * @param callback  		Callback function
	 * @param outcome   		The outcome of the operation
	 * @param oldBankAccount	The old bank account, null if adding a new bank account
	 */
	public static void dialogue(Context context, IDialogueCallback callback, IOperationOutcome outcome, BankAccount oldBankAccount) {
		AlertDialog dialog = new AlertDialog.Builder(context).create();

		final View layout = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dialogue_add_bank_account, null);
		dialog.setView(layout);

		EditText bankAccountName = layout.findViewById(R.id.addBankAccountName);
		EditText bankAccountNumber = layout.findViewById(R.id.addBankAccountNumber);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setButton(Dialog.BUTTON_NEUTRAL, "Cancel", (dialogInterface, i) -> dialog.dismiss());
		dialog.setOnShowListener((DialogInterface.OnShowListener) dialogInterface -> ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener((View.OnClickListener) view ->
		{
			if (Objects.requireNonNull(bankAccountNumber).getText().toString().isEmpty()) {
				bankAccountNumber.setError("Provide an account number.");
				return;
			}

			String failedToastString = "Failed to " + (oldBankAccount != null ? "update" : "add") + " an account.";

			try {
				if (outcome.success(Objects.requireNonNull(bankAccountName).getText().toString().trim().isEmpty() ? "No name" : bankAccountName.getText().toString().trim(),
						bankAccountNumber.getText().toString().trim())) {
					Toast.makeText(context, (oldBankAccount != null ? "Updated!" : "Added!"), Toast.LENGTH_SHORT).show();
					dialog.dismiss();
				} else {
					Toast.makeText(context, failedToastString, Toast.LENGTH_SHORT).show();
				}
			} catch (IllegalArgumentException ignored) {
				Toast.makeText(context, failedToastString, Toast.LENGTH_SHORT).show();
			}
		}));

		if (oldBankAccount != null) {
			dialog.setTitle("Update Bank Account");
			dialog.setButton(Dialog.BUTTON_POSITIVE, "Update", (DialogInterface.OnClickListener) null);
			bankAccountName.setText(oldBankAccount.getAccountName());
			bankAccountNumber.setText(oldBankAccount.getAccountNumber());
			bankAccountNumber.setEnabled(false);
		} else {
			dialog.setTitle("Add Bank Account");
			dialog.setButton(Dialog.BUTTON_POSITIVE, "Add", (DialogInterface.OnClickListener) null);

		}

		dialog.setOnDismissListener(dialogInterface -> callback.onDismiss());
		dialog.show();
	}

	public static void addBankAccountDialogue(Context context, Card card, AccessBankAccount accessBankAccount, IDialogueCallback callback) {
		dialogue(context, callback, (bankAccountName, bankAccountNumber) -> accessBankAccount.insertBankAccount(new BankAccount(bankAccountName, bankAccountNumber, card)), null);
	}
}
