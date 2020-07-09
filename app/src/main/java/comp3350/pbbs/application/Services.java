package comp3350.pbbs.application;

import android.os.Build;
import androidx.annotation.RequiresApi;
import comp3350.pbbs.persistence.StubDatabase;

public class Services {

    private static StubDatabase dbAccessService = null;

    /**
     *
     * @param dbName database name
     * @return a {@code StubDatabase} instance with given name
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static StubDatabase createDataAccess(String dbName){
        if (dbAccessService == null){
            dbAccessService = new StubDatabase(dbName);
            if(dbName.equals(Main.dbName))
                dbAccessService.populateData();
        }
        return dbAccessService;
    }

    /**
     *
     * @param dbName database name
     * @return {@code null} if currently no data access is created, otherwise the current data access
     */
    public static StubDatabase getDataAccess(@SuppressWarnings("unused") String dbName){
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
