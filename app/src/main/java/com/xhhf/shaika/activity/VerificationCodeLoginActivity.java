package com.xhhf.shaika.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.xhhf.shaika.MainActivity;
import com.xhhf.shaika.R;
import com.xhhf.shaika.ShaiKaApplication;
import com.xhhf.shaika.config.Contants;
import com.xhhf.shaika.util.ClassPathResource;
import com.xhhf.shaika.util.Share;
import com.xhhf.shaika.util.ToastUtil;
import com.xhhf.shaika.util.Util;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 验证码登录
 * Created by ZJH on 2016.11.10.
 */
public class VerificationCodeLoginActivity extends BaseActivity {
    private SharedPreferences sp;

    @InjectView(R.id.ac_verification_code_et_phone)
    protected EditText vcUserName;

    @InjectView(R.id.ac_verification_code_et_input)
    protected EditText vcVerificationCode;

    @InjectView(R.id.ac_verification_code_btn_login)
    protected Button vcLogin;

    @InjectView(R.id.ac_verification_code_send_code)
    protected Button vcSendCode;

    private Context context;
    private TimeCount timeCount;

    @Override
    protected int addView() {
        return R.layout.ac_verification_code_login;
    }

    @Override
    protected void initView() {
        context = VerificationCodeLoginActivity.this;
        ButterKnife.inject(VerificationCodeLoginActivity.this);
        timeCount = new TimeCount(60000, 1000);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        vcUserName.setText(sp.getString("userName", ""));
    }

    //返回键
//    @OnClick(R.id.ac_verification_code_ib_back)
//    protected void vcBack(){
//        finish();
//    }

    /**
     * 获取验证码
     */
    @OnClick(R.id.ac_verification_code_send_code)
    protected void vcSendCode() {
        String userName = vcUserName.getText().toString();
        String src = ClassPathResource.errorHintMobile(context, userName);
        if (!TextUtils.isEmpty(src)) {
            ToastUtil.show(context, src);
            return;
        }
        timeCount.start();
        String url = Contants.API.CODE + "?phone=" + userName;

        SharedPreferences.Editor edit = sp.edit();
        edit.putString("userName", userName);
        edit.commit();

        OkGo.get(url).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                ToastUtil.show(context, s);
            }
        });
    }

    /**
     * 登录
     */
    @OnClick(R.id.ac_verification_code_btn_login)
    protected void vcLogin() {
        vcLogin.setEnabled(false);
        String phone = vcUserName.getText().toString();
        String vccode = vcVerificationCode.getText().toString();

        if (TextUtils.isEmpty(vcUserName.getText().toString())) {
            ToastUtil.show(context, getString(R.string.error_username_toast));
            return;
        }
        if (TextUtils.isEmpty(vcSendCode.getText().toString())) {
            ToastUtil.show(context, getString(R.string.error_code_toast));
            return;
        }
        HashMap<String, String> vcHashMap = new HashMap<>();
        vcHashMap.put("phone", phone);
        vcHashMap.put("smsCode", vccode);
        String url = Contants.API.SLOGIN;
        ShaiKaApplication.okHttpHelper.post(url, "verificationlogin", Util.paramsEncoding(vcHashMap)).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                ToastUtil.show(VerificationCodeLoginActivity.this, s);
                Share.i("验证码登录返回值： " + s);
                vcLogin.setEnabled(true);
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 倒计时
     *
     * @author Administrator
     */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            vcSendCode.setText("重新获取");
            vcSendCode.setEnabled(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
//            Share.i(">>>> 倒计时 millisUntilFinished:" + millisUntilFinished);
            vcSendCode.setEnabled(false);
            vcSendCode.setText(millisUntilFinished / 1000 + "秒后重新发送");
        }

    }
}
