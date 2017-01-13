package com.xhhf.shaika.activity;

import com.xhhf.shaika.R;

import butterknife.ButterKnife;

/**
 * 我的点评
 * Created by ZJH on 2016.11.15.
 */
public class MyCommentActivity extends BaseActivity {
    @Override
    protected int addView() {
        return R.layout.ac_my_comment;
    }

    @Override
    protected void initView() {
        ButterKnife.inject(this);
    }

//    @OnClick(R.id.ac_my_comment_ib_back)
//    protected void ibBack(){
//        finish();
//    }
}
