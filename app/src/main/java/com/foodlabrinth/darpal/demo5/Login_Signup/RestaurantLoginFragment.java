package com.foodlabrinth.darpal.demo5.Login_Signup;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.foodlabrinth.darpal.demo5.AccountFragment;
import com.foodlabrinth.darpal.demo5.ServiceHandler;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;


public class RestaurantLoginFragment extends Fragment {

    Button btnLinkToRegister;
    Button login;
    EditText resname, email, password;

    String url;
    String EMAIL = "EmailId";
    String PASSWORD = "Password";
    String RNAME = "RestaurantName";
    String s1, s2, s3, s4;
    String email1,password1,rname1,resid1;
    String Res_id = "Res_id";

    public RestaurantLoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(com.foodlabrinth.darpal.demo5.R.layout.fragment_restaurant_login, container, false);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        SharedPreferences pref1 = getActivity().getSharedPreferences("userpref", Context.MODE_PRIVATE);
        boolean b1 = pref1.getBoolean("login", false);

        SharedPreferences pref = getActivity().getSharedPreferences("mypref", Context.MODE_PRIVATE);
        boolean b=pref.getBoolean("login",false);
        if(b)
        {
            AccountFragment.tabLayout.setVisibility(View.VISIBLE);
            ProfileFragment profile = new ProfileFragment();
            getFragmentManager().beginTransaction().replace(com.foodlabrinth.darpal.demo5.R.id.loginlayout, profile).commit();
        }


        btnLinkToRegister = (Button) view.findViewById(com.foodlabrinth.darpal.demo5.R.id.btnLinkToRegisterScreen);
        login = (Button) view.findViewById(com.foodlabrinth.darpal.demo5.R.id.btnLogin);
        resname = (EditText) view.findViewById(com.foodlabrinth.darpal.demo5.R.id.restaurantname);
        email = (EditText) view.findViewById(com.foodlabrinth.darpal.demo5.R.id.email);
        password = (EditText) view.findViewById(com.foodlabrinth.darpal.demo5.R.id.password);


        login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (resname.getText().toString().equals("") && email.getText().toString().equals("") && password.getText().toString().equals("")) {
                        resname.setError("This Field cannot be blank");
                        email.setError("This Field cannot be blank");
                        password.setError("This Field cannot be blank");
                        Toast.makeText(getActivity(), "Please Complete above required fields", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        try {
                             s1 = resname.getText().toString();
                             s2 = email.getText().toString();
                             s3 = password.getText().toString();
                            url = "http://foodlabrinth.com/restaurant_login.php?RestaurantName=" + s1 + "&EmailId=" + s2 + "&Password=" + s3;
                            url = url.replaceAll(" ", "%20");
                            new Checkdata().execute();

                        } catch (Exception e) {
                            Log.e("Error", String.valueOf(e));
                        }
                    }
                }
            });

            btnLinkToRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RestaurantLoginRegistrationFragment restaurantLoginRegistrationFragment = new RestaurantLoginRegistrationFragment();
                    getFragmentManager().beginTransaction().replace(com.foodlabrinth.darpal.demo5.R.id.loginlayout, restaurantLoginRegistrationFragment).addToBackStack(null).commit();
                }
            });

        isOnline();
        return view;
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
            Snackbar.make(getActivity().findViewById(com.foodlabrinth.darpal.demo5.R.id.container), "Not Connected To Internet", Snackbar.LENGTH_LONG)
                    .show();
            //Toast.makeText(getActivity(), " Not Connected to the Internet ", Toast.LENGTH_LONG).show();
            return false;
        }

        return connec.getActiveNetworkInfo() != null &&
                connec.getActiveNetworkInfo().isConnectedOrConnecting();
    }




    class Checkdata extends AsyncTask<Void, Void, Void> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            //pd.setTitle("Loading Data");
            pd.setMessage("Please wait");
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            ServiceHandler serviceHandler = new ServiceHandler();
            String result = serviceHandler.GetHTTPData(url);
            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    resid1 = jsonObject.getString(Res_id);
                    rname1 = jsonObject.getString(RNAME);
                    email1 = jsonObject.getString(EMAIL);
                    password1 = jsonObject.getString(PASSWORD);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pd.dismiss();
            try {
                if (rname1.equalsIgnoreCase(s1) && email1.equals(s2) && password1.equals(s3)) {

                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(url).build();
                    Response response = client.newCall(request).execute();

                    //Session code
                    SharedPreferences pref = getActivity().getSharedPreferences("mypref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor ed = pref.edit();
                    ed.putString("Res_id", resid1);
                    ed.putString("rname", s1);
                    ed.putString("email", s2);
                    ed.putString("password", s3);
                    ed.putBoolean("login", true);
                    ed.apply();
                    ed.commit();
                    Log.e("Res_id =",resid1);
                    AccountFragment.tabLayout.setVisibility(View.GONE);
                    //Toast.makeText(getActivity(), response.body().toString(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();

                    ProfileFragment profile = new ProfileFragment();
                    getFragmentManager().beginTransaction().replace(com.foodlabrinth.darpal.demo5.R.id.loginlayout, profile).commit();
                }
            } catch (Exception e) {
                isOnline();
                //Toast.makeText(getActivity(), "Please Enter Valid Data", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
