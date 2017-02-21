package com.app2mobile.geofence;

import java.util.ArrayList;
import java.util.Map;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

public class GeofenceUtiles implements ConnectionCallbacks,
		OnConnectionFailedListener, ResultCallback<Status> {
	private Context mContext;
	protected GoogleApiClient mGoogleApiClient;
	protected ArrayList<Geofence> mGeofenceList;
	protected static final String TAG = "creating-and-monitoring-geofences";
	private PendingIntent mGeofencePendingIntent;
	private static GeofenceUtiles instance = null;

	public static GeofenceUtiles getInstance(Context context) {
		if (instance == null) {
			instance = new GeofenceUtiles(context);
		}
		return instance;
	}

	public GeofenceUtiles(Context mContext) {
		// super();
		this.mContext = mContext;
		buildGoogleApiClient();
		populateGeofenceList();
	}

	@Override
	public void onResult(Status arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		// TODO Auto-generated method stub
		if (connectionResult.hasResolution()) {
			try {
				// Start an Activity that tries to resolve the error
				connectionResult.startResolutionForResult((Activity) mContext,
						Constants.CONNECTION_FAILURE_RESOLUTION_REQUEST);
			} catch (IntentSender.SendIntentException e) {
				e.printStackTrace();
			}
		} else {
			// showErrorDialog(connectionResult.getErrorCode());
			Log.i(TAG, "Location services connection failed with code "
					+ connectionResult.getErrorCode());
		}
	}

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		addGeofencesButtonHandler();
	}

	@Override
	public void onConnectionSuspended(int arg0) {
		// TODO Auto-generated method stub
		if (null != mGeofencePendingIntent) {
			LocationServices.GeofencingApi.removeGeofences(mGoogleApiClient,
					mGeofencePendingIntent);
		}

	}

	/**
	 * Builds a GoogleApiClient. Uses the {@code #addApi} method to request the
	 * LocationServices API.
	 */
	protected synchronized void buildGoogleApiClient() {
		mGoogleApiClient = new GoogleApiClient.Builder(mContext)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addApi(LocationServices.API).build();
		mGoogleApiClient.connect();
	}

	public void populateGeofenceList() {
		mGeofenceList = new ArrayList<Geofence>();
		for (Map.Entry<String, LatLng> entry : Constants.RESTAURANT_AREA_LANDMARKS
				.entrySet()) {

			mGeofenceList.add(new Geofence.Builder()
			// Set the request ID of the geofence. This is a string to identify
			// this
			// geofence.
					.setRequestId(entry.getKey())

					// Set the circular region of this geofence.
					.setCircularRegion(entry.getValue().latitude,
							entry.getValue().longitude,
							Constants.GEOFENCE_RADIUS_IN_METERS)

					// Set the expiration duration of the geofence. This
					// geofence gets automatically
					// removed after this period of time.
					.setExpirationDuration(
							Constants.GEOFENCE_EXPIRATION_IN_HOURS)

					// Set the transition types of interest. Alerts are only
					// generated for these
					// transition. We track entry and exit transitions in this
					// sample.
					.setTransitionTypes(
							Geofence.GEOFENCE_TRANSITION_ENTER
									| Geofence.GEOFENCE_TRANSITION_EXIT)

					// Create the geofence.
					.build());
		}
	}

	/**
	 * Builds and returns a GeofencingRequest. Specifies the list of geofences
	 * to be monitored. Also specifies how the geofence notifications are
	 * initially triggered.
	 */
	private GeofencingRequest getGeofencingRequest() {
		GeofencingRequest.Builder builder = new GeofencingRequest.Builder();

		// The INITIAL_TRIGGER_ENTER flag indicates that geofencing service
		// should trigger a
		// GEOFENCE_TRANSITION_ENTER notification when the geofence is added and
		// if the device
		// is already inside that geofence.
		builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);

		// Add the geofences to be monitored by geofencing service.
		builder.addGeofences(mGeofenceList);

		// Return a GeofencingRequest.
		return builder.build();
	}

	/**
	 * Adds geofences, which sets alerts to be notified when the device enters
	 * or exits one of the specified geofences. Handles the success or failure
	 * results returned by addGeofences().
	 */
	public void addGeofencesButtonHandler() {
		// if (!mGoogleApiClient.isConnected()) {
		// Toast.makeText(this, getString(R.string.not_connected),
		// Toast.LENGTH_SHORT).show();
		// return;
		// }

		try {
			LocationServices.GeofencingApi.addGeofences(mGoogleApiClient,
			// The GeofenceRequest object.
					getGeofencingRequest(),
					// A pending intent that that is reused when calling
					// removeGeofences(). This
					// pending intent is used to generate an intent when a
					// matched geofence
					// transition is observed.
					getGeofencePendingIntent()).setResultCallback(this); // Result
																			// processed
																			// in
																			// onResult().
		} catch (SecurityException securityException) {
			// Catch exception generated if the app does not use
			// ACCESS_FINE_LOCATION permission.
			logSecurityException(securityException);
		}
	}

	private void logSecurityException(SecurityException securityException) {
		Log.e(TAG, "Invalid location permission. "
				+ "You need to use ACCESS_FINE_LOCATION with geofences",
				securityException);
	}

	/**
	 * Gets a PendingIntent to send with the request to add or remove Geofences.
	 * Location Services issues the Intent inside this PendingIntent whenever a
	 * geofence transition occurs for the current list of geofences.
	 * 
	 * @return A PendingIntent for the IntentService that handles geofence
	 *         transitions.
	 */
	private PendingIntent getGeofencePendingIntent() {
		// Reuse the PendingIntent if we already have it.
		if (mGeofencePendingIntent != null) {
			return mGeofencePendingIntent;
		}
		Intent intent = new Intent(mContext,
				GeofenceTransitionsIntentService.class);
		// We use FLAG_UPDATE_CURRENT so that we get the same pending intent
		// back when calling
		// addGeofences() and removeGeofences().
		return PendingIntent.getService(mContext, 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
	}

	// *****************************************************************************//
	// private void showErrorDialog(int errorCode) {
	//
	// // Get the error dialog from Google Play services
	// Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(errorCode,
	// (Activity) mContext,
	// LocationUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST);
	//
	// // If Google Play services can provide an error dialog
	// if (errorDialog != null) {
	//
	// // Create a new DialogFragment in which to show the error dialog
	// ErrorDialogFragment errorFragment = new ErrorDialogFragment();
	//
	// // Set the dialog in the DialogFragment
	// errorFragment.setDialog(errorDialog);
	//
	// // Show the error dialog in the DialogFragment
	// errorFragment.show(
	// ((FragmentActivity) mContext).getSupportFragmentManager(),
	// LocationUtils.APPTAG);
	// }
	// }
}
