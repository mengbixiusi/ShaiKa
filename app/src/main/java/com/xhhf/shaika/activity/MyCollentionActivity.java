package com.xhhf.shaika.activity;

import com.xhhf.shaika.R;

import butterknife.ButterKnife;

/**
 * 我的收藏
 * Created by ZJH on 2016.11.07.
 */
public class MyCollentionActivity extends BaseActivity {
    @Override
    protected int addView() {
        return R.layout.ac_mycollection;
    }

    @Override
    protected void initView() {
        ButterKnife.inject(this);

    }

//    @OnClick(R.id.ib_back_mycollection)
//    protected void myCollectionBack(){
//        finish();
//    }
}
