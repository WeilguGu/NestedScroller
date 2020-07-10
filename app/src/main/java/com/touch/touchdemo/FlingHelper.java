package com.touch.touchdemo;

import android.content.Context;
import android.view.ViewConfiguration;

/**
 * @author weilgu
 * @time 2020/7/7
 * @des
 */
public class FlingHelper {

    private Context mContext;
    private final float FLING_FRICTION;
    private float mPhysicalCoeff;
    private final double DECELERATION_RATE;

    public FlingHelper(Context context){
        this.mContext = context;
        mPhysicalCoeff = mContext.getResources().getDisplayMetrics().density * 160.0f * 386.0878f * 0.84f;
        FLING_FRICTION = ViewConfiguration.getScrollFriction();
        DECELERATION_RATE = Math.log(0.78) / Math.log(0.9);
    }

    private double getSplineDeceleration(int i){
        return Math.log(0.35f * Math.abs(i) / (FLING_FRICTION * mPhysicalCoeff));
    }

    private double getSplineDecelerationByDistance(double d){
        return (DECELERATION_RATE - 1.0) * Math.log(d / (FLING_FRICTION * mPhysicalCoeff) /DECELERATION_RATE);
    }

    /**
     * 根据加速度来获取需要fling的距离
     * @param i 加速度
     * @return fling的距离
     */
    public double getSplineFlingDistance(int i){
        return Math.exp(getSplineDeceleration(i) * (DECELERATION_RATE * (DECELERATION_RATE - 1))) * (DECELERATION_RATE * mPhysicalCoeff);
    }

    /**
     * 根据距离来获取加速度
     * @param d 距离
     * @return 返回加速度
     */
    public int getVelocityByDistance(double d){
        return (int)(Math.abs(Math.exp(getSplineDecelerationByDistance(d)) * FLING_FRICTION * mPhysicalCoeff / 0.3499999940395355));
    }
}
