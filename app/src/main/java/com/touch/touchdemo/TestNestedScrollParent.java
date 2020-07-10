package com.touch.touchdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingChild2;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.view.ScrollingView;
import androidx.core.view.ViewCompat;

/**
 * @author weilgu
 * @time 2020/7/8
 * @des
 */
public class TestNestedScrollParent extends FrameLayout implements NestedScrollingParent2, NestedScrollingChild2, ScrollingView {

    public TestNestedScrollParent(@NonNull Context context) {
        this(context,null);
    }

    public TestNestedScrollParent(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TestNestedScrollParent(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
        Log.i("weilgu","onStartNestedScroll");
        return true;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {
        Log.i("weilgu","onNestedScrollAccepted");
    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {
        Log.i("weilgu","onStopNestedScroll");
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        Log.i("weilgu","onNestedScroll");
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        Log.i("weilgu","onNestedPreScroll");
    }

    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes) {
        return onStartNestedScroll(child,target,axes, ViewCompat.TYPE_TOUCH);
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes) {
        onNestedScrollAccepted(child,target,axes,ViewCompat.TYPE_TOUCH);
    }

    @Override
    public void onStopNestedScroll(@NonNull View target) {
        onStopNestedScroll(target,ViewCompat.TYPE_TOUCH);
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        onNestedScroll(target,dxConsumed,dyConsumed,dxUnconsumed,dyUnconsumed,ViewCompat.TYPE_TOUCH);
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed) {
        onNestedPreScroll(target,dx,dy,consumed,ViewCompat.TYPE_TOUCH);
    }

    @Override
    public boolean onNestedFling(@NonNull View target, float velocityX, float velocityY, boolean consumed) {
        Log.i("weilgu","onNestedFling");
        return false;
    }

    @Override
    public boolean onNestedPreFling(@NonNull View target, float velocityX, float velocityY) {
        Log.i("weilgu","onNestedPreFling");
        return false;
    }

    @Override
    public int getNestedScrollAxes() {
        Log.i("weilgu","getNestedScrollAxes");
        return 0;
    }

    @Override
    public boolean startNestedScroll(int axes, int type) {
        Log.i("weilgu","child startNestedScroll");
        return true;
    }

    @Override
    public void stopNestedScroll(int type) {
        Log.i("weilgu","child stopNestedScroll");
    }

    @Override
    public boolean hasNestedScrollingParent(int type) {
        Log.i("weilgu","child hasNestedScrollingParent");
        return false;
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, @Nullable int[] offsetInWindow, int type) {
        Log.i("weilgu","child dispatchNestedScroll");
        return false;
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, @Nullable int[] consumed, @Nullable int[] offsetInWindow, int type) {
        Log.i("weilgu","child dispatchNestedPreScroll");
        return false;
    }

    @Override
    public int computeHorizontalScrollRange() {
        return 0;
    }

    @Override
    public int computeHorizontalScrollOffset() {
        return 0;
    }

    @Override
    public int computeHorizontalScrollExtent() {
        return 0;
    }

    @Override
    public int computeVerticalScrollRange() {
        return 0;
    }

    @Override
    public int computeVerticalScrollOffset() {
        return 0;
    }

    @Override
    public int computeVerticalScrollExtent() {
        return 0;
    }
}
