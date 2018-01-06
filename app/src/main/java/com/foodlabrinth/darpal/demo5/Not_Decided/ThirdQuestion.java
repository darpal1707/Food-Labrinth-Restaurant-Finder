package com.foodlabrinth.darpal.demo5.Not_Decided;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.Toast;

import com.foodlabrinth.darpal.demo5.Adapter.AmbienceCustomAdapter;
import com.foodlabrinth.darpal.demo5.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

public class ThirdQuestion extends Fragment {

    GridView gridView;
    public CheckBox checkBox;
    int[] array = new int[]{R.mipmap.luxury, R.mipmap.romantic, R.mipmap.rooftop, R.mipmap.streetamb, R.mipmap.unnamed, R.mipmap.hangoutspark};
    String arr[] = {"Luxury", "Romantic", "Roof Top", "Street/Open Area", "Unique Dining", "Hangout"};
    ArrayList<String> getdata;
    String meal;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String[] stockArr;
    String s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third_question, container, false);
        getdata = new ArrayList<>();
        gridView = (GridView) view.findViewById(R.id.ambiencegrid);
        AmbienceCustomAdapter ambienceCustomAdapter = new AmbienceCustomAdapter(getActivity(), array);
        gridView.setAdapter(ambienceCustomAdapter);
        /*pref = getActivity().getSharedPreferences("Ref", Context.MODE_PRIVATE);
        ed = pref.edit();*/
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String data = arr[position];
                Toast.makeText(getActivity(), "" + data, Toast.LENGTH_SHORT).show();
                FourthQuestion fourthQuestion = new FourthQuestion();
                fourthQuestion.alldata(data, stockArr, meal);
                getFragmentManager().beginTransaction().replace(R.id.questions, fourthQuestion).addToBackStack(null).commit();

            }
        });

        pref = getActivity().getSharedPreferences("Cuisines", Context.MODE_PRIVATE);
        editor = pref.edit();
        return view;
    }

    public void GetArrayData(ArrayList<String> arrayList, String meal) {
        this.getdata = arrayList;
        this.meal = meal;
        HashSet<String> hs = new HashSet<>();
        hs.addAll(getdata);
        getdata.clear();
        getdata.addAll(hs);
        for (int i = 0; i < arrayList.size(); i++) {
            Log.e("DataGet", getdata.get(i));
            Log.e("Meal", meal);
        }
        stockArr = new String[getdata.size()];
        stockArr = getdata.toArray(stockArr);
        Log.e("Array Converted", stockArr.toString());

        StringTokenizer st = new StringTokenizer(getdata.toString());
        while (st.hasMoreTokens()) {
            System.out.println("Next token is : " + st.nextToken(","));
        }
        /*try {
            editor.putString("Meal", meal);
            for (int i = 0; i < getdata.size(); i++) {
                editor.putString("val" + String.valueOf(i), getdata.get(i));
            }
            editor.apply();
        } catch (Exception e) {
            Log.e("error", String.valueOf(e));
        }*/

    }

}
