package com.sanba.im.testcollapplication.scroll;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * 作者:Created by 简玉锋 on 2017/2/25 15:02
 * 邮箱: jianyufeng@38.hn
 */

public class BounceableLinearLayout extends LinearLayout {
    private Scroller mScroller;
    private GestureDetector mGestureDetector;

    public BounceableLinearLayout(Context context) {
        this(context, null);
    }

    public BounceableLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BounceableLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setClickable(true);
        setLongClickable(true);
        mScroller = new Scroller(context);
        mGestureDetector = new GestureDetector(context, new GestureListenerImpl());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                //手指抬起时回到最初位置
                prepareScroll(0, 0);

                break;
            default:
                //其余情况交给GestureDetector手势处理
                return mGestureDetector.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }


    class GestureListenerImpl implements GestureDetector.OnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        //控制拉动幅度:
        //int disY=(int)((distanceY - 0.5)/2);
        //亦可直接调用:
        //smoothScrollBy(0, (int)distanceY);
        // e1：第1个ACTION_DOWN MotionEvent
        // e2：最后一个ACTION_MOVE MotionEvent
        // distanceX：距离上次产生onScroll事件后，X抽移动的距离
        // distanceY：距离上次产生onScroll事件后，Y抽移动的距离
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            int disY = (int) ((distanceY - 0.5) / 2);//控制拉动幅度:
            beginScroll(0, disY);
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }
        // 用户按下触摸屏、快速移动后松开,这个时候，你的手指运动是有加速度的。
        // 由1个MotionEvent ACTION_DOWN,
        // 多个ACTION_MOVE, 1个ACTION_UP触发
        // e1：第1个ACTION_DOWN MotionEvent
        // e2：最后一个ACTION_MOVE MotionEvent
        // velocityX：X轴上的移动速度，像素/秒
        // velocityY：Y轴上的移动速度，像素/秒
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    }

    //滚动到目标位置
    private void prepareScroll(int fx, int fy) {
        System.out.println("prepareScroll_____>>>>>>>>>>>>>>>>>");
        int dx = fx - mScroller.getFinalX();
        int dy = fy - mScroller.getFinalY();
        beginScroll(dx, dy);
    }

    //设置滚动的相对偏移
    private void beginScroll(int dx, int dy) {
        System.out.println("smoothScrollBy()---> dx=" + dx + ",dy=" + dy);
        //第一,二个参数起始位置;第三,四个滚动的偏移量
        //从最后移动的位置开始移动 ，
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy,500);
        System.out.println("smoothScrollBy()---> " +
                "mScroller.getFinalX()=" + mScroller.getFinalX() + "," +
                "mScroller.getFinalY()=" + mScroller.getFinalY());
        //必须执行invalidate()从而调用computeScroll()
        invalidate();

    }

    @Override
    public void computeScroll() {
        System.out.println("computeScroll-------?????????????????");
        if (mScroller.computeScrollOffset()) {
            System.out.println("computeScroll()---> " +
                    "mScroller.getCurrX()=" + mScroller.getCurrX() + "," +
                    "mScroller.getCurrY()=" + mScroller.getCurrY());
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
        super.computeScroll();
    }
}
