package cn.ytang.james.uset.okhttp.cookie;

import android.text.TextUtils;
import android.util.Log;

import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cn.ytang.james.uset.utils.CollectionUtils;

/**
 *
 * Created by James on 17/12/1.
 */
public class CookieUtils {

  private static final String TAG = "CookieUtils";
  public static final String WEBVIEW_COOKIE_DOMAIN = "ffan.com";
  public static final String WEBVIEW_COOKIE_PATH = "/";
  public static final int WEBVIEW_COOKIE_MAXAGE = 31535999;

  private static final String HTTP_SCHEME = "http";
  private static final String COOKIE_DATE_FORMAT = "EEE, dd MMM yyyy hh:mm:ss z";
  private static final String COOKIE_SPLIT = ";";

  private static final String COOKIE_DOMAIN_SPLIT = ".";

  /**
   * 缓存 key 只关心 host
   */
  public static URI cookiesUri(String host) {
    if (TextUtils.isEmpty(host)) {
      return null;
    }
    try {
      return new URI(HTTP_SCHEME, host, null, null);
    } catch (URISyntaxException e) {
      return null;
    }
  }

  /**
   * 缓存 key 只关心 host
   *
   * @param uri
   * @return
   */
  public static URI cookiesUri(URI uri) {
    if (uri == null) {
      return null;
    }
    try {
      return new URI(HTTP_SCHEME, uri.getHost(), null, null);
    } catch (URISyntaxException e) {
      return uri; // probably a URI with no host
    }
  }

  public static URI createUriWithDomain(String domain) {
    try {
      return new URI(HTTP_SCHEME, domain, null, null);
    } catch (URISyntaxException e) {
      return null;
    }
  }

  public static String getDomain(URI uri, HttpCookie cookie) {
    if (cookie != null && !TextUtils.isEmpty(cookie.getDomain())) {
      return cookie.getDomain();
    }
    if (uri != null && !TextUtils.isEmpty(uri.getHost())) {
      return uri.getHost();
    }
    return null;
  }

  public static boolean domainMatches(String domain, String host) {
    if (TextUtils.isEmpty(domain) || TextUtils.isEmpty(host)) {
      return false;
    }

    String hostLower = host.toLowerCase(Locale.US);
    String domainLower = domain.toLowerCase(Locale.US);

    if (TextUtils.equals(hostLower, domainLower)) {
      return true;
    }

    /**
     * 匹配顶级域名，例如 host 是 ffan.com ，可以击中 .ffan.com 的 domain
     */
    if (domainLower.startsWith(COOKIE_DOMAIN_SPLIT)) {
      domainLower = domainLower.substring(1);
    }

    if (hostLower.endsWith(domainLower)) {
      return true;
    }
    return false;
  }

  public static String cookieToStringForWebView(HttpCookie cookie) {
    if (cookie == null) {
      return null;
    }
    Calendar calendar = Calendar.getInstance();
    StringBuilder builder = new StringBuilder();
    builder.append(cookie.getName());
    builder.append("=");
    builder.append(cookie.getValue());
    builder.append("; domain=");
    builder.append(cookie.getDomain());
    if (!cookie.hasExpired()) {
      builder.append("; expires=");
      long expires = cookie.getMaxAge() * 1000 + System.currentTimeMillis();
      Date date = new Date(expires);
      calendar.setTime(date);
      builder.append(new SimpleDateFormat(COOKIE_DATE_FORMAT).format(calendar.getTimeInMillis()));
    }
    builder.append("; path=");
    builder.append(cookie.getPath());
    builder.append("; version=");
    builder.append(cookie.getVersion());
    return builder.toString();
  }

  public static List<HttpCookie> parseWebViewCookieString(String cookieString) {
    List<HttpCookie> cookieList = null;
    if (!TextUtils.isEmpty(cookieString)) {
      cookieList = new ArrayList<>();
      String[] cookies = cookieString.split(COOKIE_SPLIT);
      if (cookies.length > 0) {
        for (String cookieStr : cookies) {
          try {
            List<HttpCookie> httpCookies = HttpCookie.parse(cookieStr);
            CookieUtils.setWebViewCookieDomain(httpCookies);
            cookieList.addAll(httpCookies);
          } catch (Exception e) {
            Log.e(TAG, e.getMessage());
          }
        }
      }
    }
    return cookieList;
  }

  /**
   * 去除重复的 cookie key ，只在 wanda 使用，会造成不同的 host 下相同的 cookie 覆盖。
   *
   * @param cookieList
   * @return
   */
  public static List<HttpCookie> deleteDuplicateKey(List<HttpCookie> cookieList) {
    if (CollectionUtils.isEmpty(cookieList)) {
      return cookieList;
    }
    Map<String, HttpCookie> cookieMap = new HashMap<>();
    for (HttpCookie cookie : cookieList) {
      if (cookie != null) {
        cookieMap.put(cookie.getName(), cookie);
      }
    }
    List<HttpCookie> cookies = new ArrayList<>();
    for (String key : cookieMap.keySet()) {
      cookies.add(cookieMap.get(key));
    }
    return cookies;
  }


  /**
   * 目前所有 WebView 内写入的 Cookie 都将种在 ffan.com 这个 domain 下
   *
   * @param httpCookies
   */
  public static void setWebViewCookieDomain(List<HttpCookie> httpCookies) {
    if (!CollectionUtils.isEmpty(httpCookies)) {
      for (HttpCookie cookie : httpCookies) {
        if (cookie != null) {
          cookie.setDomain(WEBVIEW_COOKIE_DOMAIN);
          cookie.setPath(WEBVIEW_COOKIE_PATH);
          cookie.setMaxAge(WEBVIEW_COOKIE_MAXAGE);
        }
      }
    }
  }

}
