package com.song.androidstudy.xposed;

import android.app.Notification;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.song.androidstudy.crypto.Base64;

import java.lang.reflect.Method;
import java.util.Arrays;

import javax.crypto.Cipher;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class SMIDHookTest implements IXposedHookLoadPackage {

    private static final String TAG = "SMIDHookTest";

    private static Context[] mContext = {null};

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
//        XposedBridge.log("Loaded SMIDHookTest app: " + lpparam.packageName);
//        Log.e(TAG, "handleLoadPackage: " + lpparam.packageName);

        if (!lpparam.packageName.equals("com.github.gavin.smid")) {
            return;
        }

        // 打印开始日志
        Log.e(TAG, "Hook 成功 ！");

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
//        }


//        XposedHelpers.findAndHookMethod("javax.crypto.Cipher", lpparam.classLoader, "getInstance", String.class, Provider.class, new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//
//                String content = (String) param.args[0];
//
//                Log.e(TAG, "beforeHookedMethod: 加解密 方式 -- >" + content);
//
//                super.beforeHookedMethod(param);
//            }
//        });


        Class clazz = XposedHelpers.findClass(Cipher.class.getName(), null);

        Method m = XposedHelpers.findMethodExact(clazz, "getInstance", String.class);

        m.setAccessible(true);

        XposedBridge.hookMethod(m, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                String content = (String) param.args[0];

                Log.e(TAG, "beforeHookedMethod: 加解密 方式 -- >" + content);

                super.beforeHookedMethod(param);
            }
        });

        Class baiduclazz = XposedHelpers.findClass(Settings.Secure.class.getName(), null);

        Method baidugetstring = XposedHelpers.findMethodExact(baiduclazz, "getString", ContentResolver.class, String.class);

        baidugetstring.setAccessible(true);

        XposedBridge.hookMethod(baidugetstring, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                String content = (String) param.args[1];

                Log.e(TAG, "beforeHookedMethod: getstring -- >" + content);

                super.beforeHookedMethod(param);
            }
        });

        try {

            Class settingclazz = XposedHelpers.findClass(Settings.System.class.getName(), null);

            Method settinggetstring = XposedHelpers.findMethodExact(settingclazz, "getString", ContentResolver.class, String.class);

//        settinggetstring.setAccessible(true);

            XposedBridge.hookMethod(settinggetstring, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                    String content = (String) param.args[1];

                    Log.e(TAG, "beforeHookedMethod: sysytem getstring -- >" + content);

                    super.beforeHookedMethod(param);
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    Object result = param.getResult();
                    Log.e(TAG, "afterHookedMethod: sysytem getstring result-->"+result);

                    super.afterHookedMethod(param);
                }
            });

            Class putclazz = XposedHelpers.findClass(Settings.System.class.getName(), null);

            Method putstring = XposedHelpers.findMethodExact(putclazz, "putString", ContentResolver.class, String.class,String.class);

//        putstring.setAccessible(true);

            XposedBridge.hookMethod(putstring, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                    String content = (String) param.args[1];
                    String content2 = (String) param.args[2];

                    Log.e(TAG, "beforeHookedMethod: sysytem putstring -- >" + content+"---"+content2);

                    super.beforeHookedMethod(param);
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    Object result = param.getResult();
                    Log.e(TAG, "afterHookedMethod: sysytem putstring result-->"+result);

                    super.afterHookedMethod(param);
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }


//        XposedHelpers.findAndHookMethod("javax.crypto.KeyGenerator", lpparam.classLoader, "getInstance", String.class, new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//
//                String content = (String) param.args[0];
//                Log.e(TAG, "beforeHookedMethod: aes 方式" + content);
//                super.beforeHookedMethod(param);
//            }
//        });
        XposedHelpers.findAndHookMethod("javax.crypto.Cipher", lpparam.classLoader, "doFinal", byte[].class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                byte[] content = (byte[]) param.args[0];

                String haha = new String(content, "utf-8");
                Log.e(TAG, "beforeHookedMethod: 加解密 -- >" + haha);

                String s = Base64.encode(content);
                Log.e(TAG, "beforeHookedMethod: base64 -- >" + s);
//
//                String ss = "1D1CDC:D27911:BF151Bfeq#%dc87#989(^)78909-=89+1vfiqocxq58*@#~09$";
//                if (ss.equals(haha)) {
//                    param.args[0] = "1D1CDC:D27912:BF151Bfeq#%dc87#989(^)78909-=89+1vfiqocxq58*@#~09$".getBytes();
//                }

                super.beforeHookedMethod(param);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                byte[] content = (byte[]) param.getResult();
                Log.e(TAG, "afterHookedMethod: 解密 -->" + new String(content));
                Log.e(TAG, "afterHookedMethod: 解密 base64-->" + Base64.encode(content));


                super.afterHookedMethod(param);
            }
        });

        XposedHelpers.findAndHookMethod("javax.crypto.Cipher", lpparam.classLoader, "doFinal", byte[].class, int.class, int.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                byte[] content = (byte[]) param.args[0];

                String haha = new String(content, "utf-8");
                Log.e(TAG, "beforeHookedMethod: aes 加解密 -- >" + haha);

                String s = Base64.encode(content);
                Log.e(TAG, "beforeHookedMethod: aes base64 -- >" + s);
//
//                String ss = "1D1CDC:D27911:BF151Bfeq#%dc87#989(^)78909-=89+1vfiqocxq58*@#~09$";
//                if (ss.equals(haha)) {
//                    param.args[0] = "1D1CDC:D27912:BF151Bfeq#%dc87#989(^)78909-=89+1vfiqocxq58*@#~09$".getBytes();
//                }

                super.beforeHookedMethod(param);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                byte[] content = (byte[]) param.getResult();
                Log.e(TAG, "afterHookedMethod: aes 解密 -->" + new String(content));
                Log.e(TAG, "afterHookedMethod: aes 解密 base64-->" + Base64.encode(content));


                super.afterHookedMethod(param);
            }
        });
//
        XposedHelpers.findAndHookMethod("java.security.MessageDigest", lpparam.classLoader, "getInstance", String.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                String content = (String) param.args[0];

                Log.e(TAG, "beforeHookedMethod: hash method-- >" + content);

                super.beforeHookedMethod(param);
            }
        });

        XposedHelpers.findAndHookMethod("java.security.MessageDigest", lpparam.classLoader, "update", byte[].class, int.class, int.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                byte[] content = (byte[]) param.args[0];

                Log.e(TAG, "beforeHookedMethod: hash -- >" + new String(content, "utf-8"));

                super.beforeHookedMethod(param);
            }
        });

        XposedHelpers.findAndHookMethod("java.io.File", lpparam.classLoader, "getName", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                String result = (String) param.getResult();
                Log.e(TAG, "afterHookedMethod: file-->" + result);

                super.afterHookedMethod(param);
            }
        });

        XposedHelpers.findAndHookConstructor("java.io.File", lpparam.classLoader, String.class, String.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                String arg = (String) param.args[0];
                String arg1 = (String) param.args[1];

                Log.e(TAG, "afterHookedMethod: new file-->" + arg + "---" + arg1);

                super.beforeHookedMethod(param);
            }
        });

        XposedHelpers.findAndHookMethod("android.os.Environment", lpparam.classLoader, "getExternalStorageDirectory", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {


                Log.e(TAG, "afterHookedMethod: getExternalStorageDirectory-->");

                super.beforeHookedMethod(param);
            }
        });

        XposedHelpers.findAndHookMethod("android.content.ContextWrapper", lpparam.classLoader, "getContentResolver", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {


                Log.e(TAG, "afterHookedMethod: getContentResolver-->");

                super.beforeHookedMethod(param);
            }
        });

    }


}