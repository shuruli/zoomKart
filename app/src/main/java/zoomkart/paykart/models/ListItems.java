package zoomkart.paykart.models;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by sudhanvahuruli on 7/25/16.
 */
public class ListItems {
    public List<Item> listItems;

    public static ListItems fromJson(String s) {
        return new Gson().fromJson(s, ListItems.class);
    }
    public String toString() {
        return new Gson().toJson(this);
    }
}
