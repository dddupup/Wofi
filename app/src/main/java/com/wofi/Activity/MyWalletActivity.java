package com.wofi.Activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wofi.R;
import com.wofi.Service.GsonService;
import com.wofi.constants.Constants;
import com.wofi.utils.Journey;
import com.wofi.utils.Rechargebill;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.wofi.application.MyApplication.rechargebillList;
import static com.wofi.application.MyApplication.cash;
import static com.wofi.application.MyApplication.userinfo;


/**
 * Created by shaohao on 2017/7/31.
 */

public class MyWalletActivity extends AppCompatActivity implements View.OnClickListener{
    private Toolbar wallet;
    private Button chongzhi,chongyajin,mingxi;
    private TextView yue;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mywallet);
        wallet= (Toolbar) findViewById(R.id.wallet);
        setSupportActionBar(wallet);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        chongzhi= (Button) findViewById(R.id.chongzhi);
        chongyajin= (Button) findViewById(R.id.chongyajin);
        mingxi= (Button) findViewById(R.id.mingxi);
        yue= (TextView) findViewById(R.id.chefei);
        chongzhi.setOnClickListener(this);
        chongyajin.setOnClickListener(this);
        mingxi.setOnClickListener(this);
        yue.setText(String.valueOf(userinfo.getUserAccount()));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.chongzhi:
                Intent in1=new Intent(MyWalletActivity.this,RechargeActivity.class);
                startActivity(in1);
                break;
            case R.id.chongyajin:
                Intent in2=new Intent(MyWalletActivity.this,UserCashActivity.class);
                startActivity(in2);
                break;
            case R.id.mingxi:
                Intent in3=new Intent(MyWalletActivity.this,RechargebillActivity.class);
                startActivity(in3);
                break;
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        yue.setText(String.valueOf(userinfo.getUserAccount()));
        Log.e("余额",String.valueOf(userinfo.getUserAccount()));
    }

}
