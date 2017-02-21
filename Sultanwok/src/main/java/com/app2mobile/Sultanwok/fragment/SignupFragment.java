package com.app2mobile.Sultanwok.fragment;

import java.io.File;
import java.io.IOException;

import org.json.JSONObject;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import com.app2mobile.acplibrary.ACProgressCustom;
import com.app2mobile.gcm.GCMUtiles;
import com.app2mobile.Sultanwok.CheckOutProductListActivity;
import com.app2mobile.Sultanwok.Global;
import com.app2mobile.Sultanwok.R;
import com.app2mobile.utiles.AppUtiles;
import com.app2mobile.utiles.ConstantsUrl;
import com.app2mobile.utiles.EmailPatternMatcher;
import com.app2mobile.utiles.JsonParser;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class SignupFragment extends BaseFragment {
	private RelativeLayout signUpObj;
	private ScrollView scroll;
	private EditText firstNameObj, lastNameObj, emailObj, passwordObj,
			confirmPasswordObj, phoneEdt;
	private TextView privacyPolicyTxt,signup_txt;
	public static final String PROPERTY_REG_ID = "registration_id";
	public static final String PROPERTY_APP_VERSION = "appVersion";

	public static final int DISCOUNT_NOTIFICATION_ID = 2;
	private String android_id;// =
								// Secure.getString(getActivity().getContentResolver(),
	private static SignupFragment instance = null;

	// Secure.ANDROID_ID);

	/**
	 * GCM variables
	 */
	// public static final String TAG = "AJA GCM";
	protected static final String EXTRA_MESSAGE = "message";
	public final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	/**
	 * This is the project number you got from the API Console, as described in
	 * "Getting Started."
	 */
	private String SENDER_ID = ConstantsUrl.PUSHNOTIFICATION_PROJECTID;
	private GoogleCloudMessaging gcm;
	private String regid;
	private String isProfile;
	private int isCart = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		Bundle bundle = getActivity().getIntent().getExtras();
		if (bundle != null) {
			isProfile = bundle.getString("profile");
			if (bundle.containsKey("isCart"))
				isCart = bundle.getInt("isCart");

		}
	}
	public static SignupFragment newInstance() {

		if (instance == null) {
			instance = new SignupFragment();
		}
		return instance;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.new_sign_up, container, false);
	}
@Override
public void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	//Global.getInstance().trackScreenView("SignUp Page");

}
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onViewCreated(view, savedInstanceState);
		signUpObj = (RelativeLayout) view.findViewById(R.id.signUp);
		firstNameObj = (EditText) view.findViewById(R.id.firstName);
		lastNameObj = (EditText) view.findViewById(R.id.lastName);
		emailObj = (EditText) view.findViewById(R.id.email);
		passwordObj = (EditText) view.findViewById(R.id.password);
		confirmPasswordObj = (EditText) view.findViewById(R.id.confirmPassword);
		privacyPolicyTxt = (TextView) view.findViewById(R.id.privacy_policy_next);
		phoneEdt = (EditText) view.findViewById(R.id.phone);
		signup_txt=(TextView)view.findViewById(R.id.signup_text);
		scroll=(ScrollView)view.findViewById(R.id.rel);
		signUpObj.setBackgroundColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));
	    File f = new File("/mnt/sdcard/App2food/"+ConstantsUrl.STORE_BUNDLE_ID+"backround_img.jpg");
		
		Bitmap bmp = BitmapFactory.decodeFile(f.getAbsolutePath());
		Drawable dr = new BitmapDrawable(bmp);
		scroll.setBackgroundDrawable(dr);
//		privacyPolicyTxt.setText(Html
//				.fromHtml(getString(R.string.policy)));
		Typeface tf= Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
		firstNameObj.setTypeface(tf);
		lastNameObj.setTypeface(tf);
		emailObj.setTypeface(tf);
		passwordObj.setTypeface(tf);
		confirmPasswordObj.setTypeface(tf);
		phoneEdt.setTypeface(tf);
		signup_txt.setTypeface(tf);
		mTitleTxt.setText("SignUp");
		privacyPolicyTxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Uri uri = Uri
						.parse("http://www.app2mobile.com/privacypolicy.html");
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
			}
		});
		signUpObj.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String firstNameStr = firstNameObj.getText().toString();
				String lastNameStr = lastNameObj.getText().toString();
				String emailStr = emailObj.getText().toString();
				String passwordStr = passwordObj.getText().toString();
				String confirmPassword = confirmPasswordObj.getText()
						.toString();
				String phoneStr = phoneEdt.getText().toString();
				if (firstNameStr == null || firstNameStr.equals("")) {
					AppUtiles.getInstance().showToast(getView(),
							getString(R.string.firstname_required));
				} else if (lastNameStr == null || lastNameStr.equals("")) {
					AppUtiles.getInstance().showToast(getView(),
							getString(R.string.lastname_required));
				} else if (emailStr == null || emailStr.equals("")) {
					AppUtiles.getInstance().showToast(getView(),
							getString(R.string.email_required));
				} else if (!EmailPatternMatcher.EMAIL_ADDRESS_PATTERN.matcher(
						emailStr).matches()) {
					AppUtiles.getInstance().showToast(getView(),
							getString(R.string.emailFormat_required));
				} 
//				else if (phoneStr == null || phoneStr.equals("")) {
//					AppUtiles.showToast(getActivity(),
//							getString(R.string.phone_required));
//				} 
				else if (passwordStr == null || passwordStr.equals("")) {
					AppUtiles.getInstance().showToast(getView(),
							getString(R.string.password));
				} else if (confirmPassword == null
						|| confirmPassword.equals("")) {
					AppUtiles.getInstance().showToast(getView(),
							getString(R.string.confirmPassword_name_required));
				} else if (!passwordStr.equals(confirmPassword)) {
					AppUtiles.getInstance().showToast(getView(),
							getString(R.string.password_confirm_match1));
				} else {
					// new SignUpAsynTask().execute(new String[] { firstNameStr,
					// lastNameStr, emailStr, android_id, passwordStr });

					if (checkPlayServices()) {
						gcm = GoogleCloudMessaging.getInstance(getActivity());
						regid=GCMUtiles.getInstance().getRegistrationId(
								getActivity());
						((Global)SignupFragment.this.getActivity().getApplicationContext()).setReg_id(regid);
						//regid = getRegistrationId(getActivity());

						// if (regid.isEmpty()) {
						registerInBackground(new String[] { firstNameStr,
								lastNameStr, emailStr, regid, passwordStr,
								"android", phoneStr,ConstantsUrl.STOREID,"register"});
						// }
					} else {
						// Log.i(TAG,
						// "No valid Google Play Services APK found.");
						AppUtiles.getInstance().showToast(getView(),
								"No valid Google Play Services APK found.");
					}
				}
			}
		});
	}

	// private class SignUpAsynTask extends AsyncTask<String[], Void, String> {
	// String names[];
	// String values[];
	//
	// @Override
	// protected void onPreExecute() {
	// // TODO Auto-generated method stub
	// super.onPreExecute();
	// names = new String[] { "firstname", "lastname", "email",
	// "device_id", "password", "device_type" };
	// }
	//
	// @Override
	// protected String doInBackground(String[]... params) {
	// // TODO Auto-generated method stub
	// return JsonParser.Webserice_Call_URL(ConstantsUrl.SIGNUP, names,
	// params[0]);
	//
	// }
	//
	// @Override
	// protected void onPostExecute(String result) {
	// // TODO Auto-generated method stub
	// super.onPostExecute(result);
	//
	// parseResponse(result);
	// }
	// }

	private void parseResponse(String result) {
		try {
			if (result != null) {
				JSONObject mainJson = new JSONObject(result);
				if (mainJson.has("state")) {
					String state = JsonParser
							.getkeyValue_Str(mainJson, "state");
					String msg = JsonParser.getkeyValue_Str(mainJson, "msg");
					if (state.equals("1")) {
						if (mainJson.has("data")) {
							JSONObject dataJson = mainJson
									.getJSONObject("data");
							if (dataJson.has("customer")) {
								JSONObject customerJson = dataJson
										.getJSONObject("customer");
								// String customerId =
								// JsonParser.getkeyValue_Str(
								// customerJson, "cust_id");
								String firstname = JsonParser.getkeyValue_Str(
										customerJson, "customer_fname");
								String lastname = JsonParser.getkeyValue_Str(
										customerJson, "customer_lname");
								String email = JsonParser.getkeyValue_Str(
										customerJson, "customer_email");
								String phone = JsonParser.getkeyValue_Str(
										customerJson, "customer_phone");
							}
						}
						getActivity().finish();
					}
					AppUtiles.getInstance().showToast(getView(), msg);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// private void parseResponse(String result) {
	// // regiseration response
	// //
	// {"state":1,"msg":"Successfully Registered, Please Check your email for the Verification Code",
	// //
	// "data":{"customer":{"customer_fname":"Mukesh","customer_lname":"Singh",
	// // "customer_email":"mshuiet@gmail.com","customer_phone":""}}}
	// if (result != null) {
	// if (result != null) {
	// try {
	// JSONObject mainJsonObject = new JSONObject(result);
	// if (mainJsonObject.has("error")) {
	// AppUtiles.showToast(getActivity(), JsonParser
	// .getkeyValue_Str(mainJsonObject, "error"));
	// } else {
	// String customerId = JsonParser.getkeyValue_Str(
	// mainJsonObject, "cust_id");
	// String firstname = JsonParser.getkeyValue_Str(
	// mainJsonObject, "firstname");
	// String lastname = JsonParser.getkeyValue_Str(
	// mainJsonObject, "lastname");
	// String email = JsonParser.getkeyValue_Str(
	// mainJsonObject, "email");
	// String couponCode = JsonParser.getkeyValue_Str(
	// mainJsonObject, "voucher");
	// String couponText = JsonParser.getkeyValue_Str(
	// mainJsonObject, "voucher_note");
	// JSONObject shippingCreatedOn = mainJsonObject
	// .getJSONObject("shippingaddress");
	// JSONObject bilingCreatedOn = mainJsonObject
	// .getJSONObject("billingAddress");
	// // String billingCrearedOn = JsonParser.getkeyValue_Str(
	// // bilingCreatedOn, "created_at");
	// /**
	// * get after checkout billing
	// */
	//
	// AddressMetadata billingData = new AddressMetadata();
	// billingData.setEntityId(JsonParser.getkeyValue_Str(
	// shippingCreatedOn, "entity_id"));
	// billingData.setEntityTypeId(JsonParser.getkeyValue_Str(
	// bilingCreatedOn, "entity_type_id"));
	// billingData.setAttributeSetId(JsonParser
	// .getkeyValue_Str(bilingCreatedOn,
	// "attribute_set_id"));
	// billingData.setIncrementedId(JsonParser
	// .getkeyValue_Str(bilingCreatedOn,
	// "increment_id"));
	// billingData.setParentId(JsonParser.getkeyValue_Str(
	// bilingCreatedOn, "parent_id"));
	// billingData.setCreatedOn(JsonParser.getkeyValue_Str(
	// bilingCreatedOn, "created_at"));
	// billingData.setUpdatedOn(JsonParser.getkeyValue_Str(
	// bilingCreatedOn, "updated_at"));
	// billingData.setIsActive(JsonParser.getkeyValue_Str(
	// bilingCreatedOn, "is_active"));
	// billingData.setFirstName(JsonParser.getkeyValue_Str(
	// bilingCreatedOn, "firstname"));
	// billingData.setLastName(JsonParser.getkeyValue_Str(
	// bilingCreatedOn, "lastname"));
	// billingData.setCity(JsonParser.getkeyValue_Str(
	// bilingCreatedOn, "city"));
	// billingData.setCountryId(JsonParser.getkeyValue_Str(
	// bilingCreatedOn, "country_id"));
	// billingData.setRegion(JsonParser.getkeyValue_Str(
	// bilingCreatedOn, "region"));
	// billingData.setPostCode(JsonParser.getkeyValue_Str(
	// bilingCreatedOn, "postcode"));
	// billingData.setTelephone(JsonParser.getkeyValue_Str(
	// bilingCreatedOn, "telephone"));
	// billingData.setStreet(JsonParser.getkeyValue_Str(
	// bilingCreatedOn, "street"));
	//
	// /**
	// * get after checkout shipping
	// */
	// AddressMetadata shippingData = new AddressMetadata();
	// shippingData.setEntityId(JsonParser.getkeyValue_Str(
	// shippingCreatedOn, "entity_id"));
	// shippingData.setEntityTypeId(JsonParser
	// .getkeyValue_Str(shippingCreatedOn,
	// "entity_type_id"));
	// shippingData.setAttributeSetId(JsonParser
	// .getkeyValue_Str(shippingCreatedOn,
	// "attribute_set_id"));
	// shippingData.setIncrementedId(JsonParser
	// .getkeyValue_Str(shippingCreatedOn,
	// "increment_id"));
	// shippingData.setParentId(JsonParser.getkeyValue_Str(
	// shippingCreatedOn, "parent_id"));
	// shippingData.setCreatedOn(JsonParser.getkeyValue_Str(
	// shippingCreatedOn, "created_at"));
	// shippingData.setUpdatedOn(JsonParser.getkeyValue_Str(
	// shippingCreatedOn, "updated_at"));
	// shippingData.setIsActive(JsonParser.getkeyValue_Str(
	// shippingCreatedOn, "is_active"));
	// shippingData.setFirstName(JsonParser.getkeyValue_Str(
	// shippingCreatedOn, "firstname"));
	// shippingData.setLastName(JsonParser.getkeyValue_Str(
	// shippingCreatedOn, "lastname"));
	// shippingData.setCity(JsonParser.getkeyValue_Str(
	// shippingCreatedOn, "city"));
	// shippingData.setCountryId(JsonParser.getkeyValue_Str(
	// shippingCreatedOn, "country_id"));
	// shippingData.setRegion(JsonParser.getkeyValue_Str(
	// shippingCreatedOn, "region"));
	// shippingData.setPostCode(JsonParser.getkeyValue_Str(
	// shippingCreatedOn, "postcode"));
	// shippingData.setTelephone(JsonParser.getkeyValue_Str(
	// shippingCreatedOn, "telephone"));
	// shippingData.setStreet(JsonParser.getkeyValue_Str(
	// shippingCreatedOn, "street"));
	//
	// /**
	// * /** sessionId
	// */
	//
	// String sessionId = JsonParser.getkeyValue_Str(
	// mainJsonObject, "session_id");
	//
	// Gson gson = new Gson();
	// SharedPreferences appPrefs = getActivity()
	// .getSharedPreferences(ConstantsUrl.APP_PREF, 0);
	// SharedPreferences.Editor prefs_editor = appPrefs.edit();
	// prefs_editor.putBoolean(ConstantsUrl.IS_LOGIN, true);
	// prefs_editor.putString(ConstantsUrl.CUSTOMER_ID,
	// customerId);
	// prefs_editor.putString(ConstantsUrl.FIRST_NAME,
	// firstname);
	// prefs_editor
	// .putString(ConstantsUrl.LAST_NAME, lastname);
	// prefs_editor.putString(ConstantsUrl.EMAIL, email);
	//
	// prefs_editor.putString(ConstantsUrl.SHIPPING_ADDRESS,
	// gson.toJson(shippingData));
	// prefs_editor.putString(ConstantsUrl.BILLING_ADDRESS,
	// gson.toJson(shippingData));
	// prefs_editor.putString(ConstantsUrl.SESSION_ID,
	// sessionId);
	// prefs_editor.commit();
	//
	// if (couponCode != null && !couponCode.equals("null")
	// && !couponCode.equals("")) {
	// if (couponText == null || couponText.equals("null")
	// || couponCode.equals("")) {
	// couponText = "Use this coupon on your first order to get 10% Discount";
	// }
	// sendNotification(couponCode, couponText);
	// }
	// if (isProfile != null && isProfile.equals("Profile")) {
	// Intent intentProfile = new Intent(getActivity(),
	// UserProfileActivity.class);
	// startActivity(intentProfile);
	//
	// } else if (isProfile != null
	// && isProfile.equals("orderHistory")) {
	// Intent intentProfile = new Intent(getActivity(),
	// OrderHistoryActivity.class);
	// startActivity(intentProfile);
	// } else if (isProfile != null
	// && isProfile.equals("checkOut")) {
	// // Intent intentProfile = new Intent(getActivity(),
	// // PaymentDetailPart1Activity.class);
	// // intentProfile.putExtra("isGuest", false);
	// // intentProfile.putExtra("isCart",
	// // LoginSignupActivity.isCart);
	// // startActivity(intentProfile);
	// getActivity().setResult(getActivity().RESULT_OK,
	// getActivity().getIntent());
	// }
	//
	// getActivity().finish();
	//
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// }
	// }

	/**
	 * Check the device to make sure it has the Google Play Services APK. If it
	 * doesn't, display a dialog that allows users to download the APK from the
	 * Google Play Store or enable it in the device's system settings.
	 */
	private boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getActivity());
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode,
						getActivity(), PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {

				getActivity().finish();
			}
			return false;
		}
		return true;
	}

	/**
	 * Gets the current registration ID for application on GCM service.
	 * <p>
	 * If result is empty, the app needs to register.
	 * 
	 * @return registration ID, or empty string if there is no existing
	 *         registration ID.
	 */
	private String getRegistrationId(Context context) {
		final SharedPreferences prefs = getGCMPreferences(context);
		String registrationId = prefs.getString(PROPERTY_REG_ID, "");
		// if (registrationId.isEmpty()) {
		if (registrationId == null || registrationId.trim().equals("")) {

			return "";
		}
		// Check if app was updated; if so, it must clear the registration ID
		// since the existing regID is not guaranteed to work with the new
		// app version.
		int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION,
				Integer.MIN_VALUE);
		int currentVersion = getAppVersion(context);
		if (registeredVersion != currentVersion) {

			return "";
		}
		return registrationId;
	}

	/**
	 * @return Application's {@code SharedPreferences}.
	 */
	private SharedPreferences getGCMPreferences(Context context) {
		// This sample app persists the registration ID in shared preferences,
		// but
		// how you store the regID in your app is up to you.
		return getActivity().getSharedPreferences(ConstantsUrl.APP_PREF,
				Context.MODE_PRIVATE);
	}

	/**
	 * @return Application's version code from the {@code PackageManager}.
	 */
	private static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// should never happen
			throw new RuntimeException("Could not get package name: " + e);
		}
	}

	private void registerInBackground(final String[] value) {
		new AsyncTask<String[], Void, String>() {
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
			};
			@Override
			protected String doInBackground(String[]... params) {
				// TODO Auto-generated method stub
				String msg;
				try {
					if (gcm == null) {
						gcm = GoogleCloudMessaging.getInstance(getActivity());
					}
					if (regid == null || regid.trim().equals("")) {
						regid = gcm.register(SENDER_ID);
						value[3] = regid;
						// msg = "Device registered, registration ID=" + regid;
					}

					// You should send the registration ID to your server over
					// HTTP,
					// so it can use GCM/HTTP or CCS to send messages to your
					// app.
					// The request to your server should be authenticated if
					// your app
					// is using accounts.
					msg = sendRegistrationIdToBackend(value);

					// For this demo: we don't need to send it because the
					// device
					// will send upstream messages to a server that echo back
					// the
					// message using the 'from' address in the message.

					// Persist the regID - no need to register again.
					storeRegistrationId(getActivity(), regid);
				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();
					// If there is an error, don't just keep trying to register.
					// Require the user to click a button again, or perform
					// exponential back-off.
				}
				return msg;
				// return null;
			}

			protected void onPostExecute(String result) {

				parseResponse(result);
				progressDialog.dismiss();
			};
		}.execute(value);
	}

	/**
	 * Stores the registration ID and app versionCode in the application's
	 * {@code SharedPreferences}.
	 * 
	 * @param context
	 *            application's context.
	 * @param regId
	 *            registration ID
	 */
	private void storeRegistrationId(Context context, String regId) {
		final SharedPreferences prefs = getGCMPreferences(context);
		int appVersion = getAppVersion(context);

		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(PROPERTY_REG_ID, regId);
		editor.putInt(PROPERTY_APP_VERSION, appVersion);
		editor.commit();
	}

	/**
	 * Sends the registration ID to your server over HTTP, so it can use
	 * GCM/HTTP or CCS to send messages to your app. Not needed for this demo
	 * since the device sends upstream messages to a server that echoes back the
	 * message using the 'from' address in the message.
	 */
	private String sendRegistrationIdToBackend(String[] value) {
		String names[] = new String[] { "firstname", "lastname", "email",
				"device_id", "password", "device_type", "phone","store_id","action" };

		return JsonParser.Webserice_Call_URL(ConstantsUrl.SINGUP_LOGIN
				, names, value);
		// Your implementation here.
	}

	private void sendNotification(String couponCode, String couponMsg) {
		Intent resultIntent = new Intent(getActivity(),
				CheckOutProductListActivity.class);
		resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_CLEAR_TASK);
		// TaskStackBuilder stackBuilder =
		// TaskStackBuilder.create(getActivity());
		// // Adds the back stack
		// stackBuilder.addParentStack(CheckOutProductListActivity.class);
		// // Adds the Intent to the top of the stack
		// stackBuilder.addNextIntent(resultIntent);
		// // Gets a PendingIntent containing the entire back stack
		// PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
		// PendingIntent.FLAG_UPDATE_CURRENT);
		PendingIntent resultPendingIntent = PendingIntent.getActivity(
				getActivity(), 0, resultIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				getActivity());
		builder.setContentIntent(resultPendingIntent);
		builder.setSmallIcon(R.drawable.ic_launcher);
		builder.setContentTitle(couponCode);
		builder.setContentText(couponMsg);
		// builder.setSubText(getString(R.string.app_name));
		builder.setStyle(new NotificationCompat.BigTextStyle().bigText(
				couponMsg).setBigContentTitle(couponCode));
		builder.setAutoCancel(true);
		NotificationManager mNotificationManager = (NotificationManager) getActivity()
				.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(DISCOUNT_NOTIFICATION_ID, builder.build());
	}

	@Override
	public int getLayout() {
		// TODO Auto-generated method stub
		return R.layout.new_sign_up;
	}
}
