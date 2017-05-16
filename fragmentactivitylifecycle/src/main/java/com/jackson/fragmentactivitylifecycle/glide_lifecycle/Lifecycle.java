package com.jackson.fragmentactivitylifecycle.glide_lifecycle;


import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.support.v4.app.FragmentActivity;

/**
 * Created by Jackson on 2017/5/11.
 * Version : 1
 * Details : 本包代码大部分是取于Glide库 https://github.com/bumptech/glide，
 * 我做代码的抽取和重新封装，用于监听Activity,Fragment,FragmentActivity,
 * support.v4.Fragment的生命周期。
 */
public class Lifecycle {

    public static void observe(Activity activity, ActivityLifecycleCallbacks listener){
        if(listener instanceof FragmentLifecycleCallbacks){
            throw new IllegalArgumentException("must be ActivityLifecycleCallbacks");
        }
        LifeCycleRetriever.get().observe(activity,listener);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void observe(android.app.Fragment fragment,FragmentLifecycleCallbacks listener) {
        LifeCycleRetriever.get().observe(fragment, listener);
    }

    public static void observe(android.support.v4.app.Fragment fragment, FragmentLifecycleCallbacks listener){
        LifeCycleRetriever.get().observe(fragment, listener);
    }

    public static void observe(FragmentActivity fragmentActivity, ActivityLifecycleCallbacks listener){
        if(listener instanceof FragmentLifecycleCallbacks){
            throw new IllegalArgumentException("must be ActivityLifecycleCallbacks");
        }
        LifeCycleRetriever.get().observe(fragmentActivity, listener);
    }


}
