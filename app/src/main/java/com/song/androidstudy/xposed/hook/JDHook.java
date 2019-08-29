package com.song.androidstudy.xposed.hook;

import android.content.ContentResolver;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;

import com.song.androidstudy.crypto.Base64;

import java.lang.reflect.Method;

import javax.crypto.Cipher;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * JD hook 代码
 * Created by chensongsong on 2019/8/29.
 */
public class JDHook {

    private static final String TAG = "JDHook";
    private static final String PACKAGE_NAME = "com.jingdong.app.mall";
//    private static final String PACKAGE_NAME = "com.example.geetestthr";

    public JDHook(XC_LoadPackage.LoadPackageParam lpparam) {

        if (PACKAGE_NAME.equals(lpparam.packageName)) {

            sensorHook(lpparam);

//            encryptHook(lpparam);
//            hashHook(lpparam);
//            systemSettingHook(lpparam);
//            fileHook(lpparam);
        }

    }

    /**
     * hook 传感器数据
     *
     * @param lpparam
     */
    private void sensorHook(XC_LoadPackage.LoadPackageParam lpparam) {

        try {
            XposedHelpers.findAndHookMethod("android.hardware.Sensor", lpparam.classLoader, "getType", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    int result = (int) param.getResult();
                    Log.e(TAG, "Sensor.getType() result: " + result);
                    super.afterHookedMethod(param);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            XposedHelpers.findAndHookMethod("android.content.ContextWrapper", lpparam.classLoader, "getSystemService", String.class, new XC_MethodHook() {

                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    String content = (String) param.args[0];
                    Log.e(TAG, "Context.getSystemService param: " + content);
                    super.beforeHookedMethod(param);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            XposedHelpers.findAndHookMethod("android.hardware.SensorManager", lpparam.classLoader, "getDefaultSensor",
                    int.class, new XC_MethodHook() {

                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            int content = (int) param.args[0];
                            Log.e(TAG, "SensorManager.getDefaultSensor param: " + content);
                            super.beforeHookedMethod(param);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
//        try {
//            XposedHelpers.findAndHookMethod("android.hardware.SensorManager", lpparam.classLoader, "getDefaultSensor",
//                    int.class, boolean.class, new XC_MethodHook() {
//
//                        @Override
//                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                            int content = (int) param.args[0];
//                            Log.e(TAG, "SensorManager.getDefaultSensor param: " + content);
//                            super.beforeHookedMethod(param);
//                        }
//                    });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        try {
            XposedHelpers.findAndHookMethod("android.hardware.SensorManager", lpparam.classLoader, "registerListener",
                    SensorEventListener.class, Sensor.class, int.class, new XC_MethodHook() {

                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            Log.e(TAG, "SensorManager.registerListener has invoked.");
                            super.beforeHookedMethod(param);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            XposedHelpers.findAndHookMethod("android.hardware.SensorManager", lpparam.classLoader, "registerListener",
                    SensorEventListener.class, Sensor.class, int.class, Handler.class, new XC_MethodHook() {

                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            Log.e(TAG, "SensorManager.registerListener has invoked.");
                            super.beforeHookedMethod(param);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            XposedHelpers.findAndHookMethod("android.hardware.SensorManager", lpparam.classLoader, "registerListener",
                    SensorEventListener.class, Sensor.class, int.class, int.class, new XC_MethodHook() {

                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            Log.e(TAG, "SensorManager.registerListener has invoked.");
                            super.beforeHookedMethod(param);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            XposedHelpers.findAndHookMethod("android.hardware.SensorManager", lpparam.classLoader, "registerListener",
                    SensorEventListener.class, Sensor.class, int.class, int.class, Handler.class, new XC_MethodHook() {

                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            Log.e(TAG, "SensorManager.registerListener has invoked.");
                            super.beforeHookedMethod(param);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            XposedHelpers.findAndHookMethod("android.hardware.SensorManager", lpparam.classLoader, "registerListener",
                    SensorListener.class, int.class, new XC_MethodHook() {

                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            Log.e(TAG, "SensorManager.registerListener has invoked.");
                            super.beforeHookedMethod(param);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            XposedHelpers.findAndHookMethod("android.hardware.SensorManager", lpparam.classLoader, "registerListener",
                    SensorListener.class, int.class, int.class, new XC_MethodHook() {

                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            Log.e(TAG, "SensorManager.registerListener has invoked.");
                            super.beforeHookedMethod(param);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                XposedHelpers.findAndHookMethod("android.hardware.SensorManager", lpparam.classLoader, "registerDynamicSensorCallback",
                        SensorManager.DynamicSensorCallback.class, new XC_MethodHook() {

                            @Override
                            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                                Log.e(TAG, "SensorManager.registerDynamicSensorCallback has invoked.");
                                super.beforeHookedMethod(param);
                            }
                        });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                XposedHelpers.findAndHookMethod("android.hardware.SensorManager", lpparam.classLoader, "registerDynamicSensorCallback",
                        SensorManager.DynamicSensorCallback.class, Handler.class, new XC_MethodHook() {

                            @Override
                            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                                Log.e(TAG, "SensorManager.registerDynamicSensorCallback has invoked.");
                                super.beforeHookedMethod(param);
                            }
                        });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            XposedHelpers.findAndHookMethod("android.hardware.SensorManager", lpparam.classLoader, "unregisterListener",
                    SensorEventListener.class, Sensor.class, new XC_MethodHook() {

                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            Log.e(TAG, "SensorManager.unregisterListener has invoked.");
                            super.beforeHookedMethod(param);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            XposedHelpers.findAndHookMethod("android.hardware.SensorManager", lpparam.classLoader, "unregisterListener",
                    SensorEventListener.class, new XC_MethodHook() {

                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            Log.e(TAG, "SensorManager.unregisterListener has invoked.");
                            super.beforeHookedMethod(param);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            XposedHelpers.findAndHookMethod("android.hardware.SensorManager", lpparam.classLoader, "unregisterListener",
                    SensorListener.class, new XC_MethodHook() {

                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            Log.e(TAG, "SensorManager.unregisterListener has invoked.");
                            super.beforeHookedMethod(param);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            XposedHelpers.findAndHookMethod("android.hardware.SensorManager", lpparam.classLoader, "unregisterListener",
                    SensorListener.class, int.class, new XC_MethodHook() {

                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            Log.e(TAG, "SensorManager.unregisterListener has invoked.");
                            super.beforeHookedMethod(param);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                XposedHelpers.findAndHookMethod("android.hardware.SensorManager", lpparam.classLoader, "unregisterDynamicSensorCallback",
                        SensorManager.DynamicSensorCallback.class, new XC_MethodHook() {

                            @Override
                            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                                Log.e(TAG, "SensorManager.unregisterDynamicSensorCallback has invoked.");
                                super.beforeHookedMethod(param);
                            }
                        });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                XposedHelpers.findAndHookMethod("android.hardware.SensorManager", lpparam.classLoader, "unregisterDynamicSensorCallback",
                        SensorManager.DynamicSensorCallback.class, new XC_MethodHook() {

                            @Override
                            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                                Log.e(TAG, "SensorManager.unregisterListener has invoked.");
                                super.beforeHookedMethod(param);
                            }
                        });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        try {
//            XposedHelpers.findAndHookMethod("android.hardware.SensorManager", lpparam.classLoader, "unregisterDynamicSensorCallbackImpl",
//                    SensorManager.DynamicSensorCallback.class, new XC_MethodHook() {
//
//                        @Override
//                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                            Log.e(TAG, "SensorManager.unregisterDynamicSensorCallbackImpl has invoked.");
//                            super.beforeHookedMethod(param);
//                        }
//                    });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    /**
     * 加密 Hook
     *
     * @param lpparam
     */
    private void encryptHook(XC_LoadPackage.LoadPackageParam lpparam) {

        Class clazz = XposedHelpers.findClass(Cipher.class.getName(), null);
        Method m = XposedHelpers.findMethodExact(clazz, "getInstance", String.class);
        m.setAccessible(true);
        XposedBridge.hookMethod(m, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                String content = (String) param.args[0];
                Log.e(TAG, "Cipher.getInstance param: " + content);
                super.beforeHookedMethod(param);
            }
        });

        XposedHelpers.findAndHookMethod("javax.crypto.Cipher", lpparam.classLoader, "doFinal", byte[].class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                byte[] content = (byte[]) param.args[0];
                String data = new String(content, "utf-8");
                Log.e(TAG, "Cipher.doFinal param: " + data);
                String data_base64 = Base64.encode(content);
                Log.e(TAG, "Cipher.doFinal param base64: " + data_base64);
                super.beforeHookedMethod(param);
            }

        });

        XposedHelpers.findAndHookMethod("javax.crypto.Cipher", lpparam.classLoader, "doFinal", byte[].class, int.class, int.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                byte[] content = (byte[]) param.args[0];
                String data = new String(content, "utf-8");
                Log.e(TAG, "Cipher.doFinal param: " + data);
                String data_base64 = Base64.encode(content);
                Log.e(TAG, "Cipher.doFinal param base64: " + data_base64);
                super.beforeHookedMethod(param);
            }

        });

    }

    /**
     * hook hash 函数
     *
     * @param lpparam
     */
    private void hashHook(XC_LoadPackage.LoadPackageParam lpparam) {

        XposedHelpers.findAndHookMethod("java.security.MessageDigest", lpparam.classLoader, "getInstance", String.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                String content = (String) param.args[0];
                Log.e(TAG, "MessageDigest.getInstance(algorithm) param: " + content);
                super.beforeHookedMethod(param);
            }
        });

        XposedHelpers.findAndHookMethod("java.security.MessageDigest", lpparam.classLoader, "update", byte[].class, int.class, int.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                byte[] content = (byte[]) param.args[0];
                Log.e(TAG, "MessageDigest.update(byte[],int,int) param: " + new String(content, "utf-8"));
                super.beforeHookedMethod(param);
            }
        });

    }

    /**
     * 系统设置
     *
     * @param lpparam
     */
    private void systemSettingHook(XC_LoadPackage.LoadPackageParam lpparam) {

        XposedHelpers.findAndHookMethod("android.content.ContextWrapper", lpparam.classLoader, "getContentResolver", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                Log.e(TAG, "Context.getContentResolver has invoked.");
                super.beforeHookedMethod(param);
            }
        });

        try {

            Class secureCls = XposedHelpers.findClass(Settings.Secure.class.getName(), null);
            Method secureGetstring = XposedHelpers.findMethodExact(secureCls, "getString", ContentResolver.class, String.class);
            secureGetstring.setAccessible(true);
            XposedBridge.hookMethod(secureGetstring, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                    String content = (String) param.args[1];
                    Log.e(TAG, "Settings.Secure.getString param: " + content);
                    super.beforeHookedMethod(param);
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    String result = (String) param.getResult();
                    Log.e(TAG, "Settings.Secure.getString result: " + result);
                    super.afterHookedMethod(param);
                }
            });

            Class systemCls = XposedHelpers.findClass(Settings.System.class.getName(), null);
            Method systemGetString = XposedHelpers.findMethodExact(systemCls, "getString", ContentResolver.class, String.class);
            XposedBridge.hookMethod(systemGetString, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                    String content = (String) param.args[1];
                    Log.e(TAG, "Settings.System.getString param: " + content);
                    super.beforeHookedMethod(param);
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    String result = (String) param.getResult();
                    Log.e(TAG, "Settings.System.getString result: " + result);
                    super.afterHookedMethod(param);
                }

            });

            Method systemPutString = XposedHelpers.findMethodExact(systemCls, "putString", ContentResolver.class, String.class, String.class);
            XposedBridge.hookMethod(systemPutString, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                    String p1 = (String) param.args[1];
                    String p2 = (String) param.args[2];
                    Log.e(TAG, "Settings.System.putString param: " + p1 + "#" + p2);
                    super.beforeHookedMethod(param);
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    Object result = param.getResult();
                    Log.e(TAG, "Settings.System.putString result: " + result);
                    super.afterHookedMethod(param);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void fileHook(XC_LoadPackage.LoadPackageParam lpparam) {

        XposedHelpers.findAndHookMethod("java.io.File", lpparam.classLoader, "getName", new XC_MethodHook() {

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                String result = (String) param.getResult();
                Log.e(TAG, "File.getName result: " + result);
                super.afterHookedMethod(param);
            }
        });

        XposedHelpers.findAndHookConstructor("java.io.File", lpparam.classLoader, String.class, String.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                String arg = (String) param.args[0];
                String arg1 = (String) param.args[1];
                Log.e(TAG, "fileHook new File param: " + arg + "#" + arg1);
                super.beforeHookedMethod(param);
            }
        });
    }


}
