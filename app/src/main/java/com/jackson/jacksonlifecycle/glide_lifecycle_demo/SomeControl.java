package com.jackson.jacksonlifecycle.glide_lifecycle_demo;

import android.app.Activity;

import com.jackson.activityfragmentlifecycle.glide_lifecycle.ActivityLifecycleCallbacks;
import com.jackson.activityfragmentlifecycle.glide_lifecycle.Lifecycle;

/**
 * Created by Jackson on 2017/5/17.
 * Version : 1
 * Details : 这个类完全监听activity的生命周期，可以根据activity的不同
 * 生命周期状态执行不一样的方法或者状态控制
 */
public class SomeControl {

    private SomeControl(Activity activity){
        Lifecycle.observe(activity,new ActivityLifecycle());
    }

    public static SomeControl get(Activity activity){
        return new SomeControl(activity);
    }

    public void doRequest(){
        //DO SOMETHING
    }

    public void stopRequest(){
        //DO SOMETHING
    }


    private class ActivityLifecycle implements ActivityLifecycleCallbacks{

        @Override
        public void onCreate() {
            doRequest();
        }

        @Override
        public void onStart() {

        }

        @Override
        public void onStop() {
            stopRequest();
        }

        @Override
        public void onDestroy() {

        }
    }

}
