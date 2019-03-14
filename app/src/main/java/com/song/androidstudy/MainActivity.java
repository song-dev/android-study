package com.song.androidstudy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.song.androidstudy.bitmap.BitmapActivity;
import com.song.androidstudy.gestureunlock.GestureUnlockActivity;
import com.song.androidstudy.ipc.IPCTestActivity;
import com.song.androidstudy.lifecycle.OneActivity;
import com.song.androidstudy.permission.TestPermissionActivity;
import com.song.androidstudy.rxjavaretrofit.RetrofitRxjavaActivity;
import com.song.androidstudy.thread.ThreadActivity;
import com.song.androidstudy.views.event.DispatchTouchEventActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chensongsong on 2018/10/19.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @BindView(R.id.lifecycle)
    Button lifecycleBtn;
    @BindView(R.id.network)
    Button networkBtn;
    @BindView(R.id.event)
    Button eventBtn;
    @BindView(R.id.permission)
    Button permissionBtn;
    @BindView(R.id.unlock)
    Button unlockBtn;
    @BindView(R.id.thread)
    Button threadBtn;
    @BindView(R.id.bitmap)
    Button bitmapBtn;
    @BindView(R.id.aidl)
    Button aidlBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Example of a call to a native method
//        TextView tv = (TextView) findViewById(R.id.sample_text);
//        tv.setText(stringFromJNI());
        Log.e(TAG, "onCreate: " + stringFromJNI());
//        Log.e(TAG, "onCreate: " + test());
        Log.e(TAG, "onCreate: " + getInfo(this));
//        Log.e(TAG, "onCreate: " + getData(this));
//        Log.e(TAG, "onCreate: " + getAndroidId(this));
//        Log.e(TAG, "onCreate: " + getIMEI(this));
        lifecycleBtn = ((Button) findViewById(R.id.lifecycle));
        networkBtn = ((Button) findViewById(R.id.network));
        eventBtn = ((Button) findViewById(R.id.event));
        permissionBtn = ((Button) findViewById(R.id.permission));
        unlockBtn = ((Button) findViewById(R.id.unlock));
//        bitmapBtn = ((Button) findViewById(R.id.bitmap));
        threadBtn = ((Button) findViewById(R.id.thread));
        lifecycleBtn.setOnClickListener(this);
        networkBtn.setOnClickListener(this);
        eventBtn.setOnClickListener(this);
        permissionBtn.setOnClickListener(this);
        unlockBtn.setOnClickListener(this);
        bitmapBtn.setOnClickListener(this);
        threadBtn.setOnClickListener(this);
        aidlBtn.setOnClickListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    public native String getData(Context context);

    public native String test();

    public native String getInfo(Context context);

    public native String getAndroidId(Context context);

    public native String getIMEI(Context context);


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lifecycle:
                startActivity(new Intent(MainActivity.this, OneActivity.class));
                break;
            case R.id.network:
                startActivity(new Intent(MainActivity.this, RetrofitRxjavaActivity.class));
                break;
            case R.id.event:
                startActivity(new Intent(MainActivity.this, DispatchTouchEventActivity.class));
                break;
            case R.id.permission:
                startActivity(new Intent(MainActivity.this, TestPermissionActivity.class));
                break;
            case R.id.unlock:
                startActivity(new Intent(MainActivity.this, GestureUnlockActivity.class));
                break;
            case R.id.bitmap:
                startActivity(new Intent(MainActivity.this, BitmapActivity.class));
                break;
            case R.id.thread:
                startActivity(new Intent(MainActivity.this, ThreadActivity.class));
                break;
            case R.id.aidl:
                startActivity(new Intent(MainActivity.this, IPCTestActivity.class));
                break;

        }

    }
}
