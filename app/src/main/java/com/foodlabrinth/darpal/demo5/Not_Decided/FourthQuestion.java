package com.foodlabrinth.darpal.demo5.Not_Decided;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.foodlabrinth.darpal.demo5.R;
import com.foodlabrinth.darpal.demo5.Result_Page.ResultPageActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FourthQuestion extends Fragment {

    private TextView subtitleTextView;
    private View descriptionLayout;
    Button click;
    CardView low, medium, high;
    ArrayList<String> getdata;
    String meal;
    String ambianc;
    String l = "", m = "", h = "", temp = "";
    String arr[];
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fourth_question, container, false);

        low = (CardView) view.findViewById(R.id.lowbudget);
        medium = (CardView) view.findViewById(R.id.mediumbudget);
        high = (CardView) view.findViewById(R.id.highbudget);
        getdata = new ArrayList<>();

        click = (Button) view.findViewById(R.id.click);

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp = l + m + h;
                Log.e("Temp", temp);

                Intent intent = new Intent(getActivity(), ResultPageActivity.class);
                ResultPageActivity.Senddata(ambianc, temp,arr,meal);
                startActivity(intent);

            }
        });

        low.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                l = "500";
                m = "";
                h = "";

            }
        });
        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m = "1500";
                l = "";
                h = "";
            }
        });
        high.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                h = "2500";
                l = "";
                m = "";
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    public void alldata( String ambiance,String data[],String meal) {
        //this.getdata = arrayList;
        this.meal = meal;
        this.ambianc = ambiance;
        this.arr=data;
        for(int i=0;i<data.length;i++)
        {
            Log.e("forth",data[i]);
        }
        /*HashSet<String >hs=new HashSet<>();
        hs.addAll(arrayList);*/
        //Log.e("Hasdata NEw", String.valueOf(hs));
    }

}
