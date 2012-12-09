package com.example.endproject;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.endproject.database.DatabaseHelper;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * This class representation the application core.
 * 
 * @author Seize the Day
 *
 */
public class MainActivity extends Activity 
{	
	/**
	 * Static field to define name in table
	 */
	private static final String TABLE = "timealarm";
	
	/**
	 * Static field to define name in table
	 */
	private static final String TABLE_COMPONENT = "component";

	/**
	 * Object need to connect with database
	 */
	private SQLiteDatabase db;

	/**
	 * Object using to moved in database.
	 */
	private Cursor cursor;

	/**
	 * Object using to moved in database.
	 */
	private Cursor cursor2;
		
	
	
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
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Create helper for database connection
        db = (new DatabaseHelper(this)).getWritableDatabase();
        // get data with rawQuery
        cursor = db.rawQuery("SELECT _id, hour, minute FROM timealarm ORDER BY hour DESC", null);
        
    }
    
    
    
    /**
     * Check if alarm clock is set. Paint correct form.
     * Print all selected components.
     */
    @Override
    protected void onResume()
    {
    	super.onResume();
        String[] resultColumns = new String[]{"hour", "minute"};
        cursor = db.query(TABLE,resultColumns,null,null,null,null,null);
        
        if (cursor.moveToFirst()) 
        {
        	setAlarm(cursor.getInt(0),cursor.getInt(1));
        }
        else
        {
        	setAlarm();
        }
        
        resultColumns = new String[] { "_id", "item", "data" };
        
        cursor2 = db.query(TABLE_COMPONENT, resultColumns, null , null, null, null, null);
        ListView list;

		list = (ListView) findViewById(R.id.listViewComponents);
		
		ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();		
		
		for(cursor2.moveToFirst(); !cursor2.isAfterLast(); cursor2.moveToNext()) 
		{
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("task_id",new String( cursor2.getString(0)));
			map.put("task",new String (cursor2.getString(1)));

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
		        Intent i = new Intent(context, RemoveFromDB.class);
		        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		        i.putExtra("db_name",TABLE_COMPONENT);
		        i.putExtra("id",Integer.parseInt(((TextView) view.findViewById(R.id.task_id)).getText().toString()));
		        i.putExtra("message","Do you want remove '"+((TextView) view.findViewById(R.id.task)).getText().toString()+"' from start up window?");
		        context.startActivity(i);
			}
			
		});
    }
    
    
    
    /**
     * Off all buttons to set alarm
     */
    private void setAlarm()
    {
    	ImageButton b = (ImageButton) findViewById(R.id.imageButton1);
		b.setImageResource(R.drawable.button_add);
		b.setEnabled(true);
		
		b = (ImageButton) findViewById(R.id.imageButton2);
		b.setVisibility(View.INVISIBLE);
		
		TextView t = (TextView) findViewById(R.id.textView1);
		t.setText(null);
    }
    
    
    
    /**
     * Turn on all buttons to set alarm. Also print all informations text and graphics.
     * @param hour - int
     * @param min	- int
     */
    private void setAlarm(int hour, int min)
    { 	
    	ImageButton b = (ImageButton) findViewById(R.id.imageButton1);
		b.setImageResource(R.drawable.alarm_pressed);
		b.setEnabled(false);
		
		b = (ImageButton) findViewById(R.id.imageButton2);
		b.setVisibility(View.VISIBLE);
		
		TextView t = (TextView) findViewById(R.id.textView1);
		t.setText(hour+":"+min);
    }
  
    
    
    /**
     * Close database connection.
     */
    @Override
	public void onDestroy() {  	
		super.onDestroy();
		cursor.close();
		db.close();	
	}
       
    
    
    /**
     * Show add component window.
     * @param v
     */
    public void buttonPressed(View v)
    {
    	Intent i = new Intent(MainActivity.this,AddComponent.class);
    	startActivity(i);
    }
    
    
    
    /**
     * Show set alarm window
     * @param v
     */
    public void add_button_pressed(View v)
    {
    	Intent i = new Intent(MainActivity.this,SetAlarm.class);    	
    	startActivity(i);
    }
    
    
    
    /**
     * Remove alarm. Drop data form database and terminate alarm from Android AlarmManager object.
     * @param v
     */
    public void removeAlarm(View v)
    {
    	SetAlarm a = new SetAlarm();
    	a.CancelAlarm(this);
    	
		db.delete(TABLE, null, null);
		
		//off buttons 
		setAlarm();
    }

}
