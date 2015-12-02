package com.example.dgonzalez.wificonnect;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;

import com.commonsware.cwac.wakeful.WakefulIntentService;

import java.util.Date;
import java.util.List;

/**
 * This {@code IntentService} does the app's actual work.
 * {@code SampleAlarmReceiver} (a {@code WakefulBroadcastReceiver}) holds a
 * partial wake lock for this service while the service does its work. When the
 * service is finished, it calls {@code completeWakefulIntent()} to release the
 * wake lock.
 */
public class SchedulingService extends IntentService {
//    public class SchedulingService extends WakefulIntentService {
//    WifiManager mWifiManager;
    //    WifiReceiver mWifiReceiver;

    public SchedulingService() {
        super("SchedulingService");
        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Try to connect to the Google homepage and download content.
        // Release the wake lock provided by the BroadcastReceiver.
        Handler uiHandler = new Handler(Looper.getMainLooper());
        Runnable runnable = new Runnable() {
            @Override
            public void run()
            {
                BusProvider.getInstance().post(new SendMessageEvent());
            }
        };
        uiHandler.post(runnable);

        vibrate();
        AlarmReceiver.completeWakefulIntent(intent);
    }

    private void vibrate()
    {
        int state = Utils.AppWifiManger.getWifiState();
        int maxLevel = 100;
        if (state == WifiManager.WIFI_STATE_ENABLED)
        {
            // Get Scanned results in an array List
            List<ScanResult> wifiList = Utils.AppWifiManger.getScanResults();
            //Vibrator mVibratorService = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            // Iterate on the list
            for (ScanResult result : wifiList)
            {
                if (result.BSSID.equals(Utils.BSSID))
                {
                    int signal = Utils.AppWifiManger.calculateSignalLevel(result.level, maxLevel);

                    if (signal >= Utils.BuzzValue && Utils.VibrateBoolean) {

                        if (Utils.VibratorService != null) {
                            // Vibrate for 500 milliseconds
                            Utils.VibratorService.vibrate(500);
                        }
                    }
                    break;
                }
            }
        }
    }
//    @Override
//    protected void doWakefulWork(final Intent intent) {
//        Handler uiHandler = new Handler(Looper.getMainLooper());
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run()
//            {
//                BusProvider.getInstance().post(new SendMessageEvent());
//            }
//        };
//        uiHandler.post(runnable);
//
////        int state = Utils.AppWifiManger.getWifiState();
////        int maxLevel = 100;
////        if (state == WifiManager.WIFI_STATE_ENABLED)
////        {
////            // Get Scanned results in an array List
////            List<ScanResult> wifiList = Utils.AppWifiManger.getScanResults();
////            Vibrator mVibratorService = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
////            // Iterate on the list
////            for (ScanResult result : wifiList)
////            {
////                if (result.BSSID.equals(Utils.BSSID))
////                {
////                    int signal = Utils.AppWifiManger.calculateSignalLevel(result.level, maxLevel);
////
////                    if (signal >= 90) {
////
////                        if (mVibratorService != null) {
////                            // Vibrate for 500 milliseconds
////                            mVibratorService.vibrate(500);
////                        }
////                    }
////                    break;
////                }
////            }
////        }
////
//
//        AlarmReceiver.completeWakefulIntent(intent);
//    }
}
