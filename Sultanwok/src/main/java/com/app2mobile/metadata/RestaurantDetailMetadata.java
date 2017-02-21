package com.app2mobile.metadata;

import java.io.Serializable;
import java.util.HashMap;

import android.R.integer;

public class RestaurantDetailMetadata implements Serializable{
	private String mStoreId, mStoreName, mStoreDeliveryStatus,
	mStoreDeliveryCharge, mStoreAvgDeliveryTime,
	mStoreMinDeliveryValue, mStoreTermsNCond, mCreateTs,mStoreNumber,mStore_payment,mStore_color;
	

	private HashMap<Integer, Restaurant_Miles_Charge> store_miles_charge;
	
	public HashMap<Integer, Restaurant_Miles_Charge> getStore_miles_charge() {
		return store_miles_charge;
	}

	public void setStore_miles_charge(HashMap<Integer, Restaurant_Miles_Charge> store_miles_charge) {
		this.store_miles_charge = store_miles_charge;
	}

	public String getmStore_payment() {
		return mStore_payment;
	}

	public String getmStore_color() {
		return mStore_color;
	}

	public void setmStore_color(String mStore_color) {
		this.mStore_color = mStore_color;
	}

	public void setmStore_payment(String mStore_payment) {
		this.mStore_payment = mStore_payment;
	}

private HashMap<String, RestaurantDeliveryTimeMetadata> mDeliveryList;
private HashMap<String, DeliveryTimingList> timelist;

	public HashMap<String, DeliveryTimingList> getTimelist() {
	return timelist;
}

public void setTimelist(HashMap<String, DeliveryTimingList> timelist) {
	this.timelist = timelist;
}

	public String getmStoreNumber() {
	return mStoreNumber;
}

public void setmStoreNumber(String mStoreNumber) {
	this.mStoreNumber = mStoreNumber;
}

public HashMap<String, RestaurantDeliveryTimeMetadata> getmDeliveryList() {
	return mDeliveryList;
}

public void setmDeliveryList(
		HashMap<String, RestaurantDeliveryTimeMetadata> mDeliveryList) {
	this.mDeliveryList = mDeliveryList;
}

	public String getmStoreId() {
		return mStoreId;
	}

	public void setmStoreId(String mStoreId) {
		this.mStoreId = mStoreId;
	}

	public String getmStoreName() {
		return mStoreName;
	}

	public void setmStoreName(String mStoreName) {
		this.mStoreName = mStoreName;
	}

	public String getmStoreDeliveryStatus() {
		return mStoreDeliveryStatus;
	}

	public void setmStoreDeliveryStatus(String mStoreDeliveryStatus) {
		this.mStoreDeliveryStatus = mStoreDeliveryStatus;
	}

	public String getmStoreDeliveryCharge() {
		return mStoreDeliveryCharge;
	}

	public void setmStoreDeliveryCharge(String mStoreDeliveryCharge) {
		this.mStoreDeliveryCharge = mStoreDeliveryCharge;
	}

	public String getmStoreAvgDeliveryTime() {
		return mStoreAvgDeliveryTime;
	}

	public void setmStoreAvgDeliveryTime(String mStoreAvgDeliveryTime) {
		this.mStoreAvgDeliveryTime = mStoreAvgDeliveryTime;
	}

	public String getmStoreMinDeliveryValue() {
		return mStoreMinDeliveryValue;
	}

	public void setmStoreMinDeliveryValue(String mStoreMinDeliveryValue) {
		this.mStoreMinDeliveryValue = mStoreMinDeliveryValue;
	}

	public String getmStoreTermsNCond() {
		return mStoreTermsNCond;
	}

	public void setmStoreTermsNCond(String mStoreTermsNCond) {
		this.mStoreTermsNCond = mStoreTermsNCond;
	}

	public String getmCreateTs() {
		return mCreateTs;
	}

	public void setmCreateTs(String mCreateTs) {
		this.mCreateTs = mCreateTs;
	}
	
	

}
