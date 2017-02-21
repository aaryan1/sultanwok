package com.app2mobile.metadata;

import java.io.Serializable;

public class RestaurantAddOnMetadata implements Serializable{
	String id,taxId,pName,pPrice,inStock,pDesc,serviceTaxAmt,salesTaxAmt,pImage;
	private boolean isSelected;
	
	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
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

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getpPrice() {
		return pPrice;
	}

	public void setpPrice(String pPrice) {
		this.pPrice = pPrice;
	}

	

	public String getInStock() {
		return inStock;
	}

	public void setInStock(String inStock) {
		this.inStock = inStock;
	}

	public String getpDesc() {
		return pDesc;
	}

	public void setpDesc(String pDesc) {
		this.pDesc = pDesc;
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

	public String getpImage() {
		return pImage;
	}

	public void setpImage(String pImage) {
		this.pImage = pImage;
	}
	

}
