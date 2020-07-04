package comp3350.pbbs.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

import comp3350.pbbs.tests.business.TestAccessTransactionUpload;
import comp3350.pbbs.tests.objects.TestBudgetCategory;
import comp3350.pbbs.tests.objects.TestCreditCard;
import comp3350.pbbs.tests.objects.TestTransaction;
import comp3350.pbbs.tests.objects.TestUser;

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
    }
}
