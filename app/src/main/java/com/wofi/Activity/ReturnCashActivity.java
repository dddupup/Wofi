package com.wofi.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.wofi.R;
import com.wofi.constants.Constants;
import com.wofi.utils.HttpUtils;

import java.io.IOException;

/**
 * Created by zxx on 2017/8/7.
 */

public class ReturnCashActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView text_return;
    private Toolbar t_return;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_cash);
        text_return = (TextView) findViewById(R.id.text_return);
        t_return = (Toolbar) findViewById(R.id.credit);
        setSupportActionBar(t_return);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        text_return.setOnClickListener(this);
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
        switch (view.getId()) {
            case R.id.text_return:
                Thread t=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            SharedPreferences sp=getSharedPreferences("Login",MODE_PRIVATE);
                            HttpUtils.sendOkHttpRequest(Constants.RETURN_CASH_URL+sp.getString("Username",""));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                t.start();
                Intent in1 =new Intent(ReturnCashActivity.this,MyWalletActivity.class);
                startActivity(in1);
                finish();
                break;
        }


    }
}