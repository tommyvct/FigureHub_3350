package comp3350.pbbs.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

import comp3350.pbbs.tests.business.*;
import comp3350.pbbs.tests.business.accesstransaction.*;
import comp3350.pbbs.tests.objects.*;
import comp3350.pbbs.tests.persistence.TestDataAccess;

/**
 * AllTests
 * Group4
 * PBBS
 *
 * A test suite to run all the tests at a time
 */
public class AllTests {
    public static TestSuite suite;   //A TestSuite variable

    /**
     * Method to initialize the test suite.
     */
    public static Test suite() {
        suite = new TestSuite("All tests");
        testObjects();
        testBusiness();
        testPersistence();
        return suite;
    }

    /**
     * Method to run the tests which are for objects.
     */
    private static void testObjects() {
        suite.addTestSuite(TestTransaction.class);
        suite.addTestSuite(TestCard.class);
        suite.addTestSuite(TestBudgetCategory.class);
        suite.addTestSuite(TestBankAccount.class);
    }

    /**
     * Method to run the tests which are for the business layer.
     */
    private static void testBusiness() {
        suite.addTestSuite(TestAccessTransactionUpload.class);
        suite.addTestSuite(TestAccessTransactionRetrieve.class);
        suite.addTestSuite(TestAccessTransactionUpdate.class);
        suite.addTestSuite(TestAccessValidation.class);
        suite.addTestSuite(TestAccessUser.class);
        suite.addTestSuite(TestAccessBudgetCategory.class);
        suite.addTestSuite(TestAccessBankAccount.class);
        suite.addTestSuite(TestAccessCard.class);
    }

    /**
     * Method to run the tests which are for the persistence layer.
     */
    private static void testPersistence() {
        suite.addTestSuite(TestDataAccess.class);
    }
}
