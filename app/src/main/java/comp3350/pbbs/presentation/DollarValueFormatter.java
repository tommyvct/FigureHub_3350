package comp3350.pbbs.presentation;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;

/**
 * DollarValueFormatter
 * Group4
 * PBBS
 * <p>
 * Class that handles formatting float values to dollars
 */
public class DollarValueFormatter extends ValueFormatter {
	private DecimalFormat rounding = new DecimalFormat("0.00");

	@Override
	public String getFormattedValue(float value) {
		return "$" + rounding.format(value);
	}
}
