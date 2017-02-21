package com.app2mobile.utiles;

import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;

public class AppUtiles {
	public HashMap<String, Integer> attributeImageList = new HashMap<String, Integer>();
	private static AppUtiles instance = null;

	public static AppUtiles getInstance() {
		if (instance == null) {
			instance = new AppUtiles();
		}
		return instance;
	}

	/**
	 * Animations
	 * 
	 * @return
	 */
	public Animation inFromRightAnimation() {

		Animation inFromRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, +1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromRight.setDuration(500);
		inFromRight.setFillAfter(true);
		inFromRight.setInterpolator(new AccelerateInterpolator());
		return inFromRight;
	}

	/**
	 * 
	 * @return
	 */
	public Animation outToRightAnimation() {
		Animation outtoRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, +1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		outtoRight.setDuration(500);
		outtoRight.setFillAfter(true);
		outtoRight.setInterpolator(new AccelerateInterpolator());
		return outtoRight;
	}

	/**
	 * 
	 * @param v
	 * @param mContext
	 */
	public  void setCartItems(TextView v, Context mContext) {
		int items = 0;
		try {
				DatabaseHelper helper = DatabaseHelper.newInstance(mContext);
				helper.openDataBase();
				items = helper.getTotalCartItems();
			helper.close();

		} catch (Exception e) {
			e.printStackTrace();
			items = 0;
		}
		v.setText(String.valueOf(items));
	}

	/**
	 * 
	 */
	public  void clearPrefrence(SharedPreferences prefs) {
		SharedPreferences.Editor prefs_editor = prefs.edit();
		prefs_editor.clear();
		prefs_editor.commit();
	}

	/**
	 * 
	 * @param bitmap
	 * @return
	 */
	public  Bitmap getCroppedBitmap(Bitmap bitmap) {

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xffffffff;// 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, Math.min(bitmap.getWidth(),
				bitmap.getHeight()), Math.min(bitmap.getWidth(),
				bitmap.getHeight()));

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);

		canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
				Math.min(bitmap.getWidth() / 2, bitmap.getHeight() / 2) - 8,
				paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));

		Paint mPaint = new Paint();
		mPaint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(12);
		canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
				Math.min(bitmap.getWidth() / 2, bitmap.getHeight() / 2) - 8,
				paint);
		return output;
	}

	public  void productDetailsAttributesImageList() {
		// attributeImageList.put("Greens", R.drawable.ic_greens);
		// attributeImageList.put("Gourmet", R.drawable.ic_gourmet);
		// attributeImageList.put("Dressing", R.drawable.ic_dressing);
		// attributeImageList.put("Protein", R.drawable.ic_protein);
		// attributeImageList.put("Throw-ins", R.drawable.ic_throw_ins);
	}

	// public static JSONObject convertInJsonFormat(
	// ArrayList<OrderItemsMetadata> cartItemsList,
	// ArrayList<CheckOutMetadata> dataList, String couponCode,
	// String pincode, GregorianCalendar dateTime, String shipping,
	// String inst, int status, String custId) {
	// try {
	// JSONObject mainJsonObject = new JSONObject();
	// mainJsonObject.put("zip_code", pincode);
	// mainJsonObject.put("deliverytime",
	// new SimpleDateFormat("H:mm").format(dateTime.getTime()));
	// mainJsonObject.put("couponcode", couponCode);
	// mainJsonObject.put("shipping", shipping.toLowerCase());
	// mainJsonObject.put("splInst", inst);
	// mainJsonObject.put("cust_id", custId);
	// JSONArray productJsonArray = new JSONArray();
	// if (status == 1) {
	// for (int i = 0; i < cartItemsList.size(); i++) {
	//
	// JSONObject productJsonObject = new JSONObject();
	//
	// productJsonObject.put("id", cartItemsList.get(i)
	// .getProductSku());
	// productJsonObject.put("inst", cartItemsList.get(i)
	// .getInst());
	// productJsonObject.put("qty", cartItemsList.get(i)
	// .getQuantity());
	// ArrayList<ProductAttributesMetadata> data = cartItemsList
	// .get(i).getpCustomFields();
	// JSONArray attributesArray = new JSONArray();
	// if (data != null) {
	// for (int l = 0; l < data.size(); l++) {
	// if (data.get(l) != null) {
	// ArrayList<ProductOptionsMetadata> productOptions = data
	// .get(l).getpOptionsList();
	//
	// if (productOptions != null) {
	// for (int m = 0; m < productOptions.size(); m++) {
	// String sku = productOptions.get(m)
	// .getpSKU();
	//
	// if (sku != null && !sku.equals("")
	// && !sku.equals("null")) {
	// attributesArray.put(sku);
	// }
	// }
	// }
	// }
	// }
	// }
	// productJsonObject.put("custom", attributesArray);
	// productJsonArray.put(productJsonObject);
	// }
	// } else if (status == 2) {
	// for (int i = 0; i < dataList.size(); i++) {
	//
	// JSONObject productJsonObject = new JSONObject();
	// productJsonObject
	// .put("id", dataList.get(i).getProductSKU());
	// productJsonObject.put("inst", dataList.get(i)
	// .getSpecialInstruction());
	// productJsonObject.put("qty", dataList.get(i).getQuantity());
	// JSONArray attributesArray = new JSONArray();
	//
	// ArrayList<String> skuList = dataList.get(i)
	// .getProductSKUList();
	//
	// if (skuList != null && !skuList.equals("")
	// && !skuList.equals("null")) {
	//
	// for (int j = 0; j < skuList.size(); j++) {
	//
	// attributesArray.put(skuList.get(j));
	// }
	// }
	// productJsonObject.put("custom", attributesArray);
	// productJsonArray.put(productJsonObject);
	// }
	// }
	// mainJsonObject.put("product", productJsonArray);
	//
	// return mainJsonObject;
	// } catch (JSONException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return null;
	// }

	public  void showToast(View context, String message) {
//		Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
//		toast.setGravity(Gravity.CENTER, 5, 5);
		Snackbar snack=Snackbar.make(context,message, Snackbar.LENGTH_LONG);
		View view = snack.getView();
		FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
		params.gravity = Gravity.TOP;
		//view.setBackgroundColor(color.app_theme_color);
		view.setLayoutParams(params);
		//toast.show();
		snack.show();
	}

	// public static void parseResponse(String result, Context mContext,
	// SharedPreferences mAppPrefs, Dialog expressCheckout, int value,
	// String CouponCode, boolean guestStatus) {
	// if (result != null) {
	// try {
	// JSONObject mainJsonObject = new JSONObject(result);
	// if (mainJsonObject.has("status")) {
	// String status = JsonParser.getkeyValue_Str(mainJsonObject,
	// "status");
	// if (status != null && status.equals("1")) {
	// String sessionId = JsonParser.getkeyValue_Str(
	// mainJsonObject, "session");
	// String totalAmout = JsonParser.getkeyValue_Str(
	// mainJsonObject, "finalamount");
	// String discountAmout = JsonParser.getkeyValue_Str(
	// mainJsonObject, "discountamount");
	// String grandTotal = JsonParser.getkeyValue_Str(
	// mainJsonObject, "grandtotal");
	// String shippingAmount = JsonParser.getkeyValue_Str(
	// mainJsonObject, "shippingamount");
	// SharedPreferences.Editor prefs_editor = mAppPrefs
	// .edit();
	// prefs_editor.putString(
	// ConstantsUrl.CHECKOUT_SESSION_ID, sessionId);
	// prefs_editor.putString(ConstantsUrl.TOTALAMOUNT,
	// totalAmout);
	// prefs_editor.putString(ConstantsUrl.GRANDAMOUNT,
	// grandTotal);
	// prefs_editor.putString(ConstantsUrl.DISCOUNTAMOUNT,
	// discountAmout);
	// prefs_editor.putString(ConstantsUrl.SHIPPINGAMOUNT,
	// shippingAmount);
	// prefs_editor.putString(ConstantsUrl.COUPONCODE,
	// CouponCode);
	// prefs_editor.commit();
	// if (expressCheckout != null)
	// expressCheckout.dismiss();
	//
	// Intent checkOutIntent;
	// checkOutIntent = new Intent(mContext,
	// PaymentDetailPart1Activity.class);
	// checkOutIntent.putExtra("isCart", value);
	// checkOutIntent.putExtra("isGuest", guestStatus);
	// mContext.startActivity(checkOutIntent);
	//
	// } else if (status != null && status.equals("0")) {
	// if (mainJsonObject.has("msg"))
	// AppUtiles.showToast(mContext, JsonParser
	// .getkeyValue_Str(mainJsonObject, "msg"));
	// }
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// AppUtiles.showToast(mContext, "Problem occured!");
	// }
	// } else {
	// AppUtiles.showToast(mContext, "Problem occured!");
	// }
	// }

	// public static Bitmap getBitmap(String url, Context mContext) {
	//
	// try {
	// DefaultHttpClient httpClient = new DefaultHttpClient();
	// HttpGet request = new HttpGet(url);
	// HttpResponse response = httpClient.execute(request);
	// HttpEntity entity = response.getEntity();
	// InputStream is = entity.getContent();
	// Bitmap bitmap = BitmapFactory.decodeStream(is);
	// is.close();
	// return bitmap;
	// } catch (Throwable ex) {
	// ex.printStackTrace();
	// if (ex instanceof OutOfMemoryError)
	// return null;
	// }
	// // catch (Exception e) {
	// // e.printStackTrace();
	// // }
	// return null;
	// }
	// public static Bitmap getBitmap(String url, Context mContext) {
	// // from web
	// try {
	// // Create a KeyStore containing our trusted CAs
	// String keyStoreType = KeyStore.getDefaultType();
	// KeyStore keyStore = KeyStore.getInstance(keyStoreType);
	// keyStore.load(null, null);
	// TrustManager tm = new X509TrustManager() {
	// @Override
	// public void checkClientTrusted(X509Certificate[] arg0,
	// String arg1) throws CertificateException {
	// // TODO Auto-generated method stub
	// }
	// @Override
	// public void checkServerTrusted(X509Certificate[] arg0,
	// String arg1) throws CertificateException {
	// // TODO Auto-generated method stub
	// }
	// @Override
	// public X509Certificate[] getAcceptedIssuers() {
	// // TODO Auto-generated method stub
	// return null;
	// }
	// };
	// // Create an SSLContext that uses our TrustManager
	// SSLContext context = SSLContext.getInstance("TLS");
	// context.init(null, new TrustManager[] { tm },
	// new java.security.SecureRandom());
	// HostnameVerifier hostnameVerifier = new HostnameVerifier() {
	// @Override
	// public boolean verify(String hostname, SSLSession session) {
	// HostnameVerifier hv = HttpsURLConnection
	// .getDefaultHostnameVerifier();
	// return true;
	// }
	// };
	// Bitmap bitmap = null;
	// URL imageUrl = new URL(url);
	// HttpsURLConnection conn = (HttpsURLConnection) imageUrl
	// .openConnection();
	// //
	// conn.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
	// conn.setHostnameVerifier(hostnameVerifier);
	// conn.setSSLSocketFactory(context.getSocketFactory());
	// conn.setConnectTimeout(30000);
	// conn.setReadTimeout(30000);
	// conn.setInstanceFollowRedirects(true);
	// if (conn != null) {
	// InputStream inputStream = null;
	// try {
	// inputStream = conn.getInputStream();
	// bitmap = BitmapFactory.decodeStream(inputStream);
	// return bitmap;
	// } finally {
	// if (inputStream != null) {
	// inputStream.close();
	// }
	// // entity.consumeContent();
	// }
	// }
	// } catch (Throwable ex) {
	// ex.printStackTrace();
	// if (ex instanceof OutOfMemoryError)
	// return null;
	// }
	// return null;
	// }

	public  String getStateName(String state, Context mContext) {
		String stateCode = "-1";
		String query = "select state_name from tbl_state where country_code='US' AND state_code='"
				+ state + "'";
		DatabaseHelper helper = DatabaseHelper.newInstance(mContext);
		helper.openDataBase();
		SQLiteDatabase sqliteObj = helper.getReadableDatabase();
		Cursor cursorObj = sqliteObj.rawQuery(query, null);
		if (cursorObj != null) {
			if (cursorObj.moveToFirst()) {
				do {
					stateCode = cursorObj.getString(0);
				} while (cursorObj.moveToNext());
			}
			cursorObj.close();
		}
		helper.close();
		return stateCode;
	}
}
