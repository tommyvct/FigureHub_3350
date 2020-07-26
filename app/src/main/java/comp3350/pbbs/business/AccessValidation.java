package comp3350.pbbs.business;

import java.util.Calendar;

import comp3350.pbbs.objects.Cards.CreditCard;

public class AccessValidation
{
    /**
     * method: check if the a credit card holder's full name is valid
     *
     * @param str the credit card holder name
     * @return true if the holder name meet the requirement of the format
     */
    public static boolean isValidName(String str) {
        return CreditCard.isValidName(str);
    }

    /**
     * method: check if the input expire date is valid
     *
     * @param month the month
     * @param year  the year
     * @return 0 if everything's alright <br>
     * 1 if invalid month <br>
     * 2 if invalid year <br>
     * 3 if both month and year is invalid <br>
     * 4 if year is less than 4 digit <br>
     * 5 if month expired <br>
     * 6 if year expired <br>
     * 7 if null or empty string provided
     */
    public static int isValidExpirationDate(String month, String year) {
        int m, y;
        int result = 0;
        Calendar calender = Calendar.getInstance();
        int currMonth = calender.get(Calendar.MONTH) + 1;
        int currYear = calender.get(Calendar.YEAR);

        try {
            m = Integer.parseInt(month);
            y = Integer.parseInt(year);
        } catch (NumberFormatException e) {
            return 7;
        }

        if (y < 1000)
            return 4;
        if (m < 1 || m > 12)
            result += 1;
        if (y > 2099)
            result += 2;
        if (currYear == y && currMonth > m)
            return 5;
        if (y < currYear)
            return 6;

        return result;
    }

    /**
     * method: check if the input pay date is valid
     *
     * @param n the day
     * @return true if the day is real-world existed
     */
    public static boolean isValidPayDate(int n) {
        return CreditCard.isValidPayDate(n);
    }

    /**
     * This method checks if the given date string and time string are valid.
     *
     * A valid date follows the format dd/mm/yyyy or dd-mm-yyyy, while a valid time follows the
     * 24-hour format: 0:00 to 23:59
     *
     * @param dateStr The date to parse.
     * @param timeStr The time to check.
     * @return True if the amount is valid, or false if it is invalid
     */
    public static boolean isValidDateTime(String dateStr, String timeStr) {
        return AccessTransaction.parseDatetime(dateStr, timeStr) != null;
    }

    /**
     * This method tells if the given amount string is a valid numeric amount
     *
     * A valid amount is either a positive integer (20) or a positive decimal number with 2 decimal
     * places (20.03)
     *
     * @param amountStr The amount to check.
     * @return True if the amount is valid, or false if it is invalid
     */
    public static boolean isValidAmount(String amountStr) {
        return AccessTransaction.parseAmount(amountStr) != null;
    }

    /**
     * This method checks if the description is valid or not
     *
     * A valid description is non null and not empty
     *
     * @param desc The description to check
     * @return True if the description is valid, or false if it is invalid
     */
    public static boolean isValidDescription(String desc) {
        return desc != null && !desc.isEmpty();
    }

}