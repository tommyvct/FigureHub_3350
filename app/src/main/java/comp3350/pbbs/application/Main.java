package comp3350.pbbs.application;

import android.annotation.SuppressLint;

public class Main
{
    public static final String dbName = "TBCU"; //database contains Transactions, Budget Categories, Credit Cards, and Users
    private static String dbPathName = "db/TBCU";

    public static void main(String[] args){
        startup();
        shutDown();
    }

    @SuppressLint("NewApi")
    public static void startup() { Services.createDataAccess(dbName);}

    public static void shutDown() { Services.closeDataAccess();}

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
