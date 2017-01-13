package com.xhhf.shaika.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xhhf.shaika.R;
import com.xhhf.shaika.bean.ExpandablChild;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/11 0011.
 *晒卡页面可扩展listview适配器
 */

public class ShaiKaExpandAdapter extends BaseExpandableListAdapter {

    /**
     * 群组名
     */
    private ArrayList<String> groups;
    /**
     * 群组下的子项
     */
    private ArrayList<ArrayList<ExpandablChild>> childs;
    private Context mContext;
    private View groupView;

    public ShaiKaExpandAdapter(Context context) {
        mContext = context;
        init();
    }

    /**
     * 为了测试方便，先初始化一些数据
     */
    public void init() {
        groups = new ArrayList<String>();
        //添加一些群组
        for (int i = 0; i < 5; i++) {
            groups.add("商家" + i);
        }
        childs = new ArrayList<ArrayList<ExpandablChild>>();
        //为每个群组添加一些子项
        for (int i = 0; i < groups.size(); i++) {
            //如果该群组是偶数，为其添加5个子项,奇数添加3项
            ArrayList<ExpandablChild> values = new ArrayList<>();
            if (i % 2 == 0) {
                for (int j = 0; j < 3; j++) {
                    ExpandablChild child = new ExpandablChild();
                    child.setCategray("乔丹分享了1000元");
                    child.setDiscount(j + "折");
                    values.add(child);
                    childs.add(values);
                }
            } else {
                for (int j = 0; j < 3; j++) {
                    ExpandablChild child = new ExpandablChild();
                    child.setCategray("乔丹分享了1000元");
                    child.setDiscount(j + "折");
                    values.add(child);
                    childs.add(values);
                }
            }
        }
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childs.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childs.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        /**创建view */
        groupView = LayoutInflater.from(mContext).inflate(R.layout.expandlistview_group, parent,false);
        /**通过创建的view与自定义布局绑定，就要以找到布局里的组件了*/
        TextView tvGroupTitle = (TextView) groupView.findViewById(R.id.tv_categary);
        ImageView ivGroupImg = (ImageView) groupView.findViewById(R.id.iv_icon);
        ImageView ivArrow = (ImageView) groupView.findViewById(R.id.iv_group_arrow);
        tvGroupTitle.setText(groups.get(groupPosition));
        ivGroupImg.setBackgroundResource(R.mipmap.missyou);

        // 根据当前父条目的展开状态来设置不同的图片
        if (isExpanded){
            // 条目展开，设置向下的箭头
            ivArrow.setImageDrawable(mContext.getResources()
                    .getDrawable(R.mipmap.arrow));
        }else {
            ivArrow.setImageDrawable(mContext.getResources()
                    .getDrawable(R.mipmap.shuiping));

        }
        return groupView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.expandlistview_child, parent,false);
//        TextView tvChildTitle = (TextView)v.findViewById(R.id.tv_categary);
//        TextView tvChildExplain = (TextView)v.findViewById(R.id.tv_discount);
//        ImageView ivChildImg = (ImageView)v.findViewById(R.id.iv_icon);
//        tvChildTitle.setText(childs.get(groupPosition).get(childPosition).getCategray());
//        tvChildExplain.setText(childs.get(groupPosition).get(childPosition).getDiscount());
//        ivChildImg.setBackgroundResource(R.mipmap.missyou);
        return v;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}
