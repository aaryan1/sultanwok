package com.app2mobile.metadata;

import java.io.Serializable;

public class RestaurantGroupOptionsMetadata implements Serializable{
	private String pName;
	private String pDesc;
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
	

}
