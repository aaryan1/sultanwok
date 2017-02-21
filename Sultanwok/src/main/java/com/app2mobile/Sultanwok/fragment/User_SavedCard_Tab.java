package com.app2mobile.Sultanwok.fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.app2mobile.acplibrary.ACProgressCustom;
import com.app2mobile.Sultanwok.BaseActivity;
import com.app2mobile.Sultanwok.CreditCard_Adapter;
import com.app2mobile.Sultanwok.R;
import com.app2mobile.metadata.Credit_Card_Metadata;
import com.app2mobile.utiles.AppUtiles;
import com.app2mobile.utiles.ConstantsUrl;
import com.app2mobile.utiles.JsonParser;

import android.app.ActionBar.LayoutParams;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class User_SavedCard_Tab extends Fragment {
	private RecyclerView recycle;
	public Button add_card;
	private CreditCard_Adapter credit_adapter;
	  private List<Credit_Card_Metadata> list;
		public SharedPreferences mAppPref, mRestaurantDetailPrefs,mAppPrefs;
		private SharedPreferences.Editor prefs_editor;
		  boolean isValid = true;
		  Dialog	expressDialog;
		  EditText exp;
		  TextInputLayout cardExpLayout;
		  TextWatcher mDateEntryWatcher;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.user_profile_savedcard, container, false);
	}
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		try{
		 mAppPrefs = ((BaseActivity) getActivity()).mAppPref;
			mAppPref = getActivity().getSharedPreferences(ConstantsUrl.APP_PREF, 0);
		
			prefs_editor = mAppPref.edit();
			list= new ArrayList<Credit_Card_Metadata>();
		add_card=(Button)view.findViewById(R.id.add_card);
		recycle= (RecyclerView)view.findViewById(R.id.card_list);
		LinearLayoutManager  mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
		recycle.setLayoutManager(mLayoutManager);
		add_card.setBackgroundColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));

		//new GetUserCardDetails().execute();
add_card.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				expressDialog = new Dialog(getActivity(), R.style.CustomDialog);
				expressDialog.setContentView(R.layout.add_creditcard);
				expressDialog.getWindow().setLayout(LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT);
				
				  exp= (EditText)expressDialog.findViewById(R.id.exp);
					 cardExpLayout=(TextInputLayout)expressDialog.findViewById(R.id.cardExp_text_input_layout);

				final EditText card_number=(EditText)expressDialog.findViewById(R.id.card_number);
				exp.setRawInputType(Configuration.KEYBOARD_12KEY);
				card_number.setRawInputType(Configuration.KEYBOARD_12KEY);

				final Button sub= (Button)expressDialog.findViewById(R.id.submit);
				exp.addTextChangedListener(mDateEntryWatcher);
				sub.setBackgroundColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));
				sub.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(isValid){
							
						new Add_Card().execute(new String[]{"cust_id","store_id","c_num","e_date"}, new String[]{mAppPrefs.getString(ConstantsUrl.CUSTOMER_ID, ""),ConstantsUrl.STOREID,card_number.getText().toString(),exp.getText().toString()});
						refresh_card();
						}else{
						AppUtiles.getInstance().showToast(getView(), "Exp Date is Not Valid.");

					}
					}
				});
				expressDialog.show();
			}
		});

mDateEntryWatcher = new TextWatcher() {
	
	@Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String working = s.toString();
       
       isValid=true;
        if (working.length()==2 && before ==0) {
        	if (Integer.parseInt(working) < 1 || Integer.parseInt(working)>12) {
        		isValid = false;
            } else {
            	working+="/";
            	exp.setText(working);
            	exp.setSelection(working.length());
            	
            }
       } 
        else if (working.length()==7 && before ==0) {
    	   String enteredYear = working.substring(3);
    	   int currentYear = Calendar.getInstance().get(Calendar.YEAR);
           if (Integer.parseInt(enteredYear) < currentYear) {
        	   isValid = false;
           }
       } else if (working.length()!=7) {
    	   isValid = false;
       }
      
       if (!isValid) {
        	//AppUtiles.getInstance().showToast(getContext(),"Enter a valid date: MM/YYYY");
        	cardExpLayout.setError("Enter a valid date: MM/YYYY");

       } else {
    	   cardExpLayout.setError(null);
       }
       
	}

	@Override
	public void afterTextChanged(Editable s) {}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
	 
};
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private void refresh_card(){
		credit_adapter= new CreditCard_Adapter(getActivity(), list);
		credit_adapter.clearApplications();
		new GetUserCardDetails().execute();
	}
	
	public class GetUserCardDetails extends AsyncTask<String[], Void, String>{

		@Override
		protected String doInBackground(String[]... params) {
			// TODO Auto-generated method stub
			return JsonParser.Webserice_Call_URL(ConstantsUrl.GETCREDITCARDDETAILS, new String[]{"cust_id","store_id"}, new String[]{mAppPrefs.getString(ConstantsUrl.CUSTOMER_ID, ""),ConstantsUrl.STOREID});
			
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
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
							Credit_Card_Metadata detail= new Credit_Card_Metadata(mAppPrefs.getString(ConstantsUrl.FIRST_NAME, ""),obj.getString("card"),obj.getString("expirationmonth"),obj.getString("expirationyear"),obj.getString("imageUrl"),obj.getString("token"));
							list.add(detail);
//							
						}
						
						credit_adapter= new CreditCard_Adapter(getActivity(), list);
						
					    recycle.setAdapter(credit_adapter);
					    credit_adapter.notifyDataSetChanged();
					}
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public class Add_Card extends AsyncTask<String[],Void, String>{
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
			
			return JsonParser.Webserice_Call_URL(ConstantsUrl.ADDCARD, params[0], params[1]);
			
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Log.e("Add card result", result);
			progressDialog.dismiss();
			try {
				JSONObject mainobj= new JSONObject(result);
				String state= mainobj.getString("state");
				if(state.equals("1")){
					String msg= mainobj.getString("msg");
					AppUtiles.getInstance().showToast(getView(), msg);
				}else{
					String msg= mainobj.getString("msg");
					AppUtiles.getInstance().showToast(getView(), msg);
				}
				
				expressDialog.dismiss();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	 
}
