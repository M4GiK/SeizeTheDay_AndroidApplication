package com.example.endproject;

import java.io.OutputStream;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity 
{	
	
	private Intent i = null;
	private Intent intent_setalarm = null;

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    public void buttonPressed(View v)
    {
    	if (i==null) i = new Intent(MainActivity.this,AddComponent.class);
//    	Intent i = new Intent(MainActivity.this,AddComponent.class);
    	startActivity(i);
    }
    
    public void add_button_pressed(View v)
    {
    	if (intent_setalarm==null) intent_setalarm = new Intent(MainActivity.this,SetAlarm.class);
    	startActivity(intent_setalarm);
    //	((ImageButton)(v)).setImageResource(R.drawable.button_add_pressed);
//    	imageButton1.setBackgroundDrawable(getResources().getDrawable(R.drawable.ImageName));
//    	if(v == imageButton1)
//    	{
//    		imageButton1.setImageResource(R.drawable.button_add_pressed);
//    	}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
