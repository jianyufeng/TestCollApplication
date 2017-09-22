package com.sanba.im.testcollapplication.drag;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.sanba.im.testcollapplication.R;

/**
 * 作者:Created by 简玉锋 on 2017/2/21 18:10
 * 邮箱: jianyufeng@38.hn
 */

public class VDHLayout extends LinearLayout {
    private ViewDragHelper mHelper;
    private View mDragView;
    private View mAutoBackView;
    private View mEdgeTrackerView;
    private Point mAutoBackOriginPos = new Point();

    public VDHLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHelper = ViewDragHelper.create(this, 1, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                //那个View可以拖动
                return child == mDragView || child == mAutoBackView;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                //返回水平边界
                final int leftBound = getPaddingLeft();
                final int rightBound = getWidth() - child.getWidth() - getPaddingRight();
                final int newLeft = Math.min(Math.max(left, leftBound), rightBound);
                return newLeft;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                //返回垂直边界
                final int topBound = getPaddingTop();
                final int bottomBound = getHeight() - child.getHeight() - getPaddingBottom();
                final int newTop = Math.min(Math.max(top, topBound), bottomBound);
                return newTop;
            }

            //手指释放的时候回调
            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                //mAutoBackView手指释放时可以自动回去
                if (releasedChild == mAutoBackView) {
                    mHelper.settleCapturedViewAt(mAutoBackOriginPos.x, mAutoBackOriginPos.y);
                    invalidate();
                }
            }

            //在边界拖动时回调
            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                mHelper.captureChildView(mEdgeTrackerView, pointerId);
            }

            // 如果你用Button测试，或者给TextView添加了clickable = true ，都记得重写下面这两个方法：
            @Override
            public int getViewHorizontalDragRange(View child) {
                return getMeasuredWidth() - child.getMeasuredWidth();
            }

            //方法的返回值应当是该childView横向或者纵向的移动的范围，当前如果只需要一个方向移动，可以只复写一个。
            @Override
            public int getViewVerticalDragRange(View child) {
                return getMeasuredHeight() - child.getMeasuredHeight();
            }

            //当ViewDragHelper状态发生变化时回调（IDLE,DRAGGING,SETTING[自动滚动时]）
            @Override
            public void onViewDragStateChanged(int state) {
                super.onViewDragStateChanged(state);
            }

            //当captureview的位置发生改变时回调
            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
            }

            //当captureview被捕获时回调
            @Override
            public void onViewCaptured(View capturedChild, int activePointerId) {
                super.onViewCaptured(capturedChild, activePointerId);
            }

            //当触摸到边界时回调。
            @Override
            public void onEdgeTouched(int edgeFlags, int pointerId) {
                super.onEdgeTouched(edgeFlags, pointerId);
            }

            //true的时候会锁住当前的边界，false则unLock。
            @Override
            public boolean onEdgeLock(int edgeFlags) {
                return super.onEdgeLock(edgeFlags);
            }

            //改变同一个坐标（x,y）去寻找captureView位置的方法。（具体在：findTopChildUnder方法中）
            @Override
            public int getOrderedChildIndex(int index) {
                return super.getOrderedChildIndex(index);
            }
        });
        mHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    @Override
    public void computeScroll() {
        if (mHelper.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (mAutoBackView != null) {
            mAutoBackOriginPos.x = mAutoBackView.getLeft();
            mAutoBackOriginPos.y = mAutoBackView.getTop();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mDragView = findViewById(R.id.drag);
        mAutoBackView = findViewById(R.id.back);
        mEdgeTrackerView = findViewById(R.id.edge);
    }
}
