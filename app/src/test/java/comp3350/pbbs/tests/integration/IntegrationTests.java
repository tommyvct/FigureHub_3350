package comp3350.pbbs.tests.integration;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * IntegrationTests
 * Group4
 * PBBS
 * <p>
 * This class will call all the integration tests
 */
public class IntegrationTests {
    public static TestSuite suite;

    public static Test suite() {
        suite = new TestSuite("Integration tests");
        suite.addTestSuite(TestHSQLIntegration.class);
        suite.addTestSuite(TestBusinessPersistenceSeam.class);
        return suite;
    }
}
