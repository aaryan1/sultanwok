package com.app2mobile.gcm;

import org.json.JSONObject;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.app2mobile.Sultanwok.MainActivity;
import com.app2mobile.Sultanwok.NotificationMessageActivity;
import com.app2mobile.Sultanwok.R;
import com.app2mobile.utiles.JsonParser;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GCMNotificationIntentService extends IntentService {

	public static final int NOTIFICATION_ID = 1;
	private NotificationManager mNotificationManager;
	NotificationCompat.Builder builder;

	// private Handler mHandler = new Handler();

	public GCMNotificationIntentService() {
		super("GcmIntentService");
	}

	public static final String TAG = "GCMNotificationIntentService";

	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle extras = intent.getExtras();
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

		String messageType = gcm.getMessageType(intent);
		//	Toast.makeText(getApplicationContext(), messageType, 2).show();
		if (!extras.isEmpty()) {
			if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
					.equals(messageType)) {
			//	Toast.makeText(getApplicationContext(), extras.toString(), 2).show();

				sendNotification("Send error: " + extras.toString());
			} 
			else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
					.equals(messageType)) {
			//	Toast.makeText(getApplicationContext(), extras.toString(), 2).show();

				sendNotification("Deleted messages on server: "
						+ extras.toString());
			}
			else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
					.equals(messageType)) {
			//	Toast.makeText(getApplicationContext(), extras.toString(), 2).show();

				// for (int i = 0; i < 3; i++) {
				//
				// try {
				// Thread.sleep(5000);
				// } catch (InterruptedException e) {
				// }
				//
				// }
				Log.e("send notification", extras.getString("message"));
				sendNotification(extras.getString("message"));

			}
		}
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

	private void sendNotification(String msg) {

		try {
			JSONObject mainJson = new JSONObject(msg);
			if (mainJson.has("status")) {
				String message = JsonParser
						.getkeyValue_Str(mainJson, "message");
				String state = JsonParser.getkeyValue_Str(mainJson, "status");
				mNotificationManager = (NotificationManager) this
						.getSystemService(Context.NOTIFICATION_SERVICE);
				Intent notificationIntent = new Intent(this, MainActivity.class);
				// Construct a task stack.
				TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

				// Add the main Activity to the task stack as the parent.
				stackBuilder.addParentStack(MainActivity.class);

				// Push the content Intent onto the stack.
				stackBuilder.addNextIntent(notificationIntent);

				// Get a PendingIntent containing the entire back stack.
				PendingIntent notificationPendingIntent = stackBuilder
						.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

				NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
						this)
						.setSmallIcon(R.drawable.ic_launcher)
						.setContentTitle(getString(R.string.app_name))
						.setAutoCancel(true)
						.setStyle(
								new NotificationCompat.BigTextStyle()
										.bigText(message))
						.setContentText(message);

				mBuilder.setContentIntent(notificationPendingIntent);
				mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
				if (state != null && (state.equals("0") || state.equals("1"))) {
					displayDialog(message, this, state);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void displayDialog(final String msg, final Context context,
			String state) {
		synchronized (this) {
			Intent intent = new Intent(this, NotificationMessageActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
					| Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
			intent.putExtra("msg", msg);
			intent.putExtra("status", state);
			startActivity(intent);

		}

		// mHandler.post(new Runnable() {
		//
		// @Override
		// public void run() {
		// // TODO Auto-generated method stub
		// // AppUtiles.showToast(getBaseContext(), msg);
		//
		// }
		// });
	}
}