package com.example.carte;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.carte.DataContract.DataEntry;

public class DbHelper extends SQLiteOpenHelper {
	// If you change the database schema, you must increment the database
	// version.
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "dataInSqlite.db";

	private static final String TYPE_TEXT = " TEXT";
	//private static final String TYPE_DATE = " DATE";
	private static final String COMMA_SEP = ",";
	private static final String SQL_CREATE_ITEM_DETAILS_TABLE = "CREATE TABLE "
			+ DataEntry.TABLE_CART_DETAILS + " (" 
			+ DataEntry._ID + " INTEGER PRIMARY KEY,"
			+ DataEntry.COLUMN_NAME_ITEM_NAME + TYPE_TEXT + COMMA_SEP 
			+ DataEntry.COLUMN_NAME_QUANTITY + TYPE_TEXT + COMMA_SEP 
			+ DataEntry.COLUMN_NAME_PRICE + TYPE_TEXT + COMMA_SEP 
			+ DataEntry.COLUMN_NAME_STORE_NAME + TYPE_TEXT + COMMA_SEP 
			+ DataEntry.COLUMN_NAME_DATE_TIME + TYPE_TEXT 
			+ " )";

	private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "
			+ DataEntry.TABLE_CART_DETAILS;

	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_ITEM_DETAILS_TABLE);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// This database is only a cache for online data, so its upgrade policy
		// is
		// to simply to discard the data and start over
		db.execSQL(SQL_DELETE_ENTRIES);
		onCreate(db);
	}

	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}
}
