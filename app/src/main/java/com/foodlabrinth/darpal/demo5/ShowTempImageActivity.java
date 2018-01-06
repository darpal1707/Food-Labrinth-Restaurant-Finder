package com.foodlabrinth.darpal.demo5;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.ceylonlabs.imageviewpopup.ImagePopup;

public class ShowTempImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_temp_image);

        final ImagePopup imagePopup = new ImagePopup(this);
        imagePopup.setWindowWidth(1440);
        imagePopup.setWindowHeight(2560);
        //imagePopup.setFitsSystemWindows(true);
        imagePopup.setHideCloseIcon(true);
        imagePopup.setImageOnClickClose(true);
        int transparent_black = Color.parseColor("#CC000000");
        imagePopup.setBackgroundColor(transparent_black);

        final ImageView i1 = (ImageView) findViewById(R.id.imageviewdisp1);
        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePopup.initiatePopup(i1.getDrawable());
            }
        });

        final ImageView i2 = (ImageView) findViewById(R.id.imageviewdisp);
        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePopup.initiatePopup(i2.getDrawable());
            }
        });
    }
}
