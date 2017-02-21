package com.app2mobile.Sultanwok.fragment;

import java.io.File;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app2mobile.acplibrary.ACProgressCustom;
import com.app2mobile.Sultanwok.CategoryActivity;
import com.app2mobile.Sultanwok.ForgotPasswordActivity;
import com.app2mobile.Sultanwok.Global;
import com.app2mobile.Sultanwok.R;
import com.app2mobile.Sultanwok.SingupActivity;
import com.app2mobile.Sultanwok.VarifyUserActivity;
import com.app2mobile.metadata.AddressMetadata;
import com.app2mobile.utiles.AppUtiles;
import com.app2mobile.utiles.ConstantsUrl;
import com.app2mobile.utiles.EmailPatternMatcher;
import com.app2mobile.utiles.JsonParser;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;

public class LoginFragment extends BaseFragment {

	private Button checkOutGuestObj, loginObj;
	private TextView emailObj, passwordObj, forgotPasswordObj, verifyTxt,
			signup_text, text,termncondition,termncondition_text;
	private ImageView google;
	private ImageView fb;
	private GoogleApiClient gplus;
	String user_id;
	boolean isclicked = false;
	private RelativeLayout facebook_guest;
	/**
	 * This is the project number you got from the API Console, as described in
	 * "Getting Started."
	 */
	private String SENDER_ID = ConstantsUrl.PUSHNOTIFICATION_PROJECTID;
	private GoogleCloudMessaging gcm;
	private String regid;
	private String isProfile;
	private boolean mIntentInProgress;
	private ConnectionResult mConnectionResult;
	private boolean mSignInClicked;
	// private int isCart = 0;
	private SharedPreferences appPrefs;
	private SharedPreferences.Editor prefs_editor;
	private static LoginFragment instance = null;
	private boolean isCheckout = false;

	private CheckBox show_password;

	int islogin = 0;

	public static LoginFragment newInstance() {

		if (instance == null) {
			instance = new LoginFragment();
		}
		return instance;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle bundle = getActivity().getIntent().getExtras();
		if (bundle != null) {
			isProfile = bundle.getString("profile");

		}
		Intent intent = getActivity().getIntent();
		if (Intent.ACTION_VIEW.equals(intent.getAction())) {
			Uri ur = intent.getData();
			String islogin = ur.getQueryParameter("is_login");

			user_id = ur.getQueryParameter("user_id");
			Log.e("user_iddd", islogin);
			Log.e("user_iddd", user_id);
			if (islogin.equals("1")) {
				new Facebook_Login().execute();
			}
		}
		appPrefs = getActivity().getSharedPreferences(ConstantsUrl.APP_PREF, 0);
		prefs_editor = appPrefs.edit();

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		// this.onSaveInstanceState(outState);

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	//	Global.getInstance().trackScreenView("Login Page");
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		mTitleTxt.setText("Login");
		loginObj = (Button) view.findViewById(R.id.login);
		emailObj = (TextView) view.findViewById(R.id.email);
		passwordObj = (TextView) view.findViewById(R.id.password);
		forgotPasswordObj = (TextView) view.findViewById(R.id.forgot_password);
		fb = (ImageView) view.findViewById(R.id.facebook);
		show_password = (CheckBox) view.findViewById(R.id.checkBox1);
		text = (TextView) view.findViewById(R.id.textView1);
		signup_text = (TextView) view.findViewById(R.id.signup_text);
		termncondition= (TextView)view.findViewById(R.id.termncondition);
		termncondition_text=(TextView)view.findViewById(R.id.textView2);
		facebook_guest = (RelativeLayout) view
				.findViewById(R.id.facebook_guest);
		Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
				"OpenSans-Light.ttf");
		emailObj.setTypeface(tf);
		passwordObj.setTypeface(tf);
		forgotPasswordObj.setTypeface(tf);
		show_password.setTypeface(tf);
		text.setTypeface(tf);
		signup_text.setTypeface(tf);
		termncondition_text.setTypeface(tf);
		termncondition.setTypeface(tf);
		signup_text.setTextColor(Color
				.parseColor(ConstantsUrl.STORE_COLOR_CODE));
		termncondition.setTextColor(Color
				.parseColor(ConstantsUrl.STORE_COLOR_CODE));
		// google=(ImageView)view.findViewById(R.id.google);
		verifyTxt = (TextView) view.findViewById(R.id.verifyUser);
		verifyTxt.setPaintFlags(verifyTxt.getPaintFlags()
				| Paint.UNDERLINE_TEXT_FLAG);
		forgotPasswordObj.setText(Html
				.fromHtml(getString(R.string.forgot_password)));

		checkOutGuestObj = (Button) view.findViewById(R.id.checkoutAsGuest);
		// mTitleTxt.setText("Login");
		checkOutGuestObj.setBackgroundColor(Color
				.parseColor(ConstantsUrl.STORE_COLOR_CODE));
		loginObj.setBackgroundColor(Color
				.parseColor(ConstantsUrl.STORE_COLOR_CODE));

		File f = new File("/mnt/sdcard/App2food/"
				+ ConstantsUrl.STORE_BUNDLE_ID + "backround_img.jpg");

		Bitmap bmp = BitmapFactory.decodeFile(f.getAbsolutePath());
		Drawable dr = new BitmapDrawable(bmp);
		facebook_guest.setBackgroundDrawable(dr);
		show_password.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (show_password.isChecked() == true) {
					passwordObj.setInputType(InputType.TYPE_CLASS_TEXT);
				} else {

					passwordObj.setInputType(InputType.TYPE_CLASS_TEXT
							| InputType.TYPE_TEXT_VARIATION_PASSWORD);
				}
			}
		});
		if (isProfile != null && !isProfile.equals("Profile")
				&& !isProfile.equals("orderHistory")
				&& !isProfile.equals("profile")) {
			checkOutGuestObj.setVisibility(View.VISIBLE);
		} else {
			checkOutGuestObj.setVisibility(View.GONE);
		}
		fb.setOnClickListener(new OnClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// clearApplicationData();
				// clearSharedPreferences(getActivity());
				//
				// //((ActivityManager)
				// getActivity().getSystemService(Context.ACTIVITY_SERVICE)).clearApplicationUserData();
				// //clearPreferences();
				// SharedPreferences.Editor pref =
				// getActivity().getSharedPreferences(ConstantsUrl.RESTAURANTDETAILPREFS,
				// 0).edit();
				// pref.clear();
				// pref.commit();
				// ArrayList<RestaurantCategoryMetadata> catList = new
				// ArrayList<RestaurantCategoryMetadata>();
				// HashMap<Integer, ArrayList<RestaurantCategoryMetadata>>
				// subCategoryList = new HashMap<Integer,
				// ArrayList<RestaurantCategoryMetadata>>();
				// HashMap<Integer, ArrayList<RestaurantProductMetadata>>
				// productList = new HashMap<Integer,
				// ArrayList<RestaurantProductMetadata>>();
				//
				// catList.clear();
				// subCategoryList.clear();
				// productList.clear();
				isclicked = true;
				getActivity().finish();
				// Intent in = new Intent(getActivity(),Facebook_Login.class);
				// startActivity(in);
				// Intent in= new Intent(Intent.ACTION_VIEW);
				// in.addCategory(Intent.CATEGORY_BROWSABLE);
				// Uri uri = Uri.parse("http://stg.app2food.com/facebook/");
				// in.setData(uri);
				// startActivity(in);
			}
		});

		forgotPasswordObj.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(getActivity(),
						ForgotPasswordActivity.class);
				startActivity(intent);
				getActivity().finish();

			}
		});
		verifyTxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),
						VarifyUserActivity.class);
				startActivity(intent);
				getActivity().finish();
			}
		});
		loginObj.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String emailStr = emailObj.getText().toString();
				String passwordStr = passwordObj.getText().toString();
				if (emailStr == null || emailStr.equals("")) {
					// AppUtiles.getInstance().showToast(getActivity(),
					// getString(R.string.email_required));
//					Snackbar.make(getView(),
//							getString(R.string.email_required),
//							Snackbar.LENGTH_LONG).show();
					AppUtiles.getInstance().showToast(getView(), getString(R.string.email_required));
				} else if (!EmailPatternMatcher.EMAIL_ADDRESS_PATTERN.matcher(
						emailStr).matches()) {
					// AppUtiles.getInstance().showToast(getActivity(),
					// getString(R.string.emailFormat_required));
//					Snackbar.make(getView(),
//							getString(R.string.emailFormat_required),
//							Snackbar.LENGTH_LONG).show();
					AppUtiles.getInstance().showToast(getView(), getString(R.string.emailFormat_required));
				} else if (passwordStr == null || passwordStr.equals("")) {
					// AppUtiles.getInstance().showToast(getActivity(),
					// getString(R.string.password_required));
//					Snackbar.make(getView(),
//							getString(R.string.password_required),
//							Snackbar.LENGTH_LONG).show();
					AppUtiles.getInstance().showToast(getView(), getString(R.string.emailFormat_required));

				} else {
					if (checkPlayServices()) {
						gcm = GoogleCloudMessaging.getInstance(getActivity());
						regid = getRegistrationId(getActivity());
						//Toast.makeText(getActivity(), ConstantsUrl.STORE_BUNDLE_ID,3).show();   //ts
						new LoginAsynTask().execute(new String[] { emailStr,
								passwordStr, regid, "android", "login",
								ConstantsUrl.STORE_BUNDLE_ID }); // replaced  by  ConstantsUrl.STOREID
						login.setTitle("Logout");
					} else {
						// AppUtiles.getInstance().showToast(getActivity(),
						// "No valid Google Play Services APK found.");
//						Snackbar.make(getView(),
//								"No valid Google Play Services APK found.",
//								Snackbar.LENGTH_LONG).show();
						AppUtiles.getInstance().showToast(getView(), "No valid Google Play Services APK found.");

					}
				}
			}
		});
		checkOutGuestObj.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentGuest = getActivity().getIntent();
				intentGuest.putExtra("isGuest", true);
				((Global) getActivity().getApplicationContext())
						.setIs_guest_user("true");
				getActivity().setResult(getActivity().RESULT_OK,
						getActivity().getIntent());
				getActivity().finish();
			}
		});

		signup_text.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), SingupActivity.class);
				startActivity(intent);
				getActivity().finish();
			}
		});
	}

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
						getActivity(),
						SignupFragment.PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {

				getActivity().finish();
			}
			return false;
		}
		return true;
	}

	public static void clearSharedPreferences(Context ctx) {
		File dir = new File(ctx.getFilesDir().getParent() + "/shared_prefs/");
		String[] children = dir.list();

		for (int i = 0; i < children.length; i++) {
			// clear each of the prefrances
			ctx.getSharedPreferences(children[i].replace(".xml", ""),
					Context.MODE_PRIVATE).edit().clear().commit();
		}
		// Make sure it has enough time to save all the commited changes
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		for (int i = 0; i < children.length; i++) {
			// delete the files
			new File(dir, children[i]).delete();
		}
	}

	public void clearApplicationData() {
		File cache = getActivity().getCacheDir();
		File appDir = new File(cache.getParent());
		if (appDir.exists()) {
			String[] children = appDir.list();
			for (String s : children) {
				if (!s.equals("lib")) {
					deleteDir(new File(appDir, s));
					Log.i("TAG",
							"**************** File /data/data/APP_PACKAGE/" + s
									+ " DELETED *******************");
				}
			}
		}
	}

	public static boolean deleteDir(File dir) {
		if (dir != null && dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (isclicked == true) {
			String url = ConstantsUrl.facebook_url
					+ getResources().getString(R.string.scheme);
			Intent in = new Intent(Intent.ACTION_VIEW);
			in.setData(Uri.parse(url));
			final PackageManager mgr = getActivity().getPackageManager();
			List<ResolveInfo> list = mgr.queryIntentActivities(in,
					PackageManager.GET_INTENT_FILTERS);
			isclicked = false;
			Log.e("fdsfasfd", list.toString());
			// in.setPackage("com.app2mobile.demorestaurantapp");
			startActivity(in);
		}
	}

	private void clearPreferences() {
		try {
			// clearing app data
			Runtime runtime = Runtime.getRuntime();
			runtime.exec("pm clear com.app2mobile.demorestaurantapp");

		} catch (Exception e) {
			e.printStackTrace();

		}
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
		String registrationId = prefs.getString(SignupFragment.PROPERTY_REG_ID,
				"");
		// if (registrationId.isEmpty()) {
		if (registrationId == null || registrationId.trim().equals("")) {

			return "";
		}
		// Check if app was updated; if so, it must clear the registration ID
		// since the existing regID is not guaranteed to work with the new
		// app version.
		int registeredVersion = prefs.getInt(
				SignupFragment.PROPERTY_APP_VERSION, Integer.MIN_VALUE);
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
		editor.putString(SignupFragment.PROPERTY_REG_ID, regId);
		editor.putInt(SignupFragment.PROPERTY_APP_VERSION, appVersion);
		editor.commit();
	}

	private class LoginAsynTask extends AsyncTask<String[], Void, String> {
		String names[];
		ACProgressCustom progressDialog;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			
			super.onPreExecute();
			names = new String[] { "username", "password", "device_id",
			"device_type", "action", "store_id" };
			progressDialog = new ACProgressCustom.Builder(getActivity())
		    .useImages(R.drawable.loading1, R.drawable.loading2,R.drawable.loading3, R.drawable.loading4,R.drawable.loading5,R.drawable.loading6).build();
			progressDialog.setCanceledOnTouchOutside(true);

			progressDialog.show();
		}

		@Override
		protected String doInBackground(String[]... params) {
			// TODO Auto-generated method stub

			try {
				if (gcm == null) {
					gcm = GoogleCloudMessaging.getInstance(getActivity());
				}
				if (regid == null || regid.trim().equals("")) {
					regid = gcm.register(SENDER_ID);
					params[0][2] = regid;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return JsonParser.Webserice_Call_URL(ConstantsUrl.SINGUP_LOGIN
					+ "login", names, params[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			storeRegistrationId(getActivity(), regid);
			parseResponse(result);
			progressDialog.dismiss();
		}
	}

	private void parseResponse(String result) {
		System.out.println("login response " + result);
		if (result != null) {
			try {
				JSONObject mainJson = new JSONObject(result);
				if (mainJson.has("state")) {
					String state = JsonParser
							.getkeyValue_Str(mainJson, "state");
					String msg = JsonParser.getkeyValue_Str(mainJson, "msg");
					// AppUtiles.getInstance().showToast(getActivity(), msg);
					

					if (state != null && state.equals("1")) {
						if (mainJson.has("data")) {
							login.setTitle("Logout");

							JSONObject dataJson = mainJson
									.getJSONObject("data");
							if (dataJson.has("customer_data")) {
								try {
									JSONObject custJson = dataJson
											.getJSONObject("customer_data");
									String custId = JsonParser.getkeyValue_Str(
											custJson, "cust_id");
									((Global) LoginFragment.this.getActivity()
											.getApplicationContext())
											.setCust_id(custId);
									String custFName = JsonParser
											.getkeyValue_Str(custJson,
													"cust_fname");
									String custLName = JsonParser
											.getkeyValue_Str(custJson,
													"cust_lname");
									String custEmail = JsonParser
											.getkeyValue_Str(custJson,
													"cust_email");
									String custPhone = JsonParser
											.getkeyValue_Str(custJson,
													"cust_phone");

									prefs_editor.putBoolean(
											ConstantsUrl.IS_LOGIN, true);
									prefs_editor.putString(
											ConstantsUrl.CUSTOMER_ID, custId);
									prefs_editor.putString(
											ConstantsUrl.FIRST_NAME, custFName);
									prefs_editor.putString(
											ConstantsUrl.LAST_NAME, custLName);
									prefs_editor.putString(ConstantsUrl.EMAIL,
											custEmail);
									prefs_editor.putString(ConstantsUrl.PHONE,
											custPhone);
									prefs_editor.commit();
								} catch (Exception e) {

								}

							}
							if (dataJson.has("customer_billing_address")) {
								try {
									JSONObject billing = dataJson
											.getJSONObject("customer_billing_address");
									AddressMetadata address = new AddressMetadata();
									address.setCity(JsonParser.getkeyValue_Str(
											billing, "city"));
									address.setStreet(JsonParser
											.getkeyValue_Str(billing, "address"));
									address.setTelephone(JsonParser
											.getkeyValue_Str(billing,
													"telephone"));
									address.setPostCode(JsonParser
											.getkeyValue_Str(billing, "pincode"));
									address.setRegionId(JsonParser
											.getkeyValue_Str(billing,
													"state_code"));
									address.setCityId(JsonParser
											.getkeyValue_Str(billing, "city_id"));
									address.setRegion(AppUtiles.getInstance()
											.getStateName(
													address.getRegionId(),
													getActivity()));
									Gson gson = new Gson();
									prefs_editor.putString(
											ConstantsUrl.BILLING_ADDRESS,
											gson.toJson(address));
									prefs_editor.commit();
								} catch (Exception e) {

								}

							}
							if (dataJson.has("customer_shipping_address")) {
								try {
									JSONArray billing = dataJson
											.getJSONArray("customer_shipping_address");
									for (int i = 0; i <= billing.length(); i++) {
										JSONObject obj = billing
												.getJSONObject(i);

										AddressMetadata address = new AddressMetadata();
										address.setCity(JsonParser
												.getkeyValue_Str(obj,
														"city_name"));
										address.setStreet(JsonParser
												.getkeyValue_Str(obj, "address"));
										address.setTelephone(JsonParser
												.getkeyValue_Str(obj,
														"telephone"));
										address.setPostCode(JsonParser
												.getkeyValue_Str(obj, "pincode"));
										address.setRegionId(JsonParser
												.getkeyValue_Str(obj,
														"state_code"));
										address.setCityId(JsonParser
												.getkeyValue_Str(obj, "city_id"));
										address.setState_code(JsonParser
												.getkeyValue_Str(obj,
														"state_code"));
										address.setRegion(AppUtiles
												.getInstance().getStateName(
														address.getRegionId(),
														getActivity()));
										// Single_Address_Metadata adress= new
										// Single_Address_Metadata(JsonParser
										// .getkeyValue_Str(obj, "address"),
										// JsonParser.getkeyValue_Str(
										// obj, "city"));

										Gson gson = new Gson();
										prefs_editor.putString(
												ConstantsUrl.SHIPPING_ADDRESS,
												gson.toJson(address));
										prefs_editor.commit();
									}
								} catch (Exception e) {

								}

							}
						}
						getActivity().setResult(Activity.RESULT_OK);
						getActivity().finish();
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private class Facebook_Login extends AsyncTask<String[], Void, String> {

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
			try {
				if (gcm == null) {
					gcm = GoogleCloudMessaging.getInstance(getActivity());
				}
				if (regid == null || regid.trim().equals("")) {
					regid = gcm.register(SENDER_ID);
					params[0][2] = regid;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return JsonParser.Webserice_Call_URL(ConstantsUrl.GETUSERBYID,
					new String[] { "cust_id", "store_id" }, new String[] {
							user_id, ConstantsUrl.STOREID });
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			storeRegistrationId(getActivity(), regid);
			parseResponse1(result);
			progressDialog.dismiss();
		}

		private void parseResponse1(String result) {
			System.out.println("login response " + result);
			if (result != null) {
				try {
					JSONObject mainJson = new JSONObject(result);
					if (mainJson.has("state")) {
						String state = JsonParser.getkeyValue_Str(mainJson,
								"state");
						String msg = JsonParser
								.getkeyValue_Str(mainJson, "msg");
						// AppUtiles.getInstance().showToast(getActivity(),
						// msg);
						Snackbar.make(getView(), msg, Snackbar.LENGTH_SHORT)
								.show();

						if (state != null && state.equals("1")) {
							if (mainJson.has("data")) {
								JSONObject dataJson = mainJson
										.getJSONObject("data");

								if (dataJson.has("customer_data")) {
									try {
										JSONObject custJson = dataJson
												.getJSONObject("customer_data");
										String custId = JsonParser
												.getkeyValue_Str(custJson,
														"cust_id");
										((Global) LoginFragment.this
												.getActivity()
												.getApplicationContext())
												.setCust_id(custId);
										String custFName = JsonParser
												.getkeyValue_Str(custJson,
														"cust_fname");
										String custLName = JsonParser
												.getkeyValue_Str(custJson,
														"cust_lname");
										String custEmail = JsonParser
												.getkeyValue_Str(custJson,
														"cust_email");
										String custPhone = JsonParser
												.getkeyValue_Str(custJson,
														"cust_phone");

										prefs_editor.putBoolean(
												ConstantsUrl.IS_LOGIN, true);
										prefs_editor.putString(
												ConstantsUrl.CUSTOMER_ID,
												custId);
										prefs_editor.putString(
												ConstantsUrl.FIRST_NAME,
												custFName);
										prefs_editor.putString(
												ConstantsUrl.LAST_NAME,
												custLName);
										prefs_editor.putString(
												ConstantsUrl.EMAIL, custEmail);
										prefs_editor.putString(
												ConstantsUrl.PHONE, custPhone);
										prefs_editor.commit();
									} catch (Exception e) {

									}

								}
								if (dataJson.has("customer_billing_address")) {
									try {
										JSONObject billing = dataJson
												.getJSONObject("customer_billing_address");
										AddressMetadata address = new AddressMetadata();
										address.setCity(JsonParser
												.getkeyValue_Str(billing,
														"city"));
										address.setStreet(JsonParser
												.getkeyValue_Str(billing,
														"address"));
										address.setTelephone(JsonParser
												.getkeyValue_Str(billing,
														"telephone"));
										address.setPostCode(JsonParser
												.getkeyValue_Str(billing,
														"pincode"));
										address.setRegionId(JsonParser
												.getkeyValue_Str(billing,
														"state_code"));
										address.setCityId(JsonParser
												.getkeyValue_Str(billing,
														"city_id"));
										address.setRegion(AppUtiles
												.getInstance().getStateName(
														address.getRegionId(),
														getActivity()));
										Gson gson = new Gson();
										prefs_editor.putString(
												ConstantsUrl.BILLING_ADDRESS,
												gson.toJson(address));
										prefs_editor.commit();
									} catch (Exception e) {

									}

								}
								if (dataJson.has("customer_shipping_address")) {
									try {
										JSONArray billing = dataJson
												.getJSONArray("customer_shipping_address");
										for (int i = 0; i <= billing.length(); i++) {
											JSONObject obj = billing
													.getJSONObject(i);

											AddressMetadata address = new AddressMetadata();
											address.setCity(JsonParser
													.getkeyValue_Str(obj,
															"city"));
											address.setStreet(JsonParser
													.getkeyValue_Str(obj,
															"address"));
											address.setTelephone(JsonParser
													.getkeyValue_Str(obj,
															"telephone"));
											address.setPostCode(JsonParser
													.getkeyValue_Str(obj,
															"pincode"));
											address.setRegionId(JsonParser
													.getkeyValue_Str(obj,
															"state_code"));
											address.setCityId(JsonParser
													.getkeyValue_Str(obj,
															"city_id"));
											address.setRegion(AppUtiles
													.getInstance()
													.getStateName(
															address.getRegionId(),
															getActivity()));
											Gson gson = new Gson();
											prefs_editor
													.putString(
															ConstantsUrl.SHIPPING_ADDRESS,
															gson.toJson(address));
											prefs_editor.commit();
										}
									} catch (Exception e) {

									}

								}
							}
							getActivity().setResult(Activity.RESULT_OK);
							getActivity().finish();
							Intent in = new Intent(getActivity(),
									CategoryActivity.class);
							startActivity(in);
						}

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public int getLayout() {
		// TODO Auto-generated method stub
		return R.layout.fragment_new_login;
	}

}
