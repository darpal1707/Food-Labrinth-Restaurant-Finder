package com.foodlabrinth.darpal.demo5.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.foodlabrinth.darpal.demo5.Not_Decided.NotDecidedActivity;

/**
 * Created by Darpal on 2/27/2017.
 */

public class AmbienceCustomAdapter extends BaseAdapter {

    CheckBox checkBox;
    ImageView imageView;
    private int[] array = new int[0];
    Context context;
    NotDecidedActivity notDecidedActivity;


    public AmbienceCustomAdapter(Context mealcontext, int[] array) {
        context = mealcontext;
        this.array = array;
        notDecidedActivity = (NotDecidedActivity) context;
    }


    @Override
    public int getCount() {
        return array.length;
    }

    @Override
    public Object getItem(int position) {
        return array[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        grid = new View(context);
        grid = layoutInflater.inflate(com.foodlabrinth.darpal.demo5.R.layout.ambiencecell, null);
        ImageView imageview = (ImageView) grid.findViewById(com.foodlabrinth.darpal.demo5.R.id.imageview);
        CheckBox checkbox = (CheckBox) grid.findViewById(com.foodlabrinth.darpal.demo5.R.id.checkbox);
        imageview.setImageResource(array[position]);
        /*grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/


        return grid;
    }
}
