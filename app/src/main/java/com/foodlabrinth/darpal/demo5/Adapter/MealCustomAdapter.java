package com.foodlabrinth.darpal.demo5.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.foodlabrinth.darpal.demo5.Not_Decided.NotDecidedActivity;
import com.foodlabrinth.darpal.demo5.Not_Decided.SecondQuestion;

/**
 * Created by Darpal on 2/27/2017.
 */

public class MealCustomAdapter extends BaseAdapter {

    RadioButton checkBox;
    ImageView imageView;
    private int[] array = new int[0];
    Context context;
    NotDecidedActivity notDecidedActivity;
    String arr[];

    public MealCustomAdapter(Context mealcontext, int[] array, String  arr[]){
        context = mealcontext;
        this.array = array;
        this.arr=arr;
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        View grid;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


            grid = new View(context);
            grid = layoutInflater.inflate(com.foodlabrinth.darpal.demo5.R.layout.mealcell, null);
            ImageView imageview = (ImageView) grid.findViewById(com.foodlabrinth.darpal.demo5.R.id.imageview);
            //final RadioButton checkbox = (RadioButton) grid.findViewById(R.id.abcd);
            imageview.setImageResource(array[position]);


            grid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SecondQuestion secondQuestion = new SecondQuestion();
                    String s = arr[position];
                    //Toast.makeText(context, "s==" + s, Toast.LENGTH_SHORT).show();
                    Bundle b = new Bundle();
                    b.putString("Meal",s);
                    secondQuestion.setArguments(b);
                    notDecidedActivity.getSupportFragmentManager().beginTransaction().replace(com.foodlabrinth.darpal.demo5.R.id.questions, secondQuestion).addToBackStack(null).commit();
                }
            });
        return grid;
    }

}
