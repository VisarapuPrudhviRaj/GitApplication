package pr.bluefrog.gitapplication.bottomnavigationview;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pr.bluefrog.gitapplication.R;

public class ShopFrament extends Fragment {

    RecyclerView recyclerView;
    public static final String URL = "https://api.androidhive.info/json/movies_2017.json";
    Context context;
    List<ShopModel> list = new ArrayList<>();
    ShopAdapter adapter;


    public static ShopFrament newInstance(String param1, String param2, Context context) {
        ShopFrament fragment = new ShopFrament();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shop_layout, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);

        adapter = new ShopAdapter(getActivity().getApplicationContext(), list);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        fetchShop();


        return view;
    }

    private void fetchShop() {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.d("Shop", "onResponse: " + response);

                List<ShopModel> items = new Gson().fromJson(response.toString(), new TypeToken<List<ShopModel>>() {
                }.getType());

                list.clear();
                list.addAll(items);
                adapter.notifyDataSetChanged();




           /*     try {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                        JSONObject jsonObject = new JSONObject(String.valueOf(response));
//                        JSONArray jsonArray = new JSONArray(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("array");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            ShopModel model = new ShopModel();
                            JSONObject result = jsonArray.getJSONObject(i);
                            model.setTitle(result.getString("title"));
                            model.setImage(result.getString("image"));
                            model.setPrice(result.getString("price"));
                            list.add(model);
                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Shop Error", "onResponse: " + error);
            }
        });


        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        queue.add(jsonArrayRequest);


    }


    public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.MyViewHolder> {

        Context context;
        List<ShopModel> list;

        public ShopAdapter(Context context, List<ShopModel> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public ShopAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.shop_adapter_layout, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ShopAdapter.MyViewHolder holder, int position) {
            ShopModel model = list.get(position);

            holder.tv_shop_name.setText(model.getTitle());
            holder.tv_shop_price.setText(model.getPrice());
            Glide.with(context).
                    load(model.getImage()).
                    into(holder.image_shop);

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView image_shop;
            TextView tv_shop_name, tv_shop_price;


            public MyViewHolder(View itemView) {
                super(itemView);
                image_shop = itemView.findViewById(R.id.image_shop);
                tv_shop_name = itemView.findViewById(R.id.tv_shop_name);
                tv_shop_price = itemView.findViewById(R.id.tv_shop_price);

            }
        }
    }
}
