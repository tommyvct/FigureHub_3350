package comp3350.pbbs.tests.business;

import junit.framework.TestCase;

import comp3350.pbbs.application.Main;
import comp3350.pbbs.persistence.DataAccessController;
import comp3350.pbbs.business.AccessUser;
import comp3350.pbbs.tests.persistence.StubDatabase;

/**
 * TestAccessUser
 * Azizul Hakim
 * PBBS
 * <p>
 * This class tests the AccessUser class.
 */
public class TestAccessUser extends TestCase {
	private AccessUser testAccess = null;           //variable for AccessUser class

	/**
	 * This method creates StubDatabase and AccessUser
	 */
	public void setUp() {
		DataAccessController.createDataAccess(new StubDatabase(Main.dbName));
		testAccess = new AccessUser();
	}

	/**
	 * Tests the AccessUser object
	 */
	public void testAccessUser() {
		assertNotNull(testAccess);
	}


	/**
	 * on First launch, username should be null.
	 * after setUsername, getUsername should be able to return it.
	 * change the username to a new one using setUsername, getUsername should be get it too.
	 */
	public void testGetUser() {
		try {
			testAccess.getUsername();
			fail("Expected NullPointerException");
		} catch (NullPointerException ignored) {
		}

		testAccess.setUsername("Tommy");
		assertEquals("Tommy", testAccess.getUsername());

		testAccess.setUsername("John");
		assertEquals("John", testAccess.getUsername());
	}

	/**
	 * This method closes StubDatabase
	 */
	public void tearDown() {
		DataAccessController.closeDataAccess();
	}
}
