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

public class PresenterDemoActivity extends Activity implements IPresenterCreator<PresenterDemoContracts.Presenter> ,PresenterDemoContracts.View{

    private PresenterDemoContracts.Presenter p;


    public static void start(Context context){
        context.startActivity(new Intent(context,PresenterDemoActivity.class));
    }


    @Override
    public PresenterDemoContracts.Presenter create() {
        return new PresenterDemoPresenter();
    }

    @Override
    public void initPresenterFinished(PresenterDemoContracts.Presenter p) {
        this.p = p;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presenter_demo);
        Presenter.bind(this,this);
    }

    @Override
    public void showDate(String s) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }
}
