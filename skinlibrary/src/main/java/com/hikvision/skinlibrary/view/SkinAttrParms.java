package com.hikvision.skinlibrary.view;

/**
 * View属性类
 *
 * @author hanpei
 */
public class SkinAttrParms {
    private String attrName;
    private int id;

    public SkinAttrParms(String attrName, int id) {
        this.attrName = attrName;
        this.id = id;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}