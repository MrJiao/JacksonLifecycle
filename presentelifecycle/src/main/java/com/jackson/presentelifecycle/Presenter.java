package com.jackson.presentelifecycle;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;

import com.jackson.fragmentactivitylifecycle.glide_lifecycle.Lifecycle;


/**
 * Created by jackson on 2017/5/14.
 */
public class Presenter {

    public static void bind(Activity activity, IPresenterCreator creator) {
        Lifecycle.observe(activity, new PresenterLifecycle(activity, creator));
    }

    public static void bind(FragmentActivity activity, IPresenterCreator creator) {
        Lifecycle.observe(activity, new PresenterLifecycle(activity, creator));
    }

    public static void bind(android.app.Fragment fragment, IPresenterCreator creator) {
        Lifecycle.observe(fragment, new FragmentPresenterLifecycle(fragment, creator));
    }

    public static void bind(android.support.v4.app.Fragment fragment, IPresenterCreator creator) {
        Lifecycle.observe(fragment, new FragmentPresenterLifecycle(fragment, creator));
    }


}
