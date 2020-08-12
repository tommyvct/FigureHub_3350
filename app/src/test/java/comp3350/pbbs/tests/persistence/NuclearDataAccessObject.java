package comp3350.pbbs.tests.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import comp3350.pbbs.persistence.DataAccessObject;

/**
 * NuclearDataAccessObject
 * PBBS
 *
 * Extension of Data Access Object that allows for deleting all records in the database. Should be
 * used for testing only.
 */
public class NuclearDataAccessObject extends DataAccessObject {

    /**
     * Constructor of DB
     *
     * @param dbName the DB name
     */
    public NuclearDataAccessObject(String dbName) {
        super(dbName);
    }

    /**
     * Deletes all records in the Database. You have been warned.
     *
     * @return  True if successfully, false if not.
     */
    public boolean nuke() {
        boolean result = false;
        try {
            String cmdString = "DELETE FROM TRANSACTION";
            Statement stmt = con.createStatement();
            int updateCount = stmt.executeUpdate(cmdString);
            checkWarning(stmt, updateCount);
            cmdString = "DELETE FROM BANKACCOUNT";
            stmt = con.createStatement();
            updateCount = stmt.executeUpdate(cmdString);
            checkWarning(stmt, updateCount);
            cmdString = "DELETE FROM CARD";
            stmt = con.createStatement();
            updateCount = stmt.executeUpdate(cmdString);
            checkWarning(stmt, updateCount);
            cmdString = "DELETE FROM BUDGETCATEGORY";
            stmt = con.createStatement();
            updateCount = stmt.executeUpdate(cmdString);
            checkWarning(stmt, updateCount);
            cmdString = "DELETE FROM USERNAME";
            stmt = con.createStatement();
            updateCount = stmt.executeUpdate(cmdString);
            checkWarning(stmt, updateCount);
            result = true;
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return result;
    }
}
