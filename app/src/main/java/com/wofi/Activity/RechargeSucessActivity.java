package com.wofi.Activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.wofi.R;
import com.wofi.Service.GsonService;

/**
 * Created by shaohao on 2017/8/7.
 */

public class RechargeSucessActivity extends AppCompatActivity {
    private GsonService mservice;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rechargesucess);
        toolbar= (Toolbar) findViewById(R.id.sucess);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    private ServiceConnection con=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
             mservice=( (GsonService.MyBinder)iBinder).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mservice=null;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent=new Intent(this,GsonService.class);
        bindService(intent,con,Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(con);
    }
}
