package com.foodlabrinth.darpal.demo5.Result_Page;

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

public class ResultPageActivity extends AppCompatActivity {

    String s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14;
    //String Finalquery = "https://foodlabrinth.000webhostapp.com/not_decided.php?Meal=" + meal1 + "&Cuisine=" + s1 + "&Cuisine=" + s2 + "&Cuisine=" + s3 + "&Cuisine=" + s4 + "&Cuisine=" + s5 + "&Cuisine=" + s6 + "&Cuisine=" + s7 + "&Cuisine=" + s8 + "&Cuisine=" + s9 + "&Cuisine=" + s10 + "&Cuisine=" + s11 + "&Cuisine=" + s12 + "&Cuisine=" + s13 + "&Ambience=" + ambianc1 + "&Cost=" + temp1;
    String Finalquery;
    static ArrayList<String> getdata;
    static String ambianc1;
    static String temp1;


    TextView resName, resAddress, cuisineName, costValue, timeDisplay, contactNo, email;
    TextView RestaurantName, RestaurantAddress, Cuisine, Ambience, Cost;

    String searchtext;
    String URL1, URL2;
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
    ArrayList<String> arrayListrating;
    String rating;
    SharedPreferences preferences;
    SharedPreferences.Editor ed;
    static String getdata1[];
    static String meal1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.foodlabrinth.darpal.demo5.R.layout.activity_result_page);
        theListView = (ListView) findViewById(com.foodlabrinth.darpal.demo5.R.id.mainListView);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        try {

            /*SharedPreferences mealcuisine = getSharedPreferences("Cuisines", MODE_PRIVATE);
            mealcuisine.getString("Meal", meal1);
            mealcuisine.getString("s1", s1);
            mealcuisine.getString("s2", s2);
            mealcuisine.getString("s3", s3);
            mealcuisine.getString("s4", s4);
            mealcuisine.getString("s5", s5);
            mealcuisine.getString("s6", s6);
            mealcuisine.getString("s7", s7);
            mealcuisine.getString("s8", s8);
            mealcuisine.getString("s9", s9);
            mealcuisine.getString("s10", s10);
            mealcuisine.getString("s11", s11);
            mealcuisine.getString("s12", s12);
            mealcuisine.getString("s13", s13);*/
            //mealcuisine.getString("s14", s14);

            arrayList = new ArrayList<>();
            arrList = new ArrayList<>();
            arrayListrating = new ArrayList<>();
            theListView = (ListView) findViewById(com.foodlabrinth.darpal.demo5.R.id.mainListView);

            preferences = getSharedPreferences("rat", Context.MODE_PRIVATE);
            ed = preferences.edit();


            SharedPreferences pref = getSharedPreferences("userpref", Context.MODE_PRIVATE);
            uid = pref.getString("uid", null);
            String data1 = null;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < getdata1.length; i++) {
                Log.e("Result ", getdata1[i]);
                sb.append("&Cuisine[]=").append(getdata1[i]);
            }
            data1 = sb.toString();
            Finalquery = "http://foodlabrinth.com/not_decided.php?Meal=" + meal1 + data1 + "&Ambience=" + ambianc1 + "&Cost=" + temp1;
            Finalquery = Finalquery.replaceAll(" ", "%20");
            Finalquery = Finalquery.replaceAll("\\[]", "[]");
            //Finalquery = Finalquery.replaceAll("\n", "%20");
            URL source = new URL(Finalquery);
            Log.e("URL", Finalquery);
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
        } catch (Exception e) {
            Toast.makeText(this, "at result" + e, Toast.LENGTH_SHORT).show();
        }
    }

    public static void Senddata(String ambianc, String temp, String data[], String meal) {
        //getdata = arrayList;
        meal1 = meal;
        ambianc1 = ambianc;
        temp1 = temp;
        getdata1 = data;
    }

    private class Getdata extends AsyncTask<Void, Void, Void> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(ResultPageActivity.this);
            pd.setMessage("Please Wait...");
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                ServiceHandler sh = new ServiceHandler();
                String result = sh.GetHTTPData(Finalquery);
                if (result != null) {

                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        resid = jsonObject.getString(RESID);
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
                        hm.put(RESNAME, resname);
                        hm.put(RESADDRESS, resadd);

                        hm.put(RESCUISINE, cusin);
                        hm.put(RESCOST, cost);
                        hm.put(RESAMBIENCE, ambiab);
                        hm.put(RESCONTACT, contact);
                        hm.put(RESEMAIL, email);
                        hm.put(RESTIME, time);

                        Item item = new Item();
                        item.setFromAddress(resname);
                        item.setToAddress(resadd);
                        item.setCuisine(cusin);
                        item.setPledgePrice(cost);
                        item.setAmbience(ambiab);
                        item.setContact(contact);
                        item.setEmail(email);
                        item.setDate(time);

                        arrayList.add(item);
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
            foldingCellListAdapter = new FoldingCellListAdapter(ResultPageActivity.this, arrayList);
            theListView.setAdapter(foldingCellListAdapter);
            SharedPreferences pref = getSharedPreferences("mypref", Context.MODE_PRIVATE);
            boolean b = pref.getBoolean("login", false);

            if (b) {
                //foldingCellListAdapter.contentRequestBtn.setVisibility(View.GONE);
                foldingCellListAdapter.setDefaultRequestBtnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alert1 = new AlertDialog.Builder(ResultPageActivity.this);
                        LayoutInflater inflater = getLayoutInflater();

                        View view = inflater.inflate(com.foodlabrinth.darpal.demo5.R.layout.cannot_rate, (ViewGroup) v.findViewById(com.foodlabrinth.darpal.demo5.R.id.root1));
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
                        AlertDialog.Builder alert = new AlertDialog.Builder(ResultPageActivity.this);
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
                                reviews = reviewet.getText().toString();

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
                                Toast.makeText(ResultPageActivity.this, "Review submitted ", Toast.LENGTH_SHORT).show();

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
}
