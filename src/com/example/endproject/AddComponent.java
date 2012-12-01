package com.example.endproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class AddComponent extends Activity
{
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_component);
	}

	public void buttonPressed(View v)
    {
		finish();
    }
}
