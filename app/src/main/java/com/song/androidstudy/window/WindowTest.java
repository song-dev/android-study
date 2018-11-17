package com.song.androidstudy.window;

import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

public class WindowTest {
    {
        /**
         * 1. Window是抽象类，WindowManager实际实现为WindowManagerImpl，WindowManagerImpl实际实现为单例WindowManagerGlobal
         * 2. WindowManager是外界访问Window的入口，Window的具体实现在WindowManagerService中，WindowManagerService和WindowManager交互是IPC过程
         * 3. WindowManager核心方法是addView，removeView，updateViewLayout
         * 4. WindowManager的Flag控制Window的级别，系统Window是2000-2999，子系统是1000-1999，应用Window是1-99，级别越高显示最外层
         * 5. Window中添加View是通过WindowManagerService，同时view的measure,layout,draw也是ViewRootImpl控制，ViewRootImpl是view和Window媒介
         * 6. 每个Window都有一个session
         * 7. 每个activity（PhoneWindow）都有一个token，但是系统Window可以不要，故dialog必须要token，依附activity
         * 
         */
    }

    /**
     * 创建一个window
     * @param context
     */
    public void buildWindow(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        layoutParams.x = 100;
        layoutParams.y = 100;
        layoutParams.flags = WindowManager.LayoutParams.LAST_SYSTEM_WINDOW|WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        windowManager.addView(new TextView(context), layoutParams);
    }
}
