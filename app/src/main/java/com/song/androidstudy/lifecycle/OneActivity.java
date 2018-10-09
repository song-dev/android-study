package com.song.androidstudy.lifecycle;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.song.androidstudy.R;

public class OneActivity extends AppCompatActivity {

    private static final String TAG = "OneActivity";
    private android.widget.TextView sampletext;

    {
        // 生命周期
        /**
         * 1. activity的创建onCreate-->onStart-->onResume（onStart也是可见的，已经被创建，但是并不能交互，onResume是栈顶可见，在前台显示）
         * 2. activity的销毁onPause-->onStop-->onDestroy
         * 3. one跳转到two，回到one：one:onPause-->two:onCreate-->two:onStart-->two:onResume-->one:onSaveInstanceState-->one:stop
         * -->回退键-->two:onPause-->one:onRestart-->one:onStart-->one:onResume-->two:onStop-->two:onDestroy
         * (总结：1.one页面必须onPause，Two页面才能创建；2.one页面被全部遮挡了，也就是two调用onResume，再调用one的onStop；
         * 3.onSaveInstanceState在stop之前调用，前提是页面未被销毁; 4.页面重新回到栈顶则先调用onRestart，再onStart)
         * 4. 当发生异常（包括crash、内存不足activity被销毁，和横竖屏切换），销毁之前onSaveInstanceState在onStop之前调用，重新进入页面，onRestoreInstanceState在onResume之前调用
         * 5. 页面show普通dialog无生命周期变化，如果show dialog activity则当前页面onPause-->onSaveInstanceState,dismiss掉dialog则onResume
         * 6. onPause和onStop尽量不做耗时操作，如：onPause之后Two页面才能创建
         * 7. 横竖屏切换（默认配置），onPause-->onSaveInstanceState-->onStop-->onDestroy-->onCreate-->onStart-->onRestoreInstanceState-->onResume
         * 8. manifest的configChanges参数控制禁止创建activity，orientation横竖屏切换、keyboardHidden键盘隐藏、screenSize屏幕大小改变(screenOrientation控制屏幕方向)
         * 9. 取而代之onConfigurationChanged被调用（仅且进制横竖屏切换创建activity）
         *
         */
    }

    {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        Log.e(TAG, "onCreate: ");
        this.sampletext = (TextView) findViewById(R.id.sample_text);
        sampletext.setClickable(true);
        sampletext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OneActivity.this, TwoActivity.class));
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "onRestart: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: ");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e(TAG, "onRestoreInstanceState: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: ");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e(TAG, "onSaveInstanceState: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.e(TAG, "onConfigurationChanged: ");
    }
}
