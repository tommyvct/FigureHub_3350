package comp3350.pbbs.tests.objects;

import junit.framework.TestCase;

import comp3350.pbbs.objects.CreditCard;
import comp3350.pbbs.objects.User;
import static org.junit.Assert.assertNotEquals;

/**
 *
 */
public class TestUser extends TestCase{
    private User newUser;

    public void setUp() {
        newUser = new User("Terra", "Jentsch");
    }

    public void testGetFirstName(){
        assertEquals("Terra", newUser.getFirstName());
    }

    public void testGetLastName(){
        assertEquals("Jentsch", newUser.getLastName());
    }

    public void testEquals(){
        User compUser = new User("terra", "jentsch");
        assertTrue(newUser.equals(compUser));

        User compUser2 = new User("Ariel", "Jentsch");
        assertFalse(newUser.equals(compUser2));

        User compUser3 = new User("Terra", "Smith");
        assertFalse((newUser.equals(compUser3)));
    }
}
