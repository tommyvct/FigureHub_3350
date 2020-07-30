package comp3350.pbbs.tests.business.accesstransaction;

/**
 * TestAccessTransactionDelete
 * Group4
 * PBBS
 *
 * This class tests part of the AccessTransaction class
 */
public class TestAccessTransactionDelete /*extends TestCase*/ {//make sure to uncomment the class
//    private AccessTransaction accessTransaction;
//    private Transaction testTransaction1;
//    private Transaction testTransaction2;
//
//    /**
//     * Before each test, set the test values
//     */
//    public void setUp() {
//        DataAccess db = Services.createDataAccess(new StubDatabase("test"));
//        accessTransaction = new AccessTransaction(true);
//        Date testDate = new Date();
//        float testAmount = 19.99f;
//        String testDesc = "Bought groceries.";
//        Card testCard = new Card("mastercard", "1000100010001000", "Alan Alfred", 6, 2022, 27);
//        BudgetCategory testBudgetCategory = new BudgetCategory("Groceries", 100);
//        testTransaction1 = new Transaction(testDate, testAmount, testDesc, testCard, testBudgetCategory);
//        testTransaction2 = new Transaction(Services.calcDate(testDate, -1), testAmount, testDesc, testCard, testBudgetCategory);
//        assertTrue(db.insertTransaction(testTransaction1));
//        assertTrue(db.insertTransaction(testTransaction2));
//    }
//
//    /**
//     * Close the database connection after the test
//     */
//    public void tearDown() {
//        Services.closeDataAccess();
//    }
//
//    /**
//     * Test deleting a single transaction, as well as deleting the same transaction
//     */
//    public void testSingleTransaction() {
//        assertTrue(accessTransaction.deleteTransaction(testTransaction1));
//
//        List<Transaction> results = accessTransaction.retrieveTransactions();
//        assertFalse(results.contains(testTransaction1));
//        assertTrue(results.contains(testTransaction2));
//
//        assertFalse(accessTransaction.deleteTransaction(testTransaction1));
//    }
//
//    /**
//     * Test deleting multiple transactions
//     */
//    public void testMultipleTransactions() {
//        assertTrue(accessTransaction.deleteTransaction(testTransaction1));
//        assertTrue(accessTransaction.deleteTransaction(testTransaction2));
//
//        List<Transaction> results = accessTransaction.retrieveTransactions();
//        assertTrue(results.isEmpty());
//    }
//
//    /**
//     * Test deleting invalid transactions
//     */
//    public void testInvalidTransactions() {
//        Card testCard = new Card("mastercard", "1000100010001000", "Alan Alfred", 6, 2022, 27);
//        BudgetCategory testBudgetCategory = new BudgetCategory("Groceries", 100);
//        assertFalse(accessTransaction.deleteTransaction(new Transaction(new Date(), 20, "Bought groceries", testCard, testBudgetCategory)));
//        assertFalse(accessTransaction.deleteTransaction(null));
//    }
}
