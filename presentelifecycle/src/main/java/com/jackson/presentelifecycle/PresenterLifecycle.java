package com.jackson.presentelifecycle;

import android.app.Activity;

import com.jackson.activityfragmentlifecycle.glide_lifecycle.ActivityLifecycleCallbacks;
import com.jackson.activityfragmentlifecycle.log.Logger;


/**
 * Created by jackson on 2017/5/14.
 * Presenter的生命周期回调控制类
 */
public class PresenterLifecycle implements ActivityLifecycleCallbacks {
    private Logger logger = new Logger("PresenterLifecycle");
    private Activity activity;
    private final IPresenterCreator creator;
    private PresenterLoader loader;
    private boolean isFirst = true;

    public PresenterLifecycle(Activity activity, IPresenterCreator creator) {
        if(activity ==null)
            throw new NullPointerException("activity must not be null");
        this.activity = activity;
        this.creator = creator;
    }

    @Override
    public void onCreate() {
        if (loader == null) {
            logger.i("onCreate");
            loader = (PresenterLoader) activity.getLoaderManager().initLoader(0, null, new PresenterCallbacks<>(activity, creator));
        }
    }

    @Override
    public void onStart() {
        logger.i("onStart");
        final ILifeCyclePresenter presenter = getPresenter();
        presenter.onAttachView(activity);
        if(isFirst){
            presenter.onInitFinished();
            creator.initPresenterFinished(presenter);
            isFirst = false;
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
    }

    private ILifeCyclePresenter getPresenter() {
        return loader == null ? null : loader.getPresenter();
    }
}
