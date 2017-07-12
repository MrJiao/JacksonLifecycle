package com.jackson.presentelifecycle;

import android.app.Activity;

import com.jackson.activityfragmentlifecycle.glide_lifecycle.ActivityLifecycleCallbacks;
import com.jackson.activityfragmentlifecycle.log.Logger;

import static android.R.attr.fragment;


/**
 * Created by jackson on 2017/5/14.
 * Presenter的生命周期回调控制类
 */
public class PresenterLifecycle implements ActivityLifecycleCallbacks {
    private Logger logger = new Logger("PresenterLifecycle");
    private Activity activity;
    private final IPresenterCreator creator;
    private PresenterLoader loader;

    public PresenterLifecycle(Activity activity, IPresenterCreator creator) {
        if(activity ==null)
            throw new NullPointerException("activity must not be null");
        this.activity = activity;
        this.creator = creator;
    }

    @Override
    public void onCreate() {
        if(loader == null){
            loader = (PresenterLoader) activity.getLoaderManager().getLoader(0);
        }
        if (loader == null) {
            logger.i("onCreate");
            loader = (PresenterLoader) activity.getLoaderManager().initLoader(0, null, new PresenterCallbacks<>(activity, creator));
        }
    }

    @Override
    public void onStart() {
        logger.i("onStart");
        final ILifeCyclePresenter presenter = getPresenter();
        if(presenter == null)return;
        presenter.onAttachView(activity);
        presenter.onAttachedView();
        if(loader.isFirstCreate()){
            presenter.onInitFinished();
            creator.initPresenterFinished(presenter);
            loader.setIsFirstCreate(false);
        }
    }

    @Override
    public void onStop() {
        logger.i("onStop");
        getPresenter().onStop();
    }

    @Override
    public void onDestroy() {
        logger.i("onDestroy");
        loader = null;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    private ILifeCyclePresenter getPresenter() {
        return loader == null ? null : loader.getPresenter();
    }
}
