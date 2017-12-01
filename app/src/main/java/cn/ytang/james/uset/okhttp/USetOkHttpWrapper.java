package cn.ytang.james.uset.okhttp;

import android.text.TextUtils;

import java.io.IOException;

import cn.ytang.james.uset.okhttp.config.USetOkHttpConfigImpl;
import cn.ytang.james.uset.utils.CollectionUtils;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * wrapper,a single instance, for OkHttp,retrofit,rxJava...
 *
 * Created by James on 17/12/1.
 */

public class USetOkHttpWrapper {

    private static USetOkHttpWrapper sInstance;
    private USetOkHttpConfigImpl mConfig;
    private OkHttpClient mOkHttpClient;
    private Retrofit mRetrofit;
    private Retrofit.Builder mBuilder;

    private USetOkHttpWrapper() {
        init();
    }

    private void init() {
        initConfig();
        initOkHttpClient();
        initRetrofit();
    }

    private void initConfig() {
        mConfig = new USetOkHttpConfigImpl();
    }

    private void initOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (mConfig.getCache() != null) {
            builder.cache(mConfig.getCache());
        }
        if (!CollectionUtils.isEmpty(mConfig.getInterceptors())) {
            for (Interceptor i : mConfig.getInterceptors()) {
                builder.addInterceptor(i);
            }
        }

        mOkHttpClient = builder.build();
    }

    /**
     * retrofit默认连接配置，连接超时15秒,读取超时20秒,没有写入超时
     */
    private void initRetrofit() {
        mBuilder = new Retrofit.Builder();
        mBuilder.callFactory(mOkHttpClient)
                .baseUrl(mConfig.getBaseUrl());
        if (!CollectionUtils.isEmpty(mConfig.getConverters())) {
            for (Converter.Factory c : mConfig.getConverters()) {
                mBuilder.addConverterFactory(c);
            }
        }
        mBuilder.addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        mRetrofit = mBuilder.build();
    }

    public static USetOkHttpWrapper getInstance() {
        if (sInstance == null) {
            synchronized (USetOkHttpWrapper.class) {
                if (sInstance == null) {
                    sInstance = new USetOkHttpWrapper();
                }
            }
        }
        return sInstance;
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    public boolean clearCache() {
        try {
            mConfig.getCache().delete();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void updateBaseUrl(String url) {
        if (!TextUtils.isEmpty(url)) {
            mBuilder = mBuilder.baseUrl(url);
            mRetrofit = mBuilder.build();
        }
    }

}
