package com.github.qiu.sosimplebanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.qiu.sosimplebanner.banner.CommonBanner;
import com.github.qiu.sosimplebanner.banner.custom.CustomBanner1;
import com.github.qiu.sosimplebanner.banner.custom.CustomBanner2;
import com.github.qiu.sosimplebanner.bean.CustomBean;

import java.util.ArrayList;

/**
 * @author qcl
 * @time 2017/8/7
 * @email 13203727217@163.com
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CommonBanner commonBanner = findViewById(R.id.common_banner);
        CustomBanner1 customBanner1 = findViewById(R.id.custom_banner1);
        CustomBanner2 customBanner2 = findViewById(R.id.custom_banner2);

        commonBanner.setPlaceHolderImgsRes(true, R.mipmap.wuhan, R.mipmap.loading);

        CustomBean bean = parseNetwork1Respose();
        customBanner1.setBannerData(bean.banners);

        CustomBean bean2 = parseNetwork2Respose();
        customBanner2.setBannerData(bean2.banners);
    }


    /**
     * 这个是需要从服务端接口获取json数据然后转换为bean类,我这里测试直接使用假数据了
     *
     * @return CustomBean
     */
    private CustomBean parseNetwork1Respose() {
        CustomBean customBean = new CustomBean();
        customBean.status = 0;//成功

        CustomBean.BannersBean banner1 = new CustomBean.BannersBean();
        banner1.text = "我是不跟随的view--1";
        banner1.imgUrl = "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3043020277,2528231292&fm=26&gp=0.jpg";
        CustomBean.BannersBean banner2 = new CustomBean.BannersBean();
        banner2.text = "我是不跟随的view--2";
        banner2.imgUrl = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2782602456,4129616836&fm=26&gp=0.jpg";
        CustomBean.BannersBean banner3 = new CustomBean.BannersBean();
        banner3.text = "我是不跟随的view--3";
        banner3.imgUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502105950930&di=01fb8164489153e7a17b93aeefc56c6b&imgtype=0&src=http%3A%2F%2Fpic17.nipic.com%2F20111106%2F3135149_202902287000_2.jpg";

        customBean.banners = new ArrayList<>();
        customBean.banners.add(banner1);
        customBean.banners.add(banner2);
        customBean.banners.add(banner3);

        return customBean;
    }


    /**
     * 这个是需要从服务端接口获取json数据然后转换为bean类,我这里测试直接使用假数据了
     *
     * @return CustomBean
     */
    private CustomBean parseNetwork2Respose() {
        CustomBean customBean = new CustomBean();
        customBean.status = 0;//成功

        CustomBean.BannersBean banner1 = new CustomBean.BannersBean();
        banner1.text = "我是跟随的view--1";
        banner1.imgUrl = "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3043020277,2528231292&fm=26&gp=0.jpg";
        CustomBean.BannersBean banner2 = new CustomBean.BannersBean();
        banner2.text = "我是跟随的view--2";
        banner2.imgUrl = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2782602456,4129616836&fm=26&gp=0.jpg";
        CustomBean.BannersBean banner3 = new CustomBean.BannersBean();
        banner3.text = "我是跟随的view--3";
        banner3.imgUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502105950930&di=01fb8164489153e7a17b93aeefc56c6b&imgtype=0&src=http%3A%2F%2Fpic17.nipic.com%2F20111106%2F3135149_202902287000_2.jpg";

        customBean.banners = new ArrayList<>();
        customBean.banners.add(banner1);
        customBean.banners.add(banner2);
        customBean.banners.add(banner3);

        return customBean;
    }

}
