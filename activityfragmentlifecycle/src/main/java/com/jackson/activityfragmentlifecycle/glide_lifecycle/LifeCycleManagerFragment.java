package com.jackson.activityfragmentlifecycle.glide_lifecycle;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.jackson.activityfragmentlifecycle.log.Logger;

import java.util.HashSet;

/**
 * Created by Jackson on 2017/5/11.
 * Version : 1
 * Details :
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class LifeCycleManagerFragment extends android.app.Fragment {
    private Logger logger = new Logger("LifeCycleManagerFragment");

    private final ActivityFragmentLifecycle lifecycle;

    private final HashSet<LifeCycleManagerFragment> childRequestManagerFragments
            = new HashSet<>();
    private LifeCycleManagerFragment rootRequestManagerFragment;

    public LifeCycleManagerFragment() {
        this(new ActivityFragmentLifecycle());
    }

    @SuppressLint("ValidFragment")
    LifeCycleManagerFragment(ActivityFragmentLifecycle lifecycle) {
        this.lifecycle = lifecycle;
    }

    private void addChildRequestManagerFragment(LifeCycleManagerFragment child) {
        childRequestManagerFragments.add(child);
    }

    private void removeChildRequestManagerFragment(LifeCycleManagerFragment child) {
        childRequestManagerFragments.remove(child);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        logger.i("onAttach");
        rootRequestManagerFragment = LifeCycleRetriever.get()
                .getLifeCycleManagerFragment(getActivity().getFragmentManager());
        if (rootRequestManagerFragment != this) {
            rootRequestManagerFragment.addChildRequestManagerFragment(this);
        }
    }

    @Override
    public void onDetach() {
        logger.i("onDetach");
        super.onDetach();
        if (rootRequestManagerFragment != null) {
            rootRequestManagerFragment.removeChildRequestManagerFragment(this);
            rootRequestManagerFragment = null;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logger.i("onCreate");
        lifecycle.onCreate();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        logger.i("onCreateView");
        lifecycle.onCreateView();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        logger.i("onActivityCreated");
        lifecycle.onActivityCreated();
    }

    @Override
    public void onStart() {
        super.onStart();
        logger.i("onStart");
        lifecycle.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        logger.i("onPause");
        lifecycle.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        logger.i("onResume");
        lifecycle.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        logger.i("onStop");
        lifecycle.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        logger.i("onDestroy");
        lifecycle.onDestroy();
    }

    public void addListener(ActivityLifecycleCallbacks listener) {
        if(listener == null)return;
        lifecycle.addListener(listener);
    }
}
