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
    private ArrayList<BudgetCategory> budgetCategoryList;
    private ArrayAdapter<BudgetCategory> budgetArrayAdapter;
    private int selectedBudgetPosition = -1;
    EditText BudgetNameET;
    EditText BudgetLimitET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_budget_category);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Add Budget Category");

        BudgetNameET = findViewById(R.id.editBudgetName);
        BudgetLimitET = findViewById(R.id.editBudgetLimit);

        findViewById(R.id.addBudgetSubmit).setOnClickListener(view ->
        {
            if (accessBudgetCategory.insertBudgetCategory(BudgetNameET.getText().toString(), BudgetLimitET.getText().toString()))   // validate fields, use methods from business class
            {
                Snackbar.make(view, "Budget Category Added!", Snackbar.LENGTH_SHORT)
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
            else
            {
                // thatEditText.setError()
            }
        });

//        accessBudgetCategory = new AccessBudgetCategory();
//
//        budgetCategoryList = accessBudgetCategory.getAllBudgetCategories();
//        if (budgetCategoryList == null)
//        {
//            //TODO: send fatal error
//            // fail(this, "Failed to get budget categories");
//        }
//        else
//        {
//            budgetArrayAdapter = new ArrayAdapter<BudgetCategory>(this, android.R.layout.simple_list_item_activated_2, android.R.id.text1, budgetCategoryList)
//            {
//                @Override
//                public View getView(int position, View convertView, ViewGroup parent) {
//                    View view = super.getView(position, convertView, parent);
//
//                    TextView text1 = (TextView) view.findViewById(android.R.id.text1);
//                    TextView text2 = (TextView) view.findViewById(android.R.id.text2);
//
//                    text1.setText(budgetCategoryList.get(position).getBudgetName());
//                    text2.setText(String.valueOf(budgetCategoryList.get(position).getBudgetLimit()));
//
//                    return view;
//                }
//            };
//
//            final ListView listView = (ListView)findViewById(R.id.listBudgets);
//            listView.setAdapter(budgetArrayAdapter);
//
//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Button updateButton = (Button)findViewById(R.id.buttonBudgetUpdate);
//                    Button deleteButton = (Button)findViewById(R.id.buttonBudgetDelete);
//
//                    if (position == selectedBudgetPosition) {
//                        listView.setItemChecked(position, false);
//                        updateButton.setEnabled(false);
//                        deleteButton.setEnabled(false);
//                        selectedBudgetPosition = -1;
//                    } else {
//                        listView.setItemChecked(position, true);
//                        updateButton.setEnabled(true);
//                        deleteButton.setEnabled(true);
//                        selectedBudgetPosition = position;
//                        selectBudgetAtPosition(position);
//                    }
//                }
//            });
//
//            final EditText editBudgetName = (EditText)findViewById(R.id.cardDescription);
//            final EditText editBudgetLimit = (EditText) findViewById(R.id.cardNumber);
//            editBudgetName.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {}
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                    //buttonCourseStudents.setEnabled(editBudgetName.getText().toString().length() > 0);
//                }
//            });
//            editBudgetLimit.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {}
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                    //buttonCourseStudents.setEnabled(editBudgetLimit.getText().toString().length() > 0);
//                }
//            });
//        }
    }

    public void selectBudgetAtPosition(int position) {
        BudgetCategory selected = budgetArrayAdapter.getItem(position);

        EditText editID = (EditText)findViewById(R.id.cardDescription);
        EditText editName = (EditText)findViewById(R.id.cardNumber);

        editID.setText(selected.getBudgetName());
        editName.setText(String.valueOf(selected.getBudgetLimit()));
    }

}
