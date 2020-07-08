package comp3350.pbbs.presentation.addObject;
import comp3350.pbbs.R;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Objects;

import comp3350.pbbs.business.AccessBudgetCategory;
import comp3350.pbbs.objects.BudgetCategory;

public class addBudgetCategory extends AppCompatActivity {

    private AccessBudgetCategory accessBudgetCategory;
//    private ArrayList<BudgetCategory> budgetCategoryList;
//    private ArrayAdapter<BudgetCategory> budgetArrayAdapter;
//    private int selectedBudgetPosition = -1;
    EditText BudgetNameET;
    EditText BudgetLimitET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_budget_category);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Add Budget Category");
        accessBudgetCategory = new AccessBudgetCategory();


        BudgetNameET = findViewById(R.id.editBudgetName);
        BudgetLimitET = findViewById(R.id.editBudgetLimit);

        findViewById(R.id.addBudgetSubmit).setOnClickListener(view ->
        {
            if (accessBudgetCategory.insertBudgetCategory(BudgetNameET.getText().toString(), BudgetLimitET.getText().toString()))   // validate fields, use methods from business class
            {
                Snackbar.make(view, "Budget Category Added!", Snackbar.LENGTH_SHORT)
                        .addCallback(new Snackbar.Callback() {
                            @Override
                            public void onDismissed(Snackbar transientBottomBar, int event) {
                                super.onDismissed(transientBottomBar, event);
                                finish();
                            }
                        }).show();
            } else {
                // thatEditText.setError()
            }
        });
    }
}
