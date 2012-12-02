package com.example.endproject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class CallAlarm extends Activity 
{
	private String path;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.call_alarm);
		Bundle extras = getIntent().getExtras();
	    path = extras.getString("path");
	    Log.d("m",path);
	}
	
	public void bClosePressed(View v)
	{
		finish();
	}
}
