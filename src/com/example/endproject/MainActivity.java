package com.example.endproject;

import com.example.endproject.database.DatabaseHelper;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


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
//    	Log.d("alarm","none");
    	
    	ImageButton b = (ImageButton) findViewById(R.id.imageButton1);
		b.setImageResource(R.drawable.button_add);
		b.setEnabled(true);
		
		b = (ImageButton) findViewById(R.id.imageButton2);
		b.setVisibility(View.INVISIBLE);
		
		TextView t = (TextView) findViewById(R.id.textView1);
		t.setText(null);
    }
    
    private void setAlarm(int hour, int min)
    {
//    	Log.d("alarm","yes ->"+hour+" min"+min+" ");
    	
    	ImageButton b = (ImageButton) findViewById(R.id.imageButton1);
		b.setImageResource(R.drawable.alarm_pressed);
		b.setEnabled(false);
		
		b = (ImageButton) findViewById(R.id.imageButton2);
		b.setVisibility(View.VISIBLE);
		
		TextView t = (TextView) findViewById(R.id.textView1);
		t.setText(hour+":"+min);
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
    
    public void removeAlarm(View v)
    {
//    	Log.d("c","cancel alarm call");
    	SetAlarm a = new SetAlarm();
    	a.CancelAlarm(this);
    	
		// remove from db data
		db.delete(TABLE, null, null);
		
		//off buttons 
		setAlarm();
    }
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
