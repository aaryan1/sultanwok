package com.app2mobile.Sultanwok;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.app2mobile.acplibrary.ACProgressCustom;
import com.app2mobile.metadata.Single_Address_Metadata;
import com.app2mobile.utiles.ConstantsUrl;
import com.app2mobile.utiles.JsonParser;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Billing_Address_Dialog extends Activity{
	public SharedPreferences mAppPref, mRestaurantDetailPrefs;
	 List<Single_Address_Metadata> address_list;
	  RecyclerView address_recycle;
	  Address_Adapter single_address;
		private SharedPreferences appPrefs;
		Button submit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.billing_address_listview);
		appPrefs = getSharedPreferences(ConstantsUrl.APP_PREF, 0);
		address_recycle=(RecyclerView)findViewById(R.id.address_list);
		submit=(Button)findViewById(R.id.add_address);
		address_list= new ArrayList<Single_Address_Metadata>();
		LinearLayoutManager  manager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL, false);
		address_recycle.setLayoutManager(manager);
		new GetUserAddress().execute();
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	
	
	public class GetUserAddress extends AsyncTask<String[], Void, String>{
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
			
			progressDialog = new ACProgressCustom.Builder(getApplicationContext())
		    .useImages(R.drawable.loading1, R.drawable.loading2,R.drawable.loading3, R.drawable.loading4,R.drawable.loading5,R.drawable.loading6).build();
			progressDialog.setCanceledOnTouchOutside(true);

			progressDialog.show();
}
		@Override
		protected String doInBackground(String[]... params) {
			// TODO Auto-generated method stub
	return JsonParser.Webserice_Call_URL(ConstantsUrl.GETUSERADDRESS, new String[]{"cust_id"}, new String[]{appPrefs.getString(ConstantsUrl.CUSTOMER_ID, "")});

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
						single_address= new Address_Adapter(getApplicationContext(), address_list);
					
						single_address.notifyDataSetChanged();
						
						address_recycle.setAdapter(single_address);
					
					}
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
