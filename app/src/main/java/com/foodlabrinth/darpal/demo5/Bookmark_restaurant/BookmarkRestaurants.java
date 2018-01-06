package com.foodlabrinth.darpal.demo5.Bookmark_restaurant;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.foodlabrinth.darpal.demo5.R;
import com.foodlabrinth.darpal.demo5.ServiceHandler;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import static android.R.attr.id;

public class BookmarkRestaurants extends AppCompatActivity {

    TextView resName, resAddress, cuisineName, costValue, timeDisplay, contactNo, email;
    TextView RestaurantName, RestaurantAddress, Cuisine, Ambience, Cost;


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
    String AVGRATE = "AVG(r.Rating)";

    String URL2;
    String resid, uid;
    String stars;
    String reviews;
    static String rating;


    ArrayList<Item> arrayList;

    ListView theListView;
    FoldingCellListAdapter foldingCellListAdapter;

    String URLDATA;
    String Bookmark_Status;
    ShineButton shineButton;
    private TextView tvLabelName;
    String bookmarkUrl;
    boolean status = true;
    private String titleText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark_restaurants);

        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        SharedPreferences pref = getSharedPreferences("userpref", Context.MODE_PRIVATE);
        uid = pref.getString("uid", null);

        URLDATA = "http://foodlabrinth.com/bookmark_fetch.php?Uid=" + uid;

        arrayList = new ArrayList<>();
        theListView = (ListView) findViewById(R.id.mainListView);

        URLDATA = URLDATA.replaceAll(" ", "%20");
        URLDATA = URLDATA.replaceAll("\n", "%20");
        try {
            URL source = new URL(URLDATA);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        new Getdata().execute();

        resName = (TextView) findViewById(R.id.restaurant_name);
        resAddress = (TextView) findViewById(R.id.restaurant_address);
        cuisineName = (TextView) findViewById(R.id.cuisine_name);
        costValue = (TextView) findViewById(R.id.cost_value);
        timeDisplay = (TextView) findViewById(R.id.content_delivery_time);
        contactNo = (TextView) findViewById(R.id.contact_display_one);
        email = (TextView) findViewById(R.id.email_display);

        RestaurantName = (TextView) findViewById(R.id.resDispName);
        RestaurantAddress = (TextView) findViewById(R.id.title_to_address);
        Cuisine = (TextView) findViewById(R.id.cuisineDisp);
        Ambience = (TextView) findViewById(R.id.ambience_title);
        Cost = (TextView) findViewById(R.id.title_pledge);

        arrayList = Item.getTestingList();


        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    class Getdata extends AsyncTask<Void, Void, Void> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(BookmarkRestaurants.this);
            pd.setMessage("Please Wait...");
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                ServiceHandler sh = new ServiceHandler();
                String result = sh.GetHTTPData(URLDATA);
                if (result != null) {
                    Log.e("bookmark result", result);
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        resid = jsonObject.getString(RESID);
                        String avgrate = jsonObject.getString(AVGRATE);
                        String resname = jsonObject.getString(RESNAME);
                        String RES_NAME = jsonObject.getString(RESNAME);
                        String resadd = jsonObject.getString(RESADDRESS);
                        String RES_ADD = jsonObject.getString(RESADDRESS);
                        String cusin = jsonObject.getString(RESCUISINE);
                        String cost = jsonObject.getString(RESCOST);
                        String ambiab = jsonObject.getString(RESAMBIENCE);
                        String contact = jsonObject.getString(RESCONTACT);
                        String email = jsonObject.getString(RESEMAIL);
                        String time = jsonObject.getString(RESTIME);

                        HashMap<String, String> hm = new HashMap<>();
                        hm.put(RESID, resid);
                        hm.put(RESNAME, resname);
                        hm.put(RESADDRESS, resadd);
                        hm.put(AVGRATE, avgrate);
                        hm.put(RESCUISINE, cusin);
                        hm.put(RESCOST, cost);
                        hm.put(RESAMBIENCE, ambiab);
                        hm.put(RESCONTACT, contact);
                        hm.put(RESEMAIL, email);
                        hm.put(RESTIME, time);
                        hm.put("rate", rating);

                        Item item = new Item();
                        item.setResid(resid);
                        item.setFromAddress(resname);
                        item.setToAddress(resadd);
                        item.setCuisine(cusin);
                        item.setPledgePrice(cost);
                        item.setAmbience(ambiab);
                        item.setContact(contact);
                        item.setEmail(email);
                        item.setDate(time);
                        item.setAvgrate(avgrate);

                        arrayList.add(item);
                        Log.e("Bookmark array", String.valueOf(arrayList));
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
            //datasend();
            //rating = String.valueOf(3);
            foldingCellListAdapter = new FoldingCellListAdapter(BookmarkRestaurants.this, arrayList, rating);
            theListView.setAdapter(foldingCellListAdapter);

            SharedPreferences pref = getSharedPreferences("mypref", Context.MODE_PRIVATE);
            boolean b = pref.getBoolean("login", false);
            foldingCellListAdapter.setDefaultRequestBtnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int p = FoldingCellListAdapter.id1;
                    Log.e("p", String.valueOf(p));
                    Item item = arrayList.get(p);
                    resid = item.getResid();
                    Log.e("Res_id---review", resid);
                    AlertDialog.Builder alert = new AlertDialog.Builder(BookmarkRestaurants.this);
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    View view = inflater.inflate(R.layout.restaurant_rating, (ViewGroup) v.findViewById(R.id.root));
                    final EditText reviewet = (EditText) view.findViewById(R.id.review);
                    RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);

                    ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                        @Override
                        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                            stars = String.valueOf(rating);
                        }
                    });
                    alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            reviews = reviewet.getText().toString();

                            try {

                                String url = "http://foodlabrinth.com/review.php?Review=" + reviews + "&Res_id=" + resid + "&Rating=" + stars + "&Uid=" + uid;

                                url = url.replaceAll(" ", "%20");
                                url = url.replaceAll("\n", "%20");
                                URL source = new URL(url);
                                Log.e("urll==", url);

                                OkHttpClient client = new OkHttpClient();
                                Request request = new Request.Builder().url(url).build();
                                client.newCall(request).execute();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(BookmarkRestaurants.this, "Review submitted ", Toast.LENGTH_SHORT).show();

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

                    Toast.makeText(BookmarkRestaurants.this, "DEFAULT HANDLER FOR ALL BUTTONS", Toast.LENGTH_SHORT).show();
                }


            });
        }
    }


    public boolean onOptionsItemSelected(MenuItem item) {

        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }
}
