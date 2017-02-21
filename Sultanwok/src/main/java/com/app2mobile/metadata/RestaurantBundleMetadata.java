package com.app2mobile.metadata;

import java.io.Serializable;
import java.util.ArrayList;

public class RestaurantBundleMetadata implements Serializable {
	private String mGroupName, mGroupType, mGroupSelectType, mGroupId,mGroupImage;
	private int mGroupSeletOption;
	private boolean isSelectionRequired,setselection;

	

	ArrayList<RestaurantAddOnsMetadata> bundleAddOnsList;

	// ArrayList<RestaurantGroupOptionsMetadata> groupOptionsMetadata;

	// public ArrayList<RestaurantGroupOptionsMetadata>
	// getGroupOptionsMetadata() {
	// return groupOptionsMetadata;
	// }
	// public void setGroupOptionsMetadata(
	// ArrayList<RestaurantGroupOptionsMetadata> groupOptionsMetadata) {
	// this.groupOptionsMetadata = groupOptionsMetadata;
	// }
	public boolean isSetselection() {
		return setselection;
	}

	public void setSetselection(boolean setselection) {
		this.setselection = setselection;
	}
	public String getmGroupName() {
		return mGroupName;
	}

	public String getmGroupImage() {
		return mGroupImage;
	}

	public void setmGroupImage(String mGroupImage) {
		this.mGroupImage = mGroupImage;
	}

	public String getmGroupId() {
		return mGroupId;
	}

	public void setmGroupId(String mId) {
		this.mGroupId = mId;
	}

	public boolean isSelectionRequired() {
		return isSelectionRequired;
	}

	public void setSelectionRequired(boolean isSelectionRequired) {
		this.isSelectionRequired = isSelectionRequired;
	}

	public String getmGroupSelectType() {
		return mGroupSelectType;
	}

	public void setmGroupSelectType(String mGroupSelectType) {
		this.mGroupSelectType = mGroupSelectType;
	}

	public int getmGroupSeletOption() {
		return mGroupSeletOption;
	}

	public void setmGroupSeletOption(int mGroupSeletOption) {
		this.mGroupSeletOption = mGroupSeletOption;
	}

	public void setmGroupName(String mGroupName) {
		this.mGroupName = mGroupName;
	}

	public String getmGroupType() {
		return mGroupType;
	}

	public void setmGroupType(String mGroupType) {
		this.mGroupType = mGroupType;
	}

	public ArrayList<RestaurantAddOnsMetadata> getBundleAddOnsList() {
		return bundleAddOnsList;
	}

	public void setBundleAddOnsList(
			ArrayList<RestaurantAddOnsMetadata> bundleAddOnsList) {
		this.bundleAddOnsList = bundleAddOnsList;
	}

}
