package com.xhhf.shaika.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/15 0015.
 * 晒卡界面可扩展listview全部数据
 */

public class ProductSortAll {
    public List<ExpandableGroup> groups;

    public List<ExpandableGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<ExpandableGroup> groups) {
        this.groups = groups;
    }

}
