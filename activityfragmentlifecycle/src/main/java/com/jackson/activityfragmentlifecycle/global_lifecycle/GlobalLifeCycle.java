package com.jackson.activityfragmentlifecycle.global_lifecycle;

import android.app.Application;
import android.util.Log;

import com.jackson.activityfragmentlifecycle.log.L;

import java.util.ArrayList;

/**
 * Created by Jackson on 2017/5/10.
 * Version : 1
 * Details :
 */
public class GlobalLifeCycle {

    private static final String TAG = "GlobalLifeCycle";
    ArrayList<BaseLifeCycleCallbacks> mBaseLifeCycleCallbacksList;

    private GlobalLifeCycle() {
    }

    private static GlobalLifeCycle instance;

    private static GlobalLifeCycle get() {
        if (instance == null) {
            synchronized (GlobalLifeCycle.class) {
                if (instance == null) {
                    instance = new GlobalLifeCycle();
                }
            }
        }
        return instance;
    }

    public static GlobalLifeCycle init(Application application) {
        final GlobalLifeCycle control = get();
        if(control.mBaseLifeCycleCallbacksList==null){
            control.mBaseLifeCycleCallbacksList = new ArrayList<>();
            application.registerActivityLifecycleCallbacks(new GlobalActivityLifecycleCallbacks(control));
        }
        return control;
    }

    /**
     * @param callbacks
     * detail:排序插入，保证优先级高的在前，先被执行
     */
    public void registerCallbacks(BaseLifeCycleCallbacks callbacks, int priority) {
        if (mBaseLifeCycleCallbacksList.contains(callbacks)) {
            Log.w(TAG, "callbacks 已经存在了，不要重复添加");
            return;
        }

        callbacks.setPriority(priority);
        if (mBaseLifeCycleCallbacksList.size() == 0) {
            mBaseLifeCycleCallbacksList.add(callbacks);
            return;
        }

        int position = -1;
        for (int i = 0; i < mBaseLifeCycleCallbacksList.size(); i++) {
            if (mBaseLifeCycleCallbacksList.get(i).getPriority() <= priority) {
                position = i;
                break;
            }
        }
        if (position == -1) {
            mBaseLifeCycleCallbacksList.add(callbacks);
            return;
        }
        mBaseLifeCycleCallbacksList.add(position, callbacks);
        L.i(TAG,"registerCallbacks","size",mBaseLifeCycleCallbacksList.size());
    }

    public GlobalLifeCycle registerCallbacks(BaseLifeCycleCallbacks callbacks) {
        registerCallbacks(callbacks, 0);
        return this;
    }


    public boolean unRegisterCallbacks(BaseLifeCycleCallbacks callbacks) {
        return mBaseLifeCycleCallbacksList.remove(callbacks);
    }

    public void unRegisterAllCallbacks(){
        mBaseLifeCycleCallbacksList.clear();
    }

}
