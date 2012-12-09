package com.example.endproject;

import java.util.ArrayList;
import java.util.List;

import com.example.endproject.database.DatabaseHelper;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

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
		Log.d("rrrrr","rrrrrrrrrrr");
	}
	
	public void refreshView()
	{
		List<String> list = new ArrayList<String>();
		// get data with query
        String[] resultColumns = new String[]{"_id","task_text"};
        cursor = db.query(TABLE_TASK,resultColumns,null,null,null,null,"_id DESC");
        
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            // The Cursor is now set to the right position
           list.add(cursor.getString(1));
        }
        
        
        // add data to adapter
//		ListAdapter adapter = new SimpleCursorAdapter(this,
//				R.layout.list_item, cursor,
//				new String[] {"name", "score"},      // from
//				new int[] {R.id.name, R.id.score});  // to
//		// show data in listView
//		setListAdapter(adapter);
//		// register listView's context menu
//		registerForContextMenu(getListView());
		
		ListView lw = (ListView) findViewById(R.id.listView1);
//		String r[] = {"aaaa","bbbbb"};
		lw.setAdapter(new ArrayAdapter<String>(this, R.layout.task_item,list));
	}
}
