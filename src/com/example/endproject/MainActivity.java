package com.example.endproject;

import com.example.endproject.database.DatabaseHelper;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;


public class MainActivity extends Activity 
{	
	private static final String TABLE = "timealarm";
	/** Object using to connect with database. */
	private SQLiteDatabase db;
	/** Object using to moved in database. */
	private Cursor cursor;
		
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
    }
    
    private void setAlarm()
    {
    	Log.d("alarm","none");
    	
    	ImageButton b = (ImageButton) findViewById(R.id.imageButton1);
		b.setImageResource(R.drawable.button_add);
		b.setEnabled(true);
    }
    
    private void setAlarm(int hour, int min)
    {
    	Log.d("alarm","yes ->"+hour+" min"+min);
    	
    	ImageButton b = (ImageButton) findViewById(R.id.imageButton1);
		b.setImageResource(R.drawable.button_add_pressed);
		b.setEnabled(false);
    }
  
    @Override
	public void onDestroy() {
    	
		super.onDestroy();
		cursor.close();
		db.close();
		
	}
       
    public void buttonPressed(View v)
    {
    	Intent i = new Intent(MainActivity.this,AddComponent.class);
    	startActivity(i);
    }
    
    public void add_button_pressed(View v)
    {
    	Intent i = new Intent(MainActivity.this,SetAlarm.class);    	
    	startActivity(i);
    }
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
