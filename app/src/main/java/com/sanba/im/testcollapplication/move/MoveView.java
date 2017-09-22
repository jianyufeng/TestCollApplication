package com.sanba.im.testcollapplication.move;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

/**
 * 作者:Created by 简玉锋 on 2017/2/24 13:40
 * 邮箱: jianyufeng@38.hn
 */

public class MoveView extends View {
//    private LinearLayout parent;
    private Scroller scroller;
    public MoveView(Context context) {
        this(context, null);
    }

    public MoveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MoveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        scroller =  new Scroller(context);
    }

    private int downX;
    private int downY;

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()){
            ((View)getParent()).scrollTo(scroller.getCurrX(),scroller.getCurrY());
        }
        invalidate();
    }




     @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = x;
                downY = y;
                break;

            case MotionEvent.ACTION_MOVE:
//                parent =(LinearLayout) getParent();
                int offX = x - downX;
                int offY = y - downY;
//                int left = getLeft() + offX;
//                if (left < 0) {
//                    left = 0;
//                    return true;
//                }
//                int top = getTop() + offY;
//                if (top < 0) {
//                    top = 0;
//                    return true;
//                }
//                int right = getRight() + offX;
//                if (right > parent.getWidth()) {
//                    right = 0;
//                    return true;
//                }
//                int bottom = getBottom() + offY;
//                if (bottom > parent.getHeight()) {
//                    bottom = 0;
//                    return true;
//                }
//                layout(left, top, right, bottom);

//                offsetLeftAndRight(offX);
//                offsetTopAndBottom(offY);

//                ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) getLayoutParams();
//                lp.leftMargin = getLeft() + offX ;
//                lp.topMargin = getTop() + offY;
//                setLayoutParams(lp);

                ((View)getParent()).scrollBy(-offX,-offY);
                break;
            case MotionEvent.ACTION_UP:
                View viewGroup = (View) getParent();
                scroller.startScroll(viewGroup.getScrollX(),
                        viewGroup.getScrollY(),
                        -viewGroup.getScrollX()
                ,-viewGroup.getScrollY());
            break;
        }
        return true;
    }
}
