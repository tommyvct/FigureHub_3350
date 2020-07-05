package comp3350.pbbs.tests.business;

import junit.framework.TestCase;
import java.util.ArrayList;

import comp3350.pbbs.application.Main;
import comp3350.pbbs.application.Services;
import comp3350.pbbs.objects.User;
import comp3350.pbbs.business.AccessUser;

/**
 * TestAccessUser
 * Azizul Hakim
 * PBBS
 *
 * This class tests the AccessUser class.
 */
public class TestAccessUser extends TestCase{
    private AccessUser testAccess = null;           //variable for AccessUser class

    /**
     * This method creates StubDatabase and AccessUser
     */
    public void setUp(){
        Services.createDataAccess(Main.dbName);
        testAccess = new AccessUser();
    }

    /**
     * Tests the AccessUser object
     */
    public void testAccessUser(){
        assertNotNull(testAccess);
    }

    /**
     * Tests getUser1 method which returns the User object
     */
    public void testGetUser1(){
        User notExist = new User("Azizul","Hakim");
        User exist = new User("Jimmy","Kimel");
        assertFalse(testAccess.getUser1().equals(notExist));
        assertTrue(testAccess.getUser1().equals(exist));
    }

    /**
     * Tests getUser method which returns the user ArrayList
     */
    public void testGetUser(){
        assertNotNull(testAccess.getUser());
    }

    /**
     * Tests updateUser method which returns the updated user
     */
    public void testUpdateUser(){
        User currentUser = testAccess.getUser1();
        User updatedUser = new User("Azizul", "Hakim");

        assertNotNull(testAccess.updateUser(currentUser, updatedUser));
    }

    /**
     * This method closes StubDatabase
     */
    public void tearDown(){
        Services.closeDataAccess();
    }
}
