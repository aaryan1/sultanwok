package com.app2mobile.location;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.app2mobile.geofence.GeofenceUtiles;
import com.app2mobile.utiles.ConstantsUrl;

public class BootupCompleteBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if (intent != null && intent.getAction() != null
				&& intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
		
			GeofenceUtiles.getInstance(context);
			setAlarmManagerForNotification(context);

		}
		// System.out.println("bootup notification ");
	}

	private void setAlarmManagerForNotification(Context mContext) {
		SharedPreferences mAppPrefs = mContext.getSharedPreferences(
				ConstantsUrl.APP_PREF, 0);
		/**
		 * for start service
		 */
		Intent myIntent = new Intent(mContext, AlaramBroadcastReceiver.class);
		myIntent.putExtra(NotificationService.INTENT_NOTIFY, true);
		// PendingIntent pendingIntent = PendingIntent.getService(mContext, 0,
		// myIntent, 0);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0,
				myIntent, 0);
		AlarmManager alarmManager = (AlarmManager) mContext
				.getSystemService(Context.ALARM_SERVICE);

		/*
		 * The following sets the Alarm in the specific time by getting the long
		 * value of the alarm date time which is in calendar object by calling
		 * the getTimeInMillis(). Since Alarm supports only long value , we're
		 * using this method.
		 */
		Calendar Calendar_Object = Calendar.getInstance();
		Calendar_Object.set(Calendar.HOUR_OF_DAY, 13);
		alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
				Calendar_Object.getTimeInMillis(), AlarmManager.INTERVAL_DAY,
				pendingIntent);
		Editor editPrefs = mAppPrefs.edit();
		editPrefs.putBoolean(ConstantsUrl.IS_ALARM_START, true);
		editPrefs.commit();
	}

}
