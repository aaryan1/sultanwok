package com.app2mobile.metadata;

import java.io.Serializable;
import java.util.ArrayList;

public class RestaurantMetadata implements Serializable {
	private String mBaseUrl, mProductImgUrl, mCategoryImgUrl,mBundleImgUrl,category_name;
	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	// private String mStoreId, mStoreName, mStoreDeliveryStatus,
	// mStoreDeliveryCharge, mStoreAvgDeliveryTime,
	// mStoreMinDeliveryValue, mStoreTermsNCond, mCreateTs;
	private RestaurantDetailMetadata restaurantDetail;
	private ArrayList<RestaurantCategoryMetadata> categoryList;

	
	public String getmBundleImgUrl() {
		return mBundleImgUrl;
	}

	public void setmBundleImgUrl(String mBundleImgUrl) {
		this.mBundleImgUrl = mBundleImgUrl;
	}

	public String getmBaseUrl() {
		return mBaseUrl;
	}

	public void setmBaseUrl(String mBaseUrl) {
		this.mBaseUrl = mBaseUrl;
	}

	public String getmProductImgUrl() {
		return mProductImgUrl;
	}

	public void setmProductImgUrl(String mProductImgUrl) {
		this.mProductImgUrl = mProductImgUrl;
	}

	public String getmCategoryImgUrl() {
		return mCategoryImgUrl;
	}

	public void setmCategoryImgUrl(String mCategoryImgUrl) {
		this.mCategoryImgUrl = mCategoryImgUrl;
	}

	public RestaurantDetailMetadata getRestaurantDetail() {
		return restaurantDetail;
	}

	public void setRestaurantDetail(RestaurantDetailMetadata restaurantDetail) {
		this.restaurantDetail = restaurantDetail;
	}

	// public String getmStoreId() {
	// return mStoreId;
	// }
	// public void setmStoreId(String mStoreId) {
	// this.mStoreId = mStoreId;
	// }
	// public String getmStoreName() {
	// return mStoreName;
	// }
	// public void setmStoreName(String mStoreName) {
	// this.mStoreName = mStoreName;
	// }
	// public String getmStoreDeliveryStatus() {
	// return mStoreDeliveryStatus;
	// }
	// public void setmStoreDeliveryStatus(String mStoreDeliveryStatus) {
	// this.mStoreDeliveryStatus = mStoreDeliveryStatus;
	// }
	// public String getmStoreDeliveryCharge() {
	// return mStoreDeliveryCharge;
	// }
	// public void setmStoreDeliveryCharge(String mStoreDeliveryCharge) {
	// this.mStoreDeliveryCharge = mStoreDeliveryCharge;
	// }
	// public String getmStoreAvgDeliveryTime() {
	// return mStoreAvgDeliveryTime;
	// }
	// public void setmStoreAvgDeliveryTime(String mStoreAvgDeliveryTime) {
	// this.mStoreAvgDeliveryTime = mStoreAvgDeliveryTime;
	// }
	// public String getmStoreMinDeliveryValue() {
	// return mStoreMinDeliveryValue;
	// }
	// public void setmStoreMinDeliveryValue(String mStoreMinDeliveryValue) {
	// this.mStoreMinDeliveryValue = mStoreMinDeliveryValue;
	// }
	// public String getmStoreTermsNCond() {
	// return mStoreTermsNCond;
	// }
	// public void setmStoreTermsNCond(String mStoreTermsNCond) {
	// this.mStoreTermsNCond = mStoreTermsNCond;
	// }
	// public String getmCreateTs() {
	// return mCreateTs;
	// }
	// public void setmCreateTs(String mCreateTs) {
	// this.mCreateTs = mCreateTs;
	// }
	public ArrayList<RestaurantCategoryMetadata> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(
			ArrayList<RestaurantCategoryMetadata> categoryList) {
		this.categoryList = categoryList;
	}

}
