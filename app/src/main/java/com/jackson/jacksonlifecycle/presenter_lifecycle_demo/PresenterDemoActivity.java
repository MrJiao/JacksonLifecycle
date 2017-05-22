package com.jackson.jacksonlifecycle.presenter_lifecycle_demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.jackson.jacksonlifecycle.R;
import com.jackson.presentelifecycle.IPresenterCreator;
import com.jackson.presentelifecycle.Presenter;

/**
 * Created by jackson on 2017/5/16.
 */

public class PresenterDemoActivity extends Activity implements IPresenterCreator<PresenterDemoContracts.Presenter>{

    private PresenterDemoContracts.Presenter p;

    @Override
    public PresenterDemoContracts.Presenter create() {
        return new PresenterDemoPresenter();
    }

    @Override
    public void initPresenterFinished(PresenterDemoContracts.Presenter p) {
        this.p = p;//初始化完成后回调这个方法传入绑定好View的Presenter
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presenter_demo);
        Presenter.bind(this,this);//为了绑定activity生命周期
    }

}
