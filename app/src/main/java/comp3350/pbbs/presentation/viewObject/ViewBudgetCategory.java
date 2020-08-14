package comp3350.pbbs.presentation.viewObject;

import android.animation.ArgbEvaluator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import comp3350.pbbs.R;
import comp3350.pbbs.business.AccessBudgetCategory;
import comp3350.pbbs.business.BudgetCategoryTransactionLinker;
import comp3350.pbbs.objects.BudgetCategory;
import comp3350.pbbs.presentation.DollarValueFormatter;
import comp3350.pbbs.presentation.updateObject.UpdateBudgetCategory;

/**
 * ViewBudgetCategory
 * Group4
 * PBBS
 * <p>
 * Class to view a single budget category with cool charts
 */
public class ViewBudgetCategory extends Activity {

	private PieChart pieChart;  // Pie chart component
	private AccessBudgetCategory accessBudgetCategory; // Access layer for the budget categories
	private BudgetCategory budgetCategory;  // The budget category to view
	private BudgetCategoryTransactionLinker linker; //Object that links budget categories and transactions

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_budget);

		// Select the pie chart
		pieChart = findViewById(R.id.budget_info);

		// Grab the budget category
		budgetCategory = Objects.requireNonNull((BudgetCategory) getIntent().getSerializableExtra("budgetCategory"));
		accessBudgetCategory = new AccessBudgetCategory();

		// Insert a hole in the pie chart
		pieChart.setDrawHoleEnabled(true);

		// Put the budget name in the center
		pieChart.setCenterText(budgetCategory.getBudgetName());
		pieChart.setCenterTextSize(20f);

		// Disable description & legend
		pieChart.getDescription().setEnabled(false);
		pieChart.getLegend().setEnabled(false);

		// Reduce the scale slightly
		pieChart.setScaleX(0.9f);
		pieChart.setScaleY(0.9f);
		linker = new BudgetCategoryTransactionLinker();

		// Construct the month selector
		Spinner monthSelector = findViewById(R.id.budgetMonthSelector);
		List<Calendar> activeMonths = linker.getActiveMonths(budgetCategory);
		List<String> monthOptions = new ArrayList<String>();
		DateFormat dateFormat = new SimpleDateFormat("MMMM, yyyy");

		// If there are no active months, default to the current month and year
		if (activeMonths.isEmpty()) {
			Calendar now = Calendar.getInstance();
			now.setTime(new Date());
			activeMonths.add(now);
		}

		// Generate the selection text
		for (Calendar activeMonth : activeMonths) {
			monthOptions.add(dateFormat.format(activeMonth.getTime()));
		}

		// Set the array adapter
		monthSelector.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, monthOptions));

		// Set the selection listener
		monthSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			/**
			 * Sets the pie chart values to the values relating to the selected month
			 *
			 * @param parentView        The parent view
			 * @param selectedItemView  The view of the selected item
			 * @param position          The position in the array that was selected
			 * @param id                Id, unrelated to position
			 */
			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				setPieChartValues(activeMonths.get(position));
			}

			/**
			 * If there is nothing selected, the program has reached an invalid state.
			 *
			 * @param adapterView   View of the adapter
			 */
			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {
				throw new NullPointerException("There should be a month selected no matter what");
			}
		});

		findViewById(R.id.updateBudgetButton).setOnClickListener(view ->
		{
			Intent updateBudget = new Intent(view.getContext(), UpdateBudgetCategory.class);
			updateBudget.putExtra("toModify", budgetCategory);
			startActivityForResult(updateBudget, 0);
			finish();
		});
	}

	/**
	 * Sets the pie chart values based on the selected month
	 *
	 * @param month Calendar instance which contains the month and year to query
	 */
	private void setPieChartValues(Calendar month) {
		// Set the colors between 'good' and 'bad'
		// Names are based on html color names
		int forestGreen = Color.parseColor("#228B22");
		int fireBrick = Color.parseColor("#b22222");
		List<PieEntry> entries = new ArrayList<PieEntry>();
		ArrayList<Integer> colors = new ArrayList<Integer>();

		// Get the amount spent and the max budget limit for this budget
		float amount = linker.calculateBudgetCategoryTotal(budgetCategory, month);
		float max = (float) budgetCategory.getBudgetLimit();

		// Calculate the diff
		float diff = max - amount;
		// If there is still money left on the budget
		if (diff > 0) {
			// Add the amount if there is any money spent
			if (amount > 0) {
				entries.add(new PieEntry(
						amount,
						"Current Amount"
				));
				colors.add(ContextCompat.getColor(getBaseContext(), R.color.colorPrimary));
			}

			// Add a color gradient between forestGreen and fireBrick based on the percent left on budget
			// Note that the order matters when adding entries, for pie charts the entries are inserted clockwise
			colors.add((Integer) new ArgbEvaluator().evaluate(amount / max, forestGreen, fireBrick));
			entries.add(new PieEntry(
					diff,
					"Left on Budget"
			));
		} else if (diff < 0) { // If over the budget
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
		} else { //diff == 0
			entries.add(new PieEntry(
					max,
					"At Budget Limit"
			));
			colors.add(ContextCompat.getColor(getBaseContext(), R.color.colorPrimary));
		}

		// Get the data set for the entries & set colours
		PieDataSet dataSet = new PieDataSet(entries, budgetCategory.getBudgetName());
		dataSet.setColors(colors);
		PieData data = new PieData(dataSet);

		// Set the text for the dollar values
		data.setValueTextSize(23f);
		data.setValueTextColor(Color.LTGRAY);
		data.setValueFormatter(new DollarValueFormatter());

		// Set the text for the entry labels (ex. 'At budget limit')
		pieChart.setEntryLabelTextSize(13f);
		pieChart.setEntryLabelColor(Color.LTGRAY);

		// Set the data and animate it
		pieChart.setData(data);
		pieChart.animateY(1400, Easing.EaseInOutQuad);
	}
}
