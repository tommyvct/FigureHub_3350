package comp3350.pbbs.presentation.updateObject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import comp3350.pbbs.R;
import comp3350.pbbs.business.AccessCard;
import comp3350.pbbs.business.AccessValidation;
import comp3350.pbbs.objects.Card;

/**
 * updateCard
 * Group4
 * PBBS
 *
 * This class updates an existed card within the list.
 */
public class updateCard extends AppCompatActivity implements OnItemSelectedListener {

    AccessCard accessCard;    // AccessCreditCard variable
    EditText cardName;                    // EditText variable for cardName
    EditText cardNumber;                // EditText variable for cardNumber
    EditText validThruMonth;            // EditText variable for valid month
    EditText validThruYear;                // EditText variable for valid year
    EditText payday;                    // EditText variable for payday
    EditText cardholderName;            // EditText variable for holder name
    Card oldCard;                        //old card to update
    RadioGroup radioGroup;
    boolean debit;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Update Card");
        // init object
        oldCard = Objects.requireNonNull((Card) getIntent().getSerializableExtra("toUpdate"));
        accessCard = new AccessCard();

        // init GUI elements
        cardName = findViewById(R.id.cardName);
        cardNumber = findViewById(R.id.cardNumber);
        cardNumber.setEnabled(false);
        validThruMonth = findViewById(R.id.ValidThruMonth);
        validThruYear = findViewById(R.id.validThruYear);
        payday = findViewById(R.id.payDay);
        cardholderName = findViewById(R.id.cardholderName);
        findViewById(R.id.addCardSubmit).setVisibility(View.GONE);
        findViewById(R.id.updateMarkInactive).setVisibility(View.VISIBLE);
        findViewById(R.id.defaultBankAccountInfo).setVisibility(View.GONE);
        radioGroup = findViewById(R.id.addCreditOrDebit);

        // fill with old card info
        cardName.setText(oldCard.getCardName());
        cardNumber.setText(oldCard.getCardNum());
        // in case of lacking expiry date,
        validThruMonth.setText(String.valueOf(oldCard.getExpireMonth()).equals("0") ? "" : String.valueOf(oldCard.getExpireMonth()));
        validThruYear.setText(String.valueOf(oldCard.getExpireYear()).equals("0") ? "" : String.valueOf(oldCard.getExpireYear()));

        // if credit card
        if (oldCard.getPayDate() != 0) {
            payday.setText(String.valueOf(oldCard.getPayDate()));
            debit = false;
            radioGroup.check(R.id.addCreditRadioButton);
        } else {
            payday.setVisibility(View.GONE);
            debit = true;
            radioGroup.check(R.id.addDebitRadioButton);
            ((TextView) findViewById(R.id.expiryDateTextHint)).setText(R.string.expire_date_optional);
        }
        cardholderName.setText(oldCard.getHolderName());
        findViewById(R.id.addCreditRadioButton).setEnabled(false);
        findViewById(R.id.addDebitRadioButton).setEnabled(false);

        findViewById(R.id.updateCardSubmit).setOnClickListener(view ->
        {
            //checking if the newly created creditCard is valid or not
            boolean valid = true;

            if (cardNumber.getText().toString().trim().isEmpty()) {
                cardNumber.setError("Provide a card number.");
                valid = false;
            }

            // if credit card or debit card with expiry date
            if (!debit || !(validThruMonth.getText().toString().isEmpty() && validThruYear.getText().toString().isEmpty())) {
                switch (AccessValidation.isValidExpirationDate(validThruMonth.getText().toString(), validThruYear.getText().toString())) {
                    case 1:  // invalid month
                        validThruMonth.setError("There is no such month!");
                        valid = false;
                        break;

                    case 2:  // invalid year, like year 3077
                        validThruYear.setError("Year should be less than 2099.");
                        valid = false;
                        break;

                    case 3: // both 1 and 2
                        validThruMonth.setError("There is no such month!");
                        validThruYear.setError("Year should be less than 2099.");
                        valid = false;
                        break;

                    case 4: // year less than 4 digit
                        validThruYear.setError("Provide year in 4 digits, e.g. 2020.");
                        valid = false;
                        break;

                    case 5: // expired month
                        validThruMonth.setError("Card already expired.");
                        valid = false;
                        break;

                    case 6: // expired Year
                        validThruYear.setError("Card already expired.");
                        valid = false;
                        break;

                    case 7:
                        validThruMonth.setError("Expire month is required.");
                        validThruYear.setError("Expire year is required.");
                        valid = false;
                        break;
                }
            }

            if (!debit && payday.getText().toString().isEmpty()) {
                payday.setError("Which day of month do you need to pay this card?");
                valid = false;
            } else if (!debit && !AccessValidation.isValidPayDate(Integer.parseInt(payday.getText().toString())))   // validate fields, use methods from business class
            {
                payday.setError("There is no such day in a month!");
                valid = false;
            }

            if (cardholderName.getText().toString().trim().isEmpty()) {
                cardholderName.setError("Provide a cardholder name.");
                valid = false;
            } else if (!AccessValidation.isValidName(cardholderName.getText().toString().trim()))   // validate fields, use methods from business class
            {
                cardholderName.setError("Cardholder name can only contain letters, period and dash.");
                valid = false;
            }

			//if everything is valid then checks if the card can be inserted or not
			if (valid && accessCard.updateCard(oldCard, debit ?
					new Card(
							cardName.getText().toString().trim().isEmpty() ? "No Name" : cardName.getText().toString().trim(),
							cardNumber.getText().toString(),
							cardholderName.getText().toString().trim(),
							Integer.parseInt(validThruMonth.getText().toString().isEmpty() ? "0" : validThruMonth.getText().toString()),
							Integer.parseInt(validThruYear.getText().toString().isEmpty() ? "0" : validThruYear.getText().toString()))
					:
					new Card(
							cardName.getText().toString().trim().isEmpty() ? "No Name" : cardName.getText().toString().trim(),
							cardNumber.getText().toString(),
							cardholderName.getText().toString().trim(),
							Integer.parseInt(validThruMonth.getText().toString().isEmpty() ? "0" : validThruMonth.getText().toString()),
							Integer.parseInt(validThruYear.getText().toString().isEmpty() ? "0" : validThruYear.getText().toString()),
							Integer.parseInt(debit ? "0" : payday.getText().toString())))
					)
			{
				setResult(2);
				finish();
				Toast.makeText(view.getContext(), "Card updated!", Toast.LENGTH_SHORT).show();
			}
			else {
				Snackbar.make(view,"Failed to update Card",Snackbar.LENGTH_SHORT).show();
			}
		});


		if (oldCard.isActive())
		{
			// mark as inactive
			findViewById(R.id.markCardInactive).setOnClickListener(view ->
			{
				accessCard.markInactive(oldCard);
				setResult(2);
				finish();
				Toast.makeText(view.getContext(), "Card marked as inactive!", Toast.LENGTH_SHORT).show();
			});
		}
		else
		{
			((Button) findViewById(R.id.markCardInactive)).setText("Mark Active");
			// mark as active
			findViewById(R.id.markCardInactive).setOnClickListener(view ->
			{
				accessCard.markActive(oldCard);
				setResult(2);
				finish();
				Toast.makeText(view.getContext(), "Card marked as active!", Toast.LENGTH_SHORT).show();
			});
		}
	}

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}