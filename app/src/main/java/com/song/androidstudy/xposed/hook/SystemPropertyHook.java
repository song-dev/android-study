package com.song.androidstudy.xposed.hook;

import android.content.ContentResolver;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by chensongsong on 2019/8/29.
 */
public class SystemPropertyHook implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

        try {
            XposedHelpers.findAndHookMethod("android.provider.Settings$Secure", lpparam.classLoader, "getString",
                    ContentResolver.class, String.class, XC_MethodReplacement.returnConstant("testandroidid"));
        } catch (Exception e) {
        }

        try {
            XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", lpparam.classLoader, "getDeviceId",
                    XC_MethodReplacement.returnConstant("test"));
        } catch (Exception e) {
        }
    }
}
