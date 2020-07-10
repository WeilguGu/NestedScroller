package com.touch.touchdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.view.NestedScrollingParentHelper;

/**
 * @author weilgu
 * @time 2020/7/6
 * @des
 */
public class MyNestedScroller extends FrameLayout implements NestedScrollingParent2 {

    private View mTopBar;
    private View headTopBar;
    private View headView;
    private View content;
    private View contentRv;
    private NestedScrollingParentHelper mParentHelper;
    private int mHeadTopBarHeight;
    private int mTopBarHeight;
    private int mHeadViewHeiget;

    public MyNestedScroller(@NonNull Context context) {
        super(context,null);
    }

    public MyNestedScroller(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);
    }

    public MyNestedScroller(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mParentHelper = new NestedScrollingParentHelper(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        /*headTopBar = findViewById(R.id.ll_head_top_bar);
        mTopBar = findViewById(R.id.ll_top_bar);
        headView = findViewById(R.id.rv_head);
        content = findViewById(R.id.ll_content);
        contentRv = findViewById(R.id.rv_content);*/
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /*mHeadTopBarHeight = headTopBar.getMeasuredHeight();
        mTopBarHeight = mTopBar.getMeasuredHeight();
        mHeadViewHeiget = headView.getMeasuredHeight();
        ViewGroup.LayoutParams layoutParams = content.getLayoutParams();
        layoutParams.height = getMeasuredHeight() - mTopBarHeight;
        content.setLayoutParams(layoutParams);*/
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        headView.layout(0,mHeadTopBarHeight,right,bottom);
        content.layout(0,mHeadTopBarHeight + mHeadTopBarHeight,right,bottom);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
        return true;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {

    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {

    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {

    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {

    }
}
