package com.song.androidstudy.shell;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by chensongsong on 2019/3/14.
 */
public class ShellHelper {

    private static final String TAG = "ShellHelper";

    /**
     * 执行shell命令
     *
     * @param cmd
     * @return
     */
    public static String executeShell(String cmd) {

        try {
            Process p = Runtime.getRuntime().exec(cmd);
            String data = null;
//            BufferedReader ie = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
//            String error = null;
//            while ((error = ie.readLine()) != null
//                    && !error.equals("null")) {
//                data += error + "\n";
//            }
            String line = null;
            while ((line = in.readLine()) != null && !"null".equals(line)) {
                data += line + "\n";
            }
            Log.e(TAG, "cmdShell: " + data);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
