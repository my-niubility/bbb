package com.nbl.model;

public class TDay {
    private String day;

    private Short festivalFlag;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day == null ? null : day.trim();
    }

    public Short getFestivalFlag() {
        return festivalFlag;
    }

    public void setFestivalFlag(Short festivalFlag) {
        this.festivalFlag = festivalFlag;
    }
}