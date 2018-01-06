package com.foodlabrinth.darpal.demo5;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.foodlabrinth.darpal.demo5.Bookmark_restaurant.BookmarkRestaurants;
import com.foodlabrinth.darpal.demo5.Search_Restaurant.SearchRestaurantActivity;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import static com.foodlabrinth.darpal.demo5.R.menu.menu_main;


public class    MainActivity extends AppCompatActivity {


    private BottomBar mBottomBar;
    private Toolbar toolbar;
    private FrameLayout frame;
    private Bundle bundle;
    boolean doubleBack = false;
    private MaterialSearchView searchView;
    String searchtext;

    AccountFragment accountFragment;
    SharedPreferences preferences;
    boolean b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences("userpref", Context.MODE_PRIVATE);
        //preferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        b = preferences.getBoolean("login", false);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        frame = (FrameLayout) findViewById(R.id.frame);

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setVoiceSearch(true);
        searchView.setCursorDrawable(R.drawable.custom_cursor);
        searchView.setEllipsize(true);
        //searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchtext=query.toString();
                Log.e("searchtext==",searchtext);

                Intent intent = new Intent(MainActivity.this, SearchRestaurantActivity.class);
                intent.putExtra("searchtext",searchtext);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic

                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

            }
        });



        bundle = new Bundle();
        bundle.putString("Home", "Home Fragment");
        bundle.putString("Collection", "Feed Fragment");
        bundle.putString("Account", "Account Fragment");
        bundle.putString("Feed", "Collection Fragment");

        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.noNavBarGoodness();

        mBottomBar.setItemsFromMenu(R.menu.bottombar_menu, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {

                if (menuItemId == R.id.nav_home) {

                    HomeFragment homeFragment = new HomeFragment();
                    homeFragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, homeFragment).commit();

                } else if (menuItemId == R.id.nav_collection) {

                    EventFragment eventsFragment = new EventFragment();
                    eventsFragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, eventsFragment).commit();


                } else if (menuItemId == R.id.nav_account) {

                    //Toast.makeText(MainActivity.this, "click", Toast.LENGTH_SHORT).show();
                        accountFragment = new AccountFragment();
                        accountFragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, accountFragment).commit();


                } else if (menuItemId == R.id.nav_feed) {

                    FeedFragment feedFragment = new FeedFragment();
                    feedFragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, feedFragment).commit();

                }


            }


            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.nav_home) {
                    // The user reselected item number one, scroll your content to top.

                }
            }
        });

        mBottomBar.mapColorForTab(0, ContextCompat.getColor(this, R.color.bottomBar));
        mBottomBar.mapColorForTab(1, ContextCompat.getColor(this, R.color.bottomBar));
        mBottomBar.mapColorForTab(2, ContextCompat.getColor(this, R.color.bottomBar));
        mBottomBar.mapColorForTab(3, ContextCompat.getColor(this, R.color.bottomBar));

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        mBottomBar.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(menu_main, menu);

        MenuItem item = menu.findItem(R.id.navigate);
        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.bookmark) {
            startActivity(new Intent(this, BookmarkRestaurants.class));
        }


        return super.onOptionsItemSelected(item);
    }


    /*public void onBackPressed(){
        if(doubleBack){
            super.onBackPressed();
            return;
        }
        this.doubleBack = true;
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBack = false;
            }
        },2000);
    }*/

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                }
            }


            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }*/
}
