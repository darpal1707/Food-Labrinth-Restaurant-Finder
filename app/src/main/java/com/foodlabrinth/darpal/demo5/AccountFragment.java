package com.foodlabrinth.darpal.demo5;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.foodlabrinth.darpal.demo5.Login_Signup.ProfileFragment;
import com.foodlabrinth.darpal.demo5.Login_Signup.RestaurantLoginFragment;
import com.foodlabrinth.darpal.demo5.Login_Signup.UserLoginFragment;
import com.foodlabrinth.darpal.demo5.Login_Signup.UserProfileFragment;

public class AccountFragment extends Fragment {

    public static TabLayout tabLayout;
    MainActivity mactivity;
    private TextView tvLabelName;
    private String titleText = "";
    ProfileFragment profileFragment;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_account, container, false);

        mactivity = (MainActivity) getActivity();

        tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
        LinearLayout loginlayout = (LinearLayout) view.findViewById(R.id.loginlayout);
        tabLayout.addTab(tabLayout.newTab().setText("As User"));
        tabLayout.addTab(tabLayout.newTab().setText("As Restaurant"));

        replaceFragment(new UserLoginFragment());

        SharedPreferences pref1 = getActivity().getSharedPreferences("userpref", Context.MODE_PRIVATE);
        boolean b1 = pref1.getBoolean("login", false);

        SharedPreferences pref = getActivity().getSharedPreferences("mypref", Context.MODE_PRIVATE);
        boolean b = pref.getBoolean("login", false);

        if (b1) {
            UserProfileFragment fragment1 = new UserProfileFragment();
            getFragmentManager().beginTransaction().replace(R.id.loginlayout, fragment1).commit();
        } else if (b) {
            ProfileFragment fragment1 = new ProfileFragment();
            getFragmentManager().beginTransaction().replace(R.id.loginlayout, fragment1).commit();
        }
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                replaceFragment(new UserLoginFragment());
                if (tabLayout.getSelectedTabPosition() == 0) {
                    UserLoginFragment fragment1 = new UserLoginFragment();
                    getFragmentManager().beginTransaction().replace(R.id.loginlayout, fragment1).commit();
                } else {
                    RestaurantLoginFragment restaurantLoginFragment = new RestaurantLoginFragment();
                    getFragmentManager().beginTransaction().replace(R.id.loginlayout, restaurantLoginFragment).commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            titleText = getArguments().getString("Gallery");
        }
        return view;

    }

    private void replaceFragment(UserLoginFragment userLoginFragment) {
        FragmentManager fm = mactivity.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.loginlayout, userLoginFragment).commit();
    }



}







