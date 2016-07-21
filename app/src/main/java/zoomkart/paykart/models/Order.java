package zoomkart.paykart.models;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by sudhanvahuruli on 6/25/16.
 */
public class Order {
    private int mId;
    private int mCustomerId;
    private int mAisle;
    private double mTotalAmount = 0.0;
    private List<Item> mItems;

    public Order(int id, int customerId, int aisle, double totalAmount){
        this.mId = id;
        this.mCustomerId = customerId;
        this.mAisle = aisle;
        this.mTotalAmount = totalAmount;
    }

    public int getId() {
        return this.mId;
    }

    public int getCustomerId() {
        return this.mCustomerId;
    }

    public int getAisle(){
        return this.mAisle;
    }

    public double getTotalAmount() {
        return this.mTotalAmount;
    }

    public List<Item> getItems(){
        return this.mItems;
    }

    public void populateItemList(JSONObject jsonObject){
    // Takes the list of Items that were sent from the server in the form of a JSON object and
        // populates the item list. The item list is
    }
}
