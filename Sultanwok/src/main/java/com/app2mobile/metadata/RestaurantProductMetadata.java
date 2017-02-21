package com.app2mobile.metadata;

import java.io.Serializable;
import java.util.ArrayList;

public class RestaurantProductMetadata implements Serializable{
	private int catId,pId,pQuant;
	private String pTaxId,pName,pDesc,pImage,pSKU;
	private Double pPrice;
	private ArrayList<RestaurantAddOnMetadata> addOnList;
	private ArrayList<RestaurantVarientsMetadata> varientsList;
	private ArrayList<RestaurantBundleMetadata> bundleList;
	private ArrayList<RestaurantAttributeMetadata> attributeList;
	private ArrayList<RestaurantCategoryMetadata> SubCategory;
	public ArrayList<RestaurantCategoryMetadata> getSubCategory() {
		return SubCategory;
	}

	public void setSubCategory(ArrayList<RestaurantCategoryMetadata> subCategory) {
		SubCategory = subCategory;
	}
	private Double salesTaxAmt,serviceTaxAmt;


	public ArrayList<RestaurantAttributeMetadata> getAttributeList() {
		return attributeList;
	}

	public void setAttributeList(
			ArrayList<RestaurantAttributeMetadata> attributeList) {
		this.attributeList = attributeList;
	}

	public ArrayList<RestaurantBundleMetadata> getBundleList() {
		return bundleList;
	}

	public void setBundleList(ArrayList<RestaurantBundleMetadata> bundleList) {
		this.bundleList = bundleList;
	}

	public Double getSalesTaxAmt() {
		return salesTaxAmt;
	}

	public void setSalesTaxAmt(Double salesTaxAmt) {
		this.salesTaxAmt = salesTaxAmt;
	}

	public Double getServiceTaxAmt() {
		return serviceTaxAmt;
	}

	public void setServiceTaxAmt(Double serviceTaxAmt) {
		this.serviceTaxAmt = serviceTaxAmt;
	}

	public String getpSKU() {
		return pSKU;
	}
	public void setpSKU(String pSKU) {
		this.pSKU = pSKU;
	}
	public int getCatId() {
		return catId;
	}
	public void setCatId(int catId) {
		this.catId = catId;
	}
	public int getpId() {
		return pId;
	}
	public void setpId(int pId) {
		this.pId = pId;
	}
	public int getpQuant() {
		return pQuant;
	}
	public void setpQuant(int pQuant) {
		this.pQuant = pQuant;
	}
	public String getpTaxId() {
		return pTaxId;
	}
	public void setpTaxId(String pTaxId) {
		this.pTaxId = pTaxId;
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
	public String getpImage() {
		return pImage;
	}
	public void setpImage(String pImage) {
		this.pImage = pImage;
	}
	public Double getpPrice() {
		return pPrice;
	}
	public void setpPrice(Double pPrice) {
		this.pPrice = pPrice;
	}
	public ArrayList<RestaurantAddOnMetadata> getAddOnList() {
		return addOnList;
	}
	public void setAddOnList(ArrayList<RestaurantAddOnMetadata> addOnList) {
		this.addOnList = addOnList;
	}
	public ArrayList<RestaurantVarientsMetadata> getVarientsList() {
		return varientsList;
	}
	public void setVarientsList(ArrayList<RestaurantVarientsMetadata> varientsList) {
		this.varientsList = varientsList;
	}

}
