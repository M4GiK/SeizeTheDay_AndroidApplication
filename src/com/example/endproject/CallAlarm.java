package com.example.endproject;

import com.example.endproject.database.DatabaseHelper;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.View;

public class CallAlarm extends Activity 
{
    MediaPlayer mp;
	private SQLiteDatabase db;
	private static final String TABLE = "timealarm";
    
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.call_alarm);
		
		// remove from db data
		db = (new DatabaseHelper(this)).getWritableDatabase();
		db.delete(TABLE, null, null);

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
	
    @Override
	public void onDestroy() {
    	
		super.onDestroy();
		db.close();		
	}
}
