package np.com.onlineclothingshop.API;

import np.com.onlineclothingshop.models.Item;
import np.com.onlineclothingshop.models.ItemResponse;
import np.com.onlineclothingshop.models.Response;
import np.com.onlineclothingshop.models.User;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface EndPoints {

    @POST("login")
    Call<Response> login(@Body User user);

    @POST("register")
    Call<Response> register(@Body User user);

    @Multipart
    @POST("upload")
    Call<Response> uploadImage(@Part MultipartBody.Part file);

    @POST("item")
    Call<Response> addItem(@Body Item item);

    @GET("items")
    Call<ItemResponse> getAllItems();

}