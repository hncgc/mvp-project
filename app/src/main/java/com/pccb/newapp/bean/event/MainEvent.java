package com.pccb.newapp.bean.event;

/**
 * 一级页面切换实体
 * @author cgc
 * @created 2017-11-23
 */
public class MainEvent {
    public final int index;

    public MainEvent(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
