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
 * 自定义banner 示例2
 */
public class CustomBanner2 extends BaseBanner<CustomBean.BannersBean> {

    public CustomBanner2(Context context) {
        super(context);
    }

    public CustomBanner2(Context context, AttributeSet paramAttributeSet) {
        super(context, paramAttributeSet);
    }

    @Override
    protected void initView(Context context, AttributeSet paramAttributeSet) {
        View view = View.inflate(context, R.layout.custom2_banner, this);
        pager = view.findViewById(R.id.vp_banner);
        indicator = view.findViewById(R.id.indicator_banner);
    }

    @Override
    protected void onPageSelected(int position) {

    }

    @Override
    protected ImageView initItemView(int index, View itemView) {
        ImageView imageView = itemView.findViewById(R.id.iv);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if (bannerList != null && index < bannerList.size()) {//只配置本地占位图的时候，bannerlist为null
            TextView tv = itemView.findViewById(R.id.tv);
            tv.setText(bannerList.get(index).text);//由于
        }
        return imageView;

    }

    @Override
    protected View createItemView() {
        return View.inflate(context, R.layout.custom2_vp_itemview, null);
    }

}
