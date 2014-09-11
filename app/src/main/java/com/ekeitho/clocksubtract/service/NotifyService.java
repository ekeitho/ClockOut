package com.ekeitho.clocksubtract.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;

import com.ekeitho.clocksubtract.R;
import com.ekeitho.clocksubtract.ui.MainActivity;


/**
 * This service is started when an Alarm has been raised
 *
 * We pop a notification into the status bar for the user to click on
 * When the user clicks the notification a new activity is opened
 *
 * @author paul.blundell
 */
public class NotifyService extends Service {

    // This is the object that receives interactions from clients
    private final IBinder mBinder = new ServiceBinder();
    // Unique id to identify the notification.
    private static final int NOTIFICATION = 0;
    // Name of an intent extra we can use to identify if
    // this service was started to create a notification
    public static final String INTENT_NOTIFY = "com.ekeitho.subtract.service.INTENT_NOTIFY";
    // Name of intent to pass date string
    public static final String DATE_NOTIFY = "com.ekeitho.subtract.service.DATE_NOTIFY";
    // The system notification manager
    private NotificationManager mNM;
    // The shared preference for wake up
    private SharedPreferences prefs;


    /**
     * Class for clients to access
     */
    public class ServiceBinder extends Binder {
        NotifyService getService() {
            return NotifyService.this;
        }
    }

    @Override
    public void onCreate() {
        Log.i("NotifyService", "onCreate()");
        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        // checks to make sure wakelock is set true on the settings
        if( prefs.getBoolean(getString(R.string.pref_vibrate_key), false)) {
            WakeLocker.acquire(this);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);

        // If this service was started by out AlarmTask intent then we want to show our notification
        if(intent.getBooleanExtra(INTENT_NOTIFY, false) && intent.getStringExtra(DATE_NOTIFY) != null)
            showNotification(intent.getStringExtra(DATE_NOTIFY));

        // We don't care if this service is stopped as we have already delivered our notification
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private void checkNotifcationSettings() {
        String displayWakeupKey = this.getString(R.string.wakeup_phone_key);
        // if wake up enabled
        boolean defaultForWakeup =
                Boolean.parseBoolean(this.getString(R.string.wakeup_phone_default));
        boolean wakeupEnabled =
                prefs.getBoolean(displayWakeupKey, defaultForWakeup);

        if (wakeupEnabled) {
            Log.v("Wakeup", "ENABLED");
            WakeLocker.release();
        }

        String displayVibrateKey = this.getString(R.string.pref_vibrate_key);
        //if vibrate enabled
        boolean defaultForVibrate =
                Boolean.parseBoolean(this.getString(R.string.pref_vibrate_default));
        boolean vibrateEnabled =
                prefs.getBoolean(displayVibrateKey, defaultForVibrate);

        if( vibrateEnabled ) {
            Log.v("Vibrate", "ENABLED");
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            v.vibrate(500);
        }

    }

    /**
     * Creates a notification and shows it in the OS drag-down status bar
     */
    private void showNotification(String dateFormat) {
        // This is the 'title' of the notification
        CharSequence title = "ClockOut";
        // This is the icon to use on the notification
        int icon = R.drawable.ic_launcher;
        // This is the scrolling text of the notification
        CharSequence text = "Remember to clock out at " + dateFormat + ".";
        // What time to show on the notification
        long time = System.currentTimeMillis();

        Notification notification = new Notification(icon, text, time);

        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);

        // Set the info for the views that show in the notification panel.
        notification.setLatestEventInfo(this, title, text, contentIntent);

        // Clear the notification when it is pressed
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        // Send the notification to the system.
        mNM.notify(NOTIFICATION, notification);

        checkNotifcationSettings();
        // Stop the service when we are finished
        stopSelf();
    }
}