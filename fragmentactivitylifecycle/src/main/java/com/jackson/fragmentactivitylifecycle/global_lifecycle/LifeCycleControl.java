package com.jackson.fragmentactivitylifecycle.global_lifecycle;

import android.app.Application;
import android.util.Log;

import com.jackson.fragmentactivitylifecycle.log.L;

import java.util.ArrayList;

/**
 * Created by Jackson on 2017/5/10.
 * Version : 1
 * Details :
 */
public class LifeCycleControl {

    private static final String TAG = "LifeCycleControl";
    ArrayList<BaseLifeCycleCallbacks> mBaseLifeCycleCallbacksList;

    private LifeCycleControl() {
    }

    private static LifeCycleControl instance;

    private static LifeCycleControl get() {
        if (instance == null) {
            synchronized (LifeCycleControl.class) {
                if (instance == null) {
                    instance = new LifeCycleControl();
                }
            }
        }
        return instance;
    }

    public static LifeCycleControl init(Application application) {
        final LifeCycleControl control = get();
        if(control.mBaseLifeCycleCallbacksList==null){
            control.mBaseLifeCycleCallbacksList = new ArrayList<>();
            application.registerActivityLifecycleCallbacks(new LifeCycle(control));
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
        L.e(TAG,"registerCallbacks","size",mBaseLifeCycleCallbacksList.size());
    }

    public void registerCallbacks(BaseLifeCycleCallbacks callbacks) {
        registerCallbacks(callbacks, 0);
    }


    public boolean unRegisterCallbacks(BaseLifeCycleCallbacks callbacks) {
        return mBaseLifeCycleCallbacksList.remove(callbacks);
    }

    public void unRegisterAllCallbacks(){
        mBaseLifeCycleCallbacksList.clear();
    }

}
