package pr.bluefrog.gitapplication.bottomnavigationview.retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import pr.bluefrog.gitapplication.R;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SampleRetrofitActivity extends AppCompatActivity {
    LinearLayout llContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_retrofit);
        llContainer = findViewById(R.id.llContainer);
    }

    public void onClick_Load(View view) {
/*
        Api.getClient().login("014306", "000000000000", "password", "3", new Callback<LoginResponse>() {
            @Override
            public void success(LoginResponse loginResponse, Response response) {
                Log.d("success", "success: "+loginResponse.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("success", "success: "+error);

            }
        });
*/
   /*     Api.getClient().login( new Callback<List<LoginResponse>>() {
            @Override
            public void success(List<LoginResponse> loginResponse, Response response) {
                Log.d("success", "success: "+loginResponse.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("success", "success: "+error);

            }
        });*/

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", "sydney@fife");
            jsonObject.put("password", "pistol");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Api.getClient().register(jsonObject, new Callback<JSONObject>() {
            @Override
            public void success(JSONObject tokenResponse, Response response) {
                Log.d("success", "success: " + tokenResponse.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("success", "success: " + error);

            }
        });


    }
}
