package comp3350.pbbs.business;


import comp3350.pbbs.application.Main;
import comp3350.pbbs.application.Services;
import comp3350.pbbs.persistence.StubDatabase;

/**
 * AccessUser
 *
 * This class creates an AccessUser class for the business layer.
 */
public class AccessUser {
    private StubDatabase dataAccess;        //variable for the stubDatabase

    /**
     * This method creates StubDatabase and initializes all the fields
     */
    public AccessUser(){
        dataAccess = (StubDatabase) Services.getDataAccess(Main.dbName);
    }


    /**
     * Getter for username
     * @return username
     */
    public String getUsername(){
        return dataAccess.getUsername();
    }

    /**
     * setter for username, used when renaming
     * @param newUsername
     */
    public void setUsername(String newUsername){
        dataAccess.setUsername(newUsername);
    }
}
