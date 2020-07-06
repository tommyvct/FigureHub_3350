package comp3350.pbbs.presentation.addObject;

import androidx.appcompat.app.AppCompatActivity;

import comp3350.pbbs.R;
import comp3350.pbbs.business.AccessTransaction;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.EditText;


import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.Objects;

public class addTransaction extends AppCompatActivity
{
    DatePickerDialog datePickerDialog;
    EditText dateText;
    TimePickerDialog timePickerDialog;
    EditText timeText;
    final Calendar c = Calendar.getInstance();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Add Transaction");

        AccessTransaction accessTransaction = new AccessTransaction();
        dateText = findViewById(R.id.dateInput);
        dateText.setOnClickListener(v -> dateText.setOnClickListener(v1 ->
        {
             datePickerDialog = new DatePickerDialog(addTransaction.this,
                                                     (view, year1, monthOfYear, dayOfMonth) ->
                                                     {
                                                         dateText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1);
                                                     }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
             datePickerDialog.show();
        }));

        timeText = findViewById(R.id.timeInput);
        timeText.setOnClickListener(v -> timeText.setOnClickListener(v1 ->
        {
             timePickerDialog = new TimePickerDialog(addTransaction.this,
                                                     (timePicker, hourOfDay, minute) ->
                                                     {
                                                         timeText.setText(hourOfDay + " : " + minute);
                                                     }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false);
             timePickerDialog.show();
        }));

        findViewById(R.id.addTranssubmit).setOnClickListener(view ->
        {
//            if (!accessTransaction.isValidDateTime(dateText.getText().toString(), timeText.getText().toString()))   // validate fields, use methods from business class
//            {
//                timeText.setError("Invalid time.");
//                dateText.setError("Invalid date.");
//                return;
//            }
//            if (!accessTransaction.isValidAmount(((EditText) findViewById(R.id.addTransAmount)).getText().toString()))
//            {
//                // TODO: it should be able to tell why it didn't went through
//                ((EditText) findViewById(R.id.addTransAmount)).setError("Invalid amount.");
//                return;
//            }
//            if (!accessTransaction.isValidDescription(((EditText) findViewById(R.id.addTransDescription)).getText().toString()))
//            {
//                ((EditText) findViewById(R.id.addTransDescription)).setError("Invalid description.");
//                return;
//            }
//             // transaction.addTransaction(string, string, string);
//
//            if  (accessTransaction.addTransaction
//                (
//                    ((EditText) findViewById(R.id.addTransDescription)).getText().toString(),
//                    dateText.getText().toString(),
//                    timeText.getText().toString(),
//                    ((EditText) findViewById(R.id.addTransAmount)).getText().toString(),
//                    null,   // TODO: card selector
//                    null   // TODO: budgetCategory selector
//                ))
//            {
//                Snackbar.make(view, "Transaction Added!", Snackbar.LENGTH_SHORT)
//                        .addCallback(new Snackbar.Callback()
//                        {
//                            @Override
//                            public void onDismissed(Snackbar transientBottomBar, int event)
//                            {
//                                super.onDismissed(transientBottomBar, event);
//                                finish();
//                            }
//                        }).show();
//            }
//            else
//            {
                Snackbar.make(view, "Failed to add Transaction.", Snackbar.LENGTH_LONG).show();
//            }
        });
    }

    // TODO: field validation
}