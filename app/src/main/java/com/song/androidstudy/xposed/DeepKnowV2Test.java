package com.song.androidstudy.xposed;

import android.content.Context;
import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class DeepKnowV2Test implements IXposedHookLoadPackage {

    private static final String TAG = "DeepKnowV2Test";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

        if (!lpparam.packageName.equals("com.example.mobjni")) {
            return;
        }
        Log.e(TAG, "init DeepKnowV2Test");

        try {

            // 监听 android.widget.TextView
            XposedHelpers.findAndHookMethod("com.geetest.mobinfo.a", lpparam.classLoader, "a", Context.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);

                    Log.e(TAG, "com.geetest.mobinfo.a has been hooked");

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
