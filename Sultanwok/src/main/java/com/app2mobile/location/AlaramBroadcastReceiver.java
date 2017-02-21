package com.app2mobile.location;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NotificationCompat;
import android.text.format.DateFormat;

import com.app2mobile.Sultanwok.MainActivity;
import com.app2mobile.Sultanwok.R;
import com.app2mobile.utiles.DatabaseHelper;

public class AlaramBroadcastReceiver extends BroadcastReceiver {
	public static final int NOTIFICATION_ID = 1003;
	public static final String INTENT_NOTIFY = "com.a2m.service.INTENT_NOTIFY";

	// private NotificationManager mNotificationManager;
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		if (intent.getBooleanExtra(INTENT_NOTIFY, false)
				&& getCartItemsDate(context) > 0) {
			showNotification("You have " + getCartItemsDate(context)
					+ " product in cart.", context);
		}
	}

	private void showNotification(String msg, Context mContext) {
		NotificationManager mNotificationManager = (NotificationManager) mContext
				.getSystemService(Context.NOTIFICATION_SERVICE);
		PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0,
				new Intent(mContext, MainActivity.class), 0);

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				mContext).setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle(mContext.getString(R.string.app_name))
				.setAutoCancel(true)
				.setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
				.setContentText(msg);

		mBuilder.setContentIntent(contentIntent);
		mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
		// stopSelf();

	}

	private int getCartItemsDate(Context mContext) {
		int quantity = 0;
		GregorianCalendar calender = new GregorianCalendar();
		calender.add(Calendar.DAY_OF_MONTH, -2);
		DatabaseHelper helperObj = DatabaseHelper.newInstance(mContext);
		SQLiteDatabase sqlietObj = helperObj.openDatabaseInReadMode();
		String query = "select * from " + DatabaseHelper.TB_CheckOut
				+ " where " + DatabaseHelper.CheckOut_CreatedOn + "<'"
				+ DateFormat.format("yyyy-MM-dd", calender.getTime()) + "'";

		Cursor cursorObj = sqlietObj.rawQuery(query, null);
		if (cursorObj != null) {
			quantity = cursorObj.getCount();
			// if (cursorObj.moveToFirst()) {
			// do {
			//
			// } while (cursorObj.moveToNext());
			// }
			cursorObj.close();
		}
		helperObj.close();

		return quantity;

	}
}
