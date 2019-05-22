package com.song.androidstudy.testcpp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.song.androidstudy.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestCppActivity extends AppCompatActivity {

    private static final String TAG = "TestCppActivity";

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @BindView(R.id.content)
    TextView contentTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_cpp);
        ButterKnife.bind(this);

        // Example of a call to a native method
//        TextView tv = (TextView) findViewById(R.id.sample_text);
//        tv.setText(stringFromJNI());

        String info = getInfo(this);
        contentTv.setText(info);

        Log.e(TAG, "onCreate: " + stringFromJNI());
        Log.e(TAG, "onCreate: " + info);
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
}
