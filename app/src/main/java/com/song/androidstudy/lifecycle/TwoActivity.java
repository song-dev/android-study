package com.song.androidstudy.lifecycle;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.song.androidstudy.R;

public class TwoActivity extends AppCompatActivity {

    private static final String TAG = "TwoActivity";
    private android.widget.TextView sampletext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        this.sampletext = (TextView) findViewById(R.id.sample_text);
        Log.e(TAG, "onCreate: ");
        sampletext.setClickable(true);
        sampletext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // dialogactivity，当前页面onPause-->onSaveInstanceState
                startActivity(new Intent(TwoActivity.this, DialogTestActivity.class));
            }
        });
        sampletext.setLongClickable(true);
        sampletext.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // 普通dialog，当前activity生命周期无变化
                AlertDialog.Builder builder = new AlertDialog.Builder(TwoActivity.this);
                AlertDialog dialog = builder.setMessage("test").create();
                dialog.show();
                return false;
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
