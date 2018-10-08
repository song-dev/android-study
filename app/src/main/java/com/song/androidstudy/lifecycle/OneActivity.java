package com.song.androidstudy.lifecycle;

import android.content.Intent;
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
         * 1. activity的创建onCreate-->onStart-->onResume
         * 2. activity的销毁onPause-->onStop-->onDestroy
         * 3. one跳转到two，回到one：one:onPause-->two:onCreate-->two:onStart-->two:onResume-->one:onSaveInstanceState-->one:stop
         * -->回退键-->two:onPause-->one:onRestart-->one:onStart-->one:onResume-->two:onStop-->two:onDestroy
         * (总结：1.one页面必须onPause，Two页面才能创建；2.one页面被全部遮挡了，也就是two调用onResume，再调用one的onStop；
         * 3.onSaveInstanceState在stop之前调用，前提是页面未被销毁; 4.页面重新回到栈顶则先调用onRestart，再onStart)
         * 4.
         */
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
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e(TAG, "onRestoreInstanceState: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "onRestart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: ");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e(TAG, "onSaveInstanceState: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: ");
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
}
