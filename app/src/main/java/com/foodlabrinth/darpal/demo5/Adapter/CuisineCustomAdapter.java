package com.foodlabrinth.darpal.demo5.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.foodlabrinth.darpal.demo5.Not_Decided.NotDecidedActivity;

import java.util.ArrayList;

/**
 * Created by Darpal on 2/27/2017.
 */

public class CuisineCustomAdapter extends BaseAdapter {


    int[] array = new int[0];
    Context context;
    boolean[] itemChecked;
    NotDecidedActivity notDecidedActivity;
    String arr[];
    String arraynew;
    private ArrayList<String> arrayList=new ArrayList<>();
    String arrdata[];

    public CuisineCustomAdapter(Context cuisinecontext, int[] array, String arr[]) {
        context = cuisinecontext;
        this.array = array;
        this.arr = arr;
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View grid;
        final CheckBox cb;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        grid = new View(context);
        grid = layoutInflater.inflate(com.foodlabrinth.darpal.demo5.R.layout.cuisinecell, null);
        final ImageView imageview = (ImageView) grid.findViewById(com.foodlabrinth.darpal.demo5.R.id.imageview);
        //cb = (CheckBox) grid.findViewById(R.id.checkbox1);

        imageview.setImageResource(array[position]);
        final View finalGrid = grid;
        /*grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    arraynew = arr[position];
                    arrayList.add(arraynew);
                    for(int i=0;i<arrayList.size();i++)
                    {
                        Log.e("data",arrayList.get(i));
                    }
                } catch (Exception ignored) {
                    Toast.makeText(context, "" + ignored, Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(context, "" + arraynew, Toast.LENGTH_SHORT).show();
            }
        });*/

        return grid;
    }
}
