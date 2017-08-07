package com.wofi.utils;

import java.io.Serializable;

/**
 * Created by zxx on 2017/8/4.
 */

public class ItemModel implements Serializable {
    //左上角三角图标
    public static final int ONE = 1001;
    //textview布局
    public static final int TWO = 1002;
    //edittext布局
    public static final int THREE = 1003;
    public int type;
    public Object data;
    //是否免费的标志
    public boolean isFree;
    public ItemModel(int type, Object data, boolean isFree) {
        this.type = type;
        this.data = data;
    }
}
