package com.sanba.im.testcollapplication.widegt;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.sanba.im.testcollapplication.R;

import java.util.ArrayList;

/**
 * 作者:Created by 简玉锋 on 2017/2/22 11:34
 * 邮箱: jianyufeng@38.hn
 */

public class MyView extends View {
    //需要绘制的文本
    private String mText;
    //文本的颜色
    private int mTextColor;
    //文本的大小
    private int mTextSize;
    //绘制文本时控制文本绘制的范围
    private Rect mBound;
    //绘制文本的画笔
    private Paint mPaint;

    private ArrayList<String> mTextList;

    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyView);
        mText = a.getString(R.styleable.MyView_myText);
        mTextColor = a.getColor(R.styleable.MyView_myText_Color, Color.BLACK);
        mTextSize = (int) a.getDimension(R.styleable.MyView_myText_Size, 100);
        a.recycle();//注意回收


        //初始化
//        mText = "123456789A";
//        mTextColor = Color.BLACK;
//        mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,12,getResources().getDisplayMetrics());

        mPaint = new Paint();
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);
        //获得绘制文本的宽高
        mBound = new Rect();
        mPaint.getTextBounds(mText, 0, mText.length(), mBound);
        mTextList = new ArrayList();
    }

    float lineNum;
    boolean isOneLine = true;
    float spLineNum;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec); //获取宽度的模式
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);//获取高度的模式
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);//获取宽度的尺寸
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);//获取高度的尺寸

        int width;
        int height;


        int textWidth = mBound.width(); //文本的宽度
        if (mTextList.size() == 0) {
            //将文本分段
            int padding = getPaddingLeft() + getPaddingRight();
            int specWidth = widthSize - padding; //可以显示文本的最大的宽度
            if (textWidth < specWidth) {
                //一行足以
                lineNum = 1;
                mTextList.add(mText);
            } else {
                //超过一行
                isOneLine = false;
                spLineNum = textWidth / specWidth;
                if ((spLineNum + "").contains(".")) {
                    lineNum = Integer.parseInt((spLineNum + "").substring(0, (spLineNum + "").indexOf("."))) + 1;
                } else {
                    lineNum = spLineNum;
                }
                int lineLength = (int) (mText.length() / spLineNum);
                Log.v("openxu", "文本总长度:" + mText);
                Log.v("openxu", "文本总长度:" + mText.length());
                Log.v("openxu", "能绘制文本的宽度:" + lineLength);
                Log.v("openxu", "需要绘制:" + lineNum + "行");
                Log.v("openxu", "lineLength:" + lineLength);
                for (int i = 0; i < lineNum; i++) {
                    String lineStr;
                    if (mText.length() < lineLength) {
                        lineStr = mText.substring(0, mText.length());

                    } else {
                        lineStr = mText.substring(0, lineLength);
                    }
                    Log.v("openxu", "lineStr:" + lineStr);
                    mTextList.add(lineStr);
                    if (!TextUtils.isEmpty(mText)) {
                        if (mText.length() < lineLength) {
                            mText = mText.substring(0, mText.length());
                        } else {
                            mText = mText.substring(lineLength, mText.length());
                        }
                    } else {
                        break;
                    }
                }
            }
        }

        if (widthMode == MeasureSpec.EXACTLY) {
            //如果match_parent或者具体的值，直接赋值
            width = widthSize;
        } else {
            //如果是wrap_content，我们要得到控件需要多大的尺寸
            if (isOneLine) {
                //控件的宽度就是文本的宽度加上两边的内边距。内边距就是padding值，在构造方法执行完就被赋值
                width = (getPaddingLeft() + getPaddingRight() + textWidth);
            } else {
                //如果是多行，说明控件宽度应该填充父窗体
                width = widthSize;
            }

        }
        //高度跟宽度处理方式一样
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            //如果是wrap_content，我们要得到控件需要多大的尺寸
            int textHeight = mBound.height();
            if (isOneLine) {
                height = getPaddingTop() + getPaddingBottom() + textHeight;
            }else {
                height  = (int) (getPaddingTop()+getPaddingBottom() + textHeight * lineNum);
            }
        }
        //保存测量宽度和测量高度
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制文本
        for (int i = 0; i < mTextList.size(); i++) {
            mPaint.getTextBounds(mTextList.get(i),0,mTextList.get(i).length(),mBound);
            canvas.drawText(mTextList.get(i), getWidth() / 2 - mBound.width() / 2, getPaddingTop() + (mBound.height()*(i+1)), mPaint);
        }














    }
}
