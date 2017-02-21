package com.app2mobile.Sultanwok.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app2mobile.acplibrary.ACProgressCustom;
import com.app2mobile.Sultanwok.Global;
import com.app2mobile.Sultanwok.Payment_InformationActivity;
import com.app2mobile.Sultanwok.R;
import com.app2mobile.metadata.AddressMetadata;
import com.app2mobile.metadata.RestaurantCityMetadata;
import com.app2mobile.metadata.RestaurantStateMetadata;
import com.app2mobile.utiles.AppUtiles;
import com.app2mobile.utiles.ConstantsUrl;
import com.app2mobile.utiles.DatabaseHelper;
import com.app2mobile.utiles.EmailPatternMatcher;
import com.app2mobile.utiles.JsonParser;

import com.google.gson.Gson;

public class DetailFragment extends BaseFragment {
	public static DetailFragment detail;
	TextView name, email, phoneno, bill_street, bill_zip, ship_street,
			ship_zip;
	RelativeLayout footer;
	private boolean isBilling;
	private String address[] = new String[4];
	private String billingAddres[] = new String[4];
	AutoCompleteTextView bill_city, bill_state, ship_city, ship_state;
	private ArrayList<RestaurantStateMetadata> data = new ArrayList<RestaurantStateMetadata>();
	public StatesAdapter stateAdapteObj;
	private CityAdapter cityAdapterObj;
	String stateStr,stateCode, cityStr, zipCodeStr, cityCode = "",
			localityId = "", storeDeliverableId = "", locaionName = "";
	private AddressMetadata shippingAddress, billingAddress;
	private String shippingStateStr = "", shippingCityStr = "",
			shippingStreetStr = "", shippingZipCodeStr = "",
			shippingPhone = "",shippingCityCodeStr = "",shippingStateCodeStr = "",shippingStoreDeliverableLocation="";
	private ArrayList<RestaurantStateMetadata> filterStateList = new ArrayList<RestaurantStateMetadata>();
	private ArrayList<RestaurantCityMetadata> cityList = new ArrayList<RestaurantCityMetadata>();
	private HashMap<String, RestaurantCityMetadata> cityNamesList = new HashMap<String, RestaurantCityMetadata>();
	private ArrayList<RestaurantCityMetadata> filterCityList = new ArrayList<RestaurantCityMetadata>();
	CoordinatorLayout cor_layout;
	private LinearLayout delivery_layout;
	public static DetailFragment newstance() {
		if (detail == null) {
			detail = new DetailFragment();
		}
		return detail;

	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle intent = getArguments();
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

		if (intent != null) {
			isBilling = intent.getBoolean("test");
			address = intent.getStringArray("shippingAddress");
			if (intent.containsKey("billingAddress")) {
				billingAddres = intent.getStringArray("billingAddress");
			}

		}

	}

	@Override
	public int getLayout() {
		// TODO Auto-generated method stub
		return R.layout.fragment_detail;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		mTitleTxt.setText("User Details");
		name = (TextView) view.findViewById(R.id.name);
		email = (TextView) view.findViewById(R.id.email);
		phoneno = (TextView) view.findViewById(R.id.phoneno);
		//bill_street = (TextView) view.findViewById(R.id.street);
	//	bill_city = (AutoCompleteTextView) view.findViewById(R.id.city);
		phoneno.setInputType(Configuration.KEYBOARD_12KEY);
		//bill_zip = (TextView) view.findViewById(R.id.zip);
		Gson gson= new Gson();
		String shippingJson = mAppPrefs.getString(
				ConstantsUrl.SHIPPING_ADDRESS, "");
		String billingJson = mAppPrefs.getString(ConstantsUrl.BILLING_ADDRESS,
				"");
		shippingAddress = gson.fromJson(shippingJson, AddressMetadata.class);
		billingAddress = gson.fromJson(billingJson, AddressMetadata.class);
		delivery_layout=(LinearLayout)view.findViewById(R.id.delivery_address_layout);
		ship_street = (TextView) view.findViewById(R.id.shipping_street);
		ship_city = (AutoCompleteTextView) view
				.findViewById(R.id.shipping_city);
		ship_state = (AutoCompleteTextView) view
				.findViewById(R.id.shipping_state);
		ship_zip = (TextView) view.findViewById(R.id.shipping_zip);
		ship_zip.setRawInputType(Configuration.KEYBOARD_12KEY);
		footer = (RelativeLayout) view.findViewById(R.id.footer);
		cor_layout=(CoordinatorLayout)view.findViewById(R.id.coordinator);
		footer.setBackgroundColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));
		 File f = new File("/mnt/sdcard/App2food/"+ConstantsUrl.STORE_BUNDLE_ID+"backround_img.jpg");
			
			Bitmap bmp = BitmapFactory.decodeFile(f.getAbsolutePath());
			Drawable dr = new BitmapDrawable(bmp);
			
			cor_layout.setBackgroundDrawable(dr);
		if (mAppPrefs.getBoolean(ConstantsUrl.IS_LOGIN, false)) {
		String emailStr = mAppPrefs.getString(ConstantsUrl.EMAIL, "");
		String nameStr=mAppPrefs.getString(ConstantsUrl.FIRST_NAME, "");
		String phonenoStr=mAppPrefs.getString(ConstantsUrl.PHONE, "");
		
		if (shippingAddress != null) {
			if (shippingAddress.getTelephone() != null)
				//phoneEdt.setText(shippingAddress.getTelephone());
			if (shippingAddress.getRegion() != null)
				shippingStateStr = shippingAddress.getRegion();
			shippingCityStr = shippingAddress.getCity();
			shippingCityCodeStr = shippingAddress.getCityId();
			shippingStreetStr = shippingAddress.getStreet();
			shippingStateCodeStr = shippingAddress.getRegionId();
			shippingZipCodeStr = shippingAddress.getPostCode();
			shippingPhone = shippingAddress.getTelephone();
			if (shippingStreetStr != null
					&& !shippingStreetStr.equals("null")
					&& !shippingStreetStr.equals("")
					&& shippingCityStr != null
					&& !shippingCityStr.equals("null")
					&& !shippingCityStr.equals("")
					&& shippingStateStr != null
					&& !shippingStateStr.equals("null")
					&& !shippingStateStr.equals(""))
				
			ship_street.setText(shippingStreetStr);
			ship_city.setText(shippingCityStr);
			ship_state.setText(shippingStateStr);
			ship_zip.setText(shippingZipCodeStr);
			if (shippingCityCodeStr != null
					&& !shippingCityCodeStr.equals("")
					&& !shippingCityCodeStr.equalsIgnoreCase(("null"))) {
				getStoreDelivereableId(shippingCityCodeStr);
			

		}
		}
		}
		name.setText(mAppPrefs.getString(ConstantsUrl.FIRST_NAME, ""));
		email.setText(mAppPrefs.getString(ConstantsUrl.EMAIL, ""));
		phoneno.setText(mAppPrefs.getString(ConstantsUrl.PHONE, ""));
		stateAdapteObj = new StatesAdapter();
		//bill_state.setThreshold(1);
		//bill_state.setAdapter(stateadapter);
//		Snackbar.make(getView(), "Snackbar", Snackbar.LENGTH_LONG).show();
		ship_state.setThreshold(1);
		ship_state.setAdapter(stateAdapteObj);
		cityAdapterObj = new CityAdapter();
		ship_city.setThreshold(1);
		ship_city.setAdapter(cityAdapterObj);
		new Allstate().execute(new String[] { "country_code" },
				new String[] { "US" });
		//new GetCitiesAsy(1).execute("VA");
		footer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				checkValues();
				
			}
		});
//		bill_state.setOnEditorActionListener(new OnEditorActionListener() {
//
//			@Override
//			public boolean onEditorAction(TextView v, int actionId,
//					KeyEvent event) {
//				// TODO Auto-generated method stub
//
//				if (actionId == EditorInfo.IME_ACTION_DONE) {
//					stateStr = bill_state.getText().toString();
//					cityStr = "";
//					cityCode = "";
//					localityId = "";
//					storeDeliverableId = "";
//					locaionName = "";
//					// System.out.println("state   " + stateStr);
//					findStateCodeStr(stateStr);
//					return true;
//				}
//				
//				return false;
//			}
//		});
		ship_state.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				stateStr = data.get(position).getState_name();
				stateCode = data.get(position).getState_code();
				ship_state.setText(stateStr);
				ship_city.setText("");
				new GetCitiesAsy(0).execute(new String[] { "country_code","state_code" },
						new String[] { "US",stateCode });
			
			}
		});
		
		ship_city.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				RestaurantCityMetadata cityData = cityList.get(position);
				cityStr = cityData.getCityName();
				cityCode = cityData.getCityId();
				ship_city.setText(cityStr);
				localityId = "";
				storeDeliverableId = "";
				locaionName = "";
				((Global)DetailFragment.this.getActivity().getApplicationContext()).setCityId(cityCode);
				// localityTxt.setText("");
				// if (!isBilling)
				// getAllLocality(stateCode, cityCode);
			}
		});
	}
	private void checkValues() {
		String firstNameStr = name.getText().toString();
		String emailStr = email.getText().toString();
		String phoneStr = phoneno.getText().toString();

		 if (firstNameStr == null || firstNameStr.equals("")) {
//		  AppUtiles.getInstance().showToast(getActivity(),
//		  getString(R.string.firstname_required));
	//		Snackbar.make(getView(), getString(R.string.firstname_required), Snackbar.LENGTH_LONG).show();
			AppUtiles.getInstance().showToast(getView(), getString(R.string.firstname_required));
		 } else if (emailStr == null || emailStr.equals("")) {
//		  AppUtiles.getInstance().showToast(getActivity(),
//		  getString(R.string.email_required));
				//Snackbar.make(getView(), getString(R.string.email_required), Snackbar.LENGTH_LONG).show();
				AppUtiles.getInstance().showToast(getView(), getString(R.string.email_required));

		 } else if
		 (!EmailPatternMatcher.EMAIL_ADDRESS_PATTERN.matcher(emailStr)
		 .matches()) {
//		  AppUtiles.getInstance().showToast(getActivity(),
//		  getString(R.string.emailFormat_required));
				//Snackbar.make(getView(), getString(R.string.emailFormat_required), Snackbar.LENGTH_LONG).show();
				AppUtiles.getInstance().showToast(getView(), getString(R.string.emailFormat_required));
		 } else if (phoneStr == null || phoneStr.equals("")) {
				//Snackbar.make(getView(), getString(R.string.phone_required), Snackbar.LENGTH_LONG).show();
				AppUtiles.getInstance().showToast(getView(),  getString(R.string.phone_required));

		 }
		  else if (ship_street.getText()==""|| ship_city.getText().toString()==""|| ship_state.getText().toString()==""|| ship_zip.getText()=="")
					  {
				//Snackbar.make(getView(), getString(R.string.shipping_required), Snackbar.LENGTH_LONG).show();
				AppUtiles.getInstance().showToast(getView(), getString(R.string.shipping_required));

					 }
		  else if(ship_zip.getText().toString().equals("") ||ship_zip.getText().length()==0){
			//	Snackbar.make(getView(), "Please Enter Zipcode", Snackbar.LENGTH_LONG).show();
				AppUtiles.getInstance().showToast(getView(), "Please Enter Zipcode");

		  }
		 else{
			 Intent in= new Intent(getActivity(),Payment_InformationActivity.class);
				in.putExtra("name", name.getText().toString());
				in.putExtra("last_name", mAppPrefs.getString(ConstantsUrl.LAST_NAME, ""));
				in.putExtra("email", email.getText().toString());
				in.putExtra("phone_no", phoneno.getText().toString());
				in.putExtra("shipping_street", ship_street.getText().toString());
				in.putExtra("shipping_city", ship_city.getText().toString());
				in.putExtra("shipping_city_id", cityCode);

				in.putExtra("shipping_state", ship_state.getText().toString());
				in.putExtra("shipping_state_code", stateCode);
				in.putExtra("shipping_code", ship_zip.getText().toString());

				startActivity(in);
		 }
	}
	
	
	private void findStateCodeStr(String stateStr) {
		String stateCodeStr = "";
		try {
			for (int j = 0; j < filterStateList.size(); j++) {
				RestaurantStateMetadata stateData = filterStateList.get(j);
				if (stateData.getState_name() != null
						&& stateData.getState_name().equalsIgnoreCase(
								stateStr.trim())) {
					stateCodeStr = stateData.getState_code();
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			stateCodeStr = "";
		}
		stateCode = stateCodeStr;
	}

	class Allstate extends AsyncTask<String[], Void, String> {
		Dialog progress;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progress = new Dialog(getActivity());
			progress.requestWindowFeature(Window.FEATURE_NO_TITLE);
			progress.setCancelable(false);
			progress.setCanceledOnTouchOutside(false);
			progress.setContentView(R.layout.custom_progress_layout);
			progress.getWindow().setBackgroundDrawable(
					new ColorDrawable(android.graphics.Color.TRANSPARENT));
			progress.show();
		}

		@Override
		protected String doInBackground(String[]... params) {
			// TODO Auto-generated method stub

			return JsonParser.Webserice_Call_URL(ConstantsUrl.GETSTATES,
					params[0], params[1]);
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			System.out.println(result);
			parseResponse(result);
			Collections.sort(data);
			filterStateList = data;
			progress.dismiss();
		}
	}

	private void parseResponse(String statesResponse) {

		data.clear();
		filterStateList.clear();
		if (statesResponse != null) {
			try {

				JSONObject mainJsonObject = new JSONObject(statesResponse);
				if (mainJsonObject.has("state")) {
					String state = JsonParser.getkeyValue_Str(mainJsonObject,
							"state");
					if (state != null && state.equals("1")) {
						JSONObject dataJsonObject = mainJsonObject
								.getJSONObject("data");
						if (dataJsonObject.has("states")) {
							JSONArray statesArr = dataJsonObject
									.getJSONArray("states");
							for (int j = 0; j < statesArr.length(); j++) {
								JSONObject jsonObject = statesArr
										.getJSONObject(j);
								RestaurantStateMetadata stateData = new RestaurantStateMetadata();
								// stateData.setState_id(cursorObj.getString(0));
								stateData.setCountry_code("US");
								stateData.setState_code(JsonParser
										.getkeyValue_Str(jsonObject,
												"state_code").trim());
								
								stateData.setState_name(JsonParser
										.getkeyValue_Str(jsonObject,
												"state_name"));
								System.out.println("......."
										+ stateData.getState_name());
								data.add(stateData);
							}
						}
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private class StatesAdapter extends BaseAdapter implements Filterable {
		private StatesFilter statesFilterObj;

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return data.size();
		}

		@Override
		public RestaurantStateMetadata getItem(int position) {
			// TODO Auto-generated method stub
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub

			Viewholder holderObj;
			if (convertView == null) {
				LayoutInflater mInflater = LayoutInflater.from(getActivity());
				holderObj = new Viewholder();
				convertView = mInflater.inflate(R.layout.dropdown_item, parent,
						false);
				holderObj.nameTxt = (TextView) convertView
						.findViewById(R.id.text);
				convertView.setTag(holderObj);
			} else {
				holderObj = (Viewholder) convertView.getTag();
			}
			RestaurantStateMetadata cityData = getItem(position);
			if (cityData != null)
				holderObj.nameTxt.setText(cityData.getState_name());
			holderObj.nameTxt.setPadding((int) (4 * getResources()
					.getDisplayMetrics().density), (int) (8 * getResources()
					.getDisplayMetrics().density), (int) (4 * getResources()
					.getDisplayMetrics().density), (int) (8 * getResources()
					.getDisplayMetrics().density));
			return convertView;
		}

		@Override
		public Filter getFilter() {
			// TODO Auto-generated method stub
			if (statesFilterObj == null) {
				statesFilterObj = new StatesFilter();
			}
			return statesFilterObj;
		}

	}

	private class StatesFilter extends Filter {

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			// TODO Auto-generated method stub
			FilterResults results = new FilterResults();
			if (constraint != null && constraint.length() > 0) {
				ArrayList<RestaurantStateMetadata> filterList = new ArrayList<RestaurantStateMetadata>();
				
				for (int i = 0; i < filterStateList.size(); i++) {
					if (filterStateList.get(i).getState_name().toUpperCase()
							.startsWith(constraint.toString().toUpperCase())) {
						filterList.add(filterStateList.get(i));
					}
				}
				results.count = filterList.size();
				results.values = filterList;
				
			} else {
				results.count = filterStateList.size();
				results.values = filterStateList;
			}
			return results;
		}

		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			// TODO Auto-generated method stub
			
			data = (ArrayList<RestaurantStateMetadata>) results.values;
			stateAdapteObj.notifyDataSetChanged();
		}

	}
		public class Viewholder {
			private TextView nameTxt;
		}
	
	
	private void getStoreDelivereableId(String stateCode) {
		try {
			DatabaseHelper helperobj = DatabaseHelper
					.newInstance(getActivity());
			helperobj.openDataBase();
			Cursor cursorObj = helperobj
					.getDeliveryUsingCity(shippingCityCodeStr);

			if (cursorObj != null) {
				if (cursorObj.moveToFirst()) {
					do {
						shippingStoreDeliverableLocation = cursorObj
								.getString(cursorObj
										.getColumnIndex(DatabaseHelper.LOCALITY_STOREDELIVERRID));
					} while (cursorObj.moveToNext());
				}
				cursorObj.close();
			}

			helperobj.close();

		} catch (Exception e) {

		}
	}

	
	
	private class GetCitiesAsy extends AsyncTask<String[], Void, String> {
		int status;

		public GetCitiesAsy(int value) {
			// TODO Auto-generated constructor stub
			status = value;
		}
		ACProgressCustom progressDialog;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
//			progressDialog = new Dialog(getActivity());
//			progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//			progressDialog.setCancelable(false);
//			progressDialog.setCanceledOnTouchOutside(false);
//			progressDialog.setContentView(R.layout.custom_progress_layout);
//			progressDialog.getWindow().setBackgroundDrawable(
//					new ColorDrawable(android.graphics.Color.TRANSPARENT));
//			progressDialog.show();
			
			progressDialog = new ACProgressCustom.Builder(getActivity())
		    .useImages(R.drawable.loading1, R.drawable.loading2,R.drawable.loading3, R.drawable.loading4,R.drawable.loading5,R.drawable.loading6).build();
			progressDialog.setCanceledOnTouchOutside(true);

			progressDialog.show();
		}

		@Override
		protected String doInBackground(String[]... params) {
			// TODO Auto-generated method stub
			return JsonParser.Webserice_Call_URL(ConstantsUrl.GETCITIES
					, params[0], params[1]);
				//getDeliverableCities(params[0]);
				// System.out.println(params[0] + " cityList " +
				// cityList.size());
			
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Log.e("Result", result);
			parseCitiesResponse(result);

			Collections.sort(cityList);

			filterCityList = cityList;

			
			progressDialog.dismiss();
		}

	}

	private void parseCitiesResponse(String response) {
//		System.out.println("test city running2 ");
		cityNamesList.clear();
		cityList.clear();

		if (response != null) {
			try {
				JSONObject mainJsonObject = new JSONObject(response);
				if (mainJsonObject.has("state")) {
					String state = JsonParser.getkeyValue_Str(mainJsonObject,
							"state");
					if (state != null && state.equals("1")) {
						JSONObject dataJsonObject = mainJsonObject
								.getJSONObject("data");
						if (dataJsonObject.has("cities")) {
							JSONArray cityArr = dataJsonObject
									.getJSONArray("cities");
							for (int i = 0; i < cityArr.length(); i++) {
								JSONObject cityJsonObject = cityArr
										.getJSONObject(i);
								RestaurantCityMetadata city = new RestaurantCityMetadata();
								city.setCityId(JsonParser.getkeyValue_Str(
										cityJsonObject, "city_id"));
								city.setCityName(JsonParser.getkeyValue_Str(
										cityJsonObject, "city_name"));
								city.setStateCode(JsonParser.getkeyValue_Str(
										cityJsonObject, "state_code"));
								if (!cityNamesList.containsKey(city
										.getCityName())) {
									cityNamesList.put(city.getCityName(), city);
									cityList.add(city);
								}
							}

						}
					}
				}
			} catch (Exception e) {
				// e.printStackTrace();
			}
		}
	}
	
	private class CityAdapter extends BaseAdapter implements Filterable {
		private CityFilter cityFilterObj;

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return cityList.size();
		}

		@Override
		public RestaurantCityMetadata getItem(int position) {
			// TODO Auto-generated method stub
			return cityList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			Viewholder holderObj;
			if (convertView == null) {
				LayoutInflater mInflater = LayoutInflater.from(getActivity());
				holderObj = new Viewholder();
				convertView = mInflater.inflate(R.layout.dropdown_item, parent,
						false);
				holderObj.nameTxt = (TextView) convertView
						.findViewById(R.id.text);
				convertView.setTag(holderObj);
			} else {
				holderObj = (Viewholder) convertView.getTag();
			}
			RestaurantCityMetadata cityData = getItem(position);
			if (cityData != null)
				holderObj.nameTxt.setText(cityData.getCityName());
			holderObj.nameTxt.setPadding((int) (4 * getResources()
					.getDisplayMetrics().density), (int) (8 * getResources()
					.getDisplayMetrics().density), (int) (4 * getResources()
					.getDisplayMetrics().density), (int) (8 * getResources()
					.getDisplayMetrics().density));
			return convertView;
		}

		@Override
		public Filter getFilter() {
			// TODO Auto-generated method stub

			if (cityFilterObj == null) {
				cityFilterObj = new CityFilter();
			}
			return cityFilterObj;
		}

	}
	private class CityFilter extends Filter {

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			// TODO Auto-generated method stub

			FilterResults results = new FilterResults();
			if (constraint != null && constraint.length() > 0) {
				ArrayList<RestaurantCityMetadata> filterList = new ArrayList<RestaurantCityMetadata>();
				for (int i = 0; i < filterCityList.size(); i++) {
					if (filterCityList.get(i).getCityName().toUpperCase()
							.startsWith(constraint.toString().toUpperCase())) {
						filterList.add(filterCityList.get(i));
					}
				}
				results.count = filterList.size();
				results.values = filterList;
			} else {
				results.count = filterCityList.size();
				results.values = filterCityList;
			}
			return results;
		}

		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			// TODO Auto-generated method stub
			cityList = (ArrayList<RestaurantCityMetadata>) results.values;
			cityAdapterObj.notifyDataSetChanged();
		}

	}

	private void getDeliverableCities(String stateCode) {
		try {

			cityNamesList.clear();
			ArrayList<RestaurantCityMetadata> deliverableCities = new ArrayList<RestaurantCityMetadata>();

			DatabaseHelper helperObj = DatabaseHelper
					.newInstance(getActivity());
			helperObj.openDataBase();
			Cursor cursorObj = helperObj.getDeliverableCities(stateCode);

			if (cursorObj != null) {
				if (cursorObj.moveToFirst()) {
					do {
						RestaurantCityMetadata cityMetadata = new RestaurantCityMetadata();
						cityMetadata
								.setCityId(cursorObj.getString(cursorObj
										.getColumnIndex(DatabaseHelper.LOCALITY_CITYID)));
						cityMetadata.setCityName(cursorObj.getString(cursorObj
								.getColumnIndex(DatabaseHelper.LOCALITY_CITY)));
						cityMetadata
								.setStateCode(cursorObj.getString(cursorObj
										.getColumnIndex(DatabaseHelper.LOCALITY_STATECODE)));
						cityMetadata
								.setStoreDeliveredId(cursorObj.getString(cursorObj
										.getColumnIndex(DatabaseHelper.LOCALITY_STOREDELIVERRID)));
						cityMetadata
								.setMinimumOrderValue(cursorObj.getString(cursorObj
										.getColumnIndex(DatabaseHelper.LOCALITY_MOV)));

						deliverableCities.add(cityMetadata);
						cityNamesList.put(cityMetadata.getCityName(),
								cityMetadata);

					} while (cursorObj.moveToNext());
				}
				cursorObj.close();
			}
			cityList = deliverableCities;
			helperObj.close();
		} catch (Exception e) {

			// e.printStackTrace();
		}

	}

}