package homeautomation.capstone.com.homeautomation.Interface;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Matthew on 4/9/2016.
 */
public interface WebService {
    @GET("get_door_status")
    Call<List<String>> getDoorStatus();

    @GET("/projects")
    Call<ResponseBody> jquery();

    @POST("Signin")
    Call<String> signin();
/*
    /get_light_status
    /toggle_door
*/
}
