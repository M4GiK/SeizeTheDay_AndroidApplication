package com.example.endproject;

import com.example.endproject.database.DatabaseHelper;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

/**
 * This class add new component to application.
 * 
 * @author Seize the Day
 * 
 */
public class AddComponent extends Activity {

	/**
	 * Static field to define name in table
	 */
	private static final String TABLE = "component";

	/**
	 * Object need to connect with database
	 */
	private SQLiteDatabase db;

	/**
	 * Object using to moved in database.
	 */
	private Cursor cursor;
	
	
	
	/**
	 * This method initialize all part to save selected category. Called when
	 * the activity is starting. This is where most initialization should go:
	 * calling setContentView(int) to inflate the activity's UI, using
	 * findViewById(int) to programmatically interact with widgets in the UI,
	 * calling managedQuery(android.net.Uri, String[], String, String[], String)
	 * to retrieve cursors for data being displayed, etc.
	 * 
	 * @param savedInstanceState
	 *            - Bundle
	 */
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_component);
		
		db = (new DatabaseHelper(this)).getWritableDatabase();
		
		String[] resultColumns = new String[] { "_id", "item", "data" };
		
		cursor = db.query(TABLE, resultColumns, "item=?" , new String[]{"aphorism"}, null, null, null);
		
	}

	
	
	/**
	 * This method moves to new activity.
	 * 
	 * @param v
	 *            -View
	 */
	public void newsClicked(View v) {

		Intent intent = new Intent(AddComponent.this, SetNews.class);
		startActivity(intent);
		
	}

	public void bTaskManagerPresssed(View v)
	{
		Intent i = new Intent(this,TaskManager.class);
		startActivity(i);
	}

	/**
	 * This method moves to new activity.
	 * 
	 * @param v
	 *            -View
	 */
	public void aphorismClicked(View v) {

		// Write information to data base.
		ContentValues values = new ContentValues(2);
		
		if (cursor != null) {
			// Removing from database 
			db.delete(TABLE, "item=?", new String[]{"aphorism"});
		}
		
		values.put("item", "aphorism");
		values.put("data", "http://www.inspirationline.com/inspirationline.xml");	
		
		db.insert(TABLE, null, values);
		cursor.requery();
		
		Intent intent = new Intent(AddComponent.this, MainActivity.class);
		startActivity(intent);
		
	}
	
	
	
	/**
	 * 
	 * @param v
	 *            -View
	 */
	public void buttonPressed(View v) {
		finish();
	}
}
