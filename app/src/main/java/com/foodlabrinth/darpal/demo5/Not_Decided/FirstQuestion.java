package com.foodlabrinth.darpal.demo5.Not_Decided;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.RadioButton;

import com.foodlabrinth.darpal.demo5.Adapter.MealCustomAdapter;
import com.foodlabrinth.darpal.demo5.R;


public class FirstQuestion extends Fragment {

    GridView gridView;
    public RadioButton checkBox;
    int[] array = new int[]{R.mipmap.breakfast, R.mipmap.lunch, R.mipmap.dinner, R.mipmap.coffee, R.mipmap.latenight};
    String arr[] = {"Breakfast", "Lunch", "Dinner", "Cafe", "Late Night"};

    public FirstQuestion() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_first_question, container, false);
        gridView = (GridView) view.findViewById(R.id.mealgrid);
        final MealCustomAdapter mealCustomAdapter = new MealCustomAdapter(getActivity(), array, arr);
        gridView.setAdapter(mealCustomAdapter);


        return view;
    }



}
