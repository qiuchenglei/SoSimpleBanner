package com.github.qiu.sosimplebanner.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.github.qiu.sosimplebanner.R;
import com.github.qiu.sosimplebanner.bean.BaseBannerBean;
import com.github.qiu.sosimplebanner.util.ImageUtil;
import com.github.qiu.sosimplebanner.view.CirclesIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qcl
 * @time 2017/8/7
 * @email 13203727217@163.com
 * Banner界面重构，以方便更灵活的实现类似新闻类的多种不同的布局方式
 */
public abstract class BaseBanner<T extends BaseBannerBean> extends FrameLayout {
    private static final int INTERVAL = 4000;//viewPager切换时间
    private static final int AD_CHANGE = 101;//viewPager切换指令

    protected CustomViewPager pager;
    protected CirclesIndicator indicator;

    private OnBannerClickListener<T> listener;
    private int[] placeHolderImgsRes;
    private boolean canLoop;// 一个广告的时候不用转

    //下面是子类中可用的
    protected int currentIndex;
    protected Context context;
    protected List<T> bannerList;

    public BaseBanner(Context context) {
        this(context, null);
    }

    public BaseBanner(Context context, AttributeSet paramAttributeSet) {
        super(context, paramAttributeSet);
        this.context = context;

        initView(context, paramAttributeSet);

        //按比例定高度（默认宽度充满手机显示宽度）
        TypedArray ta = context.obtainStyledAttributes(paramAttributeSet, R.styleable.BaseBanner);
        float scale = ta.getFloat(R.styleable.BaseBanner_scale, -1);
        if (scale > 0) {
            if (scale > 1) {
                scale = 1;
            }
            int width = getResources().getDisplayMetrics().widthPixels;
            int height = (int) (width * scale);
            pager.setLayoutParams(new LayoutParams(width, height));
        }
        ta.recycle();

        initListener();
    }

    /**
     * 继承修改布局，两个控件pager和indicator需在此处初始化
     */
    protected abstract void initView(Context context, AttributeSet paramAttributeSet);

    private void initListener() {
        //初始化
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                BaseBanner.this.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                currentIndex = position % indicator.getChildCount();
                indicator.setCurrentPosition(currentIndex);
                BaseBanner.this.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                    if (isLooping)
                        stopADLoop();
                } else {
                    if (!isLooping)
                        startADLoop();
                }
                BaseBanner.this.onPageScrollStateChanged(state);
            }
        });
    }

    /**
     * viewPager的滑动监听，可继承做相应逻辑
     */
    protected void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    /**
     * viewPager的滑动监听，可继承做相应逻辑
     */
    protected void onPageSelected(int position) {}

    /**
     * viewPager的滑动监听，可继承做相应逻辑
     */
    protected void onPageScrollStateChanged(int state) {}

    /**
     * 配置banner数据
     */
    public void setBannerData(List<T> bannerList) {
        if (bannerList == null || bannerList.isEmpty())
            return;

        this.bannerList = bannerList;
        indicator.setBannerViewPager(bannerList);

        if (bannerList.size() == 2 || bannerList.size() == 3) {
            List<T> list = new ArrayList<>(bannerList);
            bannerList.addAll(list);
        }

        setAdapter(configImgs(bannerList));

        onPageSelected(0);
    }

    private void setAdapter(List<View> ivList) {
        BannerAdapter adAdapter = new BannerAdapter(ivList);
        pager.setAdapter(adAdapter);
        adAdapter.notifyDataSetChanged();

        if (ivList.size() > 1) {
            canLoop = true;
            int aboutValue = Integer.MAX_VALUE >> 10;
            int firstPosition = aboutValue - aboutValue % ivList.size();//第一个position
            pager.setCurrentItem(firstPosition, false);
            startADLoop();
        }
    }

    /**
     * 配置数据
     */
    private List<View> configImgs(final List<T> bannerList) {
        List<View> ivs = new ArrayList<>();
        for (int i = 0; i < bannerList.size(); i++) {
            View itemView = createItemView();   //创建viewpager子view
            ImageView iv = initItemView(i, itemView);  //初始化子view中的imageview
            int imgRes = 0;
            if (placeHolderImgsRes != null && placeHolderImgsRes.length > 0) {
                imgRes = placeHolderImgsRes[i % placeHolderImgsRes.length]; //本地默认图片
            }

            if (TextUtils.isEmpty(bannerList.get(i).getThumb())) {
                if (imgRes != 0)
                    iv.setImageResource(imgRes);
            } else {
                ImageUtil.display(context, bannerList.get(i).getThumb(), iv, imgRes);
            }

            iv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onBannerClick(v, BaseBanner.this.bannerList.get(currentIndex), currentIndex);
                    }
                }
            });
            ivs.add(itemView);
        }
        return ivs;
    }

    /**
     * 初始化itemView
     * @param itemView ViewPager的itemView
     * @return itemView中所包含的需要展示横幅的ImageView
     */
    protected abstract ImageView initItemView(int index, View itemView);

    /**
     * 创建ViewPager的itemView
     * @return itemView
     */
    protected abstract View createItemView();

    /**
     * 配置本地的数据
     *
     * @param localImgRes
     */
    private List<View> configLocalData(int[] localImgRes) {

        ArrayList<Integer> imgSrc = new ArrayList<>();
        for (int i = 0; i < localImgRes.length; i++) {
            imgSrc.add(localImgRes[i]);
        }
        if (imgSrc.size() == 2 || imgSrc.size() == 3) {
            imgSrc.addAll(imgSrc);
        }
        List<View> ivs = new ArrayList<>();
        for (int i = 0; i < imgSrc.size(); i++) {
            View itemView = createItemView();
            ImageView iv = initItemView(i, itemView);
            int imgRes = imgSrc.get(i % imgSrc.size());//本地图片
            if (imgRes != 0) {
                iv.setImageResource(imgRes);
            }
            iv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onBannerClick(v, null, currentIndex);
                    }
                }
            });
            ivs.add(itemView);
        }
        return ivs;
    }

    // 启动循环广告的线程
    private void startADLoop() {
        if (canLoop) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler.sendEmptyMessageDelayed(AD_CHANGE, INTERVAL);
            isLooping = true;
        }
    }

    private boolean isLooping;

    // 停止循环广告的线程，清空消息队列
    public void stopADLoop() {
        isLooping = false;
        if (mHandler != null) {
            mHandler.removeMessages(AD_CHANGE);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopADLoop();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == AD_CHANGE) {
                pager.setCurrentItem(pager.getCurrentItem() + 1);
                mHandler.sendEmptyMessageDelayed(AD_CHANGE, INTERVAL);
            }
        }
    };

    public interface OnBannerClickListener<U> {
        void onBannerClick(View arg0, U bannerBean, int position);

    }

    /**
     * 配置banner的监听事件
     */
    public void setOnBannerClickListener(OnBannerClickListener<T> listener) {
        this.listener = listener;
    }

    /**
     * 配置本地的轮播数据（可支持多张，但一般情况都是一张占位图片，有时候做个静态数据会用到多张）
     * @param isCurrentDisplay 是否立即显示
     * @param placeHolderImgsRes  本地resource图片，占位图片（如果加载网路图片，成功后会替换掉）
     */
    public void setPlaceHolderImgsRes(boolean isCurrentDisplay, int... placeHolderImgsRes) {
        this.placeHolderImgsRes = placeHolderImgsRes;
        if (isCurrentDisplay) {
            indicator.setBannerViewPager(placeHolderImgsRes);

            setAdapter(configLocalData(placeHolderImgsRes));
        }
    }

    /**
     * 配置本地的轮播数据（可支持多张，但一般情况都是一张占位图片，有时候做个静态数据会用到多张）
     * @param placeHolderImgsRes    本地resource图片，占位图片（如果加载网路图片，成功后会替换掉）
     */
    public void setPlaceHolderImgsRes(int... placeHolderImgsRes) {
        setPlaceHolderImgsRes(false, placeHolderImgsRes);
    }

}