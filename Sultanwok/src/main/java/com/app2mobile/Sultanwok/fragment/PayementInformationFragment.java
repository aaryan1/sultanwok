package com.app2mobile.Sultanwok.fragment;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app2mobile.acplibrary.ACProgressCustom;
import com.app2mobile.gcm.GCMUtiles;
import com.app2mobile.Sultanwok.BaseActivity;
import com.app2mobile.Sultanwok.Checkout_Webview;
import com.app2mobile.Sultanwok.CreditCard_Adapter;
import com.app2mobile.Sultanwok.Credit_Card_Detail;
import com.app2mobile.Sultanwok.DetailActivity;
import com.app2mobile.Sultanwok.Global;
import com.app2mobile.Sultanwok.OrderHistoryActivity;
import com.app2mobile.Sultanwok.PickupnDeliveryDialog;
import com.app2mobile.Sultanwok.R;
import com.app2mobile.Sultanwok.Show_Credit_Card_Actvity;
import com.app2mobile.Sultanwok.UserProfileActivity;
import com.app2mobile.metadata.AddressMetadata;
import com.app2mobile.metadata.Credit_Card_Metadata;
import com.app2mobile.metadata.PickupnDelivery;
import com.app2mobile.metadata.RestaurantDetailMetadata;
import com.app2mobile.metadata.Restaurant_Miles_Charge;
import com.app2mobile.segmentradiobutton.AwesomeRadioButton;
import com.app2mobile.segmentradiobutton.SegmentedGroup;
import com.app2mobile.utiles.AppUtiles;
import com.app2mobile.utiles.ConstantsUrl;
import com.app2mobile.utiles.DatabaseHelper;
import com.app2mobile.utiles.JsonParser;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;

public class PayementInformationFragment extends BaseFragment {
	private static PayementInformationFragment instance;
	private static RadioGroup card_cash;
	private static RadioButton card, cash;
	public AwesomeRadioButton ten_percent, fifteen_percent,
			twenty_percent;
	public TextView name, address, pincode, checkout, timetxt, totalAmount,
			total_tax, total_with_tax, tip, discount, delivery_charge,
			time_txt, phone, mail;
	private static EditText coupan_code, order_driver_tip, promo_code;
	private static RelativeLayout footer;
	private int isClearCart = 2;
	private String payment_mode, cc_exp_month, cc_number, cc_cvv, cc_type,
			cc_exp_year;
	private ImageView edit_time, edit_detail;
	private GoogleCloudMessaging gcm;
	private Button apply_coupan_code;
	final String prefix = "$ ";
	JSONObject mainJsonObject, DiscountJson;
	private static String regid, intent_name, last_name, email, phone_no,
			billing_street, billing_city, billing_state, billing_code,
			billing_city_code, card_number, month, year, cvv, card_type,
			productJson, discount_code, new_product_total, store_payment,
			discountAmt;

	private RestaurantDetailMetadata restaurantDetailData;
	public SegmentedGroup driver_tip;
	Intent in;

	private String billing_state_code, delivery_amount;
	List<Credit_Card_Metadata> list;
	CreditCard_Adapter credit_adapter;
	RecyclerView recycle;
	Boolean ispause = false;
	private AddressMetadata shippingAddress;
	LinearLayout rel;
	String pastText = "";

	@Override
	public int getLayout() {
		// TODO Auto-generated method stub
		return R.layout.fragment_payement_information;
	}

	public static PayementInformationFragment newInstance() {

		if (instance == null) {

			instance = new PayementInformationFragment();
		}
		return instance;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getActivity().getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

		in = getActivity().getIntent();

		if (in.hasExtra("name")) {
			intent_name = in.getStringExtra("name");
			last_name = in.getStringExtra("last_name");
			email = in.getStringExtra("email");
			phone_no = in.getStringExtra("phone_no");
			billing_street = in.getStringExtra("shipping_street");
			billing_city = in.getStringExtra("shipping_city");
			billing_city_code = in.getStringExtra("shipping_city_id");
			billing_state = in.getStringExtra("shipping_state");
			billing_state_code = in.getStringExtra("shipping_state_code");
			billing_code = in.getStringExtra("shipping_code");
			// month = in.getStringExtra("month");
			// year = in.getStringExtra("year");
			// cvv = in.getStringExtra("cvv");
			// card_type = in.getStringExtra("card_type");
		} else {
			intent_name = mAppPrefs.getString(ConstantsUrl.FIRST_NAME, "");
			last_name = mAppPrefs.getString(ConstantsUrl.LAST_NAME, "");
			email = mAppPrefs.getString(ConstantsUrl.EMAIL, "");

			phone_no = mAppPrefs.getString(ConstantsUrl.PHONE, "");
			billing_street = ((Global) PayementInformationFragment.this
					.getContext().getApplicationContext()).getAddress1();
			billing_city = ((Global) PayementInformationFragment.this
					.getContext().getApplicationContext()).getCity();
			billing_city_code = ((Global) PayementInformationFragment.this
					.getContext().getApplicationContext()).getCityId();
			billing_state = ((Global) PayementInformationFragment.this
					.getContext().getApplicationContext()).getState();
			billing_state_code = ((Global) PayementInformationFragment.this
					.getContext().getApplicationContext()).getSelect_state_id();
			billing_code = ((Global) PayementInformationFragment.this
					.getContext().getApplicationContext()).getPincode();
			card_number = in.getStringExtra("card_number");
		}
	}

	@SuppressLint("NewApi") @Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		Bundle bundle = getActivity().getIntent().getExtras();
		if (bundle != null) {

			if (bundle.containsKey("isCart"))
				isClearCart = bundle.getInt("isCart");
		}
		Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
				"Quattrocento-Bold.ttf");
		name = (TextView) view.findViewById(R.id.name);
		address = (TextView) view.findViewById(R.id.address);
		pincode = (TextView) view.findViewById(R.id.city_pincode);
		card_cash = (RadioGroup) view.findViewById(R.id.card_cash);
		footer = (RelativeLayout) view.findViewById(R.id.footer);
		rel = (LinearLayout) view.findViewById(R.id.rel1);
		card = (RadioButton) view.findViewById(R.id.card);
		cash = (RadioButton) view.findViewById(R.id.cash);
		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP){
			cash.setButtonTintList(ColorStateList.valueOf(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE)));
			card.setButtonTintList(ColorStateList.valueOf(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE)));
		}
		PickupnDelivery pick= new PickupnDelivery();
		ArrayList<String> val= new ArrayList<String>();
		val= ((Global)getActivity().getApplication()).getCard_cash();
		if(val.contains("cash")){
			cash.setVisibility(View.VISIBLE);
		}
		if(val.contains("credit")){
			card.setVisibility(View.VISIBLE);
		}
		mTitleTxt.setText("Checkout");
		edit_time = (ImageView) view.findViewById(R.id.edit);
		coupan_code = (EditText) view.findViewById(R.id.promo_code);
		order_driver_tip = (EditText) view.findViewById(R.id.tip_amount);
		phone = (TextView) view.findViewById(R.id.phone_no);
		mail = (TextView) view.findViewById(R.id.email);
		checkout = (TextView) view.findViewById(R.id.checkOut);
		apply_coupan_code = (Button) view.findViewById(R.id.apply);
		driver_tip = (com.app2mobile.segmentradiobutton.SegmentedGroup) view.findViewById(R.id.segment);
		ten_percent = (AwesomeRadioButton) view.findViewById(R.id.ten);
		fifteen_percent = (AwesomeRadioButton) view.findViewById(R.id.fifteen);
		twenty_percent = (AwesomeRadioButton) view.findViewById(R.id.twenty);
		timetxt = (TextView) view.findViewById(R.id.timetxt);
		edit_detail = (ImageView) view.findViewById(R.id.address_edit);
		totalAmount = (TextView) view.findViewById(R.id.totalAmount);
		total_tax = (TextView) view.findViewById(R.id.vat_amount);
		total_with_tax = (TextView) view.findViewById(R.id.total_amount);
		tip = (TextView) view.findViewById(R.id.tip);
		discount = (TextView) view.findViewById(R.id.discount);
		delivery_charge = (TextView) view.findViewById(R.id.delivery_amount);
		time_txt = (TextView) view.findViewById(R.id.time_text);
		TextView subtotal_txt=(TextView)view.findViewById(R.id.subtotal);
		TextView discount_text=(TextView)view.findViewById(R.id.discount_text);
		TextView tip_text=(TextView)view.findViewById(R.id.tip_text);
		TextView vat=(TextView)view.findViewById(R.id.vat);
		TextView delivery_charges=(TextView)view.findViewById(R.id.delivery_charges);
		TextView total=(TextView)view.findViewById(R.id.total);
		subtotal_txt.setTypeface(tf);
		discount_text.setTypeface(tf);
		tip_text.setTypeface(tf);
		vat.setTypeface(tf);
		delivery_charges.setTypeface(tf);
		total.setTypeface(tf);
		File f = new File("/mnt/sdcard/App2food/"
				+ ConstantsUrl.STORE_BUNDLE_ID + "backround_img.jpg");

		Bitmap bmp = BitmapFactory.decodeFile(f.getAbsolutePath());
		Drawable dr = new BitmapDrawable(bmp);

		rel.setBackgroundDrawable(dr);
		footer.setBackgroundColor(Color
				.parseColor(ConstantsUrl.STORE_COLOR_CODE));
		Gson gson = new Gson();
		String restaurantDetail = ((BaseActivity) getActivity()).mRestaurantDetailPrefs
				.getString(ConstantsUrl.RESTAURANT_DETAILS, "");
		restaurantDetailData = gson.fromJson(restaurantDetail,
				RestaurantDetailMetadata.class);
		store_payment = restaurantDetailData.getmStore_payment();
		String shippingJson = mAppPrefs.getString(
				ConstantsUrl.SHIPPING_ADDRESS, "");

		shippingAddress = gson.fromJson(shippingJson, AddressMetadata.class);
//		apply_coupan_code.setBackgroundColor(Color
//				.parseColor(ConstantsUrl.STORE_COLOR_CODE));
		Typeface tf1 = Typeface.createFromAsset(getActivity().getAssets(),
				"OpenSans-Regular.ttf");
		name.setTypeface(tf1);
		address.setTypeface(tf1);
		pincode.setTypeface(tf1);
		coupan_code.setTypeface(tf1);
		order_driver_tip.setTypeface(tf1);
		checkout.setTypeface(tf1);
		card.setTypeface(tf1);
		cash.setTypeface(tf1);
		apply_coupan_code.setTypeface(tf1);
		totalAmount.setTypeface(tf);
		total_tax.setTypeface(tf);
		total_with_tax.setTypeface(tf);
		tip.setTypeface(tf);
		discount.setTypeface(tf);
		delivery_charge.setTypeface(tf);

		time_txt.setText(((Global) PayementInformationFragment.this
				.getActivity().getApplicationContext()).getDelivery_type()
				+ " Time:");
		if (!card_cash.isSelected()) {
			((Global) PayementInformationFragment.this.getActivity()
					.getApplicationContext()).setDelivery_charge("");
		}
		if (((Global) PayementInformationFragment.this.getActivity()
				.getApplicationContext()).getDelivery_type().equalsIgnoreCase(
				"pickup")) {
			address.setVisibility(View.GONE);
			pincode.setVisibility(View.GONE);
		}
		if (in != null) {
			name.setText(intent_name);
			address.setText(billing_street + ", " + billing_city + ", "
					+ billing_state + ", " + billing_state_code);
			phone.setText(phone_no);
			mail.setText(email);
			pincode.setText(billing_code);
		} else {
			name.setText(mAppPrefs.getString(ConstantsUrl.FIRST_NAME, ""));
			address.setText(mAppPrefs.getString(ConstantsUrl.SHIPPING_ADDRESS,
					""));
			phone.setText(mAppPrefs.getString(ConstantsUrl.PHONE, ""));
			mail.setText(mAppPrefs.getString(ConstantsUrl.EMAIL, ""));
		}
		if (((Global) getActivity().getApplicationContext()).getIs_guest_user()
				.equals("false")) {

			if (billing_city == null || billing_city.equals("")) {
				name.setText(mAppPrefs.getString(ConstantsUrl.FIRST_NAME, ""));
				if (shippingAddress != null) {
					if (shippingAddress.getStreet() != null
							|| !shippingAddress.getStreet().equals("")
							|| shippingAddress.getStreet().length() != 0) {
						address.setText(shippingAddress.getStreet() + ", "
								+ shippingAddress.getCity() + ", "
								+ shippingAddress.getRegion() + ", "
								+ shippingAddress.getRegionId());
						pincode.setText(shippingAddress.getPostCode());
						billing_street = shippingAddress.getStreet();
						billing_city = shippingAddress.getCity();
						billing_state = shippingAddress.getRegion();
						billing_city_code = shippingAddress.getCityId();
						billing_state_code = shippingAddress.getState_code();
						billing_code = shippingAddress.getPostCode();
					} else {
						address.setText("");
						pincode.setText("");
					}
				} else {
					address.setText("");
					pincode.setText("");
				}
			}
		}
		totalAmount.setText(getString(R.string.usd)
				+ " "
				+ ((Global) PayementInformationFragment.this.getActivity()
				.getApplicationContext()).getTotalamout_without_text());


		total_with_tax.setText(getString(R.string.usd)
				+ " "+ ((Global) PayementInformationFragment.this.getActivity()
				.getApplicationContext()).getTotal_amt());
		total_tax.setText("$ "
				+ ((Global) PayementInformationFragment.this.getActivity()
				.getApplicationContext()).getSales_tax());
		timetxt.setText(((Global) PayementInformationFragment.this
				.getActivity().getApplicationContext()).getDelvery_time());
		//

		order_driver_tip.setText("$ 0.00");
		Selection.setSelection(order_driver_tip.getText(), order_driver_tip
				.getText().length());

		// order_driver_tip.getText().setSpan(watcher, 0, 0,
		// Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		discountAmt="0";new_product_total="0";
		calculateTotal();
		edit_detail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mAppPrefs.getBoolean(ConstantsUrl.IS_LOGIN, false)) {
					ispause = true;
					((Global) PayementInformationFragment.this.getActivity()
							.getApplication())
							.setIs_from_payment_information_page("true");
					Log.e("trueeee", ((Global) PayementInformationFragment.this
							.getActivity().getApplication())
							.getIs_from_payment_information_page());

					Intent intent = new Intent(getActivity(),
							UserProfileActivity.class);
					// intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					startActivity(intent);
				} else {
					Intent intent = new Intent(getActivity(),
							DetailActivity.class);
					// intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					startActivity(intent);
				}
			}
		});
		card_cash.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (card.isSelected()) {
					payment_mode = "online";
				} else if (cash.isSelected()) {
					payment_mode = "cash";
//					if (((Global) PayementInformationFragment.this
//							.getActivity().getApplicationContext())
//							.getIs_billing_address().equals("1")) {
//						Intent in = new Intent(getActivity(),
//								Pickup_Address_dialog.class);
//						startActivity(in);
//					}
				}
			}
		});
//		cash.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//			@Override
//			public void onCheckedChanged(CompoundButton buttonView,
//					boolean isChecked) {
//				// TODO Auto-generated method stub
//				if (card.isSelected()) {
//					payment_mode = "online";
//
//					Intent in = new Intent(getActivity(),
//							Credit_Card_Detail.class);
//					startActivity(in);
//				} else {
//					payment_mode = "cash";
//					if (((Global) PayementInformationFragment.this
//							.getActivity().getApplicationContext())
//							.getIs_billing_address().equals("1")) {
//						Intent in = new Intent(getActivity(),
//								Pickup_Address_dialog.class);
//						startActivity(in);
//					}
//					// cc_cvv="";
//					// cc_type="";
//					// cc_exp_year="";
//					// cc_number="";
//					// cc_exp_month="";
//				}
//			}
//		});
		cash.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				payment_mode = "cash";
//				if (((Global) PayementInformationFragment.this
//						.getActivity().getApplicationContext())
//						.getIs_billing_address().equals("1")) {
//					Intent in = new Intent(getActivity(),
//							Pickup_Address_dialog.class);
//					startActivity(in);
//				}
			}
		});
		card.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub

				payment_mode = "online";
				if (mAppPrefs.getBoolean(ConstantsUrl.IS_LOGIN, false)) {

					if (store_payment.equals("Braintree")) {
						Intent in = new Intent(getActivity(),
								Show_Credit_Card_Actvity.class);
						startActivity(in);
					} else {
						Intent in = new Intent(getActivity(),
								Credit_Card_Detail.class);
						startActivity(in);
					}

				} else {
					Intent in = new Intent(getActivity(),
							Credit_Card_Detail.class);
					startActivity(in);
				}
			}
		});
		edit_time.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						PickupnDeliveryDialog.class);
				startActivity(intent);

			}
		});

		Log.e("timeeee", ((Global) PayementInformationFragment.this
				.getActivity().getApplicationContext()).getDelvery_time());

		// ((Spinner) delivery_time).getSelectedView().setEnabled(false);
		Prepare_discount_Json();
		new CalculateDiscount(1).execute(new String[] { "store_id" },new String[] { ConstantsUrl.STOREID });

		apply_coupan_code.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String couponStr = coupan_code.getText().toString();
				int is_logged_in = 0;
				String custId = mAppPrefs.getString(ConstantsUrl.CUSTOMER_ID,
						"");
				if (mAppPrefs.getBoolean(ConstantsUrl.IS_LOGIN, false)) {
					is_logged_in = 1;
				}

				try {
					DiscountJson = new JSONObject();
					DiscountJson.put("vouchercode", couponStr);
					DiscountJson.put("is_logged_in", is_logged_in);
					DiscountJson.put("cust_id", custId);
					DiscountJson.put("store_id", ConstantsUrl.STOREID);
					DiscountJson.put(
							"cart",
							new JSONArray(
									((Global) PayementInformationFragment.this
											.getActivity()
											.getApplicationContext())
											.getProduct_cart()));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (couponStr != null && !couponStr.equals("")) {
					String bigDecimal = ((Global) PayementInformationFragment.this
							.getActivity().getApplicationContext())
							.getTotal_amt();

					new CalculateDiscount(apply_coupan_code).execute(
							new String[] { "store_id" },
							new String[] { ConstantsUrl.STOREID });
				}else{
					AppUtiles.getInstance().showToast(getView(), "Please Enter Voucher Code");
				}
			}
		});
		ten_percent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//	ten_percent.setTextColor(Color.WHITE);
				//	ten_percent.setBackgroundColor(Color
				//		.parseColor(ConstantsUrl.STORE_COLOR_CODE));
				//fifteen_percent.setBackgroundColor(Color.WHITE);
				//twenty_percent.setBackgroundColor(Color.WHITE);

				//	fifteen_percent.setTextColor(Color
				//		.parseColor(ConstantsUrl.STORE_COLOR_CODE));
				//	twenty_percent.setTextColor(Color
				//			.parseColor(ConstantsUrl.STORE_COLOR_CODE));
				double amount = Double
						.parseDouble(((Global) PayementInformationFragment.this
								.getActivity().getApplicationContext())
								.getTotalamout_without_text());
				double total = (amount / 100.0f) * 10;
				NumberFormat formatter = new DecimalFormat("#0.00");
				String total1 = formatter.format(total);
				order_driver_tip.setText("$ " + String.valueOf(total1));
				tip.setText("$ " + total1);

				calculateTotal();
			}
		});
		fifteen_percent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				double amount = Double
						.parseDouble(((Global) PayementInformationFragment.this
								.getActivity().getApplicationContext())
								.getTotalamout_without_text());
				double total = (amount / 100.0f) * 15;
				NumberFormat formatter = new DecimalFormat("#0.00");
				String total1 = formatter.format(total);
				order_driver_tip.setText("$ " + String.valueOf(total1));
				tip.setText("$ " + total1);
				calculateTotal();
			}
		});
		twenty_percent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				twenty_percent.setTextColor(Color.WHITE);
//				twenty_percent.setBackgroundColor(Color
//						.parseColor(ConstantsUrl.STORE_COLOR_CODE));
//				ten_percent.setBackgroundColor(Color.WHITE);
//
//				ten_percent.setTextColor(Color
//						.parseColor(ConstantsUrl.STORE_COLOR_CODE));
//				fifteen_percent.setBackgroundColor(Color.WHITE);
//
//				fifteen_percent.setTextColor(Color
//						.parseColor(ConstantsUrl.STORE_COLOR_CODE));
				double amount = Double
						.parseDouble(((Global) PayementInformationFragment.this
								.getActivity().getApplicationContext())
								.getTotalamout_without_text());
				double total = (amount / 100.0f) * 20;
				NumberFormat formatter = new DecimalFormat("#0.00");
				String total1 = formatter.format(total);
				order_driver_tip.setText("$ " + String.valueOf(total1));
				tip.setText("$ " + total1);
				calculateTotal();
			}
		});
		order_driver_tip.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
									  int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
										  int after) {
				// TODO Auto-generated method stub
				pastText = order_driver_tip.getText().toString();
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				// if(s.toString().contains("$")){
				//
				// }else{
				// // Editable edit=order_driver_tip.getText();
				// //s.insert(order_driver_tip.getSelectionStart(), "$ ");
				// try{
				// calculateTotal();
				// }catch(Exception e){
				// e.printStackTrace();
				// }
				// }
				int length = s.length();
				if (!s.toString().contains("$ ")) {
					order_driver_tip.setText("$ ");
					Selection.setSelection(order_driver_tip.getText(),
							order_driver_tip.getText().length());

					// }try{
					// if(s.length()==1){
					// s.append("0");
					// }
					// calculateTotal();
					// }catch(Exception e){
					// e.printStackTrace();
				}
				calculateTotal();
			}
		});

		footer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Toast.makeText(getContext(), productJson, 3).show();
				Global.getInstance().trackEvent("chekcout page", "click checkout button", "Checkout");

				if (card_cash.getCheckedRadioButtonId() == -1) {
					Toast.makeText(getContext(), "Please Select Payment Type",
							Toast.LENGTH_SHORT).show();
				} else {
					if (((Global) PayementInformationFragment.this
							.getActivity().getApplicationContext())
							.getDelivery_type().equalsIgnoreCase("delivery")) {
						if (address.getText().toString().equals("")
								|| address.getText().toString() == null
								|| address.getText().toString().equals("null")
								|| billing_street == null
								|| billing_street == "") {
							// Toast.makeText(getContext(),
							// "Please Enter Shipping Address",
							// Toast.LENGTH_SHORT).show();
							final Dialog dialog = new Dialog(getActivity(),
									R.style.CustomDialog1);
							dialog.setContentView(R.layout.dialog_alert_message);
							dialog.getWindow().setLayout(
									LayoutParams.MATCH_PARENT,
									LayoutParams.MATCH_PARENT);
							Button cancelBtn = (Button) dialog
									.findViewById(R.id.cancel);
							TextView message = (TextView) dialog
									.findViewById(R.id.message);
							message.setText("Please Enter Shipping Address");
							cancelBtn.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									dialog.dismiss();
								}
							});

							dialog.show();
						} else {
							checkout_function();
						}
					} else {
						checkout_function();
					}
				}
			}
		});

	}

	private void Prepare_discount_Json()
	{
		String couponStr = coupan_code.getText().toString();
		int is_logged_in = 0;
		String custId = mAppPrefs.getString(ConstantsUrl.CUSTOMER_ID,
				"");
		if (mAppPrefs.getBoolean(ConstantsUrl.IS_LOGIN, false)) {
			is_logged_in = 1;
		}

		try {
			DiscountJson = new JSONObject();
			DiscountJson.put("vouchercode", couponStr);
			DiscountJson.put("is_logged_in", is_logged_in);
			DiscountJson.put("cust_id", custId);
			DiscountJson.put("store_id", ConstantsUrl.STOREID);
			DiscountJson.put(
					"cart",
					new JSONArray(
							((Global) PayementInformationFragment.this
									.getActivity()
									.getApplicationContext())
									.getProduct_cart()));
			System.out.println("resultt"+DiscountJson.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

		// Log.e("is pause", ispause.toString());
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		Global.getInstance().trackScreenView("Payment Page");

		if (ispause) {
			if (((Global) PayementInformationFragment.this.getActivity()
					.getApplicationContext()).getDelivery_type()
					.equalsIgnoreCase("delivery")) {
				address.setVisibility(View.VISIBLE);
				pincode.setVisibility(View.VISIBLE);

				billing_street = ((Global) PayementInformationFragment.this
						.getContext().getApplicationContext()).getAddress1();
				billing_city = ((Global) PayementInformationFragment.this
						.getContext().getApplicationContext()).getCity();
				billing_city_code = ((Global) PayementInformationFragment.this
						.getContext().getApplicationContext()).getCityId();
				billing_state = ((Global) PayementInformationFragment.this
						.getContext().getApplicationContext()).getState();
				billing_state_code = ((Global) PayementInformationFragment.this
						.getContext().getApplicationContext())
						.getSelect_state_id();
				billing_code = ((Global) PayementInformationFragment.this
						.getContext().getApplicationContext()).getPincode();
				address.setText(billing_street + ", " + billing_city + ", "
						+ billing_state + ", " + billing_state_code);
				pincode.setText(billing_code);
				ispause=false;

			} else {
				address.setVisibility(View.GONE);
				pincode.setVisibility(View.GONE);

				order_driver_tip.setText("$ 0.00");
			}
		}
		time_txt.setText(((Global) PayementInformationFragment.this
				.getActivity().getApplicationContext()).getDelivery_type()
				+ " Time:");

		timetxt.setText(((Global) PayementInformationFragment.this
				.getActivity().getApplicationContext()).getDelvery_time());
		if (((Global) PayementInformationFragment.this.getActivity()
				.getApplicationContext()).getDelivery_type().equalsIgnoreCase(
				"delivery")) {
			address.setVisibility(View.VISIBLE);
			pincode.setVisibility(View.VISIBLE);
			new GetDelvieryCharges().execute(new String[] { "store_id",
					"street_address", "city_name", "city_id", "state_code",
					"zip_id" }, new String[] { ConstantsUrl.STOREID,
					billing_street, billing_city, billing_city_code,
					billing_state_code, billing_code });
		} else {
			((Global) PayementInformationFragment.this.getActivity()
					.getApplicationContext()).setDelivery_charge("");
			address.setVisibility(View.GONE);
			pincode.setVisibility(View.GONE);
			order_driver_tip.setText("$ 0.00");

		}
		calculateTotal();
	}

	private class SendCheckoutAsync extends AsyncTask<String[], Void, String> {
		String responseServer;
		JSONObject jsonObjRecv;
		String resultString;
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

		@SuppressWarnings("deprecation")
		@Override
		protected String doInBackground(String[]... params) {
			// TODO Auto-generated method stub

			// return JsonParser.Webserice_Call_URL(ConstantsUrl.Stagin,
			// params[0], params[1]);
			DefaultHttpClient client = new DefaultHttpClient();

			HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;

			SchemeRegistry registry = new SchemeRegistry();
			SSLSocketFactory socketFactory = SSLSocketFactory
					.getSocketFactory();
			socketFactory
					.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
			registry.register(new Scheme("https", socketFactory, 443));
			@SuppressWarnings("deprecation")
			SingleClientConnManager mgr = new SingleClientConnManager(
					client.getParams(), registry);
			@SuppressWarnings("deprecation")
			DefaultHttpClient httpClient = new DefaultHttpClient(mgr,
					client.getParams());

			// Set verifier
			HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
			try {
				HttpClient httpclient = new DefaultHttpClient();

				HttpPost httppost = new HttpPost(ConstantsUrl.CHECKOUT);
				httppost.setHeader("X-Api-Key", ConstantsUrl.API_KEY);
				httppost.setHeader("X-Store-Id", ConstantsUrl.STOREID);
				httppost.setHeader("Accept", "application/json");
				httppost.setHeader("Content-type", "application/json");

				StringEntity se;
				se = new StringEntity(mainJsonObject.toString());

				// Set HTTP parameters
				httppost.setEntity(se);

				long t = System.currentTimeMillis();
				HttpResponse response = (HttpResponse) httpclient
						.execute(httppost);
				Log.i("Taggggg",
						"HTTPResponse received in ["
								+ (System.currentTimeMillis() - t) + "ms]");

				HttpEntity entity = response.getEntity();

				if (entity != null) {
					// Read the content stream
					resultString = EntityUtils.toString(entity);



				}

			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return resultString;

		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			System.out.println("response " + result);
			if (result != null) {
				parseCheckoutResponse(result);
			}
			progressDialog.dismiss();
		}
	}

	private static String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	// convert InputStream to String

	private void parseCheckoutResponse(String result) {
		if (result != null) {
			if (store_payment.equals("Braintree")) {

				if (payment_mode.equals("online")) {
					if (result.contains("Delivery Address you put in")) {
						// AppUtiles.getInstance()
						// .showToast(getActivity(),
						// "Location Might be Outside Delivery Area");
						final Dialog dialog = new Dialog(getActivity(),
								R.style.CustomDialog1);
						dialog.setContentView(R.layout.dialog_alert_message);
						dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT,
								LayoutParams.MATCH_PARENT);
						Button cancelBtn = (Button) dialog
								.findViewById(R.id.cancel);
						TextView message = (TextView) dialog
								.findViewById(R.id.message);
						message.setText(result);

						cancelBtn.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub

								try {
									dialog.dismiss();
									//
								} catch (Exception e) {
									e.printStackTrace();
								}

							}
						});

						dialog.show();
					}

					String token=((Global) PayementInformationFragment.this
							.getActivity().getApplicationContext())
							.getCard_token();
					//Log.e("tokennnnnn", token);
					if (token.equals("")||token==null|| token.equals(null)) {
						Intent in = new Intent(getActivity(),
								Checkout_Webview.class);
						in.putExtra("data", result);
						startActivity(in);
						getActivity().finish();
					} else {
						String ajax="<div id=\"b_id\"></div><script src=\"https://code.jquery.com/jquery-latest.min.js\" type=\"text/javascript\"></script><script>    var obj ="+mainJsonObject.toString()+";    console.log(obj);    $.ajax({        type: \"POST\",        url : \""+ConstantsUrl.CHECKOUT+"\",        data: obj ,        success: function(data){ alert(data);           $(\"#b_id\").html(data);        },        failure: function(errMsg) {          alert(errMsg);  window.open('a2m://localhost?json=errMsg');        }    });</script>";
						Intent in = new Intent(getActivity(),
								Checkout_Webview.class);
						in.putExtra("data", ajax);

						startActivity(in);
						getActivity().finish();
					}
				} else {
					JSONObject mainJson;
					try {
						mainJson = new JSONObject(result);

						final String state = mainJson.getString("state");
						String msg = JsonParser
								.getkeyValue_Str(mainJson, "msg");
						// AppUtiles.showToast(getActivity(), msg);

						if (state.equals("1")) {
							final Dialog dialog = new Dialog(getActivity(),
									R.style.CustomDialog1);

							dialog.setContentView(R.layout.dialog_order_successfully);
							RelativeLayout rel=(RelativeLayout)dialog.findViewById(R.id.rel);
							File f = new File("/mnt/sdcard/App2food/"+ConstantsUrl.STORE_BUNDLE_ID+"backround_img.jpg");

							Bitmap bmp = BitmapFactory.decodeFile(f.getAbsolutePath());
							Drawable dr = new BitmapDrawable(bmp);

							rel.setBackgroundDrawable(dr);
							dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT,
									LayoutParams.MATCH_PARENT);
							Button cancelBtn = (Button) dialog
									.findViewById(R.id.cancel);
							TextView message = (TextView) dialog
									.findViewById(R.id.message);
							message.setText(msg);

							cancelBtn.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub

									try {
										dialog.dismiss();
										// if (isClearCart == 2) {
										// DatabaseHelper databaseHelper =
										// DatabaseHelper
										// .newInstance(getActivity());
										// databaseHelper.openDataBase();
										// databaseHelper.clearCart();
										// databaseHelper.close();
										// }

										DatabaseHelper databaseHelper = DatabaseHelper
												.newInstance(getActivity());
										databaseHelper.openDataBase();
										databaseHelper.clearCart();
										databaseHelper.close();
										Intent intent = new Intent(
												getActivity(),
												OrderHistoryActivity.class);
										intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
												| Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
										getActivity().startActivity(intent);
										getActivity().finish();

									} catch (Exception e) {
										e.printStackTrace();
									}

								}
							});

							dialog.show();

						}else{
							final Dialog dialog = new Dialog(getActivity(),

									R.style.CustomDialog1);

							dialog.setContentView(R.layout.dialog_alert_message);
							dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT,
									LayoutParams.MATCH_PARENT);
							Button cancelBtn = (Button) dialog
									.findViewById(R.id.cancel);
							TextView message = (TextView) dialog
									.findViewById(R.id.message);
							message.setText(msg);

							cancelBtn.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub


									dialog.dismiss();


								}
							});

							dialog.show();
						}
					}catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					// if (mainJson.has("state")) {

				}

			} else {
				try {
					JSONObject mainJson = new JSONObject(result);
					// if (mainJson.has("state")) {
					final String state = mainJson.getString("state");
					String msg = JsonParser.getkeyValue_Str(mainJson, "msg");
					// AppUtiles.showToast(getActivity(), msg);
					if (state.equals("1")) {
						final Dialog dialog = new Dialog(getActivity(),
								R.style.CustomDialog1);

						dialog.setContentView(R.layout.dialog_order_successfully);
						RelativeLayout rel=(RelativeLayout)dialog.findViewById(R.id.rel);
						File f = new File("/mnt/sdcard/App2food/"+ConstantsUrl.STORE_BUNDLE_ID+"backround_img.jpg");

						Bitmap bmp = BitmapFactory.decodeFile(f.getAbsolutePath());
						Drawable dr = new BitmapDrawable(bmp);

						rel.setBackgroundDrawable(dr);
						dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT,
								LayoutParams.MATCH_PARENT);
						Button cancelBtn = (Button) dialog
								.findViewById(R.id.cancel);
						TextView message = (TextView) dialog
								.findViewById(R.id.message);
						message.setText(msg);

						cancelBtn.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub

								try {
									dialog.dismiss();


									DatabaseHelper databaseHelper = DatabaseHelper
											.newInstance(getActivity());
									databaseHelper.openDataBase();
									databaseHelper.clearCart();
									databaseHelper.close();
									Intent intent = new Intent(
											getActivity(),
											OrderHistoryActivity.class);
									intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
											| Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
									getActivity().startActivity(intent);
									getActivity().finish();

								} catch (Exception e) {
									e.printStackTrace();
								}

							}
						});

						dialog.show();

					}else{
						final Dialog dialog = new Dialog(getActivity(),

								R.style.CustomDialog1);

						dialog.setContentView(R.layout.dialog_alert_message);
						dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT,
								LayoutParams.MATCH_PARENT);
						Button cancelBtn = (Button) dialog
								.findViewById(R.id.cancel);
						TextView message = (TextView) dialog
								.findViewById(R.id.message);
						message.setText(msg);

						cancelBtn.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub


								dialog.dismiss();



							}
						});

						dialog.show();
					}
				}catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} else {
			AppUtiles
					.getInstance()
					.showToast(getView(),
							"Sorry for the Inconvenience your Order is fail due to some reason.");
		}

	}

	private class CalculateDiscount extends AsyncTask<String[], Void, String> {
		// private String couponCodeStr = "";
		// private boolean status;

		// public SendDataOnServer(String cCode, boolean guestStatus) {
		// // TODO Auto-generated constructor stub
		// couponCodeStr = cCode;
		// status = guestStatus;
		// }
		private Button submit;
		private ProgressBar mProgress;
		public String resultString;
		private int dilogflag;
		public CalculateDiscount(Button submit) {
			// TODO Auto-generated constructor stub
			this.submit = submit;

		}
		public CalculateDiscount(int dilogflag)
		{
			this.dilogflag=dilogflag;
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

			// commented for testing
			progressDialog = new ACProgressCustom.Builder(getActivity())
					.useImages(R.drawable.loading1, R.drawable.loading2,R.drawable.loading3, R.drawable.loading4,R.drawable.loading5,R.drawable.loading6).build();
			progressDialog.setCanceledOnTouchOutside(true);
			progressDialog.show();
		}

		@SuppressWarnings("deprecation")
		@Override
		protected String doInBackground(String[]... params) {
			// TODO Auto-generated method stub

			try {
				DefaultHttpClient client = new DefaultHttpClient();
				HostnameVerifier verifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
				SchemeRegistry registry = new SchemeRegistry();
				SSLSocketFactory socketfactory = SSLSocketFactory
						.getSocketFactory();
				socketfactory
						.setHostnameVerifier((X509HostnameVerifier) verifier);
				registry.register(new Scheme("https", socketfactory, 443));
				SingleClientConnManager mgr = new SingleClientConnManager(
						client.getParams(), registry);
				HttpsURLConnection.setDefaultHostnameVerifier(verifier);
				HttpClient httpc = new DefaultHttpClient();
				HttpPost post = new HttpPost(ConstantsUrl.APPLYCOUPONCODE);
				post.setHeader("X-Api-Key", ConstantsUrl.API_KEY);
				post.setHeader("X-Store-Id", ConstantsUrl.STOREID);
				post.setHeader("Accept", "application/json");
				post.setHeader("Content-type", "application/json");
				ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
				list.add(new BasicNameValuePair("store_id",
						ConstantsUrl.STOREID));
				post.setEntity(new UrlEncodedFormEntity(list));
				StringEntity se;

				se = new StringEntity(DiscountJson.toString());

				post.setEntity(se);
				long t = System.currentTimeMillis();
				HttpResponse response = (HttpResponse) client.execute(post);
				Log.i("Taggggg",
						"HTTPResponse received in ["
								+ (System.currentTimeMillis() - t) + "ms]");

				HttpEntity entity = response.getEntity();

				if (entity != null) {
					// Read the content stream
					resultString = EntityUtils.toString(entity);
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.e("resulttt", resultString);
			return resultString;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			// AppUtiles.getInstance().parseResponse(result, getActivity(),
			// mAppPrefs,
			// expressDialog, 2, couponCodeStr, status);
			// mImg.setVisibility(View.VISIBLE);
			// mProgress.setVisibility(View.GONE);

			progressDialog.dismiss();

			parseDiscountResponse(result,this.dilogflag);
		}
	}

	private void parseDiscountResponse(String response,int dilogflag) {
		if (response != null) {
			try {
				//discountAmt="0";new_product_total="0";
				JSONObject mainJson = new JSONObject(response);
				if (mainJson.has("state")) {
					String state = mainJson.getString("state");
					String msg = mainJson.getString("msg");

					if (state != null && state.equals("1")) {
						if (mainJson.has("data")) {
							JSONObject dataJson = mainJson
									.getJSONObject("data");
							JSONObject alldis = dataJson
									.getJSONObject("allDiscount");
							discount_code = JsonParser.getkeyValue_Str(
									dataJson, "vouchercode");
							double voucher_discount = JsonParser
									.getkeyValue_Double(alldis,
											"voucherdiscount");
							double shipping_dis = JsonParser
									.getkeyValue_Double(alldis, "shipping");
							double cart_dis = JsonParser.getkeyValue_Double(
									alldis, "discount");
							voucher_discount += shipping_dis + cart_dis;
							discountAmt = String.valueOf(voucher_discount);
							String oldProductTotal = JsonParser
									.getkeyValue_Str(alldis, "producttotal");
							new_product_total = JsonParser.getkeyValue_Str(
									alldis, "newproducttotal");
							final Dialog dialog = new Dialog(getActivity(),
									R.style.CustomDialog1);
							dialog.setContentView(R.layout.dialog_alert_message);
							dialog.getWindow().setLayout(
									LayoutParams.MATCH_PARENT,
									LayoutParams.MATCH_PARENT);
							Button cancelBtn = (Button) dialog
									.findViewById(R.id.cancel);
							cancelBtn.setBackgroundColor(Color
									.parseColor(ConstantsUrl.STORE_COLOR_CODE));
							TextView message = (TextView) dialog
									.findViewById(R.id.message);
							message.setText("You Have New Product Total:"
									+ new_product_total);
							if(dilogflag!=1)
							{
								dialog.show();
							}
							else {
								calculateTotal();
							}
							// Float discount= Float.parseFloat(discountAmt);
							// DecimalFormat format= new DecimalFormat("0.00");
							// format.setMaximumFractionDigits(2);
							// discountAmt=format.format(discount);

							//
							// cancelBtn.setOnClickListener(new
							// OnClickListener() {
							//
							// @Override
							// public void onClick(View v) {
							// // TODO Auto-generated method stub
							// calculateTotal();
							// dialog.dismiss();
							// }
							// });
							cancelBtn.setOnTouchListener(new OnTouchListener() {

								@Override
								public boolean onTouch(View v, MotionEvent event) {
									// TODO Auto-generated method stub
									dialog.dismiss();
									calculateTotal();
									return true;
								}
							});
						}
					} else {
						AppUtiles
								.getInstance()
								.showToast(getView(),
										"You Have Enter Wrong Voucher code,Please Try Again With Valid Code");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void calculateTotal() {

		BigDecimal discount, tip = null;
		BigDecimal total = null, delivery_amt;
		String totalamt = totalAmount.getText().toString();
		totalamt = totalamt.substring(1);
		delivery_amount = ((Global) PayementInformationFragment.this
				.getActivity().getApplication()).getDelivery_charge();
		String order_tip = order_driver_tip.getText().toString();
		String vat_text = total_tax.getText().toString();
		vat_text = vat_text.substring(1);
		order_tip = order_tip.substring(2);
		BigDecimal subtotal = new BigDecimal(
				((Global) PayementInformationFragment.this.getActivity()
						.getApplicationContext()).getTotalamout_without_text());
		try {
			if (order_tip.equals("") || order_tip == null) {

				tip = new BigDecimal("0.00");

			} else {
				tip = new BigDecimal(order_tip);
				tip = tip.setScale(2, BigDecimal.ROUND_HALF_UP);

			}
			if (discountAmt == null || discountAmt.equals("")) {
				discount = new BigDecimal("0.00");

			} else {
				discount = new BigDecimal(discountAmt);
				discount=discount.setScale(2,BigDecimal.ROUND_HALF_UP);

			}
			if (delivery_amount == null || delivery_amount.equals("")) {
				delivery_amt = new BigDecimal("0.00");
				delivery_amt = delivery_amt.setScale(2,
						BigDecimal.ROUND_HALF_UP);

			} else {
				delivery_amt = new BigDecimal(
						((Global) PayementInformationFragment.this
								.getActivity().getApplicationContext())
								.getDelivery_charge());
				delivery_amt = delivery_amt.setScale(2,
						BigDecimal.ROUND_HALF_UP);

			}
			BigDecimal vat = new BigDecimal(
					((Global) PayementInformationFragment.this.getActivity()
							.getApplicationContext()).getSales_tax());

			total = subtotal.add(tip).add(vat).subtract(discount)
					.add(delivery_amt);

			this.discount.setText("$ " + discount.toString());
			this.delivery_charge.setText("$ " + delivery_amt.toString());
			this.tip.setText("$ " + tip.toString());
			total_with_tax.setText(getString(R.string.usd)
					+ " "+total.toString());
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public class GetDelvieryCharges extends AsyncTask<String[], Void, String> {
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
			return JsonParser.Webserice_Call_URL(ConstantsUrl.GET_DISTANCE,
					params[0], params[1]);
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Log.e("resulttt distance", result);
			progressDialog.dismiss();
			try {
				JSONObject mainobj = new JSONObject(result);
				if (mainobj.has("state")) {

					String state = mainobj.getString("state");
					if (state.equals("1")) {
						Context ctx = getActivity();
						JSONObject data = mainobj.getJSONObject("data");
						String distanceMiles = data.getString("distanceMiles");
						HashMap<Integer, Restaurant_Miles_Charge> miles_hash_map = ((Global) PayementInformationFragment.this
								.getActivity().getApplication())
								.getMil_charge();
						for (int i = 0; i < miles_hash_map.size(); i++) {
							Restaurant_Miles_Charge milles = miles_hash_map
									.get(i);
							String getmiles = milles.getMiles();
							if (getmiles.equals(distanceMiles)) {
								((Global) PayementInformationFragment.this
										.getActivity().getApplication())
										.setDelivery_charge(milles.getCharge());
								Log.e("success",
										"success"
												+ ((Global) PayementInformationFragment.this
												.getActivity()
												.getApplication())
												.getDelivery_charge());
								break;
							} else {
								((Global) PayementInformationFragment.this
										.getActivity().getApplication())
										.setDelivery_charge("");
								Log.e("fail", "fail");
							}

						}
						if(((Global) PayementInformationFragment.this
								.getActivity().getApplication())
								.getDelivery_charge().equals("")){
							final Dialog dialog = new Dialog(getActivity(),
									R.style.CustomDialog1);
							dialog.setContentView(R.layout.dialog_alert_message);
							dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT,
									LayoutParams.MATCH_PARENT);
							Button cancelBtn = (Button) dialog
									.findViewById(R.id.cancel);
							TextView message = (TextView) dialog
									.findViewById(R.id.message);
							message.setText("Delivery Address Might Be Outside Delivery Area.");

							cancelBtn.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub

									dialog.dismiss();


								}
							});

							dialog.show();
						}

						// AppUtiles.getInstance().showToast(getContext(),
						// "Delivery Charge"+charge);
						// Toast.makeText(ctx, "Delivery Charge"+charge,
						// 3).show();

						// onSaveListerner.onValueSave(streetStr, cityStr,
						// stateStr,
						// zipCodeStr, phoneStr, stateCode, cityCode,
						// localityId,
						// storeDeliverableId, locaionName,delivery_charge);

					} else {
						String msg = mainobj.getString("msg");
						// Toast.makeText(getActivity(), msg, 3).show();
						// onSaveListerner.onValueSave(streetStr, cityStr,
						// stateStr,
						// zipCodeStr, phoneStr, stateCode, cityCode,
						// localityId,
						// storeDeliverableId, locaionName,"");
						final Dialog dialog = new Dialog(getActivity(),
								R.style.CustomDialog1);
						dialog.setContentView(R.layout.dialog_alert_message);
						dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT,
								LayoutParams.MATCH_PARENT);
						Button cancelBtn = (Button) dialog
								.findViewById(R.id.cancel);
						TextView message = (TextView) dialog
								.findViewById(R.id.message);
						message.setText("Delivery Address Might Be Outside Delivery Area.");

						cancelBtn.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub

								dialog.dismiss();


							}
						});

						dialog.show();
					}

				}
				calculateTotal();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void checkout_function() {
		String usertype;
		if (mAppPrefs.getBoolean(ConstantsUrl.IS_LOGIN, false)) {
			usertype = "registered";
		} else {
			usertype = "guest";
		}

		String custId = "";
		int isLogin = 0;
		if (mAppPrefs.getBoolean(ConstantsUrl.IS_LOGIN, false)) {
			custId = mAppPrefs.getString(ConstantsUrl.CUSTOMER_ID, "");

			isLogin = 1;
		}
		try {
			mainJsonObject = new JSONObject();
			mainJsonObject.put("scheme",
					getResources().getString(R.string.scheme1));

			JSONObject billingJson = new JSONObject();

			// billingJson.put("bill_region",
			// billingStateCodeStr);
			// billingJson.put("bill_street", billingStreetStr);
			// billingJson.put("bill_telephone", billingPhone);
			if (payment_mode.equalsIgnoreCase("cod")
					& ((Global) PayementInformationFragment.this.getActivity()
					.getApplicationContext()).getDelivery_type()
					.equalsIgnoreCase("pickup")) {
				mainJsonObject.put("Billing", "");
			} else {
				billingJson.put("bill_city_id",
						((Global) PayementInformationFragment.this
								.getActivity().getApplicationContext())
								.getDelivery_city_code());

				billingJson.put("bill_street",
						((Global) PayementInformationFragment.this
								.getActivity().getApplicationContext())
								.getDelivery_street());
				billingJson.put("bill_city_name",
						((Global) PayementInformationFragment.this
								.getActivity().getApplicationContext())
								.getDelivery_city());
				billingJson.put("bill_state_code",
						((Global) PayementInformationFragment.this
								.getActivity().getApplicationContext())
								.getDelivery_state_code());
				// billingJson.put("bill_country", "US");
				billingJson.put("bill_pincode",
						((Global) PayementInformationFragment.this
								.getActivity().getApplicationContext())
								.getDelivery_pincode());
				mainJsonObject.put("Billing", billingJson);
			}
			JSONObject shippingJson = new JSONObject();
			shippingJson.put("ship_street", billing_street);
			shippingJson.put("ship_city_id", billing_city_code);
			shippingJson.put("ship_pincode", billing_code);
			shippingJson.put("ship_city_name", billing_city);

			shippingJson.put("ship_state_code", billing_state_code);

			if (((Global) PayementInformationFragment.this.getActivity()
					.getApplicationContext()).getDelivery_type()
					.equalsIgnoreCase("pickup")) {
				mainJsonObject.put("shiping", "");

			} else {

				mainJsonObject.put("shiping", shippingJson);
			}
			cc_exp_month = ((Global) PayementInformationFragment.this
					.getActivity().getApplicationContext()).getMonth();
			cc_number = ((Global) PayementInformationFragment.this
					.getActivity().getApplicationContext()).getCard_number();
			cc_exp_year = ((Global) PayementInformationFragment.this
					.getActivity().getApplicationContext()).getYear();
			cc_type = ((Global) PayementInformationFragment.this.getActivity()
					.getApplicationContext()).getCard_type();
			cc_cvv = ((Global) PayementInformationFragment.this.getActivity()
					.getApplicationContext()).getCvv();

			mainJsonObject.put("cc_exp_month", cc_exp_month);
			mainJsonObject.put("cc_exp_year", cc_exp_year);
			mainJsonObject.put("cc_number", cc_number);
			mainJsonObject.put("cc_type", cc_type);
			// mainJsonObject.put("cc_exp_year",
			// String.valueOf(selectedYear));
			mainJsonObject.put("ccode", cc_cvv);
			mainJsonObject.put("currency_code", "USD");
			mainJsonObject.put("cust_id", custId);
			String order_tip = order_driver_tip.getText().toString();
			order_tip = order_tip.substring(2);
			mainJsonObject.put("tip", order_tip);
			mainJsonObject.put("login", isLogin);
			mainJsonObject.put("email", email);
			mainJsonObject.put("firstname", intent_name);
			mainJsonObject.put("lastname", last_name);
			mainJsonObject.put("phone", phone_no);
			mainJsonObject.put("otype",
					((Global) PayementInformationFragment.this.getActivity()
							.getApplicationContext()).getDelivery_type()
							.toLowerCase());
			mainJsonObject.put("store_id", ConstantsUrl.STOREID);

			mainJsonObject.put("order_delivery_instructions",
					((Global) PayementInformationFragment.this.getActivity()
							.getApplicationContext()).getSpecial_instruction());
			mainJsonObject.put("device_id", regid);

			mainJsonObject.put("order_delivery_time",
					((Global) PayementInformationFragment.this.getActivity()
							.getApplicationContext()).getDelvery_time());
			mainJsonObject.put("order_payment_mode", payment_mode);
			mainJsonObject.put("vouchercode", coupan_code.getText().toString());
			mainJsonObject.put("device_type", "android");
			mainJsonObject.put("cc_token",
					((Global) PayementInformationFragment.this.getActivity()
							.getApplicationContext()).getCard_token());

			// mainJsonObject.put("param", usertype);
			// mainJsonObject.put("ptype", "authorizenet");
			// mainJsonObject.put("session", "");
			// mainJsonObject.put("discount", "0");
			// mainJsonObject.put("ip_address", "192.163.1.1");
			if (GCMUtiles.getInstance().checkPlayServices(getActivity())) {
				gcm = GoogleCloudMessaging.getInstance(getActivity());
				regid = GCMUtiles.getInstance()
						.getRegistrationId(getActivity());
				mainJsonObject.put("device_id", regid);

				// regid = gcm
				// .register(ConstantsUrl.PUSHNOTIFICATION_PROJECTID);
			}
			mainJsonObject.put("cart", new JSONArray(
					((Global) PayementInformationFragment.this.getActivity()
							.getApplicationContext()).getProduct_cart()));
			System.out.println("main json Object " + mainJsonObject);
			new SendCheckoutAsync().execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
