package com.xhhf.shaika.fragment;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.xhhf.shaika.R;
import com.xhhf.shaika.adapter.BusinessRecylerAdapter;
import com.xhhf.shaika.bean.DeliciousFoodBean;
import com.xhhf.shaika.bean.District;
import com.xhhf.shaika.view.DividerItemDecoration;

import java.util.ArrayList;

/**
 * 商户列表的第一个fragment
 * Created by ZJH on 2016.11.03.
 */
public class DeliciousFoodFragment extends LazyLoadFragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ArrayList<DeliciousFoodBean> dataList;
    private BusinessRecylerAdapter mAdapter;

    @Override
    public int getLayout() {
        return R.layout.fragment_delicious_food;
    }

    @Override
    public void initViews(View view) {
        // 模拟数据
        ArrayList<District> dlist = new ArrayList<District>();
        District d1 = new District();
        d1.setName("面包甜点");
        District d2 = new District();
        d2.setName("韩国料理");
        District d3 = new District();
        d3.setName("日本料理");
        District d4 = new District();
        d4.setName("川菜");
        District d5 = new District();
        d5.setName("东北菜");
        District d6 = new District();
        d6.setName("北京菜");
        District d7 = new District();
        d7.setName("小吃快餐");

        dlist.add(d1);
        dlist.add(d2);
        dlist.add(d3);
        dlist.add(d4);
        dlist.add(d5);
        dlist.add(d6);
        dlist.add(d7);

        final LinearLayout view2 = (LinearLayout)
                view.findViewById(R.id.ll_district);
        LinearLayout ll = null;

        // 在列表最前面添加全部
        District d = new District();
        d.setName("热门");
        dlist.add(0, d);

        // 代码动态生成radiobutton,设置两行，
        for (int i = 0; i < dlist.size(); i++) {
            // 这里LinearLayout肯定会实例化，因为一开始i=0，由于3个换行，所以%3
            if (i % 4 == 0) {
                ll = new LinearLayout(getActivity());
                ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                ll.setOrientation(LinearLayout.HORIZONTAL);
                view2.addView(ll);
            }

            District de = dlist.get(i);

            // 由于样式设置麻烦，所以直接用xml声明样式了。
            View v = LayoutInflater.from(getActivity()).inflate(
                    R.layout.item_radio_district, null);
            ((RadioButton) v.findViewById(R.id.rb_district)).setText(de
                    .getName());
            ((RadioButton) v.findViewById(R.id.rb_district)).setTag(de);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            lp.weight = 1;

            // 一开始，设置“全部”RadioButton为选中状态
            if (i == 0) {
                ((RadioButton) v.findViewById(R.id.rb_district))
                        .setChecked(true);
                ((RadioButton) v.findViewById(R.id.rb_district))
                        .setTextColor(getResources().getColor(R.color.white));
            }
            final int finalI = i;
            ((RadioButton) v.findViewById(R.id.rb_district))
                    .setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            Toast.makeText(getActivity(), "你选择了" + finalI, Toast.LENGTH_SHORT).show();

                            RadioButton rb = (RadioButton) v;
                            // check事件发生在click之前，模拟group操作。
                            if (rb.isChecked()) {
                                rb.setTextColor(Color.WHITE);
                                // 当点击Button时，遍历布局中所有的RadioButton，设置为未选中。
                                for (int i = 0; i < view2.getChildCount(); i++) {
                                    LinearLayout l = (LinearLayout) view2
                                            .getChildAt(i);
                                    for (int j = 0; j < l.getChildCount(); j++) {
                                        // 根据xml布局的定义，可以知道具体是在第几层LinearLayout里。
                                        RadioButton b = (RadioButton) ((LinearLayout) ((LinearLayout) l
                                                .getChildAt(j)).getChildAt(0))
                                                .getChildAt(0);
                                        b.setChecked(false);
                                        b.setTextColor(getResources().getColor(R.color.text_color_unimprotant));
                                    }
                                }
                            }
                            // 完成后，设置该按钮选中
                            rb.setChecked(true);
                            rb.setTextColor(Color.WHITE);
                            // 这里开始处理点击
                            District d = (District) rb.getTag();
                        }
                    });
            ll.addView(v, lp);
        }

        // 填充RadioButton空白，使其布局对其，保证每行都有3个，只不过设置看不见而已。
        for (int i = dlist.size() % 4; i < 4 && i != 0; i++) {
            District dd = dlist.get(i);
            View v = LayoutInflater.from(getActivity()).inflate(
                    R.layout.item_radio_district, null);
            ((RadioButton) v.findViewById(R.id.rb_district)).setText(dd
                    .getName());
            ((RadioButton) v.findViewById(R.id.rb_district))
                    .setVisibility(View.INVISIBLE);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            lp.weight = 1;
            ll.addView(v, lp);
        }

        //商家条目
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        RecyclerViewHeader header = (RecyclerViewHeader) view.findViewById(R.id.header);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        header.attachTo(recyclerView, true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        layoutManager = new LinearLayoutManager(getActivity());

    }

    //在这里处理加载数据（显示才去加载）
    @Override
    public void loadData() {

        dataList = new ArrayList<DeliciousFoodBean>();
        for (int i = 0; i < 11; i++) {
            DeliciousFoodBean newslistBean = new DeliciousFoodBean();
//                newslistBean.setId("这是第" + i + "个新闻的标题");
//                newslistBean.setName("这是第" + i + "个新闻的内容");
            dataList.add(newslistBean);
        }
//        mAdapter = new BusinessRecylerAdapter(dataList);//自定义的适配器
//        recyclerView.setAdapter(mAdapter);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.addOnScrollListener(new OnRecyclerScrollListener());
        if (mAdapter == null) {
            mAdapter = new BusinessRecylerAdapter(getActivity(),dataList);
            recyclerView.setAdapter(mAdapter);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.addOnScrollListener(new OnRecyclerScrollListener());
        } else {
            mAdapter.notifyDataSetChanged();
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.addOnScrollListener(new OnRecyclerScrollListener());
        }
    }

    /**
     * 用于下拉刷新
     */
    public void onRefresh() {
    }

    /**
     * 用于上拉加载更多
     */
    public class OnRecyclerScrollListener extends RecyclerView.OnScrollListener {

        int lastVisibleItem = 0;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            if (mAdapter != null && newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 ==

                    mAdapter.getItemCount()) {
                //滚动到底部了，可以进行数据加载等操作

                for (int i = 0; i < 10; i++) {
                    DeliciousFoodBean newslistBean = new DeliciousFoodBean();
                    newslistBean.setId("这是第" + i + "个新闻的标题");
                    newslistBean.setName("这是第" + i + "个新闻的内容");
                    dataList.add(newslistBean);
                }
                if (mAdapter == null) {

                    mAdapter = new BusinessRecylerAdapter(getActivity(),dataList);//自定义的适配器
                    recyclerView.setAdapter(mAdapter);
                } else {
                    mAdapter.notifyDataSetChanged();
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = layoutManager.findLastVisibleItemPosition();
        }
    }
}
