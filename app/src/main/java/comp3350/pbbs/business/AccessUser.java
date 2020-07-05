package comp3350.pbbs.business;

import java.util.ArrayList;
import comp3350.pbbs.objects.User;
import comp3350.pbbs.application.Main;
import comp3350.pbbs.application.Services;
import comp3350.pbbs.persistence.StubDatabase;

/**
 * AccessUser
 * Azizul Hakim
 * PBBS
 *
 * This class creates an AccessUser class for the business layer.
 */
public class AccessUser {
    private StubDatabase dataAccess;        //variable for the stubDatabase
    private ArrayList<User> User;           //variable for user ArrayList
    private User user1;                     //variable for user
    private final int currUser;             //Only one user, so it can't be changed

    /**
     * This method creates StubDatabase and initializes all the fields
     */
    public AccessUser(){
        dataAccess = (StubDatabase) Services.getDataAccess(Main.dbName);
        user1 = null;
        User = null;
        currUser = 0;
    }

    /**
     * Getter method to return the user
     * @return user1
     */
    public User getUser1(){
       return user1 = (User) dataAccess.getUser().get(currUser);
    }

    /**
     * Getter method to return the user ArrayList
     * @return user ArrayList
     */
    public ArrayList<User> getUser(){
        return User = dataAccess.getUser();
    }

    /**
     * This method updates the current user
     * @return updated user
     */
    public User updateUser(User currentUser, User newUser){
        return dataAccess.updateUser(currentUser, newUser);
    }
}
