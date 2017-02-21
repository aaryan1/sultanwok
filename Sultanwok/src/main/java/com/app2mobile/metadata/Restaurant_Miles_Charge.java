package com.app2mobile.metadata;

import java.io.Serializable;

public class Restaurant_Miles_Charge implements Serializable{
String miles, charge;

public String getMiles() {
	return miles;
}

public void setMiles(String miles) {
	this.miles = miles;
}

public String getCharge() {
	return charge;
}

public void setCharge(String charge) {
	this.charge = charge;
}
}
