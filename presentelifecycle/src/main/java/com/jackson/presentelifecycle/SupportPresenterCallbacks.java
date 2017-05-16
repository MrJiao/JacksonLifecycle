package com.jackson.presentelifecycle;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;

import com.jackson.fragmentactivitylifecycle.log.Logger;

/**
 * Created by jackson on 2017/5/4.
 */
class SupportPresenterCallbacks<T extends ILifeCyclePresenter> implements LoaderManager.LoaderCallbacks<T> {

    private Logger logger = new Logger("PresenterCallbacks");
    private final IPresenterCreator<T> presenterCreator;
    private final Context context;

    public SupportPresenterCallbacks(Context context, IPresenterCreator<T> presenterCreator){
        this.presenterCreator = presenterCreator;
        this.context=context;
    }

    @Override
    public android.support.v4.content.Loader<T> onCreateLoader(int id, Bundle args) {
        logger.i("onCreateLoader");
        return new SupportPresenterLoader<>(context, presenterCreator);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<T> loader, T data) {
        logger.i("onLoadFinished");
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<T> loader) {
        logger.i("onLoaderReset");
    }
}
