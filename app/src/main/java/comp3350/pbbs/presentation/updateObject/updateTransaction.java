package comp3350.pbbs.presentation.updateObject;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import comp3350.pbbs.R;
import comp3350.pbbs.business.AccessBudgetCategory;
import comp3350.pbbs.business.AccessCard;
import comp3350.pbbs.business.AccessTransaction;
import comp3350.pbbs.business.AccessValidation;
import comp3350.pbbs.objects.BudgetCategory;
import comp3350.pbbs.objects.Cards.Card;
import comp3350.pbbs.objects.Transaction;

public class updateTransaction extends AppCompatActivity implements OnItemSelectedListener {

    DatePickerDialog datePickerDialog;              //variable for DatePickerDialog
    EditText dateText;                              //EditText variable for date
    TimePickerDialog timePickerDialog;              //variable for TimePickerDialog
    EditText timeText;                              //EditText variable for time
    final Calendar c = Calendar.getInstance();      //Calendar variable to get the relevant date
    AccessTransaction accessTransaction;            //AccessTransaction variable
    AccessCard accessCreditCard;              //AccessCreditCard variable
    AccessBudgetCategory accessBudget;              //AccessBudgetCategory variable
    Transaction oldTransaction;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Update Transaction");

        oldTransaction = Objects.requireNonNull((Transaction) getIntent().getSerializableExtra("toUpdate"));

        ((EditText) findViewById(R.id.addTransAmount)).setText(new DecimalFormat("0.00").format(oldTransaction.getAmount()));
        ((EditText) findViewById(R.id.addTransDescription)).setText(oldTransaction.getDescription());

        String[] reversedDateTime = AccessTransaction.reverseParseDateTime(oldTransaction.getTime());
        ///////// Date Input //////////
        accessTransaction = new AccessTransaction();
        dateText = findViewById(R.id.dateInput);
        dateText.setText(reversedDateTime[0]);
        dateText.setOnClickListener(v -> dateText.setOnClickListener(v1 ->
        {
            //noinspection CodeBlock2Expr
            datePickerDialog = new DatePickerDialog(this,
                    (view, year1, monthOfYear, dayOfMonth) ->
                    {
                        dateText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1);
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        }));

        ///////// Time Input //////////
        timeText = findViewById(R.id.timeInput);
        timeText.setText(reversedDateTime[1]);
        timeText.setOnClickListener(v -> timeText.setOnClickListener(v1 ->
        {
            //noinspection CodeBlock2Expr
            timePickerDialog = new TimePickerDialog(this,
                    (timePicker, hourOfDay, minute) ->
                    {
                        timeText.setText(hourOfDay + ":" + minute);
                    }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false);
            timePickerDialog.show();
        }));

        ///////// Card Selector //////////TODO: DEBIT
        accessCreditCard = new AccessCard();
        ArrayList<String> cardDisplayList = new ArrayList<>();
        ArrayList<Card> cardArrayList = accessCreditCard.getActiveCards();
        cardDisplayList.add("Select card");
        for (Card c : cardArrayList) {
            cardDisplayList.add(c.getCardName() + "\n" + c.getCardNum());
        }
        Spinner cardSelector = findViewById(R.id.cardSelector);
        cardSelector.setOnItemSelectedListener(this);
        cardSelector.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, cardDisplayList));
        cardSelector.setSelection(cardArrayList.indexOf(oldTransaction.getCard()) + 1);

        ///////// Budget Selector //////////
        accessBudget = new AccessBudgetCategory();
        List<String> budgetList = new ArrayList<>();
        ArrayList<BudgetCategory> budgetArrayList = accessBudget.getAllBudgetCategories();
        budgetList.add("Select budget category");
        for (BudgetCategory b : budgetArrayList) {
            budgetList.add(b.getBudgetName());
        }
        Spinner BudgetSelector = findViewById(R.id.budgetSelector);
        BudgetSelector.setOnItemSelectedListener(this);
        BudgetSelector.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, budgetList));
        BudgetSelector.setSelection(budgetArrayList.indexOf(oldTransaction.getBudgetCategory()) + 1);

        ///////// Update Transaction Button //////////
        ((Button) findViewById(R.id.addTransSubmit)).setText(R.string.update);
        findViewById(R.id.addTransSubmit).setOnClickListener(view ->
        {
            //checking if the newly created transaction is valid or not
            boolean valid = true;

            // validate fields, use methods from business class
            if (!AccessValidation.isValidDateTime(dateText.getText().toString(), timeText.getText().toString())) {
                timeText.setError("Invalid time.");
                dateText.setError("Invalid date.");
                valid = false;
            }
            if (!AccessValidation.isValidAmount(((EditText) findViewById(R.id.addTransAmount)).getText().toString())) {
                ((EditText) findViewById(R.id.addTransAmount)).setError("Invalid amount.");
                valid = false;
            }
            if (!AccessValidation.isValidDescription(((EditText) findViewById(R.id.addTransDescription)).getText().toString())) {
                ((EditText) findViewById(R.id.addTransDescription)).setError("Invalid description.");
                valid = false;
            }
            if (BudgetSelector.getSelectedItemPosition() - 1 == -1) {
                ((TextView) BudgetSelector.getSelectedView()).setError("Please select a budget category.");
                valid = false;
            }
            if (cardSelector.getSelectedItemPosition() - 1 == -1) {
                ((TextView) cardSelector.getSelectedView()).setError("Please select a card.");
                valid = false;
            }
            //if everything is valid then checks if the transaction can be inserted or not
            if (valid &&
                    accessTransaction.updateTransaction
                    (
                            oldTransaction,
                            ((EditText) findViewById(R.id.addTransDescription)).getText().toString(),
                            dateText.getText().toString(),
                            timeText.getText().toString(),
                            ((EditText) findViewById(R.id.addTransAmount)).getText().toString(),
                            (Card) cardArrayList.get(cardSelector.getSelectedItemPosition() - 1),
                            budgetArrayList.get(BudgetSelector.getSelectedItemPosition() - 1)
                    ))
            {
                finish();
                Toast.makeText(view.getContext(), "Updated!", Toast.LENGTH_SHORT).show();
            } else {
                Snackbar.make(view, "Failed to update Transaction.", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}