package com.xhhf.shaika.activity;

import com.xhhf.shaika.R;

import butterknife.ButterKnife;

/**
 * Created by ZJH on 2016.11.15.
 */
public class MyOrderActivity extends BaseActivity {
    @Override
    protected int addView() {
        return R.layout.ac_my_order;
    }

    @Override
    protected void initView() {
        ButterKnife.inject(this);
    }

//    @OnClick(R.id.ac_my_order_ib_back)
//    protected void ibBack(){
//        finish();
//    }
}
