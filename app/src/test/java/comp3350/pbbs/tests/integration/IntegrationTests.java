package comp3350.pbbs.tests.integration;

import junit.framework.Test;
import junit.framework.TestSuite;

public class IntegrationTests {
    public static TestSuite suite;

    public static Test suite() {
        suite = new TestSuite("Integration tests");
        suite.addTestSuite(TestHSQLIntegration.class);
        suite.addTestSuite(TestBusinessPersistenceSeam.class);
        return suite;
    }
}
