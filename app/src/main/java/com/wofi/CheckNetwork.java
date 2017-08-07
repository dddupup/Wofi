package com.wofi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by shaohao on 2017/7/24.
 */

public class CheckNetwork {

    /**
     * 构造私有化 不允许创建对象
     */
    private CheckNetwork() {
    }

    /**
     * 获取当前的网络状态是否可用
     *
     * @param context
     * @return
     */
    public static boolean getNetworkState(Context context) {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // 获取NetworkInfo对象
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
        // 遍历每一个对象
        for (NetworkInfo networkInfo : networkInfos) {
            if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                // debug信息
                Toast.makeText(context, "当前网络状态 = " + networkInfo.getTypeName(), Toast.LENGTH_SHORT).show();
                // 网络状态可用
                return true;
            }
        }
        // 没有可用的网络
        return false;
    }
}