package com.touch.touchdemo;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingParent;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.view.NestedScrollingParent3;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author weilgu
 * @time 2020/7/3
 * @des
 */
public class MyRv extends RecyclerView implements NestedScrollingParent2 , NestedScrollingParent {

    private final NestedScrollingParentHelper mParentHelper;
    private float topBarHeight;

    public MyRv(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyRv(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        topBarHeight = dp2px(50);
        mParentHelper = new NestedScrollingParentHelper(this);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
        Log.i("weilgu","onStartNestedScroll ");
        return true;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {
        mParentHelper.onNestedScrollAccepted(child,target,axes,type);
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        onNestedScrollAccepted(child,target,axes,ViewCompat.TYPE_TOUCH);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return onStartNestedScroll(child,target,nestedScrollAxes, ViewCompat.TYPE_TOUCH);
    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {

    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        onNestedScroll(target,dxConsumed,dyConsumed,dxUnconsumed,dyUnconsumed,ViewCompat.TYPE_TOUCH);
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {

    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        onNestedPreScroll(target,dx,dy,consumed,ViewCompat.TYPE_TOUCH);
    }

    //consumed parent 消耗的距离
    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
        int lastItemPosition = layoutManager.findLastVisibleItemPosition();
        View lastItem = layoutManager.findViewByPosition(lastItemPosition);
        int statusBarHeight = getStatusBarHeight(getContext());
        float translationY = lastItem.getTranslationY();
        float contentDy = translationY - statusBarHeight - dy;
        Log.i("weilgu","contentDy " + contentDy);
        Log.i("weilgu","translationY " + translationY);
        Log.i("weilgu","statusBarHeight " + statusBarHeight);
        //向上滑动
        if (dy > 0){
            if (contentDy >= topBarHeight){ //lastItem还未滑动到topBar
                consumed[1] = dy; //parent y轴上消耗的距离
            }else{
                consumed[1] = (int)(translationY - topBarHeight - statusBarHeight);//parent y轴上消耗的距离
            }
        }
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {


        return super.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    public static int dp2px(float dipValue) {
        final float scale = Resources.getSystem().getDisplayMetrics() != null ? Resources.getSystem().getDisplayMetrics().density : 1;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }
}
