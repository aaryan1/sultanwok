package com.app2mobile.Sultanwok.fragment;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.app2mobile.Sultanwok.Payment_InformationActivity;
import com.app2mobile.Sultanwok.R;
import com.app2mobile.metadata.SpinnerItemMetadata;
import com.app2mobile.utiles.CreditCardValidator;
import com.app2mobile.utiles.MonthYearPicker;
import com.app2mobile.utiles.MyTextWatcher;


public class Credit_Card_Detail_Fragment extends BaseFragment {
	private static Credit_Card_Detail_Fragment instance;
	private TextView card_number,cvv_number;
	private Spinner cardTypeSpin;
	private SpinnerAdapter cardAdapter;
	private Button submit;
	private Boolean selected;
	private int selectedMonth, selectedYear;
	private TextInputLayout  cardTypeLayout, cardNumberLayout, cardCVVLayout,
	cardExpLayout, shippingLayout;
	private ArrayList<SpinnerItemMetadata> cardType = new ArrayList<SpinnerItemMetadata>();
	MonthYearPicker myp;
	private EditText month_year;
	SpinnerItemMetadata cardTypeData = (SpinnerItemMetadata) cardTypeSpin
			.getSelectedItem();
	private CreditCardValidator cardValidator;
	private int check;
	public static Credit_Card_Detail_Fragment newInstance() {
		if (instance == null)
			instance = new Credit_Card_Detail_Fragment();
		return instance;
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		cardValidator = new CreditCardValidator(getActivity());

		card_number=(TextView)view.findViewById(R.id.card_number);
		month_year=(EditText)view.findViewById(R.id.month_year);
		cvv_number=(TextView)view.findViewById(R.id.cvv);
		submit=(Button)view.findViewById(R.id.add);
		cardTypeLayout = (TextInputLayout) view
				.findViewById(R.id.cardType_text_input_layout);
		cardTypeSpin = (Spinner) view.findViewById(R.id.cardType);
		cardNumberLayout = (TextInputLayout) view
				.findViewById(R.id.card_number_text_input_layout);
		cardCVVLayout = (TextInputLayout) view
				.findViewById(R.id.cvv_text_input_layout);

		cardExpLayout = (TextInputLayout) view
				.findViewById(R.id.cardExp_text_input_layout);
		Typeface tf= Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
		card_number.setTypeface(tf);
		month_year.setTypeface(tf);
		cvv_number.setTypeface(tf);
		
		cardType.add(new SpinnerItemMetadata("American Express", "AE", 2));
		cardType.add(new SpinnerItemMetadata("Discover", "DI", 3));
		cardType.add(new SpinnerItemMetadata("MasterCard", "MC", 0));
		cardType.add(new SpinnerItemMetadata("Visa", "VI", 1));
		cardAdapter = new SpinnerAdapter(cardType, "Card Type");
		cardTypeSpin.setAdapter(cardAdapter);
		month_year.addTextChangedListener(mDateEntryWatcher);
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
		
		
		cvv_number.addTextChangedListener(new MyTextWatcher(cardCVVLayout,
				getString(R.string.cardCVV_required), 0, getActivity()));
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
					
					checkValues();
					Intent in = new Intent(getActivity(),Payment_InformationActivity.class);
					in.putExtra("card_type", cardTypeData.getValue().toString());
					in.putExtra("card_number", card_number.getText().toString());
					in.putExtra("month", selectedMonth);
					in.putExtra("year", selectedYear);
					in.putExtra("cvv", cvv_number.getText().toString());
					startActivity(in);
					
				
			}
		});
		
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
				
				//openDatePicker();
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
	
	@Override
	public int getLayout() {
		// TODO Auto-generated method stub
		return R.layout.dialog_card_detail;
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
	private void checkValues() {
		String cardNumberStr = card_number.getText().toString();
		String expiryDateStr = month_year.getText().toString();
		SpinnerItemMetadata deliveryData = (SpinnerItemMetadata) cardTypeSpin
				.getSelectedItem();
		String shippingStr = deliveryData.getName();
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
	
}
