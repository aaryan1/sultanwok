package com.app2mobile.metadata;

public class RestaurantLocalityMetadata implements Comparable<RestaurantLocalityMetadata>{
	private String localionId,storeDeiveredLocationId,locationName,cityId,cityName,stateCode,countryCode,mov,deliveryCharge;

	public String getLocalionId() {
		return localionId;
	}

	public void setLocalionId(String localionId) {
		this.localionId = localionId;
	}

	public String getStoreDeiveredLocationId() {
		return storeDeiveredLocationId;
	}

	public void setStoreDeiveredLocationId(String storeDeiveredLocationId) {
		this.storeDeiveredLocationId = storeDeiveredLocationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
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

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getMov() {
		return mov;
	}

	public void setMov(String mov) {
		this.mov = mov;
	}

	public String getDeliveryCharge() {
		return deliveryCharge;
	}

	public void setDeliveryCharge(String deliveryCharge) {
		this.deliveryCharge = deliveryCharge;
	}

	@Override
	public int compareTo(RestaurantLocalityMetadata another) {
		// TODO Auto-generated method stub
		return locationName.compareToIgnoreCase(another.locationName);
	}
	
}
