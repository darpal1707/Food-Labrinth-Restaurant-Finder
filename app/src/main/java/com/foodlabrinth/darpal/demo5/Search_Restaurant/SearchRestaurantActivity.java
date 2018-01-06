package com.foodlabrinth.darpal.demo5.Search_Restaurant;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.foodlabrinth.darpal.demo5.ServiceHandler;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class SearchRestaurantActivity extends AppCompatActivity {

    TextView resName, resAddress, cuisineName, costValue, timeDisplay, contactNo, email;
    TextView RestaurantName, RestaurantAddress, Cuisine, Ambience, Cost, rate;

    String searchtext;
    String URL, URL2;
    ArrayList<Item> arrayList;
    ArrayList<Item> arrList;
    ListView theListView;
    FoldingCellListAdapter foldingCellListAdapter;


    String RESNAME = "RestaurantName";
    String RESADDRESS = "Address";
    String RESCONTACT = "ContactNo";
    String RESEMAIL = "EmailId";
    String RESCOST = "Cost";
    String RESCUISINE = "Cuisine";
    String RESAMBIENCE = "Ambience";
    String RESMEAL = "Meal";
    String RESTIME = "OpenTill";
    String RESID = "Res_id";
    String resid, uid;
    String AVGRATE = "AVG(Rating)";
    String avgrate;


    ShineButton shineButton;
    String stars;
    String reviews;
    ArrayList<Item> arrayListrating;
    static String rating;
    SharedPreferences preferences;
    SharedPreferences.Editor ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.foodlabrinth.darpal.demo5.R.layout.activity_search_restaurant);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        arrayList = new ArrayList<>();
        arrList = new ArrayList<>();
        arrayListrating = new ArrayList<>();
        theListView = (ListView) findViewById(com.foodlabrinth.darpal.demo5.R.id.mainListView);

        preferences = getSharedPreferences("rat", Context.MODE_PRIVATE);
        ed = preferences.edit();

        SharedPreferences pref = getSharedPreferences("userpref", Context.MODE_PRIVATE);
        uid = pref.getString("uid", null);


        Bundle intent = getIntent().getExtras();
        searchtext = intent.getString("searchtext");
        URL = "http://foodlabrinth.com/search_res.php?RestaurantName=" + searchtext;
        URL2 = "http://foodlabrinth.com/rating_average.php?Res_id=";
        new Getdata().execute();

        //new GetAvg().execute();


        resName = (TextView) findViewById(com.foodlabrinth.darpal.demo5.R.id.restaurant_name);
        resAddress = (TextView) findViewById(com.foodlabrinth.darpal.demo5.R.id.restaurant_address);
        cuisineName = (TextView) findViewById(com.foodlabrinth.darpal.demo5.R.id.cuisine_name);
        costValue = (TextView) findViewById(com.foodlabrinth.darpal.demo5.R.id.cost_value);
        timeDisplay = (TextView) findViewById(com.foodlabrinth.darpal.demo5.R.id.content_delivery_time);
        contactNo = (TextView) findViewById(com.foodlabrinth.darpal.demo5.R.id.contact_display_one);
        email = (TextView) findViewById(com.foodlabrinth.darpal.demo5.R.id.email_display);

        RestaurantName = (TextView) findViewById(com.foodlabrinth.darpal.demo5.R.id.resDispName);
        RestaurantAddress = (TextView) findViewById(com.foodlabrinth.darpal.demo5.R.id.title_to_address);
        Cuisine = (TextView) findViewById(com.foodlabrinth.darpal.demo5.R.id.cuisineDisp);
        Ambience = (TextView) findViewById(com.foodlabrinth.darpal.demo5.R.id.ambience_title);
        Cost = (TextView) findViewById(com.foodlabrinth.darpal.demo5.R.id.title_pledge);
        rate = (TextView) findViewById(com.foodlabrinth.darpal.demo5.R.id.title_price);

        arrayList = Item.getTestingList();


        /*theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                // toggle clicked cell state
                ((FoldingCell) view).toggle(false);
                // register in adapter that state for selected cell is toggled
                foldingCellListAdapter.registerToggle(pos);
            }
        });*/

    }

    class Getdata extends AsyncTask<Void, Void, Void> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(SearchRestaurantActivity.this);
            pd.setMessage("Please Wait...");
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                ServiceHandler sh = new ServiceHandler();
                String result = sh.GetHTTPData(URL);
                String result1 = sh.GetHTTPData(URL + resid);
                if (result != null) {
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        resid = jsonObject.getString(RESID);
                        String resname = jsonObject.getString(RESNAME);
                        String resadd = jsonObject.getString(RESADDRESS);
                        String cusin = jsonObject.getString(RESCUISINE);
                        String cost = jsonObject.getString(RESCOST);
                        String ambiab = jsonObject.getString(RESAMBIENCE);
                        String contact = jsonObject.getString(RESCONTACT);
                        String email = jsonObject.getString(RESEMAIL);
                        String time = jsonObject.getString(RESTIME);


                        HashMap<String, String> hm = new HashMap<>();
                        hm.put(RESNAME, resname);
                        hm.put(RESADDRESS, resadd);
                        hm.put(RESCUISINE, cusin);
                        hm.put(RESCOST, cost);
                        hm.put(RESAMBIENCE, ambiab);
                        hm.put(RESCONTACT, contact);
                        hm.put(RESEMAIL, email);
                        hm.put(RESTIME, time);
                        hm.put("rate", rating);


                        Item item = new Item();
                        item.setFromAddress(resname);
                        item.setToAddress(resadd);
                        item.setCuisine(cusin);
                        item.setPledgePrice(cost);
                        item.setAmbience(ambiab);
                        item.setContact(contact);
                        item.setEmail(email);
                        item.setDate(time);
                        item.setAvgrate(rating);
                        arrayList.add(item);

                    }
                } else {
                    Toast.makeText(SearchRestaurantActivity.this, "Please Check Your Internet", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.e("TAG", "NOTHING TO DISPLAY");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            new GetRating().execute();
            pd.dismiss();
            datasend();
            foldingCellListAdapter = new FoldingCellListAdapter(SearchRestaurantActivity.this, arrayList,rating);
            //Log.e("Rating", rating);
            theListView.setAdapter(foldingCellListAdapter);
            SharedPreferences pref = getSharedPreferences("mypref", Context.MODE_PRIVATE);
            boolean b = pref.getBoolean("login", false);

            if (b) {
                //foldingCellListAdapter.contentRequestBtn.setVisibility(View.GONE);
                foldingCellListAdapter.setDefaultRequestBtnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alert1 = new AlertDialog.Builder(SearchRestaurantActivity.this);
                        LayoutInflater inflater = getLayoutInflater();

                        View view = inflater.inflate(com.foodlabrinth.darpal.demo5.R.layout.cannot_rate, (ViewGroup) findViewById(com.foodlabrinth.darpal.demo5.R.id.root1));
                        alert1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alert1.setView(view);
                        alert1.show();
                    }
                });
            } else {
                foldingCellListAdapter.setDefaultRequestBtnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(getApplicationContext(), "DEFAULT HANDLER FOR ALL BUTTONS", Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder alert = new AlertDialog.Builder(SearchRestaurantActivity.this);
                        LayoutInflater inflater = getLayoutInflater();

                        View view = inflater.inflate(com.foodlabrinth.darpal.demo5.R.layout.restaurant_rating, (ViewGroup) findViewById(com.foodlabrinth.darpal.demo5.R.id.root));
                        final EditText reviewet = (EditText) view.findViewById(com.foodlabrinth.darpal.demo5.R.id.review);
                        RatingBar ratingBar = (RatingBar) view.findViewById(com.foodlabrinth.darpal.demo5.R.id.ratingBar);

                        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                            @Override
                            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                                stars = String.valueOf(rating);
                            }
                        });
                        alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                reviews = reviewet.getText().toString().trim();

                                try {

                                    String url = "http://foodlabrinth.com/review.php?Review=" + reviews + "&Res_id=" + resid + "&Rating=" + stars + "&Uid=" + uid;

                                    url = url.replaceAll(" ", "%20");
                                    url = url.replaceAll("\n", "%20");
                                    URL source = new URL(url);
                                    Log.e("urll==", url);

                                    OkHttpClient client = new OkHttpClient();
                                    Request request = new Request.Builder().url(url).build();
                                    Response response = client.newCall(request).execute();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(SearchRestaurantActivity.this, "Review submitted ", Toast.LENGTH_SHORT).show();

                            }
                        });

                        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alert.setView(view);
                        alert.show();


                    }
                });
            }
        }
    }

    class GetRating extends AsyncTask<Void, Void, Void> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(SearchRestaurantActivity.this);
            pd.setMessage("Please Wait...");
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                ServiceHandler sh = new ServiceHandler();
                String result = sh.GetHTTPData(URL2 + resid);
                if (result != null) {
                    try {
                        JSONArray jsonArray = new JSONArray(result);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            rating = jsonObject.getString("rate");
                            Log.e("rating==", rating);
                            //arrayListrating.add(rating);

                            FoldingCellListAdapter.RatingSend(rating);
                            FoldingCellListAdapter.Getvalue(rating);
                            ed.clear();
                            ed.putString("key", rating);
                            ed.apply();

                        }
                    } catch (Exception e) {

                    }
                }
            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pd.dismiss();
        }
    }

    public static String datasend() {
        return rating;
    }

}
