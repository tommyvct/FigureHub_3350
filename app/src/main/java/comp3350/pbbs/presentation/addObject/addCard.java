package comp3350.pbbs.presentation.addObject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import comp3350.pbbs.R;

public class addCard extends AppCompatActivity
{
    EditText cardNumberET;
    EditText paydayET;
    EditText cardholderNameET;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Add Card");

        cardNumberET = findViewById(R.id.cardNumber);
        paydayET = findViewById(R.id.payDay);
        cardholderNameET = findViewById(R.id.cardholderName);

        findViewById(R.id.addCardSubmit).setOnClickListener(view ->
        {
            if (false)   // validate fields, use methods from business class
            {
                 // thatEditText.setError()
            }
            else
            {
                 // transaction.addCard(string, string, string);
                 Snackbar.make(view, "Card Added!", Snackbar.LENGTH_SHORT)
                         .addCallback(new Snackbar.Callback()
                         {
                             @Override
                             public void onDismissed(Snackbar transientBottomBar, int event)
                             {
                                 super.onDismissed(transientBottomBar, event);
                                 finish();
                             }
                         }).show();
            }
        });
    }

    // TODO: field validation
}