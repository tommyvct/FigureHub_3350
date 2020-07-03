package comp3350.pbbs.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Objects;

import comp3350.pbbs.R;

public class addCard extends AppCompatActivity
{
    DatePickerDialog datePickerDialog;
    EditText cardNumberET;
//    EditText validThruET;
    EditText paydayET;
    EditText cardholderNameET;
    final Calendar c = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Add Card");

        cardNumberET = findViewById(R.id.cardNumber);
//        validThruET = findViewById(R.id.validThru);
        paydayET = findViewById(R.id.payDay);
        cardholderNameET = findViewById(R.id.cardholderName);




    }
}