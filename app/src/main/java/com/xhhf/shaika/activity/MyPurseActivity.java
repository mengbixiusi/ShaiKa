package com.xhhf.shaika.activity;

import android.widget.ImageButton;

import com.xhhf.shaika.R;

import butterknife.ButterKnife;

/**
 * Created by ZJH on 2016.11.15.
 */
public class MyPurseActivity extends BaseActivity {
    private ImageButton ibBack;

    @Override
    protected int addView() {
        return R.layout.ac_my_purse;
    }

    @Override
    protected void initView() {
        ButterKnife.inject(this);
    }

    /**
     * 返回
     */
//    @OnClick(R.id.ac_my_purse_ib_back)
//    protected void myPurseBack(){
//        finish();
//    }
}
