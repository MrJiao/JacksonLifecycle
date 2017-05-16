package com.jackson.fragmentactivitylifecycle.glide_lifecycle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashSet;

/**
 * Created by Jackson on 2017/5/11.
 * Version : 1
 * Details :
 */
public class SupportLifeCycleManagerFragment  extends android.support.v4.app.Fragment {
    private final ActivityFragmentLifecycle lifecycle;

    private final HashSet<SupportLifeCycleManagerFragment> childRequestManagerFragments =
            new HashSet<>();
    private SupportLifeCycleManagerFragment rootRequestManagerFragment;

    public SupportLifeCycleManagerFragment() {
        this(new ActivityFragmentLifecycle());
    }

    @SuppressLint("ValidFragment")
    public SupportLifeCycleManagerFragment(ActivityFragmentLifecycle lifecycle) {
        this.lifecycle = lifecycle;
    }


    ActivityFragmentLifecycle getLifecycle() {
        return lifecycle;
    }

    private void addChildRequestManagerFragment(SupportLifeCycleManagerFragment child) {
        childRequestManagerFragments.add(child);
    }

    private void removeChildRequestManagerFragment(SupportLifeCycleManagerFragment child) {
        childRequestManagerFragments.remove(child);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        rootRequestManagerFragment = LifeCycleRetriever.get()
                .getSupportLifeCycleManagerFragment(getActivity().getSupportFragmentManager());

        if (rootRequestManagerFragment != this) {
            rootRequestManagerFragment.addChildRequestManagerFragment(this);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (rootRequestManagerFragment != null) {
            rootRequestManagerFragment.removeChildRequestManagerFragment(this);
            rootRequestManagerFragment = null;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycle.onCreate();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        lifecycle.onCreateView();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lifecycle.onActivityCreated();
    }

    @Override
    public void onStart() {
        super.onStart();
        lifecycle.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        lifecycle.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        lifecycle.onDestroy();
    }

    public void addListener(ActivityLifecycleCallbacks listener) {
        if(listener == null)return;
        getLifecycle().addListener(listener);
    }
}