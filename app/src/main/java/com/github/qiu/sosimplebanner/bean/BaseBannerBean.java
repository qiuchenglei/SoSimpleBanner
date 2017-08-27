package com.github.qiu.sosimplebanner.bean;

/**
 * @author qcl
 * @time 2017/8/7
 * @email 13203727217@163.com
 * 配置banner数据的bean需要继承此类,并实现其方法
 */
public abstract class BaseBannerBean {
    /**
     * @return 图片地址
     */
    public abstract String getThumb();
}
