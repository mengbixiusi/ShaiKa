package com.xhhf.shaika.activity;

import com.xhhf.shaika.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 用户反馈
 * Created by ZJH on 2016.11.08.
 */
public class FeedBackActivity extends BaseActivity {
    @Override
    protected int addView() {
        return R.layout.ac_feedback;
    }

    @Override
    protected void initView() {
        ButterKnife.inject(this);
    }
    @OnClick(R.id.ac_feedback_rl_back)
    public void gotoFeedbackBack(){
        finish();
    }

}
