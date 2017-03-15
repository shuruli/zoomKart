package zoomkart.paykart.models;

import com.stripe.android.model.Card;

/**
 * Created by sudhanvahuruli on 6/25/16.
 */
public class Payment {
    String name;
    String address;
    String postalCode;
    String province;
    Card card;
}
