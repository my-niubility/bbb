package com.nbl.model;

import java.util.Date;

public class GeneralParameter {
    private Integer id;

    private String code;

    private String parName;

    private String parValue;

    private Date updateTime;

    private String reMark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getParName() {
        return parName;
    }

    public void setParName(String parName) {
        this.parName = parName == null ? null : parName.trim();
    }

    public String getParValue() {
        return parValue;
    }

    public void setParValue(String parValue) {
        this.parValue = parValue == null ? null : parValue.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getReMark() {
        return reMark;
    }

    public void setReMark(String reMark) {
        this.reMark = reMark == null ? null : reMark.trim();
    }
}