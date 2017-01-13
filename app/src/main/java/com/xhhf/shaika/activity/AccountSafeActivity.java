package com.xhhf.shaika.activity;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import com.xhhf.shaika.R;

import butterknife.ButterKnife;

/**
 * 账户安全，涉及修改用户信息
 * Created by ZJH on 2016.11.17.
 */
public class AccountSafeActivity extends BaseActivity{
    private RelativeLayout rlPhoneNum;
    private RelativeLayout rlPassword;
    private RelativeLayout rlEmail;

    @Override
    protected int addView() {
        return R.layout.ac_account_safe;
    }

    @Override
    protected void initView() {
        ButterKnife.inject(this);

        rlPhoneNum= (RelativeLayout) findViewById(R.id.ac_account_safe_rl_phone);
        rlPassword= (RelativeLayout) findViewById(R.id.ac_account_safe_rl_password);
        rlEmail= (RelativeLayout) findViewById(R.id.ac_account_safe_rl_email);
        //修改手机号
        rlPhoneNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_phone=new Intent(AccountSafeActivity.this,ChangeUserInfoActivity.class);
                intent_phone.putExtra("flag","phone");
                startActivity(intent_phone);
            }
        });
        //修改密码
        rlPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_password=new Intent(AccountSafeActivity.this,ChangeUserInfoActivity.class);
                intent_password.putExtra("flag","password");
                startActivity(intent_password);
            }
        });
        //修改邮箱
        rlEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_email=new Intent(AccountSafeActivity.this,ChangeUserInfoActivity.class);
                intent_email.putExtra("flag","email");
                startActivity(intent_email);
            }
        });
    }
}
