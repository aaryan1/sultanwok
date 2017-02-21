package com.app2mobile.metadata;

import java.io.Serializable;

public class RestaurantDeliveryTimeMetadata implements Serializable{
private String startTime,endTime;

public String getStartTime() {
	return startTime;
}

public void setStartTime(String startTime) {
	this.startTime = startTime;
}

public String getEndTime() {
	return endTime;
}

public void setEndTime(String endTime) {
	this.endTime = endTime;
}

}
