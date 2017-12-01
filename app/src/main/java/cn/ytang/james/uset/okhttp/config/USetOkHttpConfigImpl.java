package cn.ytang.james.uset.okhttp.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

import cn.ytang.james.uset.conf.GlobalConfig;
import cn.ytang.james.uset.okhttp.interceptor.CacheInterceptor;
import cn.ytang.james.uset.okhttp.interceptor.ExtraParamsInterceptor;
import okhttp3.Cache;
import okhttp3.Interceptor;
import retrofit2.Converter;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 *
 * Created by James on 17/12/1.
 */

public class USetOkHttpConfigImpl implements IOkHttpConfig {

    private static final String CACHE_PATH = "/api/okhttp";
    private static final  long CACHE_SIZE = 5 * 1024 * 1024;

    private List<Interceptor> mInterceptors;
    private Cache mCache;
    private List<Converter.Factory> mConverterFactories;

    public USetOkHttpConfigImpl() {
        initInterceptors();
        initCache();
        initConverters();
    }

    private void initInterceptors() {
        mInterceptors = new ArrayList<>();
        mInterceptors.add(new ExtraParamsInterceptor());
        mInterceptors.add(new CacheInterceptor());
    }

    private void initCache() {
        String parentPath = getCacheParentPath();
        if (parentPath != null) {
            File f = new File(parentPath.concat(CACHE_PATH));
            if (f.exists() || f.mkdirs()) {
                mCache = new Cache(f, CACHE_SIZE);
            }
        }
    }

    private void initConverters() {
        mConverterFactories = new ArrayList<>();
        mConverterFactories.add(GsonConverterFactory.create());
    }

    @Override
    public List<Interceptor> getInterceptors() {
        return mInterceptors;
    }

    @Override
    public Cache getCache() {
        return mCache;
    }

    @Override
    public String getBaseUrl() {
        return GlobalConfig.getBaseUrl();
    }

    @Override
    public List<Converter.Factory> getConverters() {
        return mConverterFactories;
    }

    @Override
    public SSLSocketFactory getSSLSocketFactory() {
        //TODO
        return null;
    }

    @Override
    public HostnameVerifier getHostnameVerifier() {
        //TODO
        return null;
    }

    private String getCacheParentPath() {
        File file = null;
        try {
            file = GlobalConfig.getAppContext().getExternalFilesDir(null);
        } catch (Exception e) {
            e.printStackTrace();
            //here may happen internal exception for some mobile, just ignore.
        }
        if (file == null || !file.exists()) {
            file = GlobalConfig.getAppContext().getCacheDir();
        }
        String path = null;
        if (file != null) {
            path = file.getAbsolutePath();
        }
        return path;
    }

}
