package com.xhhf.shaika.activity;

import android.content.Intent;
import android.widget.EditText;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.xhhf.shaika.R;
import com.xhhf.shaika.config.Contants;
import com.xhhf.shaika.util.ToastUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 忘记密码
 * Created by ZJH on 2016.11.02.
 */
public class ForgetPasswordActivity1 extends BaseActivity {
    @InjectView(R.id.ac_forget_psw1_et_user_pnone)
    protected EditText etPhone;

    @Override
    protected int addView() {
        return R.layout.ac_forget_password1;
    }

    @Override
    protected void initView() {
        ButterKnife.inject(this);

    }

//    @OnClick(R.id.ac_forget_psw1_ib_back)
//    protected void forgetPSW1Back(){
//        finish();
//    }

    @OnClick(R.id.ac_forget_psw1_btn_next)
    protected void nextStep(){
        String username=etPhone.getText().toString().trim();

        if (username.equals("")){
            ToastUtil.show(ForgetPasswordActivity1.this,"手机号不能为空");
            return;
        }

        String url = Contants.API.CODE + "?phone=" + username;

        OkGo.get(url).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                ToastUtil.show(ForgetPasswordActivity1.this, s);
            }
        });

        Intent intent_next=new Intent(ForgetPasswordActivity1.this,ForgetPasswordActivity2.class);
        startActivity(intent_next);
    }
}
