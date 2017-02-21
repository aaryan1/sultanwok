package com.app2mobile.Sultanwok;

import java.util.ArrayList;
import java.util.Calendar;

import com.app2mobile.Sultanwok.fragment.Credit_Card_Detail_Fragment;
import com.app2mobile.metadata.SpinnerItemMetadata;
import com.app2mobile.utiles.ConstantsUrl;
import com.app2mobile.utiles.CreditCardValidator;



import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class Credit_Card_Detail extends Activity {
	private static Credit_Card_Detail_Fragment instance;
	private TextView card_number;
	private EditText month_year,cvv_number;
	private Spinner cardTypeSpin;
	private SpinnerAdapter cardAdapter;
	private Button submit;
	private Boolean selected=false;
	private String selectedMonth, selectedYear;
	private ArrayList<SpinnerItemMetadata> cardType = new ArrayList<SpinnerItemMetadata>();
	private int check;
	private TextInputLayout  cardTypeLayout, cardNumberLayout, cardCVVLayout,cardExpLayout;
	private CreditCardValidator cardValidator;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.dialog_card_detail);
			cardValidator = new CreditCardValidator(getApplicationContext());
		card_number=(TextView)findViewById(R.id.card_number);
		month_year=(EditText)findViewById(R.id.month_year);
		cvv_number=(EditText)findViewById(R.id.cvv);
		month_year.addTextChangedListener(mDateEntryWatcher);
		cardExpLayout=(TextInputLayout) findViewById(R.id.cardExp_text_input_layout);
		cardCVVLayout=(TextInputLayout)findViewById(R.id.cvv_text_input_layout);
		submit=(Button)findViewById(R.id.add);
		cardTypeSpin = (Spinner) findViewById(R.id.cardType);
		cardNumberLayout = (TextInputLayout) findViewById(R.id.card_number_text_input_layout);
		Typeface tf= Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
		card_number.setTypeface(tf);
		month_year.setTypeface(tf);
		cvv_number.setTypeface(tf);
		submit.setBackgroundColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));
		cardType.add(new SpinnerItemMetadata("American Express", "AE", 2));
		cardType.add(new SpinnerItemMetadata("Discover", "DI", 3));
		cardType.add(new SpinnerItemMetadata("MasterCard", "MC", 0));
		cardType.add(new SpinnerItemMetadata("Visa", "VI", 1));
		cardAdapter = new SpinnerAdapter(cardType, "Card Type");
		
		cardTypeSpin.setAdapter(cardAdapter);
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				checkValidation();
					SpinnerItemMetadata cardTypeData = (SpinnerItemMetadata) cardTypeSpin
							.getSelectedItem();
					//Intent in = new Intent(Credit_Card_Detail.this,Payment_InformationActivity.class);
					
					if(card_number.getText().toString().equals("")|| month_year.getText().toString().equals("") || cvv_number.getText().toString().equals("")|| cardTypeData.getName().equals("")||cvv_number.getText().toString().length()==0||card_number.getText().toString().length()==0||month_year.getText().toString().length()==0)
					{
						Toast.makeText(getApplicationContext(), "All Fields Are Mandatary. Please Fill All Details", Toast.LENGTH_SHORT).show();

					
		}else{
			if(month_year.getText().toString()==null|| month_year.getText().toString().equals("")||month_year.getText().toString().length()==0){
				Toast.makeText(getApplicationContext(), "All Fields Are Mandatary. Please Fill All Details", Toast.LENGTH_SHORT).show();

			}else{
				try{
				 String[] output = month_year.getText().toString().split("/");

				 selectedMonth=output[0];
			       selectedYear=  output[1];
			       
			       
			       ((Global)Credit_Card_Detail.this.getApplicationContext()).setCard_number(card_number.getText().toString());
					((Global)Credit_Card_Detail.this.getApplicationContext()).setCard_type(cardTypeData.getValue().toString());
					((Global)Credit_Card_Detail.this.getApplicationContext()).setMonth(selectedMonth);
					((Global)Credit_Card_Detail.this.getApplicationContext()).setYear(selectedYear);
					((Global)Credit_Card_Detail.this.getApplicationContext()).setCvv(cvv_number.getText().toString());
					//in.putExtra("card_type", cardTypeData.getValue().toString());
					
					//startActivityForResult(in, 1005);
					if(((Global)Credit_Card_Detail.this.getApplicationContext()).getIs_billing_address().equals("1")){
					Intent de = new Intent(Credit_Card_Detail.this,Pickup_Address_dialog.class);
					startActivity(de);
					
					finish();
					}else{
						finish();

					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
		
		}
				
			}
		});
		cvv_number.addTextChangedListener(new com.app2mobile.utiles.MyTextWatcher(cardCVVLayout,
				getString(R.string.cardCVV_required), 0, getApplicationContext()));
		cardTypeSpin.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				selected = true;
				cardAdapter.notifyDataSetChanged();
				return false;
			}
		});
		cardTypeSpin.setOnItemSelectedListener(new OnItemSelectedListener() {

			

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				check++;
				if (check > 1) {
					SpinnerItemMetadata cardTypeData = (SpinnerItemMetadata) cardTypeSpin
							.getSelectedItem();
					if (!cardValidator.isCreditCardValid(card_number
							.getText().toString(), cardTypeData.getId())) {
						cardNumberLayout.setErrorEnabled(true);
						cardNumberLayout
								.setError("Card type/card number is not valid.");
					} else {
						cardNumberLayout.setErrorEnabled(false);
					}
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		
		month_year.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		card_number.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				SpinnerItemMetadata cardTypeData = (SpinnerItemMetadata) cardTypeSpin
						.getSelectedItem();
				if (s.toString() == null || s.toString().equals("")) {
					cardNumberLayout.setErrorEnabled(true);
					cardNumberLayout
							.setError(getString(R.string.cardNumber_required));
				} else if (!cardValidator.isCreditCardValid(s.toString(),
						cardTypeData.getId())) {
					cardNumberLayout.setErrorEnabled(true);
					cardNumberLayout
							.setError("Card type/card number is not valid.");
				} else {
					cardNumberLayout.setErrorEnabled(false);
				}
			}
		});
		
		
	}
	
		private TextWatcher mDateEntryWatcher = new TextWatcher() {
				
				@Override
			    public void onTextChanged(CharSequence s, int start, int before, int count) {
			        String working = s.toString();
			       
			        boolean isValid = true;
			        if (working.length()==2 && before ==0) {
			        	if (Integer.parseInt(working) < 1 || Integer.parseInt(working)>12) {
			        		isValid = false;
			            } else {
			            	working+="/";
			            	month_year.setText(working);
			            	month_year.setSelection(working.length());
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

			private void checkValidation() {
			
				SpinnerItemMetadata cardTypeData = (SpinnerItemMetadata) cardTypeSpin
						.getSelectedItem();
				String cardNumberStr = card_number.getText().toString();
				String expiryDateStr = month_year.getText().toString();
				
				
				String CvvStr = cvv_number.getText().toString();
				String cardStr = cardTypeData.getValue();
				
				if (cardStr == null || cardStr.equals("")) {

					cardTypeLayout.setEnabled(true);
					cardTypeLayout.setError(getString(R.string.cardType_required));

				}
				if (cardNumberStr == null || cardNumberStr.equals("")) {

					cardNumberLayout.setEnabled(true);
					cardNumberLayout.setError(getString(R.string.cardNumber_required));
				} else if (!cardValidator.isCreditCardValid(cardNumberStr,
						cardTypeData.getId())) {
				
					cardNumberLayout.setErrorEnabled(true);
					cardNumberLayout.setError("Card type/card number is not valid.");
				}
				if (CvvStr == null || CvvStr.equals("")) {

					cardCVVLayout.setEnabled(true);
					cardCVVLayout.setError(getString(R.string.cardCVV_required));
				} else if (CvvStr.length() < 3) {

					cardCVVLayout.setEnabled(true);
					cardCVVLayout.setError(getString(R.string.card_validrequired));
				}

				if (expiryDateStr == null || expiryDateStr.equals("")) {
					// AppUtiles.getInstance().showToast(getActivity(),
					// getString(R.string.cardExp_required));
					cardExpLayout.setEnabled(true);
					cardExpLayout.setError(getString(R.string.cardExp_required));
				}
			}
	private class SpinnerAdapter extends BaseAdapter {
		private ArrayList<SpinnerItemMetadata> data;
		private String hint;

		public SpinnerAdapter(ArrayList<SpinnerItemMetadata> data, String hint) {
			this.data = data;
			this.hint = hint;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return data.size();
		}

		@Override
		public SpinnerItemMetadata getItem(int position) {
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
			if (!selected) {
				holderObj.nameTxt.setText(hint);
				holderObj.nameTxt.setTextColor(getResources().getColor(
						R.color.hint_color));
			} else {
				holderObj.nameTxt.setText(getItem(position).getName());
				holderObj.nameTxt.setTextColor(getResources().getColor(
						R.color.black));
			}

			return convertView;
		}

		@Override
		public View getDropDownView(int position, View convertView,
				ViewGroup parent) {
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
			holderObj.nameTxt.setText(getItem(position).getName());
			holderObj.nameTxt.setPadding((int) (4 * getResources()
					.getDisplayMetrics().density), (int) (8 * getResources()
					.getDisplayMetrics().density), (int) (4 * getResources()
					.getDisplayMetrics().density), (int) (8 * getResources()
					.getDisplayMetrics().density));
			holderObj.nameTxt.setTextColor(getResources().getColor(
					R.color.black));
			return convertView;
		}
	}

	private class ViewHolder {
		private TextView nameTxt;
	}
}
