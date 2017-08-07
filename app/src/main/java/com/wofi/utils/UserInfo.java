package com.wofi.utils;

import java.math.BigDecimal;

/**
 * Created by shaohao on 2017/8/5.
 */

public class UserInfo {
    private BigDecimal userAccount;
    private String userName;
    private String userId;
    private Integer userCredit;
    private Integer userCash;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public BigDecimal getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(BigDecimal userAccount) {
        this.userAccount =userAccount ;
    }

    public Integer getUserCash() {
        return userCash;
    }

    public void setUserCash(Integer userCash) {
        this.userCash = userCash;
    }

    public Integer getUserCredit() {
        return userCredit;
    }

    public void setUserCredit(Integer userCredit) {
        this.userCredit = userCredit;
    }



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
