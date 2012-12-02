package com.example.endproject;

import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends Activity 
{	
	
	private static final int SHOW_SET_TIME_ACTIVITY = 1;
	private BroadcastReceiver br;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); 
    }
    
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
