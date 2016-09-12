package com.nbl.model;

import java.util.Date;

public class ProductAudit {
    private String id;

    private String productId;

    private String type;

    private String passed;

    private Date createdTime;

    private String auditUserId;

    private Date auditTime;

    private Short isModify;

    private String description;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getPassed() {
        return passed;
    }

    public void setPassed(String passed) {
        this.passed = passed;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getAuditUserId() {
        return auditUserId;
    }

    public void setAuditUserId(String auditUserId) {
        this.auditUserId = auditUserId == null ? null : auditUserId.trim();
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public Short getIsModify() {
        return isModify;
    }

    public void setIsModify(Short isModify) {
        this.isModify = isModify;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

	@Override
	public String toString() {
		return "ProductAudit [id=" + id + ", productId=" + productId + ", type=" + type + ", passed=" + passed
				+ ", createdTime=" + createdTime + ", auditUserId=" + auditUserId + ", auditTime=" + auditTime
				+ ", isModify=" + isModify + ", description=" + description + "]";
	}
    
    
}