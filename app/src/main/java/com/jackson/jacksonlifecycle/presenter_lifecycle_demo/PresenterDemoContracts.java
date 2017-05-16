package com.jackson.jacksonlifecycle.presenter_lifecycle_demo;

import com.jackson.presentelifecycle.ILifeCyclePresenter;

/**
 * Created by jackson on 2017/5/16.
 */

public interface PresenterDemoContracts {

    interface View{
        void showDate(String s);
    }

    interface Presenter extends ILifeCyclePresenter<PresenterDemoContracts.View>{
        void loadDate();
    }

}
