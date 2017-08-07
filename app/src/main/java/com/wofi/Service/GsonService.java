package com.wofi.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wofi.constants.Constants;
import com.wofi.utils.Journey;
import com.wofi.utils.Rechargebill;
import com.wofi.utils.UserInfo;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.wofi.application.MyApplication.journeyList;
import static com.wofi.application.MyApplication.rechargebillList;
import static com.wofi.application.MyApplication.responceData;
import static com.wofi.application.MyApplication.responceData1;
import static com.wofi.application.MyApplication.responceData2;
import static com.wofi.application.MyApplication.cash;
import static com.wofi.application.MyApplication.userinfo;

/**
 * Created by shaohao on 2017/8/4.
 */

public class GsonService extends Service {

    private final IBinder binder = new MyBinder();
    private Timer timer=new Timer();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        long begin=System.currentTimeMillis();
        Loop();
        Log.e("Service测试","服务启动");
        long end=System.currentTimeMillis();
        Log.e("服务时间", String.valueOf(end-begin)+"ms");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("Service测试","服务停止");

    }
    private void parseJSONWithGSONjourney(String jsonData)
    {
        Gson gson=new Gson();
        journeyList=gson.fromJson(jsonData,new TypeToken<List<Journey>>(){}.getType());
        Collections.reverse(journeyList);
        for(Journey journey:journeyList){
            Log.e("行程","money is"+journey.getCost());
        }


    }
    private void parseJSONWithGSONrecharge(String jsonData)
    {
        Gson gson=new Gson();
        rechargebillList=gson.fromJson(jsonData,new TypeToken<List<Rechargebill>>(){}.getType());
        Collections.reverse(rechargebillList);
        for(Rechargebill rechargebill:rechargebillList){
            Log.e("充值","money is"+rechargebill.getRechargeAmount());
        }
    }
    private void parseJSONWithGSONuser(String jsonData)
    {
        Gson gson=new Gson();
        userinfo=gson.fromJson(jsonData,new TypeToken<UserInfo>(){}.getType());
        Log.e("余额","money is"+userinfo.getUserAccount());

    }
    private void initJourney()
    {
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                long begin=System.currentTimeMillis();
                SharedPreferences sp=getSharedPreferences("Login", Context.MODE_PRIVATE);
                String username=sp.getString("Username","").trim();
                OkHttpClient client=new OkHttpClient();
                Request request=new Request.Builder()
                        .url(Constants.JOURNEY_URL+username)
                        .build();
                try {
                    Response response=client.newCall(request).execute();
                    responceData1=response.body().string();
                    parseJSONWithGSONjourney(responceData1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                long end=System.currentTimeMillis();
                Log.e("线程1", String.valueOf(end-begin)+"ms");
            }
        });
        t.start();
    }
    private void initRechagebill()
    {
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                long begin=System.currentTimeMillis();
                SharedPreferences sp=getSharedPreferences("Login", Context.MODE_PRIVATE);
                String username=sp.getString("Username","").trim();
                OkHttpClient client=new OkHttpClient();
                Request request=new Request.Builder()
                        .url(Constants.RECHAGEBILL_URL+username)
                        .build();
                try {
                    Response response=client.newCall(request).execute();
                    responceData=response.body().string();
                    parseJSONWithGSONrecharge(responceData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                long end=System.currentTimeMillis();
                Log.e("线程2", String.valueOf(end-begin)+"ms");
            }
        });
        t.start();
    }
    private void initUserInfo()
    {
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sp=getSharedPreferences("Login", Context.MODE_PRIVATE);
                String username=sp.getString("Username","").trim();
                OkHttpClient client=new OkHttpClient();
                Request request=new Request.Builder()
                        .url(Constants.USERINFO_URL+username)
                        .build();
                try {
                    Response response=client.newCall(request).execute();
                    responceData2=response.body().string();
                    parseJSONWithGSONuser(responceData2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    private void initCash()
    {
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                long begin=System.currentTimeMillis();
                SharedPreferences sp=getSharedPreferences("Login", Context.MODE_PRIVATE);
                String username=sp.getString("Username","").trim();
                OkHttpClient client=new OkHttpClient();
                Request request=new Request.Builder()
                        .url(Constants.GET_CASH_URL+username)
                        .build();
                try {
                    Response response=client.newCall(request).execute();
                    cash=response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                long end=System.currentTimeMillis();
                Log.e("线程1", String.valueOf(end-begin)+"ms");
            }
        });
        t.start();
    }

    /**
     * 使用onbind()方式
     */
    public class MyBinder extends Binder{
        public GsonService getService()
        {
            return GsonService.this;
        }
    }
    private void Loop()
    {
        timer.schedule(task,1000,2000);
    }

    TimerTask task=new TimerTask() {
        @Override
        public void run() {
            initJourney();
            initRechagebill();
            initUserInfo();
            initCash();
        }
    };
}
