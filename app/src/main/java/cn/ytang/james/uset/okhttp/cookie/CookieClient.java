package cn.ytang.james.uset.okhttp.cookie;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.ytang.james.uset.utils.CollectionUtils;

/**
 *
 * Created by James on 17/12/1.
 */
public class CookieClient implements ICookieClient {

    private static ICookieClient sInstance;
    private CookieManager mCookieManager;
    private CookieStoreWrap mCookieStore;
    //TODO add account module
//    private LoginStateListener mLoginStateListener;

    public CookieClient() {
        mCookieStore = new CookieStoreWrap();
        mCookieManager = new CookieManager(mCookieStore, CookiePolicy.ACCEPT_ALL);
        //TODO add account module
//        mLoginStateListener = new LoginStateListener() {
//            @Override
//            public void onLoginSucceed(AccountModel model, String msg) {
//
//            }
//
//            @Override
//            public void onLogout() {
//                clear(); // 登出，清除cookie
//            }
//        };
//        AccountManager.getInstance().addLoginStateListener(mLoginStateListener);
    }

    public static synchronized ICookieClient getInstance() {
        if (sInstance == null) {
            sInstance = new CookieClient();
        }
        return sInstance;
    }

    @Override
    public void saveCookie(URI uri, Map<String, List<String>> header) {
        if (uri == null || header == null) {
            return;
        }
        try {
            mCookieManager.put(uri, header);
        } catch (IOException e) {
            // do nothing
        }
    }

    @Override
    public void saveCookie(URI uri, HttpCookie cookie) {
        if (uri == null || cookie == null) {
            return;
        }
        mCookieStore.add(uri, cookie);
    }

    @Override
    public String getCookieValue(URI uri) {
        if (uri == null) {
            return null;
        }
        Map<String, List<String>> cookieHeader = null;

        try {
            cookieHeader = mCookieManager.get(uri, new HashMap<String, List<String>>());
        } catch (IOException e) {
            // do nothing
        }

        if (CollectionUtils.isEmpty(cookieHeader)
                || CollectionUtils.isEmpty(cookieHeader.get(KEY_REQUEST_COOKIE))) {
            return null;
        }

        return cookieHeader.get(KEY_REQUEST_COOKIE).get(0);
    }

    @Override
    public List<HttpCookie> getCookies(URI uri) {
        return mCookieStore.get(uri);
    }

    @Override
    public void clear() {
        mCookieStore.removeAll();
    }
}
