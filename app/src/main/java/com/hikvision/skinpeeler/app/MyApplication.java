package com.hikvision.skinpeeler.app;

import android.app.Application;

import com.hikvision.skinlibrary.SkinManager;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author hanpei
 * @version 1.0, 2019/9/6
 * @since 产品模块版本
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.init(this);
    }
}
