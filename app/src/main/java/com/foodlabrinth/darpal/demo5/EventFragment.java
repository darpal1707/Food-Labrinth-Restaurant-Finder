package com.foodlabrinth.darpal.demo5;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.foodlabrinth.darpal.demo5.Collection_Restaurant.FoldingCellListAdapter;
import com.foodlabrinth.darpal.demo5.Collection_Restaurant.Item;
import com.javier.filterview.FilterView;
import com.javier.filterview.OnFilterViewResultListener;
import com.javier.filterview.extra.ExtraSection;
import com.javier.filterview.extra.cboolean.ExtraBoolean;
import com.javier.filterview.extra.cboolean.OnBooleanCheckedChangeListener;
import com.javier.filterview.extra.clist.ExtraList;
import com.javier.filterview.extra.clist.TypeList;
import com.javier.filterview.single.OnSingleOptionListener;
import com.javier.filterview.single.SingleOption;
import com.javier.filterview.single.SingleSection;
import com.javier.filterview.tag.TagGravity;
import com.javier.filterview.tag.TagMode;
import com.javier.filterview.tag.TagSection;
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


public class EventFragment extends Fragment {

    Button filter;
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

    String resid, uid;
    String stars;
    String reviews;
    static String rating;

    ArrayList<Item> arrayList;

    ListView theListView;
    FoldingCellListAdapter foldingCellListAdapter;

    String URLDATA, URL2;
    ShineButton shineButton;
    private TextView tvLabelName;
    String bookmarkUrl;
    String status;
    private String titleText = "";

    //SharedPreferences preferences;
    SharedPreferences.Editor ed;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        View view = inflater.inflate(R.layout.fragment_event, container, false);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            titleText = getArguments().getString("Collection");
        }

        SharedPreferences pref = getActivity().getSharedPreferences("userpref", Context.MODE_PRIVATE);
        uid = pref.getString("uid", null);
        URLDATA = "http://foodlabrinth.com/collection.php";
        URL2 = "http://foodlabrinth.com/rating_average.php?Res_id=";

        arrayList = new ArrayList<>();
        theListView = (ListView) view.findViewById(R.id.mainListView);

        URLDATA = URLDATA.replaceAll(" ", "%20");
        URLDATA = URLDATA.replaceAll("\n", "%20");
        try {
            URL source = new URL(URLDATA);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        new Getdata().execute();

        /*filter = (Button) view.findViewById(R.id.btShowFilterView);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilterView();
            }
        });*/

        resName = (TextView) view.findViewById(R.id.restaurant_name);
        resAddress = (TextView) view.findViewById(R.id.restaurant_address);
        cuisineName = (TextView) view.findViewById(R.id.cuisine_name);
        costValue = (TextView) view.findViewById(R.id.cost_value);
        timeDisplay = (TextView) view.findViewById(R.id.content_delivery_time);
        contactNo = (TextView) view.findViewById(R.id.contact_display_one);
        email = (TextView) view.findViewById(R.id.email_display);


        RestaurantName = (TextView) view.findViewById(R.id.resDispName);
        RestaurantAddress = (TextView) view.findViewById(R.id.title_to_address);
        Cuisine = (TextView) view.findViewById(R.id.cuisineDisp);
        Ambience = (TextView) view.findViewById(R.id.ambience_title);
        Cost = (TextView) view.findViewById(R.id.title_pledge);
        /*Button photos = (Button) view.findViewById(R.id.photosActivity);


        photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShowImagesActivity.class);
                startActivity(intent);
            }
        });*/

        arrayList = Item.getTestingList();



        return view;

    }

    public void showFilterView() {
        // String title, int titleColor, int icon, int borderWidth, int borderColor
        SingleSection singleSection = new SingleSection.Builder("Average Cost", 1)
                .setSectionNameColor(R.color.colorAccent)
                .addOption(new SingleOption("Low Budget (0-500)", R.color.colorAccent,
                        R.drawable.ic_currency_inr_black_24dp, R.color.colorBackground,
                        R.color.colorAccent, 1, R.color.colorAccent))
                .addOption(new SingleOption("Medium Budget (500-1500)", R.color.colorAccent,
                        R.drawable.ic_currency_inr_black_24dp, R.color.colorBackground,
                        R.color.colorAccent, 2, R.color.colorAccent))
                .addOption(new SingleOption("High Budget (1500+)", R.color.colorAccent,
                        R.drawable.ic_currency_inr_black_24dp, R.color.colorBackground,
                        R.color.colorAccent, 3, R.color.colorAccent))

                .build().setOnSingleOptionListener(new OnSingleOptionListener() {
                    @Override
                    public void onClick(SingleOption option) {

                    }
                });

        String[] data = {"Open Now", "Buffet", "Desserts and Bakes", "Luxury Dining", "Outdoor Seating", "Pure Veg", "Non Veg"};

        TagSection tagSection = new TagSection.Builder("Sort By", 4)
                .setSectionNameColor(R.color.colorAccent)
                .setSelectedColor(R.color.colorAccent)
                .setDeselectedColor(R.color.colorBackground)
                .setSelectedFontColor(R.color.colorBackground)
                .setDeselectedFontColor(R.color.colorAccent)
                .setGravity(TagGravity.CENTER)
                .setMode(TagMode.MULTI)
                .setLabels(data)
                .build();


        final ExtraBoolean extraBoolean1 = new ExtraBoolean(1, "Bookmarked", R.color.colorAccent, R.color.colorCyan);
        extraBoolean1.setOnBooleanCheckedChangeListener(new OnBooleanCheckedChangeListener() {
            @Override
            public void onChecked(boolean isChecked) {
                System.out.println(extraBoolean1.getTitle());
                System.out.println(isChecked);
            }
        });

        final ExtraBoolean extraBoolean2 = new ExtraBoolean(2, "Rated 3.5+", R.color.colorAccent, R.color.colorCyan);
        extraBoolean2.setOnBooleanCheckedChangeListener(new OnBooleanCheckedChangeListener() {
            @Override
            public void onChecked(boolean isChecked) {
                System.out.println(extraBoolean2.getTitle());
                System.out.println(isChecked);
            }
        });

        final ExtraBoolean extraBoolean3 = new ExtraBoolean(3, "Open Now", R.color.colorAccent, R.color.colorCyan);
        extraBoolean3.setOnBooleanCheckedChangeListener(new OnBooleanCheckedChangeListener() {
            @Override
            public void onChecked(boolean isChecked) {
                System.out.println(extraBoolean3.getTitle());
                System.out.println(isChecked);
            }
        });


        String[] dataOpt = {"Chinese", "Italian", "Asian", "Street Food", "Lebanese 5", "Mediterranean", "Indian", "Punjabi", "South Indian", "Gujarati", "Mexican", "Thai", "European"};
        ExtraList extraList = new ExtraList("Cuisines", R.color.colorAccent, dataOpt, TypeList.MULTI_CHOICE);

        String[] dataOptamb = {"Luxury", "Romantic", "RoofTop", "Hangout", "Unique Dining", "Street/Open Area", "Late Night Meals", "Buffet/Thali", "Live Sport Screenings"};
        ExtraList extraListamb = new ExtraList("Ambience", R.color.colorAccent, dataOptamb, TypeList.MULTI_CHOICE);

        ExtraSection extraSection = new ExtraSection.Builder("More Filters", 5)
                .setSectionNameColor(R.color.colorAccent)
                .addOption(extraBoolean1)
                .addOption(extraBoolean2)
                .addOption(extraBoolean3)
                //.addOption(extraText)
                //.addOption(extraText2)
                //.addOption(currencyEditText)
                //.addOption(date)
                //.addOption(extraHMS)
                .addOption(extraList)
                .addOption(extraListamb)
                .build();


        new FilterView.Builder(getActivity())
                .withTitle("Apply")
                .setToolbarVisible(true)
                .withTitleColor(R.color.colorAccent)
                .withDivisorColor(R.color.colorAccent)
                .setCloseIconColor(R.color.colorAccent)
                .addSection(singleSection)
                //.addSection(sliderSectionRange)
                .addSection(tagSection)
                .addSection(extraSection)
                .build()
                .setOnFilterViewResultListener(new OnFilterViewResultListener() {
                    @Override
                    public void onResult(JSONArray data) {
                        System.out.println(data.toString());
                    }
                }).show();

    }

    class Getdata extends AsyncTask<Void, Void, Void> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd.setMessage("Please Wait...");
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                ServiceHandler sh = new ServiceHandler();
                String result = sh.GetHTTPData(URLDATA);
                if (result != null) {

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
                    }
                }
            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(final Void aVoid) {
            super.onPostExecute(aVoid);
            //new GetRating().execute();
            pd.dismiss();
            //datasend();
            foldingCellListAdapter = new FoldingCellListAdapter(getActivity(), arrayList, rating);
            theListView.setAdapter(foldingCellListAdapter);

            SharedPreferences pref = getActivity().getSharedPreferences("mypref", Context.MODE_PRIVATE);
            boolean b = pref.getBoolean("login", false);
            foldingCellListAdapter.setDefaultRequestBtnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int p=FoldingCellListAdapter.id1;
                    Log.e("p", String.valueOf(p));
                    Item item=arrayList.get(p);
                    resid=item.getResid();
                    Log.e("Res_id---review",resid);
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
                            Toast.makeText(getActivity(), "Review submitted ", Toast.LENGTH_SHORT).show();

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

                    Toast.makeText(getActivity(),"DEFAULT HANDLER FOR ALL BUTTONS", Toast.LENGTH_SHORT).show();
                }


            });
        }
    }


    /*class GetRating extends AsyncTask<Void, Void, Void> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
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
    }*/


    @Override
    public void onStart() {
        super.onStart();

    }
}
