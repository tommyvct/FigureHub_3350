package comp3350.pbbs.tests.business;

import junit.framework.Test;
import junit.framework.TestSuite;

public class BusinessTests {
    public static TestSuite suite;

    public static Test suite() {
        suite = new TestSuite("Business tests");
        suite.addTestSuite(TestAccessValidation.class);
        suite.addTestSuite(TestAccessUser.class);
        suite.addTestSuite(TestAccessTransaction.class);
        suite.addTestSuite(TestAccessBudgetCategory.class);
        suite.addTestSuite(TestAccessBankAccount.class);
        suite.addTestSuite(TestAccessCard.class);
        suite.addTestSuite(TestParser.class);
        suite.addTestSuite(TestCardTransactionLinker.class);
        suite.addTestSuite(TestBudgetCategoryTransactionLinker.class);
        return suite;
    }
}
