package comp3350.pbbs.presentation;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Objects;

import comp3350.pbbs.R;
import comp3350.pbbs.business.AccessBudgetCategory;
import comp3350.pbbs.objects.BudgetCategory;

public class ViewBudgetCategory extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_budget);

        TextView budgetName = (TextView) findViewById(R.id.budget_category_name);
        TextView budgetInfo = (TextView) findViewById(R.id.budget_info);
        DecimalFormat rounding = new DecimalFormat("0.00");

        BudgetCategory budgetCategory =
                Objects.requireNonNull((BudgetCategory) getIntent().getSerializableExtra("budgetCategory"));
        AccessBudgetCategory accessBudgetCategory = new AccessBudgetCategory();

        budgetName.setText((budgetCategory).getBudgetName());

        String text = "Spent: $"
                + rounding.format(accessBudgetCategory.calculateBudgetCategoryTotal(budgetCategory))
                + " out of $" + rounding.format(budgetCategory.getBudgetLimit());
        budgetInfo.setText(text);
    }
}
