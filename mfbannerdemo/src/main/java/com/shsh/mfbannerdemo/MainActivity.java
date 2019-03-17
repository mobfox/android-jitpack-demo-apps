package com.shsh.mfbannerdemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mobfox.sdk.banner.Banner;

public class MainActivity extends AppCompatActivity {

    private static final String BANNER_HASH = "fe96717d9875b9da4339ea5367eff1ec";

    public LinearLayout  mViewBanner;

    private Banner       mBanner;

    //======================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewBanner   = findViewById(R.id.banner_container);

        ((Button)findViewById(R.id.btnBanner)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBannerPressed(MainActivity.this);
            }
        });
    }

    //======================================================================

    private void btnBannerPressed(final Context c)
    {
        mViewBanner.removeAllViews();

        mBanner = new Banner(this, 320, 50, BANNER_HASH, new Banner.Listener() {
            @Override
            public void onBannerLoadFailed(Banner banner,String code) {
                ShowToast(c,  code);
            }

            @Override
            public void onBannerLoaded(Banner banner) {
                ShowToast(c,  "MobFox Banner loaded");
                mViewBanner.addView(banner);
            }

            @Override
            public void onBannerClosed(Banner banner) {
                ShowToast(c,  "MobFox Banner closed");
            }

            @Override
            public void onBannerFinished() {
                ShowToast(c,  "MobFox Banner finished");
            }

            @Override
            public void onBannerClicked(Banner banner) {
                ShowToast(c,  "MobFox Banner clicked");
            }

        });

        mBanner.load();
    }

    //======================================================================

    private void ShowToast(final Context c, final String msg)
    {
        runOnUiThread( new Runnable() {
            public void run()
            {
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        } );
    }
}
