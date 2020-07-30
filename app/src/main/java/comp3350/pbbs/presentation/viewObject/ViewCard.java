package comp3350.pbbs.presentation.viewObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
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
import comp3350.pbbs.business.AccessCard;
import comp3350.pbbs.objects.Cards.Card;
import comp3350.pbbs.presentation.updateObject.updateCard;

/**
 * Group4
 * PBBS
 *
 * Class to view a credit card's balance with visual charts
 */
public class ViewCard extends Activity {
    private LineChart lineChart;
    private AccessCard accessCard;
    private Card card;
    private TextView currentBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_card);

        // Select the line chart
        lineChart = findViewById(R.id.card_info);

        // Grab the card
        card = Objects.requireNonNull((Card) getIntent().getSerializableExtra("Card"));
        accessCard = new AccessCard();
        currentBalance = findViewById(R.id.currentBalance);
        currentBalance.setText(String.format("Current Balance: $ %.2f", accessCard.calculateCardTotal(card, Calendar.getInstance())));

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
        List<Calendar> activeMonths = accessCard.getActiveMonths(card);
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

        // TODO fill listTransactions


        findViewById(R.id.updateCard).setOnClickListener(view ->

        {
            Intent intent = new Intent(view.getContext(), updateCard.class);
            intent.putExtra("toUpdate", card);
            startActivity(intent);
            finish();
        });
    }

    /**
     * Sets the line chart values based on the selected month
     *
     * @param activeMonths Calendar instance which contains the month and year to query
     */
    private void setLineChartValues(List<Calendar> activeMonths) {
        ArrayList<Entry> chartValues = new ArrayList<>();
        int index = 0;

        for(Calendar activeMonth : activeMonths) {
            chartValues.add(new Entry(index, accessCard.calculateCardTotal(card, activeMonth)));
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
    }


}
