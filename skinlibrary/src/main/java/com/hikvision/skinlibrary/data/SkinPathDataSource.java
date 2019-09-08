package com.hikvision.skinlibrary.data;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * 皮肤管理
 *
 * @author hanpei
 * @version 1.0, 2019/9/6
 * @since 产品模块版本
 */
public class SkinPathDataSource {

    private static final String SKIN_SHARED = "skin-peeler-lib";
    private static final String KEY_SKIN_PATH = "skin-path";
    private final SharedPreferences mPref;

    private static Application mApplication;

    public static void init(Application application) {
        mApplication = application;
    }


    public void saveSkinPath(String path) {
        mPref.edit().putString(KEY_SKIN_PATH, path).apply();
    }

    public String getSkinPath() {
        return mPref.getString(KEY_SKIN_PATH, null);
    }


    public static SkinPathDataSource getInstance() {
        return Holder.instance;
    }

    private SkinPathDataSource() {
        mPref = mApplication.getSharedPreferences(SKIN_SHARED, Context.MODE_PRIVATE);
    }

    private static class Holder {
        private static SkinPathDataSource instance = new SkinPathDataSource();
    }

}
