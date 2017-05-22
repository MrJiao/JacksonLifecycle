package com.jackson.jacksonlifecycle.glide_lifecycle_demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jackson.jacksonlifecycle.MainActivity;
import com.jackson.jacksonlifecycle.R;

/**
 * Created by Jackson on 2017/5/17.
 * Version : 1
 * Details :
 */
public class GlideLifecycleDemoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_demo);
        SomeControl.get(this);
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context,GlideLifecycleDemoActivity.class));
    }
}
