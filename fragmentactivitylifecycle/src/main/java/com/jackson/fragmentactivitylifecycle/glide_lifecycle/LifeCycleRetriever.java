package com.jackson.fragmentactivitylifecycle.glide_lifecycle;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jackson on 2017/5/11.
 * Version : 1
 * Details :
 */
class LifeCycleRetriever implements Handler.Callback{

    private static final LifeCycleRetriever INSTANCE = new LifeCycleRetriever();
    static final String FRAGMENT_TAG = "com.jackson.lifecycle.manager";
    private static final int ID_REMOVE_FRAGMENT_MANAGER = 1;
    private static final int ID_REMOVE_SUPPORT_FRAGMENT_MANAGER = 2;
    private static final String TAG = "LifeCycleRetriever";

    /** Main thread handler to handle cleaning up pending fragment maps. */
    private final Handler handler;

    /** Pending adds for RequestManagerFragments. */
    final Map<FragmentManager, LifeCycleManagerFragment> pendingRequestManagerFragments =
            new HashMap<>();

    /** Pending adds for SupportRequestManagerFragments. */
    final Map<android.support.v4.app.FragmentManager, SupportLifeCycleManagerFragment> pendingSupportRequestManagerFragments =
            new HashMap<>();

    /**
     * Retrieves and returns the RequestManagerRetriever singleton.
     */
    public static LifeCycleRetriever get() {
        return INSTANCE;
    }

    LifeCycleRetriever() {
        handler = new Handler(Looper.getMainLooper(), this /* Callback */);
    }


    public void observe(Activity activity, ActivityLifecycleCallbacks activityLifecycleCallbacks){
        Util.assertMainThread();
        assertNotDestroyed(activity);
        android.app.FragmentManager fm = activity.getFragmentManager();
        getLifeCycleManagerFragment(fm).addListener(activityLifecycleCallbacks);
    }

    public void observe(FragmentActivity activity,ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        Util.assertMainThread();
        assertNotDestroyed(activity);
        android.support.v4.app.FragmentManager fm = activity.getSupportFragmentManager();
        getSupportLifeCycleManagerFragment(fm).addListener(activityLifecycleCallbacks);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void observe(android.app.Fragment fragment,FragmentLifecycleCallbacks activityLifecycleCallbacks){
        Util.assertMainThread();
        if (fragment.getActivity() == null) {
            throw new IllegalArgumentException("You cannot start a load on a fragment before it is attached");
        }
        android.app.FragmentManager fm = fragment.getChildFragmentManager();
        getLifeCycleManagerFragment(fm).addListener(activityLifecycleCallbacks);
    }


    public void observe(Fragment fragment, FragmentLifecycleCallbacks activityLifecycleCallbacks){
        Util.assertMainThread();
        if (fragment.getActivity() == null) {
            throw new IllegalArgumentException("You cannot start a load on a fragment before it is attached");
        }
        android.support.v4.app.FragmentManager fm = fragment.getChildFragmentManager();
        getSupportLifeCycleManagerFragment(fm).addListener(activityLifecycleCallbacks);
    }

    SupportLifeCycleManagerFragment getSupportLifeCycleManagerFragment(final android.support.v4.app.FragmentManager fm) {
        SupportLifeCycleManagerFragment current = (SupportLifeCycleManagerFragment) fm.findFragmentByTag(FRAGMENT_TAG);
        if (current == null) {
            current = pendingSupportRequestManagerFragments.get(fm);
            if (current == null) {
                current = new SupportLifeCycleManagerFragment();
                pendingSupportRequestManagerFragments.put(fm, current);
                fm.beginTransaction().add(current, FRAGMENT_TAG).commitAllowingStateLoss();
                handler.obtainMessage(ID_REMOVE_SUPPORT_FRAGMENT_MANAGER, fm).sendToTarget();
            }
        }
        return current;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static void assertNotDestroyed(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed()) {
            throw new IllegalArgumentException("You cannot start a load for a destroyed activity");
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    LifeCycleManagerFragment getLifeCycleManagerFragment(final android.app.FragmentManager fm) {
        LifeCycleManagerFragment current = (LifeCycleManagerFragment) fm.findFragmentByTag(FRAGMENT_TAG);
        if (current == null) {
            current = pendingRequestManagerFragments.get(fm);
            if (current == null) {
                current = new LifeCycleManagerFragment();
                pendingRequestManagerFragments.put(fm, current);
                fm.beginTransaction().add(current, FRAGMENT_TAG).commitAllowingStateLoss();
                handler.obtainMessage(ID_REMOVE_FRAGMENT_MANAGER, fm).sendToTarget();
            }
        }
        return current;
    }


    @Override
    public boolean handleMessage(Message message) {
        boolean handled = true;
        Object removed = null;
        Object key = null;
        switch (message.what) {
            case ID_REMOVE_FRAGMENT_MANAGER:
                android.app.FragmentManager fm = (android.app.FragmentManager) message.obj;
                key = fm;
                removed = pendingRequestManagerFragments.remove(fm);
                break;
            case ID_REMOVE_SUPPORT_FRAGMENT_MANAGER:
                android.support.v4.app.FragmentManager supportFm = (android.support.v4.app.FragmentManager) message.obj;
                key = supportFm;
                removed = pendingSupportRequestManagerFragments.remove(supportFm);
                break;
            default:
                handled = false;
        }
        if (handled && removed == null && Log.isLoggable(TAG, Log.WARN)) {
            Log.w(TAG, "Failed to remove expected request manager fragment, manager: " + key);
        }
        return handled;
    }

}
