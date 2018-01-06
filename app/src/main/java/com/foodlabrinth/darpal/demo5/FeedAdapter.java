package com.foodlabrinth.darpal.demo5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Darpal on 4/13/2017.
 */

class FeedAdapter extends BaseAdapter {

    Context context;
    ArrayList<HashMap<String, String>> arrayList=new ArrayList<>();
    LayoutInflater inflater;

    String RESNAME = "RestaurantName";
    String RESID = "Res_id";
    String UID = "Uid";
    String RID = "Rid";
    String RESADDRESS = "Address";
    String PERSON_NAME = "EmailId";
    String RESREVIEW = "Review";

    public FeedAdapter(Context context, ArrayList<HashMap<String, String>> arrayList) {

        this.context=context;
        this.arrayList=arrayList;
        inflater=LayoutInflater.from(this.context);
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
    public View getView(int position, View convertView, ViewGroup parent) {

        MyViewHolder holder;

        if(convertView==null)
        {
            convertView=inflater.inflate(R.layout.feed_cell,parent,false);
            holder=new MyViewHolder(convertView);
            convertView.setTag(holder);

        }
        else {
            holder= (MyViewHolder) convertView.getTag();
        }
        holder.RestaurantName.setText(arrayList.get(position).get(RESNAME));
        holder.RestaurantAddress.setText(arrayList.get(position).get(RESADDRESS));
        holder.PersonName.setText(arrayList.get(position).get(PERSON_NAME));
        holder.Reviews.setText(arrayList.get(position).get(RESREVIEW));

        return convertView;
    }

    private class MyViewHolder {
        TextView RestaurantName;
        TextView RestaurantAddress;
        TextView PersonName;
        TextView Reviews;

        public MyViewHolder(View convertView) {
                RestaurantName = (TextView) convertView.findViewById(R.id.resFeedName);
                RestaurantAddress = (TextView) convertView.findViewById(R.id.resAddFeed);
                PersonName = (TextView) convertView.findViewById(R.id.personFeedName);
                Reviews = (TextView) convertView.findViewById(R.id.reviewFeed);

        }
    }
}
