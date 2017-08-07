package com.wofi.application;

import android.app.Application;

import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.wofi.utils.Journey;
import com.wofi.utils.Rechargebill;
import com.wofi.utils.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/20.
 */

public class MyApplication extends Application {

    private static MyApplication myApplication;
    public static MyApplication getInstance() {
        return myApplication;
    }
    public static List<Journey> journeyList=new ArrayList<>();//获取行程记录

    public static List<Rechargebill> rechargebillList=new ArrayList<>();//钱包记录

    public static UserInfo userinfo=new UserInfo();//用户信息
    public static String responceData1;
    public static String responceData2;
    public static String responceData;
    public static String cash;



    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        ZXingLibrary.initDisplayOpinion(this);
    }
}
