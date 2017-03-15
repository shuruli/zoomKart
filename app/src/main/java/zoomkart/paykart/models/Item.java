package zoomkart.paykart.models;


import java.io.Serializable;

/**
 * Created by sudhanvahuruli on 6/25/16.
 */
public class Item implements Serializable{
    private String name;
    private float price;

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
