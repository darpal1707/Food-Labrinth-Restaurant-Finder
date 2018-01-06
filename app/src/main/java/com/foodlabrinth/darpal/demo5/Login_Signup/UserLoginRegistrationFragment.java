package com.foodlabrinth.darpal.demo5.Login_Signup;


import android.content.Context;
import android.net.ConnectivityManager;
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

import com.foodlabrinth.darpal.demo5.R;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserLoginRegistrationFragment extends Fragment {

    private Button btnLinkToLogin;
    Button register;
    EditText firstname, lastname,email,password,contact;

    public UserLoginRegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_login_registration, container, false);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        firstname = (EditText) view.findViewById(R.id.firstname);
        lastname = (EditText) view.findViewById(R.id.lastname);
        email = (EditText) view.findViewById(R.id.email);
        password = (EditText) view.findViewById(R.id.password);
        contact = (EditText) view.findViewById(R.id.mobileno);
        btnLinkToLogin = (Button)view.findViewById(R.id.btnLinkToLoginScreen);

        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserLoginFragment userLoginFragment = new UserLoginFragment();
                getFragmentManager().beginTransaction().replace(R.id.loginlayout, userLoginFragment).commit();
            }
        });

        register = (Button) view.findViewById(R.id.btnRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String s1 = firstname.getText().toString();
                    String s2 = lastname.getText().toString();
                    String s3 = email.getText().toString();
                    String s4 = password.getText().toString();
                    String s5 = contact.getText().toString();

                    String url="http://foodlabrinth.com/user_registration.php?FirstName="+s1+
                    "&LastName="+s2+"&EmailId="+s3+"&Password="+s4+"&MobileNo="+s5+ "";

                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(url).build();
                    Response response = client.newCall(request).execute();
                    //Toast.makeText(getActivity(), response.body().toString(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(), "Registered Successfully", Toast.LENGTH_SHORT).show();

                }
                catch (Exception e){
                    isOnline();
                    Log.i("Error",e.toString());
                }

            }
        });

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
            Snackbar.make(getActivity().findViewById(R.id.container), "Not Connected To Internet", Snackbar.LENGTH_LONG)
                    .show();
            return false;
        }

        return connec.getActiveNetworkInfo() != null &&
                connec.getActiveNetworkInfo().isConnectedOrConnecting();
    }


}
