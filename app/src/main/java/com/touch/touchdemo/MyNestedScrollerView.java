package com.touch.touchdemo;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


/**
 * @author weilgu
 * @time 2020/7/7
 * @des
 */
class MyNestedScrollerView extends NestedScrollView {

    private Context mContext;
    private FlingHelper flingHelper;
    private boolean isStartFling;
    private int totalDy;
    private int veloctiyY;
    private RecyclerView mContentView;
    private RecyclerView mChildRV;
    private LinearLayout mllRl;
    private int mTopBarHeiget;


    public MyNestedScrollerView(@NonNull Context context) {
        this(context,null);
    }

    public MyNestedScrollerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyNestedScrollerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mTopBarHeiget = dp2px(50);
        Log.i("--------------------","mTopBarHeiget = " + mTopBarHeiget );
        init();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mContentView = findViewById(R.id.rlv);//ll_rv
        mllRl = findViewById(R.id.ll_child);//ll_rv
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        ViewGroup.LayoutParams layoutParams = mllRl.getLayoutParams();
        layoutParams.height = getMeasuredHeight() - mTopBarHeiget;
        mllRl.setLayoutParams(layoutParams);
        //mFoodViewHeiget = mFoodView.getMeasuredHeight();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void init() {
        flingHelper = new FlingHelper(mContext);
        setOnScrollChangeListener(new OnScrollChangeListener() {

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Log.i("weilgu","setOnScrollChangeListener scrollY = " + scrollY + "  oldScrollY = " + oldScrollY);
                if (isStartFling){
                    totalDy = 0;
                    isStartFling = false;
                }
                Log.i("--------------------","scrollY = " + scrollY + "\ngetChildAt(0).getMeasuredHeight() = " + getChildAt(0).getMeasuredHeight() + "\nv.getMeasuredHeight() = " + v.getMeasuredHeight());
                if (scrollY < (getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())){
                    dispatchChildFling();
                }
                totalDy += scrollY - oldScrollY;
            }
        });
    }

    private void dispatchChildFling(){
        Log.i("--------------------","veloctiyY = " + veloctiyY );
        if (veloctiyY != 0){
            double flingDistance = flingHelper.getSplineFlingDistance(veloctiyY);
            if (flingDistance > totalDy){
                childFling(flingHelper.getVelocityByDistance(flingDistance - Double.valueOf(totalDy)));
            }
        }
        totalDy = 0;
        veloctiyY = 0;
    }

    private void childFling(int velY){
        RecyclerView childRecycler = getChildRecyclerView(mContentView);
        if (childRecycler != null){
            childRecycler.fling(0,velY);
        }
    }

    @Override
    public void fling(int velocityY){
        //super.fling(velocityY);
        Log.i("--------------------","fling  veloctiyY = " + veloctiyY );
        if (velocityY < 0){
            this.veloctiyY = 0;
        }else{
            isStartFling = true;
            this.veloctiyY = velocityY;
        }
    }

    public RecyclerView getChildRecyclerView(ViewGroup viewGroup){
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = viewGroup.getChildAt(i);
            if (child instanceof RecyclerView){
                mChildRV = (RecyclerView)child;
                return mChildRV;
            }else if (child instanceof ViewGroup){
                mChildRV = getChildRecyclerView((ViewGroup) child);
                return mChildRV;
            }
            continue;
        }
        return null;
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed,int type) {
        //
        Log.i("----------","onNestedPreScroll dy = " + dy + "  getScrollY() = " + getScrollY() + "   mContentView.getMeasuredHeight() = " + mContentView.getMeasuredHeight());
        boolean hideTop = dy > 0 && getScrollY() < mContentView.getMeasuredHeight() - mTopBarHeiget;
        if (hideTop){
            scrollBy(0,dy);
            consumed[1] = dy;
        }
    }

    public static int dp2px(float dipValue) {
        final float scale = Resources.getSystem().getDisplayMetrics() != null ? Resources.getSystem().getDisplayMetrics().density : 1;
        return (int) (dipValue * scale + 0.5f);
    }

    public RecyclerView getChildRV() {
        return mChildRV;
    }
}
