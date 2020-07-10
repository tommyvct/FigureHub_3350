package comp3350.pbbs.application;

import android.os.Build;

import androidx.annotation.RequiresApi;

import comp3350.pbbs.persistence.StubDatabase;

/**
 * Services
 * Group4
 * PBBS
 *
 * This class creates and closes database for all other classes to use.
 */
public class Services {

    private static StubDatabase dbAccessService = null;             //static stub DB variable

    /**
     * This method creates a new initializes a stub database variable and populates data if the dbName is correct.
     *
     * @param  dbName A string representing the name of the database.
     * @return StubDatabase for other classes to use
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static StubDatabase createDataAccess(String dbName) {
        if (dbAccessService == null) {
            dbAccessService = new StubDatabase(dbName);
            if (dbName.equals(Main.dbName))
                dbAccessService.populateData();
        }
        return dbAccessService;
    }

    /**
     * This method returns the database which has been created otherwise it exits from the system.
     *
     * @param  dbName A string representing the name of the database.
     * @return StubDatabase once it has been created
     */
    public static StubDatabase getDataAccess(String dbName) {
        if (dbAccessService == null) {
            System.out.println("Connection to data access has not been established.");
            System.exit(1);
        }
        return dbAccessService;
    }

    /**
     * This method closes the database access.
     */
    public static void closeDataAccess() {
        dbAccessService = null;
    }
}