package com.app2mobile.Sultanwok;

import java.util.ArrayList;
import java.util.HashMap;

import com.app2mobile.metadata.Restaurant_Miles_Charge;
import com.app2mobile.utiles.AnalyticsTrackers;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.StandardExceptionParser;
import com.google.android.gms.analytics.Tracker;

import android.support.multidex.MultiDexApplication;

public class Global extends MultiDexApplication {
	 private static final String PROPERTY_ID = "UA-83148524-2";
	
	     public static int GENERAL_TRACKER = 0;

public String reg_id,delvery_time,pickup_time,delivery_type,cust_id,special_instruction,total_amt,card_type,card_number,month,year,cvv,product_cart,cityId,totalamout_without_text,sales_tax;
public String startTime,endTime,select_credit_card_no,select_credit_card_exp,select_credit_card_cvv,card_token="";
public String address1,city,state,pincode,select_city_id,select_state_id;
public String delivery_street,delivery_city,delivery_state,delivery_state_code,delivery_city_code,delivery_pincode;
public String is_guest_user,delivery_charge,favroite_order_id, is_from_payment_information_page;
private String is_billing_address,is_restaurant_open;

ArrayList<String> picuplist;
ArrayList<String> card_cash;
private static Global mInstance;

public ArrayList<String> getCard_cash() {
	return card_cash;
}

public enum TrackerName {

        APP_TRACKER, GLOBAL_TRACKER, ECOMMERCE_TRACKER,

    }

@Override
public void onCreate() {
    super.onCreate();
    mInstance = this;

    AnalyticsTrackers.initialize(this);
    AnalyticsTrackers.getInstance().get(AnalyticsTrackers.Target.APP);
}

    public String getIs_restaurant_open() {
	return is_restaurant_open;
}

public void setIs_restaurant_open(String is_restaurant_open) {
	this.is_restaurant_open = is_restaurant_open;
}

	public HashMap mTrackers = new HashMap();

 

    public static synchronized Global getInstance() {
        return mInstance;
    }

    public synchronized Tracker getGoogleAnalyticsTracker() {
        AnalyticsTrackers analyticsTrackers = AnalyticsTrackers.getInstance();
        return analyticsTrackers.get(AnalyticsTrackers.Target.APP);
    }
 

    public void trackScreenView(String screenName) {
        Tracker t = getGoogleAnalyticsTracker();
 
        // Set screen name.
        t.setScreenName(screenName);
 
        // Send a screen view.
        t.send(new HitBuilders.ScreenViewBuilder().build());
 
        GoogleAnalytics.getInstance(this).dispatchLocalHits();
    }
    
    public void trackException(Exception e) {
        if (e != null) {
            Tracker t = getGoogleAnalyticsTracker();
 
            t.send(new HitBuilders.ExceptionBuilder()
                            .setDescription(
                                    new StandardExceptionParser(this, null)
                                            .getDescription(Thread.currentThread().getName(), e))
                            .setFatal(false)
                            .build()
            );
        }
    }
    public void trackEvent(String category, String action, String label) {
        Tracker t = getGoogleAnalyticsTracker();
 
        // Build and send an Event.
        t.send(new HitBuilders.EventBuilder().setCategory(category).setAction(action).setLabel(label).build());
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

public String getIs_billing_address() {
	return is_billing_address;
}

public void setIs_billing_address(String is_billing_address) {
	this.is_billing_address = is_billing_address;
}
public String getIs_from_payment_information_page() {
	return is_from_payment_information_page;
}

public void setIs_from_payment_information_page(
		String is_from_payment_information_page) {
	this.is_from_payment_information_page = is_from_payment_information_page;
}

public String getFavroite_order_id() {
	return favroite_order_id;
}

public void setFavroite_order_id(String favroite_order_id) {
	this.favroite_order_id = favroite_order_id;
}

public String getDelivery_charge() {
	return delivery_charge;
}

public void setDelivery_charge(String delivery_charge) {
	this.delivery_charge = delivery_charge;
}

public HashMap<Integer, Restaurant_Miles_Charge> mil_charge;



public HashMap<Integer, Restaurant_Miles_Charge> getMil_charge() {
	return mil_charge;
}

public void setMil_charge(HashMap<Integer, Restaurant_Miles_Charge> mil_charge) {
	this.mil_charge = mil_charge;
}

public String getIs_guest_user() {
	return is_guest_user;
}

public void setIs_guest_user(String is_guest_user) {
	this.is_guest_user = is_guest_user;
}

public String getDelivery_street() {
	return delivery_street;
}

public void setDelivery_street(String delivery_street) {
	this.delivery_street = delivery_street;
}

public String getDelivery_city() {
	return delivery_city;
}

public void setDelivery_city(String delivery_city) {
	this.delivery_city = delivery_city;
}

public String getDelivery_state() {
	return delivery_state;
}

public void setDelivery_state(String delivery_state) {
	this.delivery_state = delivery_state;
}

public String getDelivery_state_code() {
	return delivery_state_code;
}

public void setDelivery_state_code(String delivery_state_code) {
	this.delivery_state_code = delivery_state_code;
}

public String getDelivery_city_code() {
	return delivery_city_code;
}

public void setDelivery_city_code(String delivery_city_code) {
	this.delivery_city_code = delivery_city_code;
}

public String getDelivery_pincode() {
	return delivery_pincode;
}

public void setDelivery_pincode(String delivery_pincode) {
	this.delivery_pincode = delivery_pincode;
}

public String getSelect_city_id() {
	return select_city_id;
}

public void setSelect_city_id(String select_city_id) {
	this.select_city_id = select_city_id;
}

public String getSelect_state_id() {
	return select_state_id;
}

public void setSelect_state_id(String select_state_id) {
	this.select_state_id = select_state_id;
}

public String getCard_token() {
	return card_token;
}

public String getAddress1() {
	return address1;
}

public void setAddress1(String address1) {
	this.address1 = address1;
}

public String getCity() {
	return city;
}

public void setCity(String city) {
	this.city = city;
}

public String getState() {
	return state;
}

public void setState(String state) {
	this.state = state;
}

public String getPincode() {
	return pincode;
}

public void setPincode(String pincode) {
	this.pincode = pincode;
}

public void setCard_token(String card_token) {
	this.card_token = card_token;
}

public String getSales_tax() {
	return sales_tax;
}

public void setSales_tax(String sales_tax) {
	this.sales_tax = sales_tax;
}

public String getSelect_credit_card_no() {
	return select_credit_card_no;
}

public void setSelect_credit_card_no(String select_credit_card_no) {
	this.select_credit_card_no = select_credit_card_no;
}

public String getSelect_credit_card_exp() {
	return select_credit_card_exp;
}

public void setSelect_credit_card_exp(String select_credit_card_exp) {
	this.select_credit_card_exp = select_credit_card_exp;
}

public String getSelect_credit_card_cvv() {
	return select_credit_card_cvv;
}

public void setSelect_credit_card_cvv(String select_credit_card_cvv) {
	this.select_credit_card_cvv = select_credit_card_cvv;
}

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

public String getTotalamout_without_text() {
	return totalamout_without_text;
}

public void setTotalamout_without_text(String totalamout_without_text) {
	this.totalamout_without_text = totalamout_without_text;
}

public int delivery_time_position;
public String getCityId() {
	return cityId;
}

public int getDelivery_time_position() {
	return delivery_time_position;
}

public void setDelivery_time_position(int delivery_time_position) {
	this.delivery_time_position = delivery_time_position;
}

public void setCityId(String cityId) {
	this.cityId = cityId;
}

public String getProduct_cart() {
	return product_cart;
}

public void setProduct_cart(String product_cart) {
	this.product_cart = product_cart;
}

public String getCard_type() {
	return card_type;
}

public void setCard_type(String card_type) {
	this.card_type = card_type;
}

public String getCard_number() {
	return card_number;
}

public void setCard_number(String card_number) {
	this.card_number = card_number;
}

public String getMonth() {
	return month;
}

public void setMonth(String month) {
	this.month = month;
}

public String getYear() {
	return year;
}

public void setYear(String year) {
	this.year = year;
}

public String getCvv() {
	return cvv;
}

public void setCvv(String cvv) {
	this.cvv = cvv;
}

public String getTotal_amt() {
	return total_amt;
}

public void setTotal_amt(String total_amt) {
	this.total_amt = total_amt;
}

public String getSpecial_instruction() {
	return special_instruction;
}

public void setSpecial_instruction(String special_instruction) {
	this.special_instruction = special_instruction;
}

public String getCust_id() {
	return cust_id;
}

public void setCust_id(String cust_id) {
	this.cust_id = cust_id;
}

public String getDelivery_type() {
	return delivery_type;
}

public void setDelivery_type(String delivery_type) {
	this.delivery_type = delivery_type;
}

public String getReg_id() {
	return reg_id;
}

public void setReg_id(String reg_id) {
	this.reg_id = reg_id;
}

public String getDelvery_time() {
	return delvery_time;
}

public void setDelvery_time(String delvery_time) {
	this.delvery_time = delvery_time;
}

public String getPickup_time() {
	return pickup_time;
}

public void setPickup_time(String pickup_time) {
	this.pickup_time = pickup_time;
}

}
