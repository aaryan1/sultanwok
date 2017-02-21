package com.app2mobile.metadata;

import java.io.Serializable;
import java.util.ArrayList;

public class FavoriteProductMetadata implements Serializable,
		Comparable<FavoriteProductMetadata> {
	private boolean isAddedInCart;
	private String favId, productId, productName, productDesc,
			specialInstruction, quantity, pricePerUnit, productSKU,
			totalAmount, createdOn, currenyType, productSubTitle,
			addPricePerUnit, addTotalAmt,mGroupId;
	private ArrayList<String> attributesIdsList;
	private Double totalSalesTax, totalServiceTax, saleTaxUnit, serviceTaxUnit;
	private int pType, parentId;
	private ArrayList<FavoriteProductMetadata> favAttributesList;

	public String getmGroupId() {
		return mGroupId;
	}

	public void setmGroupId(String mGroupId) {
		this.mGroupId = mGroupId;
	}

	public ArrayList<FavoriteProductMetadata> getFavAttributesList() {
		return favAttributesList;
	}

	public void setFavAttributesList(
			ArrayList<FavoriteProductMetadata> favAttributesList) {
		this.favAttributesList = favAttributesList;
	}

	public ArrayList<String> getAttributesIdsList() {
		return attributesIdsList;
	}

	public void setAttributesIdsList(ArrayList<String> attributesIdsList) {
		this.attributesIdsList = attributesIdsList;
	}

	public boolean isAddedInCart() {
		return isAddedInCart;
	}

	public void setAddedInCart(boolean isAddedInCart) {
		this.isAddedInCart = isAddedInCart;
	}

	public String getProductSubTitle() {
		return productSubTitle;
	}

	public void setProductSubTitle(String productSubTitle) {
		this.productSubTitle = productSubTitle;
	}

	public String getAddPricePerUnit() {
		return addPricePerUnit;
	}

	public void setAddPricePerUnit(String addPricePerUnit) {
		this.addPricePerUnit = addPricePerUnit;
	}

	public String getAddTotalAmt() {
		return addTotalAmt;
	}

	public void setAddTotalAmt(String addTotalAmt) {
		this.addTotalAmt = addTotalAmt;
	}

	// private ArrayList<FavoriteProductMetadata> favList;

	// public ArrayList<FavoriteProductMetadata> getFavList() {
	// return favList;
	// }
	//
	// public void setFavList(ArrayList<FavoriteProductMetadata> favList) {
	// this.favList = favList;
	// }

	public String getFavId() {
		return favId;
	}

	public void setFavId(String favId) {
		this.favId = favId;
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

	public String getSpecialInstruction() {
		return specialInstruction;
	}

	public void setSpecialInstruction(String specialInstruction) {
		this.specialInstruction = specialInstruction;
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

	public String getProductSKU() {
		return productSKU;
	}

	public void setProductSKU(String productSKU) {
		this.productSKU = productSKU;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getCurrenyType() {
		return currenyType;
	}

	public void setCurrenyType(String currenyType) {
		this.currenyType = currenyType;
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

	public int getpType() {
		return pType;
	}

	public void setpType(int pType) {
		this.pType = pType;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	// private String favId;
	// private String productId;
	// private String productName;
	// private String currency;
	// private String productPrice,productSKU;
	// private String createdOn, specialInstruction, totalAmount, productDesc,
	// quantity;
	// private boolean isAddedInCart;
	// private int parentId;
	// private int isAttribute;
	// private ArrayList<String> productNamesList;
	//
	//
	// public ArrayList<String> getProductNamesList() {
	// return productNamesList;
	// }
	//
	// public void setProductNamesList(ArrayList<String> productNamesList) {
	// this.productNamesList = productNamesList;
	// }
	//
	// public int getParentId() {
	// return parentId;
	// }
	//
	// public void setParentId(int parentId) {
	// this.parentId = parentId;
	// }
	//
	// public int getIsAttribute() {
	// return isAttribute;
	// }
	//
	// public void setIsAttribute(int isAttribute) {
	// this.isAttribute = isAttribute;
	// }
	//
	// public String getProductSKU() {
	// return productSKU;
	// }
	//
	// public void setProductSKU(String productSKU) {
	// this.productSKU = productSKU;
	// }
	//
	//
	//
	// public String getSpecialInstruction() {
	// return specialInstruction;
	// }
	//
	// public void setSpecialInstruction(String specialInstruction) {
	// this.specialInstruction = specialInstruction;
	// }
	//
	// public String getTotalAmount() {
	// return totalAmount;
	// }
	//
	// public void setTotalAmount(String totalAmount) {
	// this.totalAmount = totalAmount;
	// }
	//
	// public String getProductDesc() {
	// return productDesc;
	// }
	//
	// public void setProductDesc(String productDesc) {
	// this.productDesc = productDesc;
	// }
	//
	// public String getQuantity() {
	// return quantity;
	// }
	//
	// public void setQuantity(String quantity) {
	// this.quantity = quantity;
	// }
	//
	// public boolean isAddedInCart() {
	// return isAddedInCart;
	// }
	//
	// public void setAddedInCart(boolean isAddedInCart) {
	// this.isAddedInCart = isAddedInCart;
	// }
	//
	// public String getFavId() {
	// return favId;
	// }
	//
	// public void setFavId(String favId) {
	// this.favId = favId;
	// }
	//
	// public String getProductId() {
	// return productId;
	// }
	//
	// public void setProductId(String productId) {
	// this.productId = productId;
	// }
	//
	// public String getProductName() {
	// return productName;
	// }
	//
	// public void setProductName(String productName) {
	// this.productName = productName;
	// }
	//
	// public String getCurrency() {
	// return currency;
	// }
	//
	// public void setCurrency(String currency) {
	// this.currency = currency;
	// }
	//
	// public String getProductPrice() {
	// return productPrice;
	// }
	//
	// public void setProductPrice(String productPrice) {
	// this.productPrice = productPrice;
	// }
	//
	// public String getCreatedOn() {
	// return createdOn;
	// }
	//
	// public void setCreatedOn(String createdOn) {
	// this.createdOn = createdOn;
	// }
	//
	@Override
	public int compareTo(FavoriteProductMetadata another) {
		// TODO Auto-generated method stub
		return productName.compareTo(another.getProductName());
	}

}
