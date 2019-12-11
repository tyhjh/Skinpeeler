package com.hikvision.skinlibrary;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.text.TextUtils;

import com.hikvision.skinlibrary.app.SkinActivityLifecycleCallbacks;
import com.hikvision.skinlibrary.data.SkinPathDataSource;
import com.hikvision.skinlibrary.util.SkinResourcess;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Observable;

/**
 * @author hanpei
 * @version 1.0, 2019/9/6
 * @since 产品模块版本
 */
public class SkinManager extends Observable {

    private static Application mApplication;

    /**
     * 初始化
     *
     * @param application
     */
    public static void init(Application application) {
        mApplication = application;
        application.registerActivityLifecycleCallbacks(new SkinActivityLifecycleCallbacks());
        SkinPathDataSource.init(application);
        SkinResourcess.init(application);
        getInstance().loadSkin(SkinPathDataSource.getInstance().getSkinPath());
    }


    /**
     * 进行换肤
     *
     * @param path 路径为插件包地址，为空则恢复默认
     */
    public boolean loadSkin(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }

        File file = new File(path);
        if (!file.exists()) {
            return false;
        }
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method method = assetManager.getClass().getMethod("addAssetPath", String.class);
            method.setAccessible(true);
            method.invoke(assetManager, path);
            Resources resources = mApplication.getResources();
            Resources skinRes = new Resources(assetManager, resources.getDisplayMetrics(), resources.getConfiguration());

            //获取外部Apk(皮肤包) 包名
            PackageManager mPm = mApplication.getPackageManager();
            PackageInfo info = mPm.getPackageArchiveInfo(path, PackageManager
                    .GET_ACTIVITIES);
            String packageName = info.packageName;
            SkinResourcess.getInstance().applySkin(skinRes, packageName);
            //记录
            SkinPathDataSource.getInstance().saveSkinPath(path);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        setChanged();
        notifyObservers();
        return true;
    }

    /**
     * 清除换肤，恢复默认
     */
    public void clearSkin() {
        SkinPathDataSource.getInstance().saveSkinPath(null);
        SkinResourcess.getInstance().reset();
        setChanged();
        notifyObservers();
    }


    /**
     * 获取资源文件的Resources
     *
     * @param path
     * @return
     */
    public Resources getSkinRes(String path) {
        Resources resources = mApplication.getResources();
        if (TextUtils.isEmpty(path)) {
            return resources;
        } else {
            try {
                AssetManager assetManager = null;
                assetManager = AssetManager.class.newInstance();
                Method method = assetManager.getClass().getMethod("addAssetPath", String.class);
                method.setAccessible(true);
                method.invoke(assetManager, path);
                Resources skinRes = new Resources(assetManager, resources.getDisplayMetrics(), resources.getConfiguration());
                return skinRes;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            return resources;
        }


    }


    public static SkinManager getInstance() {
        return Holder.instance;
    }

    private SkinManager() {

    }

    private static class Holder {
        private static final SkinManager instance = new SkinManager();
    }


}
