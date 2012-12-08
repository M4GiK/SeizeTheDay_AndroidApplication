package com.example.endproject;

import com.example.endproject.database.DatabaseHelper;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

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
	
	/**
	 * pressed add button. Write data to data base.
	 * @param v
	 */
	public void bAddPressed(View v)
	{
		EditText te = (EditText) findViewById(R.id.editText1);
		String data = te.getText().toString();
		if(data.isEmpty())
		{
			if(data=="")
			{
				return ;
			}
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
		ListView lw = (ListView) findViewById(R.id.listView1);
//		lw.add
	}
}
