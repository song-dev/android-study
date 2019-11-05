package com.song.androidstudy.views.event;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.song.androidstudy.R;

/**
 * 事件分发study
 * <p>
 * Created by chensongsong on 2018/10/16.
 */
public class DispatchTouchEventActivity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener {

    private static final String TAG = "TouchEventActivity";
    private EventLayout eventLayout;
    private EventView eventview;

    {
        /**
         * 1. dispatchTouchEvent：事件分发,onInterceptTouchEvent：事件拦截,onTouchEvent：事件处理
         * 2. ViewGroup事件处理，ACTION_DOWN事件，若onTouch返回false，则首先分发onTouch（false未消耗），再分发onTouchEvent（返回false）
         * ，交给activity响应(消耗)。第二步ACTION_UP事件直接交给消耗ACTION_DOWN事件的activity处理，同理ACTION_MOVE也交给activity处理
         * ，若ViewGroup添加了OnClickListener监听，说明ViewGroup可以消费事件，那么就不会再给activity处理
         * 3. ViewGroup事件处理，ACTION_DOWN事件，若onTouch返回true，则首先分发onTouch（已经消耗，就不会分发给onTouchEvent）。第二步ACTION_UP事件直接交给消耗ACTION_DOWN事件的onTouch处理，同理ACTION_MOVE也交给onTouch处理
         * 4. 说明消耗事件的优先级onTouch，onTouchEvent，最后才是onClick
         * 5. getWindow().superDispatchTouchEvent(ev)最后交给DecorView处理
         */
    }

    {
        /**
         * 1. Activity的dispatchTouchEvent先交给window（PhoneWindow）处理，PhoneWindow给绑定的DecorView处理，DecorView继承ViewGroup,故ViewGroup直接处理，所以处理事件为Activity、ViewGroup和View
         * 3. 若消耗DOWN，后续都交给他处理。若拦截某个事件后续事件都交给他处理。
         * 5. 判定子view是否拿到分发事件：动画播放和点击区域是否坐落在子元素区域
         * 6. 如果子view的dispatchTouchEvent为true，则父控件的for循环终止，并且设置好对应的View为Target
         * 7. 当确定了touchEventTarget，后续的move、up事件，都需要经过activity的dispatchTouchEvent，viewgroup的dispatchTouchEvent，view的dispatchTouchEvent到onTouchEvent处理
         * 8. view的dispatchTouchEvent里面判断如果mOnTouchListener不为null，则先调用mOnTouchListener.onTouch(this, event),如果返回为true则直接在dispatchTouchEvent返回，后续不会走到onTouchEvent
         * 9. 一个事件顺序为，activity的分发，viewgroup的分发，viewgroup的拦截，view的分发，view的处理，viewgroup的处理，activity的处理。当view消耗了所有事件，之后的事件直接分发给view消耗
         * ，若view只消耗down，那么事件跳过viewgroup的处理，交给activity的处理，若viewgroup拦截了所有事件，那么事件分发不到view，直接给viewgroup消耗，否则给activity消耗
         */
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch_touch_event);
        eventLayout = ((EventLayout) findViewById(R.id.eventlayout));
        eventview = ((EventView) findViewById(R.id.eventview));
        eventLayout.setOnTouchListener(this);
//        eventLayout.setOnClickListener(this);
        eventview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e("EventView", "onTouch: ");
                return false;
            }
        });

    }

    /**
     * 处理事件优先级中级别
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "onTouchEvent: " + event.getAction());
        return super.onTouchEvent(event);
    }


    /**
     * 处理事件分发中优先级最高
     *
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.e("EventLayout", "onTouch: " + event.getAction());
        return false;
    }

    /**
     * 处理事件分发优先级最低
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        Log.e(TAG, "onClick: ");

    }

    @Override
    public void onUserInteraction() {
        Log.e(TAG, "onUserInteraction: ");
        super.onUserInteraction();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG, "dispatchTouchEvent: " + ev.getAction());
        return super.dispatchTouchEvent(ev);
    }
}
