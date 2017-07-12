package com.jackson.presentelifecycle;

import android.support.annotation.NonNull;

import com.jackson.activityfragmentlifecycle.glide_lifecycle.FragmentLifecycleCallbacks;

/**
 * Created by jackson on 2017/5/14.
 */

public class FragmentPresenterLifecycle implements FragmentLifecycleCallbacks {

    private android.app.Fragment fragment;
    private android.support.v4.app.Fragment supportFragment;
    private final IPresenterCreator creator;
    private PresenterLoader loader;
    private SupportPresenterLoader supportLoader;

    public FragmentPresenterLifecycle(@NonNull android.app.Fragment fragment, IPresenterCreator creator) {
        this.fragment = fragment;
        this.creator = creator;
    }

    public FragmentPresenterLifecycle(@NonNull android.support.v4.app.Fragment supportFragment, IPresenterCreator creator) {
        this.supportFragment = supportFragment;
        this.creator = creator;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void onActivityCreated() {
        if (fragment != null) {
            if (loader == null) {
                loader = (PresenterLoader) fragment.getLoaderManager().getLoader(0);
                return;
            }
            if (loader == null) {
                loader = (PresenterLoader) fragment.getLoaderManager().initLoader(0, null, new PresenterCallbacks<>(fragment.getActivity(), creator));
                return;
            }
        } else {
            if (supportLoader == null) {
                supportLoader = (SupportPresenterLoader) supportFragment.getLoaderManager().getLoader(0);
                return;
            }
            if (supportLoader == null) {
                supportLoader = (SupportPresenterLoader) supportFragment.getLoaderManager().initLoader(0, null, new SupportPresenterCallbacks<>(supportFragment.getContext(), creator));
                return;
            }
        }
    }

    @Override
    public void onStart() {
        final ILifeCyclePresenter presenter = getPresenter();
        if (presenter == null) return;
        if (fragment != null) {
            presenter.onAttachView(fragment);
            presenter.onAttachedView();
        } else if (supportFragment != null) {
            presenter.onAttachView(supportFragment);
            presenter.onAttachedView();
        }
        if (isFirstCreate()) {
            presenter.onInitFinished();
            creator.initPresenterFinished(presenter);
            setIsFirstCreate(false);
        }
    }

    @Override
    public void onStop() {
        getPresenter().onStop();
    }

    @Override
    public void onDestroy() {
        loader = null;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    private ILifeCyclePresenter getPresenter() {
        if(loader!=null){
            return loader.getPresenter();
        }
        if(supportLoader!=null){
            return supportLoader.getPresenter();
        }
        return null;
    }

    private boolean isFirstCreate() {
        if (loader != null) {
            return loader.isFirstCreate();
        }
        if (supportLoader != null) {
            return supportLoader.isFirstCreate();
        }
        return false;
    }

    private void setIsFirstCreate(boolean isFirstCreate) {
        if (loader != null) {
            loader.setIsFirstCreate(isFirstCreate);
        }
        if (supportLoader != null) {
            supportLoader.setIsFirstCreate(isFirstCreate);
        }
    }

}