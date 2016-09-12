package com.nbl.model;

import java.util.Date;

public class BillBalance {
    private Integer id;

    private Date calTime;

    private String accountDate;

    private String custId;

    private String merchantId;

    private String projectId;

    private Long drAmount;

    private Long crAmount;

    private Long assetAmount;

    private Long liabilityAmount;

    private String chBalanceFlag;

    private String lsBalanceFlag;

    private String msg;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCalTime() {
        return calTime;
    }

    public void setCalTime(Date calTime) {
        this.calTime = calTime;
    }

    public String getAccountDate() {
        return accountDate;
    }

    public void setAccountDate(String accountDate) {
        this.accountDate = accountDate == null ? null : accountDate.trim();
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId == null ? null : custId.trim();
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId == null ? null : merchantId.trim();
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public Long getDrAmount() {
        return drAmount;
    }

    public void setDrAmount(Long drAmount) {
        this.drAmount = drAmount;
    }

    public Long getCrAmount() {
        return crAmount;
    }

    public void setCrAmount(Long crAmount) {
        this.crAmount = crAmount;
    }

    public Long getAssetAmount() {
        return assetAmount;
    }

    public void setAssetAmount(Long assetAmount) {
        this.assetAmount = assetAmount;
    }

    public Long getLiabilityAmount() {
        return liabilityAmount;
    }

    public void setLiabilityAmount(Long liabilityAmount) {
        this.liabilityAmount = liabilityAmount;
    }

    public String getChBalanceFlag() {
        return chBalanceFlag;
    }

    public void setChBalanceFlag(String chBalanceFlag) {
        this.chBalanceFlag = chBalanceFlag == null ? null : chBalanceFlag.trim();
    }

    public String getLsBalanceFlag() {
        return lsBalanceFlag;
    }

    public void setLsBalanceFlag(String lsBalanceFlag) {
        this.lsBalanceFlag = lsBalanceFlag == null ? null : lsBalanceFlag.trim();
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
    }
}