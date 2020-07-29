package comp3350.pbbs.application;

import android.os.Build;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import androidx.annotation.RequiresApi;

import comp3350.pbbs.persistence.DataAccess;
import comp3350.pbbs.persistence.DataAccessObject;

/**
 * Services
 * Group4
 * PBBS
 *
 * This class creates and closes database for all other classes to use.
 */
public class Services {

    private static DataAccess dbAccessService = null;

    /**
     * This method creates a new database variable.
     *
     * @param dbName A string representing the name of the database.
     * @return Database for other classes to use
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static DataAccess createDataAccess(String dbName) {
        if (dbAccessService == null) {
            dbAccessService = new DataAccessObject(dbName);
            dbAccessService.open(Main.getDBPathName());
        }
        return dbAccessService;
    }

    /**
     * This method creates a new initializes a stub database variable and populates data if the dbName is correct.
     *
     * @param dataAccess A string representing the name of the database.
     * @return StubDatabase for other classes to use
     */
    public static DataAccess createDataAccess(DataAccess dataAccess) {
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
    public static DataAccess getDataAccess(String dbName) {
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

    /**
     * This method performs the date calculation
     *
     * @return a Date object
     */
    public static Date calcDate(Date d, int n) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(d);
        calendar.add(Calendar.DATE, n);
        d = calendar.getTime();
        return d;
    }
}