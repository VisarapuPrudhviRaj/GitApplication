package pr.bluefrog.gitapplication.bottomnavigationview.retrofit;

import retrofit.RestAdapter;

public class Api {
public  static String testUrl = "https://reqres.in/";
    public static ApiInterface getClient() {

        // change your base URL
/*
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint("https://mis.wdcw.ap.gov.in/SupplyChain/Api/AWMS/") //Set the BASE URL
                .build(); //Finally building the adapter
*/
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(testUrl) //Set the BASE URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        ApiInterface api = adapter.create(ApiInterface.class);
        return api; // return the APIInterface object
    }
}
