package com.jackson.activityfragmentlifecycle.glide_lifecycle;

/**
 * Created by Jackson on 2017/5/11.
 * Version : 1
 * Details :
 */
public interface FragmentLifecycleCallbacks extends ActivityLifecycleCallbacks {

    void onCreateView();

    void onActivityCreated();

}
