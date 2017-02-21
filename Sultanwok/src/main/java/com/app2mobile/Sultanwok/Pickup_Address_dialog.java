package com.app2mobile.Sultanwok;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.app2mobile.acplibrary.ACProgressCustom;
import com.app2mobile.metadata.RestaurantCityMetadata;
import com.app2mobile.metadata.RestaurantLocalityMetadata;
import com.app2mobile.metadata.RestaurantStateMetadata;
import com.app2mobile.metadata.Restaurant_Miles_Charge;
import com.app2mobile.utiles.ConstantsUrl;
import com.app2mobile.utiles.JsonParser;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView.OnEditorActionListener;

public class Pickup_Address_dialog extends Activity {
	AutoCompleteTextView cityEdt,statesEdt;
	Button saveBtn;
	EditText address_name_Edt,zipCodeEdt,streetEdt;
	private ArrayList<RestaurantStateMetadata> data = new ArrayList<RestaurantStateMetadata>();
	private ArrayList<RestaurantLocalityMetadata> localityList = new ArrayList<RestaurantLocalityMetadata>();
	private ArrayList<RestaurantCityMetadata> cityList = new ArrayList<RestaurantCityMetadata>();
	private ArrayList<RestaurantCityMetadata> filterCityList = new ArrayList<RestaurantCityMetadata>();
	private ArrayList<RestaurantStateMetadata> filterStateList = new ArrayList<RestaurantStateMetadata>();
	private HashMap<String, RestaurantCityMetadata> cityNamesList = new HashMap<String, RestaurantCityMetadata>();
	private CityAdapter cityAdapterObj;
	private StatesAdapter stateAdapteObj;
	private String streetStr, cityStr, stateStr, zipCodeStr, stateCode,
	cityCode = "", localityId = "", storeDeliverableId = "",
	locaionName = "",shipping_id,address_name,msg;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
//	setTheme(android.R.style.Theme_DeviceDefault_Dialog_MinWidth);
//	requestWindowFeature(Window.FEATURE_NO_TITLE);
	this.setFinishOnTouchOutside(false);
	super.onCreate(savedInstanceState);
//	getWindow().setLayout(LayoutParams.MATCH_PARENT,
//			LayoutParams.MATCH_PARENT);
//	
	setContentView(R.layout.pickup_address);
	
	 cityEdt = (AutoCompleteTextView)findViewById(R.id.city);
	 saveBtn = (Button)findViewById(R.id.sub);

	 address_name_Edt = (EditText)findViewById(R.id.address_name);
	 zipCodeEdt = (EditText)findViewById(R.id.pincode);
		streetEdt = (EditText)findViewById(R.id.street);

	 statesEdt = (AutoCompleteTextView)findViewById(R.id.state);
	 zipCodeEdt.setRawInputType(Configuration.KEYBOARD_12KEY);
	stateAdapteObj = new StatesAdapter();
	statesEdt.setThreshold(1);
	statesEdt.setAdapter(stateAdapteObj);
	cityAdapterObj = new CityAdapter();
	cityEdt.setThreshold(1);
	cityEdt.setAdapter(cityAdapterObj);
	new GetAllStates().execute(new String[] { "country_code" },
			new String[] { "US" });
	
	
	saveBtn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(cityEdt.getText().toString().equals("")||statesEdt.getText().toString().equals("")||streetEdt.getText().toString().equals("")||zipCodeEdt.getText().toString().equals("")){
			//	new GetDelvieryCharges().execute(new String[]{"store_id","street_address","city_name","state_code","zip_id"}, new String[]{ConstantsUrl.STOREID,streetStr,cityStr,stateCode,zipCodeEdt.getText().toString()});
			//	AppUtiles.getInstance().showToast(getApplicationContext(), "Please Enter All Details");
				Snackbar.make(getCurrentFocus(), "Please Enter All Details", Snackbar.LENGTH_SHORT).show();
			
			}else{
				((Global)Pickup_Address_dialog.this.getApplicationContext()).setDelivery_street(streetEdt.getText().toString());
				((Global)Pickup_Address_dialog.this.getApplicationContext()).setDelivery_city(cityEdt.getText().toString());
				((Global)Pickup_Address_dialog.this.getApplicationContext()).setDelivery_city_code(cityCode);
				((Global)Pickup_Address_dialog.this.getApplicationContext()).setDelivery_state(statesEdt.getText().toString());
				((Global)Pickup_Address_dialog.this.getApplicationContext()).setDelivery_state_code(stateCode);
				((Global)Pickup_Address_dialog.this.getApplicationContext()).setDelivery_pincode(zipCodeEdt.getText().toString());
				finish();
			}
		}
	});
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

}

private class GetAllStates extends AsyncTask<String[], Void, String> {
	ACProgressCustom progressDialog;
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
//		progressDialog = new Dialog(getActivity());
//		progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		progressDialog.setCancelable(false);
//		progressDialog.setCanceledOnTouchOutside(false);
//		progressDialog.setContentView(R.layout.custom_progress_layout);
//		progressDialog.getWindow().setBackgroundDrawable(
//				new ColorDrawable(android.graphics.Color.TRANSPARENT));
//		progressDialog.show();
		
		progressDialog = new ACProgressCustom.Builder(getApplicationContext())
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
	private Dialog progressDialog;
	int status;

	public GetCitiesAsy(int value) {
		// TODO Auto-generated constructor stub
		status = value;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		progressDialog = new Dialog(Pickup_Address_dialog.this);
		progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		progressDialog.setCancelable(false);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.setContentView(R.layout.custom_progress_layout);
		progressDialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		progressDialog.show();
	}

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
	//	if (isBilling) {
			return JsonParser.Webserice_Call_URL(ConstantsUrl.GETCITIES, new String[]{"country_code","state_code"}, new String[]{"US",stateCode});
//		} 
//		else {
//			getDeliverableCities(params[0]);
//			// System.out.println(params[0] + " cityList " +
//			// cityList.size());
//		}
//		return null;
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
			LayoutInflater mInflater = LayoutInflater.from(getApplicationContext());
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
			LayoutInflater mInflater = LayoutInflater.from(Pickup_Address_dialog.this);
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

private class ViewHolder {
	private TextView nameTxt;
}


public class GetDelvieryCharges extends AsyncTask<String[], Void, String>{
	ACProgressCustom progressDialog;
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
//		progressDialog = new Dialog(getActivity());
//		progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		progressDialog.setCancelable(false);
//		progressDialog.setCanceledOnTouchOutside(false);
//		progressDialog.setContentView(R.layout.custom_progress_layout);
//		progressDialog.getWindow().setBackgroundDrawable(
//				new ColorDrawable(android.graphics.Color.TRANSPARENT));
//		progressDialog.show();
		
		progressDialog = new ACProgressCustom.Builder(getApplicationContext())
	    .useImages(R.drawable.loading1, R.drawable.loading2,R.drawable.loading3, R.drawable.loading4,R.drawable.loading5,R.drawable.loading6).build();
		progressDialog.setCanceledOnTouchOutside(true);

		progressDialog.show();
}
	@Override
	protected String doInBackground(String[]... params) {
		// TODO Auto-generated method stub
		return JsonParser.Webserice_Call_URL(ConstantsUrl.GET_DISTANCE, params[0], params[1]);
	}
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		Log.e("resulttt distance", result);
		progressDialog.dismiss();
		try {
			JSONObject mainobj= new JSONObject(result);
			if(mainobj.has("state")){
				
				String state=mainobj.getString("state");
				if(state.equals("1")){
					Context ctx= Pickup_Address_dialog.this;
				JSONObject data =mainobj.getJSONObject("data");
				String distanceMiles= data.getString("distanceMiles");
				HashMap<Integer, Restaurant_Miles_Charge> miles_hash_map= ((Global)Pickup_Address_dialog.this.getApplication()).getMil_charge();
				for(int i=0;i<miles_hash_map.size();i++){
					Restaurant_Miles_Charge milles=miles_hash_map.get(i);
					String getmiles= milles.getMiles();
					if(getmiles.equals(distanceMiles))
					{
						((Global)Pickup_Address_dialog.this.getApplication()).setDelivery_charge(milles.getCharge());
						Log.e("success", "success"+ ((Global)Pickup_Address_dialog.this.getApplication()).getDelivery_charge());
						break;
					}
					else{
						((Global)Pickup_Address_dialog.this.getApplication()).setDelivery_charge("");
						Log.e("fail", "fail");
					}
				}
				
						//AppUtiles.getInstance().showToast(getContext(), "Delivery Charge"+charge);
					//	Toast.makeText(ctx, "Delivery Charge"+charge, 3).show();
				
//				onSaveListerner.onValueSave(streetStr, cityStr, stateStr,
//						zipCodeStr, phoneStr, stateCode, cityCode, localityId,
//						storeDeliverableId, locaionName,delivery_charge);
				
				}else{
					String msg= mainobj.getString("msg");
					//Toast.makeText(getActivity(), msg, 3).show();
//					onSaveListerner.onValueSave(streetStr, cityStr, stateStr,
//							zipCodeStr, phoneStr, stateCode, cityCode, localityId,
//							storeDeliverableId, locaionName,"");
				}
				finish();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
}
