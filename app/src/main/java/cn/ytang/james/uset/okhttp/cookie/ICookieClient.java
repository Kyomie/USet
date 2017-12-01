package cn.ytang.james.uset.okhttp.cookie;

import java.net.HttpCookie;
import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * cookie业务接口
 *
 * Created by James on 17/12/1.
 */

public interface ICookieClient {

  String KEY_REQUEST_COOKIE = "Cookie";
  String KEY_RESPONSE_COOKIE = "Set-Cookie";

  /**
   * 通过CookieManager解析header头并cache
   * 
   * @param uri
   * @param header
   */
  void saveCookie(URI uri, Map<String, List<String>> header);

  /**
   * 直接cache cookie信息
   * 
   * @param uri
   * @param cookie
   */
  void saveCookie(URI uri, HttpCookie cookie);

  /**
   * host或domain匹配下的所有缓存cookie值（以"；"作为分隔符的键值对String）
   * 
   * @param uri
   * @return
   */
  String getCookieValue(URI uri);

  /**
   * host或domain匹配下的所有缓存httpcookie集
   * 
   * @param uri
   * @return
   */
  List<HttpCookie> getCookies(URI uri);

  /**
   * clear all cache
   */
  void clear();

}
