package com.ekeitho.clocksubtract.service;

/**
 * Created by Keithmaynn on 9/8/14.
 */

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class AlarmTask implements Runnable {
    // The date selected for the alarm
    private final Date date;
    // The android system alarm manager
    private final AlarmManager am;
    // Your context to retrieve the alarm manager from
    private final Context context;
    // Formatter for the date string for notifcation
    SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");

    public AlarmTask(Context context, Date date) {
        this.context = context;
        this.am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        this.date = date;
    }

    @Override
    public void run() {
        // Request to start are service when the alarm date is upon us
        // We don't start an activity as we just want to pop up a notification into the system bar not a full activity
        Intent intent = new Intent(context, NotifyService.class);
        intent.putExtra(NotifyService.INTENT_NOTIFY, true);
        intent.putExtra(NotifyService.DATE_NOTIFY, formatter.format(date));
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);

        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.DAY_OF_MONTH, date.getDay());
        calendar1.set(Calendar.HOUR_OF_DAY, date.getHours());
        calendar1.set(Calendar.MINUTE, date.getMinutes());

        // Sets an alarm - note this alarm will be lost if the phone is turned off and on again
        // 300,000 = 5 minutees
        am.set(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis() - 300000, pendingIntent);
    }
}