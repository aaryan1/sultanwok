package com.app2mobile.Sultanwok;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import com.app2mobile.metadata.CheckOutMetadata;
import com.app2mobile.metadata.RestaurantDeliveryTimeMetadata;
import com.app2mobile.metadata.RestaurantDetailMetadata;
import com.app2mobile.metadata.RestaurantMetadata;
import com.app2mobile.utiles.ConstantsUrl;
import com.app2mobile.utiles.RangeTimePickerDialog;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

public class PickupnDeliveryDialog extends Activity {
	ToggleButton pickup, delivery;
	String delivery_time,pickup_time,businessHrStr, product;
	TimePicker pickup_timing, delivery_timing;
	TextView choose,circle,mCartCountTxt,mTitleTxt;
	CheckOutMetadata checkout;
	private HashMap<String, RestaurantDeliveryTimeMetadata> restaurantDeliveryTimeList;
	RelativeLayout footer;
	View view1;
	RadioButton delivery_radio,pickup_radio,now,later;
	  String delivery_status;
	GregorianCalendar finalTime, mCalendarOpeningTime, mCalendarClosingTime;
	  private int mHour,mMinute; 
	  SagmentRadioGroup1 segmentText,segment;
	Calendar calender;
	 String strDat,strDate;
	RestaurantMetadata data;
	private RestaurantDetailMetadata restaurantDetailData;
	  StringBuilder sb;
	  private GoogleMap googleMap;
	  RelativeLayout main_layout;
	  ImageView mCartImg;
	  RangeTimePickerDialog timepicker;
	@SuppressLint("NewApi") @SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_pickupndelivery);
		Intent in= this.getIntent();
		if(in.getExtras() != null){
		 data= (RestaurantMetadata) this.getIntent()
		.getExtras().get("restaurant");
		}
		RelativeLayout rel= (RelativeLayout)findViewById(R.id.rel_map);
		rel.bringChildToFront(footer);
		Window window =getWindow();

		// clear FLAG_TRANSLUCENT_STATUS flag:
		window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

		// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
		window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

		int currentapiVersion = android.os.Build.VERSION.SDK_INT;

		if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP){
		    // Do something for lollipop and above versions
			window.setStatusBarColor(Color.parseColor("#000000"));
			window.setNavigationBarColor(Color.parseColor("#000000"));
		} 
		circle=(TextView)findViewById(R.id.circle);
		mCartCountTxt = (TextView) findViewById(R.id.cartItems);
		mCartImg = (ImageView) findViewById(R.id.menuImage);
		mTitleTxt = (TextView) findViewById(R.id.title);
		mTitleTxt.setText("Order Option");
		mTitleTxt.setTextColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));
		circle.setVisibility(View.INVISIBLE);
		mCartCountTxt.setVisibility(View.INVISIBLE);
		mCartImg.setVisibility(View.INVISIBLE);
		//Typeface tf= Typeface.createFromAsset(getAssets(), "OpenSans-Italic.ttf");
		finalTime = new GregorianCalendar();
		setOpeningAndClosingTimes();
		segmentText = (SagmentRadioGroup1)findViewById(R.id.segment_text);
		segment= (SagmentRadioGroup1)findViewById(R.id.segment);
		footer=(RelativeLayout)findViewById(R.id.footer);
		choose=(TextView)findViewById(R.id.choose);
		delivery_radio=(RadioButton)findViewById(R.id.button_one);
	      pickup_radio=(RadioButton)findViewById(R.id.button_two);
	      later=(RadioButton)findViewById(R.id.later);
	      now=(RadioButton)findViewById(R.id.now);
	      main_layout=(RelativeLayout)findViewById(R.id.rel);
	      Typeface tf = Typeface.createFromAsset(getAssets(),
					"OpenSans-Light.ttf");
	      choose.setTypeface(tf);
	      File f = new File("/mnt/sdcard/App2food/"+ConstantsUrl.STORE_BUNDLE_ID+"backround_img.jpg");
			
			Bitmap bmp = BitmapFactory.decodeFile(f.getAbsolutePath());
			Drawable dr = new BitmapDrawable(bmp);
			
			main_layout.setBackgroundDrawable(dr);
	      googleMap=((MapFragment)getFragmentManager().findFragmentById(
                  R.id.map)).getMap();
	      MarkerOptions marker = new MarkerOptions().position(new LatLng(ConstantsUrl.LATITUDE, ConstantsUrl.LONGITUDE));
	      CameraPosition cameraPosition = new CameraPosition.Builder().target(
	                new LatLng(ConstantsUrl.LATITUDE, ConstantsUrl.LONGITUDE)).zoom(15).build();
	      googleMap.addMarker(marker);
	      googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
	      footer.setBackgroundColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));
		final Calendar c = Calendar.getInstance();
		 mHour = finalTime.get(Calendar.HOUR_OF_DAY);
        mMinute = finalTime.get(Calendar.MINUTE);
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
		c.add(Calendar.MINUTE, 45);
		strDate=sdf.format(c.getTime());
		 strDat = sdf.format(c.getTime());

	
		if(!((Global)PickupnDeliveryDialog.this.getApplication()).getPicuplist().contains("delivery")){
			delivery_radio.setVisibility(View.INVISIBLE);
			((Global)PickupnDeliveryDialog.this.getApplicationContext()).setDelivery_type("Pickup");
		pickup_radio.setSelected(true);
		((Global)PickupnDeliveryDialog.this.getApplicationContext()).setDelvery_time(strDat);
	}else{
		if(((Global)PickupnDeliveryDialog.this.getApplicationContext()).getDelivery_type()==""){
			
	          delivery_status= "delivery";
	        // sb.append(strDat);
				choose.setText("delivery @ "+strDate);
				((Global)PickupnDeliveryDialog.this.getApplicationContext()).setDelivery_type("delivery");
				((Global)PickupnDeliveryDialog.this.getApplicationContext()).setDelvery_time(strDat);
				System.out.println(((Global)PickupnDeliveryDialog.this.getApplicationContext()).getDelvery_time());
	      
	      delivery_radio.setBackgroundColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));
	      pickup_radio.setTextColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));
	      later.setTextColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));
			now.setBackgroundColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));
			delivery_radio.setTextColor(Color.WHITE);

	      now.setTextColor(Color.WHITE);
			}
	}
		
//if(((Global)PickupnDeliveryDialog.this.getApplication()).getPicuplist().contains("pickup")){
//	
//	}
		// strDate = finalTime.get(Calendar.HOUR_OF_DAY)  + ":"+ finalTime.get(Calendar.MINUTE) + ":" + finalTime.get(Calendar.SECOND);
		
		if(((Global)PickupnDeliveryDialog.this.getApplicationContext()).getDelivery_type()=="Pickup"){
			//pickup_radio.setSelected(true);
			segmentText.check(R.id.button_two);
			pickup_radio.setTextColor(Color.WHITE);
			delivery_radio.setTextColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));
			
			pickup_radio.setBackgroundColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));
			delivery_radio.setBackgroundColor(Color.WHITE);
//			pickup_radio.setText("Pick Up");
//			delivery_radio.setText("Delivery");
			
			later.setTextColor(Color.WHITE);
			later.setBackgroundColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));
			choose.setText("pickup @"+((Global)PickupnDeliveryDialog.this.getApplicationContext()).getDelvery_time());
			now.setBackgroundColor(Color.WHITE);
			now.setTextColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));
		}
		else{
			 delivery_status= "Delivery";
		        // sb.append(strDat);
					choose.setText("Delivery @"+strDate);
					((Global)PickupnDeliveryDialog.this.getApplicationContext()).setDelivery_type("Delivery");
					((Global)PickupnDeliveryDialog.this.getApplicationContext()).setDelvery_time(strDat);
					System.out.println(((Global)PickupnDeliveryDialog.this.getApplicationContext()).getDelvery_time());
		      
		      delivery_radio.setBackgroundColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));
					delivery_radio.setTextColor(Color.WHITE);
		      pickup_radio.setTextColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));
		      later.setTextColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));
				now.setBackgroundColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));
				

		      now.setTextColor(Color.WHITE);

		}
      
	
      delivery_radio.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		     // delivery_radio.setBackgroundColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));
			
			((Global)PickupnDeliveryDialog.this.getApplicationContext()).setDelivery_type("Delivery");
			 delivery_status= ((Global)PickupnDeliveryDialog.this.getApplicationContext()).getDelivery_type();
			 choose.setText("Delivery @ "+strDate);
		}
	});
      pickup_radio.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			((Global)PickupnDeliveryDialog.this.getApplicationContext()).setDelivery_charge("");

			((Global)PickupnDeliveryDialog.this.getApplicationContext()).setDelivery_type("Pickup");
			 delivery_status= ((Global)PickupnDeliveryDialog.this.getApplicationContext()).getDelivery_type();
			 choose.setText(delivery_status+" @"+strDat);

		}
	});
      now.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			now.setTextColor(Color.WHITE);
			later.setBackgroundColor(Color.WHITE);
			later.setTextColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));
			now.setBackgroundColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));
			if(segmentText.getCheckedRadioButtonId()==R.id.button_one){
				System.out.println("dfadsfasdfadsfsd");
				delivery_status= "Delivery";
				((Global)PickupnDeliveryDialog.this.getApplicationContext()).setDelivery_type("Delivery");
				((Global)PickupnDeliveryDialog.this.getApplicationContext()).setDelvery_time(strDate);
				 delivery_status= ((Global)PickupnDeliveryDialog.this.getApplicationContext()).getDelivery_type();
				
				 choose.setText(delivery_status+" @ "+strDate);
			}
			if(segmentText.getCheckedRadioButtonId()==R.id.button_two){
				delivery_status= "Pickup";
				((Global)PickupnDeliveryDialog.this.getApplicationContext()).setDelivery_charge("");
				((Global)PickupnDeliveryDialog.this.getApplicationContext()).setDelivery_type("Pickup");
				((Global)PickupnDeliveryDialog.this.getApplicationContext()).setDelvery_time(strDat);
				 delivery_status= ((Global)PickupnDeliveryDialog.this.getApplicationContext()).getDelivery_type();
				 choose.setText(delivery_status+" @ "+strDate);
			}
		}
	});
      
      
      later.setOnClickListener(new OnClickListener() {
    	
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//later.setBackgroundColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));
			
			 GregorianCalendar calender = new GregorianCalendar();
			 timepicker= new RangeTimePickerDialog(PickupnDeliveryDialog.this,new TimePickerDialog.OnTimeSetListener() {
				
				@Override
				public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute ) {
					// TODO Auto-generated method stub
					finalTime.set(Calendar.HOUR_OF_DAY, selectedHour);
					finalTime.set(Calendar.MINUTE, selectedMinute);
					  
					  
					GregorianCalendar c = new GregorianCalendar();
					GregorianCalendar currentCalender = new GregorianCalendar();
					//currentCalender.add(Calendar.MINUTE, 1);
					if(finalTime.get(Calendar.HOUR_OF_DAY)==c.get(Calendar.HOUR_OF_DAY) && finalTime.get(Calendar.MINUTE)<=c.get(Calendar.MINUTE)){
						finalTime.add(Calendar.MINUTE, 15);
					}
					if (currentCalender.before(finalTime)) {
						
						Log.e("timeeee", finalTime  +"  "+mCalendarOpeningTime.get(Calendar.HOUR_OF_DAY)+"  "+mCalendarClosingTime.get(Calendar.HOUR_OF_DAY));
						if (finalTime.after(mCalendarOpeningTime)
								&& finalTime
										.before(mCalendarClosingTime)) {

							choose.setText(new SimpleDateFormat(
									"h:mm aaa").format(finalTime
									.getTime()));
							
							  
		 	                  if(selectedHour>=12){ 
		 	                	 sb = new StringBuilder();
		 	                      sb.append(selectedHour-12).append( ":" ).append(selectedMinute).append(" PM");
		 	                  }else{
		 	                	 sb = new StringBuilder();
		 	                      sb.append(selectedHour).append( ":" ).append(selectedMinute).append(" AM");
		 	                  }
		 	                  strDat=sb.toString();
		 	             	choose.setText(delivery_status+" @ "+strDat);
		 	             	((Global)PickupnDeliveryDialog.this.getApplicationContext()).setDelvery_time(String.valueOf(sb));
						//	timeLayout.setErrorEnabled(false);

						} else if( finalTime.before(mCalendarOpeningTime)){
							choose.setText(delivery_status+" @ "+new SimpleDateFormat(
									"h:mm aaa").format(mCalendarOpeningTime
									.getTime()));
		 	             	((Global)PickupnDeliveryDialog.this.getApplicationContext()).setDelvery_time(mCalendarOpeningTime
									.getTime().toString());

						}
						else {
//							timeLayout.setErrorEnabled(true);
//							timeLayout
//									.setError(getString(R.string.delivery_time_required));
							AlertDialog dialog= new AlertDialog.Builder(PickupnDeliveryDialog.this).create();
							dialog.setTitle("Select a valid time");
							dialog.setMessage("Select delivery time in business hours.");
							dialog.setCancelable(false);
							
							dialog.setButton("OK", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									dialog.dismiss();
								}
							});
							dialog.show();
							// AppUtiles.getInstance()
							// .showToast(
							// getActivity(),
							// getString(R.string.delivery_time_required));
							choose.setText("");
							strDat=null;

						}

					} else {
//						
						// AppUtiles.getInstance()
						// .showToast(
						// getActivity(),
						// getString(R.string.restaurant_closed));
						AlertDialog dialog= new AlertDialog.Builder(PickupnDeliveryDialog.this).create();
						dialog.setTitle("Select a valid time");
						dialog.setMessage("Select time after current time.");
						dialog.setCancelable(false);
						
						dialog.setButton("OK", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								dialog.dismiss();
							}
						});
						dialog.show();
						choose.setText("");
						strDat=null;
					}
					
				
			}
			} , mHour, mMinute, false);
			timepicker.setTitle("Select Time");
			 GregorianCalendar cal = new GregorianCalendar();
				//cal.add(Calendar.MINUTE, 1); 
				cal.set(Calendar.HOUR_OF_DAY, finalTime.HOUR_OF_DAY);
				 Calendar cal1 = Calendar.getInstance();
				
			 if(finalTime.before(mCalendarClosingTime)){
			
				 timepicker.setMin(calender.get(Calendar.HOUR_OF_DAY),calender.get(Calendar.MINUTE));
				 timepicker.setMax(
							mCalendarClosingTime.get(Calendar.HOUR_OF_DAY),
							mCalendarClosingTime.get(Calendar.MINUTE));
			 }
			
				 else if(finalTime.after(mCalendarOpeningTime))
					 {
					 timepicker.setMin(
								mCalendarOpeningTime.get(Calendar.HOUR_OF_DAY),
								mCalendarOpeningTime.get(Calendar.MINUTE));
					 timepicker.setMax(
							 calender.get(Calendar.HOUR_OF_DAY),calender.get(Calendar.MINUTE));
					 }else{
			timepicker.setMin(
					mCalendarOpeningTime.get(Calendar.HOUR_OF_DAY),
					mCalendarOpeningTime.get(Calendar.MINUTE));
			timepicker.setMax(
					mCalendarClosingTime.get(Calendar.HOUR_OF_DAY),
					mCalendarClosingTime.get(Calendar.MINUTE));
			
			 }
			timepicker.show();
			
		}
	});
      
      segmentText.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			
				if(checkedId==R.id.button_one){
					System.out.println("button oneeeeee");
					delivery_radio.setTextColor(Color.WHITE);
					pickup_radio.setBackgroundColor(Color.WHITE);
					delivery_radio.setBackgroundColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));
					pickup_radio.setTextColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));

					((Global)PickupnDeliveryDialog.this.getApplicationContext()).setDelivery_type("Delivery");
					 delivery_status= ((Global)PickupnDeliveryDialog.this.getApplicationContext()).getDelivery_type();
					 choose.setText(delivery_status+" @ "+strDat);
				}
				if(checkedId==R.id.button_two){
					pickup_radio.setTextColor(Color.WHITE);
					delivery_radio.setTextColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));
					pickup_radio.setBackgroundColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));
					delivery_radio.setBackgroundColor(Color.WHITE);
					((Global)PickupnDeliveryDialog.this.getApplicationContext()).setDelivery_charge("");
					((Global)PickupnDeliveryDialog.this.getApplicationContext()).setDelivery_type("Pickup");
					 delivery_status= ((Global)PickupnDeliveryDialog.this.getApplicationContext()).getDelivery_type();
					 choose.setText(delivery_status+" @ "+strDat);
				}
			
		}
	});
      segment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			delivery_status=((Global)PickupnDeliveryDialog.this.getApplicationContext()).getDelivery_type();
			
			Calendar c = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
			c.add(Calendar.MINUTE, 45);
			
			 //strDate = c.get(Calendar.HOUR_OF_DAY)  + ":"+ c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND);
			 //strDat = sdf.format(c.getTime());
			if(checkedId==R.id.now){
				
				now.setTextColor(Color.WHITE);
				later.setBackgroundColor(Color.WHITE);
				later.setTextColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));
				now.setBackgroundColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));
				
				choose.setText(delivery_status+" @ "+strDate);
				((Global)PickupnDeliveryDialog.this.getApplicationContext()).setDelvery_time(strDate);

			}else{
				choose.setText(delivery_status+" @ "+strDat);
				((Global)PickupnDeliveryDialog.this.getApplicationContext()).setDelvery_time(strDat);
				later.setTextColor(Color.WHITE);
				now.setBackgroundColor(Color.WHITE);

				now.setTextColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));
				later.setBackgroundColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));
				}
				
				
			}
		
	});

         footer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(data!=null){
					if(strDat==null || strDat.equals("")){
					//	AppUtiles.getInstance().showToast(getCurrentFocus(), "Please Select Time");
						Toast.makeText(getApplicationContext(), "Please Select Time", 3).show();
					}else{
						
						if(segment.getCheckedRadioButtonId()==R.id.now)
						{
							GregorianCalendar cal = new GregorianCalendar();
							cal.add(Calendar.MINUTE, 15);
						Log.e("timee", mCalendarOpeningTime.getTime().toString());
						Log.e("timee", ""+mCalendarOpeningTime.get(Calendar.HOUR));
						if( cal.after(mCalendarOpeningTime)
								&& cal
								.before(mCalendarClosingTime)){
				Intent in= new Intent(PickupnDeliveryDialog.this,CategoryActivity.class);
				in.putExtra("restaurant", data);
				startActivity(in);
				finish();
						}
						else{
							Toast.makeText(getApplicationContext(), "Please Enter Valid Time", 3).show();
						}
						}else{
						//	AppUtiles.getInstance().showToast(getCurrentFocus(), "Please Enter Valid Time");
							
							Intent in= new Intent(PickupnDeliveryDialog.this,CategoryActivity.class);
							in.putExtra("restaurant", data);
							startActivity(in);
							finish();
						}
						
						
					}
					}
				else
				{
//					Intent intent = new Intent();
//					//intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
//					intent.setAction("com.example.Broadcast");
//					intent.putExtra("time", strDat);
//					sendBroadcast(intent);
					finish();
				}
				
			}
		});
        
        
	
	}
	 @Override
     public void onResume() {
     	// TODO Auto-generated method stub
     	super.onResume();
     	Global.getInstance().trackScreenView("Order Option");
     }
	private void setOpeningAndClosingTimes() {
		SimpleDateFormat dayFormat = new SimpleDateFormat("hh:mm a");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

//		Gson gson = new Gson();
//			
//		String restaurantDetail = BaseActivity.mRestaurantDetailPrefs
//				.getString(ConstantsUrl.RESTAURANT_DETAILS, "");
//		restaurantDetailData = gson.fromJson(restaurantDetail,
//				RestaurantDetailMetadata.class);
		
	
		String openingTime = "", closingTime = "";					
			openingTime = ((Global)PickupnDeliveryDialog.this.getApplicationContext()).getStartTime();
					closingTime = ((Global)PickupnDeliveryDialog.this.getApplicationContext()).getEndTime();
				Log.e("start timee", openingTime);
				Log.e("end timee", closingTime);

		mCalendarOpeningTime = new GregorianCalendar();
		// mCalendarOpeningTime.set(Calendar.HOUR, 9);
		// mCalendarOpeningTime.set(Calendar.MINUTE, 00);
		// mCalendarOpeningTime.set(Calendar.AM_PM, Calendar.AM);

		mCalendarClosingTime = new GregorianCalendar();
		
		Calendar conversion = Calendar.getInstance();
		Calendar conversion1 = Calendar.getInstance();
		try {
			Date date=timeFormat.parse(openingTime);
			String date1= timeFormat.format(dayFormat.parse(closingTime));
			conversion.setTime(date);
			conversion1.setTime(timeFormat.parse(date1));
			Log.e("mcalenderclosing", ""+timeFormat.parse(dayFormat.format(closingTime))+" "+timeFormat.parse(date1));


		} catch (Exception e) {
			e.printStackTrace();
		}
		mCalendarOpeningTime.set(Calendar.HOUR_OF_DAY,
				conversion.get(Calendar.HOUR_OF_DAY));
		mCalendarOpeningTime.set(Calendar.MINUTE,
				conversion.get(Calendar.MINUTE));
		conversion = new GregorianCalendar();
		try {
			conversion.setTime(timeFormat.parse(closingTime));
		} catch (Exception e) {
			e.printStackTrace();
		}
		mCalendarClosingTime.set(Calendar.HOUR_OF_DAY,
				conversion1.get(Calendar.HOUR_OF_DAY));
		mCalendarClosingTime.set(Calendar.MINUTE,
				conversion1.get(Calendar.MINUTE));
		Log.e("mcalenderclosing", ""+conversion1.get(Calendar.HOUR_OF_DAY));
		Log.e("mcalenderopeining", ""+conversion.get(Calendar.HOUR_OF_DAY));
	}
	
	
	
}
