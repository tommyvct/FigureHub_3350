package comp3350.pbbs.presentation.updateObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.Objects;
import comp3350.pbbs.R;
import comp3350.pbbs.business.AccessCard;
import comp3350.pbbs.business.AccessValidation;
import comp3350.pbbs.objects.Cards.Card;
import comp3350.pbbs.presentation.addObject.addCard;


public class updateCard extends AppCompatActivity implements OnItemSelectedListener {

	AccessCard accessCreditCard;	// AccessCreditCard variable
	EditText cardName;					// EditText variable for cardName
	EditText cardNumber;				// EditText variable for cardNumber
	EditText validThruMonth;			// EditText variable for valid month
	EditText validThruYear;				// EditText variable for valid year
	EditText payday;					// EditText variable for payday
	EditText cardholderName;			// EditText variable for holder name
	Card oldCard;						//old card to update
	boolean isDebit;

	@SuppressLint("SetTextI18n")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_card);
		Objects.requireNonNull(getSupportActionBar()).setTitle("Update Card");
		oldCard = Objects.requireNonNull((Card)getIntent().getSerializableExtra("toUpdate"));

		accessCreditCard = new AccessCard();
		cardName = findViewById(R.id.updateCardName);
		cardNumber = findViewById(R.id.updateCardNumber);
		cardNumber.setEnabled(false);
		validThruMonth = findViewById(R.id.updateValidThruMonth);
		validThruYear = findViewById(R.id.updateValidThruYear);
		payday = findViewById(R.id.updatePayDay);
		cardholderName = findViewById(R.id.updateCardHolderName);
		//validThruYear.setText("20");


		cardName.setText(oldCard.getCardName());
		cardNumber.setText(oldCard.getCardNum());
		validThruMonth.setText(String.valueOf(oldCard.getExpireMonth()));
		validThruYear.setText(String.valueOf(oldCard.getExpireYear()));
		if(oldCard.getPayDate() != 0) {
			payday.setText(String.valueOf(oldCard.getPayDate()));
			isDebit = false;
		}else{
			payday.setVisibility(View.GONE);
			isDebit = true;
		}
		cardholderName.setText(oldCard.getHolderName());

		final Button button = findViewById(R.id.updateCardSubmit);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (view.equals(button)) {
					Toast.makeText(updateCard.this, "update this card", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(updateCard.this, addCard.class);
					startActivity(intent);
				}

			}
		});

		findViewById(R.id.updateCardSubmit).setOnClickListener(view ->
		{
			//checking if the newly created creditCard is valid or not
			boolean valid = true;
			boolean validDebit = true;

			if (cardNumber.getText().toString().isEmpty()) {
				cardNumber.setError("Provide a card number.");
				valid = false;
				validDebit = false;
			}

				switch (AccessValidation.isValidExpirationDate(validThruMonth.getText().toString(), validThruYear.getText().toString())) {
					case 1:  // invalid month
						validThruMonth.setError("There is no such month!");
						valid = false;
						validDebit = false;
						break;

					case 2:  // invalid year, like year 3077
						validThruYear.setError("Year should be less than 2099.");
						valid = false;
						validDebit = false;
						break;

					case 3: // both 1 and 2
						validThruMonth.setError("There is no such month!");
						validThruYear.setError("Year should be less than 2099.");
						valid = false;
						validDebit = false;
						break;

					case 4: // year less than 4 digit
						validThruYear.setError("Provide year in 4 digits, e.g. 2020.");
						valid = false;
						validDebit = false;
						break;

					case 5: // expired month
						validThruMonth.setError("Card already expired.");
						valid = false;
						validDebit = false;
						break;

					case 6: // expired Year
						validThruYear.setError("Card already expired.");
						valid = false;
						validDebit = false;
						break;

					case 7:
							validThruMonth.setError("Expire month is required.");
							validThruYear.setError("Expire year is required.");
							valid = false;
							validDebit = false;

						break;
				}


			if (payday.getText().toString().isEmpty()) {
				payday.setError("Which day of month do you need to pay this card?");
				valid = false;
			} else if (!AccessValidation.isValidPayDate(Integer.parseInt(payday.getText().toString())))   // validate fields, use methods from business class
			{
				payday.setError("There is no such day in a month!");
				valid = false;
			}

			if (cardholderName.getText().toString().isEmpty()) {
				cardholderName.setError("Provide a cardholder name.");
				valid = false;
				validDebit = false;
			} else if (!AccessValidation.isValidName(cardholderName.getText().toString()))   // validate fields, use methods from business class
			{
				cardholderName.setError("Cardholder name can only contain letters, period and dash.");
				valid = false;
				validDebit = false;
			}

			//if everything is valid then checks if the card can be inserted or not
			if (valid && accessCreditCard.updateCard(oldCard,
					new Card(
							cardName.getText().toString().isEmpty() ? "No Name" : cardName.getText().toString(),
							cardNumber.getText().toString(),
							cardholderName.getText().toString(),
							Integer.parseInt(validThruMonth.getText().toString()),
							Integer.parseInt(validThruYear.getText().toString()),
							Integer.parseInt(payday.getText().toString())))
					)
			{
				finish();
				Toast.makeText(view.getContext(), "Credit Card updated!", Toast.LENGTH_SHORT).show();
			}
			else if((validDebit) && accessCreditCard.updateCard(oldCard,
					new Card(
							cardName.getText().toString().isEmpty() ? "No Name" : cardName.getText().toString(),
							cardNumber.getText().toString(),
							cardholderName.getText().toString(),
							Integer.parseInt(validThruMonth.getText().toString()),
							Integer.parseInt(validThruYear.getText().toString())))){
				finish();
				Toast.makeText(view.getContext(), "Debit Card updated!", Toast.LENGTH_SHORT).show();
			} else {
				Snackbar.make(view,"Failed to update Card",Snackbar.LENGTH_SHORT).show();
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