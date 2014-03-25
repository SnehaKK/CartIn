package com.example.carte;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	double latitudeVal;
	double longitudeVal;

	GPSLocation gps;

	public final static String ITEM_DETAILS = "com.example.carte.ITEM_DETAILS";
	public static String TAG = "com.example.carte.TAG";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		gps = new GPSLocation(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void gotoCart(View view) {

		String[] entry = new String[4];
		Intent intent = new Intent(this, CartDetails.class);
		EditText editText;
		editText = (EditText) findViewById(R.id.editStoreName);
		Boolean storeName = false;
		Boolean budgetValue = false;
		editText = (EditText) findViewById(R.id.editStoreName);
		entry[0] = editText.getText().toString();
		if (entry[0] != null && entry[0].length() > 0) {
			storeName = true;
		} else {
			storeName = false;
		}
		editText = (EditText) findViewById(R.id.editBudget);
		entry[1] = editText.getText().toString();
		Log.i("CartEApp", "Budget -" + entry[1]);
		if (entry[1].length() > 0) {
			if(entry[1].contains(".")) {
				budgetValue = false;
			}else if(Integer.parseInt(entry[1]) > 0){
				budgetValue = true;
			}
			
		} else {
			budgetValue = false;
		}
		if (!storeName && !budgetValue) {
			Log.i(TAG, "No store and budget");
			Toast toast = Toast.makeText(getApplicationContext(),
					"Enter Store and Budget.", Toast.LENGTH_LONG);
			toast.show();
			return;

		} else if (!storeName) {
			Log.i(TAG, "No store");
			Toast toast = Toast.makeText(getApplicationContext(),
					"Enter Store.", Toast.LENGTH_LONG);
			toast.show();
			return;
		} else if (!budgetValue) {
			Log.i(TAG, "No budget or budget not a whole number");
			Toast toast = Toast.makeText(getApplicationContext(),
					"Enter Budget", Toast.LENGTH_LONG);
			toast.show();
			return;
		}

		// System.out.println("Value of Location is: " + locValue);
		// Finding the present location of the user
		// entry[2] = "Present location of the user";

		trackLocation();
		
		gps = new GPSLocation(this);
		
		entry[2]=getAddres();
		if (gps.canGetLocation()) {
			Toast t = Toast.makeText(this, entry[2], Toast.LENGTH_LONG);
			t.show();
		}
		
		
		//Calculating your address
		//calcAddress();
		//Stopping the GPS. To save power
		stopGPS();
		
		System.out.println("In the gotoCart().Entry values are " + entry[0]
				+ ", " + entry[1] + ", " + entry[2]);
		intent.putExtra(ITEM_DETAILS, entry);
		
		startActivity(intent);
	}

	/*
	 * Update the Location and store value in LocationDetails Table
	 */
	public void saveLocationData() {

	}

	public void trackLocation() {
		if (gps.canGetLocation()) {
			Toast t = Toast.makeText(
					this,
					"Latitute:" + Double.toString(gps.getLatitude())
							+ "\nLongitute:"
							+ Double.toString(gps.getLongitude()),
					Toast.LENGTH_SHORT);
			t.show();
			this.latitudeVal = gps.getLatitude(); // returns latitude
			this.longitudeVal = gps.getLongitude(); // returns longitude

		} else {
			// Show alert box for setting change
			gps.showSettingsAlert();
		}

	}

	public void stopGPS() {
		gps = new GPSLocation(this);
		gps.stopUsingGPS();
	}

	public void calcAddress() {
		gps = new GPSLocation(this);
		if (gps.canGetLocation()) {
			Toast t = Toast.makeText(this, getAddres(), Toast.LENGTH_LONG);
			t.show();
		}
	}

	private String getAddres() {
		StringBuilder addr = new StringBuilder();
		try {
			Geocoder geocoder = new Geocoder(this, Locale.getDefault());
			List<Address> addresses = geocoder.getFromLocation(latitudeVal,
					longitudeVal, 1);
			if (addresses.size() > 0) {
				Address address = addresses.get(0);
				// result.append(address.getAddressLine(1)).append("\n");
				addr.append(address.getLocality()).append(",");
				addr.append(address.getAdminArea()).append("\n");
				addr.append(address.getCountryName());
			}
		} catch (IOException e) {
			Log.e("tag", e.getMessage());
		}

		return addr.toString();
	}

	public void closeApp(View view) {
		finish();
		System.exit(0);
	}
}
