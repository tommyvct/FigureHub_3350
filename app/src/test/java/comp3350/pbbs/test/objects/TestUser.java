package comp3350.pbbs.test.objects;

import junit.framework.TestCase;

import comp3350.pbbs.objects.User;

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

    public void testToString(){
        assertEquals("Terra Jentsch", newUser.ToString());
    }

    public void testEquals(){
        User compUser = new User("terra", "jentsch");
        assertTrue(newUser.equals(compUser));
    }
}
