package com.foodlabrinth.darpal.demo5.Upload_Image;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridView;

import com.foodlabrinth.darpal.demo5.Adapter.ShowImageAdapter;
import com.foodlabrinth.darpal.demo5.ServiceHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ShowImagesActivity extends AppCompatActivity {

    GridView resimg;
    String URL;
    String res_id;
    String resid, image;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(com.foodlabrinth.darpal.demo5.R.layout.activity_show_images);

        resimg = (GridView) findViewById(com.foodlabrinth.darpal.demo5.R.id.resimg);
        Bundle intent = getIntent().getExtras();
        res_id = intent.getString("Res_id");
        Log.e("ShowActivity", res_id);
        URL = "http://foodlabrinth.com/getImages.php?Res_id=" + res_id;

        new Getdata().execute();

    }

    class Getdata extends AsyncTask<Void, Void, Void> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(ShowImagesActivity.this);
            pd.setMessage("Please Wait...");
            pd.show();
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
                        resid = jsonObject.getString("Res_id");
                        image = jsonObject.getString("image");

                        HashMap<String, String> hm = new HashMap<>();
                        hm.put("Res_id", resid);
                        hm.put("image", image);


                        arrayList.add(hm);
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
            ShowImageAdapter imageAdapter = new ShowImageAdapter(ShowImagesActivity.this,arrayList);
            resimg.setAdapter(imageAdapter);
        }
    }
}
