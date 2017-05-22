package com.jackson.jacksonlifecycle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jackson.jacksonlifecycle.glide_lifecycle_demo.GlideLifecycleDemoActivity;
import com.jackson.jacksonlifecycle.global_activity_lifecycle_demo.GlobalLifeDemoActivity;
import com.jackson.jacksonlifecycle.presenter_lifecycle_demo.PresenterDemoActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_glide_lifecycle).setOnClickListener(this);
        findViewById(R.id.btn_global_activity_lifecycle).setOnClickListener(this);
        findViewById(R.id.btn_presenter_lifecycle).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_glide_lifecycle:
                GlideLifecycleDemoActivity.start(this);
                break;
            case R.id.btn_global_activity_lifecycle:
                GlobalLifeDemoActivity.start(this);
                break;
            case R.id.btn_presenter_lifecycle:
                PresenterDemoActivity.start(this);
                break;
        }
    }
}
