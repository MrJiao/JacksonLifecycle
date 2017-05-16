package com.jackson.presentelifecycle;

/**
 * Created by Jackson on 2017/5/4.
 * Version : 1
 * Details :
 */
public interface IPresenterCreator<T extends ILifeCyclePresenter> {
    T create();
    void initPresenterFinished(T p);
}