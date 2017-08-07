package com.wofi.utils;

/**
 * Created by shaohao on 2017/8/3.
 */

public class Rechargebill {
    private String rechargeId;
    private String userId;
    private String rechargeAmount;
    private String remaining;
    private String rechargeTime;

    public String getRechargeId()
    {
        return rechargeId;
    }
    public void setRechargeId(String rechargeId)
    {
        this.rechargeId=rechargeId;
    }
    public String getUserId()
    {
        return userId;
    }
    public void setUserId(String userId)
    {
        this.userId=userId;
    }

    public String getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(String rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    public String getRemaining() {
        return remaining;
    }

    public void setRemaining(String remaining) {
        this.remaining = remaining;
    }

    public String getRechargeTime() {
        return rechargeTime;
    }

    public void setRechargeTime(String rechargeTime) {
        this.rechargeTime = rechargeTime;
    }
}
