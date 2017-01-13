package com.xhhf.shaika.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.GridView;
import android.widget.ListView;

import com.xhhf.shaika.R;
import com.xhhf.shaika.adapter.SearchHotGvAdapter;
import com.xhhf.shaika.adapter.SearchLvHisAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2016/11/11 0011.
 */

public class SearchActivity extends BaseActivity {
    @InjectView(R.id.gv_search_hot)
    protected GridView gvHotSearch;

    @InjectView(R.id.lv_search_history)
    protected ListView lvHistorySearch;

    @InjectView(R.id.et_search_content)
    protected AutoCompleteTextView etSearch;


    @Override
    protected int addView() {
        return R.layout.search_activity;
    }

    @Override
    protected void initView() {
        ButterKnife.inject(this);

        initData();
        initListener();
    }

    /**
     * 测试数据
     */
    private List<String> hotSearch;
    private List<String> history;

    /**
     * 初始化
     */
    private void initData() {
        hotSearch = new ArrayList<>();
        history = new ArrayList<>();

        hotSearch.add("王婆大虾");
        hotSearch.add("故乡的月光");
        hotSearch.add("状元烤肠");
        hotSearch.add("德克士");
        hotSearch.add("肚兜兜病");
        hotSearch.add("喜虾客");
        hotSearch.add("华莱士");
        hotSearch.add("锦鲤坊");
        hotSearch.add("宝视达");

        history.add("石锅拌饭");
        history.add("烤肉");
    }


    /**
     * 初始化监听
     */
    private void initListener() {
        SearchHotGvAdapter gvAdapter = new SearchHotGvAdapter(this,hotSearch);
        gvHotSearch.setAdapter(gvAdapter);

        SearchLvHisAdapter lvAdapter = new SearchLvHisAdapter(this,history);
        lvHistorySearch.setAdapter(lvAdapter);

        /**
         * 热门搜索点击事件
         */
        gvHotSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = hotSearch.get(position);
                Intent hotIntent = new Intent(SearchActivity.this,BusinessDetailActivity.class);
                hotIntent.putExtra("item",item);
                startActivity(hotIntent);
            }
        });

        /**
         * 历史搜索点击事件
         */
        lvHistorySearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = hotSearch.get(position);
                Intent hotIntent = new Intent(SearchActivity.this,BusinessDetailActivity.class);
                hotIntent.putExtra("item",item);
                startActivity(hotIntent);
            }
        });

        //搜索补全
        String[] result = getResources().getStringArray(R.array.countries_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,result);
        etSearch.setAdapter(adapter);

    }

}
