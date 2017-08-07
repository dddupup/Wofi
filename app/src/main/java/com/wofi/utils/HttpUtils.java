package com.wofi.utils;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by shaohao on 2017/8/2.
 */

public class HttpUtils {
    public static OkHttpClient client = new OkHttpClient();

    public static void sendOkHttpRequest(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response=client.newCall(request).execute();
        String responseData=response.body().string();
    }

}
