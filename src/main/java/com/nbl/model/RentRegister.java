package com.nbl.model;

import java.util.Date;

public class RentRegister {
    private String rentId;

    private String rentName;

    private String extendsId;

    private String projectIntro;

    private String rentMessage;

    private String rentProduce;

    private String produceIntr;

    private String otherRemark1;

    private String otherRemark2;

    private String otherRemark3;

    private String otherRemark4;

    private Date createTime;

    public String getRentId() {
        return rentId;
    }

    public void setRentId(String rentId) {
        this.rentId = rentId == null ? null : rentId.trim();
    }

    public String getRentName() {
        return rentName;
    }

    public void setRentName(String rentName) {
        this.rentName = rentName == null ? null : rentName.trim();
    }

    public String getExtendsId() {
        return extendsId;
    }

    public void setExtendsId(String extendsId) {
        this.extendsId = extendsId == null ? null : extendsId.trim();
    }

    public String getProjectIntro() {
        return projectIntro;
    }

    public void setProjectIntro(String projectIntro) {
        this.projectIntro = projectIntro == null ? null : projectIntro.trim();
    }

    public String getRentMessage() {
        return rentMessage;
    }

    public void setRentMessage(String rentMessage) {
        this.rentMessage = rentMessage == null ? null : rentMessage.trim();
    }

    public String getRentProduce() {
        return rentProduce;
    }

    public void setRentProduce(String rentProduce) {
        this.rentProduce = rentProduce == null ? null : rentProduce.trim();
    }

    public String getProduceIntr() {
        return produceIntr;
    }

    public void setProduceIntr(String produceIntr) {
        this.produceIntr = produceIntr == null ? null : produceIntr.trim();
    }

    public String getOtherRemark1() {
        return otherRemark1;
    }

    public void setOtherRemark1(String otherRemark1) {
        this.otherRemark1 = otherRemark1 == null ? null : otherRemark1.trim();
    }

    public String getOtherRemark2() {
        return otherRemark2;
    }

    public void setOtherRemark2(String otherRemark2) {
        this.otherRemark2 = otherRemark2 == null ? null : otherRemark2.trim();
    }

    public String getOtherRemark3() {
        return otherRemark3;
    }

    public void setOtherRemark3(String otherRemark3) {
        this.otherRemark3 = otherRemark3 == null ? null : otherRemark3.trim();
    }

    public String getOtherRemark4() {
        return otherRemark4;
    }

    public void setOtherRemark4(String otherRemark4) {
        this.otherRemark4 = otherRemark4 == null ? null : otherRemark4.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}