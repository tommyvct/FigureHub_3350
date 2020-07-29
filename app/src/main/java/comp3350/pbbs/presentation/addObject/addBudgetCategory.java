package comp3350.pbbs.presentation.addObject;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import comp3350.pbbs.R;
import comp3350.pbbs.business.AccessBudgetCategory;

/**
 * addBudgetCategory
 * Group4
 * PBBS
 *
 * This class adds a new budgetCategory with the existing list.
 */
public class addBudgetCategory extends AppCompatActivity {

    private AccessBudgetCategory accessBudgetCategory;      //an accessBudgetCategory variable

    EditText BudgetNameET;                                  //EditText variable for budgetName
    EditText BudgetLimitET;                                 //EditText variable for budgetLimit

    /**
     * This method creates a new budgetCategory and adds it with the budgetCategory list
     *
     * @param savedInstanceState a bundle variable to save the state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_budget_category);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Add Budget Category");
        accessBudgetCategory = new AccessBudgetCategory();

        //Initializing the EditText variables
        BudgetNameET = findViewById(R.id.editBudgetName);
        BudgetLimitET = findViewById(R.id.editBudgetLimit);

        findViewById(R.id.addBudgetSubmit).setOnClickListener(view ->
        {
            boolean valid = true;

            if (BudgetNameET.getText().toString().trim().isEmpty())
            {
                BudgetNameET.setError("Name required.");
                valid = false;
            }

            if (BudgetLimitET.getText().toString().isEmpty())
            {
                BudgetLimitET.setError("Limit required.");
                valid = false;
            }

            if (!valid)
            {
                return;
            }

            //checking if the newly created budget is valid or not
            if (accessBudgetCategory.insertBudgetCategory(BudgetNameET.getText().toString().trim(), BudgetLimitET.getText().toString()))   // validate fields, use methods from business class
            {
                setResult(1);
                finish();
            }
            else
            {
                Snackbar.make(view, "Failed to add Budget Category.", Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
