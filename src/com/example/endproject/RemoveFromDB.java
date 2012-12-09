package com.example.endproject;

import com.example.endproject.database.DatabaseHelper;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class RemoveFromDB extends Activity
{
	
	private SQLiteDatabase db;
	private String table;
	private int id;
	private String message;
	
	
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
	
	public void bClosePressed(View v)
	{
		finish();
	}
	
	public void bRemovePressed(View v)
	{
		String[] ids = new String [1];
		ids[0] = ""+id;
		db.delete(table, "_id=?", ids);		
		finish();
	}
	
	public void onDestroy() {
    	
		super.onDestroy();
		db.close();
	}
}
