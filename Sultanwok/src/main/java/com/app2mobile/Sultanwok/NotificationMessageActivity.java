package com.app2mobile.Sultanwok;

import java.util.List;

import com.app2mobile.metadata.RestaurantDetailMetadata;
import com.app2mobile.utiles.AppUtiles;
import com.app2mobile.utiles.ConstantsUrl;
import com.google.gson.Gson;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NotificationMessageActivity extends Activity {
	private RestaurantDetailMetadata restaurantDetailData;
	private SharedPreferences mRestaurantDetailPrefs;
	private String phoneStr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
		// setFinishOnTouchOutside(false);
		// }
		String msg = null, state = null;
		getWindow().setLayout(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		mRestaurantDetailPrefs = getSharedPreferences(
				ConstantsUrl.RESTAURANTDETAILPREFS, 0);
		Bundle extras = getIntent().getExtras();
		if (!extras.isEmpty()) {
			msg = extras.getString("msg");
			state = extras.getString("status");
		}
		setContentView(R.layout.activity_notification);

		Gson gson = new Gson();
		String restaurantDetail = mRestaurantDetailPrefs.getString(
				ConstantsUrl.RESTAURANT_DETAILS, "");
		restaurantDetailData = gson.fromJson(restaurantDetail,
				RestaurantDetailMetadata.class);
		if (restaurantDetailData != null) {
			phoneStr = restaurantDetailData.getmStoreNumber();
		}
		TextView msgTxt = (TextView) findViewById(R.id.text);
		msgTxt.setText(msg);
		ImageView cancel = (ImageView) findViewById(R.id.cancel);
		LinearLayout callLayout = (LinearLayout) findViewById(R.id.callLayout);
		ImageView call = (ImageView) findViewById(R.id.call);
		if (state != null && state.equals("1")) {
			call.setVisibility(View.GONE);
			callLayout.setVisibility(View.GONE);
		}
		cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		call.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE))
						.getLine1Number() == null) {
					// no phone
					AppUtiles.getInstance().showToast(getCurrentFocus(),
							getString(R.string.phone_number_sim));
				} else {
					if (phoneStr != null && !phoneStr.equals("")
							&& !phoneStr.equalsIgnoreCase("null")) {
						Intent callIntent = new Intent(Intent.ACTION_CALL);
						callIntent.setData(Uri.parse("tel:"
								+ phoneStr));
						startActivity(callIntent);
					}
				}

			}
		});

	}

	public static boolean isIntentAvailable(Context context, String action) {
		final PackageManager packageManager = context.getPackageManager();
		final Intent intent = new Intent(Intent.ACTION_CALL);
		List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
				PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}
}
