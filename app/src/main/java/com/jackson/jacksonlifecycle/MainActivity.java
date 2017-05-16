package com.jackson.jacksonlifecycle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jackson.presentelifecycle.Presenter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
