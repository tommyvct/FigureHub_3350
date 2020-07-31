package comp3350.pbbs.presentation.addObject;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;
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
import comp3350.pbbs.objects.Card;

/**
 * addTransaction
 * Group4
 * PBBS
 *
 * This class adds a new transaction with the existing list.
 */
public class addTransaction extends AppCompatActivity implements OnItemSelectedListener {
    DatePickerDialog datePickerDialog;              //variable for DatePickerDialog
    EditText dateText;                              //EditText variable for date
    TimePickerDialog timePickerDialog;              //variable for TimePickerDialog
    EditText timeText;                              //EditText variable for time
    final Calendar c = Calendar.getInstance();      //Calendar variable to get the relevant date
    AccessTransaction accessTransaction;            //AccessTransaction variable
    AccessCard accessCard;              //AccessCreditCard variable
    AccessBudgetCategory accessBudget;              //AccessBudgetCategory variable

    /**
     * This method creates a new transaction and adds it with the transaction list
     *
     * @param savedInstanceState a bundle variable to save the state
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Add Transaction");

        ///////// Date Input //////////
        accessTransaction = new AccessTransaction();
        dateText = findViewById(R.id.dateInput);
        dateText.setOnClickListener(v -> dateText.setOnClickListener(v1 ->
        {
            //noinspection CodeBlock2Expr
            datePickerDialog = new DatePickerDialog(addTransaction.this,
                    (view, year1, monthOfYear, dayOfMonth) ->
                    {
                        dateText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1);
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        }));

        ///////// Time Input //////////
        timeText = findViewById(R.id.timeInput);
        timeText.setOnClickListener(v -> timeText.setOnClickListener(v1 ->
        {
            //noinspection CodeBlock2Expr
            timePickerDialog = new TimePickerDialog(addTransaction.this,
                    (timePicker, hourOfDay, minute) ->
                    {
                        timeText.setText(hourOfDay + ":" + minute);
                    }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false);
            timePickerDialog.show();
        }));

        ///////// Card Selector ////////////
        accessCard = new AccessCard();
        List<String> cardList = new ArrayList<>();
        List<Card> cardArrayList = accessCard.getActiveCards();
        cardList.add("Select card");
        for (Card c : cardArrayList) {
            cardList.add(c.getCardName() + "\n" + c.getCardNum());
        }
        Spinner cardSelector = findViewById(R.id.cardSelector);
        cardSelector.setOnItemSelectedListener(this);
        cardSelector.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, cardList));

        ///////// Budget Selector //////////
        accessBudget = new AccessBudgetCategory();
        List<String> budgetList = new ArrayList<>();
        List<BudgetCategory> budgetArrayList = accessBudget.getAllBudgetCategories();
        budgetList.add("Select budget category");
        for (BudgetCategory b : budgetArrayList) {
            budgetList.add(b.getBudgetName());
        }
        Spinner BudgetSelector = findViewById(R.id.budgetSelector);
        BudgetSelector.setOnItemSelectedListener(this);
        BudgetSelector.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, budgetList));


        ///////// Add Transaction Button //////////
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
            if (!AccessValidation.isValidDescription(((EditText) findViewById(R.id.addTransDescription)).getText().toString().trim())) {
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
            if (valid && accessTransaction.addTransaction
                    (
                            ((EditText) findViewById(R.id.addTransDescription)).getText().toString().trim(),
                            dateText.getText().toString(),
                            timeText.getText().toString(),
                            ((EditText) findViewById(R.id.addTransAmount)).getText().toString(),
                            (Card) cardArrayList.get(cardSelector.getSelectedItemPosition() - 1),
                            budgetArrayList.get(BudgetSelector.getSelectedItemPosition() - 1)
                    )) {
                        setResult(1);
                        finish();
            } else {
                Snackbar.make(view, "Failed to add Transaction.", Snackbar.LENGTH_LONG).show();
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