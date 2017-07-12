package com.jackson.presentelifecycle;

/**
 * Created by Jackson on 2017/5/16.
 * Version : 1
 * Details :
 */
public class LifeCyclePresenter<V> implements ILifeCyclePresenter<V> {

    public V view;

    @Override
    public void onAttachView(V view) {
        this.view = view;
    }

    @Override
    public void onAttachedView() {

    }

    @Override
    public void onInitFinished() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroyed() {

    }



}
