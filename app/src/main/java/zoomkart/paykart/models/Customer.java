package zoomkart.paykart.models;

/**
 * Created by sudhanvahuruli on 6/25/16.
 */
public class Customer {

    private int mId;
    private String mFirstName;
    private String mLastName;
    private String mPassword;

    public Customer(int id, String firstName, String lastName, String password) {
        this.mId = id;
        this.mFirstName = firstName;
        this.mLastName = lastName;
        this.mPassword = password;
    }

    public int getId(){
        return this.mId;
    }

    public String getFirstName(){
        return this.mFirstName;
    }

    public String getLastName(){
        return this.mLastName;
    }

    public void setPassword(String Password){
        this.mPassword = Password;
    }
}
