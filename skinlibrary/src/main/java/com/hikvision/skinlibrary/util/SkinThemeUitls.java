package com.hikvision.skinlibrary.util;

import android.content.Context;
import android.content.res.TypedArray;


/**
 * @author hanpei
 */
public class SkinThemeUitls {
    public static int[] getThemeResid(Context context, int[] attrs) {
        int[] resIds = new int[]{attrs.length};
        TypedArray typedArray = context.obtainStyledAttributes(attrs);
        for (int i = 0; i < typedArray.length(); i++) {
            int resourceId = typedArray.getResourceId(i, 0);
            resIds[i] = resourceId;
        }
        typedArray.recycle();
        return resIds;
    }
}
