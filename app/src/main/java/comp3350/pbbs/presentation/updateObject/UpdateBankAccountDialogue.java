package comp3350.pbbs.presentation.updateObject;

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
import comp3350.pbbs.presentation.addObject.AddBankAccountDialogue;

public class UpdateBankAccountDialogue
{

    public static void updateBankAccountDialogue(Context context, BankAccount oldBankAccount, AccessBankAccount accessBankAccount, AddBankAccountDialogue.IDialogueCallback callback)
    {
        AddBankAccountDialogue.dialogue(context, callback, (bankAccountName, bankAccountNumber) -> accessBankAccount.updateBankAccount(oldBankAccount, new BankAccount(bankAccountName, bankAccountNumber, oldBankAccount.getLinkedCard())), oldBankAccount);

//        AlertDialog dialog = new AlertDialog.Builder(context).create();
//        dialog.setTitle("Update Bank Account");
//
//        final View layout = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dialogue_add_bank_account, null);
//
//        dialog.setView(layout);
//        EditText bankAccountName = layout.findViewById(R.id.addBankAccountName);
//        EditText bankAccountNumber = layout.findViewById(R.id.addBankAccountNumber);
//        bankAccountName.setText(oldBankAccount.getAccountName());
//        bankAccountNumber.setText(oldBankAccount.getAccountNumber());
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.setButton(Dialog.BUTTON_POSITIVE, "Update", (DialogInterface.OnClickListener) null);
//        dialog.setButton(Dialog.BUTTON_NEUTRAL, "Cancel", (dialogInterface, i) -> dialog.dismiss());
//        dialog.setOnShowListener((DialogInterface.OnShowListener) dialogInterface -> ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener((View.OnClickListener) view ->
//        {
//            if (Objects.requireNonNull(bankAccountNumber).getText().toString().isEmpty())
//            {
//                bankAccountNumber.setError("Provide an account number.");
//                return;
//            }
//
//            try
//            {
//                if (new AccessBankAccount().updateBankAccount(oldBankAccount,
//                        new BankAccount(
//                                Objects.requireNonNull(bankAccountName).getText().toString().trim().isEmpty() ? "No name" : bankAccountName.getText().toString().trim(),
//                                bankAccountNumber.getText().toString().trim(),
//                                oldBankAccount.getLinkedCard()
//                        )))
//                {
//                    Toast.makeText(context, "Updated!", Toast.LENGTH_SHORT).show();
//                    dialog.dismiss();
//                }
//                else
//                {
//                    Toast.makeText(context, "Failed to update an account.", Toast.LENGTH_SHORT).show();
//                }
//            }
//            catch (IllegalArgumentException ignored)
//            {
//                Toast.makeText(context, "Failed to update an account.", Toast.LENGTH_SHORT).show();
//            }
//        }));
//        dialog.setOnDismissListener(dialogInterface -> callback.onDismiss());
//        dialog.show();
    }

}
