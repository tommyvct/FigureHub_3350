package comp3350.pbbs.presentation.addObject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import comp3350.pbbs.R;
import comp3350.pbbs.business.AccessBankAccount;
import comp3350.pbbs.business.AccessCard;
import comp3350.pbbs.business.Validation;
import comp3350.pbbs.objects.BankAccount;
import comp3350.pbbs.objects.Card;

/**
 * AddCard
 * Group4
 * PBBS
 * <p>
 * This class adds a new creditCard with the existing list.
 */
public class AddCard extends AppCompatActivity {
	EditText cardName;          // EditText variable for cardName
	EditText cardNumber;        // EditText variable for cardNumber
	EditText validThruMonth;    // EditText variable for valid month
	EditText validThruYear;     // EditText variable for valid year
	EditText payday;            // EditText variable for payday
	EditText cardholderName;    // EditText variable for holder name
	EditText bankAccountName;   // EditText variable for name of a bank account
	EditText bankAccountNumber; // EditText variable for number of a bank account
	AccessCard accessCard;
	AccessBankAccount accessBankAccount;
	RadioGroup radioGroup;
	boolean debit;

	/**
	 * This method creates a new creditCard and adds it with the creditCard list
	 *
	 * @param savedInstanceState a bundle variable to save the state
	 */
	@SuppressLint("SetTextI18n")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_card);
		Objects.requireNonNull(getSupportActionBar()).setTitle("Add Card");

		// Initializing the EditText variables
		cardName = findViewById(R.id.cardName);
		cardNumber = findViewById(R.id.cardNumber);
		validThruMonth = findViewById(R.id.ValidThruMonth);
		validThruYear = findViewById(R.id.validThruYear);
		payday = findViewById(R.id.payDay);
		cardholderName = findViewById(R.id.cardholderName);
		bankAccountName = findViewById(R.id.addDebitDefaultBankAccountName);
		bankAccountNumber = findViewById(R.id.addDebitDefaultBankAccountNumber);

		accessCard = new AccessCard();
		accessBankAccount = new AccessBankAccount();
		validThruYear.setText("20");       //For year, the first 2 digits will always be 20

		radioGroup = findViewById(R.id.addCreditOrDebit);

		radioGroup.setOnCheckedChangeListener((radioGroup, i) ->
		{
			switch (i) {
				case R.id.addCreditRadioButton:
					debit = false;
					findViewById(R.id.paydayLayout).setVisibility(View.VISIBLE);
					findViewById(R.id.addDebitDefaultBankAccount).setVisibility(View.GONE);
					((TextView) findViewById(R.id.expiryDateTextHint)).setText(R.string.expire_date);
					break;
				case R.id.addDebitRadioButton:
					debit = true;
					findViewById(R.id.paydayLayout).setVisibility(View.GONE);
					findViewById(R.id.addDebitDefaultBankAccount).setVisibility(View.VISIBLE);
					((TextView) findViewById(R.id.expiryDateTextHint)).setText(R.string.expire_date_optional);
					break;
			}
		});


		findViewById(R.id.addCardSubmit).setOnClickListener(view ->
		{
			//checking if the newly created creditCard is valid or not
			boolean valid = true;

			if (cardNumber.getText().toString().isEmpty()) {
				cardNumber.setError("Provide a card number.");
				valid = false;
			}

			if (!debit || !(validThruMonth.getText().toString().isEmpty() && validThruYear.getText().toString().isEmpty())) {
				switch (Validation.isValidExpirationDate(validThruMonth.getText().toString(), validThruYear.getText().toString())) {
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
						if (!debit) {
							validThruMonth.setError("Expire month is required.");
							validThruYear.setError("Expire year is required.");
							valid = false;
						}

						break;
				}
			}

			if (!debit && payday.getText().toString().isEmpty()) {
				payday.setError("Which day of month do you need to pay this card?");
				valid = false;
			} else if (!debit && !Validation.isValidPayDate(Integer.parseInt(payday.getText().toString())))   // validate fields, use methods from business class
			{
				payday.setError("There is no such day in a month!");
				valid = false;
			}

			if (cardholderName.getText().toString().trim().isEmpty()) {
				cardholderName.setError("Provide a cardholder name.");
				valid = false;
			} else if (!Validation.isValidName(cardholderName.getText().toString().trim()))   // validate fields, use methods from business class
			{
				cardholderName.setError("Cardholder name can only contain letters, period and dash.");
				valid = false;
			}

			if (debit && bankAccountNumber.getText().toString().isEmpty()) {
				bankAccountNumber.setError("Provide an account number.");
				valid = false;
			}

			if (valid) {
				if (debit) {
					Card card2Insert;
					// don't have a valid expiry date
					if (validThruYear.getText().toString().isEmpty() || validThruYear.getText().toString().equals("20") || validThruMonth.getText().toString().isEmpty()) {
						card2Insert = new Card(cardName.getText().toString().trim().isEmpty() ? "No Name" : cardName.getText().toString().trim(),
								cardNumber.getText().toString(),
								cardholderName.getText().toString().trim(), 0, 0);
					} else { // have one
						card2Insert = new Card(cardName.getText().toString().trim().isEmpty() ? "No Name" : cardName.getText().toString().trim(),
								cardNumber.getText().toString(),
								cardholderName.getText().toString().trim(),
								Integer.parseInt(validThruMonth.getText().toString()),
								Integer.parseInt(validThruYear.getText().toString()));
					}

					// if everything is valid then checks if the card can be inserted or not
					if (accessCard.insertCard(card2Insert) && accessBankAccount.insertBankAccount(new BankAccount(
							bankAccountName.getText().toString().trim().isEmpty() ? "No Name" : bankAccountName.getText().toString().trim(),
							bankAccountNumber.getText().toString(), card2Insert))
					) {
						setResult(1);
						finish();
					}
				} else  { // credit card
					if (accessCard.insertCard(new Card(
					        cardName.getText().toString().trim().isEmpty() ? "No Name" : cardName.getText().toString().trim(),
                            cardNumber.getText().toString(),
                            cardholderName.getText().toString().trim(),
                            Integer.parseInt(validThruMonth.getText().toString()),
                            Integer.parseInt(validThruYear.getText().toString()),
                            Integer.parseInt(payday.getText().toString())))) {
						setResult(1);
						finish();
					}
				}
			}
		});
	}
}