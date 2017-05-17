package com.jackson.jacksonlifecycle.global_activity_lifecycle_demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.jackson.activityfragmentlifecycle.log.Logger;
import com.jackson.jacksonlifecycle.R;

/**
 * Created by Jackson on 2017/5/17.
 * Version : 1
 * Details :
 */
public class GlobalLifeDemoActivity extends Activity implements InitCallBack.InitActivity {

    Logger logger = new Logger("GlobalLifeDemoActivity");

    public static void start(Context context){
        context.startActivity(new Intent(context,GlobalLifeDemoActivity.class));
    }

    @Override
    public int getViewRes() {
        logger.i("getViewRes");
        return R.layout.activity_global_lifecycle_demo;
    }

    @Override
    public void initView() {
        logger.i("initView");
    }

    @Override
    public void initListener() {
        logger.i("initListener");
    }

}
