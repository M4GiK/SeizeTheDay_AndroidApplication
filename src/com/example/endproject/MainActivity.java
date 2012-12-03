package com.example.endproject;

import com.example.endproject.database.DatabaseHelper;

import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;




public class MainActivity extends Activity 
{	
	
	private static final int SHOW_SET_TIME_ACTIVITY = 1;
	private BroadcastReceiver br;
	
	/**
	 * Object using to connect with database.
	 */
	private SQLiteDatabase db;
	
	/**
	 * Object using to moved in database.
	 */
	private Cursor cursor;
	
	
	
	/**
	 * This method is call automatically, when application started.
	 * 
	 * @param savedInstanceState - Bundle
	 * 
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
        
        // add data to adapter
     	ListAdapter adapter = new SimpleCursorAdapter(this,
     				R.layout.list_item, cursor,
     				new String[] {"hour", "minute"},		// from
     				new int[] {R.id.hour, R.id.minute});   // to
     	
//     	// show data in listView
//     	setListAdapter(adapter);
//     		
//     	// register listView's context menu
//     	registerForContextMenu(getListView());
    }
    
    
    
    /**
     * Method launch after start application.
     */
    @Override
    public void onStart()
    {
    	super.onStart();
        IntentFilter filter = new IntentFilter("MyAction");
        br = new BroadcastReceiver() 
        {
            @Override
            public void onReceive(Context context, Intent intent) 
            {
                Log.d("rrrrrr", "Yay.......");
                abortBroadcast();
            }
        };
        
        registerReceiver(br
        , filter);

    }
    
//    protected void onPause()
//    {
//    	
//    }
    
    @Override
    protected void onStop()
    {
    	unregisterReceiver(br);
//        unregisterReceiver(sendBroadcastReceiver);
//        unregisterReceiver(deliveryBroadcastReceiver);
        super.onStop();
    }
    
    
    
    /**
     * Method call when the application is close.
     */
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
    	startActivityForResult(i,SHOW_SET_TIME_ACTIVITY);

    }
    
    
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	switch(requestCode) {
    		case SHOW_SET_TIME_ACTIVITY:
    			if (resultCode == Activity.RESULT_OK) {
    				ImageButton b = (ImageButton) findViewById(R.id.imageButton1);
    				b.setImageResource(R.drawable.button_add_pressed);
    				b.setEnabled(false);
    			}
    			break;
    	}
    }

    
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
