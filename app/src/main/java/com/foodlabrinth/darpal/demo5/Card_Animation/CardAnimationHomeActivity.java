package com.foodlabrinth.darpal.demo5.Card_Animation;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class CardAnimationHomeActivity extends AppCompatActivity {

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


    String URLDATA, URL2;

    String resid, uid;
    String stars;
    String reviews;
    static String rating;

    FoldingCellListAdapter foldingCellListAdapter;
    ArrayList<Item> arrayList;
    ListView theListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(com.foodlabrinth.darpal.demo5.R.layout.activity_card_animation_home);

        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        theListView = (ListView) findViewById(com.foodlabrinth.darpal.demo5.R.id.mainListView);

        Intent intent = getIntent();
        URLDATA = intent.getStringExtra("URL");
        Log.e("URL---", URLDATA);
        new Getdata().execute();

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

        arrayList = Item.getTestingList();

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    class Getdata extends AsyncTask<Void, Void, Void> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e("in pree==", "in pree");
            pd = new ProgressDialog(CardAnimationHomeActivity.this);
            pd.setMessage("Please Wait...");
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                ServiceHandler sh = new ServiceHandler();
                String result = sh.GetHTTPData(URLDATA);
                if (result != null) {
                    Log.e("browse result",result);
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
                        Log.e("Brose array", String.valueOf(arrayList));
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
            foldingCellListAdapter = new FoldingCellListAdapter(CardAnimationHomeActivity.this, arrayList, rating);
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
                    AlertDialog.Builder alert = new AlertDialog.Builder(CardAnimationHomeActivity.this);
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    View view = inflater.inflate(com.foodlabrinth.darpal.demo5.R.layout.restaurant_rating, (ViewGroup) v.findViewById(com.foodlabrinth.darpal.demo5.R.id.root));
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
                            Toast.makeText(CardAnimationHomeActivity.this, "Review submitted ", Toast.LENGTH_SHORT).show();

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

                    Toast.makeText(CardAnimationHomeActivity.this, "DEFAULT HANDLER FOR ALL BUTTONS", Toast.LENGTH_SHORT).show();
                }


            });
        }
    }
}

