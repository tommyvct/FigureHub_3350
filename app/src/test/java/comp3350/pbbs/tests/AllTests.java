package comp3350.pbbs.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

import comp3350.pbbs.tests.business.*;
import comp3350.pbbs.tests.business.accesstransaction.*;
import comp3350.pbbs.tests.objects.*;

public class AllTests
{
    public static TestSuite suite;

    public static Test suite()
    {
        suite = new TestSuite("All tests");
        testObjects();
        testBusiness();
        return suite;
    }

    private static void testObjects()
    {
        suite.addTestSuite(TestTransaction.class);
        suite.addTestSuite(TestCreditCard.class);
        suite.addTestSuite(TestBudgetCategory.class);
        suite.addTestSuite(TestUser.class);
    }

    private static void testBusiness()
    {
        suite.addTestSuite(TestAccessTransactionUpload.class);
        suite.addTestSuite(TestAccessTransactionRetrieve.class);
        suite.addTestSuite(TestAccessTransactionUpdate.class);
        suite.addTestSuite(TestAccessTransactionDelete.class);
        suite.addTestSuite(TestAccessUser.class);
        suite.addTestSuite(TestAccessBudgetCategory.class);
        suite.addTestSuite(TestAccessCreditCard.class);
    }
}
