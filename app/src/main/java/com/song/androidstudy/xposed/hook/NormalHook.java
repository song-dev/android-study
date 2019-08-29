package com.song.androidstudy.xposed.hook;

import android.app.Notification;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.Arrays;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * 通用的 Hook 函数
 * Created by chensongsong on 2019/8/29.
 */
public class NormalHook {

    private static final String TAG = "NormalHook";

    private static Context[] mContext = {null};

    public NormalHook(XC_LoadPackage.LoadPackageParam lpparam) {
    }

    private void notificationHook(XC_LoadPackage.LoadPackageParam lpparam){
        XposedHelpers.findAndHookConstructor("android.app.Notification.Builder", lpparam.classLoader, Context.class, Notification.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                // 保存context 对象
                Context context = (Context) param.args[0];
                mContext[0] = context.getApplicationContext();

                super.beforeHookedMethod(param);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            XposedHelpers.findAndHookMethod("android.app.NotificationManager"
                    , lpparam.classLoader, "notify"
                    , String.class, int.class, Notification.class
                    , new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            XposedBridge.log("methodHookParam.args:  " + Arrays.toString(param.args));
                            Log.e(TAG, "beforeHookedMethod: " + Arrays.toString(param.args));
                            //通过param拿到第三个入参notification对象
                            Notification notification = (Notification) param.args[2];
                            Log.e(TAG, "beforeHookedMethod: notification-->" + notification);
                            //获得包名
//                                String aPackage = notification.contentView.getPackage();
//                                Log.e(TAG, "beforeHookedMethod: aPackage-->" + aPackage);
                            String title = "--";
                            String text = "--";

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                Bundle bundle = notification.extras;
                                title = (String) bundle.get("android.title");
                                text = (String) bundle.get("android.text");
                            }
                            Log.e(TAG, "beforeHookedMethod: title-->" + title + text);
                            Toast.makeText(mContext[0], title + "" + text, Toast.LENGTH_SHORT).show();
//                                if ("com.eg.android.AlipayGphone".equals(aPackage)
//                                        && title.equals("支付宝消息") &&
//                                        text.endsWith("已成功向你转了1笔钱")) {
//                                    param.setResult(null);
//                                    return;
//                                }
                            super.beforeHookedMethod(param);
                        }
                    });
        }
    }
}
