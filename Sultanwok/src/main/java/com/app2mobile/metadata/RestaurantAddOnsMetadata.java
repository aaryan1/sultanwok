package com.app2mobile.metadata;

import java.io.Serializable;

public class RestaurantAddOnsMetadata implements Serializable{
	private String id, taxId, aName, aPrice, inStock, aDesc, aImg,serviceTaxAmt,salesTaxAmt,mGroupId,salesTaxAmtPercentage,serviceTaxAmtPercentage;
	private boolean isSelected;
	
	private RestaurantTaxClassMetaData mTaxDetail;

	
	public String getServiceTaxAmtPercentage() {
		return serviceTaxAmtPercentage;
	}

	public void setServiceTaxAmtPercentage(String serviceTaxAmtPercentage) {
		this.serviceTaxAmtPercentage = serviceTaxAmtPercentage;
	}

	public String getSalesTaxAmtPercentage() {
		return salesTaxAmtPercentage;
	}

	public void setSalesTaxAmtPercentage(String salesTaxAmtPercentage) {
		this.salesTaxAmtPercentage = salesTaxAmtPercentage;
	}

	public String getmGroupId() {
		return mGroupId;
	}

	public void setmGroupId(String mGroupId) {
		this.mGroupId = mGroupId;
	}

	public String getServiceTaxAmt() {
		return serviceTaxAmt;
	}

	public void setServiceTaxAmt(String serviceTaxAmt) {
		this.serviceTaxAmt = serviceTaxAmt;
	}

	public String getSalesTaxAmt() {
		return salesTaxAmt;
	}

	public void setSalesTaxAmt(String salesTaxAmt) {
		this.salesTaxAmt = salesTaxAmt;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public RestaurantTaxClassMetaData getmTaxDetail() {
		return mTaxDetail;
	}

	public void setmTaxDetail(RestaurantTaxClassMetaData mTaxDetail) {
		this.mTaxDetail = mTaxDetail;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTaxId() {
		return taxId;
	}

	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	public String getaName() {
		return aName;
	}

	public void setaName(String aName) {
		this.aName = aName;
	}

	public String getaPrice() {
		return aPrice;
	}

	public void setaPrice(String aPrice) {
		this.aPrice = aPrice;
	}

	public String getInStock() {
		return inStock;
	}

	public void setInStock(String inStock) {
		this.inStock = inStock;
	}

	public String getaDesc() {
		return aDesc;
	}

	public void setaDesc(String aDesc) {
		this.aDesc = aDesc;
	}

	public String getaImg() {
		return aImg;
	}

	public void setaImg(String aImg) {
		this.aImg = aImg;
	}

}
