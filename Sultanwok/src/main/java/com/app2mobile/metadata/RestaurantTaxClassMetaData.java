package com.app2mobile.metadata;

import java.io.Serializable;

public class RestaurantTaxClassMetaData implements Serializable{
	private String mTaxId, mClassName, mStroeId, mServiceTax, mSalesTax,
			mServiceTaxPer, mSalesTaxPer, mCreateTs;

	public String getmTaxId() {
		return mTaxId;
	}

	public void setmTaxId(String mTaxId) {
		this.mTaxId = mTaxId;
	}

	public String getmClassName() {
		return mClassName;
	}

	public void setmClassName(String mClassName) {
		this.mClassName = mClassName;
	}

	public String getmStroeId() {
		return mStroeId;
	}

	public void setmStroeId(String mStroeId) {
		this.mStroeId = mStroeId;
	}

	public String getmServiceTax() {
		return mServiceTax;
	}

	public void setmServiceTax(String mServiceTax) {
		this.mServiceTax = mServiceTax;
	}

	public String getmSalesTax() {
		return mSalesTax;
	}

	public void setmSalesTax(String mSalesTax) {
		this.mSalesTax = mSalesTax;
	}

	public String getmServiceTaxPer() {
		return mServiceTaxPer;
	}

	public void setmServiceTaxPer(String mServiceTaxPer) {
		this.mServiceTaxPer = mServiceTaxPer;
	}

	public String getmSalesTaxPer() {
		return mSalesTaxPer;
	}

	public void setmSalesTaxPer(String mSalesTaxPer) {
		this.mSalesTaxPer = mSalesTaxPer;
	}

	public String getmCreateTs() {
		return mCreateTs;
	}

	public void setmCreateTs(String mCreateTs) {
		this.mCreateTs = mCreateTs;
	}
	
	

}
