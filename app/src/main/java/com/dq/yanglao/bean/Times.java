package com.dq.yanglao.bean;

/**
 * 我的-吃药提醒-编辑提醒-选择时间（实体类）
 * Created by jingang on 2018/6/4.
 */

public class Times {
    private String name;
    private boolean isSelect;

    public Times(String name, boolean isSelect) {
        this.name = name;
        this.isSelect = isSelect;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
