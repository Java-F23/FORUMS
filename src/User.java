import java.io.*;
import java.util.List;
import java.util.ArrayList;

class User {
    private int userID;
    private String username;
    private String password;
    private String userType;

    public User(int userID, String username, String password, String userType){
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int id) {
        this.userID = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String pass) {
        this.password = pass;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String type) {
        this.userType = type;
    }

    public void saveToFile() {
        // Implement code to save user data to a file
    }

    public void readFromFile() {
        // Implement code to read user data from a file
    }
}
