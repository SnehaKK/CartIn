package com.example.carte;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carte.DataContract.DataEntry;

public class CartDetails extends Activity {
	public final static String REDIRECT_TO_MAIN = "com.example.carte.REDIRECT_TO_MAIN";
	public final String TAG = "CarteApp";
	Button addItem;
	Button deleteItem;

	Context appContext;
	View appView;

	EditText itemName;
	EditText noOfItems;
	EditText price;

	TextView location;
	TextView budget;
	BigDecimal budgetValue;
	TextView totalCostView;
	TextView date;
	TextView storeName;
	String storeNameValue;
	Intent intent;

	ItemDetails item;

	BigDecimal amount;

	Calendar c;
	Cart cart;
	Locale locale;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cart_details);

		appContext = this;

		locale = Locale.US;
		amount = new BigDecimal("0");

		// Get the Store Name and Budget details
		intent = getIntent();
		String[] entry = intent.getStringArrayExtra(MainActivity.ITEM_DETAILS);
		Log.i(TAG, "\nStoreName:" + entry[0] + "\nBudget:" + entry[1]);

		storeName = (TextView) findViewById(R.id.storeName);
		storeName.setText(entry[0].toUpperCase(locale));
		storeNameValue = entry[0];

		// item.setBudget(entry[1]);
		budgetValue = new BigDecimal(entry[1]);
		budget = (TextView) findViewById(R.id.budgetValue);

		location = (TextView) findViewById(R.id.location);
		location.setText(entry[2]);
		totalCostView = (TextView) findViewById(R.id.totalCost);

		cart = new Cart(budgetValue, new BigDecimal("0"));

		c = Calendar.getInstance();
		date = (TextView) findViewById(R.id.date);
		date.setText(c.getTime().toString());
		budget.setText(entry[1]);

		final ListView listItems = (ListView) findViewById(R.id.listItemInCart);

		listItems.setAdapter(new ItemDetailsBaseAdapter(this, cart
				.getItemList()));

		listItems.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {
				Object obj = listItems.getItemAtPosition(position);
				// if (obj != null) {
				ItemDetails obj_itemDetails = (ItemDetails) obj;

				// if (obj_itemDetails != null) {

				itemName.setText(obj_itemDetails.getItemName());
				noOfItems.setText(Integer.toString(obj_itemDetails
						.getQuantity()));
				price.setText(obj_itemDetails.getPrice().toString());
				Toast t = Toast.makeText(CartDetails.this, "You have chosen : "
						+ " " + obj_itemDetails.getItemName(),
						Toast.LENGTH_LONG);
				t.show();

				// Calculating the totalCost;
				cart.subtractFromTotalCost(item.getAmount());
				cart.addToBudget(item.getAmount());
				totalCostView.setText(cart.getTotalCost().toString());
				budget.setText(cart.getBudget().toString());

				cart.removeItemDetails(obj_itemDetails);
				listItems.setAdapter(new ItemDetailsBaseAdapter(
						getApplicationContext(), cart.getItemList()));
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.store_budget_details, menu);
		return true;
	}

	public void addItem(View view) {
		/*
		 * 1. Save the value entered in itemDetailsClass. 2. Render the
		 * additional Item template --> addItemTemplate(); 3. Update Fields: 2.1
		 * Decrease budget by Price Value 2.2 Calculate Amount 2.2 Increase the
		 * Total Cost by Price Value
		 */

		appView = view;

		// saving data into the class
		item = new ItemDetails();

		itemName = (EditText) findViewById(R.id.editItemName);
		if (itemName.getText().toString().length() > 0)
			item.setItemName(itemName.getText().toString());
		else {
			Toast t = Toast.makeText(this, "Please enter a valid value.",
					Toast.LENGTH_SHORT);
			t.show();
			return;
		}

		noOfItems = (EditText) findViewById(R.id.editQuantity);
		Log.i("info", "Quantity: " + noOfItems.getText().toString());
		if (noOfItems.getText().toString().trim().length() == 0)
		// || Integer.parseInt(noOfItems.getText().toString()) == 0)
		{
			Toast toast = Toast.makeText(this,
					"Quantity should not be null.\nSetting quantity as 1.",
					Toast.LENGTH_LONG);
			toast.show();
			item.setQuantity(1);
		} else if (noOfItems.getText().toString().length() > 0
				&& Integer.parseInt(noOfItems.getText().toString()) <= 0) {
			Toast toast = Toast
					.makeText(
							this,
							"Quantity should be a positive number(>0).\nSetting quantity as 1.",
							Toast.LENGTH_LONG);
			toast.show();
			item.setQuantity(1);
		} else {
			item.setQuantity(Integer.parseInt(noOfItems.getText().toString()));
		}

		price = (EditText) findViewById(R.id.editPrice);
		if (price.getText().toString().trim().length() == 0) {
			Toast toast = Toast
					.makeText(
							this,
							"Price per Item should not be null.\nSetting Minimum value of 1.",
							Toast.LENGTH_SHORT);
			toast.show();
			item.setPrice("1");
		}
		if (price.getText().toString().length() > 0) {
			BigDecimal priceVal = new BigDecimal(price.getText().toString());
			if (priceVal.doubleValue() <= 0.0) {

				Log.i("info", "price: " + priceVal);
				Toast toast = Toast
						.makeText(
								this,
								"Price per Item should be a positive number.\nSetting Minimum value of 1.",
								Toast.LENGTH_SHORT);
				toast.show();
				item.setPrice("1");
			} else {
				item.setPrice(price.getText().toString());
			}
		}

		item.setAmount();
		item.setStoreName(storeNameValue);

		/*
		 * 3. Update Fields: 2.1 Decrease budget by Price Value 2.2 Increase the
		 * Total Cost by Price Value
		 */
		// budget = (TextView) findViewById(R.id.budgetValue);

		// Calcualting the totalCost;
		cart.addToTotalCost(item.getAmount());
		cart.subtractFromBudget(item.getAmount());

		cart.addItemDetails(item);

		Log.i(TAG,
				"Added item to the cart - Total Cost :" + cart.getTotalCost());
		Log.i(TAG, "Item - Name :" + item.getItemName());
		Log.i(TAG, "Remaining Budget " + cart.getBudget().toString());
		Log.i(TAG, "Item Quantity : " + item.getQuantity());
		totalCostView.setText(cart.getTotalCost().toString());
		budget.setText(cart.getBudget().toString());

		ListView itemList = (ListView) findViewById(R.id.listItemInCart);
		itemList.setAdapter(new ItemDetailsBaseAdapter(this, cart.getItemList()));
		itemName.setText("");
		price.setText("");
		noOfItems.setText("");

	}

	public void viewHistory(View view) {
		/*
		 * TODO: 1. Retrieve entries from Database 2. Go to the View Bill
		 * Activity
		 */

	}


	public void saveToDatabase(View view) {
		Toast t=Toast.makeText(this, "Saving the cart to database.", Toast.LENGTH_LONG);
		t.show();
	}
	public void saveData()
	{
		// Save the data using SQLite
		DbHelper dbHelper = new DbHelper(this);
		// Gets the data repository in write mode
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		// Create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
		values.put(DataEntry.COLUMN_NAME_ITEM_NAME, item.getItemName());
		values.put(DataEntry.COLUMN_NAME_QUANTITY, item.getQuantity());
		values.put(DataEntry.COLUMN_NAME_PRICE, item.getPrice().toString());
		values.put(DataEntry.COLUMN_NAME_STORE_NAME, item.getStoreName());
		values.put(DataEntry.COLUMN_NAME_DATE_TIME, c.getTime().toString());
		// Insert the new row, returning the primary key value of the new row
		long newRowId;
		newRowId = db.insert(DataEntry.TABLE_CART_DETAILS, null, values);
		if (newRowId == -1) {
			Toast toast = Toast.makeText(this,
					"Error in saving the file. Please try again! ",
					Toast.LENGTH_SHORT);
			toast.show();
		}
	}


	public void deleteItem(View view) {

		itemName.setText("");
		price.setText("");
		noOfItems.setText("");

	}

}
