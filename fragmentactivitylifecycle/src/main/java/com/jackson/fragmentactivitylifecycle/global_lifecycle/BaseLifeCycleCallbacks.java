package com.jackson.fragmentactivitylifecycle.global_lifecycle;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Jackson on 2017/5/10.
 * Version : 1
 * Details :
 */
public abstract class BaseLifeCycleCallbacks<T>  {


    protected abstract boolean isDoCallbacks(Activity activity);


    protected void onActivityCreated(T t, Bundle savedInstanceState) {}


    protected void onActivityStarted(T t) {}


    protected void onActivityResumed(T t) {}


    protected void onActivityPaused(T t) {}


    protected void onActivityStopped(T t) {}


    protected void onActivitySaveInstanceState(T t, Bundle outState) {}


    protected void onActivityDestroyed(T t) {}

    private int priority;
    void setPriority(int priority){
        this.priority = priority;
    }

    int getPriority(){
        return priority;
    }
}
