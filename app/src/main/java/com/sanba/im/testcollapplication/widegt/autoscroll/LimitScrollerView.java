package com.sanba.im.testcollapplication.widegt.autoscroll;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.sanba.im.testcollapplication.R;

/**
 * 作者:Created by 简玉锋 on 2017/2/23 14:03
 * 邮箱: jianyufeng@38.hn
 */

public class LimitScrollerView extends LinearLayout implements View.OnClickListener {
    private LinearLayout ll_content1;
    private LinearLayout ll_content2;
    private int limit;
    private int durationTime;
    private int periodTime;
    private int scrollHeight;

    private boolean boundData;


    private final int MSG_SETDATA = 1;
    private final int MSG_SCROL = 2;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_SETDATA) {
                boundData(true);
            } else if (msg.what == MSG_SCROL) {
                //继续动画
                startAnimation();
            }
        }
    };
    private boolean isCancel;


    public LimitScrollerView(Context context) {
        this(context, null);
    }

    public LimitScrollerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LimitScrollerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //将控件组合挂载到自己身上
        LayoutInflater.from(context).inflate(R.layout.auto_scroll_view, this, true);
        ll_content1 = (LinearLayout) findViewById(R.id.ll_content1);
        ll_content2 = (LinearLayout) findViewById(R.id.ll_content2);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LimitScrollerView);
        limit = a.getInt(R.styleable.LimitScrollerView_limit, 1);
        durationTime = a.getInt(R.styleable.LimitScrollerView_durationTime, 1000);
        periodTime = a.getInt(R.styleable.LimitScrollerView_periodTime, 1000);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //设置高度为整体高度的一般，以达到遮盖预备容器的效果
        setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight() / 2);
        //此处记下控件的高度，此高度就是动画执行时向上滚动的高度
        scrollHeight = getMeasuredHeight();
    }

    private int dataIndex;

    private  synchronized void  boundData(boolean first) {
        if (adapter == null || adapter.getCount() <= 0) {
            return;
        }
        if (first) {
            //第一次绑定数据，需要为两个容器添自条目
            boundData = true;
            ll_content1.removeAllViews();
            for (int i = 0; i < limit; i++) {
                if (dataIndex >= adapter.getCount()) {
                    dataIndex = 0;
                }
                View view = adapter.getView(dataIndex);
                //设置点击监听
                view.setClickable(true);
                view.setOnClickListener(this);


                ll_content1.addView(view);
                dataIndex++;
            }
        }
        //每次动画结束之后，为预备容器添加新条目
        ll_content2.removeAllViews();
        for (int i = 0; i < limit; i++) {
            if (dataIndex>=adapter.getCount()){
                dataIndex = 0;
            }
            View view = adapter.getView(dataIndex);
            //设置点击监听
            view.setClickable(true);
            view.setOnClickListener(this);
            ll_content2.addView(view);
            dataIndex++;
        }
    }
    private void startAnimation(){
        if (isCancel){
            return;
        }
        //当前展示的容器，从当前位置(0),向上滚动scrollHeight
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(ll_content1,"Y",ll_content1.getY(),ll_content1.getY() - scrollHeight);
        //预备容器，从当前位置向上滚动scrollHeight
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(ll_content2,"Y",ll_content2.getY(),ll_content2.getY() - scrollHeight);
        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(durationTime);
        animSet.playTogether(anim1,anim2);
        animSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //滚动结束后，now的位置变成了 -scrollHeight,这时将他移动到最底下
                ll_content1.setY(scrollHeight);
                //dowm的位置变为0，也就是当前看见的
                ll_content2.setY(0);
                //引用交换
                LinearLayout temp = ll_content1;
                ll_content1 = ll_content2;
                ll_content2 = temp;
                //给不可见的控件绑定新数据
                boundData(false);
                //停留指定时间后，重新动画
                handler.removeMessages(MSG_SCROL);   //先清空所有滚动消息，避免滚动错乱
                handler.sendEmptyMessageDelayed(MSG_SCROL,periodTime);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animSet.start();
    }
    public void startScroll() {
        if (adapter==null || adapter.getCount()<=0){
            return;
        }
        if (!boundData){
            handler.sendEmptyMessage(MSG_SETDATA);
        }
        isCancel = false;
        handler.removeMessages(MSG_SCROL);   //先清空所有滚动消息，避免滚动错乱
        handler.sendEmptyMessageDelayed(MSG_SCROL,periodTime);

    }
    public void cancel(){
        isCancel = true;
    }

    @Override
    public void onClick(View v) {
        if(clickListener!=null){
            Object obj = v.getTag();
            clickListener.onItemClick(obj);
        }
    }
    public void setItemClickListener(OnItemClickListener clickListener){
        this.clickListener =clickListener;
    }
    interface OnItemClickListener{
        public void onItemClick(Object obj);
    }
    private OnItemClickListener clickListener;
    interface LimitScrollAdapter {

        public int getCount();

        public View getView(int index);


    }

    private LimitScrollAdapter adapter;

    public void setDataAdapter(LimitScrollAdapter adapter) {
        this.adapter = adapter;
    }
}
