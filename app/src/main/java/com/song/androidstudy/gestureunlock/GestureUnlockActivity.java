package com.song.androidstudy.gestureunlock;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.song.androidstudy.R;

/**
 * 手势解锁
 * <p>
 * Created by chensongsong on 2018/10/19.
 */
public class GestureUnlockActivity extends AppCompatActivity {

    private GestureLockView lockView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_unlock);
        lockView = ((GestureLockView) findViewById(R.id.gesture_unlock));
        lockView.setKey("1234");
        lockView.setShowLine(true);
        lockView.setOnGestureFinishListener(new GestureLockView.OnGestureFinishListener() {
            @Override
            public void OnGestureFinish(boolean success, String key) {
                // 成功还是失败
                Toast.makeText(getApplicationContext(), key, Toast.LENGTH_LONG).show();
            }
        });
    }
}
