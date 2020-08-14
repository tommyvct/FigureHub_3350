package comp3350.pbbs.business;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static comp3350.pbbs.business.AccessTransaction.DATE_FORMATS;

/**
 * Parser
 * Group4
 * PBBS
 * <p>
 * This class includes all parse methods for use
 */
public class Parser {
	/**
	 * This method parses the given amount string to a float number, rounded to 2 decimals
	 *
	 * @param amountStr The string to convert
	 * @return The converted float, or null if the amount is invalid
	 */
	public static Float parseAmount(String amountStr) {
		Float toReturn = null;

		if (amountStr != null) {
			amountStr = amountStr.trim();
			// If the string is decimal-like
			if (amountStr.contains(".")) {
				// Check if the string is a decimal number with 1 or 2 decimal places
				if (amountStr.matches("\\d*\\.\\d\\d$") || amountStr.matches("\\d*\\.\\d$")) {
					// Parse the string
					toReturn = Float.parseFloat(amountStr);
					if (toReturn < 0)
						toReturn = null;
				}
			}
			// Check if the amount is a positive integer
			else if (amountStr.matches("[0-9]+")) {
				toReturn = (float) Integer.parseInt(amountStr);
			}
		}
		return toReturn;
	}

	/**
	 * This method parses the given date string and time string into a single date time object.
	 *
	 * @param dateStr The given date to convert
	 * @param timeStr The given time to convert
	 * @return java.text.Date object that contains the date time, or null if the strings
	 * do not match any of the predefined formats
	 */
	public static Date parseDatetime(String dateStr, String timeStr) {
		Date toReturn = null;

		// Check the possible date formats
		for (String format : DATE_FORMATS) {
			@SuppressLint("SimpleDateFormat")
			DateFormat df = new SimpleDateFormat(format);
			// Needed or else 30/13/2020 will become 30/1/2021
			df.setLenient(false);
			try {
				// Parse the date
				toReturn = df.parse(dateStr + " " + timeStr);
			} catch (ParseException ignored) {
			}
		}
		return toReturn;
	}
}
