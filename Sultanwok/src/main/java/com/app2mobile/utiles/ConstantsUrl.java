package com.app2mobile.utiles;


public class ConstantsUrl {
	
	public final static int DURATION = 2000;
	public final static float DEPTH = 400.0f;
	public final static String APP_PREF = "RestaurantPref";

	public static final String IS_LOGIN = "Is_Login";
	public static final String CUSTOMER_ID = "cust_id";
	public static final String FIRST_NAME = "first_name";
	public static final String EMAIL = "email";
	public static final String LAST_NAME = "last_name";
		public static final String PHONE = "phone";
		public static String STORE_ID = "store_id";
	public static final String BILLING_ADDRESS = "billingAddress";
	public static final String SHIPPING_ADDRESS = "shippingAddress";
	public static final String IS_ALARM_START = "alaramStatus";
	
	public final static String RESTAURANTDETAILPREFS = "RestaurantDetailPrefs";
	//public static final String API_KEY="46efsgfdgftvhbkjepkd";
	public static final String API_KEY="46efsgfdgftvhbkjepkd";

	public static String STOREID = "";
	public static String STORE_BUNDLE_ID = "1036";

	public static String STORE_NAME="";
	public static String STORE_COLOR_CODE="#000000";
	public static final String PUSHNOTIFICATION_PROJECTID="688459034734";
	public static final String PHONE_NUMBER="7322382352";
	public static final String RESTAURANT_DETAILS = "restaurantDetail";
//	public static final String Get_All_category = "https://www.app2mobile.com/ci_app2dine/api2/api/getProductAndCategory/store_id/"
//			+ STOREID;
	public static final String live="https://api.app2food.com/";
	public static final String stagin_url="https://stg.app2food.com/v_backend/api3/";
	public static final String Stagin= "https://stg.app2food.com/v_backend/api3/postCheckout";
	public static final String Get_All_category = live+"getProductAndCategory/"
			+ STOREID;
	public static final String SINGUP_LOGIN = live+"postUserDetails/";
			
	public static final String VERIFY_USER = "https://www.app2mobile.com/ci_app2dine/api2/api/postUserVerify/store_id/"
			+ STOREID;
	public static final String CHANGE_PASSWORD = "https://www.app2mobile.com/ci_app2dine/api2/api/postUserPassword/store_id/"
			+ STOREID;
	public static final String FORGOT_PASSWORD = live+"postForgotPassword?";
//	public static final String CHECKOUT = "https://www.app2mobile.com/ci_app2dine/api2/api/postCheckout/store_id/"
//			+ STOREID;
	public static final String Store_location = live+"getAllLocations";
	public static final String CHECKOUT = live+"postCheckout";
	public static final String APPLYCOUPONCODE = live+"applydiscount";
	public static final String ORDERHISTOREY = live+"getCustomerOrderDetails/"
			+ STOREID;
	public static final String GETOFFER=live+"getoffers";
	//public static final String GETSTATES = "https://www.app2mobile.com/ci_app2dine/api2/api/getState";
	public static final String GETSTATES = live+"getState";
	public static final String GETSTORESLOCALITIES=live+"getStoreLocalities";
	public static final String GETCITIES = live+"getCities";
	public static final String GETFAVOURITE=live+"getCustomerFav";
	public static final String ADDFAVORITE=live+"postAddCustomerFav";
	public static final String REMOVEFAVORITE="https://www.app2mobile.com/ci_app2dine/api3/removeCustomerFav";
	public static final String facebook_url="https://api.app2food.com/facebook/?scheme=";
	public static final String GETUSERBYID=live+"getUserById";
	public static final String GETCREDITCARDDETAILS=live+"getSavedUserCards";
	public static final String GETUSERADDRESS=live+"getUserShipping";
	public static final String ADDCARD=live+"postUserCreditCard";
	public static final String SAVING_USER_INFO=live+"updateUserInfo";
	public static final String SAVE_ADDRESS=live+"updateUserShipping";
	public static final String SAVE_NEW_ADDRESS=live+"addUserShipping";
	public static final String GET_DISTANCE=live+"getDistanceArea";
	public static final String SAVE_FAV_ORDER=live+"addOrderToFav";
	public static  double LATITUDE = 40.446812;
	public static  double LONGITUDE =-74.397380;

	public static final int PROFILE = 1001;
	public static final int FAVORITE = 1002;
	public static final int LOCATION = 1003;
	public static final int ORDERHISTORY = 1004;
	public static final int LOGIN=1005;
	public static final int CHECKOUTASGUEST = 1006;
	public static final int CHECKOUT_ = 1007;
	

}
