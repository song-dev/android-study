package com.song.androidstudy.xposed;

import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Wechat implements IXposedHookLoadPackage {

    private static final String TAG = "Wechat";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

        XposedBridge.log("Loaded Test app: " + lpparam.packageName);
        Log.e(TAG, "handleLoadPackage: " + lpparam.packageName);

        if (!lpparam.packageName.equals("com.tencent.mm")) {
            return;
        }

        Log.e(TAG, "handleLoadPackage: init wechat ");

        try {

            // 监听 android.widget.TextView
            XposedHelpers.findAndHookMethod("android.widget.TextView", lpparam.classLoader, "setText", CharSequence.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);

                    CharSequence text = (CharSequence) param.args[0];
                    Log.e(TAG, "beforeHookedMethod: " + text);

                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
