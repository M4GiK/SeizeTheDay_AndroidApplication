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

public class TaskManager extends Activity
{
	private SQLiteDatabase db;
	private Cursor cursor;
	
	private static final String TABLE_TASK = "tasks";
	
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.task_manager);
		
		db = (new DatabaseHelper(this)).getWritableDatabase();
		String[] resultColumns = new String[]{"_id","task_text"};
        cursor = db.query(TABLE_TASK,resultColumns,null,null,null,null,null);
	}
	
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
//		Log.d("db","insert new row");
		refreshView();
	}
	
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

			// adding HashList to ArrayList
			menuItems.add(map);	
		}
		
		
		ListAdapter adapter = new SimpleAdapter(this, menuItems,
				R.layout.task_item,
				new String[] { "task_id", "task"}, new int[] {
						R.id.task_id, R.id.task });

		list.setAdapter(adapter);
		
		final Context context = this;
		
		list.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
			{
//				Log.d(":)",""+((TextView) view.findViewById(R.id.task)).getText().toString());
				
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
