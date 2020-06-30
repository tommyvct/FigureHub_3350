package comp3350.pbbs.tests.objects;

public class User {
    private String firstName;
    private String lastName;

    public User(){
        firstName = "";
        lastName = "";
    }

    public User(String fname, String lName){
        firstName = fname;
        lastName = lName;
    }

    public String getFirstName(){ return firstName; }
    public String getLastName(){ return lastName; }

    public boolean equals(User other){
        boolean equals = firstName.equalsIgnoreCase(other.firstName);
        if(equals)equals = lastName.equalsIgnoreCase(other.lastName);
        return equals;
    }

    @Override
    public String toString(){return firstName + " " + lastName;}
}
