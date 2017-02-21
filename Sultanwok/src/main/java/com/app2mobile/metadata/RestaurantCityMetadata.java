package com.app2mobile.metadata;

public class RestaurantCityMetadata implements
		Comparable<RestaurantCityMetadata> {
	private String cityId, cityName, stateCode, storeDeliveredId,
			minimumOrderValue;

	
	
	public String getStoreDeliveredId() {
		return storeDeliveredId;
	}

	public void setStoreDeliveredId(String storeDeliveredId) {
		this.storeDeliveredId = storeDeliveredId;
	}

	public String getMinimumOrderValue() {
		return minimumOrderValue;
	}

	public void setMinimumOrderValue(String minimumOrderValue) {
		this.minimumOrderValue = minimumOrderValue;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	@Override
	public int compareTo(RestaurantCityMetadata another) {
		// TODO Auto-generated method stub
		return cityName.compareToIgnoreCase(another.getCityName());
	}

}
