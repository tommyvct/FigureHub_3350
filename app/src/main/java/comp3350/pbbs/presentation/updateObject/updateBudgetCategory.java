package comp3350.pbbs.presentation.updateObject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.Objects;
import comp3350.pbbs.R;
import comp3350.pbbs.business.AccessBudgetCategory;
import comp3350.pbbs.presentation.MainActivity;
import comp3350.pbbs.presentation.mainActivityFragments.main_budget;

/**
 * updateBudgetCategory
 * Group4
 * PBBS
 *
 * This class updates(including deletes) an existed budgetCategory within the list.
 */

public class updateBudgetCategory extends AppCompatActivity implements Serializable {

	private AccessBudgetCategory accessBudgetCategory;	// AccessBudgetCategory variable
	EditText BudgetNameET;								// EditText variable for budgetName
	EditText BudgetLimitET;								// EditText variable for budgetLimit

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_budget_category);
		Objects.requireNonNull(getSupportActionBar()).setTitle("Update Budget Category");

		accessBudgetCategory = new AccessBudgetCategory();
		BudgetNameET = findViewById(R.id.updateBudgetName);
		BudgetLimitET = findViewById(R.id.updateBudgetLimit);

		// validation for the new entered information
		findViewById(R.id.updateBudgetSubmit).setOnClickListener(view -> {
			boolean valid = true;
			if (BudgetNameET.getText().toString().isEmpty()) {
				BudgetNameET.setError("Name required.");
				valid = false;
			}
			if (BudgetLimitET.getText().toString().isEmpty()) {
				BudgetLimitET.setError("Limit required.");
				valid = false;
			}
			if (!valid) {
				return;
			}
			if (accessBudgetCategory.insertBudgetCategory(BudgetNameET.getText().toString(), BudgetLimitET.getText().toString())) {
				setResult(1);
				finish();
			} else {
				Snackbar.make(view, "Failed to add Budget Category.", Snackbar.LENGTH_SHORT).show();
			}
		});

		Button update = (Button)findViewById(R.id.updateBudgetSubmit);
		update.setOnClickListener(view -> {
			switch (view.getId()) {
				case R.id.updateBudgetSubmit:
					Toast.makeText(getApplicationContext(), "update", Toast.LENGTH_SHORT).show();
					break;
				case R.id.deleteBudgetSubmit:
					Toast.makeText(getApplicationContext(), "delete", Toast.LENGTH_SHORT).show();
					break;
			}
		});
	}
}