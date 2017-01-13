package com.xhhf.shaika.activity;

import android.content.Intent;

import com.xhhf.shaika.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 忘记密码更改成功界面
 * Created by ZJH on 2016.11.02.
 */
public class ForgetPasswordActivity4 extends BaseActivity {

    @Override
    protected int addView() {
        return R.layout.ac_forget_password4;
    }

    @Override
    protected void initView() {
        ButterKnife.inject(this);

    }

//    @OnClick(R.id.ac_forget_psw4_ib_back)
//    protected void forgetPSW4Back(){
//        finish();
//    }

    @OnClick(R.id.ac_forget_password4_btn_confirm)
    protected void nextStep(){
        Intent intent_next=new Intent(ForgetPasswordActivity4.this,LoginActivity.class);
        startActivity(intent_next);
    }
}
