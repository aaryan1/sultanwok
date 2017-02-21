package com.app2mobile.metadata;

import java.io.Serializable;

public class RestaurantVarientsMetadata implements Serializable{
	private String id,taxId,pName,pPrice,pStock,pDesc,salesTaxAmtPercentage,serviceTaxAmtPercentage;;
	private boolean isSelected;
//	private Double salesTaxAmt,serviceTaxAmt;
	private String salesTaxAmt,serviceTaxAmt;
	
//	public Double getSalesTaxAmt() {
//		return salesTaxAmt;
//	}
//
//	public void setSalesTaxAmt(Double salesTaxAmt) {
//		this.salesTaxAmt = salesTaxAmt;
//	}
//
//	public Double getServiceTaxAmt() {
//		return serviceTaxAmt;
//	}
//
//	public void setServiceTaxAmt(Double serviceTaxAmt) {
//		this.serviceTaxAmt = serviceTaxAmt;
//	}

	public boolean isSelected() {
		return isSelected;
	}

	public String getSalesTaxAmtPercentage() {
		return salesTaxAmtPercentage;
	}

	public void setSalesTaxAmtPercentage(String salesTaxAmtPercentage) {
		this.salesTaxAmtPercentage = salesTaxAmtPercentage;
	}

	public String getServiceTaxAmtPercentage() {
		return serviceTaxAmtPercentage;
	}

	public void setServiceTaxAmtPercentage(String serviceTaxAmtPercentage) {
		this.serviceTaxAmtPercentage = serviceTaxAmtPercentage;
	}

	public String getSalesTaxAmt() {
		return salesTaxAmt;
	}

	public void setSalesTaxAmt(String salesTaxAmt) {
		this.salesTaxAmt = salesTaxAmt;
	}

	public String getServiceTaxAmt() {
		return serviceTaxAmt;
	}

	public void setServiceTaxAmt(String serviceTaxAmt) {
		this.serviceTaxAmt = serviceTaxAmt;
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

	public String getpStock() {
		return pStock;
	}

	public void setpStock(String pStock) {
		this.pStock = pStock;
	}

	public String getpDesc() {
		return pDesc;
	}

	public void setpDesc(String pDesc) {
		this.pDesc = pDesc;
	}
	
	

}
