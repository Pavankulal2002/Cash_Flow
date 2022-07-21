package com.pavanpradeep.cashflow;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.felipecsl.gifimageview.library.GifImageView;

public class Splash extends AppCompatActivity {

    //Log
    private static final String TAG = "Splash";

    //GIF
    private GifImageView gifImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Log.d(TAG, "onCreate: starting splash");

        initComponent();


        //Wait for 2 seconds and start next activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Splash.this.startActivity(new Intent(Splash.this, RegisterActivity.class));
                Splash.this.finish();
            }
        },1800); // 1800ms
    }

    /**
     * Define the UI.
     */
    private void initComponent()
    {
        Log.d(TAG, "initComponent: Initialise all components");
    }
}
