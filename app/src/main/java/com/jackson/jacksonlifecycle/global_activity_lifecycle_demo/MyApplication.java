package com.jackson.jacksonlifecycle.global_activity_lifecycle_demo;

import android.app.Application;

import com.jackson.activityfragmentlifecycle.global_lifecycle.GlobalLifeCycle;

/**
 * Created by Jackson on 2017/5/17.
 * Version : 1
 * Details :
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        GlobalLifeCycle
                .init(this)
                .registerCallbacks(new InitCallBack());
    }



}
