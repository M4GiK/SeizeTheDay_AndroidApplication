package com.example.endproject;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.endproject.database.DatabaseHelper;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * This class is responsible for adding new task to database and displaying on list.
 * 
 * @author Seize the Day
 *
 */
public class TaskManager extends Activity
{
	/**
	 * Static field to define name in table
	 */
	private static final String TABLE_TASK = "tasks";
	
	/**
	 * Variable need to get access to database.
	 */
	private SQLiteDatabase db;
	
	/**
	 * Object using to moved in database.
	 */
	private Cursor cursor;
	
	
	
	/**
	 * Called when the activity is starting. This is where most initialization
	 * should go: calling setContentView(int) to inflate the activity's UI,
	 * using findViewById(int) to programmatically interact with widgets in the
	 * UI, calling managedQuery(android.net.Uri, String[], String, String[],
	 * String) to retrieve cursors for data being displayed, etc.
	 * 
	 * @param savedInstanceState
	 *            - Bundle
	 */
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.task_manager);
		
		db = (new DatabaseHelper(this)).getWritableDatabase();
		String[] resultColumns = new String[]{"_id","task_text"};
        cursor = db.query(TABLE_TASK,resultColumns,null,null,null,null,null);
	}
	
	
	
	/**
	 * refresh view if activity is come back from the background.
	 */
	protected void onResume()
	{
		super.onResume();
		refreshView();
	}
	
	
	
	/**
	 * pressed add button. Write data to data base.
	 * @param v
	 */
	public void bAddPressed(View v)
	{
		EditText te = (EditText) findViewById(R.id.editText1);
		String data = te.getText().toString();
		te.setText(null);
		if(data.isEmpty())
		{
			return ;
		}
		
        //write information to data base
        ContentValues values=new ContentValues(1);
		values.put("task_text", data);
		db.insert(TABLE_TASK, null, values);
		cursor.requery();
		refreshView();
	}
	
	
	
	/**
	 * Print all data in ListView. Get data form database.
	 */
	public void refreshView()
	{
		
		String[] resultColumns = new String[]{"_id","task_text"};
		cursor = db.query(TABLE_TASK,resultColumns,null,null,null,null,"_id DESC");
		
		ListView list;

		list = (ListView) findViewById(R.id.listView1);
		ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();		
		
		for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) 
		{
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("task_id",new String( cursor.getString(0)));
			map.put("task",new String (cursor.getString(1)));

			menuItems.add(map);	
		}
		
		
		ListAdapter adapter = new SimpleAdapter(this, menuItems,
				R.layout.task_item,
				new String[] { "task_id", "task"}, new int[] {
						R.id.task_id, R.id.task });

		list.setAdapter(adapter);
		
		final Context context = this;
		
		/*
		 *	Run Click Listener. If click on item show remove window. 
		 */
		list.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
			{
		        Intent i = new Intent(context, RemoveFromDB.class);
		        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		        i.putExtra("db_name",TABLE_TASK);
		        i.putExtra("id",Integer.parseInt(((TextView) view.findViewById(R.id.task_id)).getText().toString()));
		        i.putExtra("message","Do you want remove '"+((TextView) view.findViewById(R.id.task)).getText().toString()+"' task?");
		        context.startActivity(i);
			}
			
		});
	}
}
