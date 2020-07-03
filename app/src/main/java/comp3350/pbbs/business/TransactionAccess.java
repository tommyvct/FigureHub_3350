package comp3350.pbbs.business;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import comp3350.pbbs.application.Main;
import comp3350.pbbs.application.Services;
import comp3350.pbbs.objects.BudgetCategory;
import comp3350.pbbs.objects.CreditCard;
import comp3350.pbbs.objects.Transaction;
import comp3350.pbbs.persistence.StubDatabase;

public class TransactionAccess {
    private StubDatabase db;
    public static final String[] DATE_FORMATS = new String[]{
            "d/M/yyyy H:m",
            "d-M-yyyy H:m",
    };

    public TransactionAccess() {
        db = Services.getDataAccess(Main.dbName);
    }

    private Date parseDatetime(String dateStr, String timeStr) {
        Date toReturn = null;
        for(String format : DATE_FORMATS)
        {
            DateFormat df = new SimpleDateFormat(format);
            // Needed or else 30/13/2020 will become 30/1/2021
            df.setLenient(false);
            try {
                toReturn = df.parse(dateStr + " " + timeStr);
            } catch (ParseException pe) { }
        }
        return toReturn;
    }

    private Float parseAmount(String amountStr) {
        Float toReturn = null;
        if(amountStr != null) {
            if(amountStr.contains(".")) {
                if(amountStr.matches("\\d*\\.\\d\\d$")) {
                    toReturn = Float.parseFloat(amountStr);
                    if(toReturn < 0)
                        toReturn = null;
                }
            }
            else if(amountStr.matches("[0-9]+")){
                toReturn = (float)Integer.parseInt(amountStr);
            }
        }
        return toReturn;
    }

    public boolean isValidDateTime(String dateStr, String timeStr) {
        return parseDatetime(dateStr, timeStr) != null;
    }

    public boolean isValidAmount(String amountStr) {
        return parseAmount(amountStr) != null;
    }

    public boolean isValidDescription(String desc) {
        return desc != null;
    }

    public boolean addTransaction(String desc, String dateStr, String timeStr, String amountStr, CreditCard card, BudgetCategory budgetCategory) {
        boolean toReturn = false;
        if(isValidAmount(amountStr) && isValidDateTime(dateStr, timeStr) && isValidDescription(desc)) {
            Date transactionTime = parseDatetime(dateStr, timeStr);
            float amount = parseAmount(amountStr);
            try {
                Transaction transaction = new Transaction(transactionTime, amount, desc, card, budgetCategory);
                // TODO: make this an if statement when this has a return type of boolean
                db.addTransactions(Arrays.asList(transaction));
                toReturn = true;
            }
            catch (IllegalArgumentException iae) { }
        }

        return toReturn;
    }
}
