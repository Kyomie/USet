package cn.ytang.james.uset.okhttp.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * response 响应cache-control字段改写
 * 请求接口上加注解@Header("Cache-control:public, max-age=单位s的时间")
 *
 * Created by James on 17/12/1.
 */
public class CacheInterceptor implements Interceptor {

  private static final String CACHE_CONTROL = "Cache-Control";

  @Override
  public Response intercept(Chain chain) throws IOException {
    Response origin = chain.proceed(chain.request());
    String cacheControl = chain.request().cacheControl().toString();
    return origin.newBuilder().header(CACHE_CONTROL, cacheControl).build();
  }
}
