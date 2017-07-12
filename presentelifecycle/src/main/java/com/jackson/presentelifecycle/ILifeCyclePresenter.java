package com.jackson.presentelifecycle;

/**
 * Created by Jackson on 2017/5/4.
 * Version : 1
 * Details :带有生命周期控制的presenter基类
 */
public interface ILifeCyclePresenter<V> {
    void onAttachView(V view);
    void onAttachedView();
    void onInitFinished();
    void onStop();
    void onDestroyed();
}
