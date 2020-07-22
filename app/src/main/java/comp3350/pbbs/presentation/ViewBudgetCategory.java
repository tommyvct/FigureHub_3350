package comp3350.pbbs.presentation;

import android.animation.ArgbEvaluator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import androidx.core.content.ContextCompat;
import comp3350.pbbs.R;
import comp3350.pbbs.business.AccessBudgetCategory;
import comp3350.pbbs.business.AccessTransaction;
import comp3350.pbbs.objects.BudgetCategory;
import comp3350.pbbs.objects.Transaction;

public class ViewBudgetCategory extends Activity {

    private PieChart pieChart;
    private AccessBudgetCategory accessBudgetCategory;
    private BudgetCategory budgetCategory;
    private Spinner monthSelector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_budget);

        pieChart = findViewById(R.id.budget_info);

        budgetCategory =
                Objects.requireNonNull((BudgetCategory) getIntent().getSerializableExtra("budgetCategory"));
        accessBudgetCategory = new AccessBudgetCategory();

        pieChart.setDrawHoleEnabled(true);

        pieChart.setCenterText(budgetCategory.getBudgetName());
        pieChart.setCenterTextSize(20f);

        pieChart.getDescription().setEnabled(false);

        pieChart.getLegend().setEnabled(false);

        pieChart.setScaleX(0.9f);
        pieChart.setScaleY(0.9f);


        monthSelector = findViewById(R.id.budgetMonthSelector);
        AccessTransaction accessTransaction = new AccessTransaction();
        List<Calendar> activeMonths = accessTransaction.getActiveMonths(budgetCategory);
        List<String> monthOptions = new ArrayList<String>();
        DateFormat dateFormat = new SimpleDateFormat("MMMM, yyyy");
        if(activeMonths.isEmpty()){
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());
            activeMonths.add(now);
        }
        for(Calendar activeMonth : activeMonths) {
            monthOptions.add(dateFormat.format(activeMonth.getTime()));
        }
        monthSelector.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, monthOptions));
        monthSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                setPieChartValues(activeMonths.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                System.out.println("This shouldnt be called");
            }
        });

    }

    private void setPieChartValues(Calendar month) {
        int forestGreen = Color.parseColor("#228B22");
        int fireBrick = Color.parseColor("#b22222");
        List<PieEntry> entries = new ArrayList<PieEntry>();
        ArrayList<Integer> colors = new ArrayList<Integer>();
        float amount = accessBudgetCategory.calculateBudgetCategoryTotal(budgetCategory, month);
        float max = (float)budgetCategory.getBudgetLimit();

        float diff = max - amount;
        if (diff > 0) {
            if(amount > 0) {
                entries.add(new PieEntry(
                        amount,
                        "Current Amount"
                ));
                colors.add(ContextCompat.getColor(getBaseContext(), R.color.colorPrimary));
            }

            colors.add((Integer)new ArgbEvaluator().evaluate(amount / max, forestGreen, fireBrick));
            entries.add(new PieEntry(
                    diff,
                    "Left on Budget"
            ));
        }
        else if(diff < 0) {
            entries.add(new PieEntry(
                    Math.abs(diff),
                    "Over Budget"
            ));
            colors.add(fireBrick);

            entries.add(new PieEntry(
                    max,
                    "Budget Limit"
            ));
            colors.add(ContextCompat.getColor(getBaseContext(), R.color.colorPrimary));
        }
        else { //diff == 0
            entries.add(new PieEntry(
                    max,
                    "At Budget Limit"
            ));
            colors.add(ContextCompat.getColor(getBaseContext(), R.color.colorPrimary));
        }

        PieDataSet dataSet = new PieDataSet(entries, budgetCategory.getBudgetName());
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueTextSize(23f);
        data.setValueTextColor(Color.LTGRAY);
        pieChart.setEntryLabelTextSize(13f);
        pieChart.setEntryLabelColor(Color.LTGRAY);
        data.setValueFormatter(new DollarValueFormatter());

        pieChart.setData(data);
        pieChart.animateY(1400, Easing.EaseInOutQuad);
    }
}
