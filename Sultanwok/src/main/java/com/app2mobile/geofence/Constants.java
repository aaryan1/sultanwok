package com.app2mobile.geofence;

import java.util.HashMap;

import com.app2mobile.utiles.ConstantsUrl;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.maps.model.LatLng;

public final class Constants {

    private Constants() {
    }

    public final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

//    public static final String PACKAGE_NAME = "com.google.android.gms.location.Geofence";
//
//    public static final String SHARED_PREFERENCES_NAME = PACKAGE_NAME + ".SHARED_PREFERENCES_NAME";
//
//    public static final String GEOFENCES_ADDED_KEY = PACKAGE_NAME + ".GEOFENCES_ADDED_KEY";

    /**
     * Used to set an expiration time for a geofence. After this amount of time Location Services
     * stops tracking the geofence.
     */
    public static final long GEOFENCE_EXPIRATION_IN_HOURS = Geofence.NEVER_EXPIRE;

    /**
     * For this sample, geofences expire after twelve hours.
     */
//    public static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS =
//            GEOFENCE_EXPIRATION_IN_HOURS * 60 * 60 * 1000;
    public static final float GEOFENCE_RADIUS_IN_METERS = 1500;//1609; // 1 mile, 1.6 km

    /**
     * Map for storing information about airports in the San Francisco bay area.
     */
    public static final HashMap<String, LatLng> RESTAURANT_AREA_LANDMARKS = new HashMap<String, LatLng>();
    static {
        // San Francisco International Airport.
    	RESTAURANT_AREA_LANDMARKS.put("Central Park", new LatLng(ConstantsUrl.LATITUDE,ConstantsUrl.LONGITUDE));

        // Googleplex.
//    	RESTAURANT_AREA_LANDMARKS.put("GOOGLE", new LatLng(28.461335, 77.077911));
    	//HUDA CITY Center 28.459206, 77.072374
//    	RESTAURANT_AREA_LANDMARKS.put("HUDA CIY CENTER", new LatLng(28.459206, 77.072374));
    }
}
