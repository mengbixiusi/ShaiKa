package com.xhhf.shaika.activity;

import android.widget.ListView;

import com.xhhf.shaika.R;
import com.xhhf.shaika.adapter.HomeNotiAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2016/11/15 0015.
 */
public class HomeNotiActivity extends BaseActivity{

    @InjectView(R.id.lv_home_noti)
    protected ListView lvNoti;

    @Override
    protected int addView() {
        return R.layout.activity_home_noti;
    }

    @Override
    protected void initView() {
        ButterKnife.inject(this);

        HomeNotiAdapter adapter = new HomeNotiAdapter(this);
        lvNoti.setAdapter(adapter);

    }
}
