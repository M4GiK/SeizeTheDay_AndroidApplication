package com.example.endproject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity 
{	
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
//    	((ImageButton)(v)).setImageResource(R.drawable.button_add_pressed);
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
