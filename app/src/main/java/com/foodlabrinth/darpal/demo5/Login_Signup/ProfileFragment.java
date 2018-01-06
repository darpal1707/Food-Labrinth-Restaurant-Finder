package com.foodlabrinth.darpal.demo5.Login_Signup;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.foodlabrinth.darpal.demo5.AccountFragment;
import com.foodlabrinth.darpal.demo5.Upload_Image.GalleryIntent;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    Button rate, feedback, about, reslogout;
    CardView upload,bookmark;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(com.foodlabrinth.darpal.demo5.R.layout.fragment_profile, container, false);
        final SharedPreferences pref = getActivity().getSharedPreferences("mypref", Context.MODE_PRIVATE);
        //pref.edit().clear().commit();             logout code
        AccountFragment.tabLayout.setVisibility(View.GONE);
        String s1=pref.getString("rname",null);
        TextView tv=(TextView)v.findViewById(com.foodlabrinth.darpal.demo5.R.id.tvuname);
        tv.setText("Welcome, "+s1);

        reslogout = (Button) v.findViewById(com.foodlabrinth.darpal.demo5.R.id.res_logout);
        reslogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pref.edit().clear().commit();
                AccountFragment.tabLayout.setVisibility(View.VISIBLE);
                RestaurantLoginFragment restaurantLoginFragment = new RestaurantLoginFragment();
                getFragmentManager().beginTransaction().replace(com.foodlabrinth.darpal.demo5.R.id.loginlayout, restaurantLoginFragment).commit();
            }
        });

        feedback = (Button) v.findViewById(com.foodlabrinth.darpal.demo5.R.id.feedback);
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedbackIt();
            }
        });

        upload = (CardView) v.findViewById(com.foodlabrinth.darpal.demo5.R.id.uploadimg);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GalleryIntent.class);
                startActivity(intent);

            }
        });


        return v;
    }

    private void feedbackIt(){
        final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/email");
        intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ getString(com.foodlabrinth.darpal.demo5.R.string.mail_feedback_email) });
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(com.foodlabrinth.darpal.demo5.R.string.mail_feedback_subject));
        intent.putExtra(android.content.Intent.EXTRA_TEXT, getString(com.foodlabrinth.darpal.demo5.R.string.mail_feedback_message));
        startActivity(Intent.createChooser(intent, getString(com.foodlabrinth.darpal.demo5.R.string.title_send_feedback)));
    }

}
