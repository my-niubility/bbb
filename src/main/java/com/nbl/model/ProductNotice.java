package com.nbl.model;

import java.util.Date;

public class ProductNotice {
    private String id;

    private String productId;

    private String noticeName;

    private String noticeFileType;

    private String custId;

    private Date publishDate;

    private String status;

    private byte[] noticeFile;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public String getNoticeName() {
        return noticeName;
    }

    public void setNoticeName(String noticeName) {
        this.noticeName = noticeName == null ? null : noticeName.trim();
    }

    public String getNoticeFileType() {
        return noticeFileType;
    }

    public void setNoticeFileType(String noticeFileType) {
        this.noticeFileType = noticeFileType == null ? null : noticeFileType.trim();
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId == null ? null : custId.trim();
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public byte[] getNoticeFile() {
        return noticeFile;
    }

    public void setNoticeFile(byte[] noticeFile) {
        this.noticeFile = noticeFile;
    }
}