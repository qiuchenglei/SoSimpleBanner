package com.github.qiu.sosimplebanner.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;


import com.github.qiu.sosimplebanner.R;

import java.util.List;

/**
 * viewpager简单指示器
 */
public class CirclesIndicator extends LinearLayout {

    private static final String TAG = "CirclesIndicator";

    private int selectedColor;
    private int unselectedColor;
    private int selectedSize;
    private int unselectedSize;
    private int margin;

    private int currentPosition;
    private ViewPager viewPager;

    public CirclesIndicator(Context context) {
        super(context);
        init(context, null, 0);
    }

    public CirclesIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public CirclesIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.CirclesIndicator, defStyle, 0);

        this.selectedColor = a.getColor(R.styleable.CirclesIndicator_circleColorSelected,
                ContextCompat.getColor(context, R.color.white));
        this.unselectedColor = a.getColor(R.styleable.CirclesIndicator_circleColorUnselected,
                ContextCompat.getColor(context, R.color.gray_89));
        this.selectedSize = a.getDimensionPixelSize(R.styleable.CirclesIndicator_circleSizeSelected,
                8);
        this.unselectedSize = a.getDimensionPixelSize(R.styleable.CirclesIndicator_circleSizeUnselected,
                8);
        int space = a.getDimensionPixelSize(R.styleable.CirclesIndicator_space,
                8);
        margin = space / 2;
        a.recycle();

        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER);
    }

//    public void setViewPager(ViewPager viewPager) {
//        this.viewPager = viewPager;
//
//        if (this.viewPager == null) {
//            return;
//        }
//
//        this.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                setIndicator(position);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//
//        buildCircles(this.viewPager.getAdapter().getCount());
//    }

    /**
     * 针对banner的适配
     * 使用list配置指示器
     */
    public void setBannerViewPager(List list) {
        if (list != null && !list.isEmpty()){
            buildCircles(list.size());
        }
    }

    /**
     * 针对banner的适配
     * 使用数组配置指示器
     */
    public void setBannerViewPager(int[] placeholders) {
        if (placeholders != null && placeholders.length > 0){
            buildCircles(placeholders.length);
        }
    }

    public void setCurrentPosition(int position) {
        setIndicator(position);
    }

    private void buildCircles(int circlesCount) {
        Log.d(TAG, "buildCircles()");

        removeAllViews();

        if (circlesCount > 1) {
            for (int i = 0; i < circlesCount; i++) {
                View circle = new View(getContext());

                LayoutParams params = new LayoutParams(unselectedSize, unselectedSize);
                params.setMargins(margin, 0, margin, 0);
                addView(circle, params);

                circle.setBackgroundResource(R.drawable.bg_circle_indicator);
                ((GradientDrawable) circle.getBackground()).setColor(unselectedColor);
            }

            setMinimumHeight(selectedSize);

            setIndicator(0);
        }
    }

    private void setIndicator(int newPosition) {
        Log.d(TAG, "setIndicator()");

        if (newPosition < getChildCount()) {
            final View newCircle = getChildAt(newPosition);

            ((GradientDrawable) newCircle.getBackground()).setColor(selectedColor);

            if (unselectedSize != selectedSize) {

                ValueAnimator newPosAnimator = ValueAnimator.ofInt(unselectedSize, selectedSize);
                newPosAnimator.setDuration(200);
                newPosAnimator.setInterpolator(new DecelerateInterpolator());
                newPosAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int newRadius = (int) animation.getAnimatedValue();

                        LayoutParams params = (LayoutParams) newCircle.getLayoutParams();

                        params.width = newRadius;
                        params.height = newRadius;

                        newCircle.setLayoutParams(params);
                    }
                });
                newPosAnimator.start();
            }

            if (currentPosition != newPosition){
                final View currentCircle = getChildAt(currentPosition);
                ((GradientDrawable) currentCircle.getBackground()).setColor(unselectedColor);

                if (unselectedSize != selectedSize) {
                    ValueAnimator currentPosAnimator = ValueAnimator.ofInt(selectedSize, unselectedSize);
                    currentPosAnimator.setDuration(200);
                    currentPosAnimator.setInterpolator(new DecelerateInterpolator());
                    currentPosAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int newRadius = (int) animation.getAnimatedValue();

                            LayoutParams params = (LayoutParams) currentCircle.getLayoutParams();

                            params.width = newRadius;
                            params.height = newRadius;

                            currentCircle.setLayoutParams(params);
                        }
                    });
                    currentPosAnimator.start();
                }

                currentPosition = newPosition;
            }

        }
    }
}