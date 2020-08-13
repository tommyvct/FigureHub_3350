package comp3350.pbbs.persistence;

import android.os.Build;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import androidx.annotation.RequiresApi;

import comp3350.pbbs.application.Main;
import comp3350.pbbs.persistence.DataAccessI;
import comp3350.pbbs.persistence.DataAccessObject;


/**
 * DataAccessController
 * Group4
 * PBBS
 *
 * This class creates and closes database for all other classes to use.
 */
public class DataAccessController {

    private static DataAccessI dbAccessService = null;

    /**
     * This method creates a new database variable.
     *
     * @param dbName A string representing the name of the database.
     * @return Database for other classes to use
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static DataAccessI createDataAccess(String dbName) {
        if (dbAccessService == null) {
            dbAccessService = new StubDatabase(dbName);// DataAccessObject(dbName);
            dbAccessService.open("populate"); // Main.getDBPathName());
        }
        return dbAccessService;
    }

    /**
     * This method creates a new initializes a stub database variable and populates data if the dbName is correct.
     *
     * @param dataAccess A string representing the name of the database.
     * @return StubDatabase for other classes to use
     */
    public static DataAccessI createDataAccess(DataAccessI dataAccess) {
        if (dbAccessService == null) {
            dbAccessService = dataAccess;
            dbAccessService.open(dbAccessService.getDBName());
        }
        return dbAccessService;
    }

    /**
     * This method returns the database which has been created otherwise it exits from the system.
     *
     * @param dbName A string representing the name of the database.
     * @return StubDatabase once it has been created
     */
    public static DataAccessI getDataAccess(String dbName) {
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
        if (dbAccessService != null) {
            dbAccessService.close();
        }
        dbAccessService = null;
    }
}