package comp3350.pbbs.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

import comp3350.pbbs.tests.integration.IntegrationTests;

/**
 * RunIntegrationTests
 * Group4
 * PBBS
 * <p>
 * This class will run all the integration tests for this project
 */
public class RunIntegrationTests {
	public static TestSuite suite;

	public static Test suite() {
		suite = new TestSuite("Unit tests");
		suite.addTest(IntegrationTests.suite());
		return suite;
	}

}
