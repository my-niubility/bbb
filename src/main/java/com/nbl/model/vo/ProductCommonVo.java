package com.nbl.model.vo;

import java.math.BigDecimal;

/**
 * @author Donald
 * @createdate 2016年7月21日
 * @version 1.0 
 * @description :标准产品查询请求
 */
public class ProductCommonVo {
	
    private String productId;

    private String productName;

    private String assetId;

    private String assetName;

    private String productType;

    private String sellType;

    private String transferFlag;

    private String subsidyDay;

    private String isEstablish;

    private String assetRepayStatus;

    private Short repayMode;

    private String collectStartDt;

    private String collectEndDt;

    private String rentType;

    private String repayRentType;

    private String repayRentDt;

    private String bLockPeriod;

    private String tLockPeriod;

    private String breBackCheck;

    private String transfers;

    private Long financeScale;

    private Long unitCost;

    private String establishCondition;

    private Long establishRatio;

    private Long establishMinimum;

    private String establishDate;

    private BigDecimal expectEarnRate;

    private Long investMax;
    
    private Long investMin;

    private String financeId;

    private String financeName;

    private String productStatus;

    private String contractId;
    
    private String productLittleType;


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId == null ? null : assetId.trim();
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName == null ? null : assetName.trim();
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType == null ? null : productType.trim();
    }

    public String getSellType() {
        return sellType;
    }

    public void setSellType(String sellType) {
        this.sellType = sellType == null ? null : sellType.trim();
    }

    public String getTransferFlag() {
        return transferFlag;
    }

    public void setTransferFlag(String transferFlag) {
        this.transferFlag = transferFlag == null ? null : transferFlag.trim();
    }

    public String getSubsidyDay() {
        return subsidyDay;
    }

    public void setSubsidyDay(String subsidyDay) {
        this.subsidyDay = subsidyDay == null ? null : subsidyDay.trim();
    }

    public String getIsEstablish() {
        return isEstablish;
    }

    public void setIsEstablish(String isEstablish) {
        this.isEstablish = isEstablish == null ? null : isEstablish.trim();
    }

    public String getAssetRepayStatus() {
        return assetRepayStatus;
    }

    public void setAssetRepayStatus(String assetRepayStatus) {
        this.assetRepayStatus = assetRepayStatus == null ? null : assetRepayStatus.trim();
    }

    public Short getRepayMode() {
        return repayMode;
    }

    public void setRepayMode(Short repayMode) {
        this.repayMode = repayMode;
    }

    public String getCollectStartDt() {
        return collectStartDt;
    }

    public void setCollectStartDt(String collectStartDt) {
        this.collectStartDt = collectStartDt == null ? null : collectStartDt.trim();
    }

    public String getCollectEndDt() {
        return collectEndDt;
    }

    public void setCollectEndDt(String collectEndDt) {
        this.collectEndDt = collectEndDt == null ? null : collectEndDt.trim();
    }

    public String getRentType() {
        return rentType;
    }

    public void setRentType(String rentType) {
        this.rentType = rentType == null ? null : rentType.trim();
    }

    public String getRepayRentType() {
        return repayRentType;
    }

    public void setRepayRentType(String repayRentType) {
        this.repayRentType = repayRentType == null ? null : repayRentType.trim();
    }

    public String getRepayRentDt() {
        return repayRentDt;
    }

    public void setRepayRentDt(String repayRentDt) {
        this.repayRentDt = repayRentDt == null ? null : repayRentDt.trim();
    }

    public String getbLockPeriod() {
        return bLockPeriod;
    }

    public void setbLockPeriod(String bLockPeriod) {
        this.bLockPeriod = bLockPeriod == null ? null : bLockPeriod.trim();
    }

    public String gettLockPeriod() {
        return tLockPeriod;
    }

    public void settLockPeriod(String tLockPeriod) {
        this.tLockPeriod = tLockPeriod == null ? null : tLockPeriod.trim();
    }

    public String getBreBackCheck() {
        return breBackCheck;
    }

    public void setBreBackCheck(String breBackCheck) {
        this.breBackCheck = breBackCheck == null ? null : breBackCheck.trim();
    }

    public String getTransfers() {
        return transfers;
    }

    public void setTransfers(String transfers) {
        this.transfers = transfers == null ? null : transfers.trim();
    }

    public Long getFinanceScale() {
        return financeScale;
    }

    public void setFinanceScale(Long financeScale) {
        this.financeScale = financeScale;
    }

    public Long getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(Long unitCost) {
        this.unitCost = unitCost;
    }

    public String getEstablishCondition() {
        return establishCondition;
    }

    public void setEstablishCondition(String establishCondition) {
        this.establishCondition = establishCondition == null ? null : establishCondition.trim();
    }

    public Long getEstablishRatio() {
        return establishRatio;
    }

    public void setEstablishRatio(Long establishRatio) {
        this.establishRatio = establishRatio;
    }

    public Long getEstablishMinimum() {
        return establishMinimum;
    }

    public void setEstablishMinimum(Long establishMinimum) {
        this.establishMinimum = establishMinimum;
    }

    public String getEstablishDate() {
        return establishDate;
    }

    public void setEstablishDate(String establishDate) {
        this.establishDate = establishDate == null ? null : establishDate.trim();
    }

    public BigDecimal getExpectEarnRate() {
        return expectEarnRate;
    }

    public void setExpectEarnRate(BigDecimal expectEarnRate) {
        this.expectEarnRate = expectEarnRate;
    }

    public Long getInvestMax() {
        return investMax;
    }

    public void setInvestMax(Long investMax) {
        this.investMax = investMax;
    }

    public String getFinanceId() {
        return financeId;
    }

    public void setFinanceId(String financeId) {
        this.financeId = financeId == null ? null : financeId.trim();
    }

    public String getFinanceName() {
        return financeName;
    }

    public void setFinanceName(String financeName) {
        this.financeName = financeName == null ? null : financeName.trim();
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus == null ? null : productStatus.trim();
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId == null ? null : contractId.trim();
    }

	public Long getInvestMin() {
		return investMin;
	}

	public void setInvestMin(Long investMin) {
		this.investMin = investMin;
	}

	public String getProductLittleType() {
		return productLittleType;
	}

	public void setProductLittleType(String productLittleType) {
		this.productLittleType = productLittleType;
	}

	
    
    

}
