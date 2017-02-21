package com.app2mobile.Sultanwok.fragment;

import org.json.JSONException;
import org.json.JSONObject;

import com.app2mobile.acplibrary.ACProgressCustom;
import com.app2mobile.Sultanwok.BaseActivity;
import com.app2mobile.Sultanwok.R;
import com.app2mobile.utiles.AppUtiles;
import com.app2mobile.utiles.ConstantsUrl;
import com.app2mobile.utiles.JsonParser;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class User_Profile_Tab extends Fragment {
 EditText profileName;
 EditText profileLName;
 EditText profilePhone;
 Button save_change;
	public SharedPreferences mAppPref, mRestaurantDetailPrefs,mAppPrefs;
	private SharedPreferences.Editor prefs_editor;
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	return inflater.inflate(R.layout.user_profile_tab, container, false);
	}
	@SuppressWarnings("static-access")
	@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onViewCreated(view, savedInstanceState);
			profileName = (EditText) view.findViewById(R.id.profileName);
			profileLName = (EditText) view.findViewById(R.id.profile_lname);
			 profilePhone=(EditText)view.findViewById(R.id.profileemail);
			 profilePhone.setRawInputType(Configuration.KEYBOARD_12KEY);
			 save_change=(Button)view.findViewById(R.id.button);
			 mAppPrefs = ((BaseActivity) getActivity()).mAppPref;
				mAppPref = getActivity().getSharedPreferences(ConstantsUrl.APP_PREF, 0);
			
				prefs_editor = mAppPref.edit();
				
				String firstName = mAppPrefs.getString(ConstantsUrl.FIRST_NAME, "");
				String lastName = mAppPrefs.getString(ConstantsUrl.LAST_NAME, "");
				String telephoneStr =  mAppPrefs.getString(ConstantsUrl.PHONE, "");
				if (firstName != null && !firstName.equals("null") && lastName != null
						&& !lastName.equals("null")) {

					profileName.setText(firstName);
					profileLName.setText(lastName);
					profilePhone.setText(telephoneStr);
				} else {
					profileName.setText("");
				}
				
				save_change.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						new Save_User_Info().execute(new String[]{"cust_id","customer_fname","customer_lname","customer_phone"}, new String[]{mAppPrefs.getString(ConstantsUrl.CUSTOMER_ID, ""),profileName.getText().toString(),profileLName.getText().toString(),profilePhone.getText().toString()});
	
					}
				});
		}
	
	
	public class Save_User_Info extends AsyncTask<String[], Void, String>{
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
			return JsonParser.Webserice_Call_URL(ConstantsUrl.SAVING_USER_INFO, params[0], params[1]);
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			try {
				progressDialog.dismiss();
				JSONObject mainobj= new JSONObject(result);
					
					if(mainobj.has("state")){
						String state =mainobj.getString("state");
						if(state.equals("1")){
							String msg=mainobj.getString("msg");
							AppUtiles.getInstance().showToast(getView(), msg);
							JSONObject obj= mainobj.getJSONObject("data");
							JSONObject cust_data= obj.getJSONObject("customerData");
							prefs_editor.putString(ConstantsUrl.FIRST_NAME, cust_data.getString("customer_fname"));
							prefs_editor.putString(ConstantsUrl.LAST_NAME, cust_data.getString("customer_lname"));
							prefs_editor.putString(ConstantsUrl.PHONE, cust_data.getString("customer_phone"));
							prefs_editor.commit();
						}
						else{
							String msg=mainobj.getString("msg");
							AppUtiles.getInstance().showToast(getView(), msg);

						}
					}
					
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
