package com.song.androidstudy.xposed;

import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Test implements IXposedHookLoadPackage {

    private static final String TAG = "Test";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        XposedBridge.log("Loaded Test app: " + lpparam.packageName);
        Log.e(TAG, "handleLoadPackage: " + lpparam.packageName);
    }
}