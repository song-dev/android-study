package com.song.androidstudy.views;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * 1. 通过GestureDetector.onTouchEvent(event)接管处理事件
 */
public class TestGesTureDetector implements GestureDetector.OnGestureListener {

    private GestureDetector detector;

    public TestGesTureDetector(Context context) {
        detector = new GestureDetector(context, this);
    }


    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    /**
     * 轻按（按下未松开）
     *
     * @param e
     */
    @Override
    public void onShowPress(MotionEvent e) {

    }

    /**
     * 单击
     *
     * @param e
     * @return
     */
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    /**
     * 拖动未松开
     *
     * @param e1
     * @param e2
     * @param distanceX
     * @param distanceY
     * @return
     */
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    /**
     * 长按
     */
    @Override
    public void onLongPress(MotionEvent e) {

    }

    /**
     * 快速滑动后松开
     *
     * @param e1
     * @param e2
     * @param velocityX
     * @param velocityY
     * @return
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
