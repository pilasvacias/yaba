package com.pilasvacias.yaba.screens;

import android.os.Bundle;

import com.android.volley.VolleyLog;
import com.pilasvacias.yaba.R;
import com.pilasvacias.yaba.common.network.NetworkActivity;
import com.pilasvacias.yaba.modules.emt.models.EmtResult;
import com.pilasvacias.yaba.modules.network.handlers.SuccessHandler;
import com.pilasvacias.yaba.modules.util.Time;

import butterknife.OnClick;
import butterknife.Views;

public class ProbaActivity extends NetworkActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proba);
        Views.inject(this);
    }

    private void createRequest() {

        VolleyLog.DEBUG = true;

        requestManager
                .beginRequest(GetGroupsResult.class)
                .body("GetGroups")
                .success(new SuccessHandler<GetGroupsResult>() {
                    @Override public void onSuccess(GetGroupsResult data) {

                    }
                })
                .fakeTime(Time.seconds(2))
                .cacheTime(Time.seconds(15), Time.mins(1))
                .execute();
    }

    private static class GetGroupsResult extends EmtResult {
        //Cosasss naziss
    }

    @OnClick(R.id.button) void click() {
        createRequest();
    }

}