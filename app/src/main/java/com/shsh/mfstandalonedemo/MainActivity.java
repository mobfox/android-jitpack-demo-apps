package com.shsh.mfstandalonedemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobfox.sdk.banner.Banner;
import com.mobfox.sdk.customevents.CustomEventNative;
import com.mobfox.sdk.interstitial.Interstitial;
import com.mobfox.sdk.interstitial.InterstitialListener;
import com.mobfox.sdk.nativeads.Native;
import com.mobfox.sdk.nativeads.NativeAd;
import com.mobfox.sdk.nativeads.NativeListener;

public class MainActivity extends AppCompatActivity {

    private static final String BANNER_HASH = "fe96717d9875b9da4339ea5367eff1ec";
    private static final String INTER_HASH  = "267d72ac3f77a3f447b32cf7ebf20673";//"80187188f458cfde788d961b6882fd53";
    private static final String NATIVE_HASH = "a764347547748896b84e0b8ccd90fd62";

    public LinearLayout  mViewBanner;
    RelativeLayout       mViewNative;

    private Banner       mBanner;
    private Interstitial mInter;
    private Native       mNative;

    private TextView     headline;
    private ImageView    nativeIcon, nativeMainImg;

    //======================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewBanner   = findViewById(R.id.banner_container);
        mViewNative   = findViewById(R.id.nativeLayout);

        headline      = findViewById(R.id.headline);
        nativeIcon    = findViewById(R.id.nativeIcon);
        nativeMainImg = findViewById(R.id.nativeMainImg);

        ((Button)findViewById(R.id.btnBanner)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewBanner.setVisibility(View.VISIBLE);
                mViewNative.setVisibility(View.GONE);

                btnBannerPressed(MainActivity.this);
            }
        });

        ((Button)findViewById(R.id.btnInterstitial)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewBanner.setVisibility(View.GONE);
                mViewNative.setVisibility(View.GONE);

                btnInterstitialPressed(MainActivity.this);
            }
        });

        ((Button)findViewById(R.id.btnNative)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewBanner.setVisibility(View.GONE);
                mViewNative.setVisibility(View.VISIBLE);

                btnNativePressed(MainActivity.this);
            }
        });
    }

    //======================================================================

    private void removeAllViews()
    {
        mViewNative.setVisibility(View.INVISIBLE);
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

    private void btnNativePressed(final Context c)
    {
        mNative = new Native(this);

        //we must set a listener for native

        NativeListener listener = new NativeListener() {
            @Override
            public void onNativeReady(Native aNative, CustomEventNative event, NativeAd ad) {

                ShowToast(c,  "on native ready");

                //register custom layout click
                event.registerViewForInteraction(mViewNative);
                //fire trackers
                ad.fireTrackers(c);

                headline.setText(ad.getTexts().get(0).getText());

                ad.loadImages(c, new NativeAd.ImagesLoadedListener() {
                    @Override
                    public void onImagesLoaded(NativeAd ad) {
                        ShowToast(c,  "on images ready");
                        nativeIcon.setImageBitmap(ad.getImages().get(0).getImg());
                        nativeMainImg.setImageBitmap(ad.getImages().get(1).getImg());
                    }
                });

            }

            @Override
            public void onNativeError(Exception e) {
                ShowToast(c,  "on native error");
            }

            @Override
            public void onNativeClick(NativeAd ad) {
                ShowToast(c, "on native click");
            }

        };

        mNative.setListener(listener);

        //load our native
        mNative.load(NATIVE_HASH);
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
