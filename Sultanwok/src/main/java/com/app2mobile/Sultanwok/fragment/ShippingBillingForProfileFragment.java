package com.app2mobile.Sultanwok.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.app2mobile.acplibrary.ACProgressCustom;
import com.app2mobile.Sultanwok.BaseActivity;
import com.app2mobile.Sultanwok.R;
import com.app2mobile.metadata.RestaurantCityMetadata;
import com.app2mobile.metadata.RestaurantLocalityMetadata;
import com.app2mobile.metadata.RestaurantStateMetadata;
import com.app2mobile.utiles.AppUtiles;
import com.app2mobile.utiles.ConstantsUrl;
import com.app2mobile.utiles.JsonParser;
import com.app2mobile.utiles.OnSaveListerner;

public class ShippingBillingForProfileFragment extends DialogFragment {
	private boolean isBilling;
	private EditText streetEdt, zipCodeEdt,address_name_Edt;// , phoneEdt;
	private Button dismissBtn, saveBtn;
	private LinearLayout sameShippingLaout;
	private CheckBox mCheckbx;
	private String streetStr, cityStr, stateStr, zipCodeStr, stateCode,
			cityCode = "", localityId = "", storeDeliverableId = "",
			locaionName = "",shipping_id,address_name,msg;
	private TextView titleTxt;

	private String address[] = new String[4];
	private String billingAddress[] = new String[4];
	private OnSaveListerner onSaveListerner;
	private AutoCompleteTextView cityEdt, statesEdt;
	private ArrayList<RestaurantStateMetadata> data = new ArrayList<RestaurantStateMetadata>();
	private ArrayList<RestaurantLocalityMetadata> localityList = new ArrayList<RestaurantLocalityMetadata>();
	private ArrayList<RestaurantCityMetadata> cityList = new ArrayList<RestaurantCityMetadata>();
	private ArrayList<RestaurantCityMetadata> filterCityList = new ArrayList<RestaurantCityMetadata>();
	private ArrayList<RestaurantStateMetadata> filterStateList = new ArrayList<RestaurantStateMetadata>();
	private HashMap<String, RestaurantCityMetadata> cityNamesList = new HashMap<String, RestaurantCityMetadata>();
	private CityAdapter cityAdapterObj;
	private TextView stateTextView, localityTxt;
	private StatesAdapter stateAdapteObj;

	// AddressMetadata shippingAddress;
	public void setOnSaveListerner(OnSaveListerner onSaveListerner) {
		this.onSaveListerner = onSaveListerner;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setStyle(STYLE_NO_TITLE, R.style.CustomDialog4);

		Bundle intent = getArguments();

		if (intent != null) {
			isBilling = intent.getBoolean("test");
			address = intent.getStringArray("shippingAddress");
			if (intent.containsKey("billingAddress")) {
				billingAddress = intent.getStringArray("billingAddress");
			}
		}
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if (getDialog() == null) {
			return;
		}
		getDialog().getWindow().setLayout(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		getDialog().setCanceledOnTouchOutside(false);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		return inflater.inflate(R.layout.update_address, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		getDialog().getWindow().setLayout(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
//		dismissBtn = (Button) view.findViewById(R.id.dismiss);
		saveBtn = (Button) view.findViewById(R.id.sub);
		streetEdt = (EditText) view.findViewById(R.id.street);

		cityEdt = (AutoCompleteTextView) view.findViewById(R.id.city);
		// cityEdt.requestFocus();
		// streetEdt.requestFocus();
		address_name_Edt=(EditText)view.findViewById(R.id.address_name);
		zipCodeEdt = (EditText) view.findViewById(R.id.pincode);
		zipCodeEdt.setRawInputType(Configuration.KEYBOARD_12KEY);
		//mCheckbx = (CheckBox) view.findViewById(R.id.sameCheckbox);
		// = (TextView) view.findViewById(R.id.title);
//		sameShippingLaout = (LinearLayout) view
//				.findViewById(R.id.sameShippingLayout);
		//localityTxt = (TextView) view.findViewById(R.id.street);
		//stateTextView = (TextView) view.findViewById(R.id.state);
		statesEdt = (AutoCompleteTextView) view.findViewById(R.id.state);
		
		//localityTxt.setVisibility(View.GONE);
//		stateTextView.setVisibility(View.GONE);
//		if (isBilling) {
//			sameShippingLaout.setVisibility(View.GONE);
//			titleTxt.setText(getString(R.string.billing_address));
//
//		}
//
		saveBtn.setBackgroundColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));
		if (address != null) {
			streetEdt.setText(address[0]);
			statesEdt.setText(address[2]);
			cityEdt.setText(address[1]);
			zipCodeEdt.setText(address[3]);
			stateStr = address[2];
			cityStr = address[1];
			// phoneEdt.setText(address[4]);
			// localityStr = address[5];
			stateCode = address[5];
			cityCode = address[4];
			shipping_id=address[6];
			address_name=address[7];
			address_name_Edt.setText(address_name);

		}
		stateAdapteObj = new StatesAdapter();
		statesEdt.setThreshold(1);
		statesEdt.setAdapter(stateAdapteObj);
		cityAdapterObj = new CityAdapter();
		cityEdt.setThreshold(1);
		cityEdt.setAdapter(cityAdapterObj);

		new GetAllStates().execute(new String[] { "country_code" },
				new String[] { "US" });
		if (stateStr != null && !stateStr.equals("")
				&& !stateStr.equals("null") && stateCode != null
				&& !stateCode.equals("") && !stateCode.equals("null")) {
			new GetCitiesAsy(1).execute(stateCode);

		}

	
		statesEdt.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				// TODO Auto-generated method stub
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					stateStr = statesEdt.getText().toString();
					cityStr = "";
					cityCode = "";
					localityId = "";
					storeDeliverableId = "";
					locaionName = "";
					//localityTxt.setText("");
					cityEdt.setText("");
					// System.out.println("state   " + stateStr);
					findStateCodeStr(stateStr);
					new GetCitiesAsy(0).execute(stateCode);
					return true;
				}
				return false;
			}
		});
		statesEdt.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				stateStr = data.get(position).getState_name();
				stateCode = data.get(position).getState_code();
				statesEdt.setText(stateStr);
				cityStr = "";
				cityCode = "";
				localityId = "";
				storeDeliverableId = "";
				locaionName = "";
				//localityTxt.setText("");
				cityEdt.setText("");
				// findStateCodeStr(stateStr);
				new GetCitiesAsy(0).execute(stateCode);
			}
		});
	
		cityEdt.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				RestaurantCityMetadata cityData = cityList.get(position);
				cityStr = cityData.getCityName();
				cityCode = cityData.getCityId();
				cityEdt.setText(cityStr);
				localityId = "";
				storeDeliverableId = "";
				locaionName = "";
				// localityTxt.setText("");
				// if (!isBilling)
				// getAllLocality(stateCode, cityCode);
			}
		});
//
//		mCheckbx.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//			@Override
//			public void onCheckedChanged(CompoundButton buttonView,
//					boolean isChecked) {
//				// TODO Auto-generated method stub
//				if (isChecked) {
//					/**
//					 * set from prefernces
//					 */
//					// selected=true;
//					if (billingAddress != null) {
//						if (billingAddress[0] != null)
//							streetEdt.setText(billingAddress[0]);
//
//						// stateEdt.setText(stateStr);
//						if (billingAddress[2] != null) {
//							// stateTextView.setText(billingAddress[2]);
//							stateStr = billingAddress[2];
//							stateCode = billingAddress[5];
//							statesEdt.setText(billingAddress[2]);
//							new GetCitiesAsy(1).execute(stateCode);
//						}
//						if (billingAddress[1] != null) {
//							cityEdt.setText(billingAddress[1]);
//							cityCode = billingAddress[6];
//							cityStr = billingAddress[1];
//
//						}
//						// findPositionOfStates(billingAddress[2]);
//						if (billingAddress[3] != null)
//							zipCodeEdt.setText(billingAddress[3]);
//						// if (billingAddress[4] != null)
//						// phoneEdt.setText(billingAddress[4]);
//						if (billingAddress[7] != null
//								&& !billingAddress[7].equals("")
//								&& !billingAddress.equals("null")) {
//							localityId = billingAddress[7];
//							storeDeliverableId = billingAddress[8];
//							locaionName = billingAddress[9];
//							// localityTxt.setText(locaionName);
//
//						}
//					}
//				} else {
//					streetEdt.setText("");
//					stateStr = "";
//					stateCode = "";
//					statesEdt.setText("");
//					// new GetCitiesAsy().execute(stateCode);
//					cityEdt.setText("");
//					cityCode = "";
//					cityStr = "";
//					zipCodeEdt.setText("");
//
//					localityId = "";
//					storeDeliverableId = "";
//					locaionName = "";
//
//				}
//			}
//		});
//		dismissBtn.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				dismiss();
//			}
//		});
		saveBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				checkValues();
			}
		});
	}
//
//	private void getDeliveredId() {
//		String city = cityEdt.getText().toString();
//		if (city != null && !city.equals("") && !city.equalsIgnoreCase("null")) {
//
//			if (!cityNamesList.containsKey(cityEdt.getText().toString())) {
//				if (!isBilling) {
//					AppUtiles.getInstance()
//							.showToast(getActivity(),
//									"We do not deliver order in selected city. Please select other city.");
//				} else {
//					AppUtiles.getInstance().showToast(getActivity(),
//							"Please enter valid city");
//				}
//				storeDeliverableId = "";
//				cityCode = "";
//			} else {
//				//
//				if (!isBilling) {
//					RestaurantCityMetadata cityData = cityNamesList.get(cityEdt
//							.getText().toString());
//					cityCode = cityData.getCityId();
//					storeDeliverableId = cityData.getStoreDeliveredId();
//
//				}
//
//			}
//		} else {
//			AppUtiles.getInstance().showToast(getActivity(), getString(R.string.please_city));
//		}
//	}
//
	private void checkValues() {
		String streetStr = streetEdt.getText().toString();
		String cityStr = cityEdt.getText().toString();

		String zipCodeStr = zipCodeEdt.getText().toString();
		// String phoneStr = phoneEdt.getText().toString();
		String phoneStr = "";

		if ((!(cityStr == null || cityStr.equals("") || cityStr.equals("null")))
				&& (storeDeliverableId == null || storeDeliverableId.equals("") || storeDeliverableId
						.equals("null"))) {

			if (!cityNamesList.containsKey(cityEdt.getText().toString())) {
				if (!isBilling) {
					storeDeliverableId = "";
					cityCode = "";
				}

			} else {
				if (!isBilling) {

					RestaurantCityMetadata cityData = cityNamesList.get(cityEdt
							.getText().toString());
					cityCode = cityData.getCityId();
					storeDeliverableId = cityData.getStoreDeliveredId();
				}
			}

		}
		//
		if (streetStr == null || streetStr.equals("")) {
			AppUtiles.getInstance().showToast(getView(),
					getString(R.string.please_street));
		} else if (stateStr == null || stateStr.equals("")
				|| stateStr.equals("") || stateCode == null
				|| stateCode.equals("") || stateCode.equals("null")) {
			AppUtiles.getInstance()
					.showToast(getView(), getString(R.string.please_state));
		} else if (cityStr == null || cityStr.equals("")
				|| cityStr.equalsIgnoreCase("null")) {
			AppUtiles.getInstance().showToast(getView(), getString(R.string.please_city));
		}
		// else if (phoneStr == null || phoneStr.equals("")) {
		// AppUtiles.showToast(getActivity(),
		// getString(R.string.phone_required));
		// }
//		else if (!isBilling
//				&& (storeDeliverableId == null || storeDeliverableId.equals("") || storeDeliverableId
//						.equals("null"))) {
//			AppUtiles.getInstance()
//					.showToast(getActivity(),
//							"We do not deliver order in selected city. Please select other city.");
//
//		} 
		else if (zipCodeStr == null || zipCodeStr.equals("")) {
			AppUtiles.getInstance().showToast(getView(),
					getString(R.string.please_zipcode));
		} else {
			if (onSaveListerner != null) {
//				onSaveListerner.onValueSave(streetStr, cityStr, stateStr,
//						zipCodeStr, phoneStr, stateCode, cityCode, localityId,
//						storeDeliverableId, locaionName);
				SharedPreferences mAppPrefs,mAppPref, mRestaurantDetailPrefs;
				mAppPrefs = ((BaseActivity) getActivity()).mAppPref;
				mAppPref = getActivity().getSharedPreferences(ConstantsUrl.APP_PREF, 0);
				if(shipping_id.equals("")){
					new Save_NEW_Address().execute(new String[]{"cust_id","add_name","address","city_id","city_name","state_code","pincode","phone"}, new String[]{mAppPrefs.getString(ConstantsUrl.CUSTOMER_ID, ""),address_name_Edt.getText().toString(),streetEdt.getText().toString(),cityCode,cityEdt.getText().toString(),stateCode,zipCodeEdt.getText().toString(),"" });
				}else{
				new Save_Address().execute(new String[]{"cust_id","shipping_id","add_name","address","city_id","city_name","state_code","pincode","phone"}, new String[]{mAppPrefs.getString(ConstantsUrl.CUSTOMER_ID, ""),shipping_id,address_name_Edt.getText().toString(),streetEdt.getText().toString(),cityCode,cityEdt.getText().toString(),stateCode,zipCodeEdt.getText().toString(),"" });
				
				}
			}
			dismiss();
		}
//
	}
//
	private class ViewHolder {
		private TextView nameTxt;
	}
//
	private class GetAllStates extends AsyncTask<String[], Void, String> {
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
			return JsonParser.Webserice_Call_URL(ConstantsUrl.GETSTATES, params[0],
					params[1]);
			// getAllStates();
			// return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			parseResponse(result);
			System.out.println(result);
			Collections.sort(data);
			filterStateList = data;
			progressDialog.dismiss();
			// statesAdapter.notifyDataSetChanged();
			// findPositionOfStates(stateStr);
		}
	}

	private class GetCitiesAsy extends AsyncTask<String, Void, String> {
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
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
		//	if (isBilling) {
				return JsonParser.Webserice_Call_URL(ConstantsUrl.GETCITIES, new String[]{"country_code","state_code"}, new String[]{"US",stateCode});
//			} 
//			else {
//				getDeliverableCities(params[0]);
//				// System.out.println(params[0] + " cityList " +
//				// cityList.size());
//			}
//			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			//if (isBilling) {
				parseCitiesResponse(result);
			//}

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

//	private void getDeliverableCities(String stateCode) {
//		try {
//			
//			cityNamesList.clear();
//			ArrayList<RestaurantCityMetadata> deliverableCities = new ArrayList<RestaurantCityMetadata>();
//
//			DatabaseHelper helperObj = DatabaseHelper.newInstance(getActivity());
//			helperObj.openDataBase();
//			Cursor cursorObj = helperObj.getDeliverableCities(stateCode);
//
//			if (cursorObj != null) {
//				if (cursorObj.moveToFirst()) {
//					do {
//						RestaurantCityMetadata cityMetadata = new RestaurantCityMetadata();
//						cityMetadata
//								.setCityId(cursorObj.getString(cursorObj
//										.getColumnIndex(DatabaseHelper.LOCALITY_CITYID)));
//						cityMetadata.setCityName(cursorObj.getString(cursorObj
//								.getColumnIndex(DatabaseHelper.LOCALITY_CITY)));
//						cityMetadata
//								.setStateCode(cursorObj.getString(cursorObj
//										.getColumnIndex(DatabaseHelper.LOCALITY_STATECODE)));
//						cityMetadata
//								.setStoreDeliveredId(cursorObj.getString(cursorObj
//										.getColumnIndex(DatabaseHelper.LOCALITY_STOREDELIVERRID)));
//						cityMetadata
//								.setMinimumOrderValue(cursorObj.getString(cursorObj
//										.getColumnIndex(DatabaseHelper.LOCALITY_MOV)));
//
//
//							deliverableCities.add(cityMetadata);
//							cityNamesList.put(cityMetadata.getCityName(),
//									cityMetadata);
//
//
//					} while (cursorObj.moveToNext());
//				}
//				cursorObj.close();
//			}
//			cityList = deliverableCities;
//			helperObj.close();
//		} catch (Exception e) {
//
//			// e.printStackTrace();
//		}
//		
//	}
//
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
//
//	private void findCityCode(String cityStr) {
//		String cityCodeStr = "";
//		// if (cityStr != null && !cityStr.equals("") &&
//		// !cityStr.equals("null")) {
//		try {
//			for (int i = 0; i < filterCityList.size(); i++) {
//				RestaurantCityMetadata cityData = filterCityList.get(i);
//				if (cityData.getCityName() != null
//						&& cityData.getCityName().equalsIgnoreCase(
//								cityStr.trim())) {
//					cityCodeStr = cityData.getCityId();
//
//					break;
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			cityCodeStr = "";
//		}
//		// }
//
//		cityCode = cityCodeStr;
//	}
//
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
			ViewHolder holderObj;
			if (convertView == null) {
				LayoutInflater mInflater = LayoutInflater.from(getActivity());
				holderObj = new ViewHolder();
				convertView = mInflater.inflate(R.layout.dropdown_item, parent,
						false);
				holderObj.nameTxt = (TextView) convertView
						.findViewById(R.id.text);
				convertView.setTag(holderObj);
			} else {
				holderObj = (ViewHolder) convertView.getTag();
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
//
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

			ViewHolder holderObj;
			if (convertView == null) {
				LayoutInflater mInflater = LayoutInflater.from(getActivity());
				holderObj = new ViewHolder();
				convertView = mInflater.inflate(R.layout.dropdown_item, parent,
						false);
				holderObj.nameTxt = (TextView) convertView
						.findViewById(R.id.text);
				convertView.setTag(holderObj);
			} else {
				holderObj = (ViewHolder) convertView.getTag();
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
	
	public class Save_Address extends AsyncTask<String[], Void, String>{
		ACProgressCustom progressDialog;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
//			
			
			progressDialog = new ACProgressCustom.Builder(getActivity())
		    .useImages(R.drawable.loading1, R.drawable.loading2,R.drawable.loading3, R.drawable.loading4,R.drawable.loading5,R.drawable.loading6).build();
			progressDialog.setCanceledOnTouchOutside(true);

			progressDialog.show();	
}
		
		@Override
		protected String doInBackground(String[]... params) {
			// TODO Auto-generated method stub
			return JsonParser.Webserice_Call_URL(ConstantsUrl.SAVE_ADDRESS, params[0], params[1]);
			
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Log.e("resullttt", result);
			progressDialog.dismiss();
			if(result!=null){
			try {
				
				JSONObject mainjson= new JSONObject(result);
				String state= mainjson.getString("state");
				
				if(state.equals("1")){
					 msg=mainjson.getString("msg");
					 onSaveListerner.onValueSave(msg);
				}else{
				//	Toast.makeText(getActivity(), "Something Went Wrong, Please Try Again Later", Toast.LENGTH_LONG).show();
					//AppUtiles.getInstance().showToast(getActivity(), "Something Went Wrong, Please Try Again Later");
				msg="Something Went Wrong, Please Try Again Later";
				onSaveListerner.onValueSave(msg);
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		}
	}
	
	public class Save_NEW_Address extends AsyncTask<String[], Void, String>{
		ACProgressCustom progressDialog;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
//			
			
			progressDialog = new ACProgressCustom.Builder(getActivity())
		    .useImages(R.drawable.loading1, R.drawable.loading2,R.drawable.loading3, R.drawable.loading4,R.drawable.loading5,R.drawable.loading6).build();
			progressDialog.setCanceledOnTouchOutside(true);

			progressDialog.show();	
}
		
		@Override
		protected String doInBackground(String[]... params) {
			// TODO Auto-generated method stub
			return JsonParser.Webserice_Call_URL(ConstantsUrl.SAVE_NEW_ADDRESS, params[0], params[1]);
			
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Log.e("resullttt", result);
			progressDialog.dismiss();
			if(result!=null){
			try {
				
				JSONObject mainjson= new JSONObject(result);
				String state= mainjson.getString("state");
				
				if(state.equals("1")){
					 msg=mainjson.getString("msg");
					 onSaveListerner.onValueSave(msg);
				}else{
				//	Toast.makeText(getActivity(), "Something Went Wrong, Please Try Again Later", Toast.LENGTH_LONG).show();
					//AppUtiles.getInstance().showToast(getActivity(), "Something Went Wrong, Please Try Again Later");
				msg="Something Went Wrong, Please Try Again Later";
				onSaveListerner.onValueSave(msg);
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		}
	}
}
