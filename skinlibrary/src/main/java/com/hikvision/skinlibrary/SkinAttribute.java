package com.hikvision.skinlibrary;

import android.util.AttributeSet;
import android.view.View;

import com.hikvision.skinlibrary.util.SkinThemeUitls;
import com.hikvision.skinlibrary.view.SkinAttrParms;
import com.hikvision.skinlibrary.view.SkinView;

import java.util.ArrayList;
import java.util.List;

/**
 * view属性处理
 *
 * @author hanpei
 * @version 1.0, 2019/9/6
 * @since 产品模块版本
 */
public class SkinAttribute {

    public static final List<String> list = new ArrayList<>();

    private ArrayList<SkinView> skinViews = new ArrayList<SkinView>();

    static {
        list.add("background");
        list.add("src");
        list.add("textColor");
        list.add("drawableLeft");
        list.add("drawableTop");
        list.add("drawableRight");
        list.add("drawableBottom");
    }

    /**
     * 保存view，分解属性，并对view进行换肤处理（当前皮肤可能不是默认时需要更换）
     *
     * @param view
     * @param attrs
     */
    public void loadView(View view, AttributeSet attrs) {



        ArrayList<SkinAttrParms> skinAttrParms = new ArrayList<>();
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            String attributeName = attrs.getAttributeName(i);
            if (list.contains(attributeName)) {
                String attributeValue = attrs.getAttributeValue(i);
                if (attributeValue.startsWith("#")) {
                    continue;
                }
                int id;
                if (attributeValue.startsWith("?")) {
                    int attrid = Integer.parseInt(attributeValue.substring(1));
                    id = SkinThemeUitls.getThemeResid(view.getContext(), new int[]{attrid})[0];
                } else {
                    id = Integer.parseInt(attributeValue.substring(1));
                }
                if (id != 0) {
                    SkinAttrParms attrParms = new SkinAttrParms(attributeName, id);
                    skinAttrParms.add(attrParms);
                }
            }
        }
        //将View与之对应的可以动态替换的属性集合 放入 集合中
        if (!skinAttrParms.isEmpty()) {
            SkinView skinView = new SkinView(view, skinAttrParms);
            skinView.applySkin();
            skinViews.add(skinView);
        }
    }

    /**
     * 进行换肤
     */
    public void applySkin() {
        for (SkinView skinView : skinViews) {
            skinView.applySkin();
        }
    }


}
