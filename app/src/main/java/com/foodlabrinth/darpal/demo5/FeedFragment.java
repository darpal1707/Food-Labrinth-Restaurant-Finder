package com.foodlabrinth.darpal.demo5;


import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class FeedFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    ListView lv;
    private TextView tvLabelName;
    String URL;
    ArrayList<HashMap<String ,String >> arrayList = new ArrayList();
    private String titleText ="";

    String RESNAME = "RestaurantName";
    String RESID = "Res_id";
    String UID = "Uid";
    String RID = "Rid";
    String RESADDRESS = "Address";
    String PERSON_NAME = "EmailId";
    String RESREVIEW = "Review";

    SwipeRefreshLayout swipeLayout;


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        URL = "http://foodlabrinth.com/review_fetch.php";

        lv = (ListView) view.findViewById(R.id.feedList);
        new Getdata().execute();

        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorScheme(
                android.R.color.holo_red_light);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            titleText =  getArguments().getString("Feed");
        }
        return view;

    }



    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onRefresh() {
        arrayList.clear();
        new Getdata1().execute();

    }

    class Getdata extends AsyncTask<Void, Void, Void> {

        ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(getActivity());
            pd.setMessage("Loading Feeds...");
            pd.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                ServiceHandler sh = new ServiceHandler();
                String result = sh.GetHTTPData(URL);
                if (result != null) {

                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String resid=jsonObject.getString(RESID);
                        String resname = jsonObject.getString(RESNAME);
                        String resadd = jsonObject.getString(RESADDRESS);
                        String review = jsonObject.getString(RESREVIEW);
                        String personName = jsonObject.getString(PERSON_NAME);

                        HashMap<String, String> hm = new HashMap<>();
                        hm.put(RESNAME, resname);
                        hm.put(RESADDRESS, resadd);
                        hm.put(RESREVIEW,review);
                        hm.put(PERSON_NAME,personName);

                        /*Item item = new Item();
                        item.setFromAddress(resname);
                        item.setToAddress(resadd);
*/
                        arrayList.add(hm);

                    }
                }
                else{
                    Toast.makeText(getActivity(), "Please Check Your Internet", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.e("TAG","NOTHING TO DISPLAY");
                isOnline();
            }
            return null;
        }

        public boolean isOnline() {
            ConnectivityManager connec =
                    (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

            if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                    connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                    connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                    connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {
                //Toast.makeText(getActivity(), " Connected ", Toast.LENGTH_LONG).show();
                return true;
            }
            else if (
                    connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                            connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {
                //Toast.makeText(getActivity(), " Not Connected to the Internet ", Toast.LENGTH_LONG).show();
                Snackbar.make(getActivity().findViewById(R.id.container), "Not Connected to Internet", Snackbar.LENGTH_LONG)
                        .show();
                return false;
            }

            return connec.getActiveNetworkInfo() != null &&
                    connec.getActiveNetworkInfo().isConnectedOrConnecting();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            pd.dismiss();
            //swipeLayout.setRefreshing(false);
            FeedAdapter adapter = new FeedAdapter(getActivity(),arrayList);
            lv.setAdapter(adapter);
        }

    }

    class Getdata1 extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                ServiceHandler sh = new ServiceHandler();
                String result = sh.GetHTTPData(URL);
                if (result != null) {

                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String resid=jsonObject.getString(RESID);
                        String resname = jsonObject.getString(RESNAME);
                        String resadd = jsonObject.getString(RESADDRESS);
                        String review = jsonObject.getString(RESREVIEW);
                        String personName = jsonObject.getString(PERSON_NAME);

                        HashMap<String, String> hm = new HashMap<>();
                        hm.put(RESNAME, resname);
                        hm.put(RESADDRESS, resadd);
                        hm.put(RESREVIEW,review);
                        hm.put(PERSON_NAME,personName);

                        /*Item item = new Item();
                        item.setFromAddress(resname);
                        item.setToAddress(resadd);
*/
                        arrayList.add(hm);

                    }
                }
                else{
                    Toast.makeText(getActivity(), "Please Check Your Internet", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.e("TAG","NOTHING TO DISPLAY");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            swipeLayout.setRefreshing(false);
            FeedAdapter adapter = new FeedAdapter(getActivity(),arrayList);
            lv.setAdapter(adapter);
        }
    }

}