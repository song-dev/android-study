package com.song.androidstudy.views.views;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by chensongsong on 2018/10/18.
 */
public class MeasureView extends AppCompatTextView {

    private static final String TAG = "MeasureView";

    private static final String TEXT_STR = "hahahaha";
    private Rect rect;
    private Paint paint;

    public MeasureView(Context context) {
        super(context);
    }

    public MeasureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MeasureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        rect = new Rect();
        paint = new Paint();
    }

    {
        /**
         * 1. view的绘制过程由ViewRootImpl分发实现，且ViewRootImpl是链接WindowManager和DecorView的桥梁
         * 2. 从ViewRootImpl.performTraversals()开始，由从ViewRootImpl.performMeasure()-->View.measure()(包含child.measure())-->View.onMeasure()
         * -->ViewRootImpl.performLayout()-->View.Layout(包含child.layout())-->View.onLayout
         * -->ViewRootImpl.performDraw()-->View.draw()(包含child.draw())--View.onDraw()-->View.dispatchDraw
         * 3. 总的来说measure-->layout-->draw
         * 4. 子类的MeasureSpec受父类的MeasureSpec和本身的LayoutParam影响
         */
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = 0;
        int height = 0;
        // 获取父控件的MeasureSpec
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        switch (widthMode) {
            case MeasureSpec.EXACTLY:
                // 精确大小
                width = MeasureSpec.getSize(widthMeasureSpec);
                break;
            case MeasureSpec.AT_MOST:
            default:
                // 根据内容测量大小
                break;
        }
        int heightMode = MeasureSpec.getMode(widthMeasureSpec);
        switch (heightMode) {
            case MeasureSpec.EXACTLY:
                // 精确大小
                height = MeasureSpec.getSize(widthMeasureSpec);
                break;
            case MeasureSpec.AT_MOST:
            default:
                // 根据内容测量大小
                break;
        }
        super.onMeasure(MeasureSpec.makeMeasureSpec(width, widthMode), MeasureSpec.makeMeasureSpec(height, heightMode));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    {
        /**
         * 测量View宽高三种方法
         * 1. view.post(runnabel)
         * 2. 监听view的onFocusChanged方法(可能多次调用)
         * 3. 给view添加OnGlobalFocusChangeListener监听，view.getViewTreeObserver().addOnGlobalFocusChangeListener()
         */
    }

    {
        /**
         * getMeasuredWidth和getWidth区别
         * 1. 默认情况下相等
         * 2. getMeasuredWidth在onMeasure过程得到，getWidth在onLayout过程得到，时机不一样
         * 3. 可能返回值不一样（若重写onLayout实现）
         */
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        Log.e(TAG, "onFocusChanged: " + getWidth());
        Log.e(TAG, "onFocusChanged: " + getHeight());
        Log.e(TAG, "onFocusChanged: " + getMeasuredWidth());
        Log.e(TAG, "onFocusChanged: " + getMeasuredHeight());

        super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }
}
