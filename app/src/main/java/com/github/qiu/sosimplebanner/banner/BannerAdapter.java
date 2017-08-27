package com.github.qiu.sosimplebanner.banner;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author qcl
 * @time 2017/8/7
 * @email 13203727217@163.com
 * banner的viewpager的适配器
 */
public class BannerAdapter extends PagerAdapter {

    private List<View> ivList; // ImageView的集合
    private int count; // 广告的数量

    public BannerAdapter(List<View> ivList) {
        this.ivList = ivList;
        count = ivList == null ? 0 : ivList.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        if (count < 2) {
            return count;
        } else {
            return Integer.MAX_VALUE;
        }
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        int index = position % count;
        container.removeView(ivList.get(index));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int newPosition = position % count;
        View view = ivList.get(newPosition);
        if (view.getParent() != null)
            container.removeView(view);
        container.addView(view);//更新图片在container中的位置（把iv放至container末尾）
        return view;
    }
}
