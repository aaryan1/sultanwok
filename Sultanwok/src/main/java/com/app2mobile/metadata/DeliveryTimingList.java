package com.app2mobile.metadata;

import java.io.Serializable;
import java.util.ArrayList;



public class DeliveryTimingList implements Serializable{
	ArrayList<String> TimingList;

	public ArrayList<String> getTimingList() {
		return TimingList;
	}

	public void setTimingList(ArrayList<String> timingList) {
		TimingList = timingList;
	}

}
