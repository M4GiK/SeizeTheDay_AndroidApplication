package com.example.endproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * This class add new component to application.
 * 
 * @author Seize the Day
 * 
 */
public class AddComponent extends Activity {

	/**
	 * 
	 */
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_component);

	}

	/**
	 * This method moves to new activity.
	 * 
	 * @param v
	 *            -View
	 */
	public void newsClicked(View v) {

		Intent intent = new Intent(AddComponent.this, SetNews.class);
		startActivity(intent);
		
	}

	/**
	 * 
	 * @param v
	 *            -View
	 */
	public void buttonPressed(View v) {
		finish();
	}
}
