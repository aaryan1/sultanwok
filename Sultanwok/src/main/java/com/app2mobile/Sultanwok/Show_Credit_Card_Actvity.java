package com.app2mobile.Sultanwok;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.app2mobile.acplibrary.ACProgressCustom;
import com.app2mobile.metadata.Credit_Card_Metadata;
import com.app2mobile.utiles.ConstantsUrl;
import com.app2mobile.utiles.JsonParser;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Show_Credit_Card_Actvity extends Activity {

	private List<Credit_Card_Metadata> list;
	  private CreditCard_Adapter credit_adapter;
		public SharedPreferences mAppPref, mRestaurantDetailPrefs;
		RecyclerView recycle;
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	 setContentView(R.layout.saved_card_view);
	 mAppPref = getSharedPreferences(ConstantsUrl.APP_PREF, 0);
		mRestaurantDetailPrefs = getSharedPreferences(
				ConstantsUrl.RESTAURANTDETAILPREFS, 0);
		recycle= (RecyclerView)findViewById(R.id.card_list);
		Button submit = (Button)findViewById(R.id.add_card);
		LinearLayoutManager  mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
		recycle.setLayoutManager(mLayoutManager);
	  list= new ArrayList<Credit_Card_Metadata>();
	  
	 new  GetUserCardDetails().execute();
	 submit.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(((Global)Show_Credit_Card_Actvity.this.getApplicationContext()).getIs_billing_address().equals("1")){
			Intent de = new Intent(Show_Credit_Card_Actvity.this,Pickup_Address_dialog.class);
			startActivity(de);
			finish();
			}else{
				finish();
			}
			
		}
	});
};
	
	public class GetUserCardDetails extends AsyncTask<String[], Void, String>{
		ACProgressCustom progressDialog;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			
			progressDialog = new ACProgressCustom.Builder(Show_Credit_Card_Actvity.this)
		    .useImages(R.drawable.loading1, R.drawable.loading2,R.drawable.loading3, R.drawable.loading4,R.drawable.loading5,R.drawable.loading6).build();
			progressDialog.setCanceledOnTouchOutside(true);

			progressDialog.show();
			
		}
		@Override
		protected String doInBackground(String[]... params) {
			// TODO Auto-generated method stub
			return JsonParser.Webserice_Call_URL(ConstantsUrl.GETCREDITCARDDETAILS, new String[]{"cust_id","store_id"}, new String[]{mAppPref.getString(ConstantsUrl.CUSTOMER_ID, ""),ConstantsUrl.STOREID});
			
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			progressDialog.dismiss();
			Log.e("Resulttt", result);
			try {
				JSONObject mainjson= new JSONObject(result);
				if(mainjson.has("state")){
					String state= mainjson.getString("state");
					if(state.equals("1")){
						JSONObject data= mainjson.getJSONObject("data");
						JSONArray saved_card= data.getJSONArray("saved_cards");
						for(int i=0; i<saved_card.length();i++){
							JSONObject obj= saved_card.getJSONObject(i);
							Credit_Card_Metadata detail= new Credit_Card_Metadata(mAppPref.getString(ConstantsUrl.FIRST_NAME, "") ,obj.getString("card"),obj.getString("expirationmonth"),obj.getString("expirationyear"),obj.getString("imageUrl"),obj.getString("token"));
							list.add(detail);
//							
						}
						
						credit_adapter= new CreditCard_Adapter(getApplicationContext(), list);
				        
						credit_adapter.notifyDataSetChanged();
					    recycle.setAdapter(credit_adapter);
					}else{
						finish();
						Intent in = new Intent(Show_Credit_Card_Actvity.this, Credit_Card_Detail.class);
						startActivity(in);
					}
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


}
