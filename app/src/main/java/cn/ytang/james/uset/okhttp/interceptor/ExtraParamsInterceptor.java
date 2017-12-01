package cn.ytang.james.uset.okhttp.interceptor;

import android.util.Log;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * app请求共工参数
 *
 * Created by James on 17/12/1.
 */

public class ExtraParamsInterceptor implements Interceptor {

    private final static String TAG = ExtraParamsInterceptor.class.getSimpleName();

    private final static String KEY_CLIENT_TYPE = "clientType";
    private final static String KEY_UID = "uid";
    private final static String KEY_LOGIN_TOKEN = "loginToken";
    private final static String VALUE_CLIENT_TYPE = "Android";
    private final static String DEVICE_ID = "deviceId";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl.Builder urlBuilder = request.url().newBuilder();
        urlBuilder.addQueryParameter(KEY_CLIENT_TYPE, VALUE_CLIENT_TYPE);
        //todo
//        urlBuilder.addQueryParameter(KEY_VERSION, String.valueOf(BuildConfig.VERSION_CODE));
//        urlBuilder.addQueryParameter(DEVICE_ID, Constants.getUDID());
//        AccountModel model = AccountManager.getInstance().getAccountData();
//        if (model != null) {
//            urlBuilder.addQueryParameter(KEY_UID, String.valueOf(model.getUid()));
//            urlBuilder.addQueryParameter(KEY_LOGIN_TOKEN, model.getLoginToken());
//        }
        HttpUrl httpUrl = urlBuilder.build();

        Log.d(TAG, httpUrl.toString());

        request = request.newBuilder().url(httpUrl).build();
        return chain.proceed(request);
    }
}
