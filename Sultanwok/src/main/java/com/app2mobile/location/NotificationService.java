package com.app2mobile.location;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.text.format.DateFormat;

import com.app2mobile.Sultanwok.CategoryActivity;
import com.app2mobile.Sultanwok.R;
import com.app2mobile.utiles.DatabaseHelper;

public class NotificationService extends Service {
	private NotificationManager mNotificationManager;
	public static final String INTENT_NOTIFY = "com.a2m.service.INTENT_NOTIFY";
	public static final int NOTIFICATION_ID = 1003;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mNotificationManager = (NotificationManager) this
				.getSystemService(Context.NOTIFICATION_SERVICE);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		// If this service was started by out AlarmTask intent then we want to
		// show our notification
		// if (intent.getBooleanExtra(INTENT_NOTIFY, false))
		if(getCartItemsDate()>0)
		showNotification("You have "+getCartItemsDate()+" product in cart.");

		// We don't care if this service is stopped as we have already delivered
		// our notification
		return START_NOT_STICKY;

	}

	private void showNotification(String msg) {
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, CategoryActivity.class), 0);

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				this).setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle(getString(R.string.app_name))
				.setAutoCancel(true)
				.setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
				.setContentText(msg);

		mBuilder.setContentIntent(contentIntent);
		mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
		stopSelf();

	}

	private int getCartItemsDate() {
		int quantity = 0;
		GregorianCalendar calender = new GregorianCalendar();
		calender.add(Calendar.DAY_OF_MONTH, -2);
		DatabaseHelper helperObj = DatabaseHelper.newInstance(getBaseContext());
		SQLiteDatabase sqlietObj = helperObj.openDatabaseInReadMode();
		String query = "select * from " + DatabaseHelper.TB_CheckOut
				+ " where " + DatabaseHelper.CheckOut_CreatedOn + "<'"
				+ DateFormat.format("yyyy-MM-dd", calender.getTime())+"'";
		

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
