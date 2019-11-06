package com.song.androidstudy.xposed;

import android.util.Log;

import com.song.androidstudy.xposed.hook.JiGuangHook;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Hook 函数的入口，可以动态的设置 Hook 的函数
 * Created by chensongsong on 2019/8/29.
 */
public class MainHook implements IXposedHookLoadPackage {

    private static final String TAG = "MainHook";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

        XposedBridge.log("Loaded app: " + lpparam.packageName);
        Log.e(TAG, "MainHook Loaded app: " + lpparam.packageName);

//        new SMIDHook(lpparam);
//        new JDHook(lpparam);
        new JiGuangHook(lpparam);

    }
}
