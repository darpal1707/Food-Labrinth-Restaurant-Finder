package com.foodlabrinth.darpal.demo5.Not_Decided;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.foodlabrinth.darpal.demo5.Adapter.CuisineCustomAdapter;
import com.foodlabrinth.darpal.demo5.R;

import java.util.ArrayList;
import java.util.HashSet;


/**
 * A simple {@link Fragment} subclass.
 */
public class SecondQuestion extends Fragment {


    GridView gridView;
    public CheckBox cb;
    ImageView imageview;
    int[] array = new int[]{R.mipmap.chinese, R.mipmap.italian, R.mipmap.asian,
            R.mipmap.lebanese, R.mipmap.thai, R.mipmap.indian,
            R.mipmap.south, R.mipmap.punjabi, R.mipmap.gujaratione,
            R.mipmap.mexican, R.mipmap.streetwo, R.mipmap.coffee,
            R.mipmap.dessert};

    String arr[] = {"Chinese", "Italian", "Asian", "Lebanese", "Thai", "Indian",
            "South Indian", "Punjabi", "Gujarati", "Mexican", "Street Food", "Cafe", "Desert"};
    Button click;
    private CuisineCustomAdapter cuisineCustomAdapter;
    String arr1[];
    String arraynew;
    ArrayList<String> arrayList;

    String Meal;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_second_question, container, false);
        gridView = (GridView) view.findViewById(R.id.cuisinegrid);
        arrayList = new ArrayList<>();
        Bundle b = getArguments();
        Meal = b.getString("Meal");

        cuisineCustomAdapter = new CuisineCustomAdapter(getActivity(), array,arr);
        gridView.setAdapter(cuisineCustomAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    arraynew = arr[position];
                    arrayList.add(arraynew);
                    for(int i=0;i<arrayList.size();i++)
                    {
                        Log.e("data",arrayList.get(i));
                    }
                } catch (Exception ignored) {
                    Toast.makeText(getActivity(), "" + ignored, Toast.LENGTH_SHORT).show();
                }
                HashSet<String >hashSet=new HashSet<String>();
                hashSet.addAll(arrayList);
                Log.e("NewArray", String.valueOf(hashSet));
                Toast.makeText(getActivity(), "" + arraynew, Toast.LENGTH_SHORT).show();
            }
        });

        click = (Button) view.findViewById(R.id.nextpage);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThirdQuestion thirdQuestion = new ThirdQuestion();
                thirdQuestion.GetArrayData(arrayList, Meal);
                getFragmentManager().beginTransaction().replace(R.id.questions, thirdQuestion).addToBackStack(null).commit();
            }
        });

        return view;
    }
}
