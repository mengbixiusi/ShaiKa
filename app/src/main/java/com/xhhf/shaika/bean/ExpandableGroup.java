package com.xhhf.shaika.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/11 0011.
 */

public class ExpandableGroup {

    private String name;
    public List<ExpandablChild> childs;
    public ExpandableGroup() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ExpandablChild> getChilds() {
        return childs;
    }

    public void setChilds(List<ExpandablChild> childs) {
        this.childs = childs;
    }
}
