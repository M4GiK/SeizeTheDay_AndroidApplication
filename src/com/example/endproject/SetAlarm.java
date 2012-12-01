package com.example.endproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class SetAlarm extends Activity
{
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_alarm);
	}
	
	public void press_set_alarm(View v)
	{
		
	}
	
	public void bCancelPressed(View v)
    {
		finish();
    }
}
