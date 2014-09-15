package com.ekeitho.clocksubtract.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import java.util.Date;

/**
 * Created by Keithmaynn on 9/8/14.
 */
public class ScheduleClient {

    // The hook into our service
    private ScheduleService mBoundService;
    // The context to start the service in
    private Context mContext;
    // A flag if we are connected to the service or not
    private boolean mBound = false;

    public ScheduleClient(Context context) {
        mContext = context;
    }


    /**
     * When you attempt to connect to the service, this connection will be called with the result.
     * If we have successfully connected we instantiate our service object so that we can call methods on it.
     */
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with our service has been established,
            // giving us the service object we can use to interact with our service.
            mBoundService = ((ScheduleService.ServiceBinder) service).getService();
        }
        @Override
        public void onServiceDisconnected(ComponentName className) {
            mBoundService = null;
        }
    };


    /**
     * Call this to connect your activity to your service
     */
    public void doBindService() {
        // Establish a connection with our service
        Intent intent = new Intent(mContext, ScheduleService.class);
        mContext.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        mBound = true;
    }

    /**
     * Tell our service to set an alarm for the given date
     * @param date a date to set the notification for
     */
    public void setAlarmForNotification(Date date){
            mBoundService.setAlarm(date);
    }

    /**
     * When you have finished with the service call this method to stop it
     * releasing your connection and resources
     */
    public void doUnbindService() {

        if (mBound) {
            // Detach our existing connection.
            mContext.unbindService(mConnection);
            mBound = false;
        }

    }
}
