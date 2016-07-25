package zoomkart.paykart.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import zoomkart.paykart.models.Item;
import zoomkart.paykart.models.ListItems;

/**
 * Created by sudhanvahuruli on 7/23/16.
 */
public class RetrofitServices {

    public interface APIService {
        @GET("/order_items?customer_id=1")
        Call<ListItems> loadItems();
    }
}
