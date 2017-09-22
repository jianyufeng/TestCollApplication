package com.sanba.im.testcollapplication.event.detector;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.sanba.im.testcollapplication.R;

/**
 * 作者:Created by 简玉锋 on 2017/2/25 09:41
 * 邮箱: jianyufeng@38.hn
 */

public class GestureDetectorActivity extends AppCompatActivity {
    private static final String TAG = "GestureDetectorActivity";
    private View view;
    private GestureDetector g;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_detector);
        view = findViewById(R.id.viewId);
        g = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            // 用户轻触触摸屏，由1个MotionEvent ACTION_DOWN触发
            @Override
            public boolean onDown(MotionEvent e) {
                Log.e(TAG, "onDown: ");
                return false;
            }

            /*
                 * 用户轻触触摸屏，尚未松开或拖动，由一个1个MotionEvent ACTION_DOWN触发
                 * 注意和onDown()的区别，强调的是没有松开或者拖动的状态
                 *
                 * 而onDown也是由一个MotionEventACTION_DOWN触发的，但是他没有任何限制，
                 * 也就是说当用户点击的时候，首先MotionEventACTION_DOWN，onDown就会执行，
                 * 如果在按下的瞬间没有松开或者是拖动的时候onShowPress就会执行，如果是按下的时间超过瞬间
                 * （这块我也不太清楚瞬间的时间差是多少，一般情况下都会执行onShowPress），拖动了，就不执行onShowPress。
                 */
            @Override
            public void onShowPress(MotionEvent e) {
                Log.e(TAG, "onShowPress: ");
            }

            // 用户（轻触触摸屏后）松开，由一个1个MotionEvent ACTION_UP触发
            ///轻击一下屏幕，立刻抬起来，才会有这个触发
            //从名子也可以看出,一次单独的轻击抬起操作,当然,如果除了Down以外还有其它操作,那就不再算是Single操作了,所以这个事件 就不再响应
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.e(TAG, "onSingleTapUp: " );
                return false;
            }
            // 用户按下触摸屏，并拖动，由1个MotionEvent ACTION_DOWN, 多个ACTION_MOVE触发
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                Log.e(TAG, "onScroll: " );
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                Log.e(TAG, "onLongPress: " );
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (e1.getX() - e2.getX() >100 && Math.abs(velocityX)> 200){
                    Log.e(TAG, "onFling::: "+"left" );
                }else if ((e1.getX() - e2.getX() <100 && Math.abs(velocityX)>200)){
                    Log.e(TAG, "onFling::: "+"right" );
                }

                Log.e(TAG, "onFling: " );
                return true;
            }
        });
        g.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                Log.e(TAG, "onSingleTapConfirmed: ");
                return false;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Log.e(TAG, "onDoubleTap: " );
                return false;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                Log.e(TAG, "onDoubleTapEvent: "+e.getAction() );
                return false;
            }
        });
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return g.onTouchEvent(event);
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: " );
            }
        });
        view.setFocusable(true);
        view.setClickable(true);
        view.setLongClickable(true);
    }
}
