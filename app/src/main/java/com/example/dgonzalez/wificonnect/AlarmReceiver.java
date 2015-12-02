package com.example.dgonzalez.wificonnect;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.provider.AlarmClock;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.commonsware.cwac.wakeful.WakefulIntentService;

import java.util.Calendar;

/**
 * When the alarm fires, this WakefulBroadcastReceiver receives the broadcast Intent 
 * and then starts the IntentService {@code SampleSchedulingService} to do some work.
 */
public class AlarmReceiver extends WakefulBroadcastReceiver {
    // The app's AlarmManager, which provides access to the system alarm services.
    private AlarmManager alarmMgr;
    // The pending intent that is triggered when the alarm fires.
    private PendingIntent alarmIntent;
  
    @Override
    public void onReceive(Context context, Intent intent) {   
        // BEGIN_INCLUDE(alarm_onreceive)
        /* 
         * If your receiver intent includes extras that need to be passed along to the
         * service, use setComponent() to indicate that the service should handle the
         * receiver's intent. For example:
         * 
         * ComponentName comp = new ComponentName(context.getPackageName(), 
         *      MyService.class.getName());
         *
         * // This intent passed in this call will include the wake lock extra as well as 
         * // the receiver intent contents.
         * startWakefulService(context, (intent.setComponent(comp)));
         * 
         * In this example, we simply create a new intent to deliver to the service.
         * This intent holds an extra identifying the wake lock.
         */
        Intent service = new Intent(context, SchedulingService.class);
        
        // Start the service, keeping the device awake while it is launching.

        startWakefulService(context, service);

//        WakefulIntentService.sendWakefulWork(context, SchedulingService.class);
        // END_INCLUDE(alarm_onreceive)
    }

    /**
     * Sets a repeating alarm that runs once a day at approximately 8:30 a.m. When the
     * alarm fires, the app broadcasts an Intent to this WakefulBroadcastReceiver.
     * @param context
     */
    public void setAlarm(Context context)
    {
        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,//.ELAPSED_REALTIME_WAKEUP,
                                     SystemClock.elapsedRealtime(),// + 5*1000, //In 5 seconds
                                     5 * 1000,
                                     alarmIntent);
//        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,//.ELAPSED_REALTIME_WAKEUP,
//                SystemClock.elapsedRealtime(),// + 5*1000, //In 5 seconds
//                5 * 1000,
//                alarmIntent);

    }

    public void cancelAlarm()
    {
        // If the alarm has been set, cancel it.
        if (alarmMgr != null)
        {
            alarmMgr.cancel(alarmIntent);
        }
    }
 }
