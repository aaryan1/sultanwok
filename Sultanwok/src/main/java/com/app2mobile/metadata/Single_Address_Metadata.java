package com.app2mobile.metadata;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Single_Address_Metadata implements Serializable {
String address1,address2,city,state,pincode,cityCode,stateCode,address_name,shipping_id;
private boolean isSelected;


public boolean isSelected() {
	return isSelected;
}
public void setSelected(boolean isSelected) {
	this.isSelected = isSelected;
}
public String getAddress_name() {
	return address_name;
}
public void setAddress_name(String address_name) {
	this.address_name = address_name;
}
public String getShipping_id() {
	return shipping_id;
}
public void setShipping_id(String shipping_id) {
	this.shipping_id = shipping_id;
}
public String getCityCode() {
	return cityCode;
}
public void setCityCode(String cityCode) {
	this.cityCode = cityCode;
}
public String getStateCode() {
	return stateCode;
}
public void setStateCode(String stateCode) {
	this.stateCode = stateCode;
}
public Single_Address_Metadata(String address1, String city,String state,String pincode,String cityCode,String stateCode,String address_name,String shipping_id) {
	this.address1=address1;
	this.city=city;
	this.state=state;
	this.pincode=pincode;
	this.stateCode=stateCode;
	this.cityCode=cityCode;
	this.address_name=address_name;
	this.shipping_id=shipping_id;
}
public String getAddress1() {
	return address1;
}

public void setAddress1(String address1) {
	this.address1 = address1;
}

public String getAddress2() {
	return address2;
}

public void setAddress2(String address2) {
	this.address2 = address2;
}

public String getCity() {
	return city;
}

public void setCity(String city) {
	this.city = city;
}

public String getState() {
	return state;
}

public void setState(String state) {
	this.state = state;
}

public String getPincode() {
	return pincode;
}

public void setPincode(String pincode) {
	this.pincode = pincode;
}

}
