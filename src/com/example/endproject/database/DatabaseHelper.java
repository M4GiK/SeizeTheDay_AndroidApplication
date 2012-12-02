package com.example.endproject.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * This class create database for main application. 
 * 
 * @author m4gik
 *
 */
public class DatabaseHelper extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "SeizeDay_database";
	private static final String DATABASE_TABLE = "timealarm";
	private static final String HOUR = "hour";
	private static final String MINUTE = "minute";
	
	
	
	/**
	 * Constructor for this class.
	 * 
	 * @param context
	 */
	public DatabaseHelper(Context context) {
		
		// Context, database name, optional cursor factory, database version
		super(context, DATABASE_NAME, null, 1);
		
	}
	
	
	
	/**
	 * This method create database on mobile device.
	 * Called when the database is created for the first time. 
	 * This is where the creation of tables and the initial population of the tables should happen.
	 * 
	 * @param db - SQLiteDatabase
	 * 			
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		// Create a new table
		db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + HOUR + " INTEGER, " + MINUTE + " INTEGER);");
		
//		// Create some data
//		ContentValues values = new ContentValues();
//		
//		values.put(HOUR, 12);
//		values.put(MINUTE, 20);
//		
//		// Insert data to database, name of table, values
//		db.insert(DATABASE_TABLE, null, values);
//		
//		// Create some data
//		values.put(HOUR, 11);
//		values.put(MINUTE, 30);
//		
//		// Insert data to database, name of table, values
//		db.insert(DATABASE_TABLE, null, values);
		
	}	

	
	
	/**
	 * Called when the database needs to be upgraded. 
	 * The implementation should use this method to drop tables, 
	 * add tables, or do anything else it needs to upgrade to the new schema version.
	 * 
	 * @param db - SQLiteDatabase
	 * @param oldVersion - int
	 * @param newVersion - int
	 * 
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		Log.v("SQLite","Upgrading DB, all old data will be lost.");
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
		onCreate(db);
		
	}
	
}
