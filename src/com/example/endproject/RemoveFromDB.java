package com.example.endproject;

import com.example.endproject.database.DatabaseHelper;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * This class is responsible for removing component from database.
 * 
 * @author Seize the Day
 *
 */
public class RemoveFromDB extends Activity
{
	/**
	 * Variable need to get access to database.
	 */
	private SQLiteDatabase db;
	
	/**
	 * Variable need to get name from database.
	 */
	private String table;
	
	/**
	 * Variable need to get id.
	 */
	private int id;
	
	/**
	 * Variable need to show message.
	 */
	private String message;
	
	
	
	/**
	 * Create form and fill text view remove information. Also save id and table indormation.
	 */
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.remove_item_from_db);
		
		db = (new DatabaseHelper(this)).getWritableDatabase();
		
		Bundle extras = getIntent().getExtras();
		table = extras.getString("db_name");
		id = extras.getInt("id");
		message = extras.getString("message");
		
		TextView tv = (TextView) findViewById(R.id.textView1);
		tv.setText(message);
	}
	
	
	
	/**
	 * Cancel button pressed
	 * @param v
	 */
	public void bClosePressed(View v)
	{
		finish();
	}
	
	
	
	/**
	 * Remove button is pressed. Remove specivic id form table.
	 * @param v
	 */
	public void bRemovePressed(View v)
	{
		String[] ids = new String [1];
		ids[0] = ""+id;
		db.delete(table, "_id=?", ids);		
		finish();
	}
	
	
	
	/**
	 * Close database connection.
	 */
	public void onDestroy() {
    	
		super.onDestroy();
		db.close();
	}
}
