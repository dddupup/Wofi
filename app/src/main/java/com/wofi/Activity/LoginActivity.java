package com.wofi.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.wofi.Clause;
import com.wofi.R;
import com.wofi.constants.Constants;
import com.wofi.utils.HttpUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int CODE_REPEAT =1 ;
    private Button bt1,bt2,bt3,bt4;
    private EditText tx1,tx2;
    private ImageView image;
    private String phonenumber;
    private String pin;
    private TimerTask tt;
    private Timer tm;
    private String country="86";
    private int TIME=60;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);
        bt1= (Button) findViewById(R.id.getPin);
        bt2= (Button) findViewById(R.id.checkPin);
        bt3= (Button) findViewById(R.id.Phone);
        bt4= (Button) findViewById(R.id.Clause);
        image= (ImageView) findViewById(R.id.imageView);
        tx1= (EditText) findViewById(R.id.etNum);
        tx2= (EditText) findViewById(R.id.etPin);

        image.setFocusable(true);
        image.setFocusableInTouchMode(true);
        image.requestFocus();
        image.requestFocusFromTouch();

        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        bt4.setOnClickListener(this);

        SMSSDK.registerEventHandler(eh); //注册短信回调（记得销毁，避免泄露内存）
    }

    /**
     * 注册监听事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.getPin:
                phonenumber=tx1.getText().toString().trim().replaceAll("/s","");
                if(!TextUtils.isEmpty(phonenumber))
                {
                    //定义需要匹配的正则表达式的规则
                    String REGEX_MOBILE_SIMPLE =  "[1][358]\\d{9}";
                    //把正则表达式的规则编译成模板
                    Pattern pattern = Pattern.compile(REGEX_MOBILE_SIMPLE);
                    //把需要匹配的字符给模板匹配，获得匹配器
                    Matcher matcher = pattern.matcher(phonenumber);
                    // 通过匹配器查找是否有该字符，不可重复调用重复调用matcher.find()
                    if (matcher.find()) {//匹配手机号是否存在
                        SMSSDK.getVerificationCode(country, phonenumber);
                        //做倒计时操作
                        Toast.makeText(LoginActivity.this, "已发送" , Toast.LENGTH_SHORT).show();
                        bt1.setEnabled(false);
                        bt2.setEnabled(true);
                        tm = new Timer();
                        tt = new TimerTask() {
                            @Override
                            public void run() {

                                hd.sendEmptyMessage(TIME--);
                            }
                        };
                        tm.schedule(tt,0,1000);
                    } else {
                        toast("手机号格式错误");
                    }
                } else {
                    toast("请先输入手机号");
                }
                break;
            case R.id.checkPin:
                pin=tx2.getText().toString().trim();
                //获得用户输入的验证码
                String code = tx2.getText().toString().replaceAll("/s","");
                if (!TextUtils.isEmpty(code)) {//判断验证码是否为空
                    //验证
                    SMSSDK.submitVerificationCode(country, phonenumber,code);
                }else{//如果用户输入的内容为空，提醒用户
                    toast("请输入验证码后再提交");
                }
                break;
            case R.id.Phone:
                phonenumber=tx1.getText().toString().trim().replaceAll("/s","");
                if(!TextUtils.isEmpty(phonenumber))
                {
                    //定义需要匹配的正则表达式的规则
                    String REGEX_MOBILE_SIMPLE =  "[1][358]\\d{9}";
                    //把正则表达式的规则编译成模板
                    Pattern pattern = Pattern.compile(REGEX_MOBILE_SIMPLE);
                    //把需要匹配的字符给模板匹配，获得匹配器
                    Matcher matcher = pattern.matcher(phonenumber);
                    // 通过匹配器查找是否有该字符，不可重复调用重复调用matcher.find()
                    if (matcher.find()) {//匹配手机号是否存在
                        SMSSDK.getVoiceVerifyCode(country,phonenumber);
                        Toast.makeText(LoginActivity.this, "已发送" , Toast.LENGTH_SHORT).show();
                    } else {
                        toast("手机号格式错误");
                    }
                } else {
                    toast("请先输入手机号");
                }
                break;
            case R.id.Clause:
                Intent intentclause=new Intent(LoginActivity.this, Clause.class);
                startActivity(intentclause);
            default:
                break;

        }

    }

    protected void onDestroy() {

        // 销毁回调监听接口
        SMSSDK.unregisterAllEventHandler();
        super.onDestroy();

    }
    Handler hd = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == CODE_REPEAT) {
                bt1.setEnabled(true);
                bt2.setEnabled(true);
                tm.cancel();//取消任务
                tt.cancel();//取消任务
                TIME = 60;//时间重置
                bt1.setText("重新发送验证码");
            }else {
                bt1.setText(TIME + "重新发送验证码");
            }
        }
    };
    EventHandler eh=new EventHandler(){
        @Override
        public void afterEvent(int event, int result, Object data) {
            if (result == SMSSDK.RESULT_COMPLETE) {
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    toast("验证成功");
                    //number=tx1.getText().toString().trim().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");

                    //将本机mac地址作为token保存
                    SharedPreferences sp=LoginActivity.this.getSharedPreferences("Login",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sp.edit();
                    editor.putString("token",getLocalMacAddress());
                    editor.putString("Username",tx1.getText().toString().trim().replaceAll("/s",""));
                    editor.commit();
                    Log.e("听说名字长点才能找的到",sp.getString("token",""));
                    final String username=tx1.getText().toString().trim();
                    Thread t=new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                HttpUtils.sendOkHttpRequest(Constants.LOGIN_URL+username);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    t.start();

                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                    //获取验证码成功
                }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){//如果你调用了获取国家区号类表会在这里回调
                    //返回支持发送验证码的国家列表
                }
            }else{//错误等在这里（包括验证失败）
                //错误码请参照http://wiki.mob.com/android-api-错误码参考
                ((Throwable)data).printStackTrace();
                String str = "验证码错误";
                toast(str);
            }
        }
    };
    private void toast(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private String getLocalMacAddress() {
        WifiManager wifi = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }

}
