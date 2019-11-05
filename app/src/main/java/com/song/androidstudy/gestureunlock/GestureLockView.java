package com.song.androidstudy.gestureunlock;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import androidx.annotation.NonNull;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.song.androidstudy.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GestureLockView extends View {
    /**
     * 解锁密码key
     */
    private String key = "";
    private OnGestureFinishListener onGestureFinishListener;

    /**
     * 解锁圆点数组
     */
    private LockCircle[] cycles;
    /**
     * 存储触碰圆的序列
     */
    private List<Integer> linedCycles = new ArrayList<Integer>();

    //画笔
    /**
     * 空心外圆
     */
    private Paint paintNormal;
    /**
     * 点击后内部圆
     */
    private Paint paintInnerCycle;
    /**
     * 画路径
     */
    private Paint paintLines;
    private Path linePath = new Path();

    /**
     * 当前手指X,Y位置
     */
    private int eventX, eventY;

    /**
     * 能否操控界面绘画
     */
    private boolean canContinue = true;
    /**
     * 验证结果
     */
    private boolean result;
    private Timer timer;
    /**
     * 是否为设置模式*
     */
    private boolean isSettingMode = false;

    /**
     * 是否显示连接线
     */
    private boolean isShowLine = true;

    /**
     * 未选中颜色
     */
    private final int NORMAL_COLOR = Color.parseColor("#959BB4");
    /**
     * 错误颜色
     */
    private final int ERROE_COLOR = Color.parseColor("#FF2525"); // 正常外圆颜色
    /**
     * 选中时颜色
     */
    private final int TOUCH_COLOR = Color.parseColor("#409DE5"); // 选中内圆颜色


    //=================================start=构造方法========================
    public GestureLockView(@NonNull Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }


    public GestureLockView(@NonNull Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public GestureLockView(@NonNull Context context) {
        this(context, null);
    }
    //===============================end=构造方法========================


    /**
     * 初始化
     */
    public void init(Context context, AttributeSet attrs, int defStyle) {
        paintNormal = new Paint();
        paintNormal.setAntiAlias(true);
        paintNormal.setStrokeWidth(5);
        paintNormal.setStyle(Paint.Style.STROKE);

        paintInnerCycle = new Paint();
        paintInnerCycle.setAntiAlias(true);
        paintInnerCycle.setStyle(Paint.Style.FILL);

        paintLines = new Paint();
        paintLines.setAntiAlias(true);
        paintLines.setStyle(Paint.Style.STROKE);
        paintLines.setStrokeWidth(10);

        // 获取xml配置属性
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.GestureLockView, defStyle, 0);
        a.getColor(R.styleable.GestureLockView_GestureLockView_default_color, 0xffffff);
        a.getDimensionPixelSize(R.styleable.GestureLockView_GestureLockView_max_width, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics()));
        a.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int spceSize = MeasureSpec.getSize(widthMeasureSpec);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(
                (int) (spceSize * 0.85 + 0.5f), specMode);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int perWidthSize = getWidth() / 7;
        int perHeightSize = getHeight() / 6;
        /**初始化圆的参数*/
        if (cycles == null && (perWidthSize > 0) && (perHeightSize > 0)) {
            cycles = new LockCircle[9];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    LockCircle lockCircle = new LockCircle();
                    lockCircle.setNum(i * 3 + j);
                    lockCircle.setOx(perWidthSize * (j * 2 + 1.5f) + 0.5f);
                    lockCircle.setOy(perHeightSize * (i * 2 + 1) + 0.5f);
                    lockCircle.setR(perWidthSize * 0.4f);
                    cycles[i * 3 + j] = lockCircle;
                }
            }
        }
    }


    public void setSettingMode(boolean isSettingMode) {
        this.isSettingMode = isSettingMode;
    }


    public void setKey(String key) {
        this.key = key;
    }


    public void setOnGestureFinishListener(OnGestureFinishListener onGestureFinishListener) {
        this.onGestureFinishListener = onGestureFinishListener;
    }


    public void setShowLine(boolean isShowLine) {
        this.isShowLine = isShowLine;
    }


    /**
     * 手势输入完成后回调接口
     */
    public interface OnGestureFinishListener {
        /**
         * 手势输入完成后回调函数
         */
        public void OnGestureFinish(boolean success, String key);
    }


    /**
     * 监听手势
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (canContinue) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    eventX = (int) event.getX();
                    eventY = (int) event.getY();
                    for (LockCircle cycle : cycles) {
                        if (cycle.isPointIn(eventX, eventY)) {
                            cycle.setOnTouch(true);
                            if (!linedCycles.contains(cycle.getNum())) {
                                linedCycles.add(cycle.getNum());
                            }
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    //手指离开暂停触碰
                    canContinue = false;
                    StringBuilder stringBuffer = new StringBuilder();
                    for (int i = 0; i < linedCycles.size(); i++) {
                        stringBuffer.append(linedCycles.get(i));
                    }

                    if (!isSettingMode) {
                        result = key.equals(stringBuffer.toString());
                        if (!result) {
                            timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    eventX = eventY = 0;
                                    for (int i = 0; i < 9; i++) {
                                        cycles[i].setOnTouch(false);
                                    }
                                    linedCycles.clear();
                                    linePath.reset();
                                    canContinue = true;
                                    postInvalidate();//在非ui线程刷新界面
                                }
                            }, 1000);
                        }
                    } else {
                        result = true;
                    }

                    if (onGestureFinishListener != null &&
                            linedCycles.size() > 0) {
                        onGestureFinishListener.OnGestureFinish(result,
                                stringBuffer.toString());
                    }
                    break;
            }
            invalidate();
        }
        return true;
    }


    //重置绘图
    public void resetView() {
        eventX = eventY = 0;
        for (int i = 0; i < 9; i++) {
            cycles[i].setOnTouch(false);
        }
        linedCycles.clear();
        linePath.reset();
        canContinue = true;
        postInvalidate();//在非ui线程刷新界面
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int cycleSize = cycles.length;
        for (LockCircle cycle : cycles) {
            // 画完并且错误
            if (!canContinue && !result) {
                if (cycle.isOnTouch()) {
                    drawInnerCycle(cycle, canvas, ERROE_COLOR);
                    drawOutsideCycle(cycle, canvas, ERROE_COLOR);
                } else {
                    drawOutsideCycle(cycle, canvas, NORMAL_COLOR);
                }
            }
            //绘画中
            else {
                if (cycle.isOnTouch()) {
                    drawInnerCycle(cycle, canvas, TOUCH_COLOR);
                    drawOutsideCycle(cycle, canvas, TOUCH_COLOR);
                } else {
                    drawOutsideCycle(cycle, canvas, NORMAL_COLOR);
                }
            }
        }

        if (isShowLine) {
            if (!canContinue && !result) {
                drawLine(canvas, ERROE_COLOR);
            } else {
                drawLine(canvas, TOUCH_COLOR);
            }
        }
    }


    /**
     * 画空心圆
     */
    private void drawOutsideCycle(LockCircle lockCircle, Canvas canvas, int color) {
        paintNormal.setColor(color);
        canvas.drawCircle(lockCircle.getOx(), lockCircle.getOy(),
                lockCircle.getR(), paintNormal);
    }


    /**
     * 画横线
     */
    private void drawLine(Canvas canvas, int color) {
        //构建路径
        linePath.reset();
        if (linedCycles.size() > 0) {
            int size = linedCycles.size();
            for (int i = 0; i < size; i++) {
                int index = linedCycles.get(i);
                float x = cycles[index].getOx();
                float y = cycles[index].getOy();
                if (i == 0) {
                    linePath.moveTo(x, y);
                } else {
                    linePath.lineTo(x, y);
                }
            }
            if (canContinue) {
                linePath.lineTo(eventX, eventY);
            } else {
                linePath.lineTo(
                        cycles[linedCycles.get(linedCycles.size() - 1)].getOx(),
                        cycles[linedCycles.get(
                                linedCycles.size() - 1)].getOy());
            }
            paintLines.setColor(color);
            canvas.drawPath(linePath, paintLines);
        }
    }


    /**
     * 画中心圆圆
     */
    private void drawInnerCycle(LockCircle myCycle, Canvas canvas, int color) {
        paintInnerCycle.setColor(color);
        canvas.drawCircle(myCycle.getOx(), myCycle.getOy(), myCycle.getR() / 3f,
                paintInnerCycle);
    }


    /**
     * 每个圆点类
     *
     * @author rxx
     * <p/>
     * 2014年12月12日  上午10:05:48
     */
    class LockCircle {
        /**
         * 圆心横坐标
         */
        private float ox;
        /**
         * 圆心纵坐标
         */
        private float oy;
        /**
         * 半径长度
         */
        private float r;
        /**
         * 代表数值
         */
        private Integer num;
        /**
         * 是否选择:false=未选中
         */
        private boolean onTouch;


        public float getOx() {
            return ox;
        }


        public void setOx(float ox) {
            this.ox = ox;
        }


        public float getOy() {
            return oy;
        }


        public void setOy(float oy) {
            this.oy = oy;
        }


        public void setOy(int oy) {
            this.oy = oy;
        }


        public float getR() {
            return r;
        }


        public void setR(float r) {
            this.r = r;
        }


        public Integer getNum() {
            return num;
        }


        public void setNum(Integer num) {
            this.num = num;
        }


        public boolean isOnTouch() {
            return onTouch;
        }


        public void setOnTouch(boolean onTouch) {
            this.onTouch = onTouch;
        }


        /**
         * 判读传入位置是否在圆心内部
         */
        public boolean isPointIn(int x, int y) {
            double distance = Math.sqrt(
                    (x - ox) * (x - ox) + (y - oy) * (y - oy));
            return distance < r;
        }
    }
}