package com.jackson.jacksonlifecycle.global_activity_lifecycle_demo;

import android.app.Activity;
import android.os.Bundle;

import com.jackson.activityfragmentlifecycle.global_lifecycle.BaseLifeCycleCallbacks;

/**
 * Created by Jackson on 2017/5/10.
 * Version : 1
 * Details :
 */
public class InitCallBack extends BaseLifeCycleCallbacks<InitCallBack.InitActivity>{

    @Override
    protected boolean isDoCallbacks(Activity activity) {
        return activity instanceof InitActivity;
    }

    @Override
    protected void onActivityCreated(InitActivity t, Bundle savedInstanceState) {
        Activity activity = (Activity) t;
        activity.setContentView(t.getViewRes());
        t.initView();
        t.initListener();
    }

    public interface InitActivity {
        int getViewRes();
        void initView();
        void initListener();
    }
}

