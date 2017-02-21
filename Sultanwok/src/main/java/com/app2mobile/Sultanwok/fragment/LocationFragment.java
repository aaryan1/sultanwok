package com.app2mobile.Sultanwok.fragment;

import android.os.Bundle;
import android.view.View;

import com.app2mobile.Sultanwok.R;
import com.app2mobile.utiles.ConstantsUrl;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationFragment extends BaseFragment {

	private GoogleMap mMap;
	private static LocationFragment instance;

	public static LocationFragment newInstance() {
		if (instance == null)
			instance = new LocationFragment();
		return instance;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);

		int available = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getActivity());
		if (available == ConnectionResult.SUCCESS) {

			setUpMapIfNeeded();
		}
		mTitleTxt.setText(getString(R.string.location));
		mCartCountTxt.setVisibility(View.GONE);
		mCartImg.setVisibility(View.GONE);
		mEditImg.setVisibility(View.GONE);

	}

	@Override
	public int getLayout() {
		// TODO Auto-generated method stub
		return R.layout.fragment_location;
	}

	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the
		// map.
		if (mMap == null) {

			mMap = ((SupportMapFragment) getChildFragmentManager()
					.findFragmentById(R.id.map)).getMap();

			// Check if we were successful in obtaining the map.

			if (mMap != null) {
				UiSettings setting = mMap.getUiSettings();
				setting.setCompassEnabled(true);
				setting.setZoomControlsEnabled(true);
				setting.setMyLocationButtonEnabled(true);
				setting.setAllGesturesEnabled(true);

				// Construct a CameraPosition focusing on Mountain View and
				// animate the camera to that position.
				CameraPosition cameraPosition = new CameraPosition.Builder()
						.target(new LatLng(ConstantsUrl.LATITUDE,
								ConstantsUrl.LONGITUDE)) // Sets the
						// center of
						// the map
						// to
						// Mountain
						// View
						.zoom(17) // Sets the zoom
						.bearing(90) // Sets the orientation of the camera to
										// east
						.tilt(30) // Sets the tilt of the camera to 30 degrees
						.build(); // Creates a CameraPosition from the builder
				mMap.animateCamera(CameraUpdateFactory
						.newCameraPosition(cameraPosition));
				Marker marker = mMap
						.addMarker(new MarkerOptions()
								.position(
										new LatLng(ConstantsUrl.LATITUDE,
												ConstantsUrl.LONGITUDE))
								.title(getString(R.string.location_title))
								.snippet(getString(R.string.location_desc))
								.icon(BitmapDescriptorFactory
										.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
			}
		}
	}

}
