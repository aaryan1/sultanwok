package com.app2mobile.metadata;

public class NavDrawerItem {
	private String mTitle;
	private int mIcon;
	private String mCount = "0";
	private boolean isCounterVisible = false;
	private boolean isHeader = false;
	private boolean isIcon = false;

	public boolean isIcon() {
		return isIcon;
	}

	public void setIcon(boolean isIcon) {
		this.isIcon = isIcon;
	}

	public boolean isHeader() {
		return isHeader;
	}

	public void setHeader(boolean isHeader) {
		this.isHeader = isHeader;
	}

	public NavDrawerItem(String mTitle, boolean isHeader) {

		this.mTitle = mTitle;
		this.isHeader = isHeader;
	}

	public NavDrawerItem(String mTitle, int mIcon, String mCount,
			boolean isCounterVisible, boolean isHeader, boolean isIcon) {

		this.mTitle = mTitle;
		this.mIcon = mIcon;
		this.mCount = mCount;
		this.isCounterVisible = isCounterVisible;
		this.isHeader = isHeader;
		this.isIcon = isIcon;
	}

	public NavDrawerItem(String mTitle, int mIcon, boolean isIcon) {

		this.mTitle = mTitle;
		this.mIcon = mIcon;
		this.isIcon = isIcon;
	}

	public String getmTitle() {
		return mTitle;
	}

	public void setmTitle(String mTitle) {
		this.mTitle = mTitle;
	}

	public int getmIcon() {
		return mIcon;
	}

	public void setmIcon(int mIcon) {
		this.mIcon = mIcon;
	}

	public String getmCount() {
		return mCount;
	}

	public void setmCount(String mCount) {
		this.mCount = mCount;
	}

	public boolean isCounterVisible() {
		return isCounterVisible;
	}

	public void setCounterVisible(boolean isCounterVisible) {
		this.isCounterVisible = isCounterVisible;
	}

}
