package com.foodlabrinth.darpal.demo5.Splash_Screen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.foodlabrinth.darpal.demo5.R;

public class Splash_intro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_intro);

        /*findViewById(R.id.btn_play_again).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // We normally won't show the welcome slider again in real app
                // but this is for testing
                PrefManager prefManager = new PrefManager(getApplicationContext());

                // make first time launch TRUE
                prefManager.setFirstTimeLaunch(true);

                startActivity(new Intent(Splash_intro.this, WelcomeActivity.class));
                finish();
            }
        });*/
    }
}
