package com.sanba.im.testcollapplication.scroll;

import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.sanba.im.testcollapplication.R;

/**
 * 作者:Created by 简玉锋 on 2017/2/25 15:29
 * 邮箱: jianyufeng@38.hn
 */

public class BounceActivity extends AppCompatActivity {
    private TextView tv  ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bounce_layout);
//        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Snackbar.make(v,"12456",Snackbar.LENGTH_SHORT).show();
//            }
//        });
         tv  = (TextView) findViewById(R.id.tv);
        tv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                event.getX();
                event.getY();
                event.getRawX();
                event.getRawY();
                return false;
            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (animation !=null && !animation.hasEnded()){
//                    tv.clearAnimation();
                    animation.cancel();
                    return;
                }
//                animation = new RotateAnimation(0,360,RotateAnimation.RELATIVE_TO_PARENT,0.1f,RotateAnimation.RELATIVE_TO_PARENT, 0.1f);
              animation = new TranslateAnimation(0,0,100,100);
                animation.setDuration(2000);
                animation.setRepeatCount(1);
                animation.setRepeatMode(Animation.REVERSE);
                animation.setFillAfter(true);
                tv.startAnimation(animation);
            }
        });
    }
    private TranslateAnimation animation;
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!hasFocus){
            return;
        }
        Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        rect.width();
        rect.height();
        int top = rect.top;
        int bottom = rect.bottom;
        int right = rect.right;
        int left = rect.left;
        tv.getLeft();
        tv.getTranslationX();
        Log.e("s","");
        tv.setBackgroundResource(R.drawable.haaha);
        AnimationDrawable animationDrawable= (AnimationDrawable) tv.getBackground();
        animationDrawable.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        event.getX();
        event.getY();
        event.getRawX();
        event.getRawY();
        return super.onTouchEvent(event);
    }
}
