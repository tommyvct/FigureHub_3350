package comp3350.pbbs.presentation.viewObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
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
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import comp3350.pbbs.R;
import comp3350.pbbs.business.AccessCard;
import comp3350.pbbs.business.AccessTransaction;
import comp3350.pbbs.objects.Card;
import comp3350.pbbs.objects.Transaction;
import comp3350.pbbs.presentation.DollarValueFormatter;
import comp3350.pbbs.presentation.updateObject.updateCard;

/**
 * Group4
 * PBBS
 *
 * Class to view a credit card's balance with visual charts
 */
public class ViewCard extends Activity {
    private LineChart lineChart;
    private AccessTransaction accessTransaction;
    private Card card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_card);

        // Select the line chart
        lineChart = findViewById(R.id.card_info);

        // Grab the card
        card = Objects.requireNonNull((Card) getIntent().getSerializableExtra("Card"));
        accessTransaction = new AccessTransaction();

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
            xAxis.setTextSize(13f);
            xAxis.setLabelCount(5, true);
            xAxis.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
        }

        YAxis yAxis;
        {   // // Y-Axis Style // //
            yAxis = lineChart.getAxisLeft();
            yAxis.setValueFormatter(new DollarValueFormatter());

            // disable dual axis (only use LEFT axis)
            lineChart.getAxisRight().setEnabled(false);

            // horizontal grid lines
            yAxis.enableGridDashedLine(10f, 10f, 0f);

            // axis range
            yAxis.setAxisMinimum(0f);
            yAxis.setTextSize(13f);
        }

        DateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy");

        //Giving the x-axis month labels
        xAxis.setValueFormatter(new IndexAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return dateFormat.format(new Date((long) value));
            }
        });


        findViewById(R.id.updateCard).setOnClickListener(view ->

        {
            Intent intent = new Intent(view.getContext(), updateCard.class);
            intent.putExtra("toUpdate", card);
            startActivityForResult(intent, 0);
            finish();
        });
        lineChart.getLegend().setEnabled(false);
        setLineChartValues();
    }

    /**
     * Sets the line chart values based on the selected month
     */
    private void setLineChartValues() {
        ArrayList<Entry> chartValues = new ArrayList<>();
        ArrayList<Long> times = new ArrayList<Long>();
        ArrayList<Transaction> transactions = new ArrayList<Transaction>();

        for(Transaction t : accessTransaction.retrieveTransactions()){
            if(card.equals(t.getCard())) {
                times.add(t.getTime().getTime());
                Collections.sort(times);
                transactions.add(times.indexOf(t.getTime().getTime()), t);
            }
        }
        for(Transaction t: transactions) {
            chartValues.add(new Entry(t.getTime().getTime(), t.getAmount()));
        }
        XAxis xAxis = lineChart.getXAxis();
        if(!times.isEmpty()) {
            xAxis.setAxisMinimum(Collections.min(times));
            xAxis.setAxisMaximum(Collections.max(times));
        }
//        lineChart.setScaleX(0.9f);
//        lineChart.setScaleY(0.8f);

        LineDataSet set1;

        if (lineChart.getData() != null &&
                lineChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            set1.setValues(chartValues);
            set1.notifyDataSetChanged();
            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();
        } else {
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
            set1.setValueFormatter(new DollarValueFormatter());


            set1.setValueTextSize(13f);
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1); // add the data sets

            // create a data object with the data sets
            LineData data = new LineData(dataSets);

            // set data
            lineChart.setData(data);
            lineChart.animateX(500, Easing.EaseInOutQuad);
        }
    }


}
