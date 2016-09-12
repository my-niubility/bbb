package com.nbl.model;

import java.util.Date;
import java.util.List;

public class UserMessage {
	
    private String id;

    private String recUserId;

    private String messageId;

    private String isreadstatus;

    private String status;

    private Date createTime;
    
	public String getId() {
        return id;
    }
	
	public String messageType;

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRecUserId() {
        return recUserId;
    }

    public void setRecUserId(String recUserId) {
        this.recUserId = recUserId == null ? null : recUserId.trim();
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId == null ? null : messageId.trim();
    }

   

    public String getIsreadstatus() {
		return isreadstatus;
	}

	public void setIsreadstatus(String isreadstatus) {
		this.isreadstatus = isreadstatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	@Override
	public String toString() {
		return "UserMessage [id=" + id + ", recUserId=" + recUserId + ", messageId=" + messageId + ", isreadstatus="
				+ isreadstatus + ", status=" + status + ", createTime=" + createTime + ", messageType=" + messageType
				+ "]";
	}

	

	
    
}