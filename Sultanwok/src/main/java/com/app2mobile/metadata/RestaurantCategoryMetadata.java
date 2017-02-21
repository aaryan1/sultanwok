package com.app2mobile.metadata;

import java.io.Serializable;
import java.util.ArrayList;

public class RestaurantCategoryMetadata implements Serializable{
private String categoryName;
private int categoryId;
private String catImg;
private int parentId;
;private ArrayList<RestaurantCategoryMetadata> subCategoryList;


public int getParentId() {
	return parentId;
}
public void setParentId(int parentId) {
	this.parentId = parentId;
}
public ArrayList<RestaurantCategoryMetadata> getSubCategoryList() {
	return subCategoryList;
}
public void setSubCategoryList(
		ArrayList<RestaurantCategoryMetadata> subCategoryList) {
	this.subCategoryList = subCategoryList;
}
public int getProductId() {
	return getProductId();
}
public void setProductId(int parentId) {
	this.parentId = parentId;
}
private ArrayList<RestaurantProductMetadata> productList;


public String getCatImg() {
	return catImg;
}
public void setCatImg(String catImg) {
	this.catImg = catImg;
}
public String getCategoryName() {
	return categoryName;
}
public void setCategoryName(String categoryName) {
	this.categoryName = categoryName;
}
public int getCategoryId() {
	return categoryId;
}
public void setCategoryId(int categoryId) {
	this.categoryId = categoryId;
}
public ArrayList<RestaurantProductMetadata> getProductList() {
	return productList;
}
public void setProductList(ArrayList<RestaurantProductMetadata> productList) {
	this.productList = productList;
}

}
