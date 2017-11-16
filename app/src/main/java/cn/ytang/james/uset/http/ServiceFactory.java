package cn.ytang.james.uset.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 *
 *
 * Created by James on 17/11/16.
 */

public class ServiceFactory {

    public ServiceFactory() {
    }

    public static class SingleTonHolder {
        private static final ServiceFactory INSTANCE = new ServiceFactory();
    }

    public static ServiceFactory getInstance() {
        return SingleTonHolder.INSTANCE;
    }

    public <T> T createService(Class<T> serviceClass, String baseUrl){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).client(getOkHttpClient()).build();
        return retrofit.create(serviceClass);
    }

    private final static long DEFAULT_TIMEOUT = 10;

    private OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.addInterceptor(new HttpLogInterceptor());

        okHttpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        okHttpClientBuilder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        okHttpClientBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        return okHttpClientBuilder.build();
    }

}
