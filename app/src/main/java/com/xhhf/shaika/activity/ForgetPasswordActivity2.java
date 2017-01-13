package com.xhhf.shaika.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.EditText;

import com.xhhf.shaika.R;
import com.xhhf.shaika.util.ToastUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 忘记密码发送验证码的界面
 * Created by ZJH on 2016.11.02.
 */
public class ForgetPasswordActivity2 extends BaseActivity {
    @InjectView(R.id.ac_frogetpsw2_code_et_verification)
    protected EditText etVerificationCode;

    @InjectView(R.id.ac_forget_password2_send_code)
    protected Button btnSendCode;

    private TimeCount timeCount;

    @Override
    protected int addView() {
        return R.layout.ac_forget_password2;
    }

    @Override
    protected void initView() {
        ButterKnife.inject(this);
        timeCount = new TimeCount(60000, 1000);
        timeCount.start();
    }

//    @OnClick(R.id.ac_forget_psw2_ib_back)
//    protected void forgetPSW2Back(){
//        finish();
//    }

    @OnClick(R.id.ac_forget_password2_next)
    protected void nextStep(){
        if (etVerificationCode.getText().toString().trim().equals("")){
            ToastUtil.show(ForgetPasswordActivity2.this,"请输入验证码");
            return;
        }

        Intent intent_next=new Intent(ForgetPasswordActivity2.this,ForgetPasswordActivity3.class);
        startActivity(intent_next);
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
            btnSendCode.setText("重新获取");
            btnSendCode.setEnabled(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
//            Share.i(">>>> 倒计时 millisUntilFinished:" + millisUntilFinished);
            btnSendCode.setEnabled(false);
            btnSendCode.setText(millisUntilFinished / 1000 + "秒后重新发送");
        }

    }
}
