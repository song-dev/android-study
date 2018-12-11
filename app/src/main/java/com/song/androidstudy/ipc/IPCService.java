package com.song.androidstudy.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;

public class IPCService extends Service {

    private static final String TAG = "IPCService";

    public IPCService() {
        Log.e(TAG, "IPCService: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate: ");
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.e(TAG, "onRebind: ");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return new MyBinder();
    }

    class MyBinder extends ITestAidlInterface.Stub {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public String getValue(String s) throws RemoteException {
            Log.e(TAG, "我是服务端，收到信息-->getValue: " + s);
            // 收到服务端的监听
            // 当前运行在子线程
            Log.e(TAG, "getValue: " + (Looper.getMainLooper() == Looper.myLooper()));
            return "服务端已经收到消息";
        }
    }
}
