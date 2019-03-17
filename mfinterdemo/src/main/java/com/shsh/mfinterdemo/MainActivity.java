package com.shsh.mfinterdemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mobfox.sdk.interstitial.Interstitial;
import com.mobfox.sdk.interstitial.InterstitialListener;

public class MainActivity extends AppCompatActivity {

    private static final String INTER_HASH  = "267d72ac3f77a3f447b32cf7ebf20673";//"80187188f458cfde788d961b6882fd53";

    private Interstitial mInter;

    //======================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Button)findViewById(R.id.btnInterstitial)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnInterstitialPressed(MainActivity.this);
            }
        });
    }

    //======================================================================

    private void btnInterstitialPressed(final Context c)
    {
        mInter = new Interstitial(this,INTER_HASH);

        InterstitialListener listener = new InterstitialListener() {
            @Override
            public void onInterstitialLoaded(Interstitial interstitial) {
                ShowToast(c,  "MobFox Interstitial loaded");
                interstitial.show();
            }

            @Override
            public void onInterstitialLoadFailed(Interstitial interstitial, String s) {
                ShowToast(c,  s);
            }

            @Override
            public void onInterstitialClosed(Interstitial interstitial) {
                ShowToast(c,  "MobFox Interstitial closed");
            }

            @Override
            public void onInterstitialClicked(Interstitial interstitial) {
                ShowToast(c,  "MobFox Interstitial clicked");
            }

            @Override
            public void onInterstitialShown(Interstitial interstitial) {
                ShowToast(c,  "MobFox Interstitial shown");
            }

            @Override
            public void onInterstitialFinished(Interstitial interstitial) {
                ShowToast(c,  "MobFox Interstitial finished");
            }
        };
        mInter.setListener(listener);
        mInter.load();
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
