package com.jackson.fragmentactivitylifecycle.global_lifecycle;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.jackson.fragmentactivitylifecycle.log.L;

/**
 * Created by jackson on 2017/5/16.
 */

public class LifeCycle implements Application.ActivityLifecycleCallbacks {
    private static final String TAG = "LifeCycle";

    private final LifeCycleControl control;

    public LifeCycle(LifeCycleControl control){
        this.control = control;
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        L.e(TAG,"onActivityCreated");
        for (BaseLifeCycleCallbacks c : control.mBaseLifeCycleCallbacksList) {
            if (c.isDoCallbacks(activity)) {
                c.onActivityCreated(activity, savedInstanceState);
            }
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {
        L.e(TAG,"onActivityStarted");
        for (BaseLifeCycleCallbacks c : control.mBaseLifeCycleCallbacksList) {
            if (c.isDoCallbacks(activity)) {
                c.onActivityStarted(activity);
            }
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        L.e(TAG,"onActivityResumed");
        for (BaseLifeCycleCallbacks c : control.mBaseLifeCycleCallbacksList) {
            if (c.isDoCallbacks(activity)) {
                c.onActivityResumed(activity);
            }
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        L.e(TAG,"onActivityPaused");
        for (BaseLifeCycleCallbacks c : control.mBaseLifeCycleCallbacksList) {
            if (c.isDoCallbacks(activity)) {
                c.onActivityPaused(activity);
            }
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {
        L.e(TAG,"onActivityStopped");
        for (BaseLifeCycleCallbacks c : control.mBaseLifeCycleCallbacksList) {
            if (c.isDoCallbacks(activity)) {
                c.onActivityStopped(activity);
            }
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        L.e(TAG,"onActivitySaveInstanceState");
        for (BaseLifeCycleCallbacks c : control.mBaseLifeCycleCallbacksList) {
            if (c.isDoCallbacks(activity)) {
                c.onActivitySaveInstanceState(activity, outState);
            }
        }
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        L.e(TAG,"onActivityDestroyed");
        for (BaseLifeCycleCallbacks c : control.mBaseLifeCycleCallbacksList) {
            if (c.isDoCallbacks(activity)) {
                c.onActivityDestroyed(activity);
            }
        }
    }
}