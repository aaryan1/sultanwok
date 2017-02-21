package com.app2mobile.metadata;

import java.io.Serializable;

public class ProductOptionsMetadata implements Serializable{
private String pLabel,pPrice,pSKU;
private boolean isSelected;

public boolean isSelected() {
	return isSelected;
}

public void setSelected(boolean isSelected) {
	this.isSelected = isSelected;
}

public String getpLabel() {
	return pLabel;
}

public void setpLabel(String pLabel) {
	this.pLabel = pLabel;
}

public String getpPrice() {
	return pPrice;
}

public void setpPrice(String pPrice) {
	this.pPrice = pPrice;
}

public String getpSKU() {
	return pSKU;
}

public void setpSKU(String pSKU) {
	this.pSKU = pSKU;
}

}
