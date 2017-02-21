package com.app2mobile.metadata;

import java.io.Serializable;

public class AddressMetadata implements Serializable {
	private String region,regionId,telephone,street,city,postCode,cityId,state,state_code;

	
	
	public String getState_code() {
		return state_code;
	}

	public void setState_code(String state_code) {
		this.state_code = state_code;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	
	
//	private String entityId, parentId, customerAddressId, quoteAddressId,
//			regionId, customerId, fax, region, postCode, lastName, street,
//			city, emailId, telephone, countryId, firstName, addressType,
//			prefix, middlename, suffix, company, vatId, vatIsValid,
//			vatRequestId, vatRequestDate, vatRequestSuccess;
//
//	
//	private String entityTypeId,attributeSetId,incrementedId,createdOn,updatedOn,isActive;
//	
//	
//	public String getIsActive() {
//		return isActive;
//	}
//
//	public void setIsActive(String isActive) {
//		this.isActive = isActive;
//	}
//
//	public String getCreatedOn() {
//		return createdOn;
//	}
//
//	public void setCreatedOn(String createdOn) {
//		this.createdOn = createdOn;
//	}
//
//	public String getUpdatedOn() {
//		return updatedOn;
//	}
//
//	public void setUpdatedOn(String updatedOn) {
//		this.updatedOn = updatedOn;
//	}
//
//	public String getIncrementedId() {
//		return incrementedId;
//	}
//
//	public void setIncrementedId(String incrementedId) {
//		this.incrementedId = incrementedId;
//	}
//
//	public String getAttributeSetId() {
//		return attributeSetId;
//	}
//
//	public void setAttributeSetId(String attributeSetId) {
//		this.attributeSetId = attributeSetId;
//	}
//
//	public String getEntityTypeId() {
//		return entityTypeId;
//	}
//
//	public void setEntityTypeId(String entityTypeId) {
//		this.entityTypeId = entityTypeId;
//	}
//
//	public String getEntityId() {
//		return entityId;
//	}
//
//	public void setEntityId(String entityId) {
//		this.entityId = entityId;
//	}
//
//	public String getParentId() {
//		return parentId;
//	}
//
//	public void setParentId(String parentId) {
//		this.parentId = parentId;
//	}
//
//	public String getCustomerAddressId() {
//		return customerAddressId;
//	}
//
//	public void setCustomerAddressId(String customerAddressId) {
//		this.customerAddressId = customerAddressId;
//	}
//
//	public String getQuoteAddressId() {
//		return quoteAddressId;
//	}
//
//	public void setQuoteAddressId(String quoteAddressId) {
//		this.quoteAddressId = quoteAddressId;
//	}
//
//	public String getRegionId() {
//		return regionId;
//	}
//
//	public void setRegionId(String regionId) {
//		this.regionId = regionId;
//	}
//
//	public String getCustomerId() {
//		return customerId;
//	}
//
//	public void setCustomerId(String customerId) {
//		this.customerId = customerId;
//	}
//
//	public String getFax() {
//		return fax;
//	}
//
//	public void setFax(String fax) {
//		this.fax = fax;
//	}
//
//	public String getRegion() {
//		return region;
//	}
//
//	public void setRegion(String region) {
//		this.region = region;
//	}
//
//	public String getPostCode() {
//		return postCode;
//	}
//
//	public void setPostCode(String postCode) {
//		this.postCode = postCode;
//	}
//
//	public String getLastName() {
//		return lastName;
//	}
//
//	public void setLastName(String lastName) {
//		this.lastName = lastName;
//	}
//
//	public String getStreet() {
//		return street;
//	}
//
//	public void setStreet(String street) {
//		this.street = street;
//	}
//
//	public String getCity() {
//		return city;
//	}
//
//	public void setCity(String city) {
//		this.city = city;
//	}
//
//	public String getEmailId() {
//		return emailId;
//	}
//
//	public void setEmailId(String emailId) {
//		this.emailId = emailId;
//	}
//
//	public String getTelephone() {
//		return telephone;
//	}
//
//	public void setTelephone(String telephone) {
//		this.telephone = telephone;
//	}
//
//	public String getCountryId() {
//		return countryId;
//	}
//
//	public void setCountryId(String countryId) {
//		this.countryId = countryId;
//	}
//
//	public String getFirstName() {
//		return firstName;
//	}
//
//	public void setFirstName(String firstName) {
//		this.firstName = firstName;
//	}
//
//	public String getAddressType() {
//		return addressType;
//	}
//
//	public void setAddressType(String addressType) {
//		this.addressType = addressType;
//	}
//
//	public String getPrefix() {
//		return prefix;
//	}
//
//	public void setPrefix(String prefix) {
//		this.prefix = prefix;
//	}
//
//	public String getMiddlename() {
//		return middlename;
//	}
//
//	public void setMiddlename(String middlename) {
//		this.middlename = middlename;
//	}
//
//	public String getSuffix() {
//		return suffix;
//	}
//
//	public void setSuffix(String suffix) {
//		this.suffix = suffix;
//	}
//
//	public String getCompany() {
//		return company;
//	}
//
//	public void setCompany(String company) {
//		this.company = company;
//	}
//
//	public String getVatId() {
//		return vatId;
//	}
//
//	public void setVatId(String vatId) {
//		this.vatId = vatId;
//	}
//
//	public String getVatIsValid() {
//		return vatIsValid;
//	}
//
//	public void setVatIsValid(String vatIsValid) {
//		this.vatIsValid = vatIsValid;
//	}
//
//	public String getVatRequestId() {
//		return vatRequestId;
//	}
//
//	public void setVatRequestId(String vatRequestId) {
//		this.vatRequestId = vatRequestId;
//	}
//
//	public String getVatRequestDate() {
//		return vatRequestDate;
//	}
//
//	public void setVatRequestDate(String vatRequestDate) {
//		this.vatRequestDate = vatRequestDate;
//	}
//
//	public String getVatRequestSuccess() {
//		return vatRequestSuccess;
//	}
//
//	public void setVatRequestSuccess(String vatRequestSuccess) {
//		this.vatRequestSuccess = vatRequestSuccess;
//	}
//	
//	
}
