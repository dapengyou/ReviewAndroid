package com.test.reviewandroid.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * @createTime: 2019-10-07
 * @author: lady_zhou
 * @Description:
 */
public class MyViewGroup extends ViewGroup {
    private static final int OFFSET = 100;//表示缩进的尺寸

    public MyViewGroup(Context context) {
        super(context);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //第一步：viewgroup自身测量
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //第二步：为每个子view计算测量的限制信息 mode/size
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heighMode = MeasureSpec.getMode(heightMeasureSpec);
        int heighSize = MeasureSpec.getSize(heightMeasureSpec);

        //第三步：把上一步确定的限制信息，传递给每一个子View，然后子View开始Measure自己的尺寸
        int childCount = getChildCount();//子View 的个数
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            ViewGroup.LayoutParams layoutParams = child.getLayoutParams();
            int childWidthSpec = getChildMeasureSpec(widthMeasureSpec, 0, layoutParams.width);
            int childHeightSpec = getChildMeasureSpec(heightMeasureSpec, 0, layoutParams.height);
            child.measure(childWidthSpec, childHeightSpec);
        }

        int width = 0;
        int height = 0;
        //第四步：获取子view测量完成后的尺寸


        //第五步：viewGroup根据自身的情况，计算自己的尺寸
        switch (widthMode) {//宽度
            case MeasureSpec.EXACTLY:
                width = widthSize;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                for (int i = 0; i < childCount; i++) {
                    View child = getChildAt(i);
                    int widthAddOffset = i * OFFSET + child.getMeasuredWidth();//偏移量
                    width = Math.max(width, widthAddOffset);//取最大宽度
                }
                break;
            default:
                break;

        }
        switch (heighMode) {//高度
            case MeasureSpec.EXACTLY:
                height = heighSize;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                for (int i = 0; i < childCount; i++) {
                    View child = getChildAt(i);
                    height += child.getMeasuredHeight();
                }
                break;
            default:
                break;

        }

        //第六步： 保存自身的尺寸
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //第一步：遍历子view for
        //第二步：确定自己的规则
        //第三步：子view的测量尺寸
        //第四步：left、top、right、bottom
        //第五步：child.layout

        int left = 0;
        int top = 0;
        int right = 0;
        int bottom = 0;

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            left = i * OFFSET;
            right = left + child.getMeasuredWidth();
            bottom = top + child.getMeasuredHeight();

            child.layout(left, top, right, bottom);
            top += child.getMeasuredHeight();//不考虑padding、margin等
        }

    }
}
