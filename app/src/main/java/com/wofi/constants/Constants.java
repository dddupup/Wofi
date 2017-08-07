package com.wofi.constants;


/**
 * Created by shaohao on 2017/8/2.
 */

public class Constants {
    /**
     * 服务器地址
     */
    public final static String URL="http://192.168.32.73:8080";

    /**
     * API
     */
    public final static String LOGIN_URL = URL+"/api-user-register/";
    public final static String RECHAGEBILL_URL =URL+"/api-user-queryRecharge/";
    public final static String RECHAGE_URL =URL+"/api-user-recharge/";
    public final static String JOURNEY_URL =URL+"/api-borrow-queryBorrow/";
    public final static String USERINFO_URL =URL+"/api-user-userInfo/";
    public final static String CASH_URL =URL+"/api-user-submitUserCash/";
    public final static String GET_CASH_URL =URL+"/api-user-getUserCash/";
    public final static String RETURN_CASH_URL =URL+"/api-user-returnUserCash/";

}
