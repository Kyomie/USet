package cn.ytang.james.uset.okhttp.cookie;

import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import cn.ytang.james.uset.utils.CollectionUtils;

/**
 *
 * Created by James on 17/12/1.
 */
public class CookieStoreWrap implements CookieStore {

  private MemoryCookieStore mMemoryStore;
  private SharePrefCookieStore mSpStore;

  public CookieStoreWrap() {
    mMemoryStore = new MemoryCookieStore();
    mSpStore = new SharePrefCookieStore();
  }

  @Override
  public void add(URI uri, HttpCookie cookie) {
    mMemoryStore.add(uri, cookie);
    mSpStore.add(uri, cookie);
  }

  @Override
  public List<HttpCookie> get(URI uri) {
    List<HttpCookie> cookies = mMemoryStore.get(uri);
    if (!CollectionUtils.isEmpty(cookies)) {
      return cookies;
    }
    cookies = mSpStore.get(uri);
    if (!CollectionUtils.isEmpty(cookies)) {
      for (HttpCookie cookie : cookies) {
        mMemoryStore.add(uri, cookie);
      }
      return cookies;
    }
    cookies = new ArrayList<>();
    return cookies;
  }

  @Override
  public List<HttpCookie> getCookies() {
    List<HttpCookie> cookies = mSpStore.getCookies();
    if (!CollectionUtils.isEmpty(cookies)) {
      return cookies;
    }
    cookies = new ArrayList<>();
    return cookies;
  }

  @Override
  public List<URI> getURIs() {
    List<URI> uris = mSpStore.getURIs();
    if (!CollectionUtils.isEmpty(uris)) {
      return uris;
    }
    uris = new ArrayList<>();
    return uris;
  }

  @Override
  public boolean remove(URI uri, HttpCookie cookie) {
    mMemoryStore.remove(uri, cookie);
    mSpStore.remove(uri, cookie);
    return true;
  }

  @Override
  public boolean removeAll() {
    mMemoryStore.removeAll();
    mSpStore.removeAll();
    return true;
  }
}
