package com.hikvision.skinlibrary.view;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.view.ViewCompat;

import com.hikvision.skinlibrary.util.SkinResourcess;

import java.util.List;

/**
 * @author hanpei
 */
public class SkinView {
    View view;
    List<SkinAttrParms> parms;

    public SkinView(View view, List<SkinAttrParms> parms) {
        this.view = view;
        this.parms = parms;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    /**
     * 加载属性
     */
    public void applySkin() {
        for (SkinAttrParms parms : parms) {
            Drawable left = null, top = null, right = null, bottom = null;
            switch (parms.getAttrName()) {
                case "background":
                    Object background = SkinResourcess.getInstance().getBackground(parms
                            .getId());
                    //Color
                    if (background instanceof Integer) {
                        view.setBackgroundColor((Integer) background);
                    } else {
                        ViewCompat.setBackground(view, (Drawable) background);
                    }
                    break;
                case "src":
                    background = SkinResourcess.getInstance().getBackground(parms
                            .getId());
                    if (background instanceof Integer) {
                        ((ImageView) view).setImageDrawable(new ColorDrawable((Integer)
                                background));
                    } else {
                        ((ImageView) view).setImageDrawable((Drawable) background);
                    }
                    break;
                case "textColor":
                    ((TextView) view).setTextColor(SkinResourcess.getInstance().getColorStateList
                            (parms.getId()));
                    break;
                case "drawableLeft":
                    left = SkinResourcess.getInstance().getDrawable(parms.getId());
                    break;
                case "drawableTop":
                    top = SkinResourcess.getInstance().getDrawable(parms.getId());
                    break;
                case "drawableRight":
                    right = SkinResourcess.getInstance().getDrawable(parms.getId());
                    break;
                case "drawableBottom":
                    bottom = SkinResourcess.getInstance().getDrawable(parms.getId());
                    break;
                default:
                    break;
            }
            if (null != left || null != right || null != top || null != bottom) {
                ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(left, top, right,
                        bottom);
            }
        }
    }
}