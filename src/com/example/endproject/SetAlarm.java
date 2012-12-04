package com.example.endproject;

import java.util.Calendar;

import com.example.endproject.database.DatabaseHelper;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;

public class SetAlarm extends Activity
{
	private static final String TABLE = "timealarm";
	private SQLiteDatabase db;
	private Cursor cursor;
	
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_alarm);
		
		db = (new DatabaseHelper(this)).getWritableDatabase();
        String[] resultColumns = new String[]{"_id","hour","minute"};
        cursor = db.query(TABLE,resultColumns,null,null,null,null,null);
	}
	
	public void bSetAlarmPressed(View v)
	{
		// get time form time picker
		TimePicker tp = (TimePicker)findViewById(R.id.timePicker1);
		int hour = tp.getCurrentHour();
		int minute = tp.getCurrentMinute();

		// calculate time to call alarm
		Calendar AlarmCal = Calendar.getInstance();
		AlarmCal.setTimeInMillis(System.currentTimeMillis());
		AlarmCal.set(Calendar.HOUR_OF_DAY, hour);
		AlarmCal.set(Calendar.MINUTE, minute);
		AlarmCal.set(Calendar.SECOND, 0);
		
		// set alarm
        AlarmManager am=(AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(getApplicationContext(), Alarm.class);
        PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 0, i, 0);
        am.set(AlarmManager.RTC_WAKEUP, AlarmCal.getTimeInMillis(), pi);
        
        //write information to data base
        ContentValues values=new ContentValues(2);
		values.put("minute", minute);
		values.put("hour", hour);
		db.insert(TABLE, null, values);
		cursor.requery();
//		Log.d("db","insert new row");
        
		finish();
	}
	
	// test it!
    public void CancelAlarm(Context context)
    {
        Intent intent = new Intent(context, Alarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
	
	public void bCancelPressed(View v)
    {
		finish();
    }
	
    @Override
	public void onDestroy() {
		super.onDestroy();
		cursor.close();
		db.close();
	}
}
