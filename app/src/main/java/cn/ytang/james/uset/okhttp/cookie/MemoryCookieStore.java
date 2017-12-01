package cn.ytang.james.uset.okhttp.cookie;

import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.ytang.james.uset.utils.CollectionUtils;

/**
 * 内存缓存
 * 注意：不处理过期情况，因为客户端时间可能不准
 * 
 * Created by James on 17/12/1.
 */

public class MemoryCookieStore implements CookieStore {

  /**
   * this map may have null keys!
   */
  private final Map<URI, List<HttpCookie>> map = new HashMap<>();

  @Override
  public synchronized void add(URI uri, HttpCookie cookie) {
    if (cookie == null) {
      return;
    }

    uri = CookieUtils.cookiesUri(CookieUtils.getDomain(uri, cookie));
    List<HttpCookie> cookies = map.get(uri);
    if (cookies == null) {
      cookies = new ArrayList<>();
      map.put(uri, cookies);
    } else {
      cookies.remove(cookie);
    }
    cookies.add(cookie);
  }

  @Override
  public synchronized List<HttpCookie> get(URI uri) {
    List<HttpCookie> result = new ArrayList<>();
    if (uri == null) {
      return result;
    }

    // get cookies associated with given URI. If none, returns an empty list
    List<HttpCookie> cookiesForUri = map.get(uri);
    if (!CollectionUtils.isEmpty(cookiesForUri)) {
      for (Iterator<HttpCookie> i = cookiesForUri.iterator(); i.hasNext();) {
        HttpCookie cookie = i.next();
        result.add(cookie);
      }
    }

    // get all cookies that domain matches the URI
    for (Map.Entry<URI, List<HttpCookie>> entry : map.entrySet()) {
      if (uri.equals(entry.getKey())) {
        continue; // skip the given URI; we've already handled it
      }

      // key uri domain matches the URI
      if (entry.getKey() != null
          && CookieUtils.domainMatches(entry.getKey().getHost(), uri.getHost())) {
        result.addAll(entry.getValue());
      }

      // cookies domain matches the URI
      List<HttpCookie> entryCookies = entry.getValue();
      for (Iterator<HttpCookie> i = entryCookies.iterator(); i.hasNext();) {
        HttpCookie cookie = i.next();
        if (!CookieUtils.domainMatches(cookie.getDomain(), uri.getHost())) {
          continue;
        }
        result.add(cookie);
      }
    }

    // avoid duplicate cookie key
    List<HttpCookie> uniResult = CookieUtils.deleteDuplicateKey(result);

    return Collections.unmodifiableList(uniResult);
  }

  @Override
  public synchronized List<HttpCookie> getCookies() {
    List<HttpCookie> result = new ArrayList<HttpCookie>();
    for (List<HttpCookie> list : map.values()) {
      for (Iterator<HttpCookie> i = list.iterator(); i.hasNext();) {
        HttpCookie cookie = i.next();
        result.add(cookie);
      }
    }
    return Collections.unmodifiableList(result);
  }

  @Override
  public synchronized List<URI> getURIs() {
    List<URI> result = new ArrayList<URI>(map.keySet());
    result.remove(null); // sigh
    return Collections.unmodifiableList(result);
  }

  @Override
  public synchronized boolean remove(URI uri, HttpCookie cookie) {
    if (cookie == null) {
      return false;
    }

    List<HttpCookie> cookies = map.get(CookieUtils.getDomain(uri, cookie));
    if (cookies != null) {
      return cookies.remove(cookie);
    } else {
      return false;
    }
  }

  @Override
  public synchronized boolean removeAll() {
    boolean result = !map.isEmpty();
    map.clear();
    return result;
  }

}
