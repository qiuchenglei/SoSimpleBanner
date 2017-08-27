package com.github.qiu.sosimplebanner.banner.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.qiu.sosimplebanner.R;
import com.github.qiu.sosimplebanner.banner.BaseBanner;
import com.github.qiu.sosimplebanner.bean.CustomBean;

/**
 * @author qcl
 * @time 2017/8/7
 * @email 13203727217@163.com
 * 自定义banner示例1
 */
public class CustomBanner1 extends BaseBanner<CustomBean.BannersBean> {
    private TextView tvTitle;

    public CustomBanner1(Context context) {
        super(context);
    }

    public CustomBanner1(Context context, AttributeSet paramAttributeSet) {
        super(context, paramAttributeSet);
    }

    @Override
    protected void initView(Context context, AttributeSet paramAttributeSet) {
        View view = View.inflate(context, R.layout.custom1_banner, this);
        pager = view.findViewById(R.id.vp_banner);
        indicator = view.findViewById(R.id.indicator_banner);
        tvTitle = view.findViewById(R.id.tv_banner_title);
    }

    @Override
    protected void onPageSelected(int position) {
        //不要用postion，positioni是无限循环的viewpager的页数，数值很大，所以用currentIndex
        tvTitle.setText(bannerList.get(currentIndex).text);
    }

    @Override
    protected ImageView initItemView(int index, View itemView) {
        ImageView imageView = (ImageView) itemView;
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;

    }

    @Override
    protected View createItemView() {
        return new ImageView(context);
    }

}
