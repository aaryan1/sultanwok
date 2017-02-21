package com.app2mobile.Sultanwok.fragment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.joda.time.LocalTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.app2mobile.acplibrary.ACProgressCustom;
import com.app2mobile.Sultanwok.Autocomplete;
import com.app2mobile.Sultanwok.CategoryActivity;
import com.app2mobile.Sultanwok.Global;
import com.app2mobile.Sultanwok.PickupnDeliveryDialog;
import com.app2mobile.Sultanwok.R;
import com.app2mobile.metadata.DeliveryTimingList;
import com.app2mobile.metadata.ManualLocation_Metadata;
import com.app2mobile.metadata.RestaurantAddOnsMetadata;
import com.app2mobile.metadata.RestaurantBundleMetadata;
import com.app2mobile.metadata.RestaurantCategoryMetadata;
import com.app2mobile.metadata.RestaurantDeliveryTimeMetadata;
import com.app2mobile.metadata.RestaurantDetailMetadata;
import com.app2mobile.metadata.RestaurantMetadata;
import com.app2mobile.metadata.RestaurantProductMetadata;
import com.app2mobile.metadata.RestaurantTaxClassMetaData;
import com.app2mobile.metadata.RestaurantVarientsMetadata;
import com.app2mobile.metadata.Restaurant_Miles_Charge;
import com.app2mobile.utiles.ConnectionDetector;
import com.app2mobile.utiles.ConstantsUrl;
import com.app2mobile.utiles.DatabaseHelper;
import com.app2mobile.utiles.JsonParser;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.special.ResideMenu.ResideMenu;
import com.squareup.picasso.Picasso;

public class Manual_Location_Fragment extends BaseFragment{
	EditText search_address;
	ArrayList<ManualLocation_Metadata> pojo;
	HashMap<String, RestaurantDeliveryTimeMetadata> deliveryTimeList;
	 ArrayList<String> TimingList;
	 HashMap<String, DeliveryTimingList> list1;
	 private Activity ownerActivity;
     private Exception exceptionToBeThrown;
Autocomplete auto;
ConnectionDetector cd;
ImageView logo,menu_img;
ListView list;
private SharedPreferences appPrefs;
private SharedPreferences.Editor prefs_editor;
RelativeLayout relativeLayout;
Drawable bitmap;
String is_open;
public static Manual_Location_Fragment newInstance() {

	return new Manual_Location_Fragment();
}
@Override
public void onCreate(@Nullable Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	
	getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	
	getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

}
@Override
public void onStart() {
	// TODO Auto-generated method stub
	super.onStart();
   

}
@Override
public void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	Global.getInstance().trackScreenView("Location Page");
}
@Override
public void onStop() {
	// TODO Auto-generated method stub
	super.onStop();
   

}
	@SuppressWarnings("deprecation")
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		  
		//Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
		mCartImg = (ImageView) view.findViewById(R.id.cartImage);
		mMenuImg = (ImageView) view.findViewById(R.id.menuImage);
		
		
		search_address = (EditText)view.findViewById(R.id.search);
		ImageView img= (ImageView)view.findViewById(R.id.imageView2);
		Drawable bit=this.getResources().getDrawable(R.drawable.search_gray1);
		bit.setColorFilter(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE), android.graphics.PorterDuff.Mode.MULTIPLY);
		img.setImageDrawable(bit);
		logo=(ImageView)view.findViewById(R.id.imageView1);
		menu_img=(ImageView)view.findViewById(R.id.cartImag);
		bitmap=this.getResources().getDrawable(R.drawable.setting);
		bitmap.setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.MULTIPLY);
		menu_img.setBackgroundDrawable(bitmap);
		  relativeLayout = (RelativeLayout) view.findViewById(R.id.rel);
		  File f = new File("/mnt/sdcard/App2food/"+ConstantsUrl.STORE_BUNDLE_ID+"backround_img.jpg");
			
			Bitmap bmp = BitmapFactory.decodeFile(f.getAbsolutePath());
			Drawable dr = new BitmapDrawable(bmp);
			
			relativeLayout.setBackgroundDrawable(dr);
			
		  menu_img.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//((BaseActivity) getActivity()).OpenDrawer();
					Log.e("sliding", "sliding menu click");
					resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
				}
			});
		  ((Global) Manual_Location_Fragment.this.getActivity()
					.getApplicationContext()).setDelivery_type("Delivery");
		pojo= new ArrayList<ManualLocation_Metadata>();
		System.out.print("aaaaa");
		((Global)getActivity().getApplicationContext()).setIs_guest_user("false");
		((Global)getActivity().getApplication()).setIs_from_payment_information_page("false");

		cd = new ConnectionDetector(getActivity());
		Boolean isInternetPresent = cd.isConnectingToInternet();
		if (!isInternetPresent) {
			Toast.makeText(getActivity(), "Internet is not Connected, Try Again", 3).show();
			return;
		}else{
		new Location().execute();
		}
		
		list=(ListView)view.findViewById(R.id.listView1);
		search_address.addTextChangedListener(new TextWatcher() {
			
			@SuppressLint("DefaultLocale") @Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				auto.getFilter().filter(s.toString().toLowerCase());
				 
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		System.out.println("Constant........."+ConstantsUrl.STOREID);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				final TextView storeid=(TextView)view.findViewById(R.id.storeid);
				cd = new ConnectionDetector(getActivity());
				Boolean isInternetPresent = cd.isConnectingToInternet();
				ConstantsUrl.STOREID=storeid.getText().toString(); 
				TextView open_close=(TextView)view.findViewById(R.id.open);
				TextView address= (TextView)view.findViewById(R.id.address);
				is_open=open_close.getText().toString();
				((Global)getActivity().getApplicationContext()).setIs_restaurant_open(is_open);
		        Global.getInstance().trackEvent("Manual location", "Click", "Track event example");
				DatabaseHelper databaseHelper = DatabaseHelper
						.newInstance(getActivity());
				databaseHelper.openDataBase();
				
				databaseHelper.clearCart();
				databaseHelper.close();
				//}
				if (!isInternetPresent) {
					Toast.makeText(getActivity(), "Internet is not Connected, Try Again", 3).show();
					return;
				}else{
					if(open_close.getText().toString().equals("0")){
						
						
						final Dialog dialog = new Dialog(getActivity(),
								R.style.CustomDialog1);
						dialog.setContentView(R.layout.dialog_restaurant_close);
						dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT,
								LayoutParams.MATCH_PARENT);
						Button cancelBtn = (Button) dialog
								.findViewById(R.id.cancel);
						Button continu= (Button)dialog.findViewById(R.id.continu);
						TextView message = (TextView) dialog
								.findViewById(R.id.message);
						message.setText("Restaurant Is Close  Now");
							cancelBtn.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									dialog.dismiss();
								}
							});
							continu.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									new GetRestaurantDetails().execute(new String[]{"store_id"},new String[]{storeid.getText().toString()});
									dialog.dismiss();
								}
							});
							dialog.show();
					}else{
						getLocationInfo(address.getText().toString());
						new GetRestaurantDetails().execute(new String[]{"store_id"},new String[]{storeid.getText().toString()});

					}
					
				}
				
				}
		});
	}
	class Location extends AsyncTask<String, Void, String> {
		String result;
		
		ACProgressCustom progressDialog;
@Override
protected void onPreExecute() {
	// TODO Auto-generated method stub
	super.onPreExecute();
//	progressDialog = new Dialog(getActivity());
//	progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//	progressDialog.setCancelable(false);
//	progressDialog.setCanceledOnTouchOutside(false);
//	progressDialog.setContentView(R.layout.custom_progress_layout);
//	progressDialog.getWindow().setBackgroundDrawable(
//			new ColorDrawable(android.graphics.Color.TRANSPARENT));
//	progressDialog.show();
	
	progressDialog = new ACProgressCustom.Builder(getActivity())
    .useImages(R.drawable.loading1, R.drawable.loading2,R.drawable.loading3, R.drawable.loading4,R.drawable.loading5,R.drawable.loading6).build();
	progressDialog.setCanceledOnTouchOutside(true);

	progressDialog.show();
}
		@SuppressWarnings("deprecation")
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			   try {
				   return JsonParser
							.Webserice_Call_URL(
									ConstantsUrl.Store_location,
									new String[] { "store_bundle_id" },
									new String[] {ConstantsUrl.STORE_BUNDLE_ID});
		         } catch (Exception e) {
		             // save exception and re-thrown it then. 
		             exceptionToBeThrown = e;
		             return e.toString();
		         }
			

		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			System.out.print("aaaaa" + result);
			progressDialog.dismiss();
			super.onPostExecute(result);
			cd = new ConnectionDetector(getActivity());
			Boolean isInternetPresent = cd.isConnectingToInternet();
			if (!isInternetPresent) {
				Toast.makeText(getActivity(), "Internet is not Connected, Try Again", 3).show();
				return;
			}else{
			try {
				
				if(result!=null){
				JSONObject mainJson = new JSONObject(result);
				if (mainJson.has("state")) {
					String state = JsonParser
							.getkeyValue_Str(mainJson, "state");
					System.out.print("aaaaa" + state);

					if (state != null && state.equals("1")) {
						if (mainJson.has("data")) {
							JSONObject dataJson = mainJson
									.getJSONObject("data");
							 StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
						        StrictMode.setThreadPolicy(policy);
							String location = dataJson.getString("locations");
							System.out.println("aa"+dataJson.getString("locations"));
							JSONArray loca = new JSONArray(location);
							String color_code= JsonParser.getkeyValue_Str(dataJson, "colorbase");
							ConstantsUrl.STORE_COLOR_CODE="#"+color_code;
							String background_image = dataJson.getString("background_image");

							String logo_image = dataJson.getString("store_logo");
							//downloadFile(background_image);
							//Bitmap bitmap1 = BitmapFactory.decodeStream((InputStream)new URL(logo_image).getContent());
							  // logo.setImageBitmap(bitmap1);
							
							   Picasso.with(getActivity()).load(logo_image).into(logo);
//						
							   logo.setVisibility(View.VISIBLE);
							   
							//   relativeLayout.setBackgroundResource(background_image);
							System.out.println("aa"+loca);
							deliveryTimeList = new HashMap<String, RestaurantDeliveryTimeMetadata>();
							  list1= new HashMap<String, DeliveryTimingList>();
							for (int i = 0; i < loca.length(); i++) {
								ManualLocation_Metadata loc = new ManualLocation_Metadata();
								
								JSONObject arr = loca.getJSONObject(i);
								System.out.println(arr.getString("city_name"));
								String storeid=arr.getString("store_id");
								loc.setCity_name(JsonParser
										.getkeyValue_Str(arr,
												"city_name"));
								loc.setStore_id(JsonParser
										.getkeyValue_Str(arr,"store_id"));
								loc.setStore_first_address(JsonParser
										.getkeyValue_Str(arr,"store_address"));
								loc.setCity_name(JsonParser
										.getkeyValue_Str(arr,"city_name"));
								loc.setState_name(JsonParser
										.getkeyValue_Str(arr,"city_name"));
//								loc.setCity_code(JsonParser
//										.getkeyValue_Str(arr,"state_name"));
								loc.setState_code(JsonParser
										.getkeyValue_Str(arr,"state_code"));
								loc.setCountry_code(JsonParser
										.getkeyValue_Str(arr,"country_code"));
								loc.setStore_pincode(JsonParser
										.getkeyValue_Str(arr,"store_pincode"));
								loc.setStore_delivery_status(JsonParser.getkeyValue_Str(arr, "delivery_status"));
								JSONArray val= arr.getJSONArray("delivery_type");
								
								String a ="";
								 for(int j=0; j<val.length();j++){
									String s=" / "; 
									 if(j==val.length()-1){
										 s="";
									 }
								 a += val.getString(j).toUpperCase()+s;
								
								loc.setDelivery_type(a);
								System.out.println(a);
								 }
								 TimingList= new ArrayList<String>();
								 JSONArray tim= arr.getJSONArray("timing");
								 for(int k=0; k<tim.length();k++){
									
									
								 TimingList.add(k, tim.getString(k));
								 }
								 Integer start_total,end_total;
								DeliveryTimingList li= new  DeliveryTimingList();
								li.setTimingList(TimingList);
								 list1.put(storeid, li);
									 String start= TimingList.get(0);
									 String end= TimingList.get(TimingList.size()-1);
									 String start1 = null,end1 = null;
									 
									 loc.setTiming(start+" to "+end);
									 if(start.contains("am")){
										 start1=start.replace(" am", "");
									 }else if(start.contains("pm")){
										 SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm a");

									       SimpleDateFormat parseFormat = new SimpleDateFormat("HH:mm");
									       try {
											Date date= date12Format.parse(start);
											start1= parseFormat.format(date);
										} catch (ParseException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									       
									 }
									 
									 if(end.contains("am")){
									end1=end.replace(" am", "");
									 }else if(end.contains("pm")){
										 SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm a");
										 SimpleDateFormat parseFormat = new SimpleDateFormat("HH:mm");
										 	try {
												Date date= date12Format.parse(end);
												end1= parseFormat.format(date);
												Log.e("end Time", end1.toString());
											} catch (ParseException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											
											
									 }
									  LocalTime now = LocalTime.now();
									  Log.e("current", now.toString());
									  LocalTime start_time = new LocalTime(start1);
										
										LocalTime end_time= new LocalTime(end1);
										Boolean isLate = now.isAfter( start_time );
									Log.e("current", now.toString());
									Log.e("startdate", start_time.toString());
									Log.e("enddate", end_time.toString());
									Log.e("enddate", isLate.toString());  
									 
										if(now.isAfter( start_time )&& now.isBefore(end_time)){
											if(loc.getStore_delivery_status().equals("0")){
											loc.setStore_delivery_status("0");
											}else{
												loc.setStore_delivery_status("1");
											}
										}else{
											loc.setStore_delivery_status("0");
										}
								pojo.add(loc);
								
								RestaurantDeliveryTimeMetadata deliveryData = new RestaurantDeliveryTimeMetadata();
								deliveryData.setStartTime(start);
								deliveryData.setEndTime(end);
								deliveryTimeList.put(storeid, deliveryData);
								((Global)getActivity().getApplicationContext()).setStartTime(start);
								((Global)getActivity().getApplicationContext()).setEndTime(end);
							}
							
						}

					}
				}
				auto = new Autocomplete(getActivity(), R.layout.location_manual_custum_list, pojo);
				list.setAdapter(auto);
				auto.notifyDataSetChanged();
				
			} 
			}catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
	             exceptionToBeThrown = e;

			}
		}
		}
	}

	
	private class GetRestaurantDetails extends AsyncTask<String[], Void, String> {
		ACProgressCustom progressDialog;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = new ACProgressCustom.Builder(getActivity())
		    .useImages(R.drawable.loading1, R.drawable.loading2,R.drawable.loading3, R.drawable.loading4,R.drawable.loading5,R.drawable.loading6).build();
			progressDialog.setCanceledOnTouchOutside(true);

			progressDialog.show();
		}
		@Override
		protected String doInBackground(String[]... params) {
			// TODO Auto-generated method stub
			return JsonParser.Webserice_Call_URL(
					ConstantsUrl.Get_All_category, params[0], params[1]);
		
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Log.e("Result", "Result"+result);
			RestaurantMetadata data = parseResponse(result);
			progressDialog.dismiss();
//			Intent intent = new Intent(MainActivity.this,
//					CategoryActivity.class);
				if(is_open.equals("1"))
				{
			Intent intent = new Intent(getActivity(),
					PickupnDeliveryDialog.class);
			intent.putExtra("restaurant", data);
			startActivity(intent);
				}else{

					Intent intent = new Intent(getActivity(),
							CategoryActivity.class);
					intent.putExtra("restaurant", data);
					startActivity(intent);

					/*
					Intent intent = new Intent(getActivity(), CategoryActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("restaurant", data);
					intent.putExtras(bundle);
					startActivity(intent);
                      */

				}
			//finish();

		}
	}

	private RestaurantMetadata parseResponse(String result) {
		RestaurantMetadata restaurantData = new RestaurantMetadata();
		try {

			JSONObject mainJson = new JSONObject(result);
			if (mainJson.has("state")) {
				String state = JsonParser.getkeyValue_Str(mainJson, "state");
				if (state != null && state.equals("1")) {
					if (mainJson.has("data")) {
						JSONObject dataJson = mainJson.getJSONObject("data");
						if(dataJson.has("img_base_link")){
							JSONObject imgjson = dataJson.getJSONObject("img_base_link");

						if (imgjson.has("baseurl")) {
							restaurantData.setmBaseUrl(JsonParser
									.getkeyValue_Str(imgjson, "baseurl"));
						}
						
						if (imgjson.has("productimgurl")) {
							restaurantData
									.setmProductImgUrl(JsonParser
											.getkeyValue_Str(imgjson,
													"productimgurl"));
						}
						if (imgjson.has("categoryimgurl")) {
							restaurantData
									.setmCategoryImgUrl(JsonParser
											.getkeyValue_Str(imgjson,
													"categoryimgurl"));
						}
						if (imgjson.has("bundleimgurl")) {
							restaurantData.setmBundleImgUrl(JsonParser
									.getkeyValue_Str(imgjson, "bundleimgurl"));
						}
						}
						if(dataJson.has("category")){
							
						}
						
						
						if (dataJson.has("restaurant")) {
							System.out.println("restaurnat.....");
							JSONObject restaurantJson = dataJson
									.getJSONObject("restaurant");
//						ConstantsUrl.LONGITUDE=	JsonParser.getkeyValue_Double(
//									restaurantJson, "store_long");
//							ConstantsUrl.LATITUDE=JsonParser.getkeyValue_Double(
//									restaurantJson, "store_lat");
								String storeId = JsonParser.getkeyValue_Str(
										restaurantJson, "store_id");
								String storeName = JsonParser.getkeyValue_Str(
										restaurantJson, "store_name");
								ConstantsUrl.STORE_NAME=storeName;
								String storeDeliveryStatus = JsonParser
										.getkeyValue_Str(restaurantJson,
												"store_delivery_status");
//								String avgDeliveryTime = JsonParser
//										.getkeyValue_Str(restaurantJson,
//												"store_avg_delivery_time");
								String storeMinDeliveryValue = JsonParser
										.getkeyValue_Str(restaurantJson,
												"store_min_delivery_value");
								String storeTermNCondition = JsonParser
										.getkeyValue_Str(restaurantJson,
												"store_terms_condition");
//								String storeCreateTs = JsonParser
//										.getkeyValue_Str(restaurantJson,
//												"create_ts");
								String storePhoneNumber = JsonParser
										.getkeyValue_Str(restaurantJson,
												"store_owner_number");
								String store_gateway=JsonParser.getkeyValue_Str(dataJson, "store_gateway");
								ArrayList<String > del= new ArrayList<String>();
								ArrayList<String> cash= new ArrayList<String>();
								JSONArray arr= restaurantJson.getJSONArray("store_delivery_type");
								JSONArray card_arr= restaurantJson.getJSONArray("store_payment_type");
								for(int i=0;i<arr.length();i++){
									del.add(arr.getString(i));
								}
								for(int k=0;k<card_arr.length();k++){
									cash.add(card_arr.getString(k));
								}
								
								((Global)getActivity().getApplication()).setCard_cash(cash);
								((Global)getActivity().getApplication()).setPicuplist(del);
//								prefs_editor.putString(ConstantsUrl.STORE_COLOR_CODE,color_code);
//								prefs_editor.commit();
								RestaurantDetailMetadata detailMetadata = new RestaurantDetailMetadata();
								detailMetadata.setmStoreId(storeId);
								detailMetadata.setmStoreName(storeName);
								detailMetadata.setTimelist(list1);
								detailMetadata
										.setmStoreDeliveryStatus(storeDeliveryStatus);
//								detailMetadata
//										.setmStoreAvgDeliveryTime(avgDeliveryTime);
								detailMetadata
										.setmStoreMinDeliveryValue(storeMinDeliveryValue);
								detailMetadata
										.setmStoreTermsNCond(storeTermNCondition);
//								detailMetadata.setmCreateTs(storeCreateTs);
								detailMetadata
										.setmStoreNumber(storePhoneNumber);
								detailMetadata
										.setmDeliveryList(deliveryTimeList);
								detailMetadata.setmStore_payment(store_gateway);
								((Global)Manual_Location_Fragment.this.getActivity().getApplication()).setIs_billing_address(JsonParser.getkeyValue_Str(
										restaurantJson, "is_billing_address"));
								restaurantData
										.setRestaurantDetail(detailMetadata);
								
								Gson gson = new Gson();
								SharedPreferences mRestaurantPrefs = getActivity().getSharedPreferences(
										ConstantsUrl.RESTAURANTDETAILPREFS, 0);
								Editor editor = mRestaurantPrefs.edit();
								editor.putString(
										ConstantsUrl.RESTAURANT_DETAILS,
										gson.toJson(detailMetadata));
								editor.commit();
							}
						
							if(dataJson.has("store_miles_charge")){
								JSONArray miles_arr= dataJson.getJSONArray("store_miles_charge");
								HashMap<Integer, Restaurant_Miles_Charge> miles_hash_map= new HashMap<Integer, Restaurant_Miles_Charge>();
								for(Integer i=0;i<miles_arr.length();i++){
									Restaurant_Miles_Charge mil= new Restaurant_Miles_Charge();
								JSONObject obj= miles_arr.getJSONObject(i);
								String store_id=obj.getString("store_id");
									String miles= obj.getString("miles");
									String charge=obj.getString("charge");
									mil.setCharge(charge);
									mil.setMiles(miles);
									miles_hash_map.put(i, mil);
								}
								((Global)getActivity().getApplicationContext()).setMil_charge(miles_hash_map);
							}
						if (dataJson.has("localities")) {
							DatabaseHelper helperobj = DatabaseHelper
									.newInstance(getActivity());
							helperobj.openDataBase();
							JSONArray localityArr = dataJson
									.getJSONArray("localities");
							
							helperobj.insertLocality(localityArr);
							helperobj.close();

						}
//						HashMap<String, RestaurantTagsMetadata> tagsHashList = new HashMap<String, RestaurantTagsMetadata>();
//						if (dataJson.has("tags")) {
//							JSONArray tagsArr = dataJson.getJSONArray("tags");
//							for (int i = 0; i < tagsArr.length(); i++) {
//								JSONObject tagJson = tagsArr.getJSONObject(i);
//								RestaurantTagsMetadata tagsData = new RestaurantTagsMetadata();
//								tagsData.setTagId(JsonParser.getkeyValue_Str(
//										tagJson, "tag_id"));
//								tagsData.setTagName(JsonParser.getkeyValue_Str(
//										tagJson, "tag_name"));
//								if (!tagsHashList.containsKey(tagsData
//										.getTagId()))
//									tagsHashList.put(tagsData.getTagId(),
//											tagsData);
//							}
//						}
//
						HashMap<String, RestaurantTaxClassMetaData> taxClassHashList = new HashMap<String, RestaurantTaxClassMetaData>();
						if (dataJson.has("taxclass")) {
							JSONArray taxClassArr = dataJson
									.getJSONArray("taxclass");
							for (int i = 0; i < taxClassArr.length(); i++) {
								JSONObject taxclassJson = (JSONObject) taxClassArr
										.get(i);
								RestaurantTaxClassMetaData taxData = new RestaurantTaxClassMetaData();
								taxData.setmTaxId(JsonParser.getkeyValue_Str(
										taxclassJson, "tax_id"));
								taxData.setmStroeId(JsonParser.getkeyValue_Str(
										taxclassJson, "store_id"));
								taxData.setmClassName(JsonParser
										.getkeyValue_Str(taxclassJson,
												"class_name"));
								taxData.setmServiceTax(JsonParser
										.getkeyValue_Str(taxclassJson,
												"service_tax"));
								taxData.setmSalesTax(JsonParser
										.getkeyValue_Str(taxclassJson,
												"sales_tax"));
								taxData.setmServiceTaxPer(JsonParser
										.getkeyValue_Str(taxclassJson,
												"service_tax_per"));
								taxData.setmSalesTaxPer(JsonParser
										.getkeyValue_Str(taxclassJson,
												"sales_tax_per"));
								taxData.setmCreateTs(JsonParser
										.getkeyValue_Str(taxclassJson,
												"create_ts"));
								if (!taxClassHashList.containsKey(taxData
										.getmTaxId())) {
									taxClassHashList.put(taxData.getmTaxId(),
											taxData);
								}

							}
						}

						HashMap<Integer, ArrayList<RestaurantProductMetadata>> productList = new HashMap<Integer, ArrayList<RestaurantProductMetadata>>();
						if (dataJson.has("product")) {
							JSONArray productArray = dataJson
									.getJSONArray("product");
							for (int i = 0; i < productArray.length(); i++) {

								JSONObject productJson = productArray
										.getJSONObject(i);

								int catId = 0;
								try {
									catId = productJson.getInt("cid");
								} catch (Exception e) {
									// e.printStackTrace();
								}
								int pId = productJson.getInt("id");
								String taxId = JsonParser.getkeyValue_Str(
										productJson, "tax_id");
								String pName = JsonParser.getkeyValue_Str(
										productJson, "product_name");
								Double pPrice = productJson
										.getDouble("product_price");
								int inStock = productJson.getInt("instock");
								String pDesc = JsonParser.getkeyValue_Str(
										productJson, "product_desc");
								String pImage = JsonParser.getkeyValue_Str(
										productJson, "product_image");
								if (pImage != null && !pImage.equals("")
										&& !pImage.equals("null")) {
									pImage = restaurantData.getmProductImgUrl()
											+ pImage;
								}
								Double service_tax_amount, sales_tax_amount;
								try {
									service_tax_amount = productJson
											.getDouble("service_tax_amount");

								} catch (Exception e) {
									// e.printStackTrace();
									service_tax_amount = 0.0d;
								}
								try {
									sales_tax_amount = productJson
											.getDouble("sales_tax_amount");

								} catch (Exception e) {
									// e.printStackTrace();
									sales_tax_amount = 0.0d;
								}
								String pSKU = JsonParser.getkeyValue_Str(
										productJson, "sku");
								ArrayList<RestaurantVarientsMetadata> varientList = new ArrayList<RestaurantVarientsMetadata>();
								if (productJson.has("varients")) {
									JSONArray varientsArr = productJson
											.getJSONArray("varients");

									for (int k = 0; k < varientsArr.length(); k++) {
										RestaurantVarientsMetadata varientData = new RestaurantVarientsMetadata();
										JSONObject varientObject = varientsArr
												.getJSONObject(k);
										String varId = JsonParser
												.getkeyValue_Str(varientObject,
														"id");
										String varTaxId = JsonParser
												.getkeyValue_Str(varientObject,
														"tax_id");
										String varName = JsonParser
												.getkeyValue_Str(varientObject,
														"product_name");
										String varPrice = JsonParser
												.getkeyValue_Str(varientObject,
														"product_price");
										String varStock = JsonParser
												.getkeyValue_Str(varientObject,
														"instock");
										String varDesc = JsonParser
												.getkeyValue_Str(varientObject,
														"product_desc");
										varientData.setId(varId);
										varientData.setTaxId(varTaxId);
										varientData.setpName(varName);
										varientData.setpPrice(varPrice);
										varientData.setpStock(varStock);
										varientData.setpDesc(varDesc);
										if (taxClassHashList
												.containsKey(varientData
														.getTaxId())
												&& taxClassHashList
														.get(varientData
																.getTaxId()) != null) {
//											varientData
//													.setSalesTaxAmt(taxClassHashList
//															.get(varientData
//																	.getTaxId())
//															.getmSalesTax());
//											varientData
//													.setServiceTaxAmt(taxClassHashList
//															.get(varientData
//																	.getTaxId())
//															.getmServiceTax());
																varientData
																.setSalesTaxAmtPercentage(taxClassHashList
																		.get(varientData.getTaxId())
																		.getmSalesTax());
														varientData
																.setServiceTaxAmtPercentage(taxClassHashList
																		.get(varientData.getTaxId())
																		.getmServiceTax());
														varientData
																.setSalesTaxAmt(calculateTaxes(
																		varientData
																				.getSalesTaxAmtPercentage(),
																				varientData.getpPrice()));
														varientData
																.setServiceTaxAmt(calculateTaxes(
																		varientData
																				.getServiceTaxAmtPercentage(),
																				varientData.getpPrice()));
										} else {
											varientData.setSalesTaxAmt("0");
											varientData.setServiceTaxAmt("0");
										}
										varientList.add(varientData);

									}
								}
//								ArrayList<RestaurantAddOnMetadata> addOnList = new ArrayList<RestaurantAddOnMetadata>();
//								if (productJson.has("addons")) {
//									JSONArray addOnArr = productJson
//											.getJSONArray("addons");
//									for (int k = 0; k < addOnArr.length(); k++) {
//										JSONArray addOnsArr = addOnArr
//												.getJSONArray(k);
//										for (int l = 0; l < addOnsArr.length(); l++) {
//											RestaurantAddOnMetadata addOnData = new RestaurantAddOnMetadata();
//											JSONObject addOnObject = addOnsArr
//													.getJSONObject(l);
//											String addId = JsonParser
//													.getkeyValue_Str(
//															addOnObject, "id");
//											String addTaxId = JsonParser
//													.getkeyValue_Str(
//															addOnObject,
//															"tax_id");
//											String addName = JsonParser
//													.getkeyValue_Str(
//															addOnObject,
//															"product_name");
//											String addPrice = JsonParser
//													.getkeyValue_Str(
//															addOnObject,
//															"product_price");
//											String addStock = JsonParser
//													.getkeyValue_Str(
//															addOnObject,
//															"instock");
//											String addPDesc = JsonParser
//													.getkeyValue_Str(
//															addOnObject,
//															"product_desc");
//											String addServiceTaxAmount = JsonParser
//													.getkeyValue_Str(
//															addOnObject,
//															"service_tax_amount");
//											String addSalesTaxAmount = JsonParser
//													.getkeyValue_Str(
//															addOnObject,
//															"sales_tax_amount");
//											String addPImage = JsonParser
//													.getkeyValue_Str(
//															addOnObject,
//															"product_image");
//											addOnData.setId(addId);
//											addOnData.setTaxId(addTaxId);
//											addOnData.setpName(addName);
//											addOnData.setpPrice(addPrice);
//											addOnData.setInStock(addStock);
//											addOnData.setpDesc(addPDesc);
//											addOnData
//													.setServiceTaxAmt(addServiceTaxAmount);
//											addOnData
//													.setSalesTaxAmt(addSalesTaxAmount);
//											addOnData.setpImage(addPImage);
//											addOnList.add(addOnData);
//										}
//									}
//								}
								ArrayList<RestaurantBundleMetadata> bundleList = new ArrayList<RestaurantBundleMetadata>();
								if (productJson.has("bundle")) {
									JSONArray bundleArr = productJson
											.getJSONArray("bundle");

									for (int k = 0; k < bundleArr.length(); k++) {
										JSONObject bundleJson = bundleArr
												.getJSONObject(k);
										RestaurantBundleMetadata bundleData = new RestaurantBundleMetadata();
										bundleData.setmGroupName(JsonParser
												.getkeyValue_Str(bundleJson,
														"group_name"));
										bundleData.setmGroupType(JsonParser
												.getkeyValue_Str(bundleJson,
														"group_type"));
										bundleData
												.setmGroupSelectType(JsonParser
														.getkeyValue_Str(
																bundleJson,
																"group_select_type"));
										String bundleImgStr = JsonParser
												.getkeyValue_Str(bundleJson,
														"group_image");
										if (bundleImgStr != null
												&& !bundleImgStr.equals("")
												&& !bundleImgStr
														.equalsIgnoreCase("null")) {

											bundleData
													.setmGroupImage(restaurantData
															.getmBundleImgUrl()
															+ bundleImgStr);
										}
										String selectType = JsonParser
												.getkeyValue_Str(bundleJson,
														"group_select_option");
										try {
											bundleData
													.setmGroupSeletOption(Integer.parseInt(selectType));
										} catch (Exception e) {
											//e.printStackTrace();
											//System.out.println("myExce"+e.getMessage()+" gname "+JsonParser.getkeyValue_Str(bundleJson, "group_name")+" op "+selectType);
										}
										String groupId = JsonParser
												.getkeyValue_Str(bundleJson,
														"group_id");

										String groupRequired = JsonParser
												.getkeyValue_Str(bundleJson,
														"group_required");
										bundleData.setmGroupId(groupId);

										if (groupRequired != null
												&& groupRequired.equals("1")) {
											bundleData
													.setSelectionRequired(true);
										} else {
											bundleData
													.setSelectionRequired(false);
										}
										ArrayList<RestaurantAddOnsMetadata> addOnsList = new ArrayList<RestaurantAddOnsMetadata>();
										if (bundleJson.has("group_addons")) {
											JSONArray addOnsArr = bundleJson
													.getJSONArray("group_addons");
											for (int l = 0; l < addOnsArr
													.length(); l++) {
												JSONObject addOnJson = addOnsArr
														.getJSONObject(l);
												RestaurantAddOnsMetadata bundleAddOns = new RestaurantAddOnsMetadata();
//												if (addonsHashList
//														.containsKey(JsonParser
//																.getkeyValue_Str(
//																		addOnJson,
//																		"addon_id"))) {
//													RestaurantAddOnsMetadata addOns = addonsHashList
//															.get(JsonParser
//																	.getkeyValue_Str(
//																			addOnJson,
//																			"addon_id"));
													// bundleAddOns =
													// addonsHashList
													// .get(JsonParser
													// .getkeyValue_Str(
													// addOnJson,
													// "addon_id"));
												//	if (addOns != null) {
												String addId = JsonParser
														.getkeyValue_Str(
																addOnJson, "addon_id");
//												String addTaxId = JsonParser
//														.getkeyValue_Str(
//																addOnJson,
//																"tax_id");
												String addName = JsonParser
														.getkeyValue_Str(
																addOnJson,
																"addon_name");
												String addPrice = JsonParser
														.getkeyValue_Str(
																addOnJson,
																"addon_price");
//												String addStock = JsonParser
//														.getkeyValue_Str(
//																addOnJson,
//																"instock");
//												String addPDesc = JsonParser
//														.getkeyValue_Str(
//																addOnJson,
//																"product_desc");
												String addServiceTaxAmount = JsonParser
														.getkeyValue_Str(
																addOnJson,
																"addon_service_tax_amt");
												String addSalesTaxAmount = JsonParser
														.getkeyValue_Str(
																addOnJson,
																"addon_sales_tax_amt");
//												String addPImage = JsonParser
//														.getkeyValue_Str(
//																addOnJson,
//																"product_image");
														bundleAddOns
																.setId(addId);
																		
														bundleAddOns.setaName(addName);
//														bundleAddOns
//																.setaDesc(addOnJson.getString("addon_name"));
														bundleAddOns.setaPrice(addPrice);
//														bundleAddOns
//																.setTaxId(addOnJson.getString("addon_price") 	`);
//														bundleAddOns
//																.setaImg(addOnJson.getString("addon_price"));
//														bundleAddOns
//																.setInStock(addOns
//																		.getInStock());
														bundleAddOns
																.setSalesTaxAmt(addSalesTaxAmount);
														bundleAddOns
																.setServiceTaxAmt(addServiceTaxAmount);

												//	}
													bundleAddOns
															.setmGroupId(bundleData
																	.getmGroupId());
											//	}
												//
												addOnsList.add(bundleAddOns);

											}
										} else if (bundleJson
												.has("group_options")) {
											JSONArray groupOptionsArr = bundleJson
													.getJSONArray("group_options");
											// bundleAddOns.setaName(aName)
											for (int l = 0; l < groupOptionsArr
													.length(); l++) {
												JSONObject addOnJson = groupOptionsArr
														.getJSONObject(l);
												RestaurantAddOnsMetadata bundleAddOns = new RestaurantAddOnsMetadata();
												bundleAddOns
														.setaName(JsonParser
																.getkeyValue_Str(
																		addOnJson,
																		"product_name"));
												bundleAddOns
														.setaDesc(JsonParser
																.getkeyValue_Str(
																		addOnJson,
																		"product_desc"));
												bundleAddOns.setId(bundleAddOns
														.getaName());
												bundleAddOns
														.setSalesTaxAmt("0");
												bundleAddOns
														.setServiceTaxAmt("0");
												bundleAddOns.setaPrice("0");
												bundleAddOns
														.setmGroupId(bundleData
																.getmGroupId());
												addOnsList.add(bundleAddOns);
											}
										}
										bundleData
												.setBundleAddOnsList(addOnsList);
										bundleList.add(bundleData);
									}

								}

								RestaurantProductMetadata productData = new RestaurantProductMetadata();
								productData.setCatId(catId);
								productData.setpId(pId);
								productData.setpTaxId(taxId);
								productData.setpName(pName);
								productData.setpPrice(pPrice);
								productData.setpQuant(inStock);
								productData.setpDesc(pDesc);
								productData.setpImage(pImage);
							//	productData.setAddOnList(addOnList);
								productData.setVarientsList(varientList);
								productData.setpSKU(pSKU);
								productData.setSalesTaxAmt(sales_tax_amount);
								productData.setBundleList(bundleList);
								productData
										.setServiceTaxAmt(service_tax_amount);
								if (productList.containsKey(catId)) {
									productList.get(catId).add(productData);
								} else {
									ArrayList<RestaurantProductMetadata> product = new ArrayList<RestaurantProductMetadata>();
									product.add(productData);
									productList.put(catId, product);
								}

							}

						}

						HashMap<Integer, ArrayList<RestaurantCategoryMetadata>> subCategoryList = new HashMap<Integer, ArrayList<RestaurantCategoryMetadata>>();
						if (dataJson.has("category")) {
							JSONArray subCatArray = dataJson
									.getJSONArray("category");
							for (int l = 0; l < subCatArray.length(); l++) {
								JSONObject suCatJson = subCatArray
										.getJSONObject(l);
								int catId = suCatJson
										.getInt("store_category_id");
								String catName = JsonParser.getkeyValue_Str(
										suCatJson, "category_name");
								int parentId = suCatJson.getInt("parent_id");

									System.out.println("subcat1" + catName);
									RestaurantCategoryMetadata cData = new RestaurantCategoryMetadata();
									cData.setCategoryId(catId);
									cData.setCategoryName(catName);
									cData.setCatImg("https://www.app2mobile.com/ci_app2dine/media/uploads/category/"
											+ JsonParser.getkeyValue_Str(suCatJson,
											"category_image"));
									cData.setProductId(parentId);
									cData.setProductList(productList.get(catId));

									if (subCategoryList.containsKey(cData
											.getParentId())) {
										subCategoryList.get(cData.getParentId())
												.add(cData);
									} else {
										ArrayList<RestaurantCategoryMetadata> subCategory = new ArrayList<RestaurantCategoryMetadata>();
										subCategory.add(cData);
										subCategoryList.put(cData.getParentId(),
												subCategory);
									}


									//catList.add(cData);

							}

						}
						if (dataJson.has("category")) {
							ArrayList<RestaurantCategoryMetadata> catList = new ArrayList<RestaurantCategoryMetadata>();
							JSONArray catArray = dataJson
									.getJSONArray("category");
							for (int i = 0; i < catArray.length(); i++) {
								JSONObject categoryJson = catArray
										.getJSONObject(i);
								int catId = categoryJson
										.getInt("store_category_id");
								String catName = JsonParser.getkeyValue_Str(
										categoryJson, "category_name");
								int parentId = categoryJson.getInt("parent_id");
								if(parentId==0)
								{
								RestaurantCategoryMetadata cData = new RestaurantCategoryMetadata();
								cData.setCategoryId(catId);
								cData.setCategoryName(catName);
							//	System.out.println("......"+cData.getCategoryName())
								cData.setProductId(parentId);
//								cData.setCatImg(restaurantData
//										.getmCategoryImgUrl()
//										+ JsonParser.getkeyValue_Str(
//												categoryJson, "category_image"));
								cData.setCatImg(restaurantData
										.getmCategoryImgUrl()+ JsonParser.getkeyValue_Str(
												categoryJson, "category_image"));

								cData.setProductList(productList.get(catId));
								cData.setSubCategoryList(subCategoryList.get(catId));
									ArrayList<RestaurantCategoryMetadata> subCategoryList1=subCategoryList.get(catId);
								catList.add(cData);

									//System.out.println("subcat"+ parentId+"    "+catName);
								}else
								{
									System.out.println("subcat"+ parentId+"    "+catName);
									RestaurantCategoryMetadata cData = new RestaurantCategoryMetadata();
									cData.setCategoryId(catId);
									cData.setCategoryName(catName);
									cData.setCatImg("https://www.app2mobile.com/ci_app2dine/media/uploads/category/"
											+ JsonParser.getkeyValue_Str(categoryJson,
											"category_image"));
									cData.setProductId(parentId);
									cData.setProductList(productList.get(catId));


										 if (subCategoryList.containsKey(cData.getParentId()))
										 {
											 if(!subCategoryList.containsKey(cData.getParentId())) {
												 subCategoryList.get(cData.getParentId()).add(cData);
											 }
										 } else
										 {
											 ArrayList<RestaurantCategoryMetadata> subCategory = new ArrayList<RestaurantCategoryMetadata>();
											 subCategory.add(cData);
											 subCategoryList.put(cData.getParentId(), subCategory);

										 }
									          // catList.add(cData);  it was adding subcategory to  parent activity list (wrong code)

								}

							}

							restaurantData.setCategoryList(catList);
						}
						
						/*********************************************/
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return restaurantData;
	} 
	private String calculateTaxes(String percentage, String amountStr) {
		double amount = 0d;
		double percent = 0d, taxAmt = 0.0d;
		try {
			amount = Double.parseDouble(amountStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			percent = Double.parseDouble(percentage);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			taxAmt = (percent * amount) / 100;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(taxAmt);
	}
	@Override
	public int getLayout() {
		// TODO Auto-generated method stub
		return R.layout.location_manual;
	}
	public void getLocationFromAddress(Context context,String strAddress) {

	    Geocoder coder = new Geocoder(context);
	    List<Address> address;
	    LatLng p1 = null;

	    try {
	        address = coder.getFromLocationName("59 Halsey St, Newark, NJ 07102, USA", 5);
	        if (address == null) {
	        }
	        Address location = address.get(0);
	       ConstantsUrl.LATITUDE= location.getLatitude();
	        ConstantsUrl.LONGITUDE=location.getLongitude();
                Toast.makeText(getActivity(), ""+location.getLatitude()+location.getLongitude(), 4).show();
	        

	    } catch (Exception ex) {

	        ex.printStackTrace();
	    }
	    	    
	}
	public static JSONObject getLocationInfo(String address) {
        StringBuilder stringBuilder = new StringBuilder();
        try {

        address = address.replaceAll(" ","%20");    

        HttpPost httppost = new HttpPost("http://maps.google.com/maps/api/geocode/json?address=" + address + "&sensor=false");
        HttpClient client = new DefaultHttpClient();
        HttpResponse response;
        stringBuilder = new StringBuilder();
        	response = client.execute(httppost);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            int b;
            while ((b = stream.read()) != -1) {
                stringBuilder.append((char) b);
            }
        } catch (ClientProtocolException e) {
        } catch (IOException e) {
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringBuilder.toString());
            getLatLong(jsonObject);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonObject;
    }

	public static boolean getLatLong(JSONObject jsonObject) {

        try {

            ConstantsUrl.LONGITUDE = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                .getJSONObject("geometry").getJSONObject("location")
                .getDouble("lng");

            ConstantsUrl.LATITUDE = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                .getJSONObject("geometry").getJSONObject("location")
                .getDouble("lat");

        } catch (JSONException e) {
            return false;

        }

        return true;
    }
}
