package com.github.qiu.sosimplebanner.banner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.qiu.sosimplebanner.R;
import com.github.qiu.sosimplebanner.bean.CustomBean;

/**
 * @author qcl
 * @time 2017/8/7
 * @email 13203727217@163.com
 * 常用的简单的banner，仅展示图片
 */
public class CommonBanner extends BaseBanner<CustomBean.BannersBean> {

    public CommonBanner(Context context) {
        super(context);
    }

    public CommonBanner(Context context, AttributeSet paramAttributeSet) {
        super(context, paramAttributeSet);
    }

    @Override
    protected void initView(Context context, AttributeSet paramAttributeSet) {
        View view = View.inflate(context, R.layout.custom1_banner, this);
        pager = view.findViewById(R.id.vp_banner);
        indicator = view.findViewById(R.id.indicator_banner);
    }

    @Override
    protected void onPageSelected(int position) {
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
