package com.foodlabrinth.darpal.demo5;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.azoft.carousellayoutmanager.DefaultChildSelectionListener;
import com.foodlabrinth.darpal.demo5.Browse_Places.BrowsePlacesActivity;
import com.foodlabrinth.darpal.demo5.Card_Animation.CardAnimationHomeActivity;
import com.foodlabrinth.darpal.demo5.Not_Decided.NotDecidedActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.Random;


public class HomeFragment extends Fragment {

    //public static int INVALID_POSITION = -1;
    private Button notdecided,browse;
    private String titleText ="";


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        View view = inflater.inflate(R.layout.fragment_home2, container, false);
        notdecided = (Button) view.findViewById(R.id.notdecided);
        browse = (Button) view.findViewById(R.id.browse);

        final VerticalAdaptar verticalAdaptar= new VerticalAdaptar(getActivity());
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.list_vertical);
        initVerRecyclerView(rv, new CarouselLayoutManager(CarouselLayoutManager.VERTICAL, false), verticalAdaptar);


        MobileAds.initialize(getActivity(), "ca-app-pub-3405769962382070/6046675946");
        AdView adView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.i("Ads", "onAdLoaded");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.i("Ads", "onAdFailedToLoad");
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                Log.i("Ads", "onAdOpened");
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                Log.i("Ads", "onAdLeftApplication");
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
                Log.i("Ads", "onAdClosed");
            }
        });

        notdecided.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),NotDecidedActivity.class);
                startActivity(intent);
            }
        });

        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),BrowsePlacesActivity.class);
                startActivity(intent);
            }
        });

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            titleText =  getArguments().getString("Home");
        }
        return view;

    }



    private void initVerRecyclerView(final RecyclerView recyclerView, final CarouselLayoutManager layoutManager, final VerticalAdaptar adapter) {
        // enable zoom effect. this line can be customized
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());

        recyclerView.setLayoutManager(layoutManager);
        // we expect only fixed sized item for now
        recyclerView.setHasFixedSize(true);
        // sample adapter with random data
        recyclerView.setAdapter(adapter);
        // enable center post scrolling
        recyclerView.addOnScrollListener(new CenterScrollListener());

        DefaultChildSelectionListener.initCenterItemListener(new DefaultChildSelectionListener.OnCenterItemClickListener() {
            @Override
            public void onCenterItemClicked(@NonNull RecyclerView recyclerView, @NonNull CarouselLayoutManager carouselLayoutManager, @NonNull View v) {


                final int position = recyclerView.getChildLayoutPosition(v);
                if(position == 0){
                    Intent in = new Intent(getActivity(),CardAnimationHomeActivity.class);
                    String URL1 = "http://foodlabrinth.com/trending_this_week.php";
                    URL1 = URL1.replaceAll(" ", "%20");
                    URL1 = URL1.replaceAll("\n", "%20");
                    Log.e("Trending", URL1);
                    try {
                        URL source = new URL(URL1);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    in.putExtra("URL",URL1);
                    startActivity(in);
                }
                if(position == 1){
                    Intent in = new Intent(getActivity(),CardAnimationHomeActivity.class);
                    String URL1 = "http://foodlabrinth.com/newly_opened.php";
                    URL1 = URL1.replaceAll(" ", "%20");
                    URL1 = URL1.replaceAll("\n", "%20");
                    Log.e("Trending", URL1);
                    try {
                        URL source = new URL(URL1);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    in.putExtra("URL",URL1);
                    startActivity(in);
                }
                if(position == 2){
                    Intent in = new Intent(getActivity(),CardAnimationHomeActivity.class);
                    String URL1 = "http://foodlabrinth.com/card_animation.php?Cuisine=Gujarati";
                    in.putExtra("URL",URL1);
                    startActivity(in);

                }
                if(position == 3){
                    Intent in = new Intent(getActivity(),CardAnimationHomeActivity.class);
                    String URL1 = "http://foodlabrinth.com/card_animation.php?Cuisine=Italian";
                    URL1 = URL1.replaceAll(" ", "%20");
                    URL1 = URL1.replaceAll("\n", "%20");
                    try {
                        URL source = new URL(URL1);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    in.putExtra("URL",URL1);
                    startActivity(in);
                }
                if(position == 9){
                    Intent in = new Intent(getActivity(),CardAnimationHomeActivity.class);
                    String URL1 = "http://foodlabrinth.com/card_animation.php?Cuisine=Italian";
                    in.putExtra("URL",URL1);
                    startActivity(in);
                }
                if(position == 13){
                    Intent in = new Intent(getActivity(),CardAnimationHomeActivity.class);
                    String URL1 = "http://foodlabrinth.com/card_animation.php?Cuisine=Desert";
                    in.putExtra("URL",URL1);
                    startActivity(in);
                }
                if(position == 4){
                    Intent in = new Intent(getActivity(),CardAnimationHomeActivity.class);
                    String URL1 = "http://foodlabrinth.com/card_animation_ambience.php?Ambience=Unique Dining";
                    in.putExtra("URL",URL1);
                    URL1 = URL1.replaceAll(" ", "%20");
                    Log.e("urll==", URL1);
                    try {
                        URL sourceUrl = new URL(URL1);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    startActivity(in);
                }
                if(position == 5){
                    Intent in = new Intent(getActivity(),CardAnimationHomeActivity.class);
                    String URL1 = "http://foodlabrinth.com/card_animation_ambience.php?Ambience=Hangout";
                    in.putExtra("URL",URL1);
                    URL1 = URL1.replaceAll(" ", "%20");
                    Log.e("urll==", URL1);
                    try {
                        URL sourceUrl = new URL(URL1);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    startActivity(in);
                }
                if(position == 6){
                    Intent in = new Intent(getActivity(),CardAnimationHomeActivity.class);
                    String URL1 = "http://foodlabrinth.com/card_animation_ambience.php?Ambience=Buffet";
                    in.putExtra("URL",URL1);
                    URL1 = URL1.replaceAll(" ", "%20");
                    Log.e("urll==", URL1);
                    try {
                        URL sourceUrl = new URL(URL1);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    startActivity(in);
                }
                if(position == 7){
                    Intent in = new Intent(getActivity(),CardAnimationHomeActivity.class);
                    String URL1 = "http://foodlabrinth.com/card_animation_ambience.php?Ambience=Late Night Meals";
                    in.putExtra("URL",URL1);
                    URL1 = URL1.replaceAll(" ", "%20");
                    Log.e("urll==", URL1);
                    try {
                        URL sourceUrl = new URL(URL1);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    startActivity(in);
                }
                if(position == 8){
                    Intent in = new Intent(getActivity(),CardAnimationHomeActivity.class);
                    String URL1 = "http://foodlabrinth.com/card_animation_ambience.php?Ambience=Street/Open Area";
                    in.putExtra("URL",URL1);
                    URL1 = URL1.replaceAll(" ", "%20");
                    Log.e("urll==", URL1);
                    try {
                        URL sourceUrl = new URL(URL1);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    startActivity(in);
                }
                if(position == 11){
                    Intent in = new Intent(getActivity(),CardAnimationHomeActivity.class);
                    String URL1 = "http://foodlabrinth.com/card_animation.php?Cuisine=Desert";
                    in.putExtra("URL",URL1);
                    URL1 = URL1.replaceAll(" ", "%20");
                    Log.e("urll==", URL1);
                    try {
                        URL sourceUrl = new URL(URL1);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    startActivity(in);
                }
                if(position == 10){
                    Intent in = new Intent(getActivity(),CardAnimationHomeActivity.class);
                    String URL1 = "http://foodlabrinth.com/card_animation_ambience.php?Ambience=Live Sports";
                    in.putExtra("URL",URL1);
                    URL1 = URL1.replaceAll(" ", "%20");
                    Log.e("urll==", URL1);
                    try {
                        URL sourceUrl = new URL(URL1);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    startActivity(in);
                }

                final String msg = String.format(Locale.US, "Item %1$d was clicked", position);
                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();

            }
        },recyclerView,layoutManager);

    }




    private static final class VerticalAdaptar extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @SuppressWarnings("UnsecureRandomNumberGeneration")
        private final Random mRandom = new Random();
        private final int[] mColors;
        private final int[] mPosition;
        private final int[] image;
        private int mItemsCount = 12;
        LayoutInflater inflater;

        /*private final String[] card_title={
                "Trending this week",
                "Newly Opened",
                "Gujarati Thali",
                "Great Italian",
                "Unique Dining",
                "Hanging Out",
                "Unlimited Meals",
                "Great Buffets",
                "Late Night Cravings",
                "Outdoor Seating",
                "Pizza Time",
                "Sunday Brunches",
                "Live Sport Screenings",
                "Summer Coolers",

        };*/

        private final int[] card_image = {
                R.mipmap.trendspark,
                R.mipmap.newspark,
                R.mipmap.gujaratione,
                R.mipmap.italianspark,
                R.mipmap.uniquespark,
                R.mipmap.hangoutspark,
                R.mipmap.buffetspark,
                R.mipmap.latespark,
                R.mipmap.outdoorspark,
                R.mipmap.pizzaspark,
                R.mipmap.livespark,
                R.mipmap.summerspark
        };


        VerticalAdaptar(Context context) {

            int i = 0;
            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mColors = new int[12];
            mPosition = new int[12];
            image = new int[12];
            for (i = 0; 12 > i; ++i) {
                //mColors[i] = Color.RED; /*Color.argb(255,mRandom.nextInt(256), mRandom.nextInt(256), mRandom.nextInt(256));*/
                mPosition[i] = i;
            }

        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = inflater.inflate( R.layout.item_card, null) ;
            RecyclerView.ViewHolder holder = new RowVerNewsViewHolder(view);
            return holder;

        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            //((RowVerNewsViewHolder) holder).cItem1.setText(card_title[position]);
            ((RowVerNewsViewHolder) holder).cItem2.setImageResource(card_image[position]);
            holder.itemView.setBackgroundColor(mColors[position]);


        }

        @Override
        public int getItemCount() {
            return mItemsCount;
        }
    }

    public static class RowVerNewsViewHolder extends RecyclerView.ViewHolder {
        //TextView cItem1;
        ImageView cItem2;



        public RowVerNewsViewHolder(View itemView) {
            super(itemView);
            //cItem1 = (TextView) itemView.findViewById(R.id.skillTitle);
            cItem2 = (ImageView) itemView.findViewById(R.id.cardImage);


        }
    }


}
