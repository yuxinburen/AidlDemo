package com.davie.aidlservice;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

public class RemoteService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mService;
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
        mService = null;
    }

    private IRemoteService.Stub mService = new IRemoteService.Stub(){
        @Override
        public int getAbs(int a) throws RemoteException {
            return Math.abs(a);
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }
    };


}
