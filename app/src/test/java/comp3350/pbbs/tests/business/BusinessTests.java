package comp3350.pbbs.tests.business;

import junit.framework.Test;
import junit.framework.TestSuite;

public class BusinessTests {
    public static TestSuite suite;

    public static Test suite() {
        suite = new TestSuite("Business tests");
        suite.addTestSuite(TestAccessBankAccount.class);
        suite.addTestSuite(TestAccessBudgetCategory.class);
        suite.addTestSuite(TestAccessCard.class);
        suite.addTestSuite(TestAccessTransaction.class);
        suite.addTestSuite(TestValidation.class);
        suite.addTestSuite(TestAccessUser.class);
        suite.addTestSuite(TestBankAccountCardLinker.class);
        suite.addTestSuite(TestBudgetCategoryTransactionLinker.class);
        suite.addTestSuite(TestCardTransactionLinker.class);
        suite.addTestSuite(TestNotificationObservable.class);
        suite.addTestSuite(TestParser.class);
        return suite;
    }
}
