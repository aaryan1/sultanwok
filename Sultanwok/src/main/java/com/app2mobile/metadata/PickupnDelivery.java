package com.app2mobile.metadata;

import java.util.ArrayList;

public class PickupnDelivery {
String pickup, delivery;
ArrayList<String> picuplist;
ArrayList<String> card_cash;
public ArrayList<String> getCard_cash() {
	return card_cash;
}

public void setCard_cash(ArrayList<String> card_cash) {
	this.card_cash = card_cash;
}

public ArrayList<String> getPicuplist() {
	return picuplist;
}

public void setPicuplist(ArrayList<String> picuplist) {
	this.picuplist = picuplist;
}



}
