package cn.ytang.james.uset.okhttp.cookie;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;

import java.io.Serializable;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.ytang.james.uset.conf.GlobalConfig;
import cn.ytang.james.uset.utils.CollectionUtils;
import cn.ytang.james.uset.utils.GsonUtil;

/**
 * cookie 的 SharePref 缓存。
 * 这里的 domain 为 cookie 的 domain，不是 uri 的 host。
 * 设计：
 * 使用一个 pref 存储 domain list。
 * 使用与 domain 一一对应的 pref 存储当前 domain 下的 cookie。
 *
 * 注意：不处理过期情况，因为客户端时间可能不准
 *
 * Created by James on 17/12/1.
 */
public class SharePrefCookieStore implements CookieStore {

  /**
   * 存储 DomainSet 的 pref 地址
   */
  private static final String PREFS_DOMAIN_SET_PATH = "uset_domain";
  /**
   * 存储对应 domain cookie 的 pref 地址前缀
   */
  private static final String PREFS_DOMAIN_PREFIX = "domain_";

  /**
   * DomainSet 的存储 key
   */
  private static final String KEY_DOMAIN_SET = "domain_set";
  /**
   * Cookie 的存储 key 前缀
   */
  private static final String KEY_COOKIE_PREFIX = "cookie_";

  /**
   * submit 线程池
   */
  private final ExecutorService mPool;
  /**
   * 存储各个 domain cookie 的 pref 以 domain 为 key
   */
  private Map<String, SharedPreferences> mPrefs;
  /**
   * DomainSet 的 pref
   */
  private SharedPreferences mDomainSetPref;
  /**
   * DomainSet 的内存缓存
   */
  private DomainSet mDomainSet = null;


  public SharePrefCookieStore() {
    mPool = Executors.newSingleThreadExecutor();
    preLoad();
  }

  @Override
  public void add(URI uri, HttpCookie cookie) {
    if (cookie == null) {
      return;
    }

    SharedPreferences.Editor editor = getPrefs(CookieUtils.getDomain(uri, cookie)).edit();
    editor.putString(getCookieKey(cookie), GsonUtil.getGson().toJson(cookie));
    submit(editor);

    saveDomainSet(cookie.getDomain());
  }

  @Override
  public List<HttpCookie> get(URI uri) {
    List<HttpCookie> cookies = new ArrayList<>();
    if (uri == null) {
      return cookies;
    }
    uri = CookieUtils.cookiesUri(uri);
    List<String> domainList = getDomain(uri);
    if (CollectionUtils.isEmpty(domainList)) {
      return cookies;
    }
    /**
     * 获取所有 cookie 并生成 HttpCookie
     */
    for (String domain : domainList) {

      Map<String, ?> cookieMap = getPrefs(domain).getAll();
      if (cookieMap != null && !cookieMap.isEmpty()) {
        for (String cookie : cookieMap.keySet()) {
          Object cookieValue = cookieMap.get(cookie);
          if (cookieValue != null) {
            HttpCookie httpCookie;
            try {
              httpCookie =
                  GsonUtil.getGson().fromJson(cookieValue.toString(), HttpCookie.class);
            } catch (Exception e) {
              continue;
            }
            if (httpCookie != null) {
              cookies.add(httpCookie);
            }
          }
        }
      }

    }

    // avoid duplicate cookie key
    List<HttpCookie> uniResult = CookieUtils.deleteDuplicateKey(cookies);

    return Collections.unmodifiableList(uniResult);
  }

  @Override
  public List<HttpCookie> getCookies() {
    List<HttpCookie> cookies = new ArrayList<>();
    if (mDomainSet == null) {
      initDomainSet();
    }
    if (mDomainSet == null || CollectionUtils.isEmpty(mDomainSet.getDomains())) {
      return cookies;
    }
    for (String domain : mDomainSet.getDomains()) {

      Map<String, ?> cookieMap = getPrefs(domain).getAll();
      if (cookieMap != null && !cookieMap.isEmpty()) {
        for (String cookie : cookieMap.keySet()) {
          Object cookieValue = cookieMap.get(cookie);
          if (cookieValue != null) {
            List<HttpCookie> httpCookie = HttpCookie.parse(cookieValue.toString());
            if (httpCookie != null && !httpCookie.isEmpty()) {
              cookies.add(httpCookie.get(0));
            }
          }
        }
      }

    }


    return cookies;
  }

  @Override
  public List<URI> getURIs() {
    List<URI> uris = new ArrayList<>();

    if (mDomainSet == null) {
      initDomainSet();
    }
    if (mDomainSet == null || CollectionUtils.isEmpty(mDomainSet.getDomains())) {
      return uris;
    }
    for (String domain : mDomainSet.getDomains()) {
      uris.add(CookieUtils.createUriWithDomain(domain));
    }
    return uris;
  }

  @Override
  public boolean remove(URI uri, HttpCookie cookie) {
    if (cookie == null) {
      return false;
    }
    uri = CookieUtils.cookiesUri(uri);
    List<String> domainList = getDomain(uri);
    if (CollectionUtils.isEmpty(domainList)) {
      return false;
    }
    for (String domain : domainList) {
      SharedPreferences.Editor editor = getPrefs(domain).edit();
      editor.remove(getCookieKey(cookie));
      submit(editor);
    }
    return true;
  }

  @Override
  public boolean removeAll() {
    clear();
    return true;
  }


  public void clear() {
    if (mPrefs != null && !mPrefs.isEmpty()) {
      for (String domain : mPrefs.keySet()) {
        SharedPreferences.Editor editor = mPrefs.get(domain).edit();
        editor.clear();
        submit(editor);
      }
    }

    if (mDomainSetPref != null) {
      SharedPreferences.Editor editor = mDomainSetPref.edit();
      editor.clear();
      submit(editor);
    }
  }

  /**
   * 获取 domain set 的 pref
   *
   * @return
   */
  private SharedPreferences getDomainSetPref() {
    if (mDomainSetPref == null) {
      mDomainSetPref = GlobalConfig.getAppContext()
          .getSharedPreferences(PREFS_DOMAIN_SET_PATH, Context.MODE_PRIVATE);
    }
    return mDomainSetPref;
  }

  /**
   * 获取对应 domain 的 pref
   *
   * @param domain
   * @return
   */
  private SharedPreferences getPrefs(String domain) {
    if (mPrefs == null) {
      mPrefs = new HashMap<>();
    }

    if (!mPrefs.containsKey(PREFS_DOMAIN_PREFIX + domain)) {
      SharedPreferences pref = GlobalConfig.getAppContext()
          .getSharedPreferences(PREFS_DOMAIN_PREFIX + domain, Context.MODE_PRIVATE);
      mPrefs.put(PREFS_DOMAIN_PREFIX + domain, pref);
    }

    return mPrefs.get(PREFS_DOMAIN_PREFIX + domain);
  }


  @TargetApi(Build.VERSION_CODES.GINGERBREAD)
  private void submit(final SharedPreferences.Editor editor) {
    if (Build.VERSION.SDK_INT > 9) {
      editor.apply();
    } else {
      mPool.execute(new Runnable() {

        @Override
        public void run() {
          editor.commit();
        }
      });
    }
  }

  /**
   * 预加载 domain set 的 pref
   */
  private void preLoad() {
    getDomainSetPref();
  }

  /**
   * 存 pref 中读取 domain set
   */
  private void initDomainSet() {
    SharedPreferences domainSetPref = getDomainSetPref();
    String domainSetString = domainSetPref.getString(KEY_DOMAIN_SET, null);
    if (!TextUtils.isEmpty(domainSetString)) {
      mDomainSet = GsonUtil.getGson().fromJson(domainSetString, DomainSet.class);
    }
  }

  /**
   * 同时更新内存缓存中的 domain set 和 pref 里面的 domain set
   *
   * @param domain
   */
  private void saveDomainSet(String domain) {
    if (mDomainSet == null) {
      initDomainSet();
    }
    if (mDomainSet == null) {
      mDomainSet = new DomainSet();
    }
    mDomainSet.add(domain);

    SharedPreferences.Editor domainEditor = getDomainSetPref().edit();
    domainEditor.putString(KEY_DOMAIN_SET, GsonUtil.getGson().toJson(mDomainSet));
    submit(domainEditor);
  }

  /**
   * 通过 uri 查找 domain。
   * 规则是：
   * 如果 uri 或者 本地存储 domain set 为空则返回空
   * 如果 uri 能找到对应的 domain 结尾 则返回 domain，找不到对应的返回空
   *
   * @param uri
   * @return
   */
  private List<String> getDomain(URI uri) {
    List<String> domainList = null;
    if (uri == null || TextUtils.isEmpty(uri.getHost())) {
      return domainList;
    }
    if (mDomainSet == null) {
      initDomainSet();
    }
    if (mDomainSet == null || CollectionUtils.isEmpty(mDomainSet.getDomains())) {
      return domainList;
    }
    domainList = new ArrayList<>();
    for (String domain : mDomainSet.getDomains()) {
      if (CookieUtils.domainMatches(domain, uri.getHost())) {
        domainList.add(domain);
      }
    }
    return domainList;
  }

  /**
   * 生成 cookie 的存储 key
   *
   * @param cookie
   * @return
   */
  private String getCookieKey(HttpCookie cookie) {
    if (cookie == null || TextUtils.isEmpty(cookie.getName())) {
      return KEY_COOKIE_PREFIX;
    }
    return KEY_COOKIE_PREFIX + cookie.getName();
  }

  /**
   * 维护 domain 的集合类
   */
  private class DomainSet implements Serializable {
    private Set<String> domains;

    private DomainSet() {
      this.domains = new HashSet<>();
    }

    public Set<String> getDomains() {
      return domains;
    }

    public void add(String domain) {
      if (domains == null) {
        domains = new HashSet<>();
      }
      domains.add(domain);
    }
  }

}
