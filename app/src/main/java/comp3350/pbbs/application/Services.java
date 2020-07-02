package comp3350.pbbs.application;

import android.os.Build;

import androidx.annotation.RequiresApi;

import comp3350.pbbs.persistence.StubDatabase;

public class Services {

    private static StubDatabase dbAccessService = null;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static StubDatabase createDataAccess(String dbName){
        if (dbAccessService == null){
            dbAccessService = new StubDatabase(dbName);
            dbAccessService.populateData();
        }
        return dbAccessService;
    }

    public static StubDatabase getDataAccess(String dbName){
        if(dbAccessService == null){
            System.out.println("Connection to data access has not been established.");
            System.exit(1);
        }
        return dbAccessService;
    }

    public static void closeDataAccess(){
        dbAccessService = null;
    }
}
