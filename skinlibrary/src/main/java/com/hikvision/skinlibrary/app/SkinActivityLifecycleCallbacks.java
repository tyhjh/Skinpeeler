package com.hikvision.skinlibrary.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.hikvision.skinlibrary.SkinFactory;
import com.hikvision.skinlibrary.SkinManager;

import java.lang.reflect.Field;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.LayoutInflaterCompat;

/**
 * 监听activity生命周期变化
 *
 * @author hanpei
 * @version 1.0, 2019/9/6
 * @since 产品模块版本
 */
public class SkinActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {

    private HashMap<Activity, SkinFactory> mLayoutFactoryMap = new HashMap<>();

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        //获得Activity的布局加载器
        try {
            //Android 布局加载器 使用 mFactorySet 标记是否设置过Factory
            //如设置过源码会抛出异常
            //设置 mFactorySet 标签为false
            Field field = LayoutInflater.class.getDeclaredField("mFactorySet");
            field.setAccessible(true);
            field.setBoolean(layoutInflater, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        SkinFactory skinLayoutFactory = new SkinFactory();
        LayoutInflaterCompat.setFactory2(layoutInflater, skinLayoutFactory);
        //注册观察者
        SkinManager.getInstance().addObserver(skinLayoutFactory);
        mLayoutFactoryMap.put(activity, skinLayoutFactory);

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        //删除观察者
        SkinFactory skinLayoutFactory = mLayoutFactoryMap.remove(activity);
        SkinManager.getInstance().deleteObserver(skinLayoutFactory);
    }
}
