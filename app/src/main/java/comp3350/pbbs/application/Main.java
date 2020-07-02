package comp3350.pbbs.application;

import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import comp3350.pbbs.presentation.MainActivity;

public class Main {
    public static final String dbName = "TBCU"; //database contains Transactions, Budget Categories, Credit Cards, and Users

    public static void main(String[] args){
        startup();

        //MainActivity.run()    //This is the main Interface

        shutDown();

    }

    @SuppressLint("NewApi")
    public static void startup() { Services.createDataAccess(dbName);}

    public static void shutDown() { Services.closeDataAccess();}
}
