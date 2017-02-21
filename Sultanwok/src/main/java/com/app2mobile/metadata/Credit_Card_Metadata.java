package com.app2mobile.metadata;

public class Credit_Card_Metadata {
private String card_holder_name,card_number,exp_month,exp_year,card_image,token;


private boolean isSelected;

public Credit_Card_Metadata() {
	// TODO Auto-generated constructor stub
}
public Credit_Card_Metadata(String card_holder_name,String card_number, String exp_month,String exp_year ,String card_image,String token){
	this.card_holder_name=card_holder_name;
	this.card_number=card_number;
	this.exp_month=exp_month;
	this.exp_year=exp_year;
	this.card_image=card_image;
	this.token= token;
}


public String getToken() {
	return token;
}
public void setToken(String token) {
	this.token = token;
}
public String getExp_month() {
	return exp_month;
}
public void setExp_month(String exp_month) {
	this.exp_month = exp_month;
}
public String getExp_year() {
	return exp_year;
}
public void setExp_year(String exp_year) {
	this.exp_year = exp_year;
}

public boolean isSelected() {
	return isSelected;
}
public void setSelected(boolean isSelected) {
	this.isSelected = isSelected;
}

public String getCard_image() {
	return card_image;
}

public void setCard_image(String card_image) {
	this.card_image = card_image;
}

public String getCard_holder_name() {
	return card_holder_name;
}

public void setCard_holder_name(String card_holder_name) {
	this.card_holder_name = card_holder_name;
}

public String getCard_number() {
	return card_number;
}

public void setCard_number(String card_number) {
	this.card_number = card_number;
}





}
