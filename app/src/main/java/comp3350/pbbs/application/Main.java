package comp3350.pbbs.application;

import android.annotation.SuppressLint;

import comp3350.pbbs.business.NotificationObservable;
import comp3350.pbbs.persistence.DataAccessController;

/**
 * Main
 * Group4
 * PBBS
 *
 * This class is the Main file in the project.
 */
public class Main {
    public static final String dbName = "TBCU"; //database contains Transactions, Budget Categories, Credit Cards, and Users
    private static String dbPathName = "db/TBCU";
    public static NotificationObservable observable;

    public static void main(String[] args) {
        startup();
        shutDown();
    }

    @SuppressLint("NewApi")
    public static void startup() {
        DataAccessController.createDataAccess(dbName);
        observable = NotificationObservable.getInstance();
    }

    public static void shutDown() {
        DataAccessController.closeDataAccess();
    }

    public static String getDBPathName() {
        if (dbPathName == null)
            return dbName;
        else
            return dbPathName;
    }

    public static void setDBPathName(String pathName) {
        System.out.println("Setting DB path to: " + pathName);
        dbPathName = pathName;
    }
}
