package com.example.endproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

public class Alarm extends BroadcastReceiver
{

	@Override
	public void onReceive(Context context, Intent intent) 
	{
    //    PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
    //    PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
    //    wl.acquire();

        // Put here YOUR code.
        Toast.makeText(context, "Alarm !!!!!!!!!!", Toast.LENGTH_LONG).show(); // For example

        //todo remove me !
        Log.d("ok", "alarm");
        
    //    wl.release();
	}
	
}
