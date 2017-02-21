package com.app2mobile.metadata;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderHistoryMetadata implements Serializable {
	// private String orderStatus, orderId, orderCreatedAt, orderUpdatedAt,
	// orderState, hpcOrderId, hpcOrderFrom, subTotal,
	// grandTotal,orderSpecialInst;
	//
	// private AddressMetadata shippingAddress,billingAddress;
	// private ArrayList<OrderItemsMetadata> orderItemsList;

	private String orderId, createTs, orderStatusId, orderStatus, orderTotal,
			 orderGrossAmt, orderDeliveryTime,
			orderInstruction, orderAddress, addressStreet, addressPincode,
			addressCity, orderState, orderStateId,order_is_fav,order_fav_name;
	
	public String getOrder_is_fav() {
		return order_is_fav;
	}
	public void setOrder_is_fav(String order_is_fav) {
		this.order_is_fav = order_is_fav;
	}
	public String getOrder_fav_name() {
		return order_fav_name;
	}
	public void setOrder_fav_name(String order_fav_name) {
		this.order_fav_name = order_fav_name;
	}
	Double orderSalesTax, orderServiceTax;
	 private ArrayList<OrderItemsMetadata> orderItemsList;
	 
	 
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getCreateTs() {
		return createTs;
	}
	public void setCreateTs(String createTs) {
		this.createTs = createTs;
	}
	public String getOrderStatusId() {
		return orderStatusId;
	}
	public void setOrderStatusId(String orderStatusId) {
		this.orderStatusId = orderStatusId;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getOrderTotal() {
		return orderTotal;
	}
	public void setOrderTotal(String orderTotal) {
		this.orderTotal = orderTotal;
	}
	public Double getOrderSalesTax() {
		return orderSalesTax;
	}
	public void setOrderSalesTax(Double orderSalesTax) {
		this.orderSalesTax = orderSalesTax;
	}
	public Double getOrderServiceTax() {
		return orderServiceTax;
	}
	public void setOrderServiceTax(Double orderServiceTax) {
		this.orderServiceTax = orderServiceTax;
	}
	public String getOrderGrossAmt() {
		return orderGrossAmt;
	}
	public void setOrderGrossAmt(String orderGrossAmt) {
		this.orderGrossAmt = orderGrossAmt;
	}
	public String getOrderDeliveryTime() {
		return orderDeliveryTime;
	}
	public void setOrderDeliveryTime(String orderDeliveryTime) {
		this.orderDeliveryTime = orderDeliveryTime;
	}
	public String getOrderInstruction() {
		return orderInstruction;
	}
	public void setOrderInstruction(String orderInstruction) {
		this.orderInstruction = orderInstruction;
	}
	public String getOrderAddress() {
		return orderAddress;
	}
	public void setOrderAddress(String orderAddress) {
		this.orderAddress = orderAddress;
	}
	public String getAddressStreet() {
		return addressStreet;
	}
	public void setAddressStreet(String addressStreet) {
		this.addressStreet = addressStreet;
	}
	public String getAddressPincode() {
		return addressPincode;
	}
	public void setAddressPincode(String addressPincode) {
		this.addressPincode = addressPincode;
	}
	public String getAddressCity() {
		return addressCity;
	}
	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}
	public String getOrderState() {
		return orderState;
	}
	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}
	public String getOrderStateId() {
		return orderStateId;
	}
	public void setOrderStateId(String orderStateId) {
		this.orderStateId = orderStateId;
	}
	public ArrayList<OrderItemsMetadata> getOrderItemsList() {
		return orderItemsList;
	}
	public void setOrderItemsList(ArrayList<OrderItemsMetadata> orderItemsList) {
		this.orderItemsList = orderItemsList;
	}
	 
	 
}
