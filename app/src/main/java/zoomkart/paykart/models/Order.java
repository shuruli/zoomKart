package zoomkart.paykart.models;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by sudhanvahuruli on 6/25/16.
 */
public class Order implements Serializable{
    private int mId;
    private String mCustomerId;
    private Date mDate;
    private double mTotalAmount = 0.0;
    private Item[] mItems;

    public Order(int id, String customerId, Date date, double totalAmount, Item[] items){
        this.mId = id;
        this.mCustomerId = customerId;
        this.mDate = date;
        this.mTotalAmount = totalAmount;
        this.mItems = items;
    }

    public int getmId() {
        return mId;
    }

    public String getmCustomerId() {
        return mCustomerId;
    }

    public Date getmDate() {
        return mDate;
    }

    public double getmTotalAmount() {
        return mTotalAmount;
    }

    public Item[] getmItems() {
        return mItems;
    }
}
