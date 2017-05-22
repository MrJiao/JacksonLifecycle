package com.jackson.activityfragmentlifecycle.glide_lifecycle;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * Created by Jackson on 2017/5/11.
 * Version : 1
 * Details :
 */
class ActivityFragmentLifecycle  {
    private final Set<ActivityLifecycleCallbacks> activityLifecycleCallbacks =
            Collections.newSetFromMap(new WeakHashMap<ActivityLifecycleCallbacks, Boolean>());

    public void addListener(ActivityLifecycleCallbacks listener) {
        activityLifecycleCallbacks.add(listener);
    }

    void onCreate(){
        for (ActivityLifecycleCallbacks activityLifecycleCallbacks : Util.getSnapshot(this.activityLifecycleCallbacks)) {
            if(activityLifecycleCallbacks instanceof FragmentLifecycleCallbacks){
                activityLifecycleCallbacks.onCreate();
            }
        }
    }

    void onCreateView(){
        for (ActivityLifecycleCallbacks activityLifecycleCallbacks : Util.getSnapshot(this.activityLifecycleCallbacks)) {
            if(activityLifecycleCallbacks instanceof FragmentLifecycleCallbacks){
                ((FragmentLifecycleCallbacks) activityLifecycleCallbacks).onCreateView();
            }else if(activityLifecycleCallbacks instanceof ActivityLifecycleCallbacks) {
                activityLifecycleCallbacks.onCreate();
            }
        }
    }

    void onActivityCreated(){
        for (ActivityLifecycleCallbacks activityLifecycleCallbacks : Util.getSnapshot(this.activityLifecycleCallbacks)) {
            if(activityLifecycleCallbacks instanceof FragmentLifecycleCallbacks)
                ((FragmentLifecycleCallbacks) activityLifecycleCallbacks).onActivityCreated();
        }
    }

    void onStart() {
        for (ActivityLifecycleCallbacks activityLifecycleCallbacks : Util.getSnapshot(this.activityLifecycleCallbacks)) {
            activityLifecycleCallbacks.onStart();
        }
    }

    void onStop() {
        for (ActivityLifecycleCallbacks activityLifecycleCallbacks : Util.getSnapshot(this.activityLifecycleCallbacks)) {
            activityLifecycleCallbacks.onStop();
        }
    }

    void onDestroy() {
        for (ActivityLifecycleCallbacks activityLifecycleCallbacks : Util.getSnapshot(this.activityLifecycleCallbacks)) {
            activityLifecycleCallbacks.onDestroy();
        }
        activityLifecycleCallbacks.clear();
    }

}

