package com.app2mobile.Sultanwok.fragment;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.app2mobile.acplibrary.ACProgressCustom;
import com.app2mobile.Sultanwok.Address_Adapter;
import com.app2mobile.Sultanwok.BaseActivity;
import com.app2mobile.Sultanwok.CreditCard_Adapter;
import com.app2mobile.Sultanwok.R;
import com.app2mobile.metadata.Single_Address_Metadata;
import com.app2mobile.utiles.AppUtiles;
import com.app2mobile.utiles.ConstantsUrl;
import com.app2mobile.utiles.JsonParser;
import com.app2mobile.utiles.OnSaveListerner;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class User_Address_Tab extends Fragment {
	public SharedPreferences mAppPref, mRestaurantDetailPrefs,mAppPrefs;
	 SharedPreferences.Editor prefs_editor;
RecyclerView address;
Button add_address;
Address_Adapter single_address;
List<Single_Address_Metadata> address_list;
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	//return inflater.inflate(R.layout., root, attachToRoot);
	return inflater.inflate(R.layout.user_address_tab,container,false);
}
@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		 mAppPrefs = ((BaseActivity) getActivity()).mAppPref;
			mAppPref = getActivity().getSharedPreferences(ConstantsUrl.APP_PREF, 0);
		
			prefs_editor = mAppPref.edit();
		address=(RecyclerView)view.findViewById(R.id.address);
		add_address=(Button)view.findViewById(R.id.add_address);
		address_list= new ArrayList<Single_Address_Metadata>();
		add_address.setBackgroundColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));
		LinearLayoutManager  manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
		address.setLayoutManager(manager);
		
	//	new GetUserAddress().execute();
		address.addOnItemTouchListener(new CreditCard_Adapter(getActivity(), new CreditCard_Adapter.OnItemClickListener() {
			
			@SuppressLint("NewApi") @Override
			public void onItemClick(View view, int position) {
				// TODO Auto-generated method stub
//		
				try{
				if(((com.app2mobile.Sultanwok.Global)User_Address_Tab.this.getActivity().getApplication()).getIs_from_payment_information_page().equals("false")){
					Single_Address_Metadata meta= address_list.get(position);
					Log.e("false",((com.app2mobile.Sultanwok.Global)User_Address_Tab.this.getActivity().getApplication()).getIs_from_payment_information_page());

					//AppUtiles.getInstance().showToast(getActivity(), "called"+meta.getAddress1());
					ShippingBillingForProfileFragment fr = new ShippingBillingForProfileFragment();
					Bundle bundle= new Bundle();
					bundle.putStringArray("shippingAddress", new String[]{meta.getAddress1(),meta.getCity(),meta.getState(),meta.getPincode(),meta.getCityCode(),meta.getStateCode(),meta.getAddress_name(),meta.getShipping_id()});
					 //expressDialog.show();
					fr.setArguments(bundle);
					fr.setOnSaveListerner(new OnSaveListerner() {

						
						@Override
						public void onValueSave(String... value) {
						AppUtiles.getInstance().showToast(getView(), value[0]);
						
						}
						});
					fr.show(getActivity().getSupportFragmentManager(), "shipping");
					
				}else{
					
							// TODO Auto-generated method stub
							Log.e("true",((com.app2mobile.Sultanwok.Global)User_Address_Tab.this.getActivity().getApplication()).getIs_from_payment_information_page());

			    		
					
					
				}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
		}));
		
		
	add_address.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				ShippingBillingForProfileFragment fr = new ShippingBillingForProfileFragment();
				Bundle bundle= new Bundle();
				bundle.putStringArray("shippingAddress", new String[]{"","","","","","","",""});
				fr.setArguments(bundle);
					fr.setOnSaveListerner(new OnSaveListerner() {

					
					@Override
					public void onValueSave(String... value) {
					AppUtiles.getInstance().showToast(getView(), value[0]);
					
					 refresh_address();
					}
					});
				fr.show(getFragmentManager(), "shipping");
			}
		});
	
	}
private void refresh_address(){
	single_address= new Address_Adapter(getActivity(), address_list);

	single_address.clearApplications();

	 new GetUserAddress().execute();
		
}
public class GetUserAddress extends AsyncTask<String[], Void, String>{
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
return JsonParser.Webserice_Call_URL(ConstantsUrl.GETUSERADDRESS, new String[]{"cust_id"}, new String[]{mAppPrefs.getString(ConstantsUrl.CUSTOMER_ID, "")});

	}
	
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		Log.e("Resulttt", result);
		progressDialog.dismiss();
		try {
			JSONObject mainjson= new JSONObject(result);
			if(mainjson.has("state")){
				String state= mainjson.getString("state");
				if(state.equals("1")){
					JSONObject data= mainjson.getJSONObject("data");
					JSONArray shipping_address= data.getJSONArray("customer_shipping_address");
					for(int i=0; i<shipping_address.length();i++){
						JSONObject obj= shipping_address.getJSONObject(i);
						
						
						Single_Address_Metadata de= new Single_Address_Metadata(obj.getString("address"), obj.getString("city_name"),obj.getString("state_name"),obj.getString("pincode"),obj.getString("city_id"),obj.getString("state_code"),obj.getString("shipping_id"),obj.getString("add_name"));
						address_list.add(de);
						
					}
					single_address= new Address_Adapter(getActivity(), address_list);
			       
					single_address.notifyDataSetChanged();
					
					address.setAdapter(single_address);
				
				}
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
}
