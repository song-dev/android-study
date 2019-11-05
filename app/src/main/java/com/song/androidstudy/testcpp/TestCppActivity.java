package com.song.androidstudy.testcpp;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
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

        String info = getData(this);
        contentTv.setText(info);

        Log.e(TAG, "onCreate: " + info);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */

    public native String getData(Context context);

}
