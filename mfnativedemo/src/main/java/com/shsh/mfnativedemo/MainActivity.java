package com.shsh.mfnativedemo;

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

import com.mobfox.sdk.customevents.CustomEventNative;
import com.mobfox.sdk.nativeads.Native;
import com.mobfox.sdk.nativeads.NativeAd;
import com.mobfox.sdk.nativeads.NativeListener;

public class MainActivity extends AppCompatActivity {

    private static final String NATIVE_HASH = "a764347547748896b84e0b8ccd90fd62";

    RelativeLayout       mViewNative;

    private Native       mNative;

    private TextView     headline;
    private ImageView    nativeIcon, nativeMainImg;

    //======================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewNative   = findViewById(R.id.nativeLayout);

        headline      = findViewById(R.id.headline);
        nativeIcon    = findViewById(R.id.nativeIcon);
        nativeMainImg = findViewById(R.id.nativeMainImg);

        ((Button)findViewById(R.id.btnNative)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnNativePressed(MainActivity.this);
            }
        });
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
