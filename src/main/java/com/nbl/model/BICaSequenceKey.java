package com.nbl.model;

public class BICaSequenceKey {
    private Integer id;

    private String curdate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCurdate() {
        return curdate;
    }

    public void setCurdate(String curdate) {
        this.curdate = curdate == null ? null : curdate.trim();
    }
}