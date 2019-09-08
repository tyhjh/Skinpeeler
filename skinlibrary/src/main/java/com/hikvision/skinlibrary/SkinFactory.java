package com.hikvision.skinlibrary;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

/**
 * Factory类，初始化view，并记录所有的view
 *
 * @author hanpei
 * @version 1.0, 2019/9/6
 * @since 产品模块版本
 */
public class SkinFactory implements LayoutInflater.Factory2, Observer {


    /**
     * 属性处理类
     */
    SkinAttribute mSkinAttribute;

    /**
     * 保存view的构造方法
     */
    private static final HashMap<String, Constructor<? extends View>> sConstructorMap =
            new HashMap<String, Constructor<? extends View>>();

    static final Class<?>[] mConstructorSignature = new Class[]{
            Context.class, AttributeSet.class};


    public final String[] a = new String[]{
            "android.widget.",
            "android.view.",
            "android.webkit."
    };

    public SkinFactory() {
        mSkinAttribute = new SkinAttribute();
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attributeSet) {
        View view = createViewFormTag(name, context, attributeSet);
        if (view == null) {
            view = createView(name, context, attributeSet);
        }
        if (view != null) {
            mSkinAttribute.loadView(view, attributeSet);
        }
        return view;
    }

    @Override
    public void update(Observable observable, Object o) {
        //接受到换肤请求
        mSkinAttribute.applySkin();
    }


    /**
     * 参考LayoutInflater源码
     *
     * @param name
     * @param context
     * @param attrs
     * @return
     */
    private View createViewFormTag(String name, Context context, AttributeSet attrs) {
        //包含自定义控件
        if (-1 != name.indexOf('.')) {
            return null;
        }
        View view = null;
        for (int i = 0; i < a.length; i++) {
            view = createView(a[i] + name, context, attrs);
            if (view != null) {
                break;
            }
        }
        return view;
    }


    /**
     * 参考LayoutInflater源码
     * 获取构造函数，创建view
     *
     * @param name
     * @param context
     * @param attrs
     * @return
     */
    private View createView(String name, Context context, AttributeSet attrs) {
        Constructor<? extends View> constructor = findConstructor(context, name);
        try {
            return constructor.newInstance(context, attrs);
        } catch (Exception e) {
        }
        return null;
    }


    /**
     * 参考LayoutInflater源码
     * 通过反射获取View构造函数
     *
     * @param context
     * @param name
     * @return
     */
    private Constructor<? extends View> findConstructor(Context context, String name) {
        Constructor<? extends View> constructor = sConstructorMap.get(name);
        if (null == constructor) {
            try {
                Class<? extends View> clazz = context.getClassLoader().loadClass
                        (name).asSubclass(View.class);
                constructor = clazz.getConstructor(mConstructorSignature);
                sConstructorMap.put(name, constructor);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return constructor;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull String s, @NonNull Context context, @NonNull AttributeSet attributeSet) {
        return null;
    }

}
