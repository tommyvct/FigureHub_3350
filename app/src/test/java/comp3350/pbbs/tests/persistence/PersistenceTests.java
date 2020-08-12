package comp3350.pbbs.tests.persistence;

import junit.framework.Test;
import junit.framework.TestSuite;

public class PersistenceTests {
    public static TestSuite suite;

    public static Test suite() {
        suite = new TestSuite("Object tests");
        suite.addTestSuite(TestDataAccess.class);
        return suite;
    }


}
