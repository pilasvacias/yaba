package com.pilasvacias.yaba.core.ads;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.pilasvacias.yaba.R;
import com.pilasvacias.yaba.util.WToast;

/**
 * Created by Alvaro Lazaro on 24/10/13.
 */
public class AdFragment extends Fragment {

    private AdView adview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.ad_fragment_layout, container, false);
        adview = (AdView) v.findViewById(R.id.ad);
        adview.setAdListener(new MyAdListener());

        //All made by xml

        // adRequest = new AdRequest();
        // adRequest.addTestDevice("580E219CE5DF3BCA11CA7283DA73EE06");
        //adRequest.addTestDevice(AdRequest.TEST_EMULATOR);
        //adview.loadAd(adRequest);

        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adview.destroy();
    }


    // Receives callbacks on various events related to fetching ads.  In this sample,
    // the application displays a message on the screen.  A real application may,
    // for example, fill the ad with a banner promoting a feature.
    private class MyAdListener implements AdListener {

        @Override
        public void onDismissScreen(Ad ad) {
        }

        @Override
        public void onFailedToReceiveAd(Ad ad, AdRequest.ErrorCode errorCode) {
            //TODO do something when fail.
            WToast.crazyShort("Failed get add");
        }

        @Override
        public void onLeaveApplication(Ad ad) {

        }

        @Override
        public void onPresentScreen(Ad ad) {

        }

        @Override
        public void onReceiveAd(Ad ad) {
            WToast.crazyShort("Success get add");
        }
    }
}
