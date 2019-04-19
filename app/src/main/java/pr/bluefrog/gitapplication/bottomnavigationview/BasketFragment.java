package pr.bluefrog.gitapplication.bottomnavigationview;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pr.bluefrog.gitapplication.R;

public class BasketFragment extends Fragment implements OnMapReadyCallback {

    List<ShopModel> list = new ArrayList<>();
    GoogleMap gMap;
    public static final String base_url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?type=atm&location=37.421998333333335,-122.08400000000002&name=a&key=AIzaSyAD47cgCMH-X5YeyMoVIGbaMCBw63Fs3T4&radius=80450";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.basket_layout, container, false);
        setMap();
        mapServiceHit();

        return view;

    }

    private void setMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.getUiSettings().setCompassEnabled(false);
    }

    private void mapServiceHit() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, base_url, new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Success", "onResponse: " + response);

                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response));
                    if (jsonObject.getString("status").equals("OK")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            ShopModel shopModel = new ShopModel();
                            JSONObject resultObj = jsonArray.getJSONObject(i);
                            shopModel.setName(resultObj.getString("name"));
                            JSONObject gemotryObj = resultObj.getJSONObject("geometry");
                            JSONObject locationObj = gemotryObj.getJSONObject("location");
                            shopModel.setLat(locationObj.getString("lat"));
                            shopModel.setLng(locationObj.getString("lng"));
                            list.add(shopModel);

                        }

                        loadMapdata();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", "onErrorResponse: " + error);
            }
        });

        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        queue.add(request);
    }

    private void loadMapdata() {
        for (int i = 0; i < list.size(); i++) {
            ShopModel model = list.get(i);

            double lat = Double.parseDouble(model.getLat());
            double lng = Double.parseDouble(model.getLng());

            LatLng latLng = new LatLng(lat, lng);

            gMap.addMarker(new MarkerOptions().position(latLng).title(model.getName()));

            CameraPosition position = CameraPosition.builder()
                    .target(new LatLng(lat, lng))
                    .zoom(14)
                    .tilt(30)
                    .build();                 // Creates a CameraPosition from the builder
            gMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));
        }
    }


}
