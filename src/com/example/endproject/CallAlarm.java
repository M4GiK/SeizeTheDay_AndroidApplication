package com.example.endproject;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class CallAlarm extends Activity 
{
	private String path;
    MediaPlayer mp;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.call_alarm);
		Bundle extras = getIntent().getExtras();
	    path = extras.getString("path");
	    Log.d("m",path);
	    

	    
      mp = MediaPlayer.create(CallAlarm.this,R.raw.ring);
      mp.setOnCompletionListener(new OnCompletionListener() {

		public void onCompletion(MediaPlayer mp) 
		{
			mp.start();
		}
    	  
      });
      mp.start();
	
	}
	    
	public void bClosePressed(View v)
	{
		mp.stop();
		finish();
	}
	
	protected void onPause()
	{
		super.onPause();
		mp.stop();
	}
}
