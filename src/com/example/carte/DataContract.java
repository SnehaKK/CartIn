package com.example.carte;


import android.provider.BaseColumns;

public final class DataContract {
	// To prevent someone from accidentally instantiating the contract class,
	// give it an empty constructor.
	public DataContract() {
	}

	/* Inner class that defines the table contents */
	public static abstract class DataEntry implements BaseColumns {
		public static final String TABLE_CART_DETAILS = "CartDetails";
		public static final String COLUMN_NAME_UUID_ID = "UUId";
		public static final String COLUMN_NAME_ITEM_NAME = "ItemName";
		public static final String COLUMN_NAME_QUANTITY = "Quantity";
		public static final String COLUMN_NAME_PRICE = "Price";
		public static final String COLUMN_NAME_AMOUNT = "Amount";
		public static final String COLUMN_NAME_STORE_NAME = "StoreName";
		public static final String COLUMN_NAME_BUDGET = "Budget";
		public static final String COLUMN_NAME_DATE_TIME = "Date";

		
		
		public static final String TABLE_LOCATION_DETAILS = "LocationDetails";
		public static final String COLUMN_NAME_LOCATION = "Location";
		public static final String COLUMN_NAME_NAME_OF_LOCATION = "NameOfLocation";
	}
}
