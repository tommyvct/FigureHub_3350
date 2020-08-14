package comp3350.pbbs.tests.integration;

import comp3350.pbbs.application.Main;
import comp3350.pbbs.persistence.DataAccessController;
import comp3350.pbbs.tests.persistence.NuclearDataAccessObject;
import comp3350.pbbs.tests.persistence.StubDatabase;
import comp3350.pbbs.tests.persistence.TestDataAccess;

/**
 * TestHSQLIntegration
 * Group4
 * PBBS
 * <p>
 * This class performs database integration test
 */
public class TestHSQLIntegration extends TestDataAccess {
    public TestHSQLIntegration(String arg0) {
        super(arg0);
    }

    @Override
    public void setUp() {
        dataAccess = DataAccessController.createDataAccess(new NuclearDataAccessObject(Main.getDBPathName()));
        if(dataAccess instanceof NuclearDataAccessObject) {
            ((NuclearDataAccessObject) dataAccess).nuke();
        }
        StubDatabase.populateData(dataAccess);
    }

    @Override
    public void tearDown() {
        if(dataAccess instanceof NuclearDataAccessObject) {
            ((NuclearDataAccessObject) dataAccess).nuke();
        }
        super.tearDown();
    }
}
