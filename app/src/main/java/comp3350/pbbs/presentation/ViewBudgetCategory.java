package comp3350.pbbs.presentation;

import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;
import java.text.DecimalFormat;
import comp3350.pbbs.R;
import comp3350.pbbs.business.AccessBudgetCategory;
import comp3350.pbbs.objects.BudgetCategory;

public class ViewBudgetCategory extends Activity {
    private TextView budgetName;
    private TextView budgetInfo;
    private BudgetCategory budgetCategory;
    private AccessBudgetCategory accessBudgetCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_budget);
        budgetCategory = (BudgetCategory) getIntent().getSerializableExtra("budgetCategory");

        budgetName = (TextView) findViewById(R.id.budget_category_name);
        budgetName.setText(budgetCategory.getBudgetName());
        DecimalFormat rounding = new DecimalFormat("0.00");

        accessBudgetCategory = new AccessBudgetCategory();

        budgetInfo = (TextView) findViewById(R.id.budget_info);
        String text = "Spent: $"
                + rounding.format(accessBudgetCategory.calculateBudgetCategoryTotal(budgetCategory))
                + " out of $" + rounding.format(budgetCategory.getBudgetLimit());
        budgetInfo.setText(text);
    }
}
