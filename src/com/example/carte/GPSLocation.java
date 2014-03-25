package com.example.carte;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

public class GPSLocation extends Service implements LocationListener {

	private final Context appContext;

	// flag for GPS status
	boolean isGPSEnabled = false;

	// flag for network status
	boolean isNetworkEnabled = false;

	boolean canGetLocation = false;

	Location location; // location
	private double latitude; // latitude
	private double longitude; // longitude

	private static final float MIN_DIST_FOR_UPDATES = 0.21f; // 10 meters

	private static final long MIN_TIME_BW_UPDATES = 500 * 60 * 1; // 30 secs

	protected LocationManager locationManager;

	public GPSLocation(Context context) {
		this.appContext = context;
		getLocation();
	}

	public Location getLocation() {
		try {
			locationManager = (LocationManager) appContext
					.getSystemService(LOCATION_SERVICE);

			// get GPS status
			isGPSEnabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);

			// get network status
			isNetworkEnabled = locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			if (isNetworkEnabled) {
				this.canGetLocation = true;
				locationManager.requestLocationUpdates(
						LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES,
						MIN_DIST_FOR_UPDATES, this);

				if (locationManager != null) {
					location = locationManager
							.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
					if (location != null) {
						latitude = location.getLatitude();
						longitude = location.getLongitude();
						Log.d("Network based latitude:",
								Double.toString(latitude) + "\nLongitute:"
										+ Double.toString(longitude));
					}
				}
			}

			if (isGPSEnabled) {
				this.canGetLocation = true;
				if (location == null) {
					locationManager.requestLocationUpdates(
							LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES,
							MIN_DIST_FOR_UPDATES, this);
					Log.d("GPS Enabled", "GPS Enabled");
					Log.d("GPS based latitude:", Double.toString(latitude)
							+ "\nLongitute:" + Double.toString(longitude));
					if (locationManager != null) {
						location = locationManager
								.getLastKnownLocation(LocationManager.GPS_PROVIDER);
						if (location != null) {
							latitude = location.getLatitude();
							longitude = location.getLongitude();
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return location;
	}

	public double getLatitude() {
		if (location != null) {
			latitude = location.getLatitude();
		}
		// Log.i("Location"," is null");
		// return latitude
		return latitude;
	}

	/**
	 * Function to get longitude
	 * */
	public double getLongitude() {
		if (location != null) {
			longitude = location.getLongitude();
		}
		Log.i("Location", " is null");
		// return longitude
		return longitude;
	}

	public void showSettingsAlert() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(appContext);
		alertDialog.setTitle("GPS Settings");
		alertDialog
				.setMessage("GPS is not enabled.\nDo you want to go to settings menu?");
		// On pressing Settings button
		alertDialog.setPositiveButton("Settings",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(
								Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						appContext.startActivity(intent);
					}
				});
		// on pressing cancel button
		alertDialog.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

		alertDialog.show();
	}

	public boolean canGetLocation() {
		return this.canGetLocation;
	}

	public void stopUsingGPS() {
		if (locationManager != null) {
			locationManager.removeUpdates(GPSLocation.this);
		}
	}

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
}