package comp3350.pbbs.application;

import android.annotation.SuppressLint;

public class Main
{
    public static final String dbName = "TBCU"; //database contains Transactions, Budget Categories, Credit Cards, and Users

    public static void main(String[] args){
        startup();
        shutDown();
    }

    @SuppressLint("NewApi")
    public static void startup() { Services.createDataAccess(dbName);}

    public static void shutDown() { Services.closeDataAccess();}
}
