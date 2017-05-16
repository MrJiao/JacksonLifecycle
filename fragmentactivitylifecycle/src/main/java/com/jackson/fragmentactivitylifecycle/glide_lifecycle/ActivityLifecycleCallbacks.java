package com.jackson.fragmentactivitylifecycle.glide_lifecycle;

/**
 * Created by Jackson on 2017/5/11.
 * Version : 1
 * Details :
 */
public interface ActivityLifecycleCallbacks {

    void onCreate();

    /**
     * Callback for when {@link android.app.Activity#onStart()} is called.
     */
    void onStart();

    /**
     * Callback for when {@link android.app.Activity#onStop()}} is called.
     */
    void onStop();

    /**
     * Callback for when {@link android.app.Activity#onDestroy()} is called.
     */
    void onDestroy();
}
