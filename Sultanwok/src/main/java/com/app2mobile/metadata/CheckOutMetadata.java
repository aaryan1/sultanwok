package com.app2mobile.metadata;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class CheckOutMetadata implements 
		Comparable<CheckOutMetadata> {
	private String checkOutId, productId, productName, productDesc, specialInstruction, quantity,
			pricePerUnit, productSKU,totalAmount,createdOn, currenyType, 
			productSubTitle,addPricePerUnit,addTotalAmt, delivery_time,pickup_time;
	public String getDelivery_time() {
		return delivery_time;
	}

	public void setDelivery_time(String delivery_time) {
		this.delivery_time = delivery_time;
	}

	public String getPickup_time() {
		return pickup_time;
	}

	public void setPickup_time(String pickup_time) {
		this.pickup_time = pickup_time;
	}

	private boolean isLeft;
	private int parentId;
	private int isAttribute;
	private String attributesSKU,bundleProductSKU;
	private HashMap<Integer, ArrayList<String>>  bundleProductHashList;
	
	
	public HashMap<Integer, ArrayList<String>> getBundleProductHashList() {
		return bundleProductHashList;
	}

	public void setBundleProductHashList(
			HashMap<Integer, ArrayList<String>> bundleProductHashList) {
		this.bundleProductHashList = bundleProductHashList;
	}

	public String getBundleProductSKU() {
		return bundleProductSKU;
	}

	public void setBundleProductSKU(String bundleProductSKU) {
		this.bundleProductSKU = bundleProductSKU;
	}

	private ArrayList<String> productSKUList,bundleSKUList;
	private Double totalSalesTax,totalServiceTax,saleTaxUnit,serviceTaxUnit;
	
	


	public ArrayList<String> getBundleSKUList() {
		return bundleSKUList;
	}

	public void setBundleSKUList(ArrayList<String> bundleSKUList) {
		this.bundleSKUList = bundleSKUList;
	}

	public String getAddTotalAmt() {
		return addTotalAmt;
	}

	public void setAddTotalAmt(String addTotalAmt) {
		this.addTotalAmt = addTotalAmt;
	}

	public String getAddPricePerUnit() {
		return addPricePerUnit;
	}

	public void setAddPricePerUnit(String addPricePerUnit) {
		this.addPricePerUnit = addPricePerUnit;
	}

	public String getProductSubTitle() {
		return productSubTitle;
	}

	public void setProductSubTitle(String productSubTitle) {
		this.productSubTitle = productSubTitle;
	}

	public Double getSaleTaxUnit() {
		return saleTaxUnit;
	}

	public void setSaleTaxUnit(Double saleTaxUnit) {
		this.saleTaxUnit = saleTaxUnit;
	}

	public Double getServiceTaxUnit() {
		return serviceTaxUnit;
	}

	public void setServiceTaxUnit(Double serviceTaxUnit) {
		this.serviceTaxUnit = serviceTaxUnit;
	}

	public Double getTotalSalesTax() {
		return totalSalesTax;
	}

	public void setTotalSalesTax(Double totalSalesTax) {
		this.totalSalesTax = totalSalesTax;
	}

	public Double getTotalServiceTax() {
		return totalServiceTax;
	}

	public void setTotalServiceTax(Double totalServiceTax) {
		this.totalServiceTax = totalServiceTax;
	}

	public ArrayList<String> getProductSKUList() {
		return productSKUList;
	}

	public void setProductSKUList(ArrayList<String> productSKUList) {
		this.productSKUList = productSKUList;
	}

	public String getAttributesSKU() {
		return attributesSKU;
	}

	public void setAttributesSKU(String attributesSKU) {
		this.attributesSKU = attributesSKU;
	}

	public int getIsAttribute() {
		return isAttribute;
	}

	public void setIsAttribute(int isAttribute) {
		this.isAttribute = isAttribute;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getProductSKU() {
		return productSKU;
	}

	public void setProductSKU(String productSKU) {
		this.productSKU = productSKU;
	}

	public boolean isLeft() {
		return isLeft;
	}

	public void setLeft(boolean isLeft) {
		this.isLeft = isLeft;
	}

	public String getCheckOutId() {
		return checkOutId;
	}

	public void setCheckOutId(String checkOutId) {
		this.checkOutId = checkOutId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(String pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	public String getCurrenyType() {
		return currenyType;
	}

	public void setCurrenyType(String currenyType) {
		this.currenyType = currenyType;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getSpecialInstruction() {
		return specialInstruction;
	}

	public void setSpecialInstruction(String specialInstruction) {
		this.specialInstruction = specialInstruction;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	@Override
	public int compareTo(CheckOutMetadata another) {
		// TODO Auto-generated method stub
		return productName.compareTo(another.getProductName());
	}

}
