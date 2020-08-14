package comp3350.pbbs.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

import comp3350.pbbs.tests.business.BusinessTests;
import comp3350.pbbs.tests.objects.ObjectTests;
import comp3350.pbbs.tests.persistence.PersistenceTests;

/**
 * RunUnitTests
 * Group4
 * PBBS
 * <p>
 * This class will run all the unit tests for this project
 */
public class RunUnitTests {
	public static TestSuite suite;

	public static Test suite() {
		suite = new TestSuite("Unit tests");
		suite.addTest(ObjectTests.suite());
		suite.addTest(BusinessTests.suite());
		suite.addTest(PersistenceTests.suite());
		return suite;
	}

}
