package com.app2mobile.metadata;

public class RestaurantStateMetadata implements Comparable<RestaurantStateMetadata>{
	private String state_id, country_code, state_code, state_name;
	

	public String getState_id() {
		return state_id;
	}

	public void setState_id(String state_id) {
		this.state_id = state_id;
	}

	public String getCountry_code() {
		return country_code;
	}

	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}

	public String getState_code() {
		return state_code;
	}

	public void setState_code(String state_code) {
		this.state_code = state_code;
	}

	public String getState_name() {
		return state_name;
	}

	public void setState_name(String state_name) {
		this.state_name = state_name;
	}

	@Override
	public int compareTo(RestaurantStateMetadata another) {
		// TODO Auto-generated method stub
		return state_name.compareToIgnoreCase(another.getState_name());
	}

}
