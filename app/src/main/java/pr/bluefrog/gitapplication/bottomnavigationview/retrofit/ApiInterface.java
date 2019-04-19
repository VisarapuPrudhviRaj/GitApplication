package pr.bluefrog.gitapplication.bottomnavigationview.retrofit;

import org.json.JSONObject;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

public interface ApiInterface {

    // API's endpoints
/*
    void registration(@Field("username") String name,
                      @Field("imei") String email,
                      @Field("password") String password,
                      @Field("version") String logintype,
                      Callback<LoginResponse> callback);
*/
    @FormUrlEncoded
    @POST("/LoginValidate")

    void login(@Field("username") String name,
               @Field("imei") String imei,
               @Field("password") String password,
               @Field("version") String version,
               Callback<JSONObject> callback);
    @FormUrlEncoded
    @POST("/api/users")
    void login(@Field("name") String nme, @Field("job") String job, Callback<LoginResponse> data);
/*

    @FormUrlEncoded
    @POST("/api/register")
    void register(@Field("email") String email, @Field("password") String password, Callback<JSONObject> data);

*/

    @POST("/api/register")
    void register(@Body JSONObject email, Callback<JSONObject> data);


}
