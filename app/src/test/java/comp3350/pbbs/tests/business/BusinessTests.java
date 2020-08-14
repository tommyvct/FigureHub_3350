package comp3350.pbbs.tests.business;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * BusinessTests
 * Group4
 * PBBS
 * <p>
 * This class will call all the tests for business layer
 */
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
