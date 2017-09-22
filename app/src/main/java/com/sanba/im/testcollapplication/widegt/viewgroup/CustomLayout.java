package com.sanba.im.testcollapplication.widegt.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 作者:Created by 简玉锋 on 2017/2/23 10:34
 * 邮箱: jianyufeng@38.hn
 */

public class CustomLayout extends ViewGroup {
    public CustomLayout(Context context) {
        this(context, null);
    }

    public CustomLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    /**
     * 要求所有的孩子测量自己的大小，然后根据这些孩子的大小完成自己的尺寸测量
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取此ViewGrou上级容器为其推荐的宽高，以及计算模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        int layoutWidth = 0;
        int layoutHeight = 0;


        int cWidth = 0;
        int cHeight = 0;
        int count = getChildCount();

        //计算出所有childView的宽高
//        measureChildren(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i <count; i++) {
            View child = getChildAt(i);
            measureChildWithMargins(child,widthMeasureSpec,0,heightMeasureSpec,0);
        }
        CustomLayoutParams params = null;
        if (widthMode == MeasureSpec.EXACTLY) {
            //如果布局容器的宽度模式是确定的（具体的size或者match_parent），直接使用父窗体建议的宽度
            layoutWidth = sizeWidth;
        } else {
            //如果是未指定或者wrap_content，我们都按照包裹内容做，宽度方向上只需要拿到所有子控件中宽度做大的作为布局宽度
            for (int i = 0; i < count; i++) {
                View child = getChildAt(i);
                cWidth = child.getMeasuredWidth();
                int marginWidth = cWidth + params.leftMargin + params.rightMargin;
                //获取子控件最大的宽度
                layoutWidth = marginWidth > layoutWidth ? marginWidth : layoutWidth;
            }
        }
        //高度很宽度处理思想一样
        if (heightMode == MeasureSpec.EXACTLY) {
            layoutHeight = sizeHeight;
        } else {
            for (int i = 0; i < count; i++) {
                View child = getChildAt(i);
                cHeight = child.getMeasuredHeight();
                int marginHeight = cHeight + params.topMargin + params.bottomMargin;
                layoutHeight = marginHeight > layoutHeight ? marginHeight : layoutHeight;
            }
        }
        //保存Layout的宽高
        setMeasuredDimension(layoutWidth, layoutHeight);

   /*     //计算出所有的childView的宽和高
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        //测量并保存layout的宽高(使用getDefaultSize时，wrap_content和match_perent都是填充屏幕)
        //稍后会重新写这个方法，能达到wrap_content的效果
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth()
                , widthMeasureSpec), getDefaultSize(getSuggestedMinimumHeight()
                , heightMeasureSpec));*/
    }

    int left;
    int top;


    /**
     * 为所有的子控件摆放位置.
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        int childMesureWidth = 0;
        int childMesureHeight = 0;

        CustomLayoutParams params = null;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            //注意此处不能使用getWidth和getHeight，这两个方法必须在onLayout执行完，才能正确获取宽高
            childMesureWidth = child.getMeasuredWidth();
            childMesureHeight = child.getMeasuredHeight();

            params = (CustomLayoutParams) child.getLayoutParams();

            switch (params.position) {
                case CustomLayoutParams.POSITION_MIDDLE://中间
                    left = (getWidth() - childMesureWidth) / 2 - params.rightMargin + params.leftMargin;
                    top = (getHeight() - childMesureHeight) / 2 - params.bottomMargin + params.topMargin;
                    break;
                case CustomLayoutParams.POSITION_LEFT: //左上方
                    left = params.leftMargin;
                    top = params.topMargin;
                    break;
                case CustomLayoutParams.POSITION_RIGHT:
                    left = getWidth() - childMesureWidth-params.rightMargin;
                    top = 0+params.topMargin;
                    break;
                case CustomLayoutParams.POSITION_BOTTOM:
                    left = 0 + params.leftMargin;
                    top = getHeight() - childMesureHeight - params.bottomMargin;
                    break;
                case CustomLayoutParams.POSITION_RIGHTANDBOTTOM:
                    left = getWidth() - childMesureWidth -params.rightMargin;
                    top = getHeight() - childMesureHeight - params.bottomMargin;
                    break;
                default:
                    break;

            }

        child.layout(left,top,left +childMesureWidth,top+childMesureHeight);
        }
















      /*  int layoutWidth = 0;//容器已经占据的宽度
        int layoutHeight = 0;//容器已经占据的高度
        int maxChildHeight = 0;//一行中子控件最高的高度，用于决定下一行高度应该在目前基础上累加多少
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            //注意此处不能使用getWidth和getHeight，这两个方法必须在onLayout执行完，才能正确获取宽高
            childMesureWidth = child.getMeasuredWidth();
            childMesureHeight = child.getMeasuredHeight();
            if (layoutWidth < getWidth()) {
                //如果一行未排满，继续向右边排列
                left = layoutWidth;
                right = left + childMesureWidth;
                top = layoutHeight;
                bottom = top + childMesureHeight;
            } else {
                //排满后换行
                layoutWidth = 0;
                layoutHeight += maxChildHeight;
                maxChildHeight = 0;

                left = layoutWidth;
                right = left + childMesureWidth;
                top = layoutHeight;
                bottom = top + childMesureHeight;
            }
            layoutWidth += childMesureWidth;//宽度累加
            if (childMesureHeight > maxChildHeight) {
                maxChildHeight = childMesureHeight;
            }
            //确定子控件的位置， 四个参数分别代表（左上右下）点的坐标值
            child.layout(left, top, right, bottom);
        }*/
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new CustomLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new CustomLayoutParams(p);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new CustomLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return p instanceof CustomLayoutParams;
    }
}
