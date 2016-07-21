package zoomkart.paykart.models;


/**
 * Created by sudhanvahuruli on 6/25/16.
 */
public class Item {
    private int mId;
    private String mName;
    private double mPrice;
    private int mQuantity;
    private String mCategory;
    private String mImage;

    public Item(int id, String name, double price, int quantity, String category, String image){
        this.mId = id;
        this.mName = name;
        this.mPrice = price;
        this.mQuantity = quantity;
        this.mCategory = category;
        this.mImage = image;
    }

    public int getId() {
        return this.mId;
    }

    public String getName(){
        return this.mName;
    }

    public double getPrice(){
        return this.mPrice;
    }

    public int getQuantity(){
        return this.mQuantity;
    }

    public String getCategory(){
        return this.mCategory;
    }

    public String getImage(){
        return this.mImage;
    }
}
