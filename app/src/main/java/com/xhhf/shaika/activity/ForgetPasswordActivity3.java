package com.xhhf.shaika.activity;

import android.content.Intent;
import android.text.InputType;
import android.widget.EditText;
import android.widget.ImageView;

import com.xhhf.shaika.R;
import com.xhhf.shaika.util.ToastUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 忘记密码输入新密码界面
 * Created by ZJH on 2016.11.02.
 */
public class ForgetPasswordActivity3 extends BaseActivity {
    @InjectView(R.id.ac_forget_password3_et_new_password)
    protected EditText etPassword;

    @InjectView(R.id.ac_forget_password3_et_new_password_confirm)
    protected EditText etPasswordConfirm;

    @InjectView(R.id.ac_forget_password3_iv_edit_hide1)
    protected ImageView ivHide1;

    @InjectView(R.id.ac_forget_password3_iv_edit_hide2)
    protected ImageView ivHide2;

    private boolean flag1=false;
    private boolean flag2=false;

    @Override
    protected int addView() {
        return R.layout.ac_forget_password3;
    }

    @Override
    protected void initView() {
        ButterKnife.inject(this);

    }

//    @OnClick(R.id.ac_forget_psw3_ib_back)
//    protected void forgetPSW3Back(){
//        finish();
//    }

    @OnClick(R.id.ac_forget_password3_btn_confirm)
    protected void nextStep(){
        String psw=etPassword.getText().toString().trim();
        String newPsw=etPasswordConfirm.getText().toString().trim();

        if (psw.equals("")){
            ToastUtil.show(ForgetPasswordActivity3.this,"请输入新密码");
            return;
        }

        if (newPsw.equals("")){
            ToastUtil.show(ForgetPasswordActivity3.this,"请确认您的密码");
            return;
        }

        if (!psw.equals(newPsw)){
            ToastUtil.show(ForgetPasswordActivity3.this,"两次输入的密码不一致");
            return;
        }
        Intent intent_next=new Intent(ForgetPasswordActivity3.this,ForgetPasswordActivity4.class);
        startActivity(intent_next);
    }

    @OnClick(R.id.ac_forget_password3_iv_edit_hide1)
    protected void changePsw1(){
        etPassword.setSelection(etPassword.getText().length());
        flag1=!flag1;
        if (flag1){
            ivHide1.setImageResource(R.mipmap.hide);
            etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }else{
            ivHide1.setImageResource(R.mipmap.show);
            etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    @OnClick(R.id.ac_forget_password3_iv_edit_hide2)
    protected void changePsw2(){
        etPasswordConfirm.setSelection(etPasswordConfirm.getText().length());
        flag2=!flag2;
        if (flag2){
            ivHide2.setImageResource(R.mipmap.hide);
            etPasswordConfirm.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }else{
            ivHide2.setImageResource(R.mipmap.show);
            etPasswordConfirm.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }
}
