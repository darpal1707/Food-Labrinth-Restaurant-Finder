package com.foodlabrinth.darpal.demo5.Login_Signup;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.foodlabrinth.darpal.demo5.AccountFragment;
import com.foodlabrinth.darpal.demo5.MainActivity;
import com.foodlabrinth.darpal.demo5.R;
import com.foodlabrinth.darpal.demo5.ServiceHandler;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.foodlabrinth.darpal.demo5.R.id.btnLogin;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserLoginFragment extends Fragment implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    CallbackManager callbackManager;
    private Button btnLinkToRegister;
    LoginButton loginButton;
    private Button login;
    private ProgressDialog mProgressDialog;
    EditText email, password;
    String uid;


    public UserLoginFragment() {
        // Required empty public constructor
    }

    String url;
    String EMAIL = "EmailId";
    String PASSWORD = "Password";
    String s1, s2, s3;
    String email1, password1, gmail1;
    String personName;

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 007;
    SignInButton signInButton;
    GoogleApiClient googleApiClient;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_login, container, false);
        callbackManager = CallbackManager.Factory.create();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(getActivity());


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        SharedPreferences pref = getActivity().getSharedPreferences("userpref", Context.MODE_PRIVATE);
        boolean b = pref.getBoolean("login", false);

        if (b) {
            AccountFragment.tabLayout.setVisibility(View.VISIBLE);
            UserProfileFragment profile = new UserProfileFragment();
            getFragmentManager().beginTransaction().replace(R.id.loginlayout, profile).commit();
        }

        /* else {
            AccountFragment.tabLayout.setVisibility(View.VISIBLE);
            ProfileFragment profile = new ProfileFragment();
            getFragmentManager().beginTransaction().replace(R.id.loginlayout, profile).commit();
        }*/

        /*signInButton = (SignInButton) view.findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(this);*/

        /*GoogleSignInOptions googleSignInOptions = new
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new
                GoogleApiClient.Builder(getContext()).enableAutoManage(getActivity(), this).addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions).build();
        signInButton.setSize(signInButton.SIZE_STANDARD);
        signInButton.setScopes(googleSignInOptions.getScopeArray());*/


        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code

                        AccountFragment.tabLayout.setVisibility(View.VISIBLE);
                        UserProfileFragment profile = new UserProfileFragment();
                        getFragmentManager().beginTransaction().replace(R.id.loginlayout, profile).commit();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });


        loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile, email"));
        loginButton.setFragment(this);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccountFragment.tabLayout.setVisibility(View.VISIBLE);
                UserProfileFragment profile = new UserProfileFragment();
                getFragmentManager().beginTransaction().replace(R.id.loginlayout, profile).commit();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });


        btnLinkToRegister = (Button) view.findViewById(R.id.btnLinkToRegisterScreen);
        login = (Button) view.findViewById(btnLogin);
        email = (EditText) view.findViewById(R.id.email);
        password = (EditText) view.findViewById(R.id.password);


        /*loginButton.setReadPermissions("email");
        loginButton.setFragment(this);*/


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().equals("") && password.getText().toString().equals("")) {
                    email.setError("This field cannot be blank");
                    password.setError("This field cannot be blank");
                    Toast.makeText(getActivity(), "Please Complete above required fields", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        s1 = email.getText().toString();
                        s2 = password.getText().toString();
                        url = "http://foodlabrinth.com/user_login.php?EmailId=" + s1 + "&Password=" + s2;
                        //URL sourceUrl = new URL(url);
                        new Checkdata().execute();
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Email / Password is incorrect.", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }

            }
        });


        // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getActivity(),UserLoginRegistrationFragment.class);
                //startActivity(intent);
                UserLoginRegistrationFragment userLoginRegistrationFragment = new UserLoginRegistrationFragment();
                getFragmentManager().beginTransaction().replace(R.id.loginlayout, userLoginRegistrationFragment).addToBackStack(null).commit();

            }
        });

        //isOnline();
        return view;

    }

    @Override
    public void onClick(View v) {
        /*int id = v.getId();
        switch (id) {
            case R.id.sign_in_button:
                Signin();
                break;
        }*/
    }

    private void Signin() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        //Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    /*public boolean isOnline() {
        ConnectivityManager connec =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
            //Toast.makeText(getActivity(), " Connected ", Toast.LENGTH_LONG).show();
            return true;
        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
            //Toast.makeText(getActivity(), " Not Connected to the Internet ", Toast.LENGTH_LONG).show();
            Snackbar.make(getActivity().findViewById(R.id.container), "Not Connected to Internet", Snackbar.LENGTH_LONG)
                    .show();
            return false;
        }

        return connec.getActiveNetworkInfo() != null &&
                connec.getActiveNetworkInfo().isConnectedOrConnecting();
    }*/


    class Checkdata extends AsyncTask<Void, Void, Void> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            //pd.setTitle("Lading Data");
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
                    email1 = jsonObject.getString(EMAIL);
                    password1 = jsonObject.getString(PASSWORD);
                    uid = jsonObject.getString("Uid");

                }
            } catch (Exception e) {

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pd.dismiss();
            try {
                if (email1.equals(s1) && password1.equals(s2)) {

                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(url).build();
                    Response response = client.newCall(request).execute();

                    //Session code
                    SharedPreferences pref = getActivity().getSharedPreferences("userpref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor ed = pref.edit();
                    ed.putString("email", s1);
                    ed.putString("password", s2);
                    ed.putString("uid", uid);
                    ed.putBoolean("login", true);
                    ed.apply();
                    ed.commit();
                    AccountFragment.tabLayout.setVisibility(View.GONE);
                    UserProfileFragment profile = new UserProfileFragment();
                    getFragmentManager().beginTransaction().replace(R.id.loginlayout, profile).commit();

                }
            } catch (Exception e) {
                //isOnline();
                //Toast.makeText(getActivity(), "Please Enter Valid Data", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result =
                    Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handlesSigninResult(result);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }*/
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handlesSigninResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            Log.e(TAG, "display name: " + acct.getDisplayName());

            AccountFragment.tabLayout.setVisibility(View.GONE);
            UserProfileFragment userLoginFragment = new UserProfileFragment();
            getFragmentManager().beginTransaction().replace(com.foodlabrinth.darpal.demo5.R.id.loginlayout, userLoginFragment).commit();

            //upDateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            //upDateUI(false);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        /*OptionalPendingResult<GoogleSignInResult> opr =
                Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if (opr.isDone()) {
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handlesSigninResult(result);
        } else {

            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    handlesSigninResult(googleSignInResult);
                }
            });
        }*/
    }

}
