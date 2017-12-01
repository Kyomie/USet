package cn.ytang.james.uset.okhttp.config;

import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

import okhttp3.Cache;
import okhttp3.Interceptor;
import retrofit2.Converter;

/**
 * OkHttp ｜ retrofit configs
 * extensions will be added in the future for business need
 * Created by James on 17/12/1.
 */

public interface IOkHttpConfig {

  /**
   * interceptor for doing something on the request or response
   * 
   * @return
   */
  List<Interceptor> getInterceptors();

  /**
   * cache for okhttp lib
   * 
   * @return
   */
  Cache getCache();

  /**
   * protocol + domain, like "http://api.uset.com"
   * 
   * @return
   */
  String getBaseUrl();

  /**
   * response data converter, json, xml, etc
   * 
   * @return
   */
  List<Converter.Factory> getConverters();

  /**
   * ssl for https
   * 
   * @return
   */
  SSLSocketFactory getSSLSocketFactory();

  /**
   * verify for request
   * 
   * @return
   */
  HostnameVerifier getHostnameVerifier();

}
