package comp3350.pbbs.presentation;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import comp3350.pbbs.R;
import comp3350.pbbs.business.AccessCreditCard;
import comp3350.pbbs.business.AccessTransaction;
import comp3350.pbbs.objects.CreditCard;

/**
 * Group4
 * PBBS
 *
 * Class to view a credit card's balance with visual charts
 */
public class ViewCard extends Activity {
    private LineChart lineChart;
    private AccessCreditCard accessCreditCard;
    private CreditCard creditCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_card);

        // Select the pie chart
        lineChart = findViewById(R.id.card_info);

        // Grab the credit card
        creditCard =
                Objects.requireNonNull((CreditCard) getIntent().getSerializableExtra("creditCard"));
        accessCreditCard = new AccessCreditCard();

//        // Put the card info in the center
//        pieChart.setCenterText(budgetCategory.getBudgetName());
//        pieChart.setCenterTextSize(20f);
//
//        // Disable description & legend
//        pieChart.getDescription().setEnabled(false);
//        pieChart.getLegend().setEnabled(false);
//
//        // Reduce the scale slightly
//        pieChart.setScaleX(0.9f);
//        pieChart.setScaleY(0.9f);
        {   // // Chart Style // //

            // background color
            lineChart.setBackgroundColor(Color.WHITE);

            // disable description text
            lineChart.getDescription().setEnabled(false);

            // enable touch gestures
            lineChart.setTouchEnabled(true);

            // set listeners
//            lineChart.setOnChartValueSelectedListener(this);
            lineChart.setDrawGridBackground(false);

            // enable scaling and dragging
            lineChart.setDragEnabled(true);
            lineChart.setScaleEnabled(true);
            // chart.setScaleXEnabled(true);
            // chart.setScaleYEnabled(true);

            // force pinch zoom along both axis
            lineChart.setPinchZoom(true);
        }

        XAxis xAxis;
        {   // // X-Axis Style // //
            xAxis = lineChart.getXAxis();

            // vertical grid lines
            xAxis.enableGridDashedLine(10f, 10f, 0f);

            xAxis.setAxisMinimum(0.0f);
            xAxis.setAxisMaximum(12.0f);
        }

        YAxis yAxis;
        {   // // Y-Axis Style // //
            yAxis = lineChart.getAxisLeft();

            // disable dual axis (only use LEFT axis)
            lineChart.getAxisRight().setEnabled(false);

            // horizontal grid lines
            yAxis.enableGridDashedLine(10f, 10f, 0f);

            // axis range
//            yAxis.setAxisMaximum(200f);
            yAxis.setAxisMinimum(0f);
        }



        // Construct the month selector
        Spinner monthSelector = findViewById(R.id.cardMonthSelector);
        List<Calendar> activeMonths = accessCreditCard.getActiveMonths(creditCard);
        List<String> monthOptions = new ArrayList<String>();
        DateFormat dateFormat = new SimpleDateFormat("MMMM, yyyy");

        // If there are no active months, default to the current month and year
        if(activeMonths.isEmpty()){
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());
            activeMonths.add(now);
        }

        // Generate the selection text
        for(Calendar activeMonth : activeMonths) {
            monthOptions.add(dateFormat.format(activeMonth.getTime()));
        }

        //Giving the x-axis month labels
        xAxis.setValueFormatter(new IndexAxisValueFormatter() {
        @Override
            public String getFormattedValue(float value) {
                if (value >= 0) {
                    if (value < monthOptions.size()) return monthOptions.get((int) value);
                    return "";
                }
                return "";
            }
        });

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
                setLineChartValues(activeMonths);

                // draw points over time
                lineChart.animateX(1500);
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
    }

    /**
     * Sets the pie chart values based on the selected month
     *
     * @param activeMonths Calendar instance which contains the month and year to query
     */
    private void setLineChartValues(List<Calendar> activeMonths) {
        ArrayList<Entry> chartValues = new ArrayList<>();
        int index = 0;

        for(Calendar activeMonth : activeMonths) {
            chartValues.add(new Entry(index, accessCreditCard.calculateCreditCardTotal(creditCard, activeMonth)));
            index++;
        }

        LineDataSet set1;

        if (lineChart.getData() != null &&
                lineChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            set1.setValues(chartValues);
            set1.notifyDataSetChanged();
            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();
        }else {
            // create a dataset and give it a type
            set1 = new LineDataSet(chartValues, "DataSet 1");

            set1.setDrawIcons(false);

            // draw dashed line
            set1.enableDashedLine(10f, 5f, 0f);

            // black lines and points
            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);

            // line thickness and point size
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);

            // draw points as solid circles
            set1.setDrawCircleHole(false);

//            // customize legend entry
//            set1.setFormLineWidth(1f);
//            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
//            set1.setFormSize(15.f);
//
            // text size of values
            set1.setValueTextSize(9f);

            // draw selection line as dashed
            set1.enableDashedHighlightLine(10f, 5f, 0f);

            // set the filled area
            set1.setDrawFilled(true);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return lineChart.getAxisLeft().getAxisMinimum();
                }
            });


            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1); // add the data sets

            // create a data object with the data sets
            LineData data = new LineData(dataSets);

            // set data
            lineChart.setData(data);
        }


//    // Set the colors between 'good' and 'bad'
//        // Names are based on html color names
//        int forestGreen = Color.parseColor("#228B22");
//        int fireBrick = Color.parseColor("#b22222");
//        List<PieEntry> entries = new ArrayList<PieEntry>();
//        ArrayList<Integer> colors = new ArrayList<Integer>();
//
//        // Get the amount spent and the max budget limit for this budget
//        float amount = accessBudgetCategory.calculateBudgetCategoryTotal(budgetCategory, month);
//        float max = (float)budgetCategory.getBudgetLimit();
//
//        // Calculate the diff
//        float diff = max - amount;
//        // If there is still money left on the budget
//        if (diff > 0) {
//            // Add the amount if there is any money spent
//            if(amount > 0) {
//                entries.add(new PieEntry(
//                        amount,
//                        "Current Amount"
//                ));
//                colors.add(ContextCompat.getColor(getBaseContext(), R.color.colorPrimary));
//            }
//
//            // Add a color gradient between forestGreen and fireBrick based on the percent left on budget
//            // Note that the order matters when adding entries, for pie charts the entries are inserted clockwise
//            colors.add((Integer)new ArgbEvaluator().evaluate(amount / max, forestGreen, fireBrick));
//            entries.add(new PieEntry(
//                    diff,
//                    "Left on Budget"
//            ));
//        }
//        else if(diff < 0) { // If over the budget
//            entries.add(new PieEntry(
//                    Math.abs(diff),
//                    "Over Budget"
//            ));
//            colors.add(fireBrick);
//
//            entries.add(new PieEntry(
//                    max,
//                    "Budget Limit"
//            ));
//            colors.add(ContextCompat.getColor(getBaseContext(), R.color.colorPrimary));
//        }
//        else { //diff == 0
//            entries.add(new PieEntry(
//                    max,
//                    "At Budget Limit"
//            ));
//            colors.add(ContextCompat.getColor(getBaseContext(), R.color.colorPrimary));
//        }
//
//        // Get the data set for the entries & set colours
//        PieDataSet dataSet = new PieDataSet(entries, budgetCategory.getBudgetName());
//        dataSet.setColors(colors);
//        PieData data = new PieData(dataSet);
//
//        // Set the text for the dollar values
//        data.setValueTextSize(23f);
//        data.setValueTextColor(Color.LTGRAY);
//        data.setValueFormatter(new DollarValueFormatter());
//
//        // Set the text for the entry labels (ex. 'At budget limit')
//        pieChart.setEntryLabelTextSize(13f);
//        pieChart.setEntryLabelColor(Color.LTGRAY);
//
//        // Set the data and animate it
//        pieChart.setData(data);
//        pieChart.animateY(1400, Easing.EaseInOutQuad);
    }
}
