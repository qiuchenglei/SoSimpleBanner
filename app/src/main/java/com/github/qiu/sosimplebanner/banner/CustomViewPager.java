package com.github.qiu.sosimplebanner.banner;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * @author qcl
 * @time 2017/8/7
 * @email 13203727217@163.com
 * 解决在viewpager刷新的同时滑动操作所导致的崩溃
 */
public class CustomViewPager extends ViewPager {
    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            super.onTouchEvent(ev);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("CustomViewPager", "touch异常");
        }
        return true;
    }
}
