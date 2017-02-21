package com.app2mobile.location;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v4.app.NotificationCompat;

import com.app2mobile.Sultanwok.CategoryActivity;
import com.app2mobile.Sultanwok.R;
import com.app2mobile.utiles.ConstantsUrl;
import com.google.android.gms.location.FusedLocationProviderApi;

public class LocationReceiver extends BroadcastReceiver {
	private NotificationManager mNotificationManager;
	NotificationCompat.Builder builder;
	public static final int NOTIFICATION_ID = 1001;

	@Override
	public void onReceive(Context context, Intent intent) {

			Location location = (Location) intent.getExtras().get(
					FusedLocationProviderApi.KEY_LOCATION_CHANGED);
		
			if(location!=null)
			{
			Location restaurant = new Location("A");
			float distance = restaurant.distanceTo(location);
			location.setLatitude(ConstantsUrl.LATITUDE);
			location.setLongitude(ConstantsUrl.LONGITUDE);
			// distance=1530;
			if (distance <= 2000) {
				mNotificationManager = (NotificationManager) context
						.getSystemService(Context.NOTIFICATION_SERVICE);
				PendingIntent contentIntent = PendingIntent.getActivity(
						context, 0,
						new Intent(context, CategoryActivity.class), 0);

				NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
						context)
						.setSmallIcon(R.drawable.ic_launcher)
						.setContentTitle(context.getString(R.string.app_name))
						.setAutoCancel(true)
						.setStyle(
								new NotificationCompat.BigTextStyle()
										.bigText(context
												.getString(R.string.app_name)
												+ " is about to "
												+ String.format("%.2f",
														(distance / 1000))
												+ " km."))
						.setContentText(
								context.getString(R.string.app_name)
										+ " is about to "
										+ String.format("%.2f",
												(distance / 1000)) + " km.");

				mBuilder.setContentIntent(contentIntent);
				mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
			}
		}
	}

}
