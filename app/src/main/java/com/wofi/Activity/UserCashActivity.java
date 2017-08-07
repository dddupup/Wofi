package com.wofi.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wofi.R;
import com.wofi.utils.HttpUtils;
import com.wofi.constants.Constants;
import com.wofi.utils.UserInfo;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.wofi.application.MyApplication.responceData1;
import static com.wofi.application.MyApplication.userinfo;

/**
 * Created by zxx on 2017/8/7.
 */

public class UserCashActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView Pay;
    private Toolbar credit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash);
        Pay = (TextView) findViewById(R.id.Pay);
        credit = (Toolbar) findViewById(R.id.credit);
        setSupportActionBar(credit);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Pay.setOnClickListener(this);
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
            case R.id.Pay:
                Thread t=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            SharedPreferences sp=getSharedPreferences("Login",MODE_PRIVATE);
                            HttpUtils.sendOkHttpRequest(Constants.CASH_URL+sp.getString("Username",""));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                t.start();
                Toast.makeText(this,"充值成功",Toast.LENGTH_SHORT).show();
                Intent in=new Intent(UserCashActivity.this,MyWalletActivity1.class);
                startActivity(in);
                finish();
                break;
        }
    }

}
