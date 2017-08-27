package com.github.qiu.sosimplebanner.bean;

import java.util.List;

/**
 * @author qcl
 * @time 2017/8/7
 * @email 13203727217@163.com
 */

public class CustomBean {
    public int status; //0或1，对应成功或失败

    public List<BannersBean> banners;

    public static class BannersBean extends BaseBannerBean {

        public String imgUrl;
        public String text;
        @Override
        public String getThumb() {
            return imgUrl;
        }
    }
}