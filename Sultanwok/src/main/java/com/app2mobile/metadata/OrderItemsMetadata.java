package com.app2mobile.metadata;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderItemsMetadata implements Serializable {
	// private String name, instruction, productId, unitPrice, quantity,
	// productSku, description, inst;

	private String orderId, pId, pQuant, pUnitPrice, pTotalPrice, pDiscount,
			pInstruction, pName, pDesc, pSKU;
	private ArrayList<RestaurantAddOnMetadata> addonList;
	private String attributesSKU;
	private ArrayList<String> attributesIdsList;
	private Double serviceTaxAmt, salesTaxAmt;
	
	public ArrayList<String> getAttributesIdsList() {
		return attributesIdsList;
	}

	public void setAttributesIdsList(ArrayList<String> attributesIdsList) {
		this.attributesIdsList = attributesIdsList;
	}

	public String getAttributesSKU() {
		return attributesSKU;
	}

	public void setAttributesSKU(String attributesSKU) {
		this.attributesSKU = attributesSKU;
	}

	public ArrayList<RestaurantAddOnMetadata> getAddonList() {
		return addonList;
	}

	public void setAddonList(ArrayList<RestaurantAddOnMetadata> addonList) {
		this.addonList = addonList;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getpQuant() {
		return pQuant;
	}

	public void setpQuant(String pQuant) {
		this.pQuant = pQuant;
	}

	public String getpUnitPrice() {
		return pUnitPrice;
	}

	public void setpUnitPrice(String pUnitPrice) {
		this.pUnitPrice = pUnitPrice;
	}

	public String getpTotalPrice() {
		return pTotalPrice;
	}

	public void setpTotalPrice(String pTotalPrice) {
		this.pTotalPrice = pTotalPrice;
	}

	public String getpDiscount() {
		return pDiscount;
	}

	public void setpDiscount(String pDiscount) {
		this.pDiscount = pDiscount;
	}

	public String getpInstruction() {
		return pInstruction;
	}

	public void setpInstruction(String pInstruction) {
		this.pInstruction = pInstruction;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getpDesc() {
		return pDesc;
	}

	public void setpDesc(String pDesc) {
		this.pDesc = pDesc;
	}

	public String getpSKU() {
		return pSKU;
	}

	public void setpSKU(String pSKU) {
		this.pSKU = pSKU;
	}

	public Double getServiceTaxAmt() {
		return serviceTaxAmt;
	}

	public void setServiceTaxAmt(Double serviceTaxAmt) {
		this.serviceTaxAmt = serviceTaxAmt;
	}

	public Double getSalesTaxAmt() {
		return salesTaxAmt;
	}

	public void setSalesTaxAmt(Double salesTaxAmt) {
		this.salesTaxAmt = salesTaxAmt;
	}

}
