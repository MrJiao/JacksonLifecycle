package com.jackson.presentelifecycle;

import android.content.Context;
import android.content.Loader;

import com.jackson.activityfragmentlifecycle.log.Logger;


/**
 * Created by Jackson on 2017/5/4.
 * Version : 1
 * Details :
 */
public class PresenterLoader<T extends ILifeCyclePresenter> extends Loader {

    private T presenter;
    private IPresenterCreator<T> factory;
    private Logger logger = new Logger("PresenterLoader");
    private boolean isFirst = true;

    public PresenterLoader(Context context, IPresenterCreator<T> factory) {
        super(context);
        this.factory = factory;
    }

    @Override
    protected void onStartLoading() {
        logger.i("onStartLoading");
        // 如果已经有Presenter实例那就直接返回
        if (presenter != null) {
            deliverResult(presenter);
            return;
        }
        // 如果没有 就促使加载
        forceLoad();
    }

    @Override
    protected void onForceLoad() {
        logger.i("onForceLoad");
        // 实例化 Presenter
        presenter = factory.create();
        // 返回 Presenter
        deliverResult(presenter);
    }

    @Override
    protected void onReset() {
        logger.i("onReset");
        presenter.onDestroyed();
        presenter = null;
    }

    public T getPresenter(){
        return presenter;
    }


    public boolean isFirstCreate(){
        return isFirst;
    }

    public void setIsFirstCreate(boolean isFirst){
        this.isFirst = isFirst;
    }

}
