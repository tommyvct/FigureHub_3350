package comp3350.pbbs.presentation.viewObject;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Objects;

import comp3350.pbbs.R;
import comp3350.pbbs.business.AccessBankAccount;
import comp3350.pbbs.objects.BankAccount;
import comp3350.pbbs.objects.Card;
import comp3350.pbbs.presentation.addObject.AddBankAccountDialogue;
import comp3350.pbbs.presentation.updateObject.UpdateBankAccountDialogue;

public class ViewBankAccount extends AppCompatActivity
{
    Card debitCard;
    AccessBankAccount accessBankAccount;
    List<BankAccount> bankAccountArrayList;
    ArrayAdapter<BankAccount> listViewAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bank_account);


        accessBankAccount = new AccessBankAccount();
        debitCard = (Card) getIntent().getSerializableExtra("DebitCard");
        Objects.requireNonNull(getSupportActionBar()).setTitle("Bank Accounts");


        ListView bankAccountListView = findViewById(R.id.view_bank_account_list);

        if (debitCard == null)
        {
            bankAccountArrayList = accessBankAccount.getAllBankAccounts();
            findViewById(R.id.add_bank_account_fab).setVisibility(View.GONE);
        }
        else
        {
            bankAccountArrayList = accessBankAccount.getBankAccountsFromDebitCard(debitCard);
            Objects.requireNonNull(getSupportActionBar()).setSubtitle(debitCard.toStringShort());
            findViewById(R.id.add_bank_account_fab).setOnClickListener(view ->
            {
                AddBankAccountDialogue.dialogue(view.getContext(), debitCard, () ->
                {
                    bankAccountArrayList = (debitCard == null) ? accessBankAccount.getAllBankAccounts() : accessBankAccount.getBankAccountsFromDebitCard(debitCard);
                    listViewAdapter.clear();
                    listViewAdapter.addAll(bankAccountArrayList);
                    listViewAdapter.notifyDataSetChanged();
                });
            });
        }

        listViewAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,bankAccountArrayList);
        bankAccountListView.setAdapter(listViewAdapter);
        bankAccountListView.setOnItemClickListener((adapterView, view, i, l) ->
        {
            UpdateBankAccountDialogue.dialogue(view.getContext(), bankAccountArrayList.get(i), () ->
            {
                bankAccountArrayList = (debitCard == null) ? accessBankAccount.getAllBankAccounts() : accessBankAccount.getBankAccountsFromDebitCard(debitCard);
                listViewAdapter.clear();
                listViewAdapter.addAll(bankAccountArrayList);
                listViewAdapter.notifyDataSetChanged();
            });
        });
    }
}
