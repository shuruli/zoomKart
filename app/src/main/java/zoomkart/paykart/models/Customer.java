package zoomkart.paykart.models;

/**
 * Created by sudhanvahuruli on 6/25/16.
 */
public class Customer {

    private String mId;
    private String mFirstName;
    private String mLastName;
    private String mEmail;

    public Customer(String id, String firstName, String lastName, String email) {
        this.mId = id;
        this.mFirstName = firstName;
        this.mLastName = lastName;
        this.mEmail = email;
    }

    public Customer(){

    }

    public String getId(){
        return this.mId;
    }

    public String getFirstName(){
        return this.mFirstName;
    }

    public String getLastName(){
        return this.mLastName;
    }
}
