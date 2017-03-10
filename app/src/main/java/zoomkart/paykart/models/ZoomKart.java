package zoomkart.paykart.models;

/**
 * Created by sudhanvahuruli on 3/10/17.
 */

public final class ZoomKart {
    public static Customer getCustomer() {
        return mCustomer;
    }

    public static void setCustomer(Customer customer) {
        mCustomer = customer;
    }

    private static Customer mCustomer;


}
