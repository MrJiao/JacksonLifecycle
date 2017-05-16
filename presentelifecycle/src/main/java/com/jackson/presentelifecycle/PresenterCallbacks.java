package com.jackson.presentelifecycle;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;

import com.jackson.fragmentactivitylifecycle.log.Logger;


/**
 * Created by jackson on 2017/5/4.
 */
class PresenterCallbacks<T extends ILifeCyclePresenter> implements LoaderManager.LoaderCallbacks<T> {

    private Logger logger = new Logger("PresenterCallbacks");
    private final IPresenterCreator<T> presenterCreator;
    private final Context context;

    public PresenterCallbacks(Context context, IPresenterCreator<T> presenterCreator){
        this.presenterCreator = presenterCreator;
        this.context=context;
    }

    @Override
    public Loader<T> onCreateLoader(int id, Bundle args) {
        logger.i("onCreateLoader");
        return new PresenterLoader<>(context, presenterCreator);
    }

    @Override
    public void onLoadFinished(Loader<T> loader, T data) {
        logger.i("onLoadFinished");
    }

    @Override
    public void onLoaderReset(Loader<T> loader) {
        logger.i("onLoaderReset");
    }
}
