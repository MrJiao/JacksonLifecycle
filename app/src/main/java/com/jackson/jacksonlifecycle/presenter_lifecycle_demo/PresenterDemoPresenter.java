package com.jackson.jacksonlifecycle.presenter_lifecycle_demo;

import com.jackson.presentelifecycle.LifeCyclePresenter;

/**
 * Created by jackson on 2017/5/16.
 */

public class PresenterDemoPresenter extends LifeCyclePresenter<PresenterDemoContracts.View> implements PresenterDemoContracts.Presenter{

    @Override
    public void onInitFinished() {
        super.onInitFinished();
        loadDate();
    }

    @Override
    public void loadDate() {
        //Do Something
        view.showDate("PresenterDemoPresenter");
    }

    @Override
    public void onStop() {
        super.onStop();

    }
}
