package com.app2mobile.Sultanwok;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.app2mobile.geofence.GeofenceUtiles;
import com.app2mobile.Sultanwok.fragment.CategoryFragment;
import com.app2mobile.location.AlaramBroadcastReceiver;
import com.app2mobile.location.NotificationService;
import com.app2mobile.utiles.ConstantsUrl;

/**
 * Category activity for add category fragment
 * 
 */
public class CategoryActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		// adding category fragment in Activity
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);   
		if (savedInstanceState == null) {

			
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.content_frame, CategoryFragment.newInstance())
					// .addToBackStack("category")
					.commit();

		}
		/*
		 * set a Alarm using AlarmManager to check
		 */
		setAlarmManagerForNotification();
		/*
		 * inititalize class object.
		 * GeofenceUtiles class for getting a notification when user will near
		 * to restaurant
		 */
		GeofenceUtiles.getInstance(this);

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		// super.onActivityResult(requestCode, resultCode, data);
		System.out.println("return text back.......");
		mNavigationAdapter.notifyDataSetChanged();
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case ConstantsUrl.LOGIN:

				break;

			case ConstantsUrl.ORDERHISTORY:

				Intent orderHistoryIntent = new Intent(getBaseContext(),
						OrderHistoryActivity.class);
				startActivity(orderHistoryIntent);
				break;
			case ConstantsUrl.PROFILE:
				System.out.println("profile..........");
				Intent intent = new Intent(getBaseContext(),
						UserProfileActivity.class);
				startActivity(intent);
				break;
			}
		}
	}

	/**
	 * Verify that Google Play services is available before making a request.
	 * 
	 * @return true if Google Play services is available, otherwise false
	 */
	/*
	 * private boolean servicesConnected() {
	 * 
	 * // Check that Google Play services is available int resultCode =
	 * GooglePlayServicesUtil .isGooglePlayServicesAvailable(this);
	 * 
	 * // If Google Play services is available if (ConnectionResult.SUCCESS ==
	 * resultCode) { // In debug mode, log the status //
	 * Log.d(LocationUtils.APPTAG, //
	 * getString(R.string.play_services_available));
	 * 
	 * // Continue return true; // Google Play services was not available for
	 * some reason } else { // Display an error dialog Dialog dialog =
	 * GooglePlayServicesUtil.getErrorDialog(resultCode, this, 0); if (dialog !=
	 * null) { ErrorDialogFragment errorFragment = new ErrorDialogFragment();
	 * errorFragment.setDialog(dialog);
	 * errorFragment.show(getSupportFragmentManager(), LocationUtils.APPTAG); }
	 * return false; } }
	 */

	/**
	 * Define a DialogFragment to display the error dialog generated in
	 * showErrorDialog.
	 */
	public static class ErrorDialogFragment extends DialogFragment {

		// Global field to contain the error dialog
		private Dialog mDialog;

		/**
		 * Default constructor. Sets the dialog field to null
		 */
		public ErrorDialogFragment() {
			super();
			mDialog = null;
		}

		/**
		 * Set the dialog to display
		 * 
		 * @param dialog
		 *            An error dialog
		 */
		public void setDialog(Dialog dialog) {
			mDialog = dialog;
		}

		/*
		 * This method must return a Dialog to the DialogFragment.
		 */
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			return mDialog;
		}
	}
/**
 * Define to set a periodic Alarm  to fire a intent
 */
	private void setAlarmManagerForNotification() {

		if (!mAppPref.getBoolean(ConstantsUrl.IS_ALARM_START, false)) {
			/**
			 * for start service
			 */
			Intent myIntent = new Intent(this, AlaramBroadcastReceiver.class);
			myIntent.putExtra(NotificationService.INTENT_NOTIFY, true);
			// PendingIntent pendingIntent = PendingIntent.getService(this, 0,
			// myIntent, 0);
			PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
					myIntent, 0);
			AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
			Calendar Calendar_Object = Calendar.getInstance();
			Calendar_Object.set(Calendar.HOUR_OF_DAY, 13);
			alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
					Calendar_Object.getTimeInMillis(),
					AlarmManager.INTERVAL_DAY, pendingIntent);
			Editor editPrefs = mAppPref.edit();
			editPrefs.putBoolean(ConstantsUrl.IS_ALARM_START, true);
			editPrefs.commit();

		}
	}

	@Override
	protected int getLayoutResource() {
		// TODO Auto-generated method stub
		return R.layout.activity_home;
	}

}
