package com.xhhf.shaika.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.xhhf.shaika.R;
import com.xhhf.shaika.bean.HomeGvBean;
import com.xhhf.shaika.bean.Tab;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 分类item界面
 * 作者：Eric on 2016/11/14 15:46
 * 邮箱：845505772@qq.com
 */

public class ListActivity extends BaseActivity {
    @InjectView(R.id.title_tv_name)
    protected TextView title;

    @InjectView(R.id.list_lv_show)
    protected ListView mListView;

    private Context context;


    @Override
    protected int addView() {
        return R.layout.ac_list;
    }

    @Override
    protected void initView() {
        ButterKnife.inject(this);
        context = ListActivity.this;
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        MyAdapter myAdapter = new MyAdapter();
        mListView.setAdapter(myAdapter);
        Intent intent = getIntent();
//        HomeGvBean item = (HomeGvBean) intent.getSerializableExtra("item");
        title.setText("分类");
    }

    @OnClick(R.id.title_tv_name)
    protected void backName() {
        finish();
    }

    @OnClick(R.id.title_search)
    protected void gotoSearch() {
        Intent intent = new Intent(context, SearchActivity.class);
        startActivity(intent);
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(context, R.layout.item_category_listview, null);
            return view;
        }
    }


}
