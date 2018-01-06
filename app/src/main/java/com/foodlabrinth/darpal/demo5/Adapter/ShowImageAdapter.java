package com.foodlabrinth.darpal.demo5.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Darpal on 5/3/2017.
 */

public class ShowImageAdapter extends BaseAdapter {


    //int[] array = new int[0];
    Context context;
    //boolean[] itemChecked;
    //NotDecidedActivity notDecidedActivity;
    //String arr[];
    //String arraynew;
    private ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    //String arrdata[];


    public ShowImageAdapter(Context context, ArrayList<HashMap<String, String>> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        //this.arr = arr;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
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
        grid = layoutInflater.inflate(com.foodlabrinth.darpal.demo5.R.layout.showimgcell, null);


        final ImagePopup imagePopup = new ImagePopup(context);
        imagePopup.setWindowWidth(1440);
        imagePopup.setWindowHeight(2560);
        //imagePopup.setFitsSystemWindows(true);
        imagePopup.setHideCloseIcon(true);
        imagePopup.setImageOnClickClose(true);
        int transparent_black = Color.parseColor("#CC000000");
        imagePopup.setBackgroundColor(transparent_black);


        final ImageView imageview = (ImageView) grid.findViewById(com.foodlabrinth.darpal.demo5.R.id.imageviewdisp);
        //imageview.setOnTouchListener(new ImageMatrixTouchHandler(context));
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePopup.initiatePopup(imageview.getDrawable());
                //imagePopup.setOnTouchListener(new ImageMatrixTouchHandler(v.getContext()));
            }
        });
        //cb = (CheckBox) grid.findViewById(R.id.checkbox1);

        Picasso.with(context).load(arrayList.get(position).get("image")).into(imageview);

        return grid;
    }

}
