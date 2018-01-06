package com.foodlabrinth.darpal.demo5.Login_Signup;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.foodlabrinth.darpal.demo5.AccountFragment;
import com.foodlabrinth.darpal.demo5.Bookmark_restaurant.BookmarkRestaurants;
import com.foodlabrinth.darpal.demo5.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfileFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener {


    GoogleApiClient googleApiClient;
    boolean mSignInClicked;
    public Button share, feedback, about, rate, logout;
    CardView bookmark;
//    private GoogleApiClient mGoogleApiClient;
//    private ProgressDialog mProgressDialog;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        // Inflate the layout for this fragment
        View v = inflater.inflate(com.foodlabrinth.darpal.demo5.R.layout.fragment_user_profile, container, false);
        final SharedPreferences pref = getActivity().getSharedPreferences("userpref", Context.MODE_PRIVATE);

        AccountFragment.tabLayout.setVisibility(View.GONE);
        String s1 = pref.getString("email", null);
        TextView tv = (TextView) v.findViewById(com.foodlabrinth.darpal.demo5.R.id.tvuname);
        tv.setText("Welcome, " + s1);

       /* GoogleSignInOptions googleSignInOptions = new
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build(
        );
        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();*/



        logout = (Button) v.findViewById(com.foodlabrinth.darpal.demo5.R.id.btnLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();

                pref.edit().clear().commit();
                AccountFragment.tabLayout.setVisibility(View.VISIBLE);
                UserLoginFragment userLoginFragment = new UserLoginFragment();
                getFragmentManager().beginTransaction().replace(R.id.loginlayout, userLoginFragment).commit();

                /*Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                // ...
                                Toast.makeText(getApplicationContext(),"Logged Out",Toast.LENGTH_SHORT).show();
                                AccountFragment.tabLayout.setVisibility(View.VISIBLE);
                                UserLoginFragment profile = new UserLoginFragment();
                                getFragmentManager().beginTransaction().replace(com.foodlabrinth.darpal.demo5.R.id.loginlayout, profile).commit();
                            }
                        });*/


            }

        });

        share = (Button) v.findViewById(com.foodlabrinth.darpal.demo5.R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareIt();
            }
        });

        feedback = (Button) v.findViewById(com.foodlabrinth.darpal.demo5.R.id.feedback);
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedbackIt();
            }
        });

        bookmark = (CardView) v.findViewById(com.foodlabrinth.darpal.demo5.R.id.bookmarksuser);
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BookmarkRestaurants.class);
                startActivity(intent);

            }
        });

        return v;
    }



    private void shareIt(){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Food Labrinth coming soon!! ;)");
        startActivity(Intent.createChooser(shareIntent, "Share link using..."));
    }

    private void feedbackIt(){
        final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/email");
        intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ getString(com.foodlabrinth.darpal.demo5.R.string.mail_feedback_email) });
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(com.foodlabrinth.darpal.demo5.R.string.mail_feedback_subject));
        intent.putExtra(android.content.Intent.EXTRA_TEXT, getString(com.foodlabrinth.darpal.demo5.R.string.mail_feedback_message));
        startActivity(Intent.createChooser(intent, getString(com.foodlabrinth.darpal.demo5.R.string.title_send_feedback)));
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("Google Logout Failed", "onConnectionFailed:" + connectionResult);
    }

    /*public void signout(){
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        AccountFragment.tabLayout.setVisibility(View.VISIBLE);
                        UserLoginFragment userLoginFragment = new UserLoginFragment();
                        getFragmentManager().beginTransaction().replace(R.id.loginlayout, userLoginFragment).commit();
                    }
                });
    }*/

}
